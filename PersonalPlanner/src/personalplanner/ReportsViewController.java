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
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;

public class ReportsViewController implements Initializable {

    @FXML private ToggleButton newReportButton;
    
    @FXML final ToggleGroup reportsToggleButtons = new ToggleGroup();

    @FXML private AnchorPane subContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        newReportButton.setToggleGroup(reportsToggleButtons);
        
        newReportButton.setSelected(true);
        
    }    

    @FXML
    private void newReportButtonClicked(ActionEvent event) {
    }
    
    private void loadExternalFxml(String fileName) {
        // Make sure that you only load FXML files that have an AnchorPane root node.

        try {

            subContentPane.getChildren().clear();
            
            AnchorPane subPane = FXMLLoader.load(getClass().getResource(fileName));
            subContentPane.getChildren().add(subPane);

        } catch (IOException ex) {

            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);

        }
        
    }
    
}
