package models.auth;
import classes.JDBC;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private String size; // Mengubah kategori menjadi ukuran (size)

    public Product(int id, String name, String description, double price, int stock, String size) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.size = size; // Mengubah kategori menjadi ukuran (size)
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSize() { // Mengganti getCategory menjadi getSize
        return size;
    }

    public void setSize(String size) { // Mengganti setCategory menjadi setSize
        this.size = size;
    }

    public static List<Product> getHotItems() {
        List<Product> hotItems = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT * FROM produk WHERE jumlah > 0 ORDER BY id DESC LIMIT 4"; // Ambil 4 produk terbaru yang tersedia
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel, isi string kosong
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran") // Mengubah kolom kategori menjadi ukuran
                );
                hotItems.add(p);
            }
            System.out.println("Jumlah hot item: " + hotItems.size());
            for (Product p : hotItems) {
                System.out.println(p.getName() + " - " + p.getPrice() + " - Size: " + p.getSize());
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hotItems;
    }

    public static List<Product> getProducts() {
        List<Product> products = new ArrayList<>();
        try {
            System.out.println("Mencoba mengambil data produk dari database...");
            Connection conn = JDBC.getConnection();
            System.out.println("Koneksi database berhasil");

            // Menambahkan LIMIT 4 untuk hanya mengambil 4 produk
            String sql = "SELECT * FROM produk ORDER BY id ASC LIMIT 4";
            System.out.println("Menjalankan query: " + sql);

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Query berhasil dieksekusi");
            int count = 0;

            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                double harga = rs.getDouble("harga");
                int jumlah = rs.getInt("jumlah");
                String ukuran = rs.getString("ukuran"); // Mengubah kolom kategori menjadi ukuran

                System.out.println("Data #" + count + ": id=" + id + ", nama=" + nama + ", harga=" + harga + ", ukuran=" + ukuran);

                Product p = new Product(
                    id,
                    nama,
                    "", // description tidak ada di tabel
                    harga,
                    jumlah,
                    ukuran // Mengubah kategori menjadi ukuran
                );
                products.add(p);
            }

            System.out.println("Jumlah produk ditemukan: " + products.size());
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }
}
