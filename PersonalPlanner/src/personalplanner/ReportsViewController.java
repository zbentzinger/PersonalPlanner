package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class ReportsViewController implements Initializable {

    @FXML private ToggleButton newReportButton;
    
    @FXML final ToggleGroup reportsToggleButtons = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        newReportButton.setToggleGroup(reportsToggleButtons);
        
        newReportButton.setSelected(true);
        
    }    

    @FXML
    private void newReportButtonClicked(ActionEvent event) {
    }
    
}
