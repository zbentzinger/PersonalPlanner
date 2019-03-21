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
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
import personalplanner.Models.User;

public class HomeViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";
    private String reportsViewURL = "/personalplanner/Views/ReportsView.fxml";
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button logoutButton;
    @FXML private Button calendarButton;
    @FXML private Button reportsButton;
    @FXML private Button customersButton;

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

    }

    @FXML private void logoutButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        ResourceBundle resources = ResourceBundle.getBundle("personalplanner.Utils.LoginView");
        Parent loginView = FXMLLoader.load(
                getClass().getResource(loginViewURL),
                resources
        );
        Scene loginScene = new Scene(loginView);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();        

    }

    @FXML private void calendarButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(calendarViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CalendarViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void reportsButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(reportsViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        ReportsViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void customersButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(customersViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {
    }

}
