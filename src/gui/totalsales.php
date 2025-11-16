<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Total Sales</title>

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
            width: 90%;
            margin: 25px auto;
            background: white;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 3px 10px rgba(0,0,0,0.15);
        }

        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 15px;
        }

        th, td {
            padding: 12px;
            border-bottom: 1px solid #eee;
        }

        th {
            background: #e3e8f0;
            font-weight: bold;
        }

        tr:nth-child(even) {
            background: #f7f7f7;
        }

        .status-refunded {
            color: red;
            font-weight: bold;
        }

        .back-link {
            display: inline-block;
            margin: 20px auto;
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
    <h1>Total Sales</h1>

    <div class="table-container">
    <?php
        // Get total sales only from completed orders
        $query = "SELECT SUM(total) AS total_sales FROM orders WHERE status = 'COMPLETED'";
        $result = mysqli_query($conn, $query);
        $row = mysqli_fetch_assoc($result);

        $total_sales = $row['total_sales'];

        if ($total_sales === null) {
            echo "<h2>Php 0.00</h2>";
        } else {
            echo "<h2>Php " . number_format($total_sales, 2) . "</h2>";
        }
    ?>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>