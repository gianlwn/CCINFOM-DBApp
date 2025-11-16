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
    <h1>Update Product Stock</h1>

    <div class="form-container">
        <h2>Change Inventory</h2>

        <c:choose>
            <c:when test="${not empty message && message.contains('successfully')}">
                <p class="message-success"><c:out value="${message}" /></p>
            </c:when>
            <c:when test="${not empty message}">
                <p class="message-error"><c:out value="${message}" /></p>
            </c:when>
        </c:choose>

        <form action="updateStock.jsp" method="post">
            <label for="productId">Product ID:</label>
            <input type="number" name="productId" id="productId" required>

            <label for="newStock">New Stock Amount:</label>
            <input type="number" name="newStock" id="newStock" required>

            <button type="submit">Update Stock</button>
        </form>
    </div>

    <a href="index.jsp" class="back-link">Back to Home</a>
</body>
</html>