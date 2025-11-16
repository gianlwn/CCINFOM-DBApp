<%@page import="java.sql.*, java.util.*" %>
<%@page import="DBUtil" %> 
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<Map<String, Object>> products = new ArrayList<>();
    String errorMessage = null;

    try (Connection conn = DBUtil.getConnection()) {
        
        String query = "SELECT product_id, product_name, category_id, price, quantity_in_stock, supplier_id FROM products";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Map<String, Object> product = new HashMap<>();
                product.put("id", rs.getInt("product_id"));
                product.put("name", rs.getString("product_name"));
                product.put("category", rs.getInt("category_id"));
                product.put("price", rs.getDouble("price"));
                product.put("stock", rs.getInt("quantity_in_stock"));
                product.put("supplier", rs.getInt("supplier_id"));
                products.add(product);
            }
        } 
    } catch (Exception e) {
        e.printStackTrace();
        errorMessage = "Error retrieving products: " + e.getMessage();
    }
    
    request.setAttribute("productsList", products);
    request.setAttribute("errorMessage", errorMessage);
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Products</title>
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
            width: 80%;
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
    <h1>All Products</h1>

    <div class="table-container">
        
        <c:if test="${not empty errorMessage}">
            <p class="message-error"><strong>Error: <c:out value="${errorMessage}" /></strong></p>
        </c:if>

        <c:choose>
            <c:when test="${empty productsList}">
                <p>No products found in the database.</p>
            </c:when>
            
            <c:otherwise>
                <table>
                    <thead>
                        <tr>
                            <th>Product ID</th>
                            <th>Name</th>
                            <th>Category ID</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Supplier ID</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="product" items="${productsList}">
                            <tr>
                                <td><c:out value="${product.id}" /></td>
                                <td><c:out value="${product.name}" /></td>
                                <td><c:out value="${product.category}" /></td>
                                <td>$ <c:out value="${product.price}" /></td>
                                <td><c:out value="${product.stock}" /></td>
                                <td><c:out value="${product.supplier}" /></td>
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