<?php include("database.php"); ?>

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
    <h1>All Products</h1>
    <div class="table-container">
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
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>