<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Products</title>
</head>
<body>
    <h1>All Products</h1>

    <table border="1" cellpadding="6">
        <tr>
            <th>Product ID</th>
            <th>Name</th>
            <th>Category ID</th>
            <th>Price</th>
            <th>Stock</th>
            <th>Supplier ID</th>
        </tr>

        <?php
            $query = "SELECT * FROM products";   // <-- CHANGE TABLE NAME HERE
            $result = mysqli_query($conn, $query);

            while ($row = mysqli_fetch_assoc($result)) {
                echo "<tr>";
                echo "<td>".$row['product_id']."</td>";
                echo "<td>".$row['product_name']."</td>";
                echo "<td>".$row['category_id']."</td>";
                echo "<td>".$row['price']."</td>";
                echo "<td>".$row['quantity_in_stock']."</td>";
                echo "<td>".$row['supplier_id']."</td>";
                echo "</tr>";
            }
        ?>

    </table>
    <br><a href="index.php">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>