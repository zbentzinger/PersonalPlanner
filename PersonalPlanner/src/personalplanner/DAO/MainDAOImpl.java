package personalplanner.DAO;

import personalplanner.Utils.Database;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalplanner.Models.Address;
import personalplanner.Models.Appointment;
import personalplanner.Models.AppointmentsByCustomerReport;
import personalplanner.Models.AppointmentsByMonthReport;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;
import personalplanner.Utils.Utils;

// DAO implementation for all database tables and reports.
public class MainDAOImpl implements MainDAO {

    private String addr_fields = "address.addressid AS AddressAddressID,"
                               + "address.address AS AddressAddress,"
                               + "address.address2 AS AddressAddress2,"
                               + "address.cityid AS AddressCityID,"
                               + "address.postalCode AS AddressPostalCode,"
                               + "address.phone AS AddressPhone,"
                               + "address.createDate AS AddressCreateDate,"
                               + "address.createdBy AS AddressCreatedBy,"
                               + "address.lastUpdate AS AddressLastUpdate,"
                               + "address.lastUpdateBy AS AddressLastUpdateBy";

    private String appt_fields = "appointment.appointmentid AS AppointmentAppointmentID,"
                               + "appointment.customerid AS AppointmentCustomerID,"
                               + "appointment.userid AS AppointmentUserID,"
                               + "appointment.title AS AppointmentTitle,"
                               + "appointment.description AS AppointmentDescription,"
                               + "appointment.location AS AppointmentLocation,"
                               + "appointment.contact AS AppointmentContact,"
                               + "appointment.type AS AppointmentType,"
                               + "appointment.url AS AppointmentUrl,"
                               + "appointment.start AS AppointmentStart,"
                               + "appointment.end AS AppointmentEnd,"
                               + "appointment.createDate AS AppointmentCreateDate,"
                               + "appointment.createdBy AS AppointmentCreatedBy,"
                               + "appointment.lastUpdate AS AppointmentLastUpdate,"
                               + "appointment.lastUpdateBy AS AppointmentLastUpdateBy";

    private String city_fields = "city.cityid AS CityCityID,"
                               + "city.city AS CityCity,"
                               + "city.countryid AS CityCountryID,"
                               + "city.createDate AS CityCreateDate,"
                               + "city.createdBy AS CityCreatedBy,"
                               + "city.lastUpdate AS CityLastUpdate,"
                               + "city.lastUpdateBy AS CityLastUpdateBy";

    private String ctry_fields = "country.countryid AS CountryCountryID,"
                               + "country.country AS CountryCountry,"
                               + "country.createDate AS CountryCreateDate,"
                               + "country.createdBy AS CountryCreatedBy,"
                               + "country.lastUpdate AS CountryLastUpdate,"
                               + "country.lastUpdateBy AS CountryLastUpdateBy";

    private String cust_fields = "customer.customerid AS CustomerCustomerID,"
                               + "customer.customerName AS CustomerName,"
                               + "customer.addressid AS CustomerAddressID,"
                               + "customer.active AS CustomerActive,"
                               + "customer.createDate AS CustomerCreateDate,"
                               + "customer.createdBy AS CustomerCreatedBy,"
                               + "customer.lastUpdate AS CustomerLastUpdate,"
                               + "customer.lastUpdateBy AS CustomerLastUpdateBy";

    private String user_fields = "user.userid AS UserUserID,"
                               + "user.userName AS UserName,"
                               + "user.password AS UserPassword,"
                               + "user.active AS UserActive,"
                               + "user.createBy AS UserCreateBy,"
                               + "user.createDate AS UserCreateDate,"
                               + "user.lastUpdate AS UserLastUpdate,"
                               + "user.lastUpdatedBy AS UserLastUpdatedBy";

    private int createAddress(Address address) {

        int addressID = -1;

        String query = "INSERT INTO address "
                     + "  (address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdateBy) "
                     + "VALUES "
                     + "  (?,?,?,?,?,NOW(),?,?)";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmnt.setString(1, address.getAddress());
            pstmnt.setString(2, address.getAddress2());
            pstmnt.setInt(3, address.getCity().getCityID());
            pstmnt.setString(4, address.getZip());
            pstmnt.setString(5, address.getPhone());
            pstmnt.setString(6, address.getCreatedBy());
            pstmnt.setString(7, address.getUpdatedBy());
            pstmnt.executeUpdate();

            ResultSet result = pstmnt.getGeneratedKeys();
            if(result.next()) {

                addressID = result.getInt(1);

            }

        } catch (SQLException ex) {

            Utils.LOGGER.log(Level.SEVERE, null, ex);

        }  finally {

            Database.closeConnection();

        }

