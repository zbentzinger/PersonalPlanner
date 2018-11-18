package personalplanner;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;

public class CalendarViewController implements Initializable {

    
    @FXML private ToggleButton monthButton;
    @FXML private ToggleButton weekButton;
    @FXML private ToggleButton newAppButton;
    @FXML final ToggleGroup calendarToggleButtons = new ToggleGroup();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        monthButton.setToggleGroup(calendarToggleButtons);
        weekButton.setToggleGroup(calendarToggleButtons);
        newAppButton.setToggleGroup(calendarToggleButtons);

        monthButton.setSelected(true);

    }    


    @FXML
    private void monthButtonClicked(ActionEvent event) {
    }

    @FXML
    private void weekButtonClicked(ActionEvent event) {
    }

    @FXML
    private void newAppButtonClicked(ActionEvent event) {
    }

    
}
