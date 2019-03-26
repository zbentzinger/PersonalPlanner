package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.Appointment;
import personalplanner.Models.User;

public class EditAppointmentViewController implements Initializable {

    private Appointment appointment; 
    private MainDAO database;
    private User user;
    private String calendarViewURL = "/personalplanner/Views/CalendarView.fxml";

    @FXML private Button editAppCancelButton;
    @FXML private Button editAppSaveButton;
    @FXML private TableView<?> selectCustTable;    
    @FXML private TableColumn<?, ?> customerNameCol;
    @FXML private TextField locationTextField;
    @FXML private TextField typeTextField;
    @FXML private TextField descriptionTextField;
    @FXML private TextField startTimeTextField;
    @FXML private TextField endTimeTextField;

    @FXML private void editAppCancelButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(calendarViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CalendarViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void editAppSaveButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(calendarViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CalendarViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void initData(User user, Appointment app) {

        this.user = user;
        this.appointment = app;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        selectCustTable.setPlaceholder(new Label(""));

    }

}
