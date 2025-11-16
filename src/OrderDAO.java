import java.sql.*;
import java.util.ArrayList;

/**
 * OrderDAO is a Data Access Object (DAO) class responsible for handling
 * all database operations related to orders.
 * 
 * <p>
 * This class interacts with the "orders" table in the database.
 * It allows you to:
 * <ul>
 *     <li>Create a new order</li>
 *     <li>Retrieve an order by ID</li>
 *     <li>Get the product sold in a specific order</li>
 *     <li>Edit the status of an order</li>
 *     <li>Retrieve all orders</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This class uses JDBC (Java Database Connectivity) to communicate with the database
 * and depends on {@link ProductDAO} and {@link CustomerDAO} to manage related entities.
 * </p>
 */
public class OrderDAO {
    private final ProductDAO productDAO;    // To interact with products in the database
    private final CustomerDAO customerDAO;  // To interact with customers in the database

    /**
     * Constructor that initializes OrderDAO with dependencies.
     * 
     * @param productDAO    ProductDAO object for product-related database operations
     * @param customerDAO   CustomerDAO object for customer-related database operations
     */
    public OrderDAO(ProductDAO productDAO, CustomerDAO customerDAO) {
        this.productDAO = productDAO;
        this.customerDAO = customerDAO;
    }

    /**
     * Creates a new order in the database.
     * <p>
     * This method first validates the product ID and quantity, adds the customer if new,
     * inserts the order into the "orders" table, and updates the product stock.
     * </p>
     * 
     * @param customer  The customer placing the order
     * @param orders    The Order object containing order details
     * @return {@code true} if the order was successfully created, {@code false} otherwise
     */
    public boolean createOrder(Customer customer, Order orders) {
        
        // Check if the product ID is in the valid range (3000 - 4000)
        if (orders.getProductBought().getProductId() <= 3000 || orders.getProductBought().getProductId() >= 4000) {
            System.err.println("Enter a valid Product ID"); // Print error message
            return false; // stop the method if the product ID is invalid
        }

        // Check if quantity is positive and does not exceed stock
        if (orders.getQuantity() <= 0 || orders.getProductBought().getQuantityInStock() <= orders.getQuantity()) {
            System.err.println("Select valid quantity in the stock."); // Print error
            return false; // Stop the method if the quantity is invalid
        }

        // Add customer to database; only proceed if successful
        if (customerDAO.addCustomer(customer)) { // Returns true if customer was successfully added
            
            // SQL query to insert new order, using placeholders for PreparedStatement
            String sql = "INSERT INTO orders (customer_id, product_id, quantity, total, order_date, status) VALUES (?, ?, ?, ?, ?, ?)";
            
            // Open DB connection and prepare SQL statement (auto-close resources)
            try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                // Set values for each placeholder in the SQL statement
                stmt.setInt(1, customer.getCustomerId());               // Customer ID
                stmt.setInt(2, orders.getProductId());                  // Product ID
                stmt.setInt(3, orders.getQuantity());                   // Quantity ordered
                stmt.setDouble(4, orders.getTotal());                   // Total price for this order
                stmt.setDate(5, Date.valueOf(orders.getOrderDate()));   // Order date (converted from LocalDate to SQL Date)
                stmt.setString(6, orders.getStatus().name());           // Order status (e.g., COMPLETED)

                // Execute insert; returns number of rows affected
                int rows = stmt.executeUpdate(); 

                // Get the auto-generated order ID from DB if insert was successful
                if (rows > 0) {
                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            orders.setOrderId(keys.getInt(1)); // Set generated order ID in Order object
                        }
                    }
                }

