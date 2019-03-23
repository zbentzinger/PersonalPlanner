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
import personalplanner.Models.Address;
import personalplanner.Models.Customer;

public class AddCustomerViewController implements Initializable {

    private MainDAO database;
    private User user;
    private String customersViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button newCustCancelButton;
    @FXML private Button newCustSaveButton;
    @FXML private ChoiceBox<String> cityDropDown;
    @FXML private ChoiceBox<String> countryDropDown;
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

        newCustSaveButton.disableProperty().bind(enabledState);

    }

    private void cancel() {

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(customersViewURL));
            
            Stage stage = (Stage) newCustCancelButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CustomersViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddCustomerViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    private void populateCityDropdown(String country) {

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
        address.setAddress2("");
        address.setZip(postalCodeTextField.getText());
        address.setPhone(phoneTextField.getText());
        address.setCity(this.database.getCity(cityDropDown.getSelectionModel().getSelectedItem()));
        address.setCreatedBy(this.user.getUserName());
        address.setUpdatedBy(this.user.getUserName());

        Customer customer = new Customer();
        customer.setCustomerName(nameTextField.getText());
        customer.setAddress(address);
        customer.setCreatedBy(this.user.getUserName());
        customer.setUpdatedBy(this.user.getUserName());
        customer.setActive(1);

        this.database.insertCustomer(customer);

        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource(customersViewURL));
            
            Stage stage = (Stage) newCustSaveButton.getScene().getWindow();
            stage.setScene(new Scene((Parent) loader.load()));
            
            CustomersViewController controller = loader.getController();
            controller.initData(this.user, this.database);
            
            stage.show();

        } catch (IOException ex) {

            Logger.getLogger(AddCustomerViewController.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void initData(User user, MainDAO dao) {

        this.user = user;
        this.database = dao;
        
        populateCountryDropdown();

    }

    @Override public void initialize(URL url, ResourceBundle rb) {

        bindButtons();
        countryDropDown.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> populateCityDropdown(newVal));
        newCustCancelButton.setOnAction(e -> cancel());
        newCustSaveButton.setOnAction(e -> save());

    }

}
