package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
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

        newAppSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));
            
            Stage stage = (Stage) newAppCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            CalendarViewController controller = loader.getController();
            controller.initData(this.user, this.database);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddAppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private LocalDateTime getDateTime(String hourMin) {

         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h[:mm]a");

         LocalTime parsedTime = LocalTime.parse(
            hourMin.replaceAll("\\s+","").toUpperCase(),
            formatter
         );

         return LocalDateTime.of(dayPicker.getValue(), parsedTime);

    }

    private void populateCustomersTable() {

        custNameCol.setCellValueFactory(
            new PropertyValueFactory<>("customerName")
        );

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

        app.setStart(getDateTime(fromText.getText()));
        app.setEnd(getDateTime(toText.getText()));
        app.setCreatedBy(this.user.getUserName());
        app.setUpdatedBy(this.user.getUserName());

        this.database.insertAppointment(app);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));
            
            Stage stage = (Stage) newAppSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CalendarViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddAppointmentViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

        populateCustomersTable();


    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        bindButtons();

        newAppCancelButton.setOnAction(e -> cancel());
        newAppSaveButton.setOnAction(e -> save());

        dayPicker.setValue(LocalDate.now());

        errorLabel.setVisible(false);

    }
    
}
