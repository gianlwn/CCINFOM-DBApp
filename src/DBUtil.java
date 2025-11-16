import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBUtil is a utility class responsbile for creating and returning
 * a connection the the MySQL database used by the application.
 * 
 * <p>This class centralizes the database connection details
 * (URL, username, password) so that other classes such as DAOs
 * do not need to rewrite the same class connection code.</p>
 * 
 * <p>Any class that needs to interact with the database simply calls:
 * <br> <code>Connection conn = DBUtil.getConnection();</code>
 * </p>
 */

public class DBUtil {

    /**
     * Establishes and returns a connection to the MySQL database.
     * 
     * @return a Connection object that allows Java to communciate with MySQL
     * @throws SQLException if the database cannot be reached or login fails
     */
    public static Connection getConnection() throws SQLException {
        
        // URL of the database:
        String url = "jdbc:mysql://localhost:3306/dbapp";
        
        // MySQL username
        String user = "gian";

        // MySQL password
        String password = "0000";

        // DriverManager opens a connection using the given details
        return DriverManager.getConnection(url, user, password);
    }
}
