package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class CustomersViewController implements Initializable {

    @FXML private ToggleButton customerButton;
    @FXML private ToggleButton newCustButton;
    @FXML private ToggleButton editCustButton;
    final ToggleGroup custToggleButtons = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerButton.setToggleGroup(custToggleButtons);
        newCustButton.setToggleGroup(custToggleButtons);
        editCustButton.setToggleGroup(custToggleButtons);

        customerButton.setSelected(true);
        editCustButton.setDisable(true);
        
    }    

    @FXML
    private void customerButtonClicked(ActionEvent event) {
    }

    @FXML
    private void newCustButtonClicked(ActionEvent event) {
    }

    @FXML
    private void editCustButtonClicked(ActionEvent event) {
    }
    
}
