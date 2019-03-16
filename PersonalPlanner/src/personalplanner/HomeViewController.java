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
import javafx.stage.Stage;

public class HomeViewController implements Initializable {

    @FXML private Button logoutButton;
    @FXML private Button calendarButton;
    @FXML private Button reportsButton;
    @FXML private Button customersButton;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void logoutButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void calendarButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void reportsButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("ReportsView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML private void customersButtonClicked(ActionEvent event) throws IOException {
        
        Parent addPartView = FXMLLoader.load(getClass().getResource("CustomersView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }
    
}
