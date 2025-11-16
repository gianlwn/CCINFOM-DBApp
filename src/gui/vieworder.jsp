<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="DBUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Map<String, Object>> orders = new ArrayList<>();

    try (Connection conn = DBUtil.getConnection()) {
        String sql = "SELECT * FROM orders";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            Map<String, Object> order = new HashMap<>();
            order.put("order_id", rs.getInt("order_id"));
            order.put("customer_id", rs.getInt("customer_id"));
            order.put("product_id", rs.getInt("product_id"));
            order.put("quantity", rs.getInt("quantity"));
            order.put("total", rs.getDouble("total"));
            order.put("order_date", rs.getTimestamp("order_date"));
            order.put("status", rs.getString("status"));
            orders.add(order);
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Orders</title>
</head>
<body>
    <h1>All Orders</h1>

    <table border="1" cellpadding="6">
        <tr>
            <th>Order ID</th>
            <th>Customer</th>
            <th>Product</th>
            <th>Quantity</th>
            <th>Total</th>
            <th>Date</th>
            <th>Status</th>
        </tr>

        <% for (Map<String, Object> o : orders) { %>
            <tr>
                <td><%= o.get("order_id") %></td>
                <td><%= o.get("customer_id") %></td>
                <td><%= o.get("product_id") %></td>
                <td><%= o.get("quantity") %></td>
                <td><%= o.get("total") %></td>
                <td><%= o.get("order_date") %></td>
                <td><%= o.get("status") %></td>
            </tr>
        <% } %>
    </table>

    <br>
    <a href="index.jsp">Back to Home</a>
</body>
</html>
