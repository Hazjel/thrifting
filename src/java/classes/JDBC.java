package classes;
import java.sql.*;
import java.io.File;

public class JDBC {
    private Connection con;
    private Statement stmt;
    private boolean isConnected;
    private String message;

    // Menambahkan getter untuk message
    public String getMessage() {
        return message;
    }

    public void connect() {
        try {
            Connection conn = getConnection();
            stmt = conn.createStatement();
            isConnected = true;
            message = "DB connected";
        } catch(Exception e) {
            isConnected = false;
            message = e.getMessage();
            System.err.println("Connect error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void disconnect() {
        try {
            if (stmt != null) stmt.close();
            if (con != null) con.close();
        } catch(Exception e) {
            message = e.getMessage();
        }
    }

    public void runQuery(String query) {
        try {
            connect();
            int result = stmt.executeUpdate(query);
            message = "info: " + result + " rows affected";
        } catch(Exception e) {
            message = e.getMessage();
            System.err.println("runQuery error: " + e.getMessage());
        } finally {
            disconnect();
        }
    }

    public boolean runQuery(String query, Object... params) {
        boolean success = false;
        try {
            connect();
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            int result = pstmt.executeUpdate();
            message = "info: " + result + " rows affected";
            success = true;

            if (pstmt != null) pstmt.close();
        } catch(Exception e) {
            message = e.getMessage();
            System.err.println("runQuery with params error: " + e.getMessage());
        } finally {
            disconnect();
        }
        return success;
    }

    public ResultSet getData(String query) {
        ResultSet rs = null;
        try {
            connect();
            rs = stmt.executeQuery(query);
        } catch(Exception e) {
            message = e.getMessage();
            System.err.println("getData error: " + e.getMessage());
        }
        return rs;
    }

    public ResultSet getData(String query, Object... params) {
        ResultSet rs = null;
        try {
            connect();
            PreparedStatement pstmt = con.prepareStatement(query);

            // Set parameters
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }

            rs = pstmt.executeQuery();
            // Note: We don't close the PreparedStatement here because that would close the ResultSet
        } catch(Exception e) {
            message = e.getMessage();
            System.err.println("getData with params error: " + e.getMessage());
        }
        return rs;
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
            String url = "jdbc:mysql://localhost:3306/" + dbname +
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