package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import personalplanner.Models.User;

public class HomeViewController implements Initializable {

    private User user;
    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";
    private String reportsViewURL = "/personalplanner/Views/ReportsView.fxml";
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button logoutButton;
    @FXML private Button calendarButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;

    private void calendar() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(calendarViewURL));

            Stage stage = (Stage) calendarButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            CalendarViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void customers() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(customersViewURL));

            Stage stage = (Stage) customersButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            CustomersViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void logout() {

        try {

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(loginViewURL),
                ResourceBundle.getBundle("personalplanner.Utils.LoginView")
            );

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void reports() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(reportsViewURL));

            Stage stage = (Stage) reportsButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            ReportsViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(HomeViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        calendarButton.setOnAction(e -> calendar());
        customersButton.setOnAction(e -> customers());
        logoutButton.setOnAction(e -> logout());
        reportsButton.setOnAction(e -> reports());

    }

}
