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
    @FXML private TableColumn<Appointment, String> appDesc;
    @FXML private TableColumn<Appointment, String> appDate;
    @FXML private TableView<Appointment> calendarTableView;
    @FXML private ToggleButton monthToggleButton;
    @FXML private ToggleButton weekToggleButton;

    // Rubric C: Ability to add appointments. Will redirect to AddAppointmentView.
    private void add() {

        try {

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
            this.populateByWeek();

        } else {

            selectedMonth = selectedMonth.minusMonths(1);
            this.populateByMonth();

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

        Appointment selectedAppointment = calendarTableView.getSelectionModel().getSelectedItem();
        calendarTableView.getItems().remove(selectedAppointment);

        Utils.DATABASE.deleteAppointment(selectedAppointment);

    }

    // Rubric C: Ability to update Appointment. Will take selected appointment
    // and redirect to EditAppointmentView where it can be updated.
    private void edit() {

        Appointment selectedApp = calendarTableView.getSelectionModel().getSelectedItem();

        try {

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
            this.populateByWeek();

        } else {

            selectedMonth = selectedMonth.plusMonths(1);
            this.populateByMonth();

        }

    }

    private void populateByMonth() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM, yyyy");
        calendarCenterButton.setText(selectedMonth.format(format));

        calendarTableView.setItems(Utils.DATABASE.getAppointmentsByMonth(selectedMonth));

    }

    private void populateByWeek() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String label = selectedWeek.format(format) + " - " + selectedWeek.plusWeeks(1).format(format);
        calendarCenterButton.setText(label);

        calendarTableView.setItems(Utils.DATABASE.getAppointmentsByWeek(selectedWeek));

    }

    private void setupCalendar() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");

        // Rubric G - Lambda: appointment.start is a LocalDateTime
        // and looks kind of ugly when being displayed in a tableview.
        // I chose to use a lambda here to convert that LocalDateTime 
        // on the fly into a formatted string.
        appDate.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getStart().format(formatter))
        );

        appDesc.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getDescription())
        );

        calendarTableView.setPlaceholder(new Label(""));

    }

    // Rubric D: Calendar can be viewed on a per month basis.
    private void toggleMonth() {

        weekToggleButton.setDisable(false);
        monthToggleButton.setDisable(true);
        monthToggleButton.setSelected(true);

        this.populateByMonth();

    }

    // Rubric D: Calendar can be viewed on a per week basis.
    private void toogleWeek() {

        monthToggleButton.setDisable(false);
        weekToggleButton.setDisable(true);
        weekToggleButton.setSelected(true);

        this.populateByWeek();

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.bindButtonsToTable();
        this.setupCalendar();
        this.toggleMonth();

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
