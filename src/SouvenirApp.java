import java.util.ArrayList;
import java.util.Scanner;

public class SouvenirApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

        while (true) {     
            menu();       
            int menuChoice = scanner.nextInt();
            scanner.nextLine();

            switch (menuChoice) {   
                case 1 -> {
                    boolean isExitting = false;

                    while (!isExitting) {
                        productsMenu();
                        int productsChoice = scanner.nextInt();
                        scanner.nextLine();
                    
                        switch (productsChoice) {
                            case 1 -> {
                                System.out.println("\n--- Add Product ---");
                                System.out.print("Enter Product Name: ");
                                String name = scanner.nextLine();
                                System.out.print("Enter Category ID: ");
                                int categoryId = scanner.nextInt();
                                scanner.nextLine();
                                System.out.print("Enter Price: ");
                                double price = scanner.nextDouble();
                                System.out.print("Enter Quantity In Stock: ");
                                int quantity = scanner.nextInt();
                                System.out.print("Enter Supplier ID: ");
                                int supplierId = scanner.nextInt();
                                Product product = new Product(name, categoryId, price, quantity, supplierId);

                                if (productDAO.addProduct(product))
                                    System.out.println("Product added successfully!");
                                else
                                    System.out.println("Failed to add product.");
                            }

                            case 2 -> {
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

                            case 3 -> {
                                System.out.println("\n--- Update Product ---");
                                System.out.print("Enter Product ID to update: ");
                                int id = scanner.nextInt();
                                scanner.nextLine();
                                Product existing = productDAO.getProductById(id);

                                if (existing == null) {
                                    System.out.println("Product not found.");
                                } else {
                                    System.out.println("Editing product: " + existing.getProductName());
                                    System.out.print("Enter New Quantity In Stock: ");
                                    int newQty = scanner.nextInt();
                                    existing.setQuantityInStock(newQty);

                                    if (productDAO.updateProductStock(existing))
                                        System.out.println("Product updated successfully!");
                                    else
                                        System.out.println("Failed to update product.");
                                }
                            }

                            case 0 -> {
                                System.out.println("Exiting...");
                                isExitting = true;
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }
                
                case 2 -> {
                    System.out.println("\n--- Create Order ---");
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    // TODO: put try-catch here
                    System.out.print("Enter Product ID: ");
                    int productID = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Quantity Bought: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();
                    Customer customer = new Customer(firstName, lastName, contact, email);

                    if (customerDAO.addCustomer(customer)) {
                        Product productBought = productDAO.getProductById(productID);

                        if (productBought != null) {
                            OrderDetails orderDetails = new OrderDetails(customer.getCustomerId(), productID, quantity, productBought);

                            if (orderDetailsDAO.createOrder(customer, orderDetails))
                                System.out.println("Order created successfully!");
                            else
                                System.out.println("Failed to create order");
                        } else {
                            System.out.println("Product does not exist.");
                        }

                    } else {
                        System.out.println("Failed to create order, enter valid fields for the customer.");
                    }
                }

                case 3 -> {
                    System.out.println("\n========================================");
                    System.out.println("             ALL ORDERS                 ");
                    System.out.println("========================================");
                    ArrayList<OrderDetails> orders = orderDetailsDAO.getAllOrders();

                    if (orders.isEmpty()) {
                        System.out.println("No orders found.");
                    } else {
                        System.out.printf("%-8s %-12s %-12s %-10s %-12s %-12s%n",
                            "OrderID", "CustomerID", "ProductID", "Quantity", "OrderDate", "Total");
                        System.out.println("----------------------------------------------------------------------------");

                        for (OrderDetails od : orders)
                            System.out.printf("%-8d %-12d %-12d %-10d %-12s $%-11.2f%n", od.getOrderId(), od.getCustomerId(), od.getProductId(),
                                                                                         od.getQuantity(), od.getOrderDate(), od.getTotal());

                        System.out.println("============================================================================");
                        System.out.println("Total Orders: " + orders.size());
                    }
                }

                case 4 -> {
                    // TODO: put edit order commands
                }   

                case 0 -> {
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                }
                
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void menu() {
        System.out.println("\n========================================");
        System.out.println("    SOUVENIR SHOP MANAGEMENT SYSTEM    ");
        System.out.println("========================================");
        System.out.println("[ADD RECORDS]");
        System.out.println("1 -> Products Menu");
        System.out.println("2 -> Create Order");
        System.out.println("3 -> View All Orders");
        System.out.println("4 -> Edit Order");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    private static void productsMenu() {
        System.out.println("\n========================================");
        System.out.println("    SOUVENIR SHOP MANAGEMENT SYSTEM    ");
        System.out.println("========================================");
        System.out.println("1 -> Add Product");
        System.out.println("2 -> View All Products");
        System.out.println("3 -> Update Product Stock");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }
}
