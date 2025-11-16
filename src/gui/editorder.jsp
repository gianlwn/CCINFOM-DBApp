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
</head>
<body>
    <h1>Edit Order</h1>

    <% if (!message.isEmpty()) { %>
        <p><strong><%= message %></strong></p>
    <% } %>

    <form action="editorder.jsp" method="post">
        <label>Order ID:</label><br>
        <input type="number" name="orderId" required><br><br>

        <label>New Quantity:</label><br>
        <input type="number" name="newQuantity" min="1" required><br><br>

        <button type="submit">Update Order</button>
    </form>

    <br>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
