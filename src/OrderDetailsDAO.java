import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAO {
    public Integer createOrder(int customerId, int productId, int quantity, double unitPrice, java.sql.Timestamp orderDate) {
        String sql = "INSERT INTO order_details (customer_id, product_id, quantity, unit_price, order_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.setTimestamp(5, orderDate);

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
}

    public boolean insertOrderLine(int customerId, int productId, int quantity, double unitPrice, Timestamp orderDate) {
        String sql = "INSERT INTO order_details (customer_id, product_id, quantity, unit_price, order_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            stmt.setInt(2, productId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, unitPrice);
            stmt.setTimestamp(5, orderDate);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public ArrayList<OrderDetails> getOrderLinesByOrderId(int orderId) {
        ArrayList<OrderDetails> lines = new ArrayList<>();
        String sql = "SELECT * FROM order_details WHERE order_id = ? ORDER BY product_id";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    OrderDetails o = new OrderDetails();
                    o.setOrderId(rs.getInt("order_id"));
                    o.setCustomerId(rs.getInt("customer_id"));
                    o.setProductId(rs.getInt("product_id"));
                    o.setQuantity(rs.getInt("quantity"));
                    o.setUnitPrice(rs.getDouble("unit_price"));
                    o.setOrderDate(rs.getTimestamp("order_date"));
                    lines.add(o);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public ArrayList<OrderDetails> getAllOrders() {
        ArrayList<OrderDetails> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM order_details ORDER BY order_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OrderDetails od = new OrderDetails();
                od.setOrderId(rs.getInt("order_id"));
                od.setCustomerId(rs.getInt("customer_id"));
                od.setProductId(rs.getInt("product_id"));
                od.setQuantity(rs.getInt("quantity"));
                od.setUnitPrice(rs.getDouble("unit_price"));
                od.setOrderDate(rs.getTimestamp("order_date"));
                orderDetails.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderDetails;
    }
}
