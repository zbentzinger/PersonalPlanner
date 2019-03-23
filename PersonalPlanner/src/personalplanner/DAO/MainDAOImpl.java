package personalplanner.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import personalplanner.Models.Address;
import personalplanner.Models.Appointment;
import personalplanner.Models.City;
import personalplanner.Models.Country;
import personalplanner.Models.Customer;
import personalplanner.Models.User;

public class MainDAOImpl implements MainDAO {

    @Override public ObservableList<Appointment> getAllAppointments() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public ObservableList<Country> getAllCountries() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override public ObservableList<Customer> getAllCustomers() {
        
        ObservableList<Customer> customers = FXCollections.observableArrayList();

        String allCustomers = "SELECT "
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

            PreparedStatement pstmnt = Database.getConnection().prepareStatement(allCustomers);
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

    @Override public ObservableList<City> getCities(String country) {
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

    }

    @Override public void insertCustomer(Customer customer) {
        throw new UnsupportedOperationException("Not supported yet.");
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
