import java.sql.*;
import java.util.ArrayList;

public class CustomerDAO {
    public boolean addCustomer(Customer customer) {
        String sql = "INSERT INTO customers (first_name, last_name, contact_number, email) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getLastName());

            if (customer.getContactNumber() == null || customer.getContactNumber().isBlank())
                stmt.setNull(3, Types.VARCHAR);
            else
                stmt.setString(3, customer.getContactNumber());

            if (customer.getEmail() == null || customer.getEmail().isBlank())
                stmt.setNull(4, Types.VARCHAR);
            else
                stmt.setString(4, customer.getEmail());

            int rows = stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    customer.setCustomerId(rs.getInt(1));
                }
            }

            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
    
    public Customer getCustomerById(int id) {
        String sql = "SELECT customer_id, first_name, last_name, contact_number, email FROM customers WHERE customer_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                Customer c = new Customer();
                c.setCustomerId(rs.getInt("customer_id"));
                c.setFirstName(rs.getString("first_name"));
                c.setLastName(rs.getString("last_name"));
                c.setContactNumber(rs.getString("contact_number"));
                c.setEmail(rs.getString("email"));

                return c;
                }
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
            
            if (customer.getContactNumber() == null || customer.getContactNumber().isBlank()) 
                stmt.setNull(3, Types.VARCHAR);
            else
                stmt.setString(3, customer.getContactNumber());
        
            if (customer.getEmail() == null || customer.getEmail().isBlank())
                stmt.setNull(4, Types.VARCHAR);
            else
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

    public ArrayList<Customer> getAllCustomers() {
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
