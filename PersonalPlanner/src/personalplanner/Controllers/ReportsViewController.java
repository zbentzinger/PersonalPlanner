package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.Appointment;
import personalplanner.Models.AppointmentsByCustomerReport;
import personalplanner.Models.AppointmentsByMonthReport;
import personalplanner.Models.User;

public class ReportsViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource(homeViewURL));

            Stage stage = (Stage) homeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            HomeViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CustomersViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    // Rubric I3: Custom report - Number of appointments by customer.
    private void populateAppByCustomerReport() {

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        byCustCustCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getCustomerName())
        );

        byCustNumAppsCol.setCellValueFactory(
            row -> new SimpleIntegerProperty(row.getValue().getNumberOfAppointments()).asObject()
        );

        byCustTable.setItems(this.database.appointmentsByCustomerReport());

    }

    // Rubric I1: Number of appointment types by month report.
    private void populateAppByMonthReport() {

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        byMonthAppTypeCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getAppointmentType())
        );

        byMonthNumOfAppsCol.setCellValueFactory(
            row -> new SimpleIntegerProperty(row.getValue().getNumberOfAppointments()).asObject()
        );

        byMonthMonthCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getAppointmentMonth())
        );

        byMonthYearCol.setCellValueFactory(
            row -> new SimpleIntegerProperty(row.getValue().getAppointmentYear()).asObject()
        );

        byMonthTable.setItems(this.database.appointmentsByMonthReport());

    }

    // Rubric I2: Schedule for each consultant report.
    private void populateScheduleReport() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd hh:mma");

        // Rubric G - Lambda: I chose to use lambdas to pull rows from the report
        // and add them to the tableview.
        conSchedConCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getUser().getUserName())
        );

        conSchedAppDescCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getDescription())
        );

        conSchedFromCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getStart().format(formatter))
        );

        conSchedToCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getEnd().format(formatter))
        );

        ConSchedCustCol.setCellValueFactory(
            row -> new SimpleStringProperty(row.getValue().getCustomer().getCustomerName())
        );

        conSchedTable.setItems(this.database.getAppointmentsByUser());

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        this.populateScheduleReport();
        this.populateAppByMonthReport();
        this.populateAppByCustomerReport();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        homeButton.setOnAction(e -> home());

    }

}
