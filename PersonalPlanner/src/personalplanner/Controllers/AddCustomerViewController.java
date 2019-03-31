package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
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
import personalplanner.Models.Address;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Utils.Utils;

public class AddCustomerViewController implements Initializable {

    private Address address;
    private Customer customer;
    private User user;

    @FXML private Button newCustCancelButton;
    @FXML private Button newCustSaveButton;
    @FXML private ChoiceBox<City> cityDropDown;
    @FXML private ChoiceBox<Country> countryDropDown;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;  
    @FXML private TextField postalCodeTextField;

    // Rubric F3: Do not show buttons if not all data is filled in.
    private void bindButtonsToForm() {

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

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CUSTOMERS_VIEW_PATH));
            Stage stage = (Stage) newCustCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CustomersViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    private void populateCityDropdown(Country country) {

        cityDropDown.getItems().clear();
        cityDropDown.getItems().addAll(Utils.DATABASE.getCities(country));
        cityDropDown.getSelectionModel().select(0);

    }

    private void populateCountryDropdown() {

        countryDropDown.getItems().addAll(Utils.DATABASE.getAllCountries());
        countryDropDown.getSelectionModel().select(0);

        populateCityDropdown(countryDropDown.getSelectionModel().getSelectedItem());

    }

    private void save() {

        this.address = new Address();
        this.address.setAddress(addressTextField.getText());
        this.address.setZip(postalCodeTextField.getText());
        this.address.setPhone(phoneTextField.getText());
        this.address.setCity(cityDropDown.getSelectionModel().getSelectedItem());
        this.address.setCreatedBy(this.user.getUserName());
        this.address.setUpdatedBy(this.user.getUserName());

        this.customer = new Customer();
        this.customer.setCustomerName(nameTextField.getText());
        this.customer.setAddress(this.address);
        this.customer.setCreatedBy(this.user.getUserName());
        this.customer.setUpdatedBy(this.user.getUserName());

        Utils.DATABASE.insertCustomer(this.customer);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(Utils.CUSTOMERS_VIEW_PATH));
            Stage stage = (Stage) newCustSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            CustomersViewController controller = loader.getController();
            controller.initData(this.user);
            stage.show();

        } catch (IOException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user) {

        this.user = user;

    }

    // Rubric B: Ability to add customers.
    @Override public void initialize(URL url, ResourceBundle rb) {

        this.populateCountryDropdown();
        this.bindButtonsToForm();

        // Rubric G - Lambda: This lambda will populate my Country and City drop downs 
        // depending on which country is seleceted.
        countryDropDown.getSelectionModel().selectedItemProperty().addListener(
            (obs, oldVal, newVal) -> this.populateCityDropdown(newVal)
        );

        // Rubric G - Lambda: I chose to map all button actions using a lambda.
        newCustCancelButton.setOnAction(e -> this.cancel());
        newCustSaveButton.setOnAction(e -> this.save());

    }

}
