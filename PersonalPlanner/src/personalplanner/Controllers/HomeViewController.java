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

public class HomeViewController implements Initializable {

    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";
    private String reportsViewURL = "/personalplanner/Views/ReportsView.fxml";
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button logoutButton;
    @FXML private Button calendarButton;
    @FXML private Button reportsButton;
    @FXML private Button customersButton;


    @Override public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML private void logoutButtonClicked(ActionEvent event) throws IOException {

        ResourceBundle resources = ResourceBundle.getBundle("personalplanner.Resources.LoginView");

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

        Parent calendarView = FXMLLoader.load(getClass().getResource(calendarViewURL));

        Scene calendarScene = new Scene(calendarView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(calendarScene);
        window.show();        

    }

    @FXML private void reportsButtonClicked(ActionEvent event) throws IOException {        

        Parent reportsView = FXMLLoader.load(getClass().getResource(reportsViewURL));

        Scene reportsScene = new Scene(reportsView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(reportsScene);
        window.show();        

    }

    @FXML private void customersButtonClicked(ActionEvent event) throws IOException {        

        Parent customersView = FXMLLoader.load(getClass().getResource(customersViewURL));

        Scene customersScene = new Scene(customersView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customersScene);
        window.show();        

    }

}
