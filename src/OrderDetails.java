import java.sql.Date;

public class OrderDetails {
    private int order_id;
    private int customer_id;
    private int product_id;
    private int quantity;
    private double unit_price;
    private Date order_date;

    public OrderDetails() {}

    public OrderDetails(int order_id, int customer_id, int product_id, int quantity, double unit_price, Date order_date) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.unit_price = unit_price;
        this.order_date = order_date;
    }

    // Order ID
    public int getOrderId() {
        return order_id;
    }
    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }

    // Customer ID
    public int getCustomerId() {
        return customer_id;
    }
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    // Product ID
    public int getProductId() {
        return product_id;
    }
    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    // Quantity
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Unit Price
    public double getUnitPrice() {
        return unit_price;
    }
    public void setUnitPrice(double unit_price) {
        this.unit_price = unit_price;
    }

    // Order Date
    public Date getOrderDate() {
        return order_date;
    }
    public void setOrderDate(Date order_date) {
        this.order_date = order_date;
    }
}
