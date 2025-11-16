<?php include("database.php"); ?>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>SAGE Souvenir Shop</title>

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

        .menu-section {
            background: white;
            width: 300px;
            margin: 25px auto;
            padding: 20px;
            border-radius: 12px;
            box-shadow: 0px 3px 10px rgba(0,0,0,0.15);
        }

        .menu-section h2 {
            margin-top: 0;
            color: #333;
        }

        .menu-list {
            list-style: none;
            padding: 0;
        }

        .menu-list a {
            display: block;
            margin: 10px 0;
            padding: 12px;
            background: #4a6fa5;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: 0.2s;
            font-size: 16px;
        }

        .menu-list a:hover {
            background: #365887;
            transform: translateY(-2px);
        }
    </style>
</head>

<body>
    <form action="<?php htmlspecialchars($_SERVER['PHP_SELF']); ?>" method="post"></form>
    <h1>SAGE Souvenir Shop</h1>

    <div class="menu-section">
        <h2>Orders Menu</h2>
        <ul class="menu-list">
            <li><a href="createorder.php">Create an Order</a></li>
            <li><a href="vieworders.php">View All Orders</a></li>
            <li><a href="editorder.php">Edit Orders</a></li>
            <li><a href="totalsales.php">View Total Sales</a></li>
            <li><a href="refundedorders.php">View Refunded Orders</a></li>
        </ul>
    </div>

    <div class="menu-section">
        <h2>Products Menu</h2>
        <ul class="menu-list">
            <li><a href="addproduct.php">Add Products</a></li>
            <li><a href="viewproducts.php">View All Products</a></li>
            <li><a href="updatestock.php">Update Stock</a></li>
        </ul>
    </div>

</body>
</html>
