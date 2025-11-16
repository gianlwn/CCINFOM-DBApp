<%@ page import="java.sql.*, java.util.*" %>
<%@ page import="DBUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    // Variables for product list and feedback message
    List<Map<String, Object>> products = new ArrayList<>();
    String message = "";

    // Get product list from database
    try (Connection conn = DBUtil.getConnection()) {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT product_id, product_name FROM products");
        while (rs.next()) {
            Map<String, Object> product = new HashMap<>();
            product.put("id", rs.getInt("product_id"));
            product.put("name", rs.getString("product_name"));
            products.add(product);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Handle form submission
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String first = request.getParameter("first_name");
        String last = request.getParameter("last_name");
        String productIdStr = request.getParameter("product_id");
        String quantityStr = request.getParameter("quantity");

        if (first != null && last != null && productIdStr != null && quantityStr != null) {
            int productId = Integer.parseInt(productIdStr);
            int quantity = Integer.parseInt(quantityStr);

            try (Connection conn = DBUtil.getConnection()) {

                // Insert customer
                String insertCustomerSQL = "INSERT INTO customers (first_name, last_name) VALUES (?, ?)";
                PreparedStatement psCustomer = conn.prepareStatement(insertCustomerSQL, Statement.RETURN_GENERATED_KEYS);
                psCustomer.setString(1, first);
                psCustomer.setString(2, last);
                psCustomer.executeUpdate();

                ResultSet rsCustomer = psCustomer.getGeneratedKeys();
                int customerId = 0;
                if (rsCustomer.next()) {
                    customerId = rsCustomer.getInt(1);
                }

                // Get product price
                String priceSQL = "SELECT price FROM products WHERE product_id = ?";
                PreparedStatement psPrice = conn.prepareStatement(priceSQL);
                psPrice.setInt(1, productId);
                ResultSet rsPrice = psPrice.executeQuery();
                double price = 0;
                if (rsPrice.next()) {
                    price = rsPrice.getDouble("price");
                }

                double total = price * quantity;

                // Insert order
                String insertOrderSQL = "INSERT INTO orders (customer_id, product_id, quantity, total, order_date) VALUES (?, ?, ?, ?, NOW())";
                PreparedStatement psOrder = conn.prepareStatement(insertOrderSQL);
                psOrder.setInt(1, customerId);
                psOrder.setInt(2, productId);
                psOrder.setInt(3, quantity);
                psOrder.setDouble(4, total);

                int rows = psOrder.executeUpdate();
                if (rows > 0) {
                    message = "Order created successfully!";
                } else {
                    message = "Error creating order!";
                }

            } catch (Exception e) {
                e.printStackTrace();
                message = "Error: " + e.getMessage();
            }
        }
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an Order</title>
</head>
<body>
    <h1>Create New Order</h1>

    <form action="createorder.jsp" method="POST">
        <label for="first_name">First Name:</label><br>
        <input type="text" name="first_name" id="first_name" required><br><br>

        <label for="last_name">Last Name:</label><br>
        <input type="text" name="last_name" id="last_name" required><br><br>

        <label for="product_id">Product:</label><br>
        <select name="product_id" id="product_id" required>
            <option value="">Select Product</option>
            <% for (Map<String,Object> p : products) { %>
                <option value="<%= p.get("id") %>"><%= p.get("name") %></option>
            <% } %>
        </select><br><br>

        <label for="quantity">Quantity:</label><br>
        <input type="number" id="quantity" name="quantity" min="1" required><br><br>

        <input type="submit" value="Submit Order">
    </form>

    <br>
    <a href="index.jsp">Back to Home</a>

    <p style="color:green;"><%= message %></p>
</body>
</html>
