import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * SouvenirApp is the main entry point for the Souvenir Shop Management System.
 * 
 * <p>
 * This application provides a console-based interface to manage products and orders.
 * It allows users to:
 * <ul>
 *     <li>Create new orders for customers</li>
 *     <li>View all orders and order details</li>
 *     <li>Edit the status of orders (e.g., mark as REFUNDED)</li>
 *     <li>Calculate total sales from completed orders</li>
 *     <li>Add new products to inventory</li>
 *     <li>View all products</li>
 *     <li>Update product stock</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The application interacts with the database through DAO classes:
 * <ul>
 *     <li>{@link CustomerDAO} for managing customers</li>
 *     <li>{@link ProductDAO} for managing products</li>
 *     <li>{@link OrderDAO} for managing orders</li>
 * </ul>
 * </p>
 * 
 * <p>
 * The application uses Java's {@link Scanner} class to read user input from the console
 * and includes exception handling for invalid input.
 * </p>
 */
public class SouvenirApp {

    /**
     * Main method that runs the application.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Scanner for reading input from the console
        Scanner scanner = new Scanner(System.in);
        // Initialize DAO classes for database operations
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO(productDAO, customerDAO);

        // This while(true) loop keeps the program running until the user chooses to exit
        while (true) {     
            menu(); // Display main menu
            String menuChoice = scanner.nextLine(); // Read the user's choice

            // Check the user's main menu choice
            switch (menuChoice) {
                case "1" -> { // ORDERS MENU
                    boolean isExiting = false; // This will control exiting the orders menu

                    // Orders menu loop
                    while (!isExiting) {
                        ordersMenu(); // Display orders menu options
                        String ordersChoice = scanner.nextLine(); // Read user's orders menu choice
                    
                        switch (ordersChoice) { // Decide what to do based on the orders menu choice
                            case "1" -> { // Create a new order
                                System.out.println("\n--- Create Order ---");
                                
                                // Ask for customer details
                                System.out.print("Enter First Name: ");
                                String firstName = scanner.nextLine();
                                System.out.print("Enter Last Name: ");
                                String lastName = scanner.nextLine();
                                System.out.print("Enter Contact Number (optional): ");
                                String contact = scanner.nextLine();
                                System.out.print("Enter Email (optional): ");
                                String email = scanner.nextLine();  
                                int productID, quantity;

                                try {
                                    // Ask for product details
                                    System.out.print("Enter Product ID: ");
                                    productID = scanner.nextInt(); // Must be a number
                                    scanner.nextLine(); // consume newline
                                    System.out.print("Enter Quantity Bought: ");
                                    quantity = scanner.nextInt(); // Must be a number
                                    scanner.nextLine(); // consume newline
                                } catch (InputMismatchException e) {
                                    // If user enters a non-number for productID or quantity
                                    System.out.println("Invalid input. Try again.");
                                    scanner.nextLine(); // clear invalid input
                                    break; // Stop this case and go back to orders menu
                                }

                                // Create a customer object
                                Customer customer = new Customer(firstName, lastName, contact, email);
                                // Get the product object from the product ID
                                Product productBought = productDAO.getProductById(productID);

                                if (productBought != null) {    
                                    // Create an order object combining customer and product
                                    Order orders = new Order(customer.getCustomerId(), productID, quantity, productBought, customer);

                                    //  Save the order to the database
                                    if (orderDAO.createOrder(customer, orders))
                                        System.out.println("Order created successfully!");
                                    else
                                        System.out.println("Failed to create order.");

                                } else {
                                    // If the product ID does not exist
                                    System.out.println("Product does not exist.");
                                }     
                            }

                            case "2" -> { // View all orders
                                System.out.println("\n========================================");
                                System.out.println("             ALL ORDERS                 ");
                                System.out.println("========================================");
                                ArrayList<Order> orders = orderDAO.getAllOrders(); // Get all orders

                                if (orders.isEmpty()) {
                                    System.out.println("No orders found.");
                                } else {
                                    // Print table header
                                    System.out.printf("%-8s %-12s %-12s %-10s %-12s %-15s %s%n",
                                        "OrderID", "CustomerID", "ProductID", "Quantity", "OrderDate", "Total", "Status");
                                    System.out.println("--------------------------------------------------------------------------------------");

                                    // Print each order in the table
                                    for (Order order : orders)
                                        System.out.printf("%-8d %-12d %-12d %-10d %-12s Php %-11.2f %s%n", order.getOrderId(), order.getCustomerId(), order.getProductId(),
                                                                                                    order.getQuantity(), order.getOrderDate(), order.getTotal(), order.getStatus());

                                    System.out.println("======================================================================================");
                                    System.out.println("Total Orders: " + orders.size());
                                }
                            }

                            case "3" -> { // Edit order status
                                System.out.println("\n--- Edit Order Status ---");
                                int oid;

                                try {
                                    System.out.print("Enter Order ID to update: ");
                                    oid = scanner.nextInt(); 
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Try again.");
                                    scanner.nextLine();
                                    break;
                                }

                                scanner.nextLine(); // consume newline
                                Order existing = orderDAO.getOrderById(oid); // Find the order

                                if (existing == null) {
                                    System.out.println("Order not found.");
                                } else {
                                    System.out.println("\n--- Edit Order Status of: OID #" + existing.getOrderId() + " ---");
                                    System.out.println("1 -> Change to \'REFUNDED\'");
                                    System.out.println("0 -> Cancel");
                                    System.out.print("\nEnter choice: ");
                                    String choice = scanner.nextLine();

                                    // Change order status or cancel
                                    switch (choice) {
                                        case "1" -> {
                                            if (existing.getStatus() != Order.Status.REFUNDED) {
                                                existing.setStatus(Order.Status.REFUNDED);
                                                orderDAO.editOrderStatus(existing); // Update in database

                                                // Add quantity back to stock if refunded
                                                Product productToModify = orderDAO.getProductSoldInOrderById(existing.getOrderId());
                                                productDAO.updateProductStock(productToModify, productToModify.getQuantityInStock() + existing.getQuantity());

                                                System.out.println("Successfully changed status.");
                                            } else {
                                                System.out.println("Cannot change status");
                                            }
                                        }

                                        case "0" -> {
                                            System.out.println("Exiting...");
                                            break;
                                        }

                                        default -> System.out.println("Invalid choice!");
                                    }
                                }
                            }

                            case "0" -> { 
                                System.out.println("Exiting...");
                                isExiting = true; // Exit orders menu
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }

                case "2" -> { // PRODUCTS MENU
                    boolean isExiting = false;

                    while (!isExiting) {
                        productsMenu(); // Show product options
                        String productsChoice = scanner.nextLine();
                    
                        switch (productsChoice) {
                            case "1" -> { // Add product
                                System.out.println("\n--- Add Product ---");
                                System.out.print("Enter Product Name: ");
                                String name = scanner.nextLine();
                                int categoryId, quantity, supplierId;
                                double price;

                                try {
                                    System.out.print("Enter Category ID: ");
                                    categoryId = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Enter Price: ");
                                    price = scanner.nextDouble();
                                    System.out.print("Enter Quantity In Stock: ");
                                    quantity = scanner.nextInt();
                                    System.out.print("Enter Supplier ID: ");
                                    supplierId = scanner.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Try again.");
                                    scanner.nextLine(); // clear invalid input
                                    break;
                                }

                                Product product = new Product(name, categoryId, price, quantity, supplierId);

                                if (productDAO.addProduct(product))
                                    System.out.println("Product added successfully!");
                                else
                                    System.out.println("Failed to add product.");
                            }

                            case "2" -> { // view all products
                                System.out.println("\n========================================");
                                System.out.println("              ALL PRODUCTS              ");
                                System.out.println("========================================");
                                ArrayList<Product> products = productDAO.getAllProducts();

                                if (products.isEmpty()) {
                                    System.out.println("No products found.");
                                } else {
                                    System.out.printf("%-5s %-28s %-21s %-10s %n", "ID", "Name", "Price", "Stock");
                                    System.out.println("------------------------------------------------------------------------");

                                    for (Product p : products)
                                        System.out.printf("%-5d %-28s Php %-17.2f %-10d%n", p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantityInStock());

                                    System.out.println("========================================================================");
                                    System.out.println("Total Products: " + products.size());
                                }
                            }

                            case "3" -> { // update product stock
                                System.out.println("\n--- Update Product ---");
                                int pid;

                                try {
                                    System.out.print("Enter Product ID to update: ");
                                    pid = scanner.nextInt();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Try again.");
                                    scanner.nextLine();
                                    break;
                                }

                                scanner.nextLine();
                                Product existing = productDAO.getProductById(pid);

                                if (existing == null) {
                                    System.out.println("Product not found.");
                                } else {
                                    System.out.println("Editing product: " + existing.getProductName());
                                    int newQty;

                                    try {
                                        System.out.print("Enter New Quantity In Stock: ");
                                        newQty = scanner.nextInt();
                                    } catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Try again.");
                                        scanner.nextLine();
                                        break;
                                    }

                                    existing.setQuantityInStock(newQty);

                                    if (productDAO.updateProductStock(existing))
                                        System.out.println("Product updated successfully!");
                                    else
                                        System.out.println("Failed to update product.");
                                }
                            }

                            case "0" -> {
                                System.out.println("Exiting...");
                                isExiting = true; // exit products menu
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }

                case "3" -> { // RECORDS MENU
                    boolean isExiting = false;

                    while (!isExiting) {
                        reportsMenu(); // Show report options
                        String productsChoice = scanner.nextLine();
                    
                        switch (productsChoice) {
                            case "1" -> { // View total sales
                                double totalSales = 0;

                                // Add up all completed orders
                                for (Order order : orderDAO.getAllOrders()) {
                                    if (order.getStatus() == Order.Status.COMPLETED) {
                                        totalSales += order.getTotal();
                                    }
                                }

                                System.out.printf("Total sales: Php %.2f%n", totalSales);
                            }

                            case "2" -> { // view product performance
                                System.out.println("\n========================================");
                                System.out.println("          PRODUCT PERFORMANCE              ");
                                System.out.println("========================================");
                                // TODO: VIEW PRODUCT PERFORMANCE
         
                            }

                            case "3" -> { // product return analysis
                               // TODO: PRODUCT RETURN ANALYSIS
                            }

                            case "0" -> {
                                System.out.println("Exiting...");
                                isExiting = true; // exit products menu
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }


                case "0" -> { // exit entire program
                    System.out.println("Exiting...");
                    scanner.close(); // close scanner
                    System.exit(0); // terminate program
                }
                
                default -> System.out.println("Invalid choice!"); // main menu invalid input
            }
        }
    }

    /**
     * Displays the main menu for the application.
     */
    private static void menu() {
        System.out.println("\n========================================");
        System.out.println("    SOUVENIR SHOP MANAGEMENT SYSTEM    ");
        System.out.println("========================================");
        System.out.println("[ADD RECORDS]");
        System.out.println("1 -> Orders Menu");
        System.out.println("2 -> Products Menu");
        System.out.println("3 -> Reports Menu");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    /**
     * Displays the order menu for the application.
     */
    private static void ordersMenu() {
        System.out.println("\n========================================");
        System.out.println("              ORDERS MENU               ");
        System.out.println("========================================");
        System.out.println("1 -> Create Order");
        System.out.println("2 -> View All Orders");
        System.out.println("3 -> Edit Order");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    /**
     * Displays the products menu for the application.
     */
    private static void productsMenu() {
        System.out.println("\n========================================");
        System.out.println("             PRODUCTS MENU             ");
        System.out.println("========================================");
        System.out.println("1 -> Add Product");
        System.out.println("2 -> View All Products");
        System.out.println("3 -> Update Product Stock");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    /**
     * Displays the order menu for the application.
     */
    private static void reportsMenu() {
        System.out.println("\n========================================");
        System.out.println("              REPORTS MENU              ");
        System.out.println("========================================");
        System.out.println("1 -> View Total Sales");
        System.out.println("2 -> View Product Performance");
        System.out.println("3 -> Product Return Analysis");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }
}