                // Update product stock after order: subtract ordered quantity
                productDAO.updateProductStock(orders.getProductBought(), orders.getProductBought().getQuantityInStock() - orders.getQuantity());
                return true; // Return true if order creation succeeded
            } catch (SQLException e) {
                e.printStackTrace(); // Print exception if something goes wrong
            }

        } else {
            System.out.println("Failed to create order, enter valid fields for the customer."); // Customer not added
            return false;
        }

        return false; // Default return false if order creation fails
    }

    /**
     * Retrieves a specific order from the database by its ID.
     * 
     * @param oid The order ID
     * @return The Order object if found; null otherwise
     */
    public Order getOrderById(int oid) {
        // SQL query to select a specific order by its ID
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        // Try-with-resources to open connection and prepare statement (auto-closes resources)
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, oid); // Set the placeholder (?) with the order ID
            
            // Execute the query and get the result set
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) { // If a row is returned
                    Order order = new Order(); // Create a new Order object
                    order.setOrderId(rs.getInt("order_id"));            // Set order ID
                    order.setCustomerId(rs.getInt("customer_id"));      // Set customer ID
                    order.setProductId(rs.getInt("product_id"));        // Set product ID
                    order.setQuantity(rs.getInt("quantity"));           // Set quantity
                    order.setTotal(rs.getDouble("total"));              // Set total price
                    order.setOrderDate(rs.getDate("order_date").toLocalDate());     // Convert SQL Date to LocalDate
                    order.setStatus(Order.Status.valueOf(rs.getString("status")));  // Convert String to Enum

                    return order; // Return the filled Order object
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL exception if something goes wrong
        }

        return null; // Return null if no order found or an exception occurred
    }

    /**
     * Retrieves the Product sold in a specific order by order ID.
     *
     * @param oid The order ID
     * @return The Product object if found; null otherwise
     */
    public Product getProductSoldInOrderById(int oid) {

       // SQL query to get the product details of an order using LEFT JOIN
        String sql = "SELECT p.* FROM orders o LEFT JOIN products p ON p.product_id = o.product_id WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, oid); // Set the order ID placeholder
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) { // If a product row is returned
                    Product product = new Product(); // Create new Product object
                    product.setProductId(rs.getInt("product_id"));              // Set product ID
                    product.setProductName(rs.getString("product_name"));       // Set name
                    product.setCategoryId(rs.getInt("category_id"));            // Set category ID
                    product.setPrice(rs.getDouble("price"));                    // Set price
                    product.setQuantityInStock(rs.getInt("quantity_in_stock")); // Set stock
                    product.setSupplierId(rs.getInt("supplier_id"));            // Set supplier ID

                    return product; // Return the Product object
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception if query fails
        }

        return null; // Return null if no product found or error
    }

    /**
     * Updates the status of an order in the database.
     *
     * @param order The Order object containing the updated status and ID
     */
    public void editOrderStatus(Order order) {
       // SQL query to update the status of a specific order
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus().name()); // Set status as String (Enum name)
            stmt.setInt(2, order.getOrderId()); // Set order ID
                
            stmt.executeUpdate(); // Execute the update query
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception if update fails
        }
    }

    /**
     * Retrieves all orders from the database.
     *
     * @return An ArrayList of Order objects; empty list if no orders are found
     */
    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>(); // Create list to store orders
        
        // SQL query to select all orders, ordered by order_id
        String sql = "SELECT * FROM orders ORDER BY order_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) { // Execute query and get results
            
            while (rs.next()) { // Loop through each row in the result set
                Order order = new Order(); // Create new Order object
                order.setOrderId(rs.getInt("order_id"));            // Set order ID
                order.setCustomerId(rs.getInt("customer_id"));      // Set customer ID
                order.setProductId(rs.getInt("product_id"));        // Set product ID
                order.setQuantity(rs.getInt("quantity"));           // Set quantity
                order.setTotal((rs.getDouble("total")));            // Set total
                order.setOrderDate(rs.getDate("order_date").toLocalDate());     // Convert SQL Date to LocalDate
                order.setStatus(Order.Status.valueOf(rs.getString("status")));  // Convert status string to Enum
                orders.add(order); // Add the Order object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception if query fails
        }
        
        return orders; // Return the list of orders
    }

   /**
     * Retrieves all orders from the database that have been refunded.
     * 
     * @return An {@link ArrayList} of {@link Order} objects representing refunded orders; 
     *         empty list if no refunded orders exist
     */
    public ArrayList<Order> getAllRefundedOrders() {
        ArrayList<Order> orders = new ArrayList<>(); // Create a list to store refunded orders
        
        // SQL query to select all orders with status "REFUNDED", ordered by order_id
        String sql = "SELECT * FROM orders WHERE status = 'REFUNDED' ORDER BY order_id";
        
        // Open database connection, prepare the statement, and execute the query
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            // Loop through each row in the result set
            while (rs.next()) { // Create a new Order object
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));        // Set order ID from DB
                order.setCustomerId(rs.getInt("customer_id"));  // Set customer ID
                order.setProductId(rs.getInt("product_id"));    // Set product ID
                order.setQuantity(rs.getInt("quantity"));       // Set quantity ordered
                order.setTotal((rs.getDouble("total")));        // Set total amount
                order.setOrderDate(rs.getDate("order_date").toLocalDate());     // Convert SQL Date to LocalDate
                order.setStatus(Order.Status.valueOf(rs.getString("status")));  // Convert status string to Enum
                orders.add(order); // Add the Order object to the list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print exception if query fails
        }
        
        return orders; // Return the list of refunded orders
    }
    
}
