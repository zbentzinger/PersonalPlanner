package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;

public class MainViewController implements Initializable {

    @FXML private Button logoutButton;
    @FXML private ToggleButton calendarButton;
    @FXML private ToggleButton customersButton;
    @FXML private ToggleButton reportsButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void logoutButtonClicked(ActionEvent event) {
    }

    @FXML
    private void calendarButtonClicked(ActionEvent event) {
    }

    @FXML
    private void customersButtonClicked(ActionEvent event) {
    }

    @FXML
    private void reportsButtonClicked(ActionEvent event) {
    }
    
}
