package classes;

import java.sql.*;
import java.io.File;

public class JDBC {
    private Connection con;
    private String message;

    private final String dbname = "db_barang";
    private final String username = "root";
    private final String password = "";

    private void connect() throws Exception {
        Class.forName("com.mysql.cj.jdbc.Driver"); // pakai 'cj' bukan yang lama
        con = DriverManager.getConnection("jdbc:mysql://localhost:3307/" + dbname + "?useSSL=false", username, password);
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
    public static Connection getConnection() throws Exception {
        String dbname = "db_barang";
        String username = "root";
        String password = "";

        try {
            // Cek apakah file JAR MySQL ada di folder lib
            File jarFile = new File("web/WEB-INF/lib/mysql-connector-j-9.3.0.jar");
            if (jarFile.exists()) {
                System.out.println("MySQL Connector JAR ditemukan di: " + jarFile.getAbsolutePath());
            } else {
                System.out.println("WARNING: MySQL Connector JAR tidak ditemukan di web/WEB-INF/lib");
                jarFile = new File("mysql-connector-j-9.3.0.jar");
                if (jarFile.exists()) {
                    System.out.println("MySQL Connector JAR ditemukan di root: " + jarFile.getAbsolutePath());
                } else {
                    System.out.println("WARNING: MySQL Connector JAR tidak ditemukan di root project");
                }
            }

            // Coba driver yang tersedia di Java
            String[] possibleDrivers = {
                    "com.mysql.cj.jdbc.Driver",  // Driver baru (MySQL 8+)
                    "com.mysql.jdbc.Driver",     // Driver lama (MySQL 5.x)
                    "org.mariadb.jdbc.Driver"    // MariaDB Driver (kompatibel dengan MySQL)
            };

            boolean driverFound = false;
            for (String driver : possibleDrivers) {
                try {
                    System.out.println("Mencoba driver: " + driver);
                    Class.forName(driver);
                    System.out.println("Driver " + driver + " berhasil ditemukan");
                    driverFound = true;
                    break;
                } catch (ClassNotFoundException e) {
                    System.out.println("Driver " + driver + " tidak ditemukan");
                }
            }

            if (!driverFound) {
                throw new ClassNotFoundException("Tidak ada driver MySQL/MariaDB yang ditemukan");
            }

            // Coba koneksi dengan opsi yang lebih toleran
            String url = "jdbc:mysql://localhost:3307/" + dbname +
                    "?useSSL=false" +
                    "&allowPublicKeyRetrieval=true" +
                    "&useUnicode=true" +
                    "&serverTimezone=UTC" +
                    "&useOldAliasMetadataBehavior=true";

            System.out.println("Mencoba koneksi ke URL: " + url);
            Connection conn = DriverManager.getConnection(url, username, password);
            System.out.println("Koneksi database berhasil ke: " + dbname);
            return conn;
        } catch (Exception e) {
            System.err.println("ERROR KONEKSI DATABASE: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
