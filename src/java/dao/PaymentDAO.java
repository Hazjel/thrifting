package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import models.ManualPayment;
import classes.JDBC;

public class PaymentDAO {
    private Connection connection;
    private JDBC db;

    public PaymentDAO() {
        try {
            // Using the same connection parameters as other DAOs
            // Changed port from 3307 to 3306 (default MySQL port)
            String url = "jdbc:mysql://localhost:3307/thrifting?useSSL=false";
            String username = "root";
            String password = "";

            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error connecting to database: " + e.getMessage());
            e.printStackTrace();

            // Initialize the JDBC helper as fallback
            try {
                this.db = new JDBC();
            } catch (Exception ex) {
                System.out.println("Failed to initialize backup connection: " + ex.getMessage());
            }
        }
    }

    // Get the latest payments, limited by count
    public List<ManualPayment> getLatestPayments(int count) {
        List<ManualPayment> payments = new ArrayList<>();

        try {
            if (connection == null || connection.isClosed()) {
                // Use the Orders table as a fallback if payments table doesn't exist
                return getLatestPaymentsFromOrders(count);
            }

            String query = "SELECT * FROM payments ORDER BY id DESC LIMIT ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, count);

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                ManualPayment payment = new ManualPayment();
                payment.setId(rs.getInt("id"));
                payment.setBuyerName(rs.getString("buyer_name"));
                payment.setProductName(rs.getString("product_name"));
                payment.setPrice(rs.getDouble("price"));
                payment.setAddress(rs.getString("address"));
                payment.setProofImage(rs.getString("proof_image"));

                payments.add(payment);
            }
        } catch (SQLException e) {
            System.out.println("Error getting payments, trying orders table instead: " + e.getMessage());
            // If payments table doesn't exist, try to get data from orders table
            payments = getLatestPaymentsFromOrders(count);
        }

        return payments;
    }

    // Alternative method to get payment info from orders table
    private List<ManualPayment> getLatestPaymentsFromOrders(int count) {
        List<ManualPayment> payments = new ArrayList<>();

        try {
            // Use the Order table structure that we know exists
            String query = "SELECT id, customer_name, product_name, product_price, shipping_address, created_at FROM orders ORDER BY created_at DESC LIMIT " + count;

            ResultSet rs;
            if (db != null) {
                rs = db.getData(query);
            } else if (connection != null && !connection.isClosed()) {
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
            } else {
                return payments; // Empty list if no connection available
            }

            while (rs != null && rs.next()) {
                ManualPayment payment = new ManualPayment();
                payment.setId(rs.getInt("id"));
                payment.setBuyerName(rs.getString("customer_name"));
                payment.setProductName(rs.getString("product_name"));
                payment.setPrice(rs.getDouble("product_price"));
                payment.setAddress(rs.getString("shipping_address"));
                payment.setProofImage("images/proof-placeholder.png"); // Default placeholder

                payments.add(payment);
            }
        } catch (Exception e) {
            System.out.println("Error getting orders: " + e.getMessage());
            e.printStackTrace();
        }

        return payments;
    }

    // Get total number of payments
    public int getTotalPayments() {
        int total = 0;

        try {
            if (connection == null || connection.isClosed()) {
                return getTotalOrders();
            }

            String query = "SELECT COUNT(*) AS total FROM payments";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            System.out.println("Error getting payment count, trying orders count: " + e.getMessage());
            total = getTotalOrders();
        }

        return total;
    }

    // Get total orders as fallback
    private int getTotalOrders() {
        int total = 0;

        try {
            String query = "SELECT COUNT(*) AS total FROM orders";

            ResultSet rs;
            if (db != null) {
                rs = db.getData(query);
            } else if (connection != null && !connection.isClosed()) {
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
            } else {
                return 0;
            }

            if (rs != null && rs.next()) {
                total = rs.getInt("total");
            }
        } catch (Exception e) {
            System.out.println("Error getting order count: " + e.getMessage());
        }

        return total;
    }

    // Get total revenue from all payments
    public double getTotalRevenue() {
        double total = 0;

        try {
            if (connection == null || connection.isClosed()) {
                return getTotalRevenueFromOrders();
            }

            String query = "SELECT SUM(price) AS total_revenue FROM payments";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            if (rs.next()) {
                total = rs.getDouble("total_revenue");
            }
        } catch (SQLException e) {
            System.out.println("Error getting revenue, trying orders: " + e.getMessage());
            total = getTotalRevenueFromOrders();
        }

        return total;
    }

    // Get total revenue from orders as fallback
    private double getTotalRevenueFromOrders() {
        double total = 0;

        try {
            String query = "SELECT SUM(product_price) AS total_revenue FROM orders";

            ResultSet rs;
            if (db != null) {
                rs = db.getData(query);
            } else if (connection != null && !connection.isClosed()) {
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
            } else {
                return 0;
            }

            if (rs != null && rs.next()) {
                total = rs.getDouble("total_revenue");
            }
        } catch (Exception e) {
            System.out.println("Error getting revenue from orders: " + e.getMessage());
        }

        return total;
    }

    // Get all payments without limit
    public List<ManualPayment> getAllPayments() {
        List<ManualPayment> payments = new ArrayList<>();

        try {
            if (connection == null || connection.isClosed()) {
                // Use the Orders table as a fallback if payments table doesn't exist
                return getAllPaymentsFromOrders();
            }

            String query = "SELECT * FROM payments ORDER BY id DESC";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                ManualPayment payment = new ManualPayment();
                payment.setId(rs.getInt("id"));
                payment.setBuyerName(rs.getString("buyer_name"));
                payment.setProductName(rs.getString("product_name"));
                payment.setPrice(rs.getDouble("price"));
                payment.setAddress(rs.getString("address"));
                payment.setProofImage(rs.getString("proof_image"));

                payments.add(payment);
            }
        } catch (SQLException e) {
            System.out.println("Error getting all payments, trying orders table instead: " + e.getMessage());
            // If payments table doesn't exist, try to get data from orders table
            payments = getAllPaymentsFromOrders();
        }

        return payments;
    }

    // Alternative method to get all payment info from orders table
    private List<ManualPayment> getAllPaymentsFromOrders() {
        List<ManualPayment> payments = new ArrayList<>();

        try {
            // Use the Order table structure that we know exists
            String query = "SELECT id, customer_name, product_name, product_price, shipping_address, created_at FROM orders ORDER BY created_at DESC";

            ResultSet rs;
            if (db != null) {
                rs = db.getData(query);
            } else if (connection != null && !connection.isClosed()) {
                Statement stmt = connection.createStatement();
                rs = stmt.executeQuery(query);
            } else {
                return payments; // Empty list if no connection available
            }

            while (rs != null && rs.next()) {
                ManualPayment payment = new ManualPayment();
                payment.setId(rs.getInt("id"));
                payment.setBuyerName(rs.getString("customer_name"));
                payment.setProductName(rs.getString("product_name"));
                payment.setPrice(rs.getDouble("product_price"));
                payment.setAddress(rs.getString("shipping_address"));
                payment.setProofImage("images/proof-placeholder.png"); // Default placeholder

                payments.add(payment);
            }
        } catch (Exception e) {
            System.out.println("Error getting all orders: " + e.getMessage());
            e.printStackTrace();
        }

        return payments;
    }

    // Close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
