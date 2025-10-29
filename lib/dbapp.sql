CREATE DATABASE dbapp;

-- Category Table
CREATE TABLE categories (
  category_id INT PRIMARY KEY,
  category_name VARCHAR(100)
);

-- Supplier Table
CREATE TABLE suppliers (
  supplier_id INT PRIMARY KEY,
  supplier_name VARCHAR(100),
  contact_person VARCHAR(100),
  contact_number VARCHAR(20),
  email VARCHAR(100),
  address TEXT,
  last_delivery_date DATE
);

-- Product Table
CREATE TABLE products (
  product_id INT PRIMARY KEY,
  product_name VARCHAR(100),
  category_id INT,
  description TEXT,
  price DECIMAL(10, 2),
  quantity_in_stock INT,
  supplier_id INT,
  FOREIGN KEY (category_id) REFERENCES categories(category_id),
  FOREIGN KEY (supplier_id) REFERENCES suppliers(supplier_id)
);

-- Customer Table (empty on purpose (no rows))
CREATE TABLE customers (
  customer_id INT PRIMARY KEY,
  first_name VARCHAR(50),
  last_name VARCHAR(50),
  contact_number VARCHAR(20),
  email VARCHAR(100),
  country_id INT,
  FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

-- Order_Details Table (empty on purpose (no rows))
CREATE TABLE order_details (
  order_id INT,
  customer_id INT,
  product_id INT,
  quantity INT,
  unit_price DECIMAL(10, 2),
  order_date DATE,
  PRIMARY KEY(order_id, product_id),
  FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
  FOREIGN KEY (product_id) REFERENCES products(product_id)
);

-- Country Table
CREATE TABLE countries (
  country_id INT PRIMARY KEY,
  country_name VARCHAR(100)
);
