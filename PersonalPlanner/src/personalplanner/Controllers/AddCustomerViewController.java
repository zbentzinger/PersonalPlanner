package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.Models.User;
import personalplanner.DAO.MainDAO;
import personalplanner.DAO.MainDAOImpl;
import personalplanner.Models.Address;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;

public class AddCustomerViewController implements Initializable {

    private static final Logger LOGGER = Logger.getLogger("PersonalPlanner");

    private MainDAO database;
    private User user;
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button newCustCancelButton;
    @FXML private Button newCustSaveButton;
    @FXML private ChoiceBox<City> cityDropDown;
    @FXML private ChoiceBox<Country> countryDropDown;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;  
    @FXML private TextField postalCodeTextField;

    // Rubric F3: Do not show buttons if not all data is filled in.
    private void bindButtons() {

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

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(customersViewURL));
            Stage stage = (Stage) newCustCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CustomersViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void populateCityDropdown(Country country) {

        cityDropDown.getItems().clear();
        cityDropDown.getItems().addAll(this.database.getCities(country));
        cityDropDown.getSelectionModel().select(0);

    }

    private void populateCountryDropdown() {

        countryDropDown.getItems().addAll(this.database.getAllCountries());
        countryDropDown.getSelectionModel().select(0);

        populateCityDropdown(countryDropDown.getSelectionModel().getSelectedItem());

    }

    private void save() {

        Address address = new Address();
        address.setAddress(addressTextField.getText());
        address.setZip(postalCodeTextField.getText());
        address.setPhone(phoneTextField.getText());
        address.setCity(cityDropDown.getSelectionModel().getSelectedItem());
        address.setCreatedBy(this.user.getUserName());
        address.setUpdatedBy(this.user.getUserName());

        Customer customer = new Customer();
        customer.setCustomerName(nameTextField.getText());
        customer.setAddress(address);
        customer.setCreatedBy(this.user.getUserName());
        customer.setUpdatedBy(this.user.getUserName());

        this.database.insertCustomer(customer);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(customersViewURL));
            Stage stage = (Stage) newCustSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CustomersViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user) {

        this.user = user;

    }

    // Rubric B: Ability to add customers.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.database = new MainDAOImpl();

        populateCountryDropdown();

        bindButtons();

        // Rubric G - Lambda: This lambda will populate my Country and City drop downs 
        // depending on which country is seleceted.
        countryDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> populateCityDropdown(newVal));

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        newCustCancelButton.setOnAction(e -> cancel());
        newCustSaveButton.setOnAction(e -> save());

    }

}
