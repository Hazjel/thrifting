package dao;

import classes.JDBC;
import models.user.Order;
import models.user.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends BaseDAO {

    /**
     * Create a new order in the database
     *
     * @param productId The ID of the purchased product
     * @param productName The name of the purchased product
     * @param productPrice The price of the purchased product
     * @param customerName The name of the customer
     * @param phoneNumber The customer's phone number
     * @param shippingAddress The shipping address
     * @param notes Additional notes for the order (optional)
     * @param userId The ID of the user placing the order
     * @return boolean indicating success or failure
     */
    public boolean createOrder(int productId, String productName, double productPrice,
                            String customerName, String phoneNumber, String shippingAddress,
                            String notes, int userId) {
        try {
            String query = "INSERT INTO orders (product_id, product_name, product_price, " +
                           "customer_name, phone_number, shipping_address, notes, user_id) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            return db.runQuery(query, productId, productName, productPrice,
                             customerName, phoneNumber, shippingAddress, notes, userId);

        } catch (Exception e) {
            System.out.println("Error creating order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Create a new order in the database with transaction support
     *
     * @param connection Active database connection for transaction
     * @param productId The ID of the purchased product
     * @param productName The name of the purchased product
     * @param productPrice The price of the purchased product
     * @param customerName The name of the customer
     * @param phoneNumber The customer's phone number
     * @param shippingAddress The shipping address
     * @param notes Additional notes for the order (optional)
     * @param userId The ID of the user placing the order
     * @return boolean indicating success or failure
     */
    public boolean createOrderWithTransaction(Connection connection,
                            int productId, String productName, double productPrice,
                            String customerName, String phoneNumber, String shippingAddress,
                            String notes, int userId) {
        try {
            String query = "INSERT INTO orders (product_id, product_name, product_price, " +
                           "customer_name, phone_number, shipping_address, notes, user_id) " +
                           "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, productId);
            ps.setString(2, productName);
            ps.setDouble(3, productPrice);
            ps.setString(4, customerName);
            ps.setString(5, phoneNumber);
            ps.setString(6, shippingAddress);
            ps.setString(7, notes);
            ps.setInt(8, userId);

            int rowsAffected = ps.executeUpdate();
            ps.close();

            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println("Error creating order with transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all orders from the database
     *
     * @return List of Order objects
     */
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders ORDER BY created_at DESC";

        try {
            ResultSet rs = db.getData(query);

            while (rs != null && rs.next()) {
                Order order = mapResultSetToOrder(rs);
                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println("Error getting orders: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Get orders for a specific user
     *
     * @param userId The ID of the user
     * @return List of Order objects
     */
    public List<Order> getOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE user_id = ? ORDER BY created_at DESC";

        try {
            ResultSet rs = db.getData(query, userId);

            while (rs != null && rs.next()) {
                Order order = mapResultSetToOrder(rs);
                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println("Error getting user orders: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Get the most recent orders
     *
     * @param limit The maximum number of orders to return
     * @return List of Order objects
     */
    public List<Order> getRecentOrders(int limit) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT id, product_id, product_name, product_price, customer_name, phone_number, shipping_address, notes, created_at, user_id FROM orders ORDER BY created_at DESC LIMIT ?";

        try {
            ResultSet rs = db.getData(query, limit);

            while (rs != null && rs.next()) {
                Order order = new Order();
                order.setId(rs.getInt("id"));
                order.setProductId(rs.getInt("product_id"));
                order.setProductName(rs.getString("product_name"));
                order.setProductPrice(rs.getDouble("product_price"));
                order.setCustomerName(rs.getString("customer_name"));
                order.setPhoneNumber(rs.getString("phone_number"));
                order.setShippingAddress(rs.getString("shipping_address"));
                order.setNotes(rs.getString("notes"));
                order.setCreatedAt(rs.getTimestamp("created_at"));
                order.setUserId(rs.getInt("user_id"));
                orders.add(order);
            }

        } catch (Exception e) {
            System.out.println("Error getting recent orders: " + e.getMessage());
            e.printStackTrace();
        }

        return orders;
    }

    /**
     * Update the status of an order
     *
     * @param orderId The ID of the order
     * @param newStatus The new status
     * @return boolean indicating success or failure
     */
    public boolean updateOrderStatus(int orderId, String newStatus) {
        try {
            String query = "UPDATE orders SET order_status = ? WHERE id = ?";
            return db.runQuery(query, newStatus, orderId);

        } catch (Exception e) {
            System.out.println("Error updating order status: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Map a ResultSet to an Order object
     */
    private Order mapResultSetToOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id"));
        order.setProductId(rs.getInt("product_id"));
        order.setProductName(rs.getString("product_name"));
        order.setProductPrice(rs.getDouble("product_price"));
        order.setCustomerName(rs.getString("customer_name"));
        order.setPhoneNumber(rs.getString("phone_number"));
        order.setShippingAddress(rs.getString("shipping_address"));
        order.setNotes(rs.getString("notes"));
        order.setUserId(rs.getInt("user_id"));
        order.setCreatedAt(rs.getTimestamp("created_at"));
        return order;
    }
}
