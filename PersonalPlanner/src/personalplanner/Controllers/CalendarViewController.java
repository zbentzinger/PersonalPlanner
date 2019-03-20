package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import personalplanner.Models.User;

public class CalendarViewController implements Initializable {

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
    @FXML private TableColumn<?, ?> appDesc;
    @FXML private TableColumn<?, ?> appDate;
    @FXML private TableView<?> calendarTableView;
    @FXML private ToggleButton monthToggleButton;
    @FXML private ToggleButton weekToggleButton;
    @FXML private ToggleGroup calendarToggleButtons = new ToggleGroup();

    public void initData(User user) {

        this.user = user;

    }

    @FXML private void homeButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(homeViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        HomeViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void editAppointmentButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(editAppViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        EditAppointmentViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void addAppointmentButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(addAppViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        AddAppointmentViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void deleteAppointmentButtonClicked(ActionEvent event) {
    }

    @FXML private void backButtonClicked(ActionEvent event) {
    }

    @FXML private void nextButtonClicked(ActionEvent event) {
    }

    @FXML private void monthToggleButtonClicked(ActionEvent event) {
    }

    @FXML private void weekToggleButtonClicked(ActionEvent event) {
    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        calendarTableView.setPlaceholder(new Label(""));

        monthToggleButton.setToggleGroup(calendarToggleButtons);
        weekToggleButton.setToggleGroup(calendarToggleButtons);
        monthToggleButton.setSelected(true);
        
    }
    
}
