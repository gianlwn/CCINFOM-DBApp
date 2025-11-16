import java.time.LocalDate;
import java.time.Month;

/**
 * Represents an Order in the Souvenir Shop Management System.
 * 
 * <p>
 * This class stores information about a customer order, including:
 * <ul>
 *     <li>Order ID</li>
 *     <li>Customer who placed the order</li>
 *     <li>Product purchased</li>
 *     <li>Quantity and total price</li>
 *     <li>Order date</li>
 *     <li>Status of the order (COMPLETED or REFUNDED)</li>
 * </ul>
 * </p>
 * 
 * <p>
 * It also calculates the total price based on the product price, quantity,
 * and possible discounts for special dates.
 * </p>
 */
public class Order {

    private int order_id;           // Unique ID for the order
    private int customer_id;        // ID of the customer placing the order
    private int product_id;         // ID of the purchased product
    private int quantity;           // Quantity of product purchased
    private Product productBought;  // Product object representing the purchased item
    private Customer customer;      // Customer object placing the order
    private double total;           // Total price of the order
    private LocalDate order_date;   // Date the order was created

    /**
     * Enum representing the status of the order.
     * COMPLETED = order is successfully fulfilled.
     * REFUNDED = order has been refunded.
     */
    public enum Status {
        COMPLETED,
        REFUNDED
    }

    private Status status; // Current status of the order

    /**
     * Default constructor.
     * <p> Creates an empty Order object. Fields can be set later using setters. </p>
     */
    public Order() {}

    /**
     * Parameterized constructor.
     * 
     * <p>
     * Creates an Order with the given customer, product, and quantity.
     * Automatically calculates the total based on product price and quantity.
     * Applies special discounts on certain dates:
     * <ul>
     *     <li>Day = Month number (e.g., Jan 1, Feb 2, Mar 3, … Dec 12) → 10% off</li>
     *     <li>Dec 25 or June 12 → 20% off</li>
     * </ul>
     * The order date is set to the current date and the status is set to COMPLETED.
     * </p>
     * 
     * @param customer_id   The ID of the customer placing the order
     * @param product_id    The ID of the product being purchased
     * @param quantity      The number of units ordered
     * @param productBought The Product object representing the purchased item
     * @param customer      The Customer object placing the order
     */
    public Order(int customer_id, int product_id, int quantity, Product productBought, Customer customer) {
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.quantity = quantity;
        this.productBought = productBought;
        this.customer = customer;

        // 10% discount if day matches month (e.g., Jan 1, Feb 2, etc.)
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
            (LocalDate.now().getMonth() == Month.DECEMBER && LocalDate.now().getDayOfMonth() == 12)) {
            this.total = (quantity * productBought.getPrice()) * 0.90; // 10% off

        // 20% discount for Dec 25 or June 12
        } else if ((LocalDate.now().getMonth() == Month.DECEMBER && LocalDate.now().getDayOfMonth() == 25) ||
                  (LocalDate.now().getMonth() == Month.JUNE && LocalDate.now().getDayOfMonth() == 12)) {
            this.total = (quantity * productBought.getPrice()) * 0.80; // 20% off
        
        // Regular price
        } else {
            this.total = quantity * productBought.getPrice();
        }

        this.order_date = LocalDate.now();  // Set order date to current date
        this.status = Status.COMPLETED;     // Default status is COMPLETED
    }

    /**
     * Gets the unique order ID.
     * @return order_id The ID of this order
     */
    public int getOrderId() {
        return order_id;
    }

    /**
     * Sets the unique order ID.
     * @param order_id The ID to set for this order
     */
    public void setOrderId(int order_id) {
        this.order_id = order_id;
    }

    /**
     * Gets the ID of the customer who placed this order.
     * @return customer_id The customer's ID
     */
    public int getCustomerId() {
        return customer_id;
    }

    /**
     * Sets the ID of the customer who placed this order.
     * @param customer_id The customer's ID to set
     */
    public void setCustomerId(int customer_id) {
        this.customer_id = customer_id;
    }

    /**
     * Gets the ID of the product purchased.
     * @return product_id The product's ID
     */
    public int getProductId() {
        return product_id;
    }

    /**
     * Sets the ID of the product purchased.
     * @param product_id The product's ID to set
     */
    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Gets the quantity of the product purchased.
     * @return quantity Number of units purchased
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of the product purchased.
     * @param quantity Number of units to set
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Gets the Product object purchased in this order.
     * @return productBought The Product object
     */
    public Product getProductBought() {
        return productBought;
    }

    /**
     * Gets the Customer object who placed this order.
     * @return customer The Customer object
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Gets the total price of the order.
     * @return total The total amount for this order
     */
    public double getTotal() {
        return total;
    }

    /**
     * Sets the total price of the order.
     * @param total The total amount to set
     */
    public void setTotal(double total) {
        this.total = total;
    }

    /**
     * Gets the date when the order was placed.
     * @return order_date The date of the order
     */
    public LocalDate getOrderDate() {
        return order_date;
    }

    /**
     * Sets the date when the order was placed.
     * @param order_date The date to set
     */
    public void setOrderDate(LocalDate order_date) {
        this.order_date = order_date;
    }

    /**
     * Gets the current status of the order.
     * @return status The order status (COMPLETED or REFUNDED)
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Sets the current status of the order.
     * @param status The status to set (COMPLETED or REFUNDED)
     */
    public void setStatus(Status status) {
        this.status = status;
    }
    
}
