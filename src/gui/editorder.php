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

    <?php if (!empty($message)) echo "<p><strong>$message</strong></p>"; ?>

    <div class="table-container">
    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
        <label>Order ID:</label><br>
        <input type="number" name="orderId" required><br><br>

        <label>New Quantity:</label><br>
        <input type="number" name="newQuantity" min="1" required><br><br>

        <label>Order Status:</label><br>
        <select name="status" required>
            <option value="COMPLETED">COMPLETED</option>
            <option value="REFUNDED">REFUNDED</option>
        </select><br><br>

        <button type="submit">Update Order</button>
    </form>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php
    // If form submitted, process update
    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $order_id = $_POST["orderId"];
        $new_qty  = $_POST["newQuantity"];
        $new_status = $_POST["status"];

        // Check if order exists
        $check = mysqli_query($conn, "SELECT * FROM orders WHERE order_id = $order_id");

        if (mysqli_num_rows($check) == 0) {
            $message = "Order ID not found.";
        } else {
            // Update quantity + recalculate total
            $order = mysqli_fetch_assoc($check);
            $product_id = $order["product_id"];

            // Get product price
            $price_query = mysqli_query($conn, 
                "SELECT price FROM products WHERE product_id = $product_id"
            );
            $price_row = mysqli_fetch_assoc($price_query);
            $price = $price_row["price"];

            $new_total = $new_qty * $price;

            // Update order
            $update = "
                UPDATE orders 
                SET quantity = $new_qty,
                    total = $new_total,
                    status = '$new_status'
                WHERE order_id = $order_id
            ";

            if (mysqli_query($conn, $update)) {
                $message = "Order updated successfully!";
            } else {
                $message = "Error updating: " . mysqli_error($conn);
            }
        }
    }
?>

<?php mysqli_close($conn); ?>
