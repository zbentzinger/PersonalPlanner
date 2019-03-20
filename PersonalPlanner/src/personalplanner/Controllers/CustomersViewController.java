package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
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
import personalplanner.Utils.Database;

public class CustomersViewController implements Initializable {

    private User user;
    private String editCustViewURL = "/personalplanner/Views/EditCustomerView.fxml";
    private String addCustViewURL = "/personalplanner/Views/AddCustomerView.fxml";
    private String homeViewURL = "/personalplanner/Views/HomeView.fxml";
    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    @FXML private Button editCustomerButton;
    @FXML private Button addCustomerButton;
    @FXML private Button deleteCustomerButton;
    @FXML private Button customersHomeButton;
    @FXML private TableView<Customer> customersTableView;
    @FXML private TableColumn<Customer, String> customerNameCol;

    public void initData(User user) {

        this.user = user;

    }

    private void getCustomers() throws SQLException {

        String query = "SELECT * " 
                     + "FROM customer "
                     + "WHERE active";
        
        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) { 

            try (ResultSet result = pstmnt.executeQuery()) {

                while(result.next()) {

                    LocalDateTime created = result.getTimestamp("createDate").toLocalDateTime();
                    LocalDateTime updated = result.getTimestamp("lastUpdate").toLocalDateTime();

                    Customer customer = new Customer();
                    customer.setCustomerID(result.getInt("customerid"));
                    customer.setCustomerName(result.getString("customerName"));
                    customer.setAddressID(result.getInt("addressId"));
                    customer.setActive(result.getInt("active"));
                    customer.setCreatedAt(created);
                    customer.setCreatedBy(result.getString("createdBy"));
                    customer.setUpdatedAt(updated);
                    customer.setUpdatedBy(result.getString("lastUpdateBy"));

                    this.allCustomers.add(customer);

                }
            }
        }
    }

    private void populateCustomersTable() {

        try {

            getCustomers();

        } catch (SQLException e) {

            System.out.println("Exception in getCountries query: %s" + e);

        }

        customerNameCol.setCellValueFactory(
                new PropertyValueFactory<Customer, String>("customerName")
        );

        customersTableView.setItems(allCustomers);
        customersTableView.setPlaceholder(new Label(""));

    }

    @FXML private void editCustomerButton(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(editCustViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        EditCustomerViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void addCustomerButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(addCustViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        AddCustomerViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @FXML private void deleteCustomerButtonClicked(ActionEvent event) {
    }

    @FXML private void customersHomeButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(homeViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        HomeViewController controller = loader.getController();
        controller.initData(this.user);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        populateCustomersTable();

    }

}
