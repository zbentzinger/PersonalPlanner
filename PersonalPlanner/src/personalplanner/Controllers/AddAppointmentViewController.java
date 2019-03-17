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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddAppointmentViewController implements Initializable {

    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";

    @FXML private Button newAppCancelButton;
    @FXML private Button newAppSaveButton;

    @FXML private TableView<?> selectCustTable;
    @FXML private TableColumn<?, ?> custNameCol;

    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField startTimeTextField;
    @FXML private TextField endTimeTextField;
    @FXML private TextField locationTextField;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void newAppCancelButtonClicked(ActionEvent event) throws IOException {

        Parent calendarView = FXMLLoader.load(getClass().getResource(calendarViewURL));

        Scene calendarScene = new Scene(calendarView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(calendarScene);
        window.show();

    }

    @FXML private void newAppSaveButtonClicked(ActionEvent event) throws IOException {

        Parent calendarView = FXMLLoader.load(getClass().getResource(calendarViewURL));

        Scene calendarScene = new Scene(calendarView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(calendarScene);
        window.show();

    }
    
}
