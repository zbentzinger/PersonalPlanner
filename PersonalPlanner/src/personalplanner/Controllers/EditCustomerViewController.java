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
import personalplanner.Models.User;

public class EditCustomerViewController implements Initializable {

    private User user;
    private String custViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button editCustSaveButton;
    @FXML private Button editCustCancelButton;
    @FXML private TextField nameTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countryTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;

    public void initData(User user) {

        this.user = user;

    }

    @FXML private void editCustSaveButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(custViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void editCustCancelButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(custViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {
    }
    
}
