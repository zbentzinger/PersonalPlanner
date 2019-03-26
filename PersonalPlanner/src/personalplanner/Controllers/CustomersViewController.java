package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;

public class CustomersViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String editCustViewURL = "/personalplanner/Views/EditCustomerView.fxml";
    private String addCustViewURL = "/personalplanner/Views/AddCustomerView.fxml";
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";

    @FXML private Button editCustomerButton;
    @FXML private Button addCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button customersHomeButton;
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, String> customerNameCol;

    private void add() {  

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(addCustViewURL));

            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            AddCustomerViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CustomersViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void bindButtons() {

        editCustomerButton.disableProperty().bind(
            Bindings.isEmpty(
                customersTableView.getSelectionModel().getSelectedItems()
            )
        );

        deleteCustomerButton.disableProperty().bind(
            Bindings.isEmpty(
                customersTableView.getSelectionModel().getSelectedItems()
            )
        );

    }

    private void delete() {

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        customersTableView.getItems().remove(selectedCustomer);

        this.database.deleteCustomer(selectedCustomer);

    }

    private void edit() {

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource(editCustViewURL));

            Stage stage = (Stage) editCustomerButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            EditCustomerViewController controller = loader.getController();
            controller.initData(this.user, selectedCustomer);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CustomersViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }
    
    private void home() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(homeViewURL));

            Stage stage = (Stage) customersHomeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));

            HomeViewController controller = loader.getController();
            controller.initData(this.user);

            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(CustomersViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void populateCustomersTable() {

        customersTableView.setItems(this.database.getAllCustomers());
        customersTableView.setPlaceholder(new Label(""));

    }

    public void initData(User user) {

        this.user = user;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        customerNameCol.setCellValueFactory(
            new PropertyValueFactory<>("customerName")
        );

        populateCustomersTable();

        bindButtons();
        addCustomerButton.setOnAction(e -> add());
        deleteCustomerButton.setOnAction(e -> delete());
        editCustomerButton.setOnAction(e -> edit());
        customersHomeButton.setOnAction(e -> home());

    }

}
