<%@ page import="java.sql.*, java.util.*" %>
<%@page import="DBUtil"%> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String message = "";
    
    if ("POST".equalsIgnoreCase(request.getMethod())) {
        
        String name      = request.getParameter("product_name");
        String category  = request.getParameter("category_id");
        String priceStr  = request.getParameter("price");
        String stockStr  = request.getParameter("quantity_in_stock");
        String supplier  = request.getParameter("supplier_id");

        try {
            int categoryId = Integer.parseInt(category);
            double price = Double.parseDouble(priceStr);
            int stock = Integer.parseInt(stockStr);
            int supplierId = Integer.parseInt(supplier);

            try (Connection conn = DBUtil.getConnection()) {
                
                String insertSQL = "INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id) VALUES (?, ?, ?, ?, ?)";
                
                try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
                    pstmt.setString(1, name);
                    pstmt.setInt(2, categoryId);
                    pstmt.setDouble(3, price);
                    pstmt.setInt(4, stock);
                    pstmt.setInt(5, supplierId);
    
                    // 4. Execute the update
                    int rowsAffected = pstmt.executeUpdate();
    
                    if (rowsAffected > 0) {
                        message = "Product added successfully!";
                    } else {
                        message = "Error adding product: No rows affected.";
                    }
                } 
            } 

        } catch (NumberFormatException e) {
            message = "Error: Invalid number format for ID, Price, or Quantity.";
            e.printStackTrace();
        } catch (SQLException e) {
            message = "Error adding product: " + e.getMessage();
            e.printStackTrace();
        } catch (Exception e) {
            message = "A general error occurred: " + e.getMessage();
            e.printStackTrace();
        }
    }
    request.setAttribute("message", message);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>

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

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        
        input[type="text"], 
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
            font-size: 1.1em;
            margin-bottom: 15px;
            text-align: center;
        }

        .message-error {
            color: red;
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
    <h1>Add Product</h1>

    <div class="form-container">
        <h2>Enter Product Details</h2>

        <c:choose>
            <c:when test="${not empty message && message.contains('successfully')}">
                <p class="message-success"><strong><c:out value="${message}" /></strong></p>
            </c:when>
            <c:when test="${not empty message}">
                <p class="message-error"><strong><c:out value="${message}" /></strong></p>
            </c:when>
        </c:choose>

        <form action="addProduct.jsp" method="post">
        
            <label for="product_name">Product Name:</label>
            <input type="text" id="product_name" name="product_name" required>

            <label for="category_id">Category ID:</label>
            <input type="number" id="category_id" name="category_id" required>

            <label for="price">Price:</label>
            <input type="number" id="price" name="price" step="0.01" required>

            <label for="quantity_in_stock">Quantity in Stock:</label>
            <input type="number" id="quantity_in_stock" name="quantity_in_stock" required>

            <label for="supplier_id">Supplier ID:</label>
            <input type="number" id="supplier_id" name="supplier_id" required>

            <button type="submit">Add Product</button>
        </form>
    </div>

    <a href="index.jsp" class="back-link">Back to Home</a>
</body>
</html>