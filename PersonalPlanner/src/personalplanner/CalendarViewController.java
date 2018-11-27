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

public class CalendarViewController implements Initializable {


    @FXML private ToggleButton monthButton;
    @FXML private ToggleButton weekButton;
    @FXML private ToggleButton newAppButton;
    final ToggleGroup calendarToggleButtons = new ToggleGroup();
    @FXML
    private AnchorPane subContentPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        monthButton.setToggleGroup(calendarToggleButtons);
        weekButton.setToggleGroup(calendarToggleButtons);
        newAppButton.setToggleGroup(calendarToggleButtons);

        monthButton.setSelected(true);

        AnchorPane subPane;
        try {

            subPane = FXMLLoader.load(getClass().getResource("MonthSubView.fxml"));
            subContentPane.getChildren().add(subPane);


        } catch (IOException ex) {

            Logger.getLogger(MainViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

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

    private void unloadContentView() {

        try {

            // Unload the previous view from AnchorPane if necessary.
            subContentPane.getChildren().remove(0);

        } catch (IndexOutOfBoundsException e) {
        }

    }
    
}
