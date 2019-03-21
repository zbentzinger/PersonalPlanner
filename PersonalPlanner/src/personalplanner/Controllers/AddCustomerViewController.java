package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.Models.User;
import personalplanner.DAO.Database;
import personalplanner.DAO.MainDAO;

public class AddCustomerViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button newCustSaveButton;
    @FXML private ChoiceBox<String> cityDropDown;
    @FXML private ChoiceBox<String> countryDropDown;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;  
    @FXML private TextField postalCodeTextField;

    private int createAddress(int cityID) throws SQLException {

        int addressID = -1;
        
        String query = "INSERT INTO address "
                     + "(address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdateBy) "
                     + "VALUES (?,'',?,?,?,NOW(),?,?)";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) { 

            pstmnt.setString(1, addressTextField.getText());
            pstmnt.setInt(2, cityID);
            pstmnt.setString(3, postalCodeTextField.getText());
            pstmnt.setString(4, phoneTextField.getText());
            pstmnt.setString(5, this.user.getUserName());
            pstmnt.setString(6, this.user.getUserName());

            pstmnt.executeUpdate();

            try (ResultSet generatedKeys = pstmnt.getGeneratedKeys()) {

                while(generatedKeys.next()) {
                    addressID = generatedKeys.getInt(1);
                }
            }

            return addressID;

        }
    }

    private void createCustomer(int addressID) throws SQLException {

        String query = "INSERT INTO customer " 
                     + "(customerName,addressId,active,createDate,createdBy,lastUpdateBy) "
                     + "VALUES (?,?,1,NOW(),?,?)";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) { 

            pstmnt.setString(1, nameTextField.getText());
            pstmnt.setInt(2, addressID);
            pstmnt.setString(3, this.user.getUserName());
            pstmnt.setString(4, this.user.getUserName());

            pstmnt.executeUpdate();

        }
    }

    private ObservableList<String> getCities(String country) throws SQLException {

        ObservableList<String> cities = FXCollections.observableArrayList();

        String query = "SELECT c.city "
                     + "FROM city c "
                     + "JOIN country cc "
                     + "ON c.countryId = cc.countryid "
                     + "WHERE cc.country = ?";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) { 

            pstmnt.setString(1, country);
            try (ResultSet result = pstmnt.executeQuery()) {

                while(result.next()) {
                    cities.add(result.getString("city"));
                }
            }
        }

        return cities;

    }

    private int getCityId(String city) throws SQLException {

        int cityID = -1;

        String query = "SELECT cityid " 
                     + "FROM city " 
                     + "WHERE city = ?";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) { 

            pstmnt.setString(1, city);
            try (ResultSet result = pstmnt.executeQuery()) {

                while(result.next()) {
                    cityID = result.getInt("cityid");
                }
            }
        }

        return cityID;

    }

    private ObservableList<String> getCountries() throws SQLException {

        ObservableList<String> countries = FXCollections.observableArrayList();

        String query = "SELECT country " 
                     + "FROM country";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement pstmnt = conn.prepareStatement(query);
        ) { 

            try (ResultSet result = pstmnt.executeQuery()) {

                while(result.next()) {
                    countries.add(result.getString("country"));
                }
            }
        }

        return countries;

    }

    public void newCustCancelButtonClicked(ActionEvent event) throws IOException {

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(customersViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void newCustSaveButtonClicked(ActionEvent event) throws IOException, SQLException {        

        // Create Address, returns its ID and then create a Customer.
        int city = getCityId(cityDropDown.getSelectionModel().getSelectedItem());
        int addr = createAddress(city);

        createCustomer(addr);

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(customersViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    private void populateCityDropdown(String country) {

        cityDropDown.getItems().clear();

        try {

            cityDropDown.getItems().addAll(getCities(country));

        } catch (SQLException e) {

            System.out.println("Exception in getCities query: %s" + e);

        }

        cityDropDown.getSelectionModel().select(0);

    }

    private void populateCountryDropdown() {

        try {

            countryDropDown.getItems().addAll(getCountries());

        } catch (SQLException e) {

            System.out.println("Exception in getCountries query: %s" + e);

        }

        countryDropDown.getSelectionModel().select(0);

        populateCityDropdown(countryDropDown.getSelectionModel().getSelectedItem());

    }

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        // Make sure that all fields have a value before enabling save button.
        BooleanBinding enabledState = new BooleanBinding() {
            {
                super.bind(
                    nameTextField.textProperty(),
                    addressTextField.textProperty(),
                    phoneTextField.textProperty(),
                    postalCodeTextField.textProperty()
                );
            }

            @Override protected boolean computeValue() {
                return (
                    nameTextField.getText().isEmpty() ||
                    addressTextField.getText().isEmpty() ||
                    phoneTextField.getText().isEmpty() ||
                    postalCodeTextField.getText().isEmpty()
                );
            }
        };

        newCustSaveButton.disableProperty().bind(enabledState);

        populateCountryDropdown();

        // Lambda: add listener to country dropdown
        // so that city dropdown repopulates if another country is selected.
        countryDropDown.getSelectionModel()
                       .selectedItemProperty()
                       .addListener(
                           (obs, oldVal, newVal) -> populateCityDropdown(newVal)
                       );

    }

}
