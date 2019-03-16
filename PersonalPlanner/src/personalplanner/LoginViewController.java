package personalplanner;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.Utils.Database;

public class LoginViewController implements Initializable {

    private int userID;

    @FXML private Button exitButton;
    @FXML private Button loginButton;

    @FXML private Label invalidLabel;

    @FXML private TextField userNameField;
    @FXML private TextField passField;

    @Override public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setText("");

    }

    @FXML private void loginButtonClicked(ActionEvent event) throws IOException {

        if(validateUser()) {

            Parent addPartView = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
            Scene addPartViewScene = new Scene(addPartView);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(addPartViewScene);
            window.show();

        } else {

            invalidLabel.setText("Invalid Credentials");

        }

    }

    @FXML private void exitButtonClicked(ActionEvent event) {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    private Boolean validateUser() {

        Boolean userValid = false;
        String query = String.format(
                "SELECT userId FROM user WHERE userName = '%s' AND password = '%s'",
                userNameField.getText(),
                passField.getText()
        );

        try {
            ResultSet result = Database.Select(query);
            while(result.next()) {
                if(result.getInt(1) >= 1) {
                    // User is found, set the user variable with their userId.
                    userValid = true;
                    this.userID = result.getInt(1);

                }

            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return userValid;
    }

}
