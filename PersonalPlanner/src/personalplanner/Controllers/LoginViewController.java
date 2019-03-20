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

    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

    @FXML private Button exitButton;
    @FXML private Button loginButton;
    @FXML private Label invalidLabel;
    @FXML private TextField userNameField;
    @FXML private TextField passField;

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

                    user = new User();
                    user.setUserID(result.getInt("userid"));
                    user.setUserName(result.getString("userName"));
                    user.setPassword(result.getString("password"));
                    user.setActive(result.getInt("active"));
                    user.setCreatedBy(result.getString("createBy"));
                    user.setCreatedAt(created);
                    user.setUpdatedBy(result.getString("lastUpdatedBy"));
                    user.setUpdatedAt(updated);

                }
            }
        }

        return authenticated;

    }

    @FXML private void loginButtonClicked(ActionEvent event) throws IOException, SQLException {

        if(authenticateUser()) {

            // Load the next scene.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(homeViewURL));
            Parent view = loader.load();
            Scene scene = new Scene(view);
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        } else {

            invalidLabel.setVisible(true);

        }

    }

    @FXML private void exitButtonClicked(ActionEvent event) {

        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        invalidLabel.setVisible(false);

    }

}
