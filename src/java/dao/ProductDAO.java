package dao;

import classes.JDBC;
import models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private JDBC db;

    public ProductDAO() {
        db = new JDBC();
    }

    // GET ALL PRODUCTS (tanpa limit)
    public List<Product> getAllProducts() {
        return getProductsPaginated(-1, -1); // -1 artinya ambil semua
    }

    // GET PRODUCTS with PAGINATION
    public List<Product> getProductsPaginated(int offset, int limit) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM products";

        if (offset >= 0 && limit > 0) {
            query += " LIMIT " + offset + ", " + limit;
        }

        try (ResultSet rs = db.getData(query)) {
            while (rs != null && rs.next()) {
                Product p = new Product();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setPrice(rs.getDouble("price"));
                p.setCategory(rs.getString("category"));
                p.setDescription(rs.getString("description"));
                p.setPhoto(rs.getString("photo"));

                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }

    // COUNT TOTAL PRODUCTS
    public int getTotalProductCount() {
        String query = "SELECT COUNT(*) FROM products";
        try (ResultSet rs = db.getData(query)) {
            if (rs != null && rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Product getProductById(int id) {
        String query = "SELECT * FROM products WHERE id = " + id;
        try (ResultSet rs = db.getData(query)) {
            if (rs != null && rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getString("description"),
                        rs.getString("photo")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean insertProduct(Product p) {
        String query = "INSERT INTO products (name, price, category, description, photo) VALUES (" +
                "'" + p.getName() + "', " +
                p.getPrice() + ", " +
                "'" + p.getCategory() + "', " +
                "'" + p.getDescription() + "', " +
                "'" + p.getPhoto() + "'" +
                ")";
        try {
            db.runQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Insert Error: " + e.getMessage());
            return false;
        }
    }

    public boolean updateProduct(Product p) {
        String query = "UPDATE products SET " +
                "name = '" + p.getName() + "', " +
                "price = " + p.getPrice() + ", " +
                "category = '" + p.getCategory() + "', " +
                "description = '" + p.getDescription() + "', " +
                "photo = '" + p.getPhoto() + "' " +
                "WHERE id = " + p.getId();

        try {
            db.runQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Update Error: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteProduct(int id) {
        String query = "DELETE FROM products WHERE id = " + id;
        try {
            db.runQuery(query);
            return true;
        } catch (Exception e) {
            System.out.println("Delete Error: " + e.getMessage());
            return false;
        }
    }


}
