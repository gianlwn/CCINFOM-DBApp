<%@page import="java.sql.*"%>
<%@page import="DBUtil"%> 
<%@ page import="java.util.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String message = "";
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        
        String productIdStr = request.getParameter("productId");
        String newStockStr  = request.getParameter("newStock");

        if (productIdStr == null || newStockStr == null || productIdStr.isEmpty() || newStockStr.isEmpty()) {
            message = "Error: Both Product ID and New Stock must be provided.";
        } else {
            try {
                int productId = Integer.parseInt(productIdStr);
                int newStock  = Integer.parseInt(newStockStr);

                try (Connection conn = DBUtil.getConnection()) {
                    
                    String checkSQL = "SELECT 1 FROM products WHERE product_id = ?";
                    try (PreparedStatement checkStmt = conn.prepareStatement(checkSQL)) {
                        checkStmt.setInt(1, productId);
                        
                        try (ResultSet rs = checkStmt.executeQuery()) {
                            if (!rs.next()) {
                                message = "Product ID not found.";
                            } else {
                                String updateSQL = "UPDATE products SET quantity_in_stock = ? WHERE product_id = ?";
                                
                                try (PreparedStatement updateStmt = conn.prepareStatement(updateSQL)) {
                                    updateStmt.setInt(1, newStock);
                                    updateStmt.setInt(2, productId);
                
                                    int rowsAffected = updateStmt.executeUpdate();
                
                                    if (rowsAffected > 0) {
                                        message = "Stock updated successfully!";
                                    } else {
                                        message = "Error updating stock: Product ID found but update failed.";
                                    }
                                }
                            }
                        }
                    } 
                } 
            } catch (NumberFormatException e) {
                message = "Error: Product ID and New Stock must be valid integers.";
                e.printStackTrace();
            } catch (SQLException e) {
                message = "Database Error: " + e.getMessage();
                e.printStackTrace();
            } catch (Exception e) {
                message = "An unexpected error occurred: " + e.getMessage();
                e.printStackTrace();
            }
        }
    }
    request.setAttribute("message", message);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Stock</title>
</head>
<body>
    <h1>Update Product Stock</h1>

    <c:if test="${not empty message}">
        <p><strong><c:out value="${message}" /></strong></p>
    </c:if>

    <form action="updateStock.jsp" method="post">
        <label>Product ID:</label><br>
        <input type="number" name="productId" required><br><br>

        <label>New Stock Amount:</label><br>
        <input type="number" name="newStock" required><br><br>

        <button type="submit">Update Stock</button>
    </form>

    <br><a href="index.jsp">Back to Home</a>
</body>
</html>