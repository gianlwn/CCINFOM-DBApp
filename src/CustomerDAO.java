import java.sql.*;
import java.util.ArrayList;

/**
 * CustomerDAO is a Data Access Object (DAO) class responsible for handling
 * all database operations related to the Customer entity.
 * 
 * <p>
 * This class allows you to:
 * <ul>
 *      <li>Add a new customer to the database</li>
 *      <li>Retrieve all customers from the database</li>
 * </ul>
 * It uses JDBC (Java Database Connectivity) to communicate with the database.
 */
public class CustomerDAO {

    /**
     * Adds a new customer to the database.
     * 
     * <p>
     * This method validates the customer's first and last name before
     * inserting the record. Optional fields (contact number and email)
     * can be null or empty.
     * <p>
     * After insertion, the generated customer ID from the database
     * is set to the {@link Customer} object.
     * 
     * @param customer the Customer object containing the information to inser
     * @return  {@code true} if the customer was successfully added,
     *          {@code false} otherwise (e.g., invalid data or SQL error)
     */
    public boolean addCustomer(Customer customer) {

        // Validate first name and last name; they cannot be null or empty
        if (customer.getFirstName() == null || customer.getFirstName().isBlank() ||
            customer.getLastName() == null || customer.getLastName().isBlank()) {
            System.err.println("First name and last name cannot be null or empty.");
            return false; // Stop the method if validation fails
        }

        // SQL query with placeholders (?) for prepared statement
        String sql = "INSERT INTO customers (first_name, last_name, contact_number, email) VALUES (?, ?, ?, ?)";
        
        // Try-with-resources ensures the database connection is automatically closed
        try (Connection conn = DBUtil.getConnection(); // Get a connection to the database
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Set values for the placeholders in the SQL query
            stmt.setString(1, customer.getFirstName());     // 1st ? = first name
            stmt.setString(2, customer.getLastName());      // 2nd ? = last name

            // Check if contact number is empty; if yes, store NULL in database
            if (customer.getContactNumber() == null || customer.getContactNumber().isBlank())
                stmt.setNull(3, Types.VARCHAR); 
            else
                stmt.setString(3, customer.getContactNumber()); // 3rd ? = contact number

            // Check if email is empty; if yes, store NULL in database
            if (customer.getEmail() == null || customer.getEmail().isBlank())
                stmt.setNull(4, Types.VARCHAR);
            else
                stmt.setString(4, customer.getEmail()); // 4th ? = email

            // Execute the SQL query; returns the number of affected rows
            int rows = stmt.executeUpdate();

            // Retrieve the auto-generated customer ID from the database
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {  // If there is a generated ID
                    customer.setCustomerId(rs.getInt(1)); // Set it to the Customer object
                }
            }

            // Return true if at least one row was inserted
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace(); // Print any SQL errors
        }

        return false; // Return false if insertion failed
    }

    /**
     * Retrieves all customers from the database.
     * <p>
     * Each row in the "customers" table is converted into a {@link Customer} object.
     * The results are ordered by customer ID.
     * 
     * @return an {@link ArrayList} containing all Customer objects in the database;
     *         returns an empty list if no customers are found or an error occurs.
     */
    public ArrayList<Customer> getAllCustomers() {
        ArrayList<Customer> customers = new ArrayList<>(); // List to store all customers

        // SQL query to select all customers, ordered by ID
        String sql = "SELECT customer_id, first_name, last_name, contact_number, email FROM customers ORDER BY customer_id";
        
        try (Connection conn = DBUtil.getConnection(); // Connect to the database
             PreparedStatement stmt = conn.prepareStatement(sql); // Prepare the SQL quert
             ResultSet rs = stmt.executeQuery()) { // Execute the query and get results
            
            // Loop through each row in the result set
            while (rs.next()) {
                Customer cust = new Customer(); // Create a new Customer object
                cust.setCustomerId(rs.getInt("customer_id")); // set customer ID
                cust.setFirstName(rs.getString("first_name")); // set first name
                cust.setLastName(rs.getString("last_name")); // set last name
                cust.setContactNumber(rs.getString("contact_number")); // set contact number
                cust.setEmail(rs.getString("email")); // set email
                customers.add(cust); // add the customer object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // print SQL errors if any
        }
        
        return customers; // Return the list of customers
    }
}
