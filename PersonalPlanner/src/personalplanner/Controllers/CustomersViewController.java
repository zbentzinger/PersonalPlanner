package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.DAO.MainDAO;

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

    private void populateCustomersTable() {

        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerName")
        );

        customersTableView.setItems(this.database.getAllCustomers());
        customersTableView.setPlaceholder(new Label(""));

    }

    public void editCustomerButton(ActionEvent event) throws IOException {

        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(editCustViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        EditCustomerViewController controller = loader.getController();
        controller.initData(this.user, selectedCustomer, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void addCustomerButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(addCustViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        AddCustomerViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void deleteCustomerButtonClicked(ActionEvent event) {
    }

    public void customersHomeButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(homeViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        HomeViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

        populateCustomersTable();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

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

}
