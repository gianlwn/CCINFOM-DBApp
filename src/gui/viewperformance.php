<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Performance</title>
    <style>
        body {
            font-family: Arial, sans-serif; background: #f5f5f5; text-align: center; margin: 0; padding: 0;
        }

        h1 {
            background: #4a6fa5; color: white; padding: 20px 0; margin: 0; font-size: 32px;
        }
    
        .table-container {
            width: 90%; margin: 25px auto; background: white; padding: 20px; border-radius: 12px; box-shadow: 0px 3px 10px rgba(0,0,0,0.15);
        }

        table {
            width: 100%; border-collapse: collapse; margin-top: 15px;
        }

        th, td {
            padding: 12px; border-bottom: 1px solid #eee;
        }

        th {
            background: #e3e8f0; font-weight: bold;
        }

        tr:nth-child(even) {
            background: #f7f7f7;
        }

        .back-link {
            display: inline-block; margin: 20px auto; padding: 10px 20px; background: #999; color: white; text-decoration: none; border-radius: 8px; transition: 0.2s;
        }

        .back-link:hover {
            background: #777;
        }
    </style>
</head>
<body>
    <h1>Product Performance</h1>
    <div class="table-container">
        <table border="1" cellpadding="6">
            <tr>
                <th>Product ID</th>
                <th>Name</th>
                <th>Total Quantity Sold</th>
                <th>Total Revenue</th>
            </tr>

            <?php
                $query = "
                SELECT p.product_id, p.product_name, IFNULL(SUM(o.quantity), 0) AS total_sold, IFNULL(SUM(o.quantity * p.price), 0) AS total_revenue
                FROM products p LEFT JOIN orders o ON p.product_id = o.product_id 
                GROUP BY p.product_id, p.product_name 
                ORDER BY total_sold DESC, p.product_id ASC;";
            

                $result = mysqli_query($conn, $query);

                while ($row = mysqli_fetch_assoc($result)) {
                    echo "<tr>";
                    echo "<td>".$row['product_id']."</td>";
                    echo "<td>".$row['product_name']."</td>";
                    echo "<td>".$row['total_sold']."</td>";
                    echo "<td>Php ".number_format($row['total_revenue'], 2)."</td>";
                    echo "</tr>";
                }
            ?>
        </table>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>
