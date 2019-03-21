package personalplanner;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PersonalPlanner extends Application {

    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";

    @Override public void start(Stage stage) throws Exception {

        ResourceBundle resources = ResourceBundle.getBundle("personalplanner.Utils.LoginView");

        Parent loginView = FXMLLoader.load(
                getClass().getResource(loginViewURL),
                resources
        );

        Scene loginScene = new Scene(loginView);

        stage.setScene(loginScene);
        stage.show();

    }

    public static void main(String[] args) {

        launch(args);

    }

}
