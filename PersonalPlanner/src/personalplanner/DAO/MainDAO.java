package personalplanner.DAO;

import javafx.collections.ObservableList;
import personalplanner.Models.Appointment;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public interface MainDAO {

    ObservableList<Appointment> getAllAppointments();
    ObservableList<Country> getAllCountries();
    ObservableList<Customer> getAllCustomers();
    ObservableList<City> getCities(String country);

    void deleteAppointment(Appointment appointment);
    void insertAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);

    void deleteCustomer(Customer customer);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);

    User getUser(String username, String pass);

}
