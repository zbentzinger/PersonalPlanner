package personalplanner;

import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import personalplanner.Utils.Database;

public class PersonalPlanner extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setMinWidth(root.minWidth(-1));
        stage.setMinHeight(root.minHeight(-1));
        
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {

        try {
            
            Database.Connect();
            launch(args);
            Database.Close();
            
        } catch (SQLException ex) {

            System.out.println("Exception: " + ex.getMessage());

        }

    }

}
