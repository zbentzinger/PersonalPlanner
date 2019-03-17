package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
import personalplanner.Models.User;
import personalplanner.Utils.Database;

public class LoginViewController implements Initializable {

    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";
    private User user;

    @FXML private Button exitButton;
    @FXML private Button loginButton;

    @FXML private Label invalidLabel;

    @FXML private TextField userNameField;
    @FXML private TextField passField;

    @Override public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setVisible(false);

    }

    @FXML private void loginButtonClicked(ActionEvent event) throws IOException, SQLException {

        if(authenticateUser()) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(homeViewURL));

            Parent homeView = loader.load();

            Scene homeScene = new Scene(homeView);

            HomeViewController controller = loader.getController();
            controller.setUser(this.user);

            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(homeScene);
            window.show();

        } else {

            invalidLabel.setVisible(true);

        }

    }

    @FXML private void exitButtonClicked(ActionEvent event) {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    private boolean authenticateUser() throws SQLException {
        // return true if user is authenticated and populate user object.

        boolean authenticated = false;
        String query = "SELECT * " 
                     + "FROM user " 
                     + "WHERE userName = ? AND password = ?";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) {

            pstmnt.setString(1, userNameField.getText());
            pstmnt.setString(2, passField.getText());

            try (ResultSet result = pstmnt.executeQuery()) {

                if(result.next()) {

                    authenticated = true;
                    LocalDateTime created = result.getTimestamp("createDate").toLocalDateTime();
                    LocalDateTime updated = result.getTimestamp("lastUpdate").toLocalDateTime();

                    user = new User(
                        result.getInt("userid"),
                        result.getString("userName"),
                        result.getString("password"),
                        result.getInt("active"),
                        result.getString("createBy"),
                        created,
                        result.getString("lastUpdatedBy"),
                        updated
                    );
                }
            }
        }

        return authenticated;

    }

}
