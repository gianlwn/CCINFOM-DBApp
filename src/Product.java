public class Product {
    // Variables
    private int product_id;
    private String product_name;
    private int category_id;
    private double price;
    private int quantity_in_stock;
    private int supplier_id;

    // Constructors
    public Product() {}

    public Product(int product_id, String product_name, int category_id, double price, int quantity_in_stock, int supplier_id) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.category_id = category_id;
        this.price = price;
        this.quantity_in_stock = quantity_in_stock;
        this.supplier_id = supplier_id;
    }

    // Product ID
    public int getProductId() {
        return product_id;
    }
    public void setProductId(int product_id) {
        this.product_id = product_id;
    }

    // Product Name
    public String getProductName() {
        return product_name;
    }
    public void setProductName(String product_name) {
        this.product_name = product_name;
    }

    // Category ID
    public int getCategoryId() {
        return category_id;
    }
    public void setCategoryId(int category_id) {
        this.category_id = category_id;
    }

    // Price
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    // Quantity in Stock
    public int getQuantityInStock() {
        return quantity_in_stock;
    }
    public void setQuantityInStock(int quantity_in_stock) {
        this.quantity_in_stock = quantity_in_stock;
    }

    // Supplier ID
    public int getSupplierId() {
        return supplier_id;
    }
    public void setSupplierId(int supplier_id) {
        this.supplier_id = supplier_id;
    }
}
