<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>

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
    <h1>Add Product</h1>

    <?php 
        if (!empty($message)) {
            echo "<p><strong>$message</strong></p>";
        }
    ?>
    <div class="table-container">
    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
    
        <label>Product Name:</label><br>
        <input type="text" name="product_name" required><br><br>

        <label>Category ID:</label><br>
        <input type="number" name="category_id" required><br><br>

        <label>Price:</label><br>
        <input type="number" name="price" step="0.01" required><br><br>

        <label>Quantity in Stock:</label><br>
        <input type="number" name="quantity_in_stock" required><br><br>

        <label>Supplier ID:</label><br>
        <input type="number" name="supplier_id" required><br><br>

        <button type="submit">Add Product</button>
    </form>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $name      = $_POST["product_name"];
        $category  = $_POST["category_id"];
        $price     = $_POST["price"];
        $stock     = $_POST["quantity_in_stock"];
        $supplier  = $_POST["supplier_id"];


        if(empty($name) || empty($category) || empty($price) || empty($stock) || empty($supplier)) {
            $message = "All fields are required.";
        }
        elseif($category <= 1000 || $category >= 2000){
            $message = "Enter a valid Category ID.";
        }
        elseif($price <= 0){
            $message = "Enter a valid price.";
        }
        elseif($stock < 0){
            $message = "Enter a valid quantity.";
        }
        elseif($supplier <= 2000 || $supplier >= 3000){
            $message = "Enter a valid Supplier ID.";
        }
        else {
            // Insert product
            $insert = "
                INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id)
                VALUES ('$name', '$category', '$price', '$stock', '$supplier')
            ";

            if (mysqli_query($conn, $insert)) {
                $message = "Product added successfully!";
            } else {
                $error_code = mysqli_errno($conn);
                $error_msg  = mysqli_error($conn);

                if ($error_code === 1452) {
                    if (strpos($error_msg, 'category_id') !== false) {
                        $message = "Category ID does not exist. Please enter a valid Category ID.";
                    } elseif (strpos($error_msg, 'supplier_id') !== false) {
                        $message = "Supplier ID does not exist. Please enter a valid Supplier ID.";
                    } else {
                        $message = "Foreign key error: Check category and supplier IDs.";
                    }

                } else {
                    $message = "Error adding product: $error_msg";
                }
            }
        }   
    }
?>

<?php mysqli_close($conn); ?>
