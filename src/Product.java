/**
 * Represents a Product in the Souvenir Shop Management System.
 * 
 * <p>
 * This class stores basic information about a product, such as its ID, name, category,
 * price, quantity in stock, and supplier ID. 
 * </p>
 * 
 * <p>
 * The Product object is typically used with {@link ProductDAO} to interact with the database.
 * </p>
 */
public class Product {
    private int product_id;         // Unique ID for the product
    private String product_name;    // Name of the product
    private int category_id;        // ID of the category this product belongs to
    private double price;           // Price of the product
    private int quantity_in_stock;  // Available quantity in stock
    private int supplier_id;        // ID of the supplier providing this product

    /**
     * Default constructor.
     * 
     * <p>Creates an empty Product object. Fields can be set later using setter methods.</p>
     */
    public Product() {}

    /**
     * Parameterized constructor.
     * 
     * <p>Creates a Product object with all basic information except the product ID,
     * which is typically auto-generated in the database.</p>
     * 
     * @param product_name       The name of the product
     * @param category_id        The category ID the product belongs to
     * @param price              The price of the product
     * @param quantity_in_stock  The available quantity in stock
     * @param supplier_id        The supplier ID providing the product
     */
    public Product(String product_name, int category_id, double price, int quantity_in_stock, int supplier_id) {
        this.product_name = product_name;
        this.category_id = category_id;
        this.price = price;
        this.quantity_in_stock = quantity_in_stock;
        this.supplier_id = supplier_id;
    }

    /**
     * Gets the product's unique ID.
     * @return The product ID
     */
    public int getProductId() {
        return product_id;
    }

    /**
     * Sets the product's unique ID.
     * @param product_id The product ID to set
     */
    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    /**
     * Gets the product name.
     * @return The product name
     */
    public String getProductName() {
        return product_name;
    }

    /**
     * Sets the product name.
     * @param product_name The product name to set
     */
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    /**
     * Gets the category ID of the product.
     * @return The category ID
     */
    public int getCategoryId() {
        return category_id;
    }

    /**
     * Sets the category ID of the product.
     * @param category_id The category ID to set
     */
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    /**
     * Gets the price of the product.
     * @return The product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of the product.
     * @param price The price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Gets the available quantity in stock.
     * @return The quantity in stock
     */
    public int getQuantityInStock() {
        return quantity_in_stock;
    }

    /**
     * Sets the available quantity in stock.
     * @param quantity_in_stock The quantity to set
     */
    public void setQuantityInStock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    /**
     * Gets the supplier ID of the product.
     * @return The supplier ID
     */
    public int getSupplierId() {
        return supplier_id;
    }

    /**
     * Sets the supplier ID of the product.
     * @param supplier_id The supplier ID to set
     */
    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }
}
