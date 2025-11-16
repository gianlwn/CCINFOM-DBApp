<?php include("database.php"); ?>

<?php
    // Fetch product stock
    $stock_query = "SELECT quantity_in_stock FROM products WHERE product_id = $product_id";
    $stock_result = mysqli_query($conn, $stock_query);
    $stock_row = mysqli_fetch_assoc($stock_result);
    $current_stock = $stock_row['quantity_in_stock'];
?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an Order</title>
</head>

<body>
    <h1>Create New Order</h1>

    <form action="<?php htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="POST">

        <label for="first_name">First Name:</label><br>
        <input type="text" name="first_name" id="first_name" required><br><br>

        <label for="last_name">Last Name:</label><br>
        <input type="text" name="last_name" id="last_name" required><br><br>

        <label for="email">Email:</label><br>
        <input type="text" name="email" id="email" required><br><br>

        <label for="contact_number">Contact Number:</label><br>
        <input type="text" name="contact_number" id="contact_number" required><br><br>

        <label for="product_id">Product:</label><br>
        <select name="product_id" id="product_id" required>
            <option value="">Select Product</option>
            <?php 
                $query = "SELECT product_id, product_name FROM products";
                $result = mysqli_query($conn, $query);

                while ($row = mysqli_fetch_assoc($result)) {
                    $selected = (isset($_POST['product_id']) && $_POST['product_id'] == $row['product_id']) ? "selected" : "";
                    echo "<option value='".$row['product_id']."' $selected>".$row['product_name']."</option>";
                }
            ?>
        </select><br><br>

        <label for="quantity">Quantity:</label><br>
        <input 
            type="number" 
            id="quantity" 
            name="quantity" 
            min="1" 
            max="<?php echo $current_stock; ?>" 
            required
        >
        <p>Available stock: <?php echo $current_stock; ?></p>


        <input type="submit" value="Submit Order">
    </form>

    <br><a href="index.php">Back to Home</a>
</body>
</html>

<?php
    if ($_SERVER["REQUEST_METHOD"] == "POST") {

        // Get inputs
        $first = $_POST["first_name"];
        $last = $_POST["last_name"];
        $product_id = $_POST["product_id"];
        $quantity = $_POST["quantity"];

        // Optional fields
        $email = !empty($_POST["email"]) ? "'".$_POST["email"]."'" : "NULL";
        $contact = !empty($_POST["contact_number"]) ? "'".$_POST["contact_number"]."'" : "NULL";

        if($product_id <= 3000 || $product_id >= 4000){
            echo "Enter a valid Product ID.";
        }
        elseif(empty($first) || empty($last)){
            echo "Name cannot be empty.";
        }
        else{

            // Insert customer into customers table
            $insert_customer = "INSERT INTO customers (first_name, last_name, contact_number, email)
                                VALUES ('$first', '$last', '$contact', '$email')";

            mysqli_query($conn, $insert_customer);

            // Get the auto-incremented customer_id
            $customer_id = mysqli_insert_id($conn);

            // Get product price
            $price_query = "SELECT price FROM products WHERE product_id = $product_id";
            $price_result = mysqli_query($conn, $price_query);
            $price_row = mysqli_fetch_assoc($price_result);
            $price = $price_row['price'];

            $total = $price * $quantity;

            // Insert order using the newly created customer_id
            $insert_order = "INSERT INTO orders (customer_id, product_id, quantity, total, order_date)
                            VALUES ('$customer_id', '$product_id', '$quantity', '$total', NOW())";

            if (mysqli_query($conn, $insert_order)) {

                // update product stock
                $new_stock = $current_stock - $quantity;
                $update_stock = "UPDATE products SET quantity_in_stock = $new_stock WHERE product_id = $product_id";
                mysqli_query($conn, $update_stock);

                echo "Order created successfully!";
            } else {
                echo "Error: " . mysqli_error($conn);
            }
        }
        
    }

    mysqli_close($conn);
?>