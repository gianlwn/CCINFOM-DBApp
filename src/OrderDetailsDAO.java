import java.sql.*;
import java.util.ArrayList;

/*
TODO: change the whole class based on changes
 */

public class OrderDetailsDAO {
    public boolean createOrder(Customer customer, OrderDetails orderDetails) {
        if (orderDetails.getProductBought().getProductId() <= 0) {
            System.err.println("Product ID field must be provided.");
            return false;
        }

        if (orderDetails.getProductBought().getQuantityInStock() < 0) {
            System.err.println("Quantity in stock cannot be negative.");
            return false;
        }

        String sql = "INSERT INTO order_details (customer_id, product_id, quantity, unit_price, order_date) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, customer.getCustomerId());
            stmt.setInt(2, orderDetails.getProductId());
            stmt.setInt(3, orderDetails.getQuantity());
            stmt.setDouble(4, orderDetails.getTotal());
            stmt.setDate(5, Date.valueOf(orderDetails.getOrderDate()));

            int rows = stmt.executeUpdate();

            if (rows > 0) {
                try (ResultSet keys = stmt.getGeneratedKeys()) {
                    if (keys.next()) {
                        orderDetails.setOrderId(keys.getInt(1));
                    }
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
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
                od.setTotal((rs.getDouble("total")));
                od.setOrderDate(rs.getDate("order_date").toLocalDate());
                orderDetails.add(od);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orderDetails;
    }
}
