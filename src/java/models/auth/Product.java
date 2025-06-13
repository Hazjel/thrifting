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
    private String size;
    private String category; // Adding proper category attribute

    public Product(int id, String name, String description, double price, int stock, String size, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.category = category;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
                    rs.getString("ukuran"),
                    rs.getString("kategori") // Add the category field
                );
                hotItems.add(p);
            }
            System.out.println("Jumlah hot item: " + hotItems.size());
            for (Product p : hotItems) {
                System.out.println(p.getName() + " - " + p.getPrice() + " - Size: " + p.getSize() + " - Category: " + p.getCategory());
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
                String ukuran = rs.getString("ukuran");
                String kategori = rs.getString("kategori");

                System.out.println("Data #" + count + ": id=" + id + ", nama=" + nama + ", harga=" + harga + ", ukuran=" + ukuran + ", kategori=" + kategori);

                Product p = new Product(
                    id,
                    nama,
                    "", // description tidak ada di tabel
                    harga,
                    jumlah,
                    ukuran,
                    kategori
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

    public static List<Product> getTopTenProducts() {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            // Changed from LIMIT 10 to LIMIT 9
            String sql = "SELECT * FROM produk ORDER BY id ASC LIMIT 9";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    public static List<Product> getProductsByCategory(String category) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT * FROM produk WHERE LOWER(kategori) LIKE ? ORDER BY id ASC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk berdasarkan kategori: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Get a specific page of products
     * @param pageNumber The page number (1-based)
     * @param productsPerPage Number of products per page
     * @return List of products for the specified page
     */
    public static List<Product> getProductsByPage(int pageNumber, int productsPerPage) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            // Calculate the OFFSET based on page number (pages are 1-based, but OFFSET is 0-based)
            int offset = (pageNumber - 1) * productsPerPage;
            String sql = "SELECT * FROM produk ORDER BY id ASC LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productsPerPage);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk untuk halaman " + pageNumber + ": " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Get a specific page of products with sorting
     * @param pageNumber The page number (1-based)
     * @param productsPerPage Number of products per page
     * @param sortBy Sorting option (newest, price-low-to-high, price-high-to-low)
     * @return List of products for the specified page, sorted as requested
     */
    public static List<Product> getProductsByPage(int pageNumber, int productsPerPage, String sortBy) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            // Calculate the OFFSET based on page number (pages are 1-based, but OFFSET is 0-based)
            int offset = (pageNumber - 1) * productsPerPage;

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY harga ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY harga DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM produk " + orderByClause + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productsPerPage);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk untuk halaman " + pageNumber + ": " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Get a specific page of products filtered by category with sorting
     * @param category The category to filter by
     * @param pageNumber The page number (1-based)
     * @param productsPerPage Number of products per page
     * @param sortBy Sorting option (newest, price-low-to-high, price-high-to-low)
     * @return List of filtered products for the specified page, sorted as requested
     */
    public static List<Product> getProductsByCategoryAndPage(String category, int pageNumber, int productsPerPage, String sortBy) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            // Calculate the OFFSET based on page number
            int offset = (pageNumber - 1) * productsPerPage;

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY harga ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY harga DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM produk WHERE LOWER(kategori) LIKE ? " + orderByClause + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");
            stmt.setInt(2, productsPerPage);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil data produk berdasarkan kategori untuk halaman " + pageNumber + ": " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Count total number of products
     * @return Total number of products in the database
     */
    public static int countTotalProducts() {
        int count = 0;
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM produk";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat menghitung total produk: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Count total number of products in a specific category
     * @param category The category to count products for
     * @return Total number of products in the specified category
     */
    public static int countProductsByCategory(String category) {
        int count = 0;
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT COUNT(*) AS total FROM produk WHERE LOWER(kategori) LIKE ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat menghitung produk berdasarkan kategori: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Get all products with sorting directly from the database
     * @param sortBy Sorting option (newest, price-low-to-high, price-high-to-low)
     * @return Complete list of products sorted as requested
     */
    public static List<Product> getAllProductsSorted(String sortBy) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY harga ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY harga DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM produk " + orderByClause;
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil semua produk terurut: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }

    /**
     * Get all products filtered by category with sorting from database
     * @param category The category to filter by
     * @param sortBy Sorting option (newest, price-low-to-high, price-high-to-low)
     * @return Complete list of filtered products, sorted as requested
     */
    public static List<Product> getAllProductsByCategorySorted(String category, String sortBy) {
        List<Product> products = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY harga ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY harga DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM produk WHERE LOWER(kategori) LIKE ? " + orderByClause;
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("nama"),
                    "", // description tidak ada di tabel
                    rs.getDouble("harga"),
                    rs.getInt("jumlah"),
                    rs.getString("ukuran"),
                    rs.getString("kategori")
                );
                products.add(p);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil semua produk berdasarkan kategori terurut: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }
}
