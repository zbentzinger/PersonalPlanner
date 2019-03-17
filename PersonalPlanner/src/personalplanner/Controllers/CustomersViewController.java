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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class CustomersViewController implements Initializable {

    private String editCustViewURL = "/personalplanner/Views/EditCustomerView.fxml";
    private String addCustViewURL = "/personalplanner/Views/AddCustomerView.fxml";
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

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

        Parent editCustView = FXMLLoader.load(getClass().getResource(editCustViewURL));

        Scene editCustScene = new Scene(editCustView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(editCustScene);
        window.show();

    }

    @FXML private void addCustomerButtonClicked(ActionEvent event) throws IOException {

        Parent addPartView = FXMLLoader.load(getClass().getResource(addCustViewURL));

        Scene addPartViewScene = new Scene(addPartView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(addPartViewScene);
        window.show();

    }

    @FXML private void deleteCustomerButtonClicked(ActionEvent event) {
    }

    @FXML private void customersHomeButtonClicked(ActionEvent event) throws IOException {        

        Parent homeView = FXMLLoader.load(getClass().getResource(homeViewURL));

        Scene homeScene = new Scene(homeView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(homeScene);
        window.show();        

    }
    
}
