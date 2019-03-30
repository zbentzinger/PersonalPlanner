package personalplanner;

import java.io.File;
import java.io.IOException;
import java.util.ResourceBundle;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PersonalPlanner extends Application {

    private String loginViewURL = "/personalplanner/Views/LoginView.fxml";
    private static String LOGFILE = "src/personalplanner/Logs/log.txt";
    private static final Logger LOGGER = Logger.getLogger("PersonalPlanner");

    public static void main(String[] args) throws IOException {        

        // Rubric J: Log to file. File is located in: src/personalplanner/Logs/log.txt
        // The default log formatter already adds a datetime,
        // so there was no need to do anything special 
        // other than direct the default logging library to a file.
        File logFile = new File(LOGFILE);
        logFile.createNewFile(); // will create the file if it doesn't exist.
        FileHandler handler = new FileHandler(LOGFILE, true);
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        LOGGER.addHandler(handler);
        LOGGER.setLevel(Level.ALL);

        LOGGER.log(Level.INFO, "Launching");

        launch(args);

        LOGGER.log(Level.INFO, "Exiting");

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
