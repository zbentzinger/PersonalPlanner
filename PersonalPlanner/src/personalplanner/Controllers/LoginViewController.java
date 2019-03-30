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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.DAO.InvalidUserException;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.User;

public class LoginViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

    @FXML private Button exitButton;
    @FXML private Button loginButton;
    @FXML private Label invalidLabel;
    @FXML private TextField userNameField;
    @FXML private TextField passField;

    private void exit() {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close(); 

    }

    // Rubric H: Throw an alert if an appointment is within 15 minutes of the user logging in.
    private void checkAppointment() {


        Alert appointmentSoon = new Alert(Alert.AlertType.INFORMATION);
        appointmentSoon.setTitle("Upcoming Appointment");
        appointmentSoon.setHeaderText("You have an appointment soon");

        if (this.database.isAppointmentSoon(user))
        {

            appointmentSoon.show();

        }

    }

    private void login() {

        try {

            this.user = database.getUser(
                userNameField.getText(),
                passField.getText()
            );

            // Rubric H: show alert if appointment starting within 15 minutes.
            this.checkAppointment();

            FXMLLoader loader = new FXMLLoader(getClass().getResource(homeViewURL));

            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            HomeViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);

        // Rubric F4: Entering incorrect user credentials - show invalid label.
        } catch (InvalidUserException e) {
        
            invalidLabel.setText(e.getLocalizedMessage());
            invalidLabel.setVisible(true);
        
        }

    }

    // Rubric A and F: Login form; internationalization is handled through ResourceBundles, FXML and custom Exception.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        invalidLabel.setVisible(false);

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        exitButton.setOnAction(e -> exit());
        loginButton.setOnAction(e -> login());

    }

}
