package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.Appointment;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public class AddAppointmentViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";

    @FXML private Button newAppCancelButton;
    @FXML private Button newAppSaveButton;
    @FXML private TableView<Customer> selectCustTable;
    @FXML private TableColumn<Customer, String> custNameCol;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private DatePicker dayPicker;
    @FXML private TextField fromText;
    @FXML private TextField toText;

    // Rubric F: Don't enable save button if not all fields are populated.
    private void bindButtons() {

        BooleanBinding enabledState = new BooleanBinding() {
            {
                super.bind(
                    typeTextField.textProperty(),
                    descriptionTextField.textProperty(),
                    locationTextField.textProperty(),
                    fromText.textProperty(),
                    toText.textProperty(),
                    selectCustTable.getSelectionModel().selectedItemProperty()
                );
            }

            @Override protected boolean computeValue() {
                return (
                    typeTextField.getText().isEmpty() ||
                    descriptionTextField.getText().isEmpty() ||
                    locationTextField.getText().isEmpty() ||
                    fromText.getText().isEmpty() ||
                    toText.getText().isEmpty() ||
                    selectCustTable.getSelectionModel().isEmpty()
                );
            }
        };

        newAppSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));
            
            Stage stage = (Stage) newAppCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            CalendarViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddAppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private LocalTime getTime(String timeStr) {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h[:mm]a");

         String sanitized = timeStr.replaceAll("\\s+","").toUpperCase();

         // instantiate with invalid time for error handling further on.
         LocalTime time = LocalTime.of(1, 0);
         try {

             time = LocalTime.parse(sanitized, formatter);

         } catch (Exception e) {
         } // do nothing

         return time;

    }

    private void populateCustomersTable() {

        selectCustTable.setItems(this.database.getAllCustomers());
        selectCustTable.setPlaceholder(new Label(""));

    }

    private void save() {

        Appointment app = new Appointment();
        app.setCustomer(selectCustTable.getSelectionModel().getSelectedItem());
        app.setUser(this.user);

        app.setDescription(descriptionTextField.getText());
        app.setLocation(locationTextField.getText());

        app.setType(typeTextField.getText());

        app.setStart(
            LocalDateTime.of(
                dayPicker.getValue(),
                getTime(fromText.getText())
            )
        );

        app.setEnd(
            LocalDateTime.of(
                dayPicker.getValue(),
                getTime(toText.getText())
            )
        );

        app.setCreatedBy(this.user.getUserName());
        app.setUpdatedBy(this.user.getUserName());
        
        if(this.validateAppointment(app)) {

            this.database.insertAppointment(app);

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));

                Stage stage = (Stage) newAppSaveButton.getScene().getWindow();
                stage.setScene(new Scene((Parent) loader.load()));

                CalendarViewController controller = loader.getController();
                controller.initData(this.user);

                stage.show();

            } catch (IOException ex) {

                Logger.getLogger(AddAppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);

            }

        }

    }

    // Rubric F: Appointment times outside business hours 8PM and 5PM local time
    // show an alert box
    private boolean validateAppointment(Appointment app) {

        boolean isValid = true;

        Alert invalidTimeAlert = new Alert(Alert.AlertType.ERROR);
        invalidTimeAlert.setTitle("Invalid Appointment");
        invalidTimeAlert.setHeaderText("Invalid appointment time");

        // Rubric F1
        if (app.getEnd().isBefore(app.getStart()) ||
            app.getStart().isEqual(app.getEnd()) ||
            app.getStart().getHour() < 8 ||
            app.getStart().getHour() > 17 || 
            app.getEnd().getHour() < 8 ||
            app.getEnd().getHour() > 17 ||
            app.getStart().getDayOfWeek() == DayOfWeek.SATURDAY ||
            app.getStart().getDayOfWeek() == DayOfWeek.SUNDAY ||
            app.getEnd().getDayOfWeek() == DayOfWeek.SATURDAY ||
            app.getEnd().getDayOfWeek() == DayOfWeek.SUNDAY)
        {

            isValid = false;
            invalidTimeAlert.setContentText("Appointment times are outside of business hours (8am-5pm) MON-FRI");
            invalidTimeAlert.show();

        } else {
            
            // Rubric F2 - moved to inside else statement to limit DB queries.
            if (this.database.appointmentConflicts(app)) {           

                isValid = false;
                invalidTimeAlert.setContentText("Appointment time conflicts with another appointment");
                invalidTimeAlert.show();

            }

        }

        return isValid;
    
    }

    public void initData(User user) {

        this.user = user;

    }

    // Rubric C: Ability to Add appointment and relation to Customer.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        custNameCol.setCellValueFactory(
            new PropertyValueFactory<>("customerName")
        );

        populateCustomersTable();

        bindButtons();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        newAppCancelButton.setOnAction(e -> cancel());
        newAppSaveButton.setOnAction(e -> save());

        dayPicker.setValue(LocalDate.now());

    }
    
}
