package personalplanner.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalplanner.Models.Address;
import personalplanner.Models.Appointment;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public class MainDAOImpl implements MainDAO {

    // Used for building select queries, needed to standardize the fieldlist.
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

        } catch (SQLException e) {

            System.out.println("Exception when creating address: " + e);

        } finally {

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

        } catch (SQLException e) {

            System.out.println("Exception when removing address: " + e);

        } finally {

            Database.closeConnection();

        }

    }

    private void deleteAppointmentForCustomer(Customer customer) {

        String query = "DELETE "
                     + "FROM appointment "
                     + "WHERE customerid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            pstmnt.setInt(1, customer.getCustomerID());

            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when removing customer: " + e);

        } finally {

            Database.closeConnection();

        }

    }

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
        appointment.setStart(this.toLocalTimeZone(result.getTimestamp("AppointmentStart").toLocalDateTime()));
        appointment.setEnd(this.toLocalTimeZone(result.getTimestamp("AppointmentEnd").toLocalDateTime()));
        appointment.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("AppointmentCreateDate").toLocalDateTime()));
        appointment.setCreatedBy(result.getString("AppointmentCreatedBy"));
        appointment.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("AppointmentLastUpdate").toLocalDateTime()));
        appointment.setUpdatedBy(result.getString("AppointmentLastUpdateBy"));

        return appointment;

    }

    private Address retrieveAddress(ResultSet result) throws SQLException {

        Address address = new Address();

        address.setAddressID(result.getInt("AddressAddressID"));
        address.setAddress(result.getString("AddressAddress"));
        address.setAddress2(result.getString("AddressAddress2"));

        address.setCity(this.retrieveCity(result));

        address.setZip(result.getString("AddressPostalCode"));
        address.setPhone(result.getString("AddressPhone"));
        address.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("AddressCreateDate").toLocalDateTime()));
        address.setCreatedBy(result.getString("AddressCreatedBy"));
        address.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("AddressLastUpdate").toLocalDateTime()));
        address.setUpdatedBy(result.getString("AddressLastUpdateBy"));

        return address;

    }

    private City retrieveCity(ResultSet result) throws SQLException {

        City city = new City();

        city.setCityID(result.getInt("CityCityID"));
        city.setCityName(result.getString("CityCity"));

        city.setCountry(this.retrieveCountry(result));

        city.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("CityCreateDate").toLocalDateTime()));
        city.setCreatedBy(result.getString("CityCreatedBy"));
        city.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("CityLastUpdate").toLocalDateTime()));
        city.setUpdatedBy(result.getString("CityLastUpdateBy"));

        return city;

    }

    private Country retrieveCountry(ResultSet result) throws SQLException {

        Country country = new Country();

        country.setCountryID(result.getInt("CountryCountryID"));
        country.setCountryName(result.getString("CountryCountry"));
        country.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("CountryCreateDate").toLocalDateTime()));
        country.setCreatedBy(result.getString("CountryCreatedBy"));
        country.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("CountryLastUpdate").toLocalDateTime()));
        country.setUpdatedBy(result.getString("CountryLastUpdateBy"));

        return country;
    }

    private Customer retrieveCustomer(ResultSet result) throws SQLException {

        Customer customer = new Customer();

        customer.setCustomerID(result.getInt("CustomerCustomerID"));
        customer.setCustomerName(result.getString("CustomerName"));

        customer.setAddress(this.retrieveAddress(result));

        customer.setActive(result.getInt("CustomerActive"));
        customer.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("CustomerCreateDate").toLocalDateTime()));
        customer.setCreatedBy(result.getString("CustomerCreatedBy"));
        customer.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("CustomerLastUpdate").toLocalDateTime()));
        customer.setUpdatedBy(result.getString("CustomerLastUpdateBy"));

        return customer;

    }

    private User retrieveUser(ResultSet result) throws SQLException {

        User user = new User();

        user.setUserID(result.getInt("UserUserID"));
        user.setUserName(result.getString("UserName"));
        user.setPassword(result.getString("UserPassword"));
        user.setActive(result.getInt("UserActive"));
        user.setCreatedBy(result.getString("UserCreateBy"));
        user.setCreatedAt(this.toLocalTimeZone(result.getTimestamp("UserCreateDate").toLocalDateTime()));
        user.setUpdatedBy(result.getString("UserLastUpdatedBy"));
        user.setUpdatedAt(this.toLocalTimeZone(result.getTimestamp("UserLastUpdate").toLocalDateTime()));

        return user;

    }

    private LocalDateTime toLocalTimeZone(LocalDateTime dateTime) {

        ZonedDateTime utcZoned = dateTime.atZone(ZoneId.of("UTC"));
        LocalDateTime localZoned = utcZoned.withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime();

        return localZoned;

    }

    private LocalDateTime toUTC(LocalDateTime dateTime) {

        ZonedDateTime localZoned = dateTime.atZone(ZoneId.systemDefault());
        LocalDateTime utcZoned = localZoned.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();

        return utcZoned;

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

    @Override public ObservableList<Appointment> getAppointmentsByMonth(LocalDateTime date) {

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
                     + "WHERE MONTH(appointment.start) = MONTH(?) AND YEAR(appointment.start) = YEAR(?)";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            pstmnt.setTimestamp(1, Timestamp.valueOf(this.toUTC(date)));
            pstmnt.setTimestamp(2, Timestamp.valueOf(this.toUTC(date)));

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                appointments.add(this.retrieveAppointment(result));

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving appointments by month: " + e);

        } finally {

            Database.closeConnection();

        }

        return appointments;

    }

    @Override public ObservableList<Appointment> getAppointmentsByWeek(LocalDateTime date) {

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
                     + "WHERE WEEK(appointment.start) = WEEK(?) AND YEAR(appointment.start) = YEAR(?)";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            pstmnt.setTimestamp(1, Timestamp.valueOf(this.toUTC(date)));
            pstmnt.setTimestamp(2, Timestamp.valueOf(this.toUTC(date)));

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                appointments.add(this.retrieveAppointment(result));

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving appointments by week: " + e);

        } finally {

            Database.closeConnection();

        }

        return appointments;

    }

    @Override public void deleteAppointment(Appointment appointment) {

        String query = "DELETE "
                     + "FROM appointment "
                     + "WHERE appointmentid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, appointment.getAppointmentID());
            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when removing appointment: " + e);

        } finally {

            Database.closeConnection();

        }

    }

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
            pstmnt.setTimestamp(9, Timestamp.valueOf(this.toUTC(appointment.getStart())));
            pstmnt.setTimestamp(10, Timestamp.valueOf(this.toUTC(appointment.getEnd())));
            pstmnt.setString(11, appointment.getCreatedBy());
            pstmnt.setString(12, appointment.getUpdatedBy());

            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when creating appointment: " + e);

        } finally {

            Database.closeConnection();

        }

    }

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
            pstmnt.setTimestamp(9, Timestamp.valueOf(this.toUTC(appointment.getStart())));
            pstmnt.setTimestamp(10, Timestamp.valueOf(this.toUTC(appointment.getEnd())));
            pstmnt.setString(11, appointment.getUpdatedBy());
            pstmnt.setInt(12, appointment.getAppointmentID());

            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when updating appointment: " + e);

        } finally {

            Database.closeConnection();

        }

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

        } catch (SQLException e) {

            System.out.println("Exception when retrieving all cities: " + e);

        } finally {

            Database.closeConnection();

        }

        return cities;

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

        } catch (SQLException e) {

            System.out.println("Exception when retrieving city: " + e);

        } finally {

            Database.closeConnection();

        }

        return city;

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

        } catch (SQLException e) {

            System.out.println("Exception when retrieving all countries: " + e);

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

        } catch (SQLException e) {

            System.out.println("Exception when retrieving all customers: " + e);

        } finally {

            Database.closeConnection();

        }

        return customers;

    }

    @Override public void deleteCustomer(Customer customer) {

        this.deleteAppointmentForCustomer(customer);

        String query = "DELETE "
                     + "FROM customer "
                     + "WHERE customerid = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setInt(1, customer.getCustomerID());
            pstmnt.executeUpdate();

        } catch (SQLException e) {

            System.out.println("Exception when removing customer: " + e);

        } finally {

            Database.closeConnection();

        }

        this.deleteAddress(customer.getAddress());

    }

    @Override public void insertCustomer(Customer customer) {

        customer.getAddress().setAddressID(
            createAddress(customer.getAddress())
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

        } catch (SQLException e) {

            System.out.println("Exception when creating customer: " + e);

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

        } catch (SQLException e) {

            System.out.println("Exception when updating customer: " + e);

        } finally {

            Database.closeConnection();

        }

    }

    @Override public User getUser(String username, String pass) {

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

            while(result.next()) {

                user = this.retrieveUser(result);

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving user: " + e);

        } finally {

            Database.closeConnection();

        }

        return user;

    }
 
}
