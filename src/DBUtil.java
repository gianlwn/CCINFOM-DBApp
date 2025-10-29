import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/dbapp_draft";
        String user = "gian";
        String password = "0000";
        return DriverManager.getConnection(url, user, password);
    }
}
