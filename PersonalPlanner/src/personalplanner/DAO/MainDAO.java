package personalplanner.DAO;

import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import personalplanner.Models.Appointment;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public interface MainDAO {

    ObservableList<Appointment> getAppointmentsByMonth(LocalDateTime date);
    ObservableList<Appointment> getAppointmentsByWeek(LocalDateTime date);
    void deleteAppointment(Appointment appointment);
    void insertAppointment(Appointment appointment);
    void updateAppointment(Appointment appointment);
    boolean appointmentConflicts(Appointment appointment);

    ObservableList<City> getCities(Country country);
    City getCity(String cityName);

    ObservableList<Country> getAllCountries();

    ObservableList<Customer> getAllCustomers();
    void deleteCustomer(Customer customer);
    void insertCustomer(Customer customer);
    void updateCustomer(Customer customer);

    User getUser(String username, String pass) throws InvalidUserException;

}
