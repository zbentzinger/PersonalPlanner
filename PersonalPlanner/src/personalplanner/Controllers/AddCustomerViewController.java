package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.Utils.Database;

public class AddCustomerViewController implements Initializable {

    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button newCustSaveButton;
    @FXML private Button newCustCancelButton;

    @FXML private TextField nameTextField;
    @FXML private TextField cityTextField;
    @FXML private TextField countryTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;    

    private void setSaveButtonState() {

        BooleanBinding enabledState = new BooleanBinding() {
            {
                super.bind(
                    nameTextField.textProperty(),
                    cityTextField.textProperty(),
                    countryTextField.textProperty(),
                    addressTextField.textProperty(),
                    phoneTextField.textProperty()
                );
            }

            @Override protected boolean computeValue() {
                return (
                    nameTextField.getText().isEmpty() ||
                    cityTextField.getText().isEmpty() || 
                    countryTextField.getText().isEmpty() ||
                    addressTextField.getText().isEmpty() ||
                    phoneTextField.getText().isEmpty()
                );
            }
        };

        newCustSaveButton.disableProperty().bind(enabledState);

    }

    private int getAddressId(String address) {

        int id = -1;

        String query = String.format(
                "SELECT addressId FROM address WHERE address = '%s'",
                address
        );

        try {
            ResultSet result = Database.Select(query);

            while(result.next()) {
                if(result.getInt(1) >= 1) {
                    id = result.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return id;

    }

    private int getCityId(String cityName) {

        int id = -1;

        String query = String.format(
                "SELECT cityid FROM city WHERE city = '%s'",
                cityName
        );

        try {
            ResultSet result = Database.Select(query);

            while(result.next()) {
                if(result.getInt(1) >= 1) {
                    id = result.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return id;

    }

    private int getCountryId(String countryName) {

        int id = -1;

        String query = String.format(
                "SELECT countryId FROM country WHERE country = '%s'",
                countryName
        );

        try {
            ResultSet result = Database.Select(query);

            while(result.next()) {
                if(result.getInt(1) >= 1) {
                    id = result.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return id;

    }

    private int getLatestId(String tableName) {

        int id = -1;

        // hack
        String query = String.format(
                "SELECT %sId FROM %s ORDER BY %sId DESC limit 1;",
                tableName,
                tableName,
                tableName
        );

        try {
            ResultSet result = Database.Select(query);

            while(result.next()) {
                if(result.getInt(1) >= 1) {
                    id = result.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return id;

    }

    private void insertAddress(int cityID) {

        String query = String.format(
                "INSERT INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy, lastUpdateBy) VALUES ('%s', '', '%s', '', '%s', NOW(), '', '')",
                addressTextField.getText(),
                String.valueOf(cityID),
                phoneTextField.getText()
        );

        Database.Update(query);

    }

    private void insertCity(int countryID) {

        String query = String.format(
                "INSERT INTO city (city, countryId, createDate, createdBy, lastUpdateBy) VALUES ('%s', '%s', NOW(), '', '')",
                cityTextField.getText(),
                String.valueOf(countryID)
        );

        Database.Update(query);

    }

    private void insertCountry() {

        String query = String.format(
                "INSERT INTO country (country, createDate, createdBy, lastUpdateBy) VALUES ('%s', NOW(), '', '')",
                countryTextField.getText()
        );

        Database.Update(query);

    }

    private void insertCustomer(int addressID) {

        String query = String.format(
                "INSERT INTO customer (customerName, addressId, active, createDate, createdBy, lastUpdateBy) VALUES ('%s', '%s', TRUE, NOW(), '', '')",
                nameTextField.getText(),
                String.valueOf(addressID)
        );

        Database.Update(query);

    }

    @FXML private void newCustSaveButtonClicked(ActionEvent event) throws IOException {        

        int countryID = getCountryId(countryTextField.getText());
        int cityID = getCityId(cityTextField.getText());

        if(countryID == -1) {
            insertCountry();
            countryID = getLatestId("country");

        }

        if (cityID == -1) {
            insertCity(countryID);
            cityID = getLatestId("city");

        }

        insertAddress(cityID);
        insertCustomer(getLatestId("address"));

        Parent customersView = FXMLLoader.load(getClass().getResource(customersViewURL));

        Scene customersScene = new Scene(customersView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customersScene);
        window.show();

    }

    @FXML private void newCustCancelButtonClicked(ActionEvent event) throws IOException {

        Parent customersView = FXMLLoader.load(getClass().getResource(customersViewURL));

        Scene customersScene = new Scene(customersView);

        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setScene(customersScene);
        window.show();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        setSaveButtonState();

    }

}
