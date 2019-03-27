package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
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

public class EditAppointmentViewController implements Initializable {

    private Appointment appointment; 
    private MainDAO database;
    private User user;
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";

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
    @FXML private Label errorLabel;

    private void bindButtons() {

        // Make sure that all fields have a value before enabling save button.
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

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));
            
            Stage stage = (Stage) editAppCancelButton.getScene().getWindow();
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

         return LocalTime.parse(sanitized, formatter);

    }

    private void populateCustomersTable() {

        selectCustTable.setItems(this.database.getAllCustomers());
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
                getTime(fromText.getText())
            )
        );

        this.appointment.setEnd(
            LocalDateTime.of(
                dayPicker.getValue(),
                getTime(toText.getText())
            )
        );

        this.appointment.setUpdatedBy(this.user.getUserName());

        this.database.updateAppointment(this.appointment);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));
            
            Stage stage = (Stage) editAppSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CalendarViewController controller = loader.getController();
            controller.initData(this.user);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddAppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

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

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        customerNameCol.setCellValueFactory(
            new PropertyValueFactory<>("customerName")
        );

        populateCustomersTable();

        bindButtons();

        editAppCancelButton.setOnAction(e -> cancel());
        editAppSaveButton.setOnAction(e -> save());

        errorLabel.setVisible(false);
    }

}
