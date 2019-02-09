package personalplanner.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    static Connection conn;

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

    public static void Connect() throws SQLException  {

        conn = DriverManager.getConnection(URL);
        System.out.println("Connected to database : " + DATABASE);

    }

    public static void Close() throws SQLException {

        conn.close();
        System.out.println("Closed connection to database : " + DATABASE);

    }

}
