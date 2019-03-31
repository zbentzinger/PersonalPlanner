package personalplanner;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import personalplanner.Utils.Utils;

public class PersonalPlanner extends Application {

    public static void main(String[] args) throws IOException {        

        // Rubric J: Log to file. File is located in: src/personalplanner/Logs/log.txt
        // The default log formatter already adds a datetime,
        // so there was no need to do anything special 
        // other than direct the default logging library to a file.
        File logFile = new File(Utils.LOG_FILE_PATH);
        logFile.createNewFile(); // will create the file if it doesn't exist.
        FileHandler handler = new FileHandler(Utils.LOG_FILE_PATH, true);
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        Utils.LOGGER.addHandler(handler);
        Utils.LOGGER.setLevel(Level.ALL);

        Utils.LOGGER.log(Level.INFO, "Launching");

        launch(args);

        Utils.LOGGER.log(Level.INFO, "Exiting");

    }

    @Override public void start(Stage stage) throws Exception {

        // Rubric A; ResourceBundle is passed to FXML for internationalization.
        // Currently supports en_US and fr_FR.
        Parent loginView = FXMLLoader.load(
            getClass().getResource(Utils.LOGIN_VIEW_PATH),
            ResourceBundle.getBundle(Utils.RESOURCE_BUNDLE_PATH)
        );
        stage.setScene(new Scene(loginView));
        stage.show();

    }

}
