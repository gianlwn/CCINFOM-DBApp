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
        $new_status = $_POST["status"];

        // Check if order exists
        $check = mysqli_query($conn, "SELECT * FROM orders WHERE order_id = $order_id");

        if (mysqli_num_rows($check) == 0) {
            $message = "Order ID not found.";
        } else {
            $order = mysqli_fetch_assoc($check);
            $current_status = $order["status"];
            $current_qty = $order["quantity"];
            $product_id = $order["product_id"];

            // Prevent refunded orders from switching to completed
            if ($current_status == "REFUNDED" && $new_status == "COMPLETED") {
                $message = "Error: Refunded orders cannot be changed to COMPLETED status.";
            } else {
                // First, let's check what columns exist in the products table
                $product_columns = mysqli_query($conn, "SHOW COLUMNS FROM products");
                $has_quantity = false;
                $has_stock = false;
                
                while($column = mysqli_fetch_assoc($product_columns)) {
                    if ($column['Field'] == 'quantity') $has_quantity = true;
                    if ($column['Field'] == 'stock') $has_stock = true;
                }

                // Handle refund - return quantity to products
                if ($new_status == "REFUNDED" && $current_status != "REFUNDED") {
                    // Determine which column to update
                    if ($has_quantity) {
                        // Get current product quantity
                        $qty_query = mysqli_query($conn, 
                            "SELECT quantity FROM products WHERE product_id = $product_id"
                        );
                        $qty_row = mysqli_fetch_assoc($qty_query);
                        $current_product_qty = $qty_row["quantity"];
                        
                        // Calculate new quantity (return the order quantity)
                        $new_product_qty = $current_product_qty + $current_qty;
                        
                        // Update product quantity
                        $update_product = "
                            UPDATE products 
                            SET quantity = $new_product_qty
                            WHERE product_id = $product_id
                        ";
                    } elseif ($has_stock) {
                        // Get current product stock
                        $stock_query = mysqli_query($conn, 
                            "SELECT stock FROM products WHERE product_id = $product_id"
                        );
                        $stock_row = mysqli_fetch_assoc($stock_query);
                        $current_product_stock = $stock_row["stock"];
                        
                        // Calculate new stock (return the order quantity)
                        $new_product_stock = $current_product_stock + $current_qty;
                        
                        // Update product stock
                        $update_product = "
                            UPDATE products 
                            SET stock = $new_product_stock
                            WHERE product_id = $product_id
                        ";
                    } else {
                        // If neither quantity nor stock column exists, skip inventory update
                        $update_product = true;
                    }
                    
                    if ($update_product !== true && !mysqli_query($conn, $update_product)) {
                        $message = "Error updating product inventory: " . mysqli_error($conn);
                        mysqli_close($conn);
                        exit();
                    }
                }
                
                // Handle completed order - deduct quantity if switching from refunded
                if ($new_status == "COMPLETED" && $current_status == "REFUNDED") {
                    // Determine which column to update
                    if ($has_quantity) {
                        // Get current product quantity
                        $qty_query = mysqli_query($conn, 
                            "SELECT quantity FROM products WHERE product_id = $product_id"
                        );
                        $qty_row = mysqli_fetch_assoc($qty_query);
                        $current_product_qty = $qty_row["quantity"];
                        
                        // Check if enough quantity available
                        if ($current_product_qty >= $current_qty) {
                            $new_product_qty = $current_product_qty - $current_qty;
                            
                            // Update product quantity
                            $update_product = "
                                UPDATE products 
                                SET quantity = $new_product_qty
                                WHERE product_id = $product_id
                            ";
                        } else {
                            $message = "Error: Not enough product quantity available to complete this order.";
                            mysqli_close($conn);
                            exit();
                        }
                    } elseif ($has_stock) {
                        // Get current product stock
                        $stock_query = mysqli_query($conn, 
                            "SELECT stock FROM products WHERE product_id = $product_id"
                        );
                        $stock_row = mysqli_fetch_assoc($stock_query);
                        $current_product_stock = $stock_row["stock"];
                        
                        // Check if enough stock available
                        if ($current_product_stock >= $current_qty) {
                            $new_product_stock = $current_product_stock - $current_qty;
                            
                            // Update product stock
                            $update_product = "
                                UPDATE products 
                                SET stock = $new_product_stock
                                WHERE product_id = $product_id
                            ";
                        } else {
                            $message = "Error: Not enough stock available to complete this order.";
                            mysqli_close($conn);
                            exit();
                        }
                    } else {
                        // If neither quantity nor stock column exists, skip inventory update
                        $update_product = true;
                    }
                    
                    if ($update_product !== true && !mysqli_query($conn, $update_product)) {
                        $message = "Error updating product inventory: " . mysqli_error($conn);
                        mysqli_close($conn);
                        exit();
                    }
                }

                // Update order status only (quantity removed from form)
                $update = "
                    UPDATE orders 
                    SET status = '$new_status'
                    WHERE order_id = $order_id
                ";

                if (mysqli_query($conn, $update)) {
                    $message = "Order updated successfully!";
                } else {
                    $message = "Error updating: " . mysqli_error($conn);
                }
            }
        }
    }
?>

<?php mysqli_close($conn); ?>