package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class LoginViewController implements Initializable {

    @FXML private TextField userNameField;
    @FXML private TextField passField;
    @FXML private Button loginButton;
    @FXML private Button newUserButton;
    @FXML private Label invalidLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setText("");

    }    

    @FXML
    private void loginButtonClicked(ActionEvent event) {
    }

    @FXML
    private void newUserButtonClicked(ActionEvent event) {
    }
    
}
