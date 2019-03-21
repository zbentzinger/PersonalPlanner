package personalplanner.Controllers;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.DAO.MainDAO;

public class EditCustomerViewController implements Initializable {

    private MainDAO database;
    private Customer customer;
    private User user;
    private String custViewURL = "/personalplanner/Views/CustomersView.fxml";

    @FXML private Button editCustSaveButton;
    @FXML private Button editCustCancelButton;
    @FXML private ChoiceBox<String> countryDropDown;
    @FXML private ChoiceBox<String> cityDropDown;
    @FXML private TextField nameTextField;
    @FXML private TextField addressTextField;
    @FXML private TextField phoneTextField;
    @FXML private TextField postalCodeTextField;

    public void editCustSaveButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(custViewURL));
        Parent view = loader.load();
        Scene scene = new Scene(view);
        CustomersViewController controller = loader.getController();
        controller.initData(this.user, this.database);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    public void editCustCancelButtonClicked(ActionEvent event) throws IOException {        

        // Load the next scene.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(custViewURL));
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

        cityDropDown.getSelectionModel().select(0);

    }

    private void populateCountryDropdown() {

        countryDropDown.getSelectionModel().select(0);

        populateCityDropdown(countryDropDown.getSelectionModel().getSelectedItem());

    }

    public void initData(User user, Customer customer, MainDAO dao) {

        this.user = user;
        this.customer = customer;
        this.database = dao;
        
        nameTextField.setText(this.customer.getCustomerName());
        addressTextField.setText(this.customer.getAddress().getAddress());
        phoneTextField.setText(this.customer.getAddress().getPhone());
        postalCodeTextField.setText(this.customer.getAddress().getZip());

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

        editCustSaveButton.disableProperty().bind(enabledState);

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
