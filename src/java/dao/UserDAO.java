package dao;

import models.user.User;
import classes.JDBC;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends BaseDAO {
    public void register(String username, String email, String password) {
        try {
            if (isEmailExists(email)) {
                System.out.println("Registrasi gagal! Email sudah terdaftar!");
                return;
            }

            String query = "INSERT INTO users (name, email, username, passwords, role_type) VALUES (?, ?, ?, ?, 'buyer')";

            boolean success = db.runQuery(query, username, email, username, password);

            if (success) {
                System.out.println("Registrasi berhasil! Role default: buyer");
                System.out.println(db.getMessage());
            } else {
                System.out.println("Registrasi gagal: " + db.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Register error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public User login(String email, String password) {
        String query = "SELECT * FROM users WHERE email = ? AND passwords = ?";

        try {
            ResultSet rs = db.getData(query, email, password);

            if (rs != null && rs.next()) {
                User user = new User();

                user.setId(rs.getInt("id")); // Set the user ID
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPasswords(rs.getString("passwords"));
                user.setRoleType(rs.getString("role_type"));

                // Debug: Print user details for troubleshooting
                System.out.println("Login successful for: " + email);
                System.out.println("Role type from database: " + rs.getString("role_type"));
                System.out.println("User ID: " + rs.getInt("id")); // Debug log for user ID

                return user;
            }
            // Debug: Print when login fails
            System.out.println("Login failed for: " + email);
            System.out.println("No matching record found in database");

        } catch (Exception e) {
            System.out.println("Login error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void createUser(String name, String email, String username, String password, String roleType) {
        try {
            if (isEmailExists(email)) {
                System.out.println("Create gagal! Email sudah terdaftar!");
                return;
            }

            String query = "INSERT INTO users (name, email, username, passwords, role_type) VALUES (?, ?, ?, ?, ?)";
            boolean success = db.runQuery(query, name, email, username, password, roleType);

            if (success) {
                System.out.println("User berhasil dibuat!");
                System.out.println(db.getMessage());
            } else {
                System.out.println("Create user gagal: " + db.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Create user error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> fetchedUser = new ArrayList<>();
        String query = "SELECT * FROM users";

        try {
            ResultSet rs = db.getData(query);

            if (rs != null) {
                User user = new User();

                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPasswords(rs.getString("passwords"));
                user.setRoleType(rs.getString("role_type"));

                fetchedUser.add(user);
            } else {
                System.out.println("Tidak ada user ditemukan!");
            }
        } catch (Exception e) {
            System.out.println("Get all users error: " + e.getMessage());
            e.printStackTrace();
        }

        return fetchedUser;
    }

    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";

        try {
            ResultSet rs = db.getData(query, id);

            if (rs != null && rs.next()) {
                User user = new User();

                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setUsername(rs.getString("username"));
                user.setPasswords(rs.getString("passwords"));
                user.setRoleType(rs.getString("role_type"));

                return user;
            }
        } catch (Exception e) {
            System.out.println("Get user error: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public void updateUser(int id, String name, String email, String username, String password, String roleType) {
        try {
            if (getUserById(id) == null) {
                System.out.println("Update gagal! User dengan ID " + id + " tidak ditemukan!");
                return;
            }

            String query = "UPDATE users SET name = ?, email = ?, username = ?, passwords = ?, role_type = ? WHERE id = ?";
            boolean success = db.runQuery(query, name, email, username, password, roleType, id);

            if (success) {
                System.out.println("User berhasil diupdate!");
                System.out.println(db.getMessage());
            } else {
                System.out.println("Update user gagal: " + db.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Update user error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void deleteUser(int id) {
        try {
            if (getUserById(id) == null) {
                System.out.println("Delete gagal! User dengan ID " + id + " tidak ditemukan!");
                return;
            }

            String query = "DELETE FROM users WHERE id = ?";
            boolean success = db.runQuery(query, id);

            if (success) {
                System.out.println("User berhasil dihapus!");
                System.out.println(db.getMessage());
            } else {
                System.out.println("Delete user gagal: " + db.getMessage());
            }
        } catch (Exception e) {
            System.out.println("Delete user error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) AS total FROM users WHERE email = ?";

        try {
            ResultSet rs = db.getData(query, email);

            if (rs != null && rs.next()) {
                return rs.getInt("total") > 0;
            }

        } catch (Exception e) {
            System.out.println("Check email error: " + e.getMessage());
        }

        return false;
    }
}
