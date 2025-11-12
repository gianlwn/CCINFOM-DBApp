import java.sql.*;
import java.util.ArrayList;

public class OrderDetailsDAO {
    private final ProductDAO productDAO;
    private final CustomerDAO customerDAO;

    public OrderDetailsDAO(ProductDAO productDAO, CustomerDAO customerDAO) {
        this.productDAO = productDAO;
        this.customerDAO = customerDAO;
    }

    public boolean createOrder(Customer customer, OrderDetails orderDetails) {
        if (orderDetails.getProductBought().getProductId() <= 3000 || orderDetails.getProductBought().getProductId() >= 4000) {
            System.err.println("Enter a valid Product ID");
            return false;
        }

        if (orderDetails.getQuantity() <= 0 || orderDetails.getProductBought().getQuantityInStock() <= orderDetails.getQuantity()) {
            System.err.println("Select valid quantity in the stock.");
            return false;
        }

        if (customerDAO.addCustomer(customer)) {
            String sql = "INSERT INTO order_details (customer_id, product_id, quantity, total, order_date, status) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, customer.getCustomerId());
                stmt.setInt(2, orderDetails.getProductId());
                stmt.setInt(3, orderDetails.getQuantity());
                stmt.setDouble(4, orderDetails.getTotal());
                stmt.setDate(5, Date.valueOf(orderDetails.getOrderDate()));
                stmt.setString(6, orderDetails.getStatus().name());

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            orderDetails.setOrderId(keys.getInt(1));
                        }
                    }
                }

                productDAO.updateProductStock(orderDetails.getProductBought(), orderDetails.getProductBought().getQuantityInStock() - orderDetails.getQuantity());
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("Failed to create order, enter valid fields for the customer.");
            return false;
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
