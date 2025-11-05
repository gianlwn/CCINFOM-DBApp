import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAO {
    public boolean insertOrderDetails(OrderDetails order) {
        String sql = "INSERT INTO order_details (order_id, customer_id, product_id, quantity, unit_price, order_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getOrderId());
            stmt.setInt(2, order.getCustomerId());
            stmt.setInt(3, order.getProductId());
            stmt.setInt(4, order.getQuantity());
            stmt.setDouble(5, order.getUnitPrice());
            stmt.setDate(6, order.getOrderDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public OrderDetails getOrderDetailsById(int orderId, int productId) {
        String sql = "SELECT * FROM order_details WHERE order_id = ? AND product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                OrderDetails o = new OrderDetails();
                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setProductId(rs.getInt("product_id"));
                o.setQuantity(rs.getInt("quantity"));
                o.setUnitPrice(rs.getDouble("unit_price"));
                o.setOrderDate(rs.getDate("order_date"));

                return o;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean updateOrderDetails(OrderDetails order) {
        String sql = "UPDATE order_details SET customer_id=?, quantity=?, unit_price=?, order_date=? WHERE order_id=? AND product_id=?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, order.getCustomerId());
            stmt.setInt(2, order.getQuantity());
            stmt.setDouble(3, order.getUnitPrice());
            stmt.setDate(4, order.getOrderDate());
            stmt.setInt(5, order.getOrderId());
            stmt.setInt(6, order.getProductId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean deleteOrderDetails(int orderId, int productId) {
        String sql = "DELETE FROM order_details WHERE order_id = ? AND product_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, orderId);
            stmt.setInt(2, productId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    public ArrayList<OrderDetails> getAllOrderDetails() {
        ArrayList<OrderDetails> orders = new ArrayList<>();
        String sql = "SELECT * FROM order_details ORDER BY order_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                OrderDetails o = new OrderDetails();
                o.setOrderId(rs.getInt("order_id"));
                o.setCustomerId(rs.getInt("customer_id"));
                o.setProductId(rs.getInt("product_id"));
                o.setQuantity(rs.getInt("quantity"));
                o.setUnitPrice(rs.getDouble("unit_price"));
                o.setOrderDate(rs.getDate("order_date"));
                orders.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
}
