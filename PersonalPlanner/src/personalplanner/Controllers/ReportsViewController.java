package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import personalplanner.Models.Appointment;
import personalplanner.Models.AppointmentsByCustomerReport;
import personalplanner.Models.AppointmentsByMonthReport;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

// Rubric I: Show three reports. This is accomplished through TabPane and TableViews.
public class ReportsViewController implements Initializable {

    private User user;

    @FXML private Button homeButton;
    @FXML private TableView<AppointmentsByMonthReport> byMonthTable;
    @FXML private TableColumn<AppointmentsByMonthReport, String> byMonthAppTypeCol;
    @FXML private TableColumn<AppointmentsByMonthReport, Integer> byMonthNumOfAppsCol;
    @FXML private TableColumn<AppointmentsByMonthReport, String> byMonthMonthCol;
    @FXML private TableColumn<AppointmentsByMonthReport, Integer> byMonthYearCol;
    @FXML private TableView<Appointment> conSchedTable;
    @FXML private TableColumn<Appointment, String> conSchedConCol;
    @FXML private TableColumn<Appointment, String> conSchedAppDescCol;
    @FXML private TableColumn<Appointment, String> conSchedFromCol;
    @FXML private TableColumn<Appointment, String> conSchedToCol;
    @FXML private TableColumn<Appointment, String> ConSchedCustCol;
    @FXML private TableView<AppointmentsByCustomerReport> byCustTable;
    @FXML private TableColumn<AppointmentsByCustomerReport, String> byCustCustCol;
    @FXML private TableColumn<AppointmentsByCustomerReport, Integer> byCustNumAppsCol;

    private void home() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.HOME_VIEW_PATH));
            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    // Rubric I3: Custom report - Number of appointments by customer.
    private void populateAppByCustomerReport() {

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        byCustCustCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomerName())
        );

        byCustNumAppsCol.setCellValueFactory(
            column -> new SimpleIntegerProperty(column.getValue().getNumberOfAppointments()).asObject()
        );

        byCustTable.setItems(Utils.DATABASE.appointmentsByCustomerReport());

    }

    // Rubric I1: Number of appointment types by month report.
    private void populateAppByMonthReport() {

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        byMonthAppTypeCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAppointmentType())
        );

        byMonthNumOfAppsCol.setCellValueFactory(
            column -> new SimpleIntegerProperty(column.getValue().getNumberOfAppointments()).asObject()
        );

        byMonthMonthCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAppointmentMonth())
        );

        byMonthYearCol.setCellValueFactory(
            column -> new SimpleIntegerProperty(column.getValue().getAppointmentYear()).asObject()
        );

        byMonthTable.setItems(Utils.DATABASE.appointmentsByMonthReport());

    }

    // Rubric I2: Schedule for each consultant report.
    private void populateScheduleReport() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        conSchedConCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getUser().getUserName())
        );

        conSchedAppDescCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getDescription())
        );

        conSchedFromCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getStart().format(formatter))
        );

        conSchedToCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getEnd().format(formatter))
        );

        ConSchedCustCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomer().getCustomerName())
        );

        conSchedTable.setItems(Utils.DATABASE.getAppointmentsByUser());

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.populateScheduleReport();
        this.populateAppByMonthReport();
        this.populateAppByCustomerReport();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        homeButton.setOnAction(e -> this.home());

    }

}
