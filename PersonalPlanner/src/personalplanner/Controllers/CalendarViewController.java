package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
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

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";
    private String editAppViewURL = "/personalplanner/Views/EditAppointmentView.fxml";
    private String addAppViewURL = "/personalplanner/Views/AddAppointmentView.fxml";
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
    @FXML private TableColumn<Appointment, LocalDateTime> appDate;
    @FXML private TableView<Appointment> calendarTableView;
    @FXML private ToggleButton monthToggleButton;
    @FXML private ToggleButton weekToggleButton;

    private void add() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(addAppViewURL));

            Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            AddAppointmentViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void back() {

        // Since we disable the active view.
        if(weekToggleButton.isDisable()) {

            selectedWeek = selectedWeek.minusWeeks(1);
            populateByWeek();

        } else {

            selectedMonth = selectedMonth.minusMonths(1);
            populateByMonth();

        }

    }

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

    private void delete() {

        Appointment selectedAppointment = calendarTableView.getSelectionModel().getSelectedItem();
        calendarTableView.getItems().remove(selectedAppointment);

        this.database.deleteAppointment(selectedAppointment);

    }

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

            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);

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

            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void next() {

        // Since we disable the active view.
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

        // Prevent unselecting of a toggle button.
        weekToggleButton.setDisable(false);
        monthToggleButton.setDisable(true);
        monthToggleButton.setSelected(true);
        
        populateByMonth();

    }

    private void toogleWeek() {

        // Prevent unselecting of a toggle button.
        monthToggleButton.setDisable(false);
        weekToggleButton.setDisable(true);
        weekToggleButton.setSelected(true);

        populateByWeek();

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        bindButtons();

        appDesc.setCellValueFactory(
            new PropertyValueFactory<>("description")
        );
        appDate.setCellValueFactory(
            new PropertyValueFactory<>("start")
        );

        calendarTableView.setPlaceholder(new Label(""));

        toggleMonth();

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