        return addressID;

    }

    private void deleteAddress(Address address) {

        String query = "DELETE "
                     + "FROM address "
                     + "WHERE addressid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, address.getAddressID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    private void deleteAppointmentsForCustomer(Customer customer) {

        String query = "DELETE "
                     + "FROM appointment "
                     + "WHERE customerid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, customer.getCustomerID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    // Rubric E: Adjust appointment times to user's local time.
    private Appointment retrieveAppointment(ResultSet result) throws SQLException {

        Appointment appointment = new Appointment();
        appointment.setCustomer(this.retrieveCustomer(result));
        appointment.setUser(this.retrieveUser(result));
        appointment.setAppointmentID(result.getInt("AppointmentAppointmentID"));
        appointment.setTitle(result.getString("AppointmentTitle"));
        appointment.setDescription(result.getString("AppointmentDescription"));
        appointment.setLocation(result.getString("AppointmentLocation"));
        appointment.setContact(result.getString("AppointmentContact"));
        appointment.setType(result.getString("AppointmentType"));
        appointment.setUrl(result.getString("AppointmentUrl"));
        appointment.setCreatedBy(result.getString("AppointmentCreatedBy"));
        appointment.setUpdatedBy(result.getString("AppointmentLastUpdateBy"));
        appointment.setStart(
            Utils.toLocalTimeZone(
                result.getTimestamp("AppointmentStart").toLocalDateTime()
            )
        );
        appointment.setEnd(
            Utils.toLocalTimeZone(
                result.getTimestamp("AppointmentEnd").toLocalDateTime()
            )
        );
        appointment.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("AppointmentCreateDate").toLocalDateTime()
            )
        );
        appointment.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("AppointmentLastUpdate").toLocalDateTime()
            )
        );

        return appointment;

    }

    private AppointmentsByCustomerReport retrieveAppointmentByCustomerReport(ResultSet result) throws SQLException {

        AppointmentsByCustomerReport app = new AppointmentsByCustomerReport();
        app.setCustomerName(result.getString("CustomerName"));
        app.setNumberOfAppointments(result.getInt("NumberOfAppointments"));

        return app;

    }

    private AppointmentsByMonthReport retrieveAppointmentByMonthReport(ResultSet result) throws SQLException {

        AppointmentsByMonthReport app = new AppointmentsByMonthReport();
        app.setAppointmentMonth(result.getString("Month"));
        app.setAppointmentType(result.getString("AppointmentType"));
        app.setAppointmentYear(result.getInt("Year"));
        app.setNumberOfAppointments(result.getInt("NumberOfAppointments"));

        return app;

    }

    private Address retrieveAddress(ResultSet result) throws SQLException {

        Address address = new Address();
        address.setCity(this.retrieveCity(result));
        address.setAddressID(result.getInt("AddressAddressID"));
        address.setAddress(result.getString("AddressAddress"));
        address.setAddress2(result.getString("AddressAddress2"));
        address.setZip(result.getString("AddressPostalCode"));
        address.setPhone(result.getString("AddressPhone"));
        address.setCreatedBy(result.getString("AddressCreatedBy"));
        address.setUpdatedBy(result.getString("AddressLastUpdateBy"));
        address.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("AddressCreateDate").toLocalDateTime()
            )
        );
        address.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("AddressLastUpdate").toLocalDateTime()
            )
        );

        return address;

    }

    private City retrieveCity(ResultSet result) throws SQLException {

        City city = new City();
        city.setCountry(this.retrieveCountry(result));
        city.setCityID(result.getInt("CityCityID"));
        city.setCityName(result.getString("CityCity"));
        city.setCreatedBy(result.getString("CityCreatedBy"));
        city.setUpdatedBy(result.getString("CityLastUpdateBy"));
        city.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CityCreateDate").toLocalDateTime()
            )
        );
        city.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CityLastUpdate").toLocalDateTime()
            )
        );

        return city;

    }

    private Country retrieveCountry(ResultSet result) throws SQLException {

        Country country = new Country();
        country.setCountryID(result.getInt("CountryCountryID"));
        country.setCountryName(result.getString("CountryCountry"));
        country.setCreatedBy(result.getString("CountryCreatedBy"));
        country.setUpdatedBy(result.getString("CountryLastUpdateBy"));
        country.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CountryCreateDate").toLocalDateTime()
            )
        );
        country.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CountryLastUpdate").toLocalDateTime()
            )
        );

        return country;
    }

    private Customer retrieveCustomer(ResultSet result) throws SQLException {

        Customer customer = new Customer();
        customer.setAddress(this.retrieveAddress(result));
        customer.setCustomerID(result.getInt("CustomerCustomerID"));
        customer.setCustomerName(result.getString("CustomerName"));
        customer.setActive(result.getInt("CustomerActive"));
        customer.setCreatedBy(result.getString("CustomerCreatedBy"));
        customer.setUpdatedBy(result.getString("CustomerLastUpdateBy"));
        customer.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CustomerCreateDate").toLocalDateTime()
            )
        );
        customer.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("CustomerLastUpdate").toLocalDateTime()
            )
        );

        return customer;

    }

    private User retrieveUser(ResultSet result) throws SQLException {

        User user = new User();
        user.setUserID(result.getInt("UserUserID"));
        user.setUserName(result.getString("UserName"));
        user.setPassword(result.getString("UserPassword"));
        user.setActive(result.getInt("UserActive"));
        user.setCreatedBy(result.getString("UserCreateBy"));
        user.setUpdatedBy(result.getString("UserLastUpdatedBy"));
        user.setCreatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("UserCreateDate").toLocalDateTime()
            )
        );
        user.setUpdatedAt(
            Utils.toLocalTimeZone(
                result.getTimestamp("UserLastUpdate").toLocalDateTime()
            )
        );

        return user;

    }

    private void updateAddress(Address address) {

        String query = "UPDATE address SET"
                     + "  address = ?,"
                     + "  address2 = ?,"
                     + "  cityId = ?,"
                     + "  postalCode = ?,"
                     + "  phone = ?,"
                     + "  lastUpdateBy = ? "
                     + "WHERE addressid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, address.getAddress());
            pstmnt.setString(2, address.getAddress2());
            pstmnt.setInt(3, address.getCity().getCityID());
            pstmnt.setString(4, address.getZip());
            pstmnt.setString(5, address.getPhone());
            pstmnt.setString(6, address.getUpdatedBy());
            pstmnt.setInt(7, address.getAddressID());
            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when updating address: " + e);

        } finally {

            Database.closeConnection();

        }

    }

    // Rubric E: Adjust appointment times to user's local time.
    @Override public ObservableList<Appointment> getAppointmentsInRange(LocalDateTime begin, LocalDateTime end) {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String query = "SELECT "
                     + appt_fields + ","
                     + user_fields + ","
                     + cust_fields + ","
                     + addr_fields + ","
                     + city_fields + ","
                     + ctry_fields + " "
                     + "FROM appointment "
                     + "JOIN user ON appointment.userid = user.userid "
                     + "JOIN customer ON appointment.customerid = customer.customerid "
                     + "JOIN address ON customer.addressid = address.addressid "
                     + "JOIN city ON address.cityid = city.cityid "
                     + "JOIN country ON city.countryid = country.countryid "
                     + "WHERE start BETWEEN ? AND ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setTimestamp(1, Timestamp.valueOf(Utils.toUTC(begin)));
            pstmnt.setTimestamp(2, Timestamp.valueOf(Utils.toUTC(end)));

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                appointments.add(this.retrieveAppointment(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return appointments;

    }

    // Rubric I2: Schedule for each Consultant.
    @Override public ObservableList<Appointment> getAppointmentsByUser() {

        ObservableList<Appointment> appointments = FXCollections.observableArrayList();

        String query = "SELECT "
                     + appt_fields + ","
                     + user_fields + ","
                     + cust_fields + ","
                     + addr_fields + ","
                     + city_fields + ","
                     + ctry_fields + " "
                     + "FROM appointment "
                     + "JOIN user ON appointment.userid = user.userid "
                     + "JOIN customer ON appointment.customerid = customer.customerid "
                     + "JOIN address ON customer.addressid = address.addressid "
                     + "JOIN city ON address.cityid = city.cityid "
                     + "JOIN country ON city.countryid = country.countryid "
                     + "ORDER BY AppointmentUserID";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                appointments.add(this.retrieveAppointment(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return appointments;

    }

    // Rubric I3: Additional report - Number of Appointments for each Customer.
    @Override public ObservableList<AppointmentsByCustomerReport> appointmentsByCustomerReport() {

        ObservableList<AppointmentsByCustomerReport> report = FXCollections.observableArrayList();

        String query = "SELECT"
                     + "  COUNT(appointment.appointmentid) AS NumberOfAppointments,"
                     + "  customer.customername AS CustomerName "
                     + "FROM customer "
                     + "LEFT JOIN appointment ON customer.customerid = appointment.customerid "
                     + "GROUP BY customer.customerid;";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                report.add(this.retrieveAppointmentByCustomerReport(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return report;

    }

    // Rubric I1: Number of Appointment Types by each month.
    @Override public ObservableList<AppointmentsByMonthReport> appointmentsByMonthReport() {

        ObservableList<AppointmentsByMonthReport> report = FXCollections.observableArrayList();

        String query = "SELECT"
                     + "  type AS AppointmentType,"
                     + "  COUNT(*) AS NumberOfAppointments,"
                     + "  MONTHNAME(start) AS Month,"
                     + "  YEAR(start) AS Year "
                     + "FROM appointment "
                     + "GROUP BY"
                     + "  AppointmentType,"
                     + "  Month "
                     + "ORDER BY"
                     + "  AppointmentType,"
                     + "  start";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                report.add(this.retrieveAppointmentByMonthReport(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return report;

    }

    @Override public ObservableList<City> getCities(Country country) {

        ObservableList<City> cities = FXCollections.observableArrayList();
        
        String query = "SELECT "
                     + city_fields + ","
                     + ctry_fields + " "
                     + "FROM city "
                     + "JOIN country ON city.countryid = country.countryid "
                     + "WHERE country.countryid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, country.getCountryID());

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                cities.add(this.retrieveCity(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return cities;

    }

    @Override public ObservableList<Country> getAllCountries() {

        ObservableList<Country> countries = FXCollections.observableArrayList();
        
        String query = "SELECT "
                     + ctry_fields + " "
                     + "FROM country";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                countries.add(this.retrieveCountry(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return countries;

    }

    @Override public ObservableList<Customer> getAllCustomers() {
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String query = "SELECT "
                     + cust_fields + ","
                     + addr_fields + ","
                     + city_fields + ","
                     + ctry_fields + " "
                     + "FROM customer "
                     + "JOIN address ON customer.addressid = address.addressid "
                     + "JOIN city ON address.cityid = city.cityid "
                     + "JOIN country ON city.countryid = country.countryid "
                     + "WHERE customer.active IS TRUE";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                customers.add(this.retrieveCustomer(result));

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return customers;

    }

    @Override public City getCity(String cityName) {

        City city = new City();
        String query = "SELECT "
                     + city_fields + ","
                     + ctry_fields + " "
                     + "FROM city "
                     + "JOIN country ON city.countryid = country.countryid "
                     + "WHERE city.city = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, cityName);

            ResultSet result = pstmnt.executeQuery();
            while(result.next()) {

                city = this.retrieveCity(result);
 
            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return city;

    }

    // Rubric F4: Throw error if user cannot be found.
    @Override public User getUser(String username, String pass) throws InvalidUserException {

        User user = new User();
        String query = "SELECT "
                     + user_fields + " "
                     + "FROM user "
                     + "WHERE userName = ? AND password = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, username);
            pstmnt.setString(2, pass);

            ResultSet result = pstmnt.executeQuery();
            if(result.next()) {

                user = this.retrieveUser(result);

            } else {

                throw new InvalidUserException("invalid_message");

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return user;

    }

    // Rubric F3: Return boolean regarding if an appointment time conflicts with another owned by the user.
    @Override public boolean appointmentConflicts(Appointment appointment) {

        boolean conflicts = false;
        String query = "SELECT * "
                     + "FROM appointment "
                     + "WHERE (? BETWEEN start AND end) OR (? BETWEEN start AND end) "
                     + "HAVING appointmentid != ? AND userid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            // Adding a second here to ensure that meeetings can be consecutive:
            // meeting a: 9am-10am, meeting b: 10am-11am, etc.
            pstmnt.setTimestamp(1, Timestamp.valueOf(Utils.toUTC(appointment.getStart().plusSeconds(1))));
            pstmnt.setTimestamp(2, Timestamp.valueOf(Utils.toUTC(appointment.getEnd().plusSeconds(1))));
            pstmnt.setInt(3, appointment.getAppointmentID());
            pstmnt.setInt(4, appointment.getUser().getUserID());

            ResultSet result = pstmnt.executeQuery();
            if(result.next()) {

                conflicts = true;

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return conflicts;

    }

    // Rubric H: Throw an alert if appointment within 15 minutes.
    @Override public boolean isAppointmentSoon(User user) {

        boolean appointmentSoon = false;
        String query = "SELECT * FROM appointment "
                     + "WHERE start BETWEEN ? AND ? AND userid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setTimestamp(1, Timestamp.valueOf(Utils.toUTC(LocalDateTime.now())));
            pstmnt.setTimestamp(2, Timestamp.valueOf(Utils.toUTC(LocalDateTime.now().plusMinutes(15))));
            pstmnt.setInt(3, user.getUserID());

            ResultSet result = pstmnt.executeQuery();
            if(result.next()) {

                appointmentSoon = true;

            }

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        return appointmentSoon;

    }

    @Override public void deleteAppointment(Appointment appointment) {

        String query = "DELETE "
                     + "FROM appointment "
                     + "WHERE appointmentid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, appointment.getAppointmentID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    @Override public void deleteCustomer(Customer customer) {

        this.deleteAppointmentsForCustomer(customer);

        String query = "DELETE "
                     + "FROM customer "
                     + "WHERE customerid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, customer.getCustomerID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

        this.deleteAddress(customer.getAddress());

    }

    // Rubric E: Convert user's local time to UTC and save the appointment to database.
    @Override public void insertAppointment(Appointment appointment) {

        String query = "INSERT INTO appointment "
                     + "  (customerId,userId,title,description,location,contact,type,url,start,end,createDate,createdBy,lastUpdateBy) "
                     + "VALUES"
                     + "  (?,?,?,?,?,?,?,?,?,?,NOW(),?,?)";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, appointment.getCustomer().getCustomerID());
            pstmnt.setInt(2, appointment.getUser().getUserID());
            pstmnt.setString(3, appointment.getTitle());
            pstmnt.setString(4, appointment.getDescription());
            pstmnt.setString(5, appointment.getLocation());
            pstmnt.setString(6, appointment.getContact());
            pstmnt.setString(7, appointment.getType());
            pstmnt.setString(8, appointment.getUrl());
            pstmnt.setTimestamp(9, Timestamp.valueOf(Utils.toUTC(appointment.getStart())));
            pstmnt.setTimestamp(10, Timestamp.valueOf(Utils.toUTC(appointment.getEnd())));
            pstmnt.setString(11, appointment.getCreatedBy());
            pstmnt.setString(12, appointment.getUpdatedBy());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    @Override public void insertCustomer(Customer customer) {

        customer.getAddress().setAddressID(
            this.createAddress(customer.getAddress())
        );

        String query = "INSERT INTO customer "
                     + "  (customerName,addressId,active,createDate,createdBy,lastUpdateBy) "
                     + "VALUES"
                     + "  (?,?,?,NOW(),?,?)";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, customer.getCustomerName());
            pstmnt.setInt(2, customer.getAddress().getAddressID());
            pstmnt.setInt(3, customer.isActive());
            pstmnt.setString(4, customer.getCreatedBy());
            pstmnt.setString(5, customer.getUpdatedBy());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    // Rubric E: Convert user's local time to UTC and save the appointment to database.
    @Override public void updateAppointment(Appointment appointment) {

        String query = "UPDATE appointment SET"
                     + "  customerId=?,"
                     + "  userId=?,"
                     + "  title=?,"
                     + "  description=?,"
                     + "  location=?,"
                     + "  contact=?,"
                     + "  type=?,"
                     + "  url=?,"
                     + "  start=?,"
                     + "  end=?,"
                     + "  lastUpdateBy=? "
                     + "WHERE appointmentid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, appointment.getCustomer().getCustomerID());
            pstmnt.setInt(2, appointment.getUser().getUserID());
            pstmnt.setString(3, appointment.getTitle());
            pstmnt.setString(4, appointment.getDescription());
            pstmnt.setString(5, appointment.getLocation());
            pstmnt.setString(6, appointment.getContact());
            pstmnt.setString(7, appointment.getType());
            pstmnt.setString(8, appointment.getUrl());
            pstmnt.setTimestamp(9, Timestamp.valueOf(Utils.toUTC(appointment.getStart())));
            pstmnt.setTimestamp(10, Timestamp.valueOf(Utils.toUTC(appointment.getEnd())));
            pstmnt.setString(11, appointment.getUpdatedBy());
            pstmnt.setInt(12, appointment.getAppointmentID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }

    @Override public void updateCustomer(Customer customer) {        

        updateAddress(customer.getAddress());

        String query = "UPDATE customer SET"
                     + "  customerName=?,"
                     + "  addressId=?,"
                     + "  active=?,"
                     + "  lastUpdateBy=? "
                     + "WHERE customerid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, customer.getCustomerName());
            pstmnt.setInt(2, customer.getAddress().getAddressID());
            pstmnt.setInt(3, customer.isActive());
            pstmnt.setString(4, customer.getUpdatedBy());
            pstmnt.setInt(5, customer.getCustomerID());
            pstmnt.executeUpdate();

        } catch (SQLException ex) {

             Utils.LOGGER.log(Level.SEVERE, null, ex);

        } finally {

            Database.closeConnection();

        }

    }
 
}
