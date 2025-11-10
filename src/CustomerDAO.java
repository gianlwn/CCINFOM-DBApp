import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {
    public boolean insertCustomer(Customer customer) {
        String sql = "INSERT INTO customers (customer_id, first_name, last_name, contact_number, email) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.setString(2, customer.getFirstName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getContactNumber());
            stmt.setString(5, customer.getEmail());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Customer getCustomerById(int id) {
        String sql = "SELECT * FROM customers WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setContactNumber(rs.getString("contact_number"));
                c.setEmail(rs.getString("email"));

                return c;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateCustomer(Customer customer) {
        String sql = "UPDATE customers SET first_name=?, last_name=?, contact_number=?, email=? WHERE customer_id=?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());
            stmt.setString(3, customer.getContactNumber());
            stmt.setString(4, customer.getEmail());
            stmt.setInt(5, customer.getCustomerId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteCustomer(int id) {
        String sql = "DELETE FROM customers WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public ArrayList<Customer> getAllCustomersWithCountry() {
        ArrayList<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, contact_number, email FROM customers ORDER BY customer_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Customer cust = new Customer();
                cust.setCustomerId(rs.getInt("customer_id"));
                cust.setFirstName(rs.getString("first_name"));
                cust.setLastName(rs.getString("last_name"));
                cust.setContactNumber(rs.getString("contact_number"));
                cust.setEmail(rs.getString("email"));
                customers.add(cust);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return customers;
    }
}
