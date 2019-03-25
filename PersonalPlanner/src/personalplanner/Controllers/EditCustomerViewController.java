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
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.DAO.MainDAO;
import personalplanner.Models.City;
import personalplanner.Models.Country;

public class EditCustomerViewController implements Initializable {

    private MainDAO database;
    private Customer customer;
    private User user;
    private String custViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button editCustSaveButton;
    @FXML private Button editCustCancelButton;
    @FXML private ChoiceBox<Country> countryDropDown;
    @FXML private ChoiceBox<City> cityDropDown;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField postalCodeTextField;

    private void bindButtons() {

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

        editCustSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(custViewURL));
            
            Stage stage = (Stage) editCustCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CustomersViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddCustomerViewController.class.getName()).log(Level.SEVERE, null, ex);

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

        this.customer.getAddress().setAddress(addressTextField.getText());
        this.customer.getAddress().setZip(postalCodeTextField.getText());
        this.customer.getAddress().setPhone(phoneTextField.getText());
        this.customer.getAddress().setCity(cityDropDown.getSelectionModel().getSelectedItem());
        this.customer.getAddress().setUpdatedBy(this.user.getUserName());
        this.customer.setCustomerName(nameTextField.getText());
        this.customer.setUpdatedBy(this.user.getUserName());
        
        this.database.updateCustomer(this.customer);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(custViewURL));
            
            Stage stage = (Stage) editCustSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CustomersViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(EditCustomerViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user, Customer customer, MainDAO dao) {

        this.user = user;
        this.customer = customer;
        this.database = dao;

        populateCountryDropdown();

        countryDropDown.getSelectionModel().select(this.customer.getAddress().getCity().getCountry());
        cityDropDown.getSelectionModel().select(this.customer.getAddress().getCity());
        nameTextField.setText(this.customer.getCustomerName());
        addressTextField.setText(this.customer.getAddress().getAddress());
        phoneTextField.setText(this.customer.getAddress().getPhone());
        postalCodeTextField.setText(this.customer.getAddress().getZip());

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        bindButtons();
        countryDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> populateCityDropdown(newVal));
        editCustCancelButton.setOnAction(e -> cancel());
        editCustSaveButton.setOnAction(e -> save());

    }
    
}
