import java.sql.*;
import java.util.ArrayList;

/**
 * ProductDAO is a Data Access Object (DAO) class responsible for handling
 * all database operations related to products.
 * 
 * <p>
 * This class interacts with the "products" table in the database.
 * It allows you to:
 * <ul>
 *     <li>Add a new product</li>
 *     <li>Retrieve a product by ID</li>
 *     <li>Retrieve all products</li>
 *     <li>Update product stock</li>
 * </ul>
 * </p>
 * 
 * <p>
 * This class uses JDBC (Java Database Connectivity) to communicate with the database.
 * </p>
 */
public class ProductDAO {

    /**
     * Adds a new product to the database after validating its fields.
     * 
     * @param product The Product object to add
     * @return {@code true} if the product was successfully added, {@code false} otherwise
     */
    public boolean addProduct(Product product) {
        // Validate product ID range
        if (product.getProductId() <= 3000 || product.getProductId() >= 4000) {
            System.err.println("Enter a valid Product ID.");
            return false;
        }

        // Validate product name
        if (product.getProductName() == null || product.getProductName().isBlank()) {
            System.err.println("Product Name cannot be null or empty.");
            return false;
        }

        // Validate category ID
        if (product.getCategoryId() <= 0) {
            System.err.println("Enter a valid Category ID.");
            return false;
        }

        // Validate supplier ID
        if (product.getSupplierId() <= 0) {
            System.err.println("Enter a valid Supplier ID.");
            return false;
        }
        
        // Validate quantity in stock
        if (product.getQuantityInStock() < 0) {
            System.err.println("Enter a valid quantity.");
            return false;
        }

        // Validate price
        if (product.getPrice() <= 0) {
            System.err.println("Enter a valid price.");
            return false;
        }

        // SQL query to insert the product into the database
        String sql = "INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id) VALUES (?, ?, ?, ?, ?)";
        
        // Try-with-resources to automatically close database resources
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            // Set the parameters for the PreparedStatement
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getCategoryId());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantityInStock());
            stmt.setInt(5, product.getSupplierId());

            // Execute the insert operation
            int rows = stmt.executeUpdate();

            // Retrieve the auto-generated product ID if insert was successful
            if (rows > 0) 
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next())
                        product.setProductId(keys.getInt(1)); // Set the generated ID in Product object
                }

            return true;
        } catch (SQLException e) {
            e.printStackTrace(); // Print stack trace for debugging SQL errors
        }

        return false; // Return false if product could not be added
    }

    /**
     * Retrieves a product from the database by its unique product ID.
     *
     * <p>
     * The method prepares a SQL SELECT query with a parameterized statement to prevent SQL injection.
     * It returns a Product object with all database fields mapped if found; otherwise, returns null.
     * </p>
     *
     * @param pid The unique ID of the product to retrieve
     * @return The Product object if found; {@code null} if the product does not exist
     */
    public Product getProductById(int pid) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid); // Set product ID in query
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) { // Map database row to Product object
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantityInStock(rs.getInt("quantity_in_stock"));
                    product.setSupplierId(rs.getInt("supplier_id"));

                    return product; // Return mapped Product
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL errors
        }

        return null; // Return null if not found
    }

    /**
     * Retrieves all products from the database, ordered by their product ID.
     *
     * <p>
     * The method executes a SELECT query and iterates through the ResultSet,
     * mapping each row to a Product object, which is then added to an ArrayList.
     * </p>
     *
     * @return An ArrayList of all Product objects; empty list if no products exist
     */
    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name, category_id, price, quantity_in_stock, supplier_id FROM products ORDER BY product_id";
        
        // Loop through ResultSet to map each row to a Product object
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setCategoryId(rs.getInt("category_id"));
                p.setPrice(rs.getDouble("price"));
                p.setQuantityInStock(rs.getInt("quantity_in_stock"));
                p.setSupplierId(rs.getInt("supplier_id"));

                if (p.getQuantityInStock() <= 20)
                    p.setStockStatus("Low");
                else
                    p.setStockStatus("OK");

                products.add(p); // Add to list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL errors
        }
        
        return products;
    }

    /**
     * Updates the stock quantity of an existing product in the database.
     *
     * <p>
     * Uses the quantity stored in the Product object itself. Executes an UPDATE query
     * using a PreparedStatement and returns true if at least one row was affected.
     * </p>
     *
     * @param existingProduct The Product object containing updated stock
     * @return {@code true} if the stock was successfully updated; {@code false} otherwise
     */
    public boolean updateProductStock(Product existingProduct) {
        String sql = "UPDATE products SET quantity_in_stock = ? WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, existingProduct.getQuantityInStock());
            stmt.setInt(2, existingProduct.getProductId());
            
            return stmt.executeUpdate() > 0; // Return true if at least one row updated
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Updates the stock quantity of an existing product to a specified new quantity.
     *
     * <p>
     * Executes an UPDATE query with the provided new quantity, overriding the existing value.
     * Useful for directly setting stock after order processing or inventory adjustments.
     * </p>
     *
     * @param existingProduct The Product object to update
     * @param newQty The new stock quantity to set
     * @return {@code true} if the stock was successfully updated; {@code false} otherwise
     */
    public boolean updateProductStock(Product existingProduct, int newQty) {
        String sql = "UPDATE products SET quantity_in_stock = ? WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQty);
            stmt.setInt(2, existingProduct.getProductId());
            
            return stmt.executeUpdate() > 0; // Return true if update succeeds
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<Product> viewProductPerformance() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT p.product_id, p.product_name, IFNULL(SUM(IF(o.status = 'COMPLETED', o.quantity, 0)), 0) AS total_sold, " +
        "IFNULL(SUM(IF(o.status = 'COMPLETED', o.quantity * p.price, 0)), 0) AS total_revenue " +
        "FROM products p LEFT JOIN orders o ON p.product_id = o.product_id " +
        "GROUP BY p.product_id, p.product_name ORDER BY total_sold DESC, p.product_id ASC";
        
        // Loop through ResultSet to map each row to a Product object
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product p = new Product();
                p.setProductId(rs.getInt("product_id"));
                p.setProductName(rs.getString("product_name"));
                p.setTotalSold(rs.getInt("total_sold"));

                products.add(p); // Add to list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL errors
        }
        
        return products;
    }

    public ArrayList<Product> viewProductReturnAnalysis() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT o.order_id, p.product_name, SUM(o.quantity) AS total_refunded " +
                     "FROM products p JOIN orders o ON p.product_id = o.product_id " +
                     "WHERE o.status = 'REFUNDED' " +
                     "GROUP BY o.order_id, p.product_name " +
                     "ORDER BY total_refunded DESC, p.product_id ASC";
        
        // Loop through ResultSet to map each row to a Product object
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Product p = new Product();
                p.setOrderId(rs.getInt("order_id"));
                p.setProductName(rs.getString("product_name"));
                p.setTotalRefunded(rs.getInt("total_sold"));

                products.add(p); // Add to list
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print SQL errors
        }
        
        return products;
    }
}
