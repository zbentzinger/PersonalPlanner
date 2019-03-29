package personalplanner.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    
    private static Connection CONN;

    private static final String PROTOCOL = "mysql";
    private static final String USERNAME = "U04Szi";
    private static final String PASSWORD = "53688332123";
    private static final String HOSTNAME = "52.206.157.109";
    private static final String PORT = "3306";
    private static final String DATABASE = "U04Szi";

    // Example: jdbc:mysql://user:pass@host:port/database
    private static String URL = String.format(
        "jdbc:%s://%s:%s/%s",
        PROTOCOL, HOSTNAME, PORT, DATABASE
    );

    public static Connection getConnection() {

        try {

            Class.forName("com.mysql.jdbc.Driver");
            CONN = DriverManager.getConnection(URL, USERNAME, PASSWORD);

        } catch (SQLException | ClassNotFoundException ex) {

            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }

        return CONN;

    }

    public static void closeConnection() {

        try {

            CONN.close();

        } catch (SQLException ex) {

            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

}
