<?php
    include("database.php");

    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $product_id = $_POST["productId"];
        $new_stock  = $_POST["newStock"];

        // Check if product exists
        $check = mysqli_query($conn, "SELECT * FROM products WHERE product_id = $product_id");

        if (!is_numeric($new_stock) || $new_stock < 0) {
            $message = "Stock cannot be negative.";
        } else {
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
    }
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Update Stock</title>

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
    <h1>Update Product Stock</h1>

    <div class="table-container">
    <?php if (!empty($message)) echo "<p><strong>$message</strong></p>"; ?>

    <form action="<?php echo htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="post">
        <label>Product ID:</label><br>
        <input type="number" name="productId" required><br><br>

        <label>New Stock Amount:</label><br>
        <input type="number" name="newStock" min="0" required><br><br>

        <button type="submit">Update Stock</button>
    </form>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php mysqli_close($conn); ?>
