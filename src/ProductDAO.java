import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {
    public boolean addProduct(Product product) {
        if (product.getProductId() <= 3000 || product.getProductId() >= 4000) {
            System.err.println("Enter a valid Product ID.");
            return false;
        }

        if (product.getProductName() == null || product.getProductName().isBlank()) {
            System.err.println("Product Name cannot be null or empty.");
            return false;
        }

        if (product.getCategoryId() <= 0) {
            System.err.println("Enter a valid Category ID.");
            return false;
        }

        if (product.getSupplierId() <= 0) {
            System.err.println("Enter a valid Supplier ID.");
            return false;
        }
        
        if (product.getQuantityInStock() < 0) {
            System.err.println("Enter a valid quantity.");
            return false;
        }

        if (product.getPrice() <= 0) {
            System.err.println("Enter a valid price.");
            return false;
        }

        String sql = "INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getCategoryId());
            stmt.setDouble(3, product.getPrice());
            stmt.setInt(4, product.getQuantityInStock());
            stmt.setInt(5, product.getSupplierId());

            int rows = stmt.executeUpdate();

            if (rows > 0) 
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next())
                        product.setProductId(keys.getInt(1));
                }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Product getProductById(int pid) {
        String sql = "SELECT * FROM products WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pid);
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    Product product = new Product();
                    product.setProductId(rs.getInt("product_id"));
                    product.setProductName(rs.getString("product_name"));
                    product.setCategoryId(rs.getInt("category_id"));
                    product.setPrice(rs.getDouble("price"));
                    product.setQuantityInStock(rs.getInt("quantity_in_stock"));
                    product.setSupplierId(rs.getInt("supplier_id"));

                    return product;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ArrayList<Product> getAllProducts() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT product_id, product_name, category_id, price, quantity_in_stock, supplier_id FROM products ORDER BY product_id";
        
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
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return products;
    }

    public boolean updateProductStock(Product existingProduct) {
        String sql = "UPDATE products SET quantity_in_stock = ? WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, existingProduct.getQuantityInStock());
            stmt.setInt(2, existingProduct.getProductId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateProductStock(Product existingProduct, int newQty) {
        String sql = "UPDATE products SET quantity_in_stock = ? WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQty);
            stmt.setInt(2, existingProduct.getProductId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
