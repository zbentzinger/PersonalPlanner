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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginViewController implements Initializable {

    @FXML private TextField userNameField;
    @FXML private TextField passField;
    @FXML private Label invalidLabel;
    @FXML private Button exitButton;
    @FXML private Button loginNewUserButton;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setText("");

    }    

    @FXML
    private void loginButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML
    private void exitButtonClicked(ActionEvent event) {
        
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void loginNewUserButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("NewUserView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }
    
}
