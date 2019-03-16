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
import javafx.stage.Stage;

public class ReportsViewController implements Initializable {

    @FXML private Button appointmentTypesReport;
    @FXML private Button customerAddressesReport;
    @FXML private Button homeButton;
    @FXML private Button consultantScheduleReport;

    @FXML private TableColumn<?, ?> resultsCol;
    @FXML private TableView<?> resultsTableView;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void appointmentTypesReportClicked(ActionEvent event) {
    }

    @FXML private void customerAddressesReportClicked(ActionEvent event) {
    }

    @FXML private void homeButtonClicked(ActionEvent event) throws IOException {

        Parent addPartView = FXMLLoader.load(getClass().getResource("/personalplanner/Views/HomeView.fxml"));
        Scene addPartViewScene = new Scene(addPartView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(addPartViewScene);
        window.show();

    }

    @FXML private void consultantScheduleReportClicked(ActionEvent event) {
    }

}
