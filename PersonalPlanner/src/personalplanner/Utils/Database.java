package personalplanner.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
            "jdbc:%s://%s:%s@%s:%s/%s",
            PROTOCOL, USERNAME, PASSWORD, HOSTNAME, PORT, DATABASE
    );

    public static Connection getConnection() throws SQLException {

        CONN = DriverManager.getConnection(URL);

        return CONN;

    }

}
