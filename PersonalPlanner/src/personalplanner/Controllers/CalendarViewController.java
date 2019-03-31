package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;
import personalplanner.Models.Appointment;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

// Rubric D: Ability to view calendar by month and week.
public class CalendarViewController implements Initializable {

    private User user;
    private LocalDateTime selectedMonth = Utils.toLocalTimeZone(LocalDateTime.now());
    private LocalDateTime selectedWeek = Utils.toLocalTimeZone(LocalDateTime.now());

    @FXML private Button homeButton;
    @FXML private Button editAppointmentButton;
    @FXML private Button addAppointmentButton;
    @FXML private Button deleteAppointmentButton;
    @FXML private Button calendarCenterButton;
    @FXML private Button backButton;
    @FXML private Button nextButton;
    @FXML private ToggleButton monthToggleButton;
    @FXML private ToggleButton weekToggleButton;
    @FXML private TableView<Appointment> calendarTableView;
    @FXML private TableColumn<Appointment, String> descCol;
    @FXML private TableColumn<Appointment, String> fromCol;
    @FXML private TableColumn<Appointment, String> toCol;
    @FXML private TableColumn<Appointment, String> customerCol;

    // Rubric C: Ability to add appointments. Will redirect to AddAppointmentView.
    private void add() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` adding an appointment", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.ADD_APPOINTMENT_VIEW_PATH));
            Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            AddAppointmentViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void back() {

        if(weekToggleButton.isDisable()) {

            selectedWeek = selectedWeek.minusWeeks(1);
            this.getAppointmentsForWeek();

        } else {

            selectedMonth = selectedMonth.minusMonths(1);
            this.getAppointmentsForMonth();

        }

    }

    // Rubric F: Do not enable edit or delete buttons if selection hasn't been made.
    private void bindButtonsToTable() {

        editAppointmentButton.disableProperty().bind(
            Bindings.isEmpty(
                calendarTableView.getSelectionModel().getSelectedItems()
            )
        );

        deleteAppointmentButton.disableProperty().bind(
            Bindings.isEmpty(
                calendarTableView.getSelectionModel().getSelectedItems()
            )
        );

    }

    // Rubric C: Ability to delete appointments. Will delete selected appointment from database.
    private void delete() {

        Utils.LOGGER.log(Level.INFO, "User: `{0}` deleted an appointment", this.user.getUserName());

        Appointment selectedAppointment = calendarTableView.getSelectionModel().getSelectedItem();
        calendarTableView.getItems().remove(selectedAppointment);

        Utils.DATABASE.deleteAppointment(selectedAppointment);

    }

    // Rubric C: Ability to update Appointment. Will take selected appointment
    // and redirect to EditAppointmentView where it can be updated.
    private void edit() {

        Appointment selectedApp = calendarTableView.getSelectionModel().getSelectedItem();

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` is editing an appointment", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.EDIT_APPOINTMENT_VIEW_PATH));
            Stage stage = (Stage) editAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            EditAppointmentViewController controller = loader.getController();
            controller.initData(this.user, selectedApp);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void home() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating to back to home", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.HOME_VIEW_PATH));
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void next() {

        if(weekToggleButton.isDisable()) {
            
            selectedWeek = selectedWeek.plusWeeks(1);
            this.getAppointmentsForWeek();

        } else {

            selectedMonth = selectedMonth.plusMonths(1);
            this.getAppointmentsForMonth();

        }

    }

    private void getAppointmentsForMonth() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM, yyyy");
        calendarCenterButton.setText(selectedMonth.format(format));

        LocalDateTime firstDate = selectedMonth.withDayOfMonth(1)
                                               .withHour(0)
                                               .withMinute(0)
                                               .withSecond(0);

        LocalDateTime lastDate = selectedMonth.withDayOfMonth(selectedMonth.getMonth().minLength())
                                              .withHour(23)
                                              .withMinute(59)
                                              .withSecond(59);

        calendarTableView.setItems(
            Utils.DATABASE.getAppointmentsInRange(firstDate, lastDate)
        );

    }

    private void getAppointmentsForWeek() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String label = selectedWeek.format(format) + " - " + selectedWeek.plusWeeks(1).format(format);
        calendarCenterButton.setText(label);

        calendarTableView.setItems(
            Utils.DATABASE.getAppointmentsInRange(selectedWeek, selectedWeek.plusWeeks(1))
        );

    }

    private void setupCalendar() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, hh:mma");

        // Rubric G - Lambda: appointment.start is a LocalDateTime
        // and looks kind of ugly when being displayed in a tableview.
        // I chose to use a lambda here to convert that LocalDateTime 
        // on the fly into a formatted string.
        fromCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getStart().format(formatter))
        );
        toCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getEnd().format(formatter))
        );
        descCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getDescription())
        );
        customerCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomer().getCustomerName())
        );
        calendarTableView.setPlaceholder(new Label(""));

        this.toggleMonth();

    }

    // Rubric D: Calendar can be viewed on a per month basis.
    private void toggleMonth() {

        weekToggleButton.setDisable(false);
        monthToggleButton.setDisable(true);
        monthToggleButton.setSelected(true);

        this.getAppointmentsForMonth();

    }

    // Rubric D: Calendar can be viewed on a per week basis.
    private void toogleWeek() {

        monthToggleButton.setDisable(false);
        weekToggleButton.setDisable(true);
        weekToggleButton.setSelected(true);

        this.getAppointmentsForWeek();

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.bindButtonsToTable();
        this.setupCalendar();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        addAppointmentButton.setOnAction(e -> this.add());
        backButton.setOnAction(e -> this.back());
        deleteAppointmentButton.setOnAction(e -> this.delete());
        editAppointmentButton.setOnAction(e -> this.edit());
        homeButton.setOnAction(e -> this.home());
        monthToggleButton.setOnAction(e -> this.toggleMonth());
        nextButton.setOnAction(e -> this.next());
        weekToggleButton.setOnAction(e -> this.toogleWeek());

    }

}
