<%@ page import="java.sql.*" %>
<%@ page import="DBUtil" %>
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String message = "";

    if ("POST".equalsIgnoreCase(request.getMethod())) {
        String orderIdStr = request.getParameter("orderId");
        String newQtyStr = request.getParameter("newQuantity");

        if (orderIdStr != null && newQtyStr != null) {
            int orderId = Integer.parseInt(orderIdStr);
            int newQty = Integer.parseInt(newQtyStr);

            try (Connection conn = DBUtil.getConnection()) {

                // Check if order exists
                String checkSQL = "SELECT product_id FROM orders WHERE order_id = ?";
                PreparedStatement psCheck = conn.prepareStatement(checkSQL);
                psCheck.setInt(1, orderId);
                ResultSet rsCheck = psCheck.executeQuery();

                if (!rsCheck.next()) {
                    message = "Order ID not found.";
                } else {
                    int productId = rsCheck.getInt("product_id");

                    // Get product price
                    String priceSQL = "SELECT price FROM products WHERE product_id = ?";
                    PreparedStatement psPrice = conn.prepareStatement(priceSQL);
                    psPrice.setInt(1, productId);
                    ResultSet rsPrice = psPrice.executeQuery();

                    double price = 0;
                    if (rsPrice.next()) {
                        price = rsPrice.getDouble("price");
                    }

                    double newTotal = price * newQty;

                    // Update order
                    String updateSQL = "UPDATE orders SET quantity = ?, total = ? WHERE order_id = ?";
                    PreparedStatement psUpdate = conn.prepareStatement(updateSQL);
                    psUpdate.setInt(1, newQty);
                    psUpdate.setDouble(2, newTotal);
                    psUpdate.setInt(3, orderId);

                    int rows = psUpdate.executeUpdate();
                    if (rows > 0) {
                        message = "Order updated successfully!";
                    } else {
                        message = "Error updating order.";
                    }
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
    <title>Edit Order</title>

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
        
        input[type="number"] {
            width: 95%;
            padding: 10px;
            margin-bottom: 15px;
            border: 1px solid #ccc;
            border-radius: 6px;
            box-sizing: border-box;
        }
        
        button[type="submit"] {
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

        button[type="submit"]:hover {
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
    <h1>Edit Order</h1>

    <div class="form-container">
        <h2>Update Order Quantity</h2>

        <% 
            String msg = (String) request.getAttribute("message");
            if (msg != null && !msg.isEmpty()) {
                String cssClass = msg.contains("successfully") ? "message-success" : "message-error";
                out.println("<p class=\"" + cssClass + "\">" + msg + "</p>");
            }
        %>

        <form action="editorder.jsp" method="post">
            <label for="orderId">Order ID:</label>
            <input type="number" name="orderId" id="orderId" required>

            <label for="newQuantity">New Quantity:</label>
            <input type="number" name="newQuantity" id="newQuantity" min="1" required>

            <button type="submit">Update Order</button>
        </form>
    </div>

    <a href="index.jsp" class="back-link">Back to Home</a>
</body>
</html>
