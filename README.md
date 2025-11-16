# CCINFOM-DBApp
CCINFOM-S27-11 DBApp for Term 1, 2025-2026

 _Souvenir Shop Simulation_ |  _CCINFOM - Database App_

# SAGE Souvenir Shop Web App (SouvenirApp)
This project is a PHP and MySQL-based souvenir shop management web application. It allows users to manage products, orders, and customers efficiently.​​

## Prerequisites
Before running SouvenirApp, make sure you have the following installed on your machine:

1. **XAMPP (Recommended)**
  - Download and install [XAMPP](https://www.apachefriends.org/index.html).​
  - Make sure both Apache and MySQL servers are running.

2. **Project Files**
  - Create the folder 'SouvenirApp' in the directory of your XAMPP installation (C:\xampp\htdocs\).
  - Download the [PHP files](https://github.com/gianlwn/CCINFOM-DBApp/tree/main/src/gui) in the 'gui' folder inside the 'src' folder.
  - Place the PHP files in the 'SouvenirApp' folder (C:\xampp\htdocs\SouvenirApp).

3. **MySQL Database**
  - Download the [11-DBCREATION.sql](https://github.com/gianlwn/CCINFOM-DBApp/blob/main/database/11-DBCREATION.sql) in the 'database' folder.
  - Export the SQL file in MySQL Workbench.
  - Run lines 1-3, then lines 5-105, then you should have the database.

4. **Connecting to the MySQL Database**
  - Inside the 'database.php' file, change the parameters of lines 3 and 4 to your following credentials.

## Setup Instructions
1. **Start XAMPP**
  - Launch XAMPP Control Panel.
  - Click Start for both Apache and MySQL.
  - Confirm MySQL is running on port 3307 (update your PHP code accordingly).​

2. **Deploy Application**
  - Navigate to [http://localhost/SouvenirApp](http://localhost/SouvenirApp) in your browser.

## Troubleshooting
  - **"Could not connect!" Error:**
    Ensure MySQL is running and the port in your PHP matches XAMPP (3307).
    Check your database name and credentials.​

  - **Port Conflict:**
    If MySQL fails to start on 3306, it will switch to 3307.
    Open the XAMPP Control Panel, then press the 'config' button in MySQL.
    Press 'my.ini' then change the port number to 3307.

## How to Run
1. Clone or copy project files into htdocs.
2. Start XAMPP, confirm modules are running.
3. Open your browser, then type in: [http://localhost/SouvenirApp](http://localhost/SouvenirApp).​
