package personalplanner.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    public static void GetConnection() throws ClassNotFoundException  {
    
        Connection conn = null;

        String driver = "com.mysql.cj.jdbc.Driver";
        
        String protocol = "mysql";
        String user = "";
        String pass = "";
        String host = "";
        String db = "";

        // Example: jdbc:mysql://user:pass@host/database
        String url = String.format(
                "jdbc:%s://%s:%s@%s/%s",
                protocol, user, pass, host, db
        );
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url);
            System.out.println("Connected to database : " + db);
        } catch (SQLException e) {
            System.out.println("SQLException: "+e.getMessage());
            System.out.println("SQLState: "+e.getSQLState());
            System.out.println("VendorError: "+e.getErrorCode());
        }
        
    }
    
    
}
