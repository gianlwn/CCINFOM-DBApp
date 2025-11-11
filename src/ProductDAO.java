import java.sql.*;
import java.util.ArrayList;

public class ProductDAO {
    public boolean addProduct(Product product) {
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

    /**
     * Helper method to get the product that needs to be edited.
     */
    public Product getProductById(int id) {
        String sql = "SELECT product_id, product_name, category_id, price, quantity_in_stock, supplier_id FROM products WHERE product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    Product p = new Product();
                    p.setProductId(rs.getInt("product_id"));
                    p.setProductName(rs.getString("product_name"));
                    p.setCategoryId(rs.getInt("category_id"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantityInStock(rs.getInt("quantity_in_stock"));
                    p.setSupplierId(rs.getInt("supplier_id"));

                    return p;
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
}
