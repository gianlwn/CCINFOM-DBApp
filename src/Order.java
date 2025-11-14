import java.time.LocalDate;
import java.time.Month;

public class Order {
    private int order_id;
    private int customer_id;
    private int product_id;
    private int quantity;
    private Product productBought;
    private Customer customer;
    private double total;
    private LocalDate order_date;

    public enum Status {
        COMPLETED,
        REFUNDED
    }
    private Status status;

    public Order() {}

    public Order(int customer_id, int product_id, int quantity, Product productBought, Customer customer) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.productBought = productBought;
        this.customer = customer;

        if ((LocalDate.now().getMonth() == Month.JANUARY && LocalDate.now().getDayOfMonth() == 1) ||
            (LocalDate.now().getMonth() == Month.FEBRUARY && LocalDate.now().getDayOfMonth() == 2) ||
            (LocalDate.now().getMonth() == Month.MARCH && LocalDate.now().getDayOfMonth() == 3) ||
            (LocalDate.now().getMonth() == Month.APRIL && LocalDate.now().getDayOfMonth() == 4) ||
            (LocalDate.now().getMonth() == Month.MAY && LocalDate.now().getDayOfMonth() == 5) ||
            (LocalDate.now().getMonth() == Month.JUNE && LocalDate.now().getDayOfMonth() == 6) ||
            (LocalDate.now().getMonth() == Month.JULY && LocalDate.now().getDayOfMonth() == 7) ||
            (LocalDate.now().getMonth() == Month.AUGUST && LocalDate.now().getDayOfMonth() == 8) ||
            (LocalDate.now().getMonth() == Month.SEPTEMBER && LocalDate.now().getDayOfMonth() == 9) ||
            (LocalDate.now().getMonth() == Month.OCTOBER && LocalDate.now().getDayOfMonth() == 10) ||
            (LocalDate.now().getMonth() == Month.NOVEMBER && LocalDate.now().getDayOfMonth() == 11) ||
            (LocalDate.now().getMonth() == Month.DECEMBER && LocalDate.now().getDayOfMonth() == 12) ||
            (LocalDate.now().getMonth() == Month.DECEMBER && LocalDate.now().getDayOfMonth() == 25) ||
            (LocalDate.now().getMonth() == Month.JUNE && LocalDate.now().getDayOfMonth() == 12)) {
                this.total = (quantity * productBought.getPrice()) * 0.90;
        } else {
            this.total = quantity * productBought.getPrice();
        }

        this.order_date = LocalDate.now();
        this.status = Status.COMPLETED;
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

    // Product Bought
    public Product getProductBought() {
        return productBought;
    }

    // Customer
    public Customer getCustomer() {
        return customer;
    }

    // Unit Price
    public double getTotal() {
        return total;
    }
    public void setTotal(double total) {
        this.total = total;
    }

    // Order Date
    public LocalDate getOrderDate() {
        return order_date;
    }
    public void setOrderDate(LocalDate order_date) {
        this.order_date = order_date;
    }

    // Status
    public Status getStatus() {
        return status;
    }
    public void setStatus(Status status) {
        this.status = status;
    }
    
}
