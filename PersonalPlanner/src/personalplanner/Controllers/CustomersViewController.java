package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

public class CustomersViewController implements Initializable {

    private User user;

    @FXML private Button editCustomerButton;
    @FXML private Button addCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button customersHomeButton;
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, String> customerCol;
    @FXML private TableColumn<Customer, String> phoneCol;
    @FXML private TableColumn<Customer, String> addressCol;
    @FXML private TableColumn<Customer, String> cityCol;
    @FXML private TableColumn<Customer, String> countryCol;

    // Rubric B: Ability to add Customer. Will redirect to AddCustomerView.
    private void add() {  

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` adding a new customer", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.ADD_CUSTOMER_VIEW_PATH));
            Stage stage = (Stage) addCustomerButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            AddCustomerViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    // Rubric F: Do not enable edit or delete buttons if selection hasn't been made.
    private void bindButtonsToTable() {

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

    // Rubric B: Ability to delete customer. Will delete selected customer from database.
    private void delete() {

        Utils.LOGGER.log(Level.INFO, "User: `{0}` deleted customer", this.user.getUserName());

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        customersTableView.getItems().remove(selectedCustomer);

        Utils.DATABASE.deleteCustomer(selectedCustomer);

    }

    // Rubric B: Ability to update customer. Will take selected customer and
    // redirect to EditCustomerView which allows updating of customers.
    private void edit() {

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        try {
            
            Utils.LOGGER.log(Level.INFO, "User: `{0}` editing an existing customer ", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.EDIT_CUSTOMER_VIEW_PATH));
            Stage stage = (Stage) editCustomerButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            EditCustomerViewController controller = loader.getController();
            controller.initData(this.user, selectedCustomer);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }
    
    private void home() {

        try {

            Utils.LOGGER.log(Level.INFO, "User: `{0}` navigating back to home", this.user.getUserName());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.HOME_VIEW_PATH));
            Stage stage = (Stage) customersHomeButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            HomeViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void populateCustomersTable() {

        // Rubric G
        customerCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getCustomerName())
        );
        phoneCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getPhone())
        );
        addressCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getAddress())
        );
        cityCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getCity().getCityName())
        );
        countryCol.setCellValueFactory(
            column -> new SimpleStringProperty(column.getValue().getAddress().getCity().getCountry().getCountryName())
        );

        customersTableView.setItems(Utils.DATABASE.getAllCustomers());
        customersTableView.setPlaceholder(new Label(""));

    }

    public void initData(User user) {

        this.user = user;

    }

    // Rubric B: Ability to add/update/delete customers.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.populateCustomersTable();
        this.bindButtonsToTable();

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        addCustomerButton.setOnAction(e -> this.add());
        deleteCustomerButton.setOnAction(e -> this.delete());
        editCustomerButton.setOnAction(e -> this.edit());
        customersHomeButton.setOnAction(e -> this.home());

    }

}
