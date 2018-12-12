package personalplanner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainViewController implements Initializable {

    @FXML private Button logoutButton;

    @FXML private ToggleButton calendarButton;
    @FXML private ToggleButton customersButton;
    @FXML private ToggleButton reportsButton;    
    @FXML final ToggleGroup toggleButtons = new ToggleGroup();
    
    @FXML private AnchorPane contentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb)  {
        
        calendarButton.setToggleGroup(toggleButtons);
        customersButton.setToggleGroup(toggleButtons);
        reportsButton.setToggleGroup(toggleButtons);

        calendarButton.setSelected(true);

        loadExternalFxml("CalendarView.fxml");
        
    }    

    @FXML
    private void logoutButtonClicked(ActionEvent event) throws IOException {
    
        Parent addPartView = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();
        
    }

    @FXML
    private void calendarButtonClicked(ActionEvent event) {
        
        loadExternalFxml("CalendarView.fxml");
        
    }

    @FXML
    private void customersButtonClicked(ActionEvent event) {
        
        loadExternalFxml("CustomersView.fxml");
        
    }

    @FXML
    private void reportsButtonClicked(ActionEvent event) {

        loadExternalFxml("ReportsView.fxml");

    }
    
    private void loadExternalFxml(String fileName) {
        // Make sure that you only load FXML files that have an AnchorPane root node.

        try {

            contentPane.getChildren().clear();
            
            AnchorPane subPane = FXMLLoader.load(getClass().getResource(fileName));
            contentPane.getChildren().add(subPane);

        } catch (IOException ex) {

            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
    }
    
}
