package personalplanner.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    static Connection conn;
    static Statement stmt;
    static ResultSet result;

    private static final String PROTOCOL = "mysql";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String HOSTNAME = "";
    private static final String PORT = "3306";
    private static final String DATABASE = "";

    // Example: jdbc:mysql://user:pass@host:port/database
    private static final String URL = String.format(
            "jdbc:%s://%s:%s@%s:%s/%s",
            PROTOCOL, USERNAME, PASSWORD, HOSTNAME, PORT, DATABASE
    );

    public static void Connect()  {

        try {
            conn = DriverManager.getConnection(URL);

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        System.out.println("Connected to database: " + DATABASE);

    }

    public static void Close() {

        try {
            conn.close();

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        System.out.println("Closed connection to database : " + DATABASE);

    }

    public static ResultSet Select(String query) {

        try {
            stmt = conn.createStatement();
            result = stmt.executeQuery(query);

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

        return result;

    }

    public static void Update(String query) {

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(query);

        } catch (SQLException e) {
            System.out.println("Exception: " + e);

        }

    }

}
