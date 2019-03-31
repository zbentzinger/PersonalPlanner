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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.DAO.InvalidUserException;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

// Rubric A: Login form; internationalization is handled through ResourceBundles and FXML.
// Rubric F4: Login form handles exception by showing an error label.
public class LoginViewController implements Initializable {

    private User user;

    @FXML private Button exitButton;
    @FXML private Button loginButton;
    @FXML private Label invalidLabel;
    @FXML private TextField userNameField;
    @FXML private TextField passField;

    private void exit() {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close(); 

    }

    // Rubric H: Throw an alert window if an appointment is within 15 minutes of the user logging in.
    private void checkAppointment() {

        if (Utils.DATABASE.isAppointmentSoon(this.user)){

            Alert appointmentSoon = new Alert(Alert.AlertType.INFORMATION);
            appointmentSoon.setTitle("Upcoming Appointment");
            appointmentSoon.setHeaderText("You have an appointment soon");
            appointmentSoon.show();

        }

    }

    private void login() {

        try {

            this.user = Utils.DATABASE.getUser(
                userNameField.getText(),
                passField.getText()
            );

            // Rubric H: show alert if appointment starting within 15 minutes.
            this.checkAppointment();

            // Rubric J: Add log file entry on success log in attempt
            Utils.LOGGER.log(Level.INFO, "User: `{0}` Logged in", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.HOME_VIEW_PATH));
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        // Rubric F4: Entering incorrect user credentials - show invalid label.
        } catch (InvalidUserException e) {
        
            // Rubric J: Add log file entry on invalid login attempt
            Utils.LOGGER.log(Level.INFO, "Login attempt with invalid credentials.");

            invalidLabel.setText(e.getLocalizedMessage());
            invalidLabel.setVisible(true);
        
        }

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setVisible(false);

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        exitButton.setOnAction(e -> this.exit());
        loginButton.setOnAction(e -> this.login());

    }

}
