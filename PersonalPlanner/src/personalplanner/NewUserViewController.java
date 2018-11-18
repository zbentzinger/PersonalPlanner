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

public class NewUserViewController implements Initializable {

    @FXML private TextField userNameField;
    @FXML private TextField passField;
    @FXML private TextField confirmPassField;
    @FXML private Button createButton;
    @FXML private Button backButton;
    @FXML private Label invalidLabel;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        invalidLabel.setText("");
        
    }    

    @FXML
    private void createButtonClicked(ActionEvent event) throws IOException {
        
        loadLoginView(event);
        
    }

    @FXML
    private void backButtonClicked(ActionEvent event) throws IOException {
        
        loadLoginView(event);
        
    }
    
    private void loadLoginView(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }
    
}
