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

    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f5f5f5;
            margin: 0;
            padding: 0;
            text-align: center;
        }

        h1 {
            background: #4a6fa5;
            color: white;
            padding: 20px 0;
            margin: 0;
            font-size: 32px;
        }

        
        .form-container {
            background: white;
            width: 350px;
            margin: 25px auto;
            padding: 25px;
            border-radius: 12px;
            box-shadow: 0px 3px 10px rgba(0,0,0,0.15);
            text-align: left;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        
        input[type="text"], 
        input[type="number"],
        select {
            width: 95%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
            font-size: 16px;
        }
        
        input[type="submit"] {
            width: 100%;
            padding: 12px;
            background: #4a6fa5;
            color: white;
            border: none;
            border-radius: 8px;
            cursor: pointer;
            transition: 0.2s;
            font-size: 16px;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background: #365887;
            transform: translateY(-2px);
        }
        
        .message-success {
            color: green;
            font-weight: bold;
            font-size: 1.1em;
            margin-bottom: 15px;
            text-align: center;
        }

        .message-error {
            color: red;
            font-weight: bold;
            font-size: 1.1em;
            margin-bottom: 15px;
            text-align: center;
        }
        
        .back-link {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #999;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: 0.2s;
        }

        .back-link:hover {
            background: #777;
        }
    </style>
</head>

<body>
    <h1>Create New Order</h1>

    <div class="form-container">
        <h2>Customer Order</h2>
        
        <%-- Message Display --%>
        <% 
            if (message != null && !message.isEmpty()) {
                String cssClass = message.contains("successfully") ? "message-success" : "message-error";
                out.println("<p class=\"" + cssClass + "\">" + message + "</p>");
            }
        %>
        
        <form action="createorder.jsp" method="POST">
            <label for="first_name">First Name:</label>
            <input type="text" name="first_name" id="first_name" required>

            <label for="last_name">Last Name:</label>
            <input type="text" name="last_name" id="last_name" required>

            <label for="product_id">Product:</label>
            <select name="product_id" id="product_id" required>
                <option value="">Select Product</option>
                <% for (Map<String,Object> p : products) { %>
                    <option value="<%= p.get("id") %>"><%= p.get("name") %></option>
                <% } %>
            </select>

            <label for="quantity">Quantity:</label>
            <input type="number" id="quantity" name="quantity" min="1" required>

            <input type="submit" value="Submit Order">
        </form>
    </div>

    <a href="index.jsp" class="back-link">Back to Home</a>
</body>
</html>
