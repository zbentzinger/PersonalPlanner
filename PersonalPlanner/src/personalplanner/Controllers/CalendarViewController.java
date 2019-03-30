package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.Appointment;
import personalplanner.Models.User;

public class CalendarViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger("PersonalPlanner");

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";
    private String editAppViewURL = "/personalplanner/Views/EditAppointmentView.fxml";
    private String addAppViewURL = "/personalplanner/Views/AddAppointmentView.fxml";
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");
    private LocalDateTime selectedMonth = LocalDateTime.now();
    private LocalDateTime selectedWeek = LocalDateTime.now();

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource(addAppViewURL));
            Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            AddAppointmentViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void back() {

        if(weekToggleButton.isDisable()) {

            selectedWeek = selectedWeek.minusWeeks(1);
            populateByWeek();

        } else {

            selectedMonth = selectedMonth.minusMonths(1);
            populateByMonth();

        }

    }

    // Rubric F: Do not enable edit or delete buttons if selection hasn't been made.
    private void bindButtons() {

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

        this.database.deleteAppointment(selectedAppointment);

    }

    // Rubric C: Ability to update Appointment. Will take selected appointment
    // and redirect to EditAppointmentView where it can be updated.
    private void edit() {

        Appointment selectedApp = calendarTableView.getSelectionModel().getSelectedItem();

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(editAppViewURL));
            Stage stage = (Stage) editAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            EditAppointmentViewController controller = loader.getController();
            controller.initData(this.user, selectedApp);
            stage.show();

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void home() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(homeViewURL));
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void next() {

        if(weekToggleButton.isDisable()) {
            
            selectedWeek = selectedWeek.plusWeeks(1);
            populateByWeek();

        } else {

            selectedMonth = selectedMonth.plusMonths(1);
            populateByMonth();

        }

    }

    private void populateByMonth() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM, yyyy");
        calendarCenterButton.setText(selectedMonth.format(format));
        calendarTableView.setItems(this.database.getAppointmentsByMonth(selectedMonth));

    }

    private void populateByWeek() {

        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        String label = selectedWeek.format(format) + " - " + selectedWeek.plusWeeks(1).format(format);
        calendarCenterButton.setText(label);

        calendarTableView.setItems(this.database.getAppointmentsByWeek(selectedWeek));

    }

    private void toggleMonth() {

        weekToggleButton.setDisable(false);
        monthToggleButton.setDisable(true);
        monthToggleButton.setSelected(true);
        
        populateByMonth();

    }

    private void toogleWeek() {

        monthToggleButton.setDisable(false);
        weekToggleButton.setDisable(true);
        weekToggleButton.setSelected(true);

        populateByWeek();

    }

    public void initData(User user) {

        this.user = user;

    }

    // Rubric D: Ability to view calendar by month and week.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        bindButtons();

        appDesc.setCellValueFactory(
            new PropertyValueFactory<>("description")
        );

        // Rubric G - Lambda: appointment.start is a LocalDateTime
        // and looks kind of ugly when being displayed in a tableview.
        // I chose to use a lambda here to convert that LocalDateTime 
        // on the fly into a formatted string.
        appDate.setCellValueFactory(appointment -> new SimpleStringProperty(appointment.getValue().getStart().format(formatter)));

        calendarTableView.setPlaceholder(new Label(""));

        toggleMonth();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        addAppointmentButton.setOnAction(e -> add());
        backButton.setOnAction(e -> back());
        deleteAppointmentButton.setOnAction(e -> delete());
        editAppointmentButton.setOnAction(e -> edit());
        homeButton.setOnAction(e -> home());
        monthToggleButton.setOnAction(e -> toggleMonth());
        nextButton.setOnAction(e -> next());
        weekToggleButton.setOnAction(e -> toogleWeek());

    }

}
