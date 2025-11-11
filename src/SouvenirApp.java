import java.util.ArrayList;
import java.util.Scanner;

public class SouvenirApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CategoryDAO categoryDAO = new CategoryDAO();
        SupplierDAO supplierDAO = new SupplierDAO();
        ProductDAO productDAO = new ProductDAO();
        CustomerDAO customerDAO = new CustomerDAO();
        OrderDetailsDAO orderDetailsDAO = new OrderDetailsDAO();

        while (true) {
            System.out.println("\n========================================");
            System.out.println("    SOUVENIR SHOP MANAGEMENT SYSTEM    ");
            System.out.println("========================================");
            System.out.println("[ADD RECORDS]");
            // for the products, put all in one button
            System.out.println("1 -> Add Product"); // change
            System.out.println("2 -> Add Customer"); // change to Create Order
            System.out.println("\n[VIEW RECORDS]");
            System.out.println("3 -> View All Products"); // change
            System.out.println("4 -> View All Orders");
            System.out.println("\n[UPDATE RECORDS]");
            System.out.println("5 -> Update Product"); // change
            // add Edit Order
            System.out.println("\n0 -> Exit");
            System.out.println("========================================");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {   
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
                    if (productDAO.addProduct(product)) {
                        System.out.println("Product added successfully!");
                    } else {
                        System.out.println("Failed to add product.");
                    }
                }
                
                case 2 -> {
                    System.out.println("\n--- Add Customer ---");
                    System.out.print("Enter First Name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Enter Last Name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    Customer customer = new Customer(firstName, lastName, contact, email);
                    if (customerDAO.addCustomer(customer)) {
                        System.out.println("Customer added successfully!");
                    } else {
                        System.out.println("Failed to add customer.");
                    }
                }

                case 3 -> {
                    System.out.println("\n========================================");
                    System.out.println("              ALL PRODUCTS              ");
                    System.out.println("========================================");
                    ArrayList<Product> products = productDAO.getAllProducts();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.printf("%-5s %-20s %-15s %-10s %-10s%n", "ID", "Name", "Price", "Stock");
                        System.out.println("------------------------------------------------------------------------");
                        for (Product p : products) {
                            System.out.printf("%-5d %-20s $%-9.2f %-10d%n",
                                p.getProductId(), p.getProductName(), p.getPrice(), p.getQuantityInStock());
                        }
                        System.out.println("========================================================================");
                        System.out.println("Total Products: " + products.size());
                    }
                }

                case 4 -> {
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
                        for (OrderDetails od : orders) {
                            System.out.printf("%-8d %-12d %-12d %-10d %-12s $%-11.2f%n",
                                od.getOrderId(), od.getCustomerId(), od.getProductId(),
                                od.getQuantity(), od.getOrderDate(), od.getUnitPrice());
                        }
                        System.out.println("============================================================================");
                        System.out.println("Total Orders: " + orders.size());
                    }
                }   
 
                case 5 -> {
                    System.out.println("\n--- Update Product ---");
                    System.out.print("Enter Product ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Product existing = productDAO.getProductById(id);
                    if (existing == null) {
                        System.out.println("Product not found.");
                    } else {
                        System.out.println("Current Name: " + existing.getProductName());
                        System.out.print("Enter New Product Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter New Category ID: ");
                        int categoryId = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter New Quantity In Stock: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter New Supplier ID: ");
                        int supplierId = scanner.nextInt();
                        existing.setProductName(name);
                        existing.setCategoryId(categoryId);
                        existing.setPrice(price);
                        existing.setQuantityInStock(quantity);
                        existing.setSupplierId(supplierId);
                        if (productDAO.updateProduct(existing)) {
                            System.out.println("Product updated successfully!");
                        } else {
                            System.out.println("Failed to update product.");
                        }
                    }
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
}
