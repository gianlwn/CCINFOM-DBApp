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
</head>
<body>
    <h1>All Products</h1>
    
    <c:if test="${not empty errorMessage}">
        <p style="color:red;"><strong>Error: <c:out value="${errorMessage}" /></strong></p>
    </c:if>

    <c:choose>
        <c:when test="${empty productsList}">
            <p>No products found in the database.</p>
        </c:when>
        
        <c:otherwise>
            <table border="1" cellpadding="6">
                <tr>
                    <th>Product ID</th>
                    <th>Name</th>
                    <th>Category ID</th>
                    <th>Price</th>
                    <th>Stock</th>
                    <th>Supplier ID</th>
                </tr>
    
                <c:forEach var="product" items="${productsList}">
                    <tr>
                        <td><c:out value="${product.id}" /></td>
                        <td><c:out value="${product.name}" /></td>
                        <td><c:out value="${product.category}" /></td>
                        <td><c:out value="${product.price}" /></td>
                        <td><c:out value="${product.stock}" /></td>
                        <td><c:out value="${product.supplier}" /></td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>

    <br><a href="index.jsp">Back to Home</a>
</body>
</html>