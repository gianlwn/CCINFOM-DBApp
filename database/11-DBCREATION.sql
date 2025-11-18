-- CREATE DATABASE
CREATE DATABASE dbapp;
USE dbapp;

-- CATEGORIES TABLE
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(50) NOT NULL
) AUTO_INCREMENT = 1001;


-- SUPPLIERS TABLE
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT PRIMARY KEY,
    supplier_name VARCHAR(100) NOT NULL,
    contact_person VARCHAR(100),
    contact_number VARCHAR(30),
    email VARCHAR(100),
    address TEXT,
    last_delivery_date DATE
) AUTO_INCREMENT = 2001;


-- PRODUCTS TABLE
CREATE TABLE products (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    product_name VARCHAR(100) NOT NULL,
    category_id INT,
    price DECIMAL(10,2) NOT NULL,
    quantity_in_stock INT NOT NULL,
    supplier_id INT,
    FOREIGN KEY (category_id) REFERENCES categories(category_id),
    FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
) AUTO_INCREMENT = 3001;


-- CUSTOMERS TABLE
CREATE TABLE customers (
    customer_id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    contact_number VARCHAR(30) NOT NULL,
    email VARCHAR(100) NULL
) AUTO_INCREMENT = 101;


-- ORDERS TABLE
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    customer_id INT NOT NULL,
    product_id INT NOT NULL,
    quantity INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    order_date DATE NOT NULL,
    status ENUM('COMPLETED', 'REFUNDED') NOT NULL DEFAULT 'COMPLETED',
    FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    FOREIGN KEY (product_id) REFERENCES products(product_id)
) AUTO_INCREMENT = 4001;

ALTER TABLE customers
ADD CONSTRAINT unique_customer UNIQUE (first_name, last_name, contact_number);

-- queries for tables with initialized values --
INSERT INTO categories (category_name) VALUES
('Accessories'),
('Apparel'),
('Bags & Pouches'),
('Home decor'),
('Local crafts'),
('Stationery'),
('Figurines'),
('Keychains'),
('Local Foods'),
('Mugs');

INSERT INTO suppliers (supplier_name, contact_person, contact_number, email, address, last_delivery_date) VALUES
('Island Crafts PH', 'Maria Santos', '09171234567', 'info@islandcrafts.ph', 'Cebu City, Philippines', '2025-10-15'),
('Souvenir Hub', 'Josh Declaro', '09281234567', 'contact@souvenir.ph', 'Manila, Philippines', '2025-11-01'),
('Tropa Prints Co.', 'Liza Ramos', '09174561234', 'sales@tropaprints.com', 'Quezon City, Philippines', '2025-10-20'),
('Craft World', 'Nathaniel Cruz', '09381235678', 'craftworld@gmail.com', 'Makati City, Philippines', '2025-09-28'),
('Local Treasures Co.', 'Chandria Padilla', '09186547123', 'hello@localtreasures.com', 'Davao City, Philippines', '2025-10-30'),
('Artisans by Hand', 'Elise Tan', '09273451209', 'sales@artisantsbyhand.com', 'Laguna, Philippines', '2025-11-08'),
('Gourmet Treats PH', 'Chris Patrick', '09178902345', 'orders@gourmettreats.ph', 'Taguig City, Philippines', '2025-11-05'),
('Island Delights', 'Andy Martin', '09176718901', 'info@islanddelights.ph', 'Cebu City, Philippines', '2025-08-16'),
('MugWorks Studio', 'Jia Parado', '09184567892', 'sales@mugworks.ph', 'Baguio City, Philippines', '2025-10-27'),
('Keepsake Creations', 'Donna Lim', '09394561230', 'info@keepsakecreations.ph', 'Iloilo City, Philippines', '2025-11-03');

INSERT INTO products (product_name, category_id, price, quantity_in_stock, supplier_id) VALUES
('Postcard Set', 1001, 250, 150, 2005),
('Wooden Bracelet', 1001, 320, 100, 2005),
('T-shirt', 1002, 550, 80, 2003),
('Cap', 1002, 400, 100, 2003),
('Pouch Set', 1003, 450, 70, 2004),
('Tote Bag', 1003, 600, 50, 2004),
('Fridge Magnet', 1004, 180, 150, 2001),
('Table Lamp', 1004, 700, 30, 2001),
('Mini Statue', 1005, 450, 50, 2010),
('Woven Basket', 1005, 550, 40, 2005),
('Stickers', 1006, 200, 200, 2006),
('Souvenir Pen', 1006, 180, 250, 2006),
('Jeepney Figurine', 1007, 350, 60, 2010),
('Mayon Volcano Figurine', 1007, 400, 50, 2010),
('Leather key Holder', 1008, 300, 100, 2001),
('Jeepney Keychain', 1008, 220, 150, 2001),
('Dried Mango Pack', 1009, 350, 80, 2007),
('Chocolate Box', 1009, 500, 50, 2008),
('Ceramic Teacup Set', 1010, 600, 30, 2009),
('Coffee Mug', 1010, 400, 50, 2009);
