package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

    private void login(ActionEvent event) {

        this.user = database.getUser(
            userNameField.getText(),
            passField.getText()
        );

        if(this.user.getUserID() > 0) {

            // Load the next scene.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(homeViewURL));
            Parent view = null;
            try {
                view = loader.load();
            } catch (IOException ex) {
                Logger.getLogger(LoginViewController.class.getName()).log(Level.SEVERE, null, ex);
            }
            Scene scene = new Scene(view);
            HomeViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } else {

            invalidLabel.setVisible(true);

        }

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        invalidLabel.setVisible(false);

        exitButton.setOnAction(event -> exit());
        loginButton.setOnAction(event -> login(event));


    }

}
