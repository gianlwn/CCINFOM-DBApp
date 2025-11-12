import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class SouvenirApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CustomerDAO customerDAO = new CustomerDAO();
        ProductDAO productDAO = new ProductDAO();
        OrderDAO orderDAO = new OrderDAO(productDAO, customerDAO);

        while (true) {     
            menu();       
            String menuChoice = scanner.nextLine();

            switch (menuChoice) {   
                case "2" -> {
                    boolean isExiting = false;

                    while (!isExiting) {
                        productsMenu();
                        String productsChoice = scanner.nextLine();
                    
                        switch (productsChoice) {
                            case "1" -> {
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
                                    scanner.nextLine();
                                    break;
                                }

                                Product product = new Product(name, categoryId, price, quantity, supplierId);

                                if (productDAO.addProduct(product))
                                    System.out.println("Product added successfully!");
                                else
                                    System.out.println("Failed to add product.");
                            }

                            case "2" -> {
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

                            case "3" -> {
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
                                isExiting = true;
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }

                case "1" -> {
                    boolean isExiting = false;

                    while (!isExiting) {
                        ordersMenu();
                        String ordersChoice = scanner.nextLine();
                    
                        switch (ordersChoice) {
                            case "1" -> {
                                System.out.println("\n--- Create Order ---");
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
                                    System.out.print("Enter Product ID: ");
                                    productID = scanner.nextInt();
                                    scanner.nextLine();
                                    System.out.print("Enter Quantity Bought: ");
                                    quantity = scanner.nextInt();
                                    scanner.nextLine();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Try again.");
                                    scanner.nextLine();
                                    break;
                                }

                                Customer customer = new Customer(firstName, lastName, contact, email);
                                Product productBought = productDAO.getProductById(productID);

                                if (productBought != null) {                 
                                    Order orders = new Order(customer.getCustomerId(), productID, quantity, productBought, customer);

                                    if (orderDAO.createOrder(customer, orders))
                                        System.out.println("Order created successfully!");
                                    else
                                        System.out.println("Failed to create order.");

                                } else {
                                    System.out.println("Product does not exist.");
                                }     
                            }

                            case "2" -> {
                                System.out.println("\n========================================");
                                System.out.println("             ALL ORDERS                 ");
                                System.out.println("========================================");
                                ArrayList<Order> orders = orderDAO.getAllOrders();

                                if (orders.isEmpty()) {
                                    System.out.println("No orders found.");
                                } else {
                                    System.out.printf("%-8s %-12s %-12s %-10s %-12s %-15s %s%n",
                                        "OrderID", "CustomerID", "ProductID", "Quantity", "OrderDate", "Total", "Status");
                                    System.out.println("--------------------------------------------------------------------------------------");

                                    for (Order order : orders)
                                        System.out.printf("%-8d %-12d %-12d %-10d %-12s Php %-11.2f %s%n", order.getOrderId(), order.getCustomerId(), order.getProductId(),
                                                                                                    order.getQuantity(), order.getOrderDate(), order.getTotal(), order.getStatus());

                                    System.out.println("======================================================================================");
                                    System.out.println("Total Orders: " + orders.size());
                                }
                            }

                            case "3" -> {
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

                                scanner.nextLine();
                                Order existing = orderDAO.getOrderById(oid);

                                if (existing == null) {
                                    System.out.println("Order not found.");
                                } else {
                                    System.out.println("\n--- Edit Order Status of: OID #" + existing.getOrderId() + " ---");
                                    System.out.println("1 -> Change to \'COMPLETED\'");
                                    System.out.println("2 -> Change to \'REFUNDED\'");
                                    System.out.println("0 -> Exit");
                                    System.out.print("\nEnter choice: ");
                                    String choice = scanner.nextLine();

                                    switch (choice) {
                                        case "1" -> {
                                            if (existing.getStatus() != Order.Status.COMPLETED) {
                                                existing.setStatus(Order.Status.COMPLETED);
                                                orderDAO.editOrderStatus(existing);
                                                Product productToModify = orderDAO.getProductSoldInOrderById(existing.getOrderId());
                                                productDAO.updateProductStock(productToModify , productToModify.getQuantityInStock() - existing.getQuantity());
                                                System.out.println("Successfully changed status.");
                                            } else {
                                                System.out.println("Cannot change status");
                                            }
                                        }

                                        case "2" -> {
                                            if (existing.getStatus() != Order.Status.REFUNDED) {
                                                existing.setStatus(Order.Status.REFUNDED);
                                                orderDAO.editOrderStatus(existing);
                                                Product productToModify = orderDAO.getProductSoldInOrderById(existing.getOrderId());
                                                productDAO.updateProductStock(productToModify , productToModify.getQuantityInStock() + existing.getQuantity());
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
                                isExiting = true;
                                break;
                            }
                            
                            default -> System.out.println("Invalid choice!");
                        }
                    }
                }

                case "0" -> {
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
        System.out.println("1 -> Orders Menu");
        System.out.println("2 -> Products Menu");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    private static void productsMenu() {
        System.out.println("\n========================================");
        System.out.println("             PRODUCTS MENU    ");
        System.out.println("========================================");
        System.out.println("1 -> Add Product");
        System.out.println("2 -> View All Products");
        System.out.println("3 -> Update Product Stock");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }

    private static void ordersMenu() {
        System.out.println("\n========================================");
        System.out.println("              ORDERS MENU    ");
        System.out.println("========================================");
        System.out.println("1 -> Create Order");
        System.out.println("2 -> View All Orders");
        System.out.println("3 -> Edit Order");
        System.out.println("\n0 -> Exit");
        System.out.println("========================================");
        System.out.print("Enter choice: ");
    }
}
