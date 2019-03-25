package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import personalplanner.DAO.MainDAO;
import personalplanner.Models.Appointment;
import personalplanner.Models.User;

public class CalendarViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";
    private String editAppViewURL = "/personalplanner/Views/EditAppointmentView.fxml";
    private String addAppViewURL = "/personalplanner/Views/AddAppointmentView.fxml";

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

    private void add() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(addAppViewURL));

            Stage stage = (Stage) addAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            AddAppointmentViewController controller = loader.getController();
            controller.initData(this.user, this.database);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void back() {
    }

    private void delete() {
    }

    private void edit() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(editAppViewURL));

            Stage stage = (Stage) editAppointmentButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            EditAppointmentViewController controller = loader.getController();
            controller.initData(this.user, this.database);

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
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CalendarViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void monthToggle() {

        // Prevent unselecting of a toggle button.
        weekToggleButton.setDisable(false);
        monthToggleButton.setDisable(true);

    }

    private void next() {
    }

    private void weekToggle() {

        // Prevent unselecting of a toggle button.
        monthToggleButton.setDisable(false);
        weekToggleButton.setDisable(true);

    }

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        addAppointmentButton.setOnAction(e -> add());
        backButton.setOnAction(e -> back());
        deleteAppointmentButton.setOnAction(e -> delete());
        editAppointmentButton.setOnAction(e -> edit());
        homeButton.setOnAction(e -> home());
        monthToggleButton.setOnAction(e -> monthToggle());
        nextButton.setOnAction(e -> next());
        weekToggleButton.setOnAction(e -> weekToggle());

        monthToggleButton.setSelected(true);
        calendarTableView.setPlaceholder(new Label(""));

    }

}
