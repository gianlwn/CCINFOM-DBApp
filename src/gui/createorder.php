<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create an Order</title>

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
    <h1>Create New Order</h1>
    <div class="table-container">

    <form action="<?php htmlspecialchars($_SERVER["PHP_SELF"]); ?>" method="POST">

        <label for="first_name">First Name:</label><br>
        <input type="text" name="first_name" id="first_name" required><br><br>

        <label for="last_name">Last Name:</label><br>
        <input type="text" name="last_name" id="last_name" required><br><br>

        <label for="email">Email:</label><br>
        <input type="text" name="email" id="email"><br><br>

        <label for="contact_number">Contact Number:</label><br>
        <input type="text" name="contact_number" id="contact_number"><br><br>

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


        <input type="submit" value="Submit Order">
    </form>
    </div>
    <a href="index.php" class="back-link">Back to Home</a>
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
        if (!empty($_POST["contact_number"])) {
            if (!ctype_digit($_POST["contact_number"])) {
                echo "<p style='color:red; font-weight:bold;'>Error: Contact number must contain digits only.</p>";
                exit; // stop the entire process
            } else {
                $contact = "'".$_POST["contact_number"]."'";
            }
        } else {
            $contact = "NULL";
        }

        // Fetch product stock
        $stock_query = "SELECT quantity_in_stock FROM products WHERE product_id = $product_id";
        $stock_result = mysqli_query($conn, $stock_query);
        $stock_row = mysqli_fetch_assoc($stock_result);
        $current_stock = $stock_row['quantity_in_stock'];

        if($quantity > $current_stock){
            echo "Select valid quantity in the stock.";
        }
        elseif($product_id <= 3000 || $product_id >= 4000){
            echo "Enter a valid Product ID.";
        }
        elseif(empty($first) || empty($last)){
            echo "Name cannot be empty.";
        }
        else{

            // Insert customer into customers table
            $insert_customer = "INSERT INTO customers (first_name, last_name, contact_number, email)
                                VALUES ('$first', '$last', $contact, $email)";
            mysqli_query($conn, $insert_customer);

            // Get the auto-incremented customer_id
            $customer_id = mysqli_insert_id($conn);

            // Get product price
            $price_query = "SELECT price FROM products WHERE product_id = $product_id";
            $price_result = mysqli_query($conn, $price_query);
            $price_row = mysqli_fetch_assoc($price_result);
            $price = $price_row['price'];

            // Calculate total with discounts
            $currentMonth = date('n');  // 1-12
            $currentDay = date('j');    // 1-31

            if ($currentMonth == $currentDay) {
                $total = ($quantity * $price) * 0.90; // 10% off
            } elseif (($currentMonth == 12 && $currentDay == 25) || ($currentMonth == 6 && $currentDay == 12)) {
                $total = ($quantity * $price) * 0.80; // 20% off
            } else {
                $total = $quantity * $price;
            }

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