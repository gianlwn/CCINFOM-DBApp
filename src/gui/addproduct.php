<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add Product</title>
</head>
<body>
    <h1>Add Product</h1>

    <?php 
        if (!empty($message)) {
            echo "<p><strong>$message</strong></p>";
        }
    ?>

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

    <br><a href="index.php">Back to Home</a>
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
        elseif($category <= 0){
            $message = "Enter a valid Category ID.";
        }
        elseif($price <= 0){
            $message = "Enter a valid price.";
        }
        elseif($stock < 0){
            $message = "Enter a valid quantity.";
        }
        elseif($supplier <= 0){
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
                $message = "Error adding product: " . mysqli_error($conn);
            }
        }   
    }
?>

<?php mysqli_close($conn); ?>
