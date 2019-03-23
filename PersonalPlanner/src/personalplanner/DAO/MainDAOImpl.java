package personalplanner.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalplanner.Models.Address;
import personalplanner.Models.Appointment;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public class MainDAOImpl implements MainDAO {

    private int createAddress(Address address) {

        int addressID = -1;

        String query = "INSERT INTO address (address,address2,cityId,postalCode,phone,createDate,createdBy,lastUpdateBy) "
                     + "VALUES (?,?,?,?,?,NOW(),?,?)";

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

    @Override public ObservableList<Appointment> getAllAppointments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public void deleteAppointment(Appointment appointment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public void insertAppointment(Appointment appointment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public void updateAppointment(Appointment appointment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public ObservableList<String> getCities(String country) {

        ObservableList<String> cities = FXCollections.observableArrayList();
        
        String query = "SELECT c.city "
                     + "FROM city c "
                     + "  JOIN country cc ON c.countryid = cc.countryid "
                     + "WHERE cc.country = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            pstmnt.setString(1, country);

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                cities.add(result.getString("city"));

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving all countries: " + e);

        } finally {

            Database.closeConnection();

        }

        return cities;

    }

    @Override public City getCity(String name) {

        City city = new City();

        String query = "SELECT"
                     + "  c.cityid AS cityID,"
                     + "  c.city AS city,"
                     + "  c.createDate AS cityCreateDate,"
                     + "  c.createdBy AS cityCreateBy,"
                     + "  c.lastUpdate AS cityUpdateDate,"
                     + "  c.lastUpdateBy cityUpdateBy,"
                     + "  cc.countryid AS countryID,"
                     + "  cc.country AS country,"
                     + "  cc.createDate AS countryCreateDate,"
                     + "  cc.createdBy AS countryCreateBy,"
                     + "  cc.lastUpdate AS countryUpdateDate,"
                     + "  cc.lastUpdateBy AS countryUpdateBy "
                     + "FROM city c"
                     + "  JOIN country cc ON c.countryid = cc.countryid "
                     + "WHERE c.city = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            pstmnt.setString(1, name);

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                Country country = new Country();
                country.setCountryID(result.getInt("countryID"));
                country.setCountryName(result.getString("country"));
                country.setCreatedAt(result.getTimestamp("countryCreateDate").toLocalDateTime());
                country.setCreatedBy(result.getString("countryCreateBy"));
                country.setUpdatedAt(result.getTimestamp("countryUpdateDate").toLocalDateTime());
                country.setUpdatedBy(result.getString("countryUpdateBy"));
                
                city.setCityID(result.getInt("cityID"));
                city.setCityName(result.getString("city"));
                city.setCountry(country);
                city.setCreatedAt(result.getTimestamp("cityCreateDate").toLocalDateTime());
                city.setCreatedBy(result.getString("cityCreateBy"));
                city.setUpdatedAt(result.getTimestamp("cityUpdateDate").toLocalDateTime());
                city.setUpdatedBy(result.getString("cityUpdateBy"));
 
            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving city: " + e);

        } finally {

            Database.closeConnection();

        }

        return city;

    }

    @Override public ObservableList<String> getAllCountries() {

        ObservableList<String> countries = FXCollections.observableArrayList();
        
        String query = "SELECT * "
                     + "FROM country";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                countries.add(result.getString("country"));

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

        String query = "SELECT"
                     + "  c.customerid AS custID,"
                     + "  c.customerName AS cust,"
                     + "  c.active AS custActive,"
                     + "  c.createDate AS custCreateDate,"
                     + "  c.createdBy AS custCreateBy,"
                     + "  c.lastUpdate AS custUpdateDate,"
                     + "  c.lastUpdateBy AS custUpdateBy,"
                     + "  a.addressid AS addrID,"
                     + "  a.address AS addr,"
                     + "  a.address2 AS addr2,"
                     + "  a.postalCode AS addrZip,"
                     + "  a.phone AS addrPhone,"
                     + "  a.createDate AS addrCreateDate,"
                     + "  a.createdBy AS addrCreateBy,"
                     + "  a.lastUpdate AS addrUpdateDate,"
                     + "  a.lastUpdateBy AS addrUpdateBy,"
                     + "  cc.cityid AS cityID,"
                     + "  cc.city AS city,"
                     + "  cc.createDate AS cityCreateDate,"
                     + "  cc.createdBy AS cityCreateBy,"
                     + "  cc.lastUpdate AS cityUpdateDate,"
                     + "  cc.lastUpdateBy AS cityUpdateBy,"
                     + "  ccc.countryid AS countryID,"
                     + "  ccc.country AS country,"
                     + "  ccc.createDate AS countryCreateDate,"
                     + "  ccc.createdBy AS countryCreateBy,"
                     + "  ccc.lastUpdate AS countryUpdateDate,"
                     + "  ccc.lastUpdateBy AS countryUpdateBy "
                     + "FROM customer c"
                     + "  JOIN address a ON c.addressid = a.addressid"
                     + "  JOIN city cc ON a.cityid=cc.cityid"
                     + "  JOIN country ccc ON cc.countryid=ccc.countryid "
                     + "WHERE c.active IS TRUE";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                Country country = new Country();
                country.setCountryID(result.getInt("countryID"));
                country.setCountryName(result.getString("country"));
                country.setCreatedAt(result.getTimestamp("countryCreateDate").toLocalDateTime());
                country.setCreatedBy(result.getString("countryCreateBy"));
                country.setUpdatedAt(result.getTimestamp("countryUpdateDate").toLocalDateTime());
                country.setUpdatedBy(result.getString("countryUpdateBy"));

                City city = new City();
                city.setCityID(result.getInt("cityID"));
                city.setCityName(result.getString("city"));
                city.setCountry(country);
                city.setCreatedAt(result.getTimestamp("cityCreateDate").toLocalDateTime());
                city.setCreatedBy(result.getString("cityCreateBy"));
                city.setUpdatedAt(result.getTimestamp("cityUpdateDate").toLocalDateTime());
                city.setUpdatedBy(result.getString("cityUpdateBy"));

                Address address = new Address();
                address.setAddressID(result.getInt("addrID"));
                address.setAddress(result.getString("addr"));
                address.setAddress2(result.getString("addr2"));
                address.setCity(city);
                address.setZip(result.getString("addrZip"));
                address.setPhone(result.getString("addrPhone"));
                address.setCreatedAt(result.getTimestamp("addrCreateDate").toLocalDateTime());
                address.setCreatedBy(result.getString("addrCreateBy"));
                address.setUpdatedAt(result.getTimestamp("addrUpdateDate").toLocalDateTime());
                address.setUpdatedBy(result.getString("addrUpdateBy"));

                Customer customer = new Customer();
                customer.setCustomerID(result.getInt("custID"));
                customer.setCustomerName(result.getString("cust"));
                customer.setAddress(address);
                customer.setActive(result.getInt("custActive"));
                customer.setCreatedAt(result.getTimestamp("custCreateDate").toLocalDateTime());
                customer.setCreatedBy(result.getString("custCreateBy"));
                customer.setUpdatedAt(result.getTimestamp("custUpdateDate").toLocalDateTime());
                customer.setUpdatedBy(result.getString("custUpdateBy"));

                customers.add(customer);

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving all customers: " + e);

        } finally {

            Database.closeConnection();

        }

        return customers;

    }

    @Override public void deleteCustomer(Customer customer) {

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

        deleteAddress(customer.getAddress());

    }

    @Override public void insertCustomer(Customer customer) {

        customer.getAddress().setAddressID(
            createAddress(customer.getAddress())
        );

        String query = "INSERT INTO customer (customerName,addressId,active,createDate,createdBy,lastUpdateBy) "
                     + "VALUES (?,?,?,NOW(),?,?)";

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public User getUser(String username, String pass) {

        User user = new User();

        String query = "SELECT * " 
                     + "FROM user " 
                     + "WHERE userName = ? AND password = ?";

        try {

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(query);
            pstmnt.setString(1, username);
            pstmnt.setString(2, pass);

            ResultSet result = pstmnt.executeQuery();

            while(result.next()) {

                user.setUserID(result.getInt("userid"));
                user.setUserName(result.getString("userName"));
                user.setPassword(result.getString("password"));
                user.setActive(result.getInt("active"));
                user.setCreatedBy(result.getString("createBy"));
                user.setCreatedAt(result.getTimestamp("createDate").toLocalDateTime());
                user.setUpdatedBy(result.getString("lastUpdatedBy"));
                user.setUpdatedAt(result.getTimestamp("lastUpdate").toLocalDateTime());

            }

        } catch (SQLException e) {

            System.out.println("Exception when retrieving user: " + e);

        } finally {

            Database.closeConnection();

        }

        return user;

    }
 
}
