import java.util.Scanner;
import java.sql.Date;

public class DBApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Select option to add data:");
        System.out.println("1 -> Add Category");
        System.out.println("2 -> Add Supplier");
        System.out.println("3 -> Add Product");
        System.out.print("Enter choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1 -> {
                CategoryDAO categoryDAO = new CategoryDAO();
                Category category = new Category();

                System.out.print("Enter Category ID: ");
                category.setCategoryId(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Category Name: ");
                category.setCategoryName(scanner.nextLine());

                if (categoryDAO.insertCategory(category)) {
                    System.out.println("Category added successfully.");
                } else {
                    System.out.println("Failed to add category.");
                }
            }
            case 2 -> {
                SupplierDAO supplierDAO = new SupplierDAO();
                Supplier supplier = new Supplier();

                System.out.print("Enter Supplier ID: ");
                supplier.setSupplierId(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Supplier Name: ");
                supplier.setSupplierName(scanner.nextLine());
                System.out.print("Enter Contact Person: ");
                supplier.setContactPerson(scanner.nextLine());
                System.out.print("Enter Contact Number: ");
                supplier.setContactNumber(scanner.nextLine());
                System.out.print("Enter Email: ");
                supplier.setEmail(scanner.nextLine());
                System.out.print("Enter Address: ");
                supplier.setAddress(scanner.nextLine());
                System.out.print("Enter Last Delivery Date (YYYY-MM-DD): ");
                supplier.setLastDeliveryDate(Date.valueOf(scanner.nextLine()));

                if (supplierDAO.insertSupplier(supplier)) {
                    System.out.println("Supplier added successfully.");
                } else {
                    System.out.println("Failed to add supplier.");
                }
            }
            case 3 -> {
                ProductDAO productDAO = new ProductDAO();
                Product product = new Product();

                System.out.print("Enter Product ID: ");
                product.setProductId(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Product Name: ");
                product.setProductName(scanner.nextLine());
                System.out.print("Enter Category ID: ");
                product.setCategoryId(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Description: ");
                product.setDescription(scanner.nextLine());
                System.out.print("Enter Price: ");
                product.setPrice(scanner.nextDouble());
                scanner.nextLine();
                System.out.print("Enter Quantity In Stock: ");
                product.setQuantityInStock(scanner.nextInt());
                scanner.nextLine();
                System.out.print("Enter Supplier ID: ");
                product.setSupplierId(scanner.nextInt());
                scanner.nextLine();

                if (productDAO.insertProduct(product)) {
                    System.out.println("Product added successfully.");
                } else {
                    System.out.println("Failed to add product.");
                }
            }
            default -> System.out.println("Invalid choice. Exiting.");
        }

        scanner.close();
    }
}
