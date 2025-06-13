package dao;

import classes.JDBC;
import models.ManualPayment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManualPaymentDAO {
    private JDBC db;

    public ManualPaymentDAO() {
        db = new JDBC();
    }

    // GET ALL PAYMENTS
    public List<ManualPayment> getAllPayments() {
        List<ManualPayment> list = new ArrayList<>();
        String query = "SELECT * FROM manual_payments ORDER BY created_at DESC";

        try (ResultSet rs = db.getData(query)) {
            while (rs != null && rs.next()) {
                ManualPayment p = new ManualPayment();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setProductId(rs.getInt("product_id"));
                p.setPrice(rs.getInt("price"));
                p.setAddress(rs.getString("address"));
                p.setProofImage(rs.getString("proof_image"));
                p.setStatus(rs.getString("status"));
                p.setCreatedAt(rs.getString("created_at"));

                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // GET PAYMENT BY ID
    public ManualPayment getPaymentById(int id) {
        String query = "SELECT * FROM manual_payments WHERE id = " + id;
        try (ResultSet rs = db.getData(query)) {
            if (rs != null && rs.next()) {
                ManualPayment p = new ManualPayment();
                p.setId(rs.getInt("id"));
                p.setUserId(rs.getInt("user_id"));
                p.setProductId(rs.getInt("product_id"));
                p.setPrice(rs.getInt("price"));
                p.setAddress(rs.getString("address"));
                p.setProofImage(rs.getString("proof_image"));
                p.setStatus(rs.getString("status"));
                p.setCreatedAt(rs.getString("created_at"));

                return p;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // INSERT PAYMENT
    public boolean insertPayment(ManualPayment p) {
        String query = "INSERT INTO manual_payments (user_id, product_id, price, address, proof_image, status) VALUES (" +
                p.getUserId() + ", " +
                p.getProductId() + ", " +
                p.getPrice() + ", " +
                "'" + p.getAddress() + "', " +
                "'" + p.getProofImage() + "', " +
                "'" + p.getStatus() + "'" +
                ")";
        try {
            db.runQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Insert Error: " + e.getMessage());
            return false;
        }
    }

    // UPDATE STATUS (approved / rejected)
    public boolean updateStatus(int id, String status) {
        String query = "UPDATE manual_payments SET status = '" + status + "' WHERE id = " + id;
        try {
            db.runQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    // GET PRODUCT PRICE BY ID (buat bantu controller ngisi price otomatis)
    public int getProductPrice(int productId) {
        String query = "SELECT price FROM products WHERE id = " + productId;
        try (ResultSet rs = db.getData(query)) {
            if (rs != null && rs.next()) {
                return rs.getInt("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
