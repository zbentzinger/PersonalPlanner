package personalplanner;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

public class CalendarViewController implements Initializable {

    @FXML
    private Button homeButton;
    @FXML
    private Button editAppointmentButton;
    @FXML
    private Button addAppointmentButton;
    @FXML
    private Button deleteAppointmentButton;
    @FXML
    private Button calendarCenterButton;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;
    @FXML
    private ToggleButton monthToggleButton;
    @FXML
    private ToggleGroup calendarToggleButtons = new ToggleGroup();
    @FXML
    private ToggleButton weekToggleButton;
    @FXML
    private TableView<?> calendarTableView;
    @FXML
    private TableColumn<?, ?> appDesc;
    @FXML
    private TableColumn<?, ?> appDate;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        monthToggleButton.setToggleGroup(calendarToggleButtons);
        weekToggleButton.setToggleGroup(calendarToggleButtons);
        monthToggleButton.setSelected(true);
        
    }

    @FXML
    private void homeButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML
    private void editAppointmentButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("EditAppointmentView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML
    private void addAppointmentButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("AddAppointmentView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML
    private void deleteAppointmentButtonClicked(ActionEvent event) {
    }

    @FXML
    private void backButtonClicked(ActionEvent event) {
    }

    @FXML
    private void nextButtonClicked(ActionEvent event) {
    }

    @FXML
    private void monthToggleButtonClicked(ActionEvent event) {
    }

    @FXML
    private void weekToggleButtonClicked(ActionEvent event) {
    }

    
}
