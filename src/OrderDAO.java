import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
    private final ProductDAO productDAO;
    private final CustomerDAO customerDAO;

    public OrderDAO(ProductDAO productDAO, CustomerDAO customerDAO) {
        this.productDAO = productDAO;
        this.customerDAO = customerDAO;
    }

    public boolean createOrder(Customer customer, Order orders) {
        if (orders.getProductBought().getProductId() <= 3000 || orders.getProductBought().getProductId() >= 4000) {
            System.err.println("Enter a valid Product ID");
            return false;
        }

        if (orders.getQuantity() <= 0 || orders.getProductBought().getQuantityInStock() <= orders.getQuantity()) {
            System.err.println("Select valid quantity in the stock.");
            return false;
        }

        if (customerDAO.addCustomer(customer)) {
            String sql = "INSERT INTO orders (customer_id, product_id, quantity, total, order_date, status) VALUES (?, ?, ?, ?, ?, ?)";
            
            try (Connection conn = DBUtil.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setInt(1, customer.getCustomerId());
                stmt.setInt(2, orders.getProductId());
                stmt.setInt(3, orders.getQuantity());
                stmt.setDouble(4, orders.getTotal());
                stmt.setDate(5, Date.valueOf(orders.getOrderDate()));
                stmt.setString(6, orders.getStatus().name());

                int rows = stmt.executeUpdate();

                if (rows > 0) {
                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (keys.next()) {
                            orders.setOrderId(keys.getInt(1));
                        }
                    }
                }

                productDAO.updateProductStock(orders.getProductBought(), orders.getProductBought().getQuantityInStock() - orders.getQuantity());
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

    public Order getOrderById(int id) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery();) {
                if (rs.next()) {
                    Order order = new Order();
                    order.setOrderId(rs.getInt("order_id"));
                    order.setCustomerId(rs.getInt("customer_id"));
                    order.setProductId(rs.getInt("product_id"));
                    order.setQuantity(rs.getInt("quantity"));
                    order.setTotal(rs.getDouble("total"));
                    order.setOrderDate(rs.getDate("order_date").toLocalDate());
                    order.setStatus(Order.Status.valueOf(rs.getString("status")));

                    return order;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void editOrderStatus(Order order) {
        String sql = "UPDATE orders SET status = ? WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, order.getStatus().name());
            stmt.setInt(2, order.getOrderId());
                
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders ORDER BY order_id";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getInt("order_id"));
                order.setCustomerId(rs.getInt("customer_id"));
                order.setProductId(rs.getInt("product_id"));
                order.setQuantity(rs.getInt("quantity"));
                order.setTotal((rs.getDouble("total")));
                order.setOrderDate(rs.getDate("order_date").toLocalDate());
                order.setStatus(Order.Status.valueOf(rs.getString("status")));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return orders;
    }
}
