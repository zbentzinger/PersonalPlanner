package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
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
import personalplanner.Models.Appointment;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

public class EditAppointmentViewController implements Initializable {

    private Appointment appointment; 
    private User user;

    @FXML private Button editAppCancelButton;
    @FXML private Button editAppSaveButton;
    @FXML private TableView<Customer> selectCustTable;    
    @FXML private TableColumn<Customer, String> customerNameCol;
    @FXML private TextField locationTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private DatePicker dayPicker;
    @FXML private TextField fromText;
    @FXML private TextField toText;

    // Rubric F: Don't enable save button if not all fields are populated.
    private void bindButtonsToForm() {

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

        editAppSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CALENDAR_VIEW_PATH));
            Stage stage = (Stage) editAppCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CalendarViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void populateCustomersTable() {

        customerNameCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomerName())
        );

        selectCustTable.setItems(Utils.DATABASE.getAllCustomers());
        selectCustTable.setPlaceholder(new Label(""));

    }

    private void save() {

        this.appointment.setCustomer(selectCustTable.getSelectionModel().getSelectedItem());
        this.appointment.setUser(this.user);
        this.appointment.setDescription(descriptionTextField.getText());
        this.appointment.setLocation(locationTextField.getText());
        this.appointment.setType(typeTextField.getText());
        this.appointment.setStart(
            LocalDateTime.of(
                dayPicker.getValue(),
                Utils.getLocalTimeFromString(fromText.getText())
            )
        );
        this.appointment.setEnd(
            LocalDateTime.of(
                dayPicker.getValue(),
                Utils.getLocalTimeFromString(toText.getText())
            )
        );
        this.appointment.setUpdatedBy(this.user.getUserName());

        if (this.validateAppointment(this.appointment)) {

            Utils.DATABASE.updateAppointment(this.appointment);

            try {

                FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CALENDAR_VIEW_PATH));
                Stage stage = (Stage) editAppSaveButton.getScene().getWindow();
                stage.setScene(new Scene((Parent) loader.load()));
                CalendarViewController controller = loader.getController();
                controller.initData(this.user);
                stage.show();

            } catch (IOException ex) {

                Utils.LOGGER.log(Level.SEVERE, null, ex);

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
            if (Utils.DATABASE.appointmentConflicts(app)) {           

                isValid = false;
                invalidTimeAlert.setContentText("Appointment time conflicts with another appointment");
                invalidTimeAlert.show();

            }

        }

        return isValid;
    
    }

    public void initData(User user, Appointment app) {

        this.user = user;
        this.appointment = app;

        selectCustTable.getSelectionModel().select(this.appointment.getCustomer());
        descriptionTextField.setText(this.appointment.getDescription());
        typeTextField.setText(this.appointment.getType());
        locationTextField.setText(this.appointment.getLocation());
        dayPicker.setValue(this.appointment.getStart().toLocalDate());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mm a");
        fromText.setText(this.appointment.getStart().format(formatter));
        toText.setText(this.appointment.getEnd().format(formatter));

    }

    // Rubric B: Ability to edit Appointment.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.populateCustomersTable();
        this.bindButtonsToForm();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        editAppCancelButton.setOnAction(e -> this.cancel());
        editAppSaveButton.setOnAction(e -> this.save());

    }

}
