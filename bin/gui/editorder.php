<?php include("database.php"); $message = ""; ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Order</title>

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
    <h1>Edit Order</h1>

    <?php 
    if (!empty($message)) {
        if (strpos($message, 'Error:') === 0) echo "<div class='error-message'>$message</div>";
        else echo "<div class='success-message'>$message</div>";
    }
    ?>

    <div class="table-container">
    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
        <label>Order ID:</label>
        <input type="number" name="orderId" required><br><br>

        <label>Order Status:</label>
        <select name="status" required>
            <option value="REFUNDED">REFUNDED</option>
        </select><br><br>

        <button type="submit">Update Order</button>
    </form>
    </div>

    <a href="index.php" class="back-link">Back to Home</a>
    </body>
</html>

<?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {
        $order_id = $_POST["orderId"];

        // Fetch order info
        $order_query = mysqli_query($conn, "SELECT * FROM orders WHERE order_id = $order_id");
        if (mysqli_num_rows($order_query) == 0) {
            $message = "Error: Order ID not found.";
        } else {
            $order = mysqli_fetch_assoc($order_query);
            if ($order['status'] === "REFUNDED") {
                $message = "Error: This order is already refunded.";
            } else {
                $product_id = $order['product_id'];
                $quantity = $order['quantity'];

                // Update product stock
                $stock_query = mysqli_query($conn, "SELECT quantity_in_stock FROM products WHERE product_id = $product_id");
                if (mysqli_num_rows($stock_query) == 0) {
                    $message = "Error: Product not found.";
                } else {
                    $stock_row = mysqli_fetch_assoc($stock_query);
                    $new_stock = $stock_row['quantity_in_stock'] + $quantity;

                    // Update products table
                    mysqli_query($conn, "UPDATE products SET quantity_in_stock = $new_stock WHERE product_id = $product_id");

                    // Update order status
                    if (mysqli_query($conn, "UPDATE orders SET status = 'REFUNDED' WHERE order_id = $order_id")) {
                        $message = "Order refunded successfully! $quantity items have been returned to stock.";
                    } else {
                        $message = "Error updating order status: " . mysqli_error($conn);
                    }
                }
            }
        }
    }

    mysqli_close($conn);
?>