package classes;

import java.sql.*;

public class JDBC {
    private Connection con;
    private String message;

    private final String dbname = "db_barang";
    private final String username = "root";
    private final String password = "";

    private void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); // pakai 'cj' bukan yang lama
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + dbname + "?useSSL=false", username, password);
    }

    private void disconnect() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
            }
        } catch (Exception e) {
            message = e.getMessage();
        }
    }

    // Untuk INSERT/UPDATE/DELETE pakai prepared statement
    public boolean runQuery(String query, Object... params) {
        boolean success = false;
        try {
            connect();
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            int result = ps.executeUpdate();
            message = "info: " + result + " rows affected";
            success = result > 0;
            ps.close();
        } catch (Exception e) {
            message = "error: " + e.getMessage();
            e.printStackTrace(); // biar kelihatan errornya
        } finally {
            disconnect();
        }
        return success;
    }

    // Untuk SELECT
    public ResultSet getData(String query, Object... params) {
        ResultSet rs = null;
        try {
            connect();
            PreparedStatement ps = con.prepareStatement(query);

            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }

            rs = ps.executeQuery();
        } catch (Exception e) {
            message = "error: " + e.getMessage();
            e.printStackTrace();
        }
        return rs;
    }

    public String getMessage() {
        return message;
    }
}
