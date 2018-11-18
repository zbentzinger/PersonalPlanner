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
        
        AnchorPane subPane;
        try {

            subPane = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
            contentPane.getChildren().add(subPane);
            

        } catch (IOException ex) {

            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
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
    private void calendarButtonClicked(ActionEvent event) throws IOException {
        
        unloadContentView();
        
        AnchorPane subPane = FXMLLoader.load(getClass().getResource("CalendarView.fxml"));
        contentPane.getChildren().add(subPane);
        
    }

    @FXML
    private void customersButtonClicked(ActionEvent event) throws IOException {
        
        unloadContentView();
        
        AnchorPane subPane = FXMLLoader.load(getClass().getResource("CustomersView.fxml"));
        contentPane.getChildren().add(subPane);
        
    }

    @FXML
    private void reportsButtonClicked(ActionEvent event) throws IOException {
        
        unloadContentView();
        
        AnchorPane subPane = FXMLLoader.load(getClass().getResource("ReportsView.fxml"));
        contentPane.getChildren().add(subPane);
        
    }
    
    private void unloadContentView() {

        try {
            
            // Unload the previous view from AnchorPane if necessary.
            contentPane.getChildren().remove(0);

        } catch (IndexOutOfBoundsException e) {
        }

    }
    
}
