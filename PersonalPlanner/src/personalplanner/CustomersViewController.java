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
import javafx.stage.Stage;

public class CustomersViewController implements Initializable {

    @FXML private Button editCustomerButton;
    @FXML private Button addCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button customersHomeButton;

    @FXML private TableColumn<?, ?> customerNameCol;
    @FXML private TableColumn<?, ?> customerAddressCol;
    @FXML private TableView<?> customersTableView;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void editCustomerButton(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("EditCustomerView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void addCustomerButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("AddCustomerView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void deleteCustomerButtonClicked(ActionEvent event) {
    }

    @FXML private void customersHomeButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }
    
}
