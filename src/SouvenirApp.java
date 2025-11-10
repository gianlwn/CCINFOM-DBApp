import java.sql.Date;
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
            // Display Menu
            System.out.println("\n========================================");
            System.out.println("    SOUVENIR SHOP MANAGEMENT SYSTEM    ");
            System.out.println("========================================");
            System.out.println("[ADD RECORDS]");
            System.out.println("1 -> Add Category");
            System.out.println("2 -> Add Supplier");
            System.out.println("3 -> Add Product");
            System.out.println("4 -> Add Customer");
            System.out.println("\n[VIEW RECORDS]");
            System.out.println("5 -> View All Categories");
            System.out.println("6 -> View All Suppliers");
            System.out.println("7 -> View All Products");
            System.out.println("8 -> View All Customers");
            System.out.println("9 -> View All Orders");
            System.out.println("\n[SEARCH RECORDS]");
            System.out.println("10 -> Search Category by ID");
            System.out.println("11 -> Search Supplier by ID");
            System.out.println("12 -> Search Product by ID");
            System.out.println("13 -> Search Customer by ID");
            System.out.println("\n[UPDATE RECORDS]");
            System.out.println("14 -> Update Category");
            System.out.println("15 -> Update Supplier");
            System.out.println("16 -> Update Product");
            System.out.println("17 -> Update Customer");
            System.out.println("\n[DELETE RECORDS]");
            System.out.println("18 -> Delete Category");
            System.out.println("19 -> Delete Supplier");
            System.out.println("20 -> Delete Product");
            System.out.println("21 -> Delete Customer");
            System.out.println("\n0 -> Exit");
            System.out.println("========================================");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1 -> { // Add Category
                    System.out.println("\n--- Add Category ---");
                    System.out.print("Enter Category ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Category Name: ");
                    String name = scanner.nextLine();
                    Category category = new Category(id, name);
                    if (categoryDAO.insertCategory(category)) {
                        System.out.println("Category added successfully!");
                    } else {
                        System.out.println("Failed to add category.");
                    }
                }
                
                case 2 -> { // Add Supplier
                    System.out.println("\n--- Add Supplier ---");
                    System.out.print("Enter Supplier ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Supplier Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Contact Person: ");
                    String contactPerson = scanner.nextLine();
                    System.out.print("Enter Contact Number: ");
                    String contact = scanner.nextLine();
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter Address: ");
                    String address = scanner.nextLine();
                    System.out.print("Enter Last Delivery Date (YYYY-MM-DD): ");
                    String dateStr = scanner.nextLine();
                    Date lastDeliveryDate = Date.valueOf(dateStr);
                    Supplier supplier = new Supplier(id, name, contactPerson, contact, email, address, lastDeliveryDate);
                    if (supplierDAO.insertSupplier(supplier)) {
                        System.out.println("Supplier added successfully!");
                    } else {
                        System.out.println("Failed to add supplier.");
                    }
                }
                
                case 3 -> { // Add Product
                    System.out.println("\n--- Add Product ---");
                    System.out.print("Enter Product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Product Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Category ID: ");
                    int categoryId = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String description = scanner.nextLine();
                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    System.out.print("Enter Quantity In Stock: ");
                    int quantity = scanner.nextInt();
                    System.out.print("Enter Supplier ID: ");
                    int supplierId = scanner.nextInt();
                    Product product = new Product(id, name, categoryId, description, price, quantity, supplierId);
                    if (productDAO.insertProduct(product)) {
                        System.out.println("Product added successfully!");
                    } else {
                        System.out.println("Failed to add product.");
                    }
                }
                
                case 4 -> { // Add Customer
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
                    if (customerDAO.insertCustomer(customer)) {
                        System.out.println("Customer added successfully!");
                    } else {
                        System.out.println("Failed to add customer.");
                    }
                }
                
                case 5 -> { // View All Categories
                    System.out.println("\n========================================");
                    System.out.println("           ALL CATEGORIES              ");
                    System.out.println("========================================");
                    ArrayList<Category> categories = categoryDAO.getAllCategories();
                    if (categories.isEmpty()) {
                        System.out.println("No categories found.");
                    } else {
                        System.out.printf("%-10s %-30s%n", "ID", "Category Name");
                        System.out.println("----------------------------------------");
                        for (Category c : categories) {
                            System.out.printf("%-10d %-30s%n", c.getCategoryId(), c.getCategoryName());
                        }
                        System.out.println("========================================");
                        System.out.println("Total Categories: " + categories.size());
                    }
                }
                
                case 6 -> { // View All Suppliers
                    System.out.println("\n========================================");
                    System.out.println("             ALL SUPPLIERS              ");
                    System.out.println("========================================");
                    ArrayList<Supplier> suppliers = supplierDAO.getAllSuppliers();
                    if (suppliers.isEmpty()) {
                        System.out.println("No suppliers found.");
                    } else {
                        System.out.printf("%-5s %-20s %-20s %-15s %-25s%n", "ID", "Name", "Contact Person", "Contact #", "Email");
                        System.out.println("---------------------------------------------------------------------------------------------");
                        for (Supplier s : suppliers) {
                            System.out.printf("%-5d %-20s %-20s %-15s %-25s%n",
                                s.getSupplierId(), s.getSupplierName(), s.getContactPerson(),
                                s.getContactNumber(), s.getEmail());
                        }
                        System.out.println("=============================================================================================");
                        System.out.println("Total Suppliers: " + suppliers.size());
                    }
                }
                
                case 7 -> { // View All Products
                    System.out.println("\n========================================");
                    System.out.println("              ALL PRODUCTS              ");
                    System.out.println("========================================");
                    ArrayList<Product> products = productDAO.getAllProductsDetailed();
                    if (products.isEmpty()) {
                        System.out.println("No products found.");
                    } else {
                        System.out.printf("%-5s %-20s %-15s %-10s %-10s%n", "ID", "Name", "Description", "Price", "Stock");
                        System.out.println("------------------------------------------------------------------------");
                        for (Product p : products) {
                            System.out.printf("%-5d %-20s %-15s $%-9.2f %-10d%n",
                                p.getProductId(), p.getProductName(), p.getDescription(),
                                p.getPrice(), p.getQuantityInStock());
                        }
                        System.out.println("========================================================================");
                        System.out.println("Total Products: " + products.size());
                    }
                }
                
                case 8 -> { // View All Customers
                    System.out.println("\n========================================");
                    System.out.println("            ALL CUSTOMERS               ");
                    System.out.println("========================================");
                    ArrayList<Customer> customers = customerDAO.getAllCustomers();
                    if (customers.isEmpty()) {
                        System.out.println("No customers found.");
                    } else {
                        System.out.printf("%-5s %-15s %-15s %-15s %-25s%n", "ID", "First Name", "Last Name", "Contact", "Email");
                        System.out.println("--------------------------------------------------------------------------------");
                        for (Customer c : customers) {
                            System.out.printf("%-5d %-15s %-15s %-15s %-25s%n",
                                c.getCustomerId(), c.getFirstName(), c.getLastName(),
                                c.getContactNumber(), c.getEmail());
                        }
                        System.out.println("================================================================================");
                        System.out.println("Total Customers: " + customers.size());
                    }
                }
                
                case 9 -> { // View All Orders
                    System.out.println("\n========================================");
                    System.out.println("             ALL ORDERS                 ");
                    System.out.println("========================================");
                    ArrayList<OrderDetails> orders = orderDetailsDAO.getAllOrderDetails();
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
                
                case 10 -> { // Search Category by ID
                    System.out.println("\n--- Search Category ---");
                    System.out.print("Enter Category ID: ");
                    int id = scanner.nextInt();
                    Category category = categoryDAO.getCategoryById(id);
                    if (category != null) {
                        System.out.println("\n--- Category Found ---");
                        System.out.println("ID: " + category.getCategoryId());
                        System.out.println("Name: " + category.getCategoryName());
                    } else {
                        System.out.println("Category not found.");
                    }
                }
                
                case 11 -> { // Search Supplier by ID
                    System.out.println("\n--- Search Supplier ---");
                    System.out.print("Enter Supplier ID: ");
                    int id = scanner.nextInt();
                    Supplier supplier = supplierDAO.getSupplierById(id);
                    if (supplier != null) {
                        System.out.println("\n--- Supplier Found ---");
                        System.out.println("ID: " + supplier.getSupplierId());
                        System.out.println("Name: " + supplier.getSupplierName());
                        System.out.println("Contact Person: " + supplier.getContactPerson());
                        System.out.println("Contact Number: " + supplier.getContactNumber());
                        System.out.println("Email: " + supplier.getEmail());
                        System.out.println("Address: " + supplier.getAddress());
                        System.out.println("Last Delivery Date: " + supplier.getLastDeliveryDate());
                    } else {
                        System.out.println("Supplier not found.");
                    }
                }
                
                case 12 -> { // Search Product by ID
                    System.out.println("\n--- Search Product ---");
                    System.out.print("Enter Product ID: ");
                    int id = scanner.nextInt();
                    Product product = productDAO.getProductById(id);
                    if (product != null) {
                        System.out.println("\n--- Product Found ---");
                        System.out.println("ID: " + product.getProductId());
                        System.out.println("Name: " + product.getProductName());
                        System.out.println("Description: " + product.getDescription());
                        System.out.println("Price: $" + product.getPrice());
                        System.out.println("Stock: " + product.getQuantityInStock());
                    } else {
                        System.out.println("Product not found.");
                    }
                }
                
                case 13 -> { // Search Customer by ID
                    System.out.println("\n--- Search Customer ---");
                    System.out.print("Enter Customer ID: ");
                    int id = scanner.nextInt();
                    Customer customer = customerDAO.getCustomerById(id);
                    if (customer != null) {
                        System.out.println("\n--- Customer Found ---");
                        System.out.println("ID: " + customer.getCustomerId());
                        System.out.println("Name: " + customer.getFirstName() + " " + customer.getLastName());
                        System.out.println("Contact: " + customer.getContactNumber());
                        System.out.println("Email: " + customer.getEmail());
                    } else {
                        System.out.println("Customer not found.");
                    }
                }
                
                case 14 -> { // Update Category
                    System.out.println("\n--- Update Category ---");
                    System.out.print("Enter Category ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Category existing = categoryDAO.getCategoryById(id);
                    if (existing == null) {
                        System.out.println("Category not found.");
                    } else {
                        System.out.println("Current Name: " + existing.getCategoryName());
                        System.out.print("Enter New Category Name: ");
                        String newName = scanner.nextLine();
                        existing.setCategoryName(newName);
                        if (categoryDAO.updateCategory(existing)) {
                            System.out.println("Category updated successfully!");
                        } else {
                            System.out.println("Failed to update category.");
                        }
                    }
                }
                
                case 15 -> { // Update Supplier
                    System.out.println("\n--- Update Supplier ---");
                    System.out.print("Enter Supplier ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Supplier existing = supplierDAO.getSupplierById(id);
                    if (existing == null) {
                        System.out.println("Supplier not found.");
                    } else {
                        System.out.println("Current Name: " + existing.getSupplierName());
                        System.out.print("Enter New Supplier Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter New Contact Person: ");
                        String contactPerson = scanner.nextLine();
                        System.out.print("Enter New Contact Number: ");
                        String contact = scanner.nextLine();
                        System.out.print("Enter New Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter New Address: ");
                        String address = scanner.nextLine();
                        System.out.print("Enter New Last Delivery Date (YYYY-MM-DD): ");
                        String dateStr = scanner.nextLine();
                        Date lastDeliveryDate = Date.valueOf(dateStr);
                        existing.setSupplierName(name);
                        existing.setContactPerson(contactPerson);
                        existing.setContactNumber(contact);
                        existing.setEmail(email);
                        existing.setAddress(address);
                        existing.setLastDeliveryDate(lastDeliveryDate);
                        if (supplierDAO.updateSupplier(existing)) {
                            System.out.println("Supplier updated successfully!");
                        } else {
                            System.out.println("Failed to update supplier.");
                        }
                    }
                }
                
                case 16 -> { // Update Product
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
                        System.out.print("Enter New Description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter New Price: ");
                        double price = scanner.nextDouble();
                        System.out.print("Enter New Quantity In Stock: ");
                        int quantity = scanner.nextInt();
                        System.out.print("Enter New Supplier ID: ");
                        int supplierId = scanner.nextInt();
                        existing.setProductName(name);
                        existing.setCategoryId(categoryId);
                        existing.setDescription(description);
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
                
                case 17 -> { // Update Customer
                    System.out.println("\n--- Update Customer ---");
                    System.out.print("Enter Customer ID to update: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    Customer existing = customerDAO.getCustomerById(id);
                    if (existing == null) {
                        System.out.println("Customer not found.");
                    } else {
                        System.out.println("Current Name: " + existing.getFirstName() + " " + existing.getLastName());
                        System.out.print("Enter New First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Enter New Last Name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Enter New Contact Number: ");
                        String contact = scanner.nextLine();
                        System.out.print("Enter New Email: ");
                        String email = scanner.nextLine();
                        existing.setFirstName(firstName);
                        existing.setLastName(lastName);
                        existing.setContactNumber(contact);
                        existing.setEmail(email);
                        if (customerDAO.updateCustomer(existing)) {
                            System.out.println("Customer updated successfully!");
                        } else {
                            System.out.println("Failed to update customer.");
                        }
                    }
                }
                
                case 18 -> { // Delete Category
                    System.out.println("\n--- Delete Category ---");
                    System.out.print("Enter Category ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        if (categoryDAO.deleteCategory(id)) {
                            System.out.println("Category deleted successfully!");
                        } else {
                            System.out.println("Failed to delete category.");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                }
                
                case 19 -> { // Delete Supplier
                    System.out.println("\n--- Delete Supplier ---");
                    System.out.print("Enter Supplier ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        if (supplierDAO.deleteSupplier(id)) {
                            System.out.println("Supplier deleted successfully!");
                        } else {
                            System.out.println("Failed to delete supplier.");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                }
                
                case 20 -> { // Delete Product
                    System.out.println("\n--- Delete Product ---");
                    System.out.print("Enter Product ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        if (productDAO.deleteProduct(id)) {
                            System.out.println("Product deleted successfully!");
                        } else {
                            System.out.println("Failed to delete product.");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
                    }
                }
                
                case 21 -> { // Delete Customer
                    System.out.println("\n--- Delete Customer ---");
                    System.out.print("Enter Customer ID to delete: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();
                    System.out.print("Are you sure? (yes/no): ");
                    String confirm = scanner.nextLine();
                    if (confirm.equalsIgnoreCase("yes")) {
                        if (customerDAO.deleteCustomer(id)) {
                            System.out.println("Customer deleted successfully!");
                        } else {
                            System.out.println("Failed to delete customer.");
                        }
                    } else {
                        System.out.println("Deletion cancelled.");
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
