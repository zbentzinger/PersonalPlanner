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

    boolean deleteAppointment(Appointment appointment);
    boolean insertAppointment(Appointment appointment);
    boolean updateAppointment(Appointment appointment);

    boolean deleteCustomer(Customer customer);
    boolean insertCustomer(Customer customer);
    boolean updateCustomer(Customer customer);

    User getUser(String username, String pass);

}
