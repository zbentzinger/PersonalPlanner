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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
import personalplanner.Models.User;

public class ReportsViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

    @FXML private Button appointmentTypesReport;
    @FXML private Button customerAddressesReport;
    @FXML private Button homeButton;
    @FXML private Button consultantScheduleReport;
    @FXML private TableView<?> resultsTableView;
    @FXML private TableColumn<?, ?> resultsCol;

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

    }    

    @FXML private void appointmentTypesReportClicked(ActionEvent event) {
    }

    @FXML private void customerAddressesReportClicked(ActionEvent event) {
    }

    @FXML private void homeButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(homeViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        HomeViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void consultantScheduleReportClicked(ActionEvent event) {
    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        resultsTableView.setPlaceholder(new Label(""));

    }

}
