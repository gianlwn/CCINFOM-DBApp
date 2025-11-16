<?php
    include("database.php");

    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $product_id = $_POST["productId"];
        $new_stock  = $_POST["newStock"];

        // Check if product exists
        $check = mysqli_query($conn, "SELECT * FROM products WHERE product_id = $product_id");

        if (mysqli_num_rows($check) == 0) {
            $message = "Product ID not found.";
        } else {
            // Update stock
            $update = "
                UPDATE products 
                SET quantity_in_stock = $new_stock
                WHERE product_id = $product_id
            ";

            if (mysqli_query($conn, $update)) {
                $message = "Stock updated successfully!";
            } else {
                $message = "Error updating stock: " . mysqli_error($conn);
            }
        }
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Stock</title>
</head>
<body>
    <h1>Update Product Stock</h1>

    <!-- Display messages -->
    <?php if (!empty($message)) echo "<p><strong>$message</strong></p>"; ?>

    <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
        <label>Product ID:</label><br>
        <input type="number" name="productId" required><br><br>

        <label>New Stock Amount:</label><br>
        <input type="number" name="newStock" required><br><br>

        <button type="submit">Update Stock</button>
    </form>

    <br><a href="index.php">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>
