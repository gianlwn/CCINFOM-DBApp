<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Total Sales</title>
</head>
<body>
    <h1>Total Sales</h1>

    <?php
        // Get total sales only from completed orders
        $query = "SELECT SUM(total) AS total_sales FROM orders WHERE status = 'COMPLETED'";
        $result = mysqli_query($conn, $query);
        $row = mysqli_fetch_assoc($result);

        $total_sales = $row['total_sales'];

        if ($total_sales === null) {
            echo "<h2>Php0.00</h2>";
        } else {
            echo "<h2>Php" . number_format($total_sales, 2) . "</h2>";
        }
    ?>

    <br><a href="index.php">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>