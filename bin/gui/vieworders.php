<?php include("database.php"); ?>

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

        <?php
            $query = "SELECT * FROM orders";   // <-- CHANGE TABLE NAME HERE
            $result = mysqli_query($conn, $query);

            while ($row = mysqli_fetch_assoc($result)) {
                echo "<tr>";
                echo "<td>".$row['order_id']."</td>";
                echo "<td>".$row['customer_id']."</td>";
                echo "<td>".$row['product_id']."</td>";
                echo "<td>".$row['quantity']."</td>";
                echo "<td>".$row['total']."</td>";
                echo "<td>".$row['order_date']."</td>";
                echo "<td>".$row['status']."</td>";
                echo "</tr>";
            }
        ?>

    </table>
    <br><a href="index.php">Back to Home</a>
</body>
</html>


<?php mysqli_close($conn); ?>

