package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
import javafx.stage.Stage;
import personalplanner.Models.Appointment;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

public class AddAppointmentViewController implements Initializable {

    private Appointment appointment;
    private User user;

    @FXML private Button newAppCancelButton;
    @FXML private Button newAppSaveButton;
    @FXML private DatePicker dayPicker;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField locationTextField;
    @FXML private TextField fromText;
    @FXML private TextField toText;
    @FXML private TableView<Customer> selectCustTable;
    @FXML private TableColumn<Customer, String> customerCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> cityCol;

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

        newAppSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating back to the calendar and not saving", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CALENDAR_VIEW_PATH));
            Stage stage = (Stage) newAppCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CalendarViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void populateCustomersTable() {

        // Rubric G
        customerCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomerName())
        );
        phoneCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getPhone())
        );
        addressCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getAddress())
        );
        cityCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getCity().getCityName())
        );

        selectCustTable.setItems(Utils.DATABASE.getAllCustomers());
        selectCustTable.setPlaceholder(new Label(""));

    }

    private void save() {

        this.appointment = new Appointment();
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
        this.appointment.setCreatedBy(this.user.getUserName());
        this.appointment.setUpdatedBy(this.user.getUserName());
        
        if(this.validateAppointment()) {

            Utils.DATABASE.insertAppointment(this.appointment);

            try {

                Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating back to the calendar and saving", this.user.getUserName());

                FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CALENDAR_VIEW_PATH));
                Stage stage = (Stage) newAppSaveButton.getScene().getWindow();
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
    private boolean validateAppointment() {

        boolean isValid = true;

        Alert invalidTimeAlert = new Alert(Alert.AlertType.ERROR);
        invalidTimeAlert.setTitle("Invalid Appointment");
        invalidTimeAlert.setHeaderText("Invalid appointment time");

        // Rubric F1
        if (this.appointment.getEnd().isBefore(this.appointment.getStart()) ||
            this.appointment.getStart().isEqual(this.appointment.getEnd()) ||
            this.appointment.getStart().getHour() < 8 ||
            this.appointment.getStart().getHour() > 17 || 
            this.appointment.getEnd().getHour() < 8 ||
            this.appointment.getEnd().getHour() > 17 ||
            this.appointment.getStart().getDayOfWeek() == DayOfWeek.SATURDAY ||
            this.appointment.getStart().getDayOfWeek() == DayOfWeek.SUNDAY ||
            this.appointment.getEnd().getDayOfWeek() == DayOfWeek.SATURDAY ||
            this.appointment.getEnd().getDayOfWeek() == DayOfWeek.SUNDAY)
        {

            isValid = false;
            invalidTimeAlert.setContentText("Appointment times are outside of business hours (8am-5pm) MON-FRI");
            invalidTimeAlert.show();

        } else {
            
            // Rubric F2 - moved to inside else statement to limit DB queries.
            if (Utils.DATABASE.appointmentConflicts(this.appointment)) {           

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

        this.populateCustomersTable();
        this.bindButtonsToForm();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        newAppCancelButton.setOnAction(e -> this.cancel());
        newAppSaveButton.setOnAction(e -> this.save());

        dayPicker.setValue(LocalDate.now());

    }
    
}
