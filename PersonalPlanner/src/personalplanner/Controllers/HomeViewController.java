package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

public class HomeViewController implements Initializable {

    private User user;

    @FXML private Button logoutButton;
    @FXML private Button calendarButton;
    @FXML private Button customersButton;
    @FXML private Button reportsButton;

    private void calendar() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating to the calendar", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CALENDAR_VIEW_PATH));
            Stage stage = (Stage) calendarButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CalendarViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void customers() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating to customers", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CUSTOMERS_VIEW_PATH));
            Stage stage = (Stage) customersButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CustomersViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void logout() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` logging out", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(
                getClass().getResource(Utils.LOGIN_VIEW_PATH),
                ResourceBundle.getBundle(Utils.RESOURCE_BUNDLE_PATH)
            );

            Stage stage = (Stage) logoutButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void reports() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating to reports", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.REPORTS_VIEW_PATH));
            Stage stage = (Stage) reportsButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            ReportsViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        calendarButton.setOnAction(e -> this.calendar());
        customersButton.setOnAction(e -> this.customers());
        logoutButton.setOnAction(e -> this.logout());
        reportsButton.setOnAction(e -> this.reports());

    }

}
