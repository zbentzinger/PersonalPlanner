package personalplanner;

import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PersonalPlanner extends Application {

    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";

    public static void main(String[] args) {

        launch(args);

    }

    @Override public void start(Stage stage) throws Exception {

        // Rubric A; ResourceBundle is passed to FXML for internationalization.
        // Currently supports en_US and fr_FR.
        Parent loginView = FXMLLoader.load(
            getClass().getResource(loginViewURL),
            ResourceBundle.getBundle("personalplanner.Utils.LoginView")
        );

        stage.setScene(new Scene(loginView));
        stage.show();

    }

}
