package models.user;
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
    private String category;
    private String photo; // Tambahkan atribut photo

    public Product(int id, String name, String description, double price, int stock, String size, String category) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.category = category;
        this.photo = null;
    }

    public Product(int id, String name, String description, double price, int stock, String size, String category, String photo) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.size = size;
        this.category = category;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public static List<Product> getHotItems() {
        List<Product> hotItems = new ArrayList<>();
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT * FROM products WHERE price > 0 ORDER BY id DESC LIMIT 4"; // Ambil 4 produk terbaru yang tersedia
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Tambahkan photo
                );
                hotItems.add(p);
                System.out.println("Hot item loaded: " + p.getName() + ", photo: " + p.getPhoto());
            }
            System.out.println("Jumlah hot item: " + hotItems.size());
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Error getting hot items: " + e.getMessage());
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

            String sql = "SELECT * FROM products ORDER BY id ASC LIMIT 4";
            System.out.println("Menjalankan query: " + sql);

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            System.out.println("Query berhasil dieksekusi");
            int count = 0;

            while (rs.next()) {
                count++;
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String description = rs.getString("description");
                String category = rs.getString("category");
                String photo = rs.getString("photo");

                System.out.println("Data #" + count + ": id=" + id + ", name=" + name + ", price=" + price + ", category=" + category + ", photo=" + photo);

                Product p = new Product(
                    id,
                    name,
                    description != null ? description : "",
                    price,
                    0, // stock tidak ada di tabel
                    "", // size tidak ada di tabel
                    category,
                    photo
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
            String sql = "SELECT * FROM products ORDER BY id ASC LIMIT 9";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Add photo field
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
            String sql = "SELECT * FROM products WHERE LOWER(category) LIKE ? ORDER BY id ASC LIMIT 10";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Add photo field
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
            String sql = "SELECT * FROM products ORDER BY id ASC LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productsPerPage);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Add photo field
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
                orderByClause = "ORDER BY price ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY price DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM products " + orderByClause + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productsPerPage);
            stmt.setInt(2, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Add photo field
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
                orderByClause = "ORDER BY price ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY price DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM products WHERE LOWER(category) LIKE ? " + orderByClause + " LIMIT ? OFFSET ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");
            stmt.setInt(2, productsPerPage);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Add photo field
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
            String sql = "SELECT COUNT(*) AS total FROM products";
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
            String sql = "SELECT COUNT(*) AS total FROM products WHERE LOWER(category) LIKE ?";
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
            System.out.println("Mengambil semua produk dengan pengurutan: " + sortBy);
            Connection conn = JDBC.getConnection();
            System.out.println("Koneksi database berhasil");

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY price ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY price DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM products " + orderByClause;
            System.out.println("Query: " + sql);

            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            int count = 0;

            while (rs.next()) {
                count++;
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Tambahkan photo
                );
                products.add(p);
            }

            System.out.println("Total produk yang ditemukan: " + count);

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
            System.out.println("Mengambil produk dengan kategori: " + category + " dan pengurutan: " + sortBy);
            Connection conn = JDBC.getConnection();
            System.out.println("Koneksi database berhasil");

            // Determine the ORDER BY clause based on sortBy parameter
            String orderByClause;
            if ("price-low-to-high".equals(sortBy)) {
                orderByClause = "ORDER BY price ASC";
            } else if ("price-high-to-low".equals(sortBy)) {
                orderByClause = "ORDER BY price DESC";
            } else {
                // Default to newest (highest ID first)
                orderByClause = "ORDER BY id DESC";
            }

            String sql = "SELECT * FROM products WHERE LOWER(category) LIKE ? " + orderByClause;
            System.out.println("Query: " + sql + " with parameter: '%" + category.toLowerCase() + "%'");

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, "%" + category.toLowerCase() + "%");
            ResultSet rs = stmt.executeQuery();
            int count = 0;

            while (rs.next()) {
                count++;
                Product p = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock tidak ada, gunakan nilai default
                    "", // size tidak ada, gunakan string kosong
                    rs.getString("category"),
                    rs.getString("photo") // Tambahkan photo
                );
                products.add(p);
            }

            System.out.println("Total produk kategori " + category + " yang ditemukan: " + count);

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("ERROR saat mengambil produk berdasarkan kategori terurut: " + e.getMessage());
            e.printStackTrace();
        }
        return products;
    }
}
