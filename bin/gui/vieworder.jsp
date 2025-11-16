<%@ page import="java.sql.*" %>
<%@ page import="DBUtil" %>
<%@ page import="java.util.*" %>
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

        .table-container {
            width: 85%;
            margin: 25px auto;
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 3px 10px rgba(0,0,0,0.15);
            overflow-x: auto; 
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin: 0 auto;
        }

        th {
            background-color: #4a6fa5;
            color: white;
            padding: 12px;
            text-align: left;
            border: 1px solid #365887;
        }

        td {
            padding: 10px 12px;
            border: 1px solid #ddd;
            text-align: left;
            color: #333;
        }

        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        
        tr:hover {
            background-color: #e6e6e6;
        }
        
        .message-error {
            color: red;
            font-weight: bold;
            font-size: 1.1em;
            margin: 15px 0;
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
    <h1>All Orders</h1>

    <div class="table-container">
        <c:if test="${not empty errorMessage}">
            <p class="message-error"><strong><c:out value="${errorMessage}" /></strong></p>
        </c:if>

        <c:choose>
            <c:when test="${empty ordersList}">
                <p>No orders found in the database.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Order ID</th>
                            <th>Customer</th>
                            <th>Product</th>
                            <th>Quantity</th>
                            <th>Total</th>
                            <th>Date</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="order" items="${ordersList}">
                            <tr>
                                <td><c:out value="${order.order_id}" /></td>
                                <td><c:out value="${order.customer_id}" /></td>
                                <td><c:out value="${order.product_id}" /></td>
                                <td><c:out value="${order.quantity}" /></td>
                                <td>$ <c:out value="${order.total}" /></td>
                                <td><c:out value="${order.order_date}" /></td>
                                <td><c:out value="${order.status}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>

    <a href="index.jsp" class="back-link">Back to Home</a>
</body>
</html>