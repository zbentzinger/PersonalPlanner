package personalplanner.DAO;

import java.time.LocalDateTime;
import javafx.collections.ObservableList;
import personalplanner.Models.Appointment;
import personalplanner.Models.AppointmentsByCustomerReport;
import personalplanner.Models.AppointmentsByMonthReport;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

// DAO interface for all database tables and reports.
public interface MainDAO {

    ObservableList<Appointment> getAppointmentsInRange(LocalDateTime begin, LocalDateTime end);
    ObservableList<Appointment> getAppointmentsByUser();
    ObservableList<AppointmentsByCustomerReport> appointmentsByCustomerReport();
    ObservableList<AppointmentsByMonthReport> appointmentsByMonthReport();
    ObservableList<City> getCities(Country country);
    ObservableList<Country> getAllCountries();
    ObservableList<Customer> getAllCustomers();

    City getCity(String cityName);
    User getUser(String username, String pass) throws InvalidUserException;

    boolean appointmentConflicts(Appointment appointment);
    boolean isAppointmentSoon(User user);

    void deleteAppointment(Appointment appointment);
    void deleteCustomer(Customer customer);
    void insertAppointment(Appointment appointment);
    void insertCustomer(Customer customer);
    void updateAppointment(Appointment appointment);
    void updateCustomer(Customer customer);

}
