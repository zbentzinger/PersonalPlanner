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

public class EditCustomerViewController implements Initializable {

    private String custViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button editCustSaveButton;
    @FXML private Button editCustCancelButton;

    @FXML private TextField nameTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countrytTextField;
    @FXML private TextField addressTextField;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void editCustSaveButtonClicked(ActionEvent event) throws IOException {        

        Parent customersView = FXMLLoader.load(getClass().getResource(custViewURL));

        Scene customersScene = new Scene(customersView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customersScene);
        window.show();        

    }

    @FXML private void editCustCancelButtonClicked(ActionEvent event) throws IOException {        

        Parent customersView = FXMLLoader.load(getClass().getResource(custViewURL));

        Scene customersScene = new Scene(customersView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customersScene);
        window.show();       

    }
    
}
