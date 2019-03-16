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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddCustomerViewController implements Initializable {

    @FXML private Button newCustSaveButton;
    @FXML private Button newCustCancelButton;

    @FXML private TextField nameTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countrytTextField;
    @FXML private TextField addressTextField;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void newCustSaveButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("/personalplanner/Views/CustomersView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void newCustCancelButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("/personalplanner/Views/CustomersView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }
    
}
