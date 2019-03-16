package personalplanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import personalplanner.Utils.Database;

public class PersonalPlanner extends Application {

    @Override public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("/personalplanner/Views/LoginView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setMinWidth(root.minWidth(-1));
        stage.setMinHeight(root.minHeight(-1));
        
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        Database.Connect();

        launch(args);

        Database.Close();

    }

}
