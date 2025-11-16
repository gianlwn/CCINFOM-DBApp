<?php
    include("database.php");

    // If form submitted, process update
    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $order_id = $_POST["orderId"];
        $new_qty  = $_POST["newQuantity"];

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
                SET quantity = $new_qty, total = $new_total 
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

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Order</title>
</head>
<body>
    <h1>Edit Order</h1>

    <?php if (!empty($message)) echo "<p><strong>$message</strong></p>"; ?>

    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
        <label>Order ID:</label><br>
        <input type="number" name="orderId" required><br><br>

        <label>New Quantity:</label><br>
        <input type="number" name="newQuantity" min="1" required><br><br>

        <button type="submit">Update Order</button>
    </form>

    <br><a href="index.php">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>
