<?php
    include("database.php");

    $message = "";
    $message_type = "error";
?>

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
        if ($message !== "") {
            $bg  = ($message_type === "success") ? "#e9f7ef" : "#fdecea";
            $col = ($message_type === "success") ? "#1e7e34" : "#b00020";
            echo "<div style='padding:12px;border-radius:8px;margin-bottom:12px;background:$bg;color:$col;'>"
            . htmlspecialchars($message)
            . "</div>";
        }
?>
    <div class="table-container">
    <form action="<?php echo htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post">
    
        <label>Product Name:</label><br>
        <input type="text" name="product_name" required oninput="this.setCustomValidity('')"oninvalid="this.setCustomValidity('Please enter a product name.')"><br><br>

        <label>Category ID:</label><br>
        <input type="number" name="category_id" min="1001" max="1999" required oninput="this.setCustomValidity('')"oninvalid="this.setCustomValidity('Category ID must be between 1001 and 1999.')"><br><br>

        <label>Price:</label><br>
        <input type="number" name="price" step="0.01" min="0.01" required oninput="this.setCustomValidity('')"oninvalid="this.setCustomValidity(this.validity.valueMissing ? 'Please enter a price.' : 'Price must be at least 0.01.')"><br><br>

        <label>Quantity in Stock:</label><br>
        <input type="number" name="quantity_in_stock" min="0" required oninput="this.setCustomValidity('')"oninvalid="this.setCustomValidity(this.validity.valueMissing ? 'Please enter a quantity.' : 'Value must be greater than or equal to 0.')"><br><br>

        <label>Supplier ID:</label><br>
        <input type="number" name="supplier_id" min="2001" max="2999" required oninput="this.setCustomValidity('')"oninvalid="this.setCustomValidity('Supplier ID must be between 2001 and 2999.')"><br><br>

        <button type="submit">Add Product</button>
    </form>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
</body>
</html>

<?php
    if ($_SERVER["REQUEST_METHOD"] === "POST") {

        $name     = trim($_POST["product_name"] ?? "");
        $category = $_POST["category_id"] ?? "";
        $price    = $_POST["price"] ?? "";
        $stock    = $_POST["quantity_in_stock"] ?? "";
        $supplier = $_POST["supplier_id"] ?? "";


        if(empty($name) || empty($category) || empty($price) || empty($stock) || empty($supplier)) {
            $message = "All fields are required.";
            $message_type = "error";
        }
        elseif($category <= 1000 || $category >= 2000){
            $message = "Enter a valid Category ID.";
            $message_type = "error";
        }
        elseif($price <= 0){
            $message = "Enter a valid price.";
            $message_type = "error";
        }
        elseif($stock < 0){
            $message = "Enter a valid quantity.";
            $message_type = "error";
        }
        elseif($supplier <= 2000 || $supplier >= 3000){
            $message = "Enter a valid Supplier ID.";
            $message_type = "error";
        }
        else {
            // Insert product
            $insert = "
                INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id)
                VALUES ('$name', '$category', '$price', '$stock', '$supplier')
            ";

            if (mysqli_query($conn, $insert)) {
                $message = "Product added successfully!";
                $message_type = "success";
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

                $message_type = "error";
            }
        }   
    }
?>

<?php mysqli_close($conn); ?>
