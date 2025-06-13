/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;


import classes.JDBC;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.user.Product;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "ProductControllers", urlPatterns = {"/ProductControllers", "/categories"})
public class ProductControllers extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("category".equals(action)) {
            handleCategoryPage(request, response);
        } else if ("detail".equals(action)) {
            handleProductDetail(request, response);
        } else {
            // Default action - show products on index page
            List<Product> products = Product.getProducts();

            // Create a map of product IDs to image paths for index page
            Map<Integer, String> productImages = new HashMap<>();
            for (Product product : products) {
                productImages.put(product.getId(), getProductImage(product));
            }

            // Get hot items (new arrivals)
            List<Product> hotItems = Product.getHotItems();

            // Create a map for hot items images
            Map<Integer, String> hotItemsImages = new HashMap<>();
            for (Product product : hotItems) {
                hotItemsImages.put(product.getId(), getProductImage(product));
            }

            // Set attributes for the index page
            request.setAttribute("products", products);
            request.setAttribute("productImages", productImages);
            request.setAttribute("hotItems", hotItems);
            request.setAttribute("hotItemsImages", hotItemsImages);

            // Forward ke index.jsp
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }

    /**
     * Selects the appropriate image for a product based on its name
     * @param product The product to find an image for
     * @return The image filename to display with appropriate path
     */
    private String getProductImage(Product product) {
        // If product has a photo path from database, use it
        if (product.getPhoto() != null && !product.getPhoto().isEmpty()) {
            System.out.println("Using photo from database: " + product.getPhoto());
            String photoPath = product.getPhoto();

            // If the path already contains a directory structure, return it as is
            if (photoPath.contains("/") || photoPath.contains("\\")) {
                // Ensure the path starts with the correct folder
                if (photoPath.startsWith("images/")) {
                    return photoPath; // Return the complete path including "images/"
                } else {
                    return "images/" + photoPath; // Add images/ prefix if missing
                }
            } else {
                // If it's just a filename with no path, assume it's in images folder
                return "images/" + photoPath;
            }
        }
        return "images/clothes.png"; // Default image if no photo is available
    }

    /**
     * Handle category page filtering and sorting
     */
    private void handleCategoryPage(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get category, sort, and page parameters
        String selectedCategory = request.getParameter("category");
        String sortBy = request.getParameter("sort");
        String pageParam = request.getParameter("page");

        if (sortBy == null) {
            sortBy = "newest"; // Default sort option
        }

        // Default to page 1 if not specified
        int currentPage = 1;
        try {
            if (pageParam != null && !pageParam.isEmpty()) {
                currentPage = Integer.parseInt(pageParam);
                if (currentPage < 1) currentPage = 1;
            }
        } catch (NumberFormatException e) {
            // If page parameter is not a valid number, default to page 1
            currentPage = 1;
        }

        // Set products per page
        final int PRODUCTS_PER_PAGE = 9;

        // Get ALL products sorted from database first
        List<Product> allSortedProducts;
        int totalProducts;

        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            // Get all products with category filter, sorted directly from database
            allSortedProducts = Product.getAllProductsByCategorySorted(selectedCategory, sortBy);
            totalProducts = allSortedProducts.size();
        } else {
            // Get all products sorted directly from database
            allSortedProducts = Product.getAllProductsSorted(sortBy);
            totalProducts = allSortedProducts.size();
        }

        // Calculate total pages
        int totalPages = (int) Math.ceil((double) totalProducts / PRODUCTS_PER_PAGE);
        if (totalPages < 1) totalPages = 1;

        // Manual pagination: Extract only the products for the current page
        List<Product> productsForCurrentPage = new ArrayList<>();

        int startIndex = (currentPage - 1) * PRODUCTS_PER_PAGE;
        int endIndex = Math.min(startIndex + PRODUCTS_PER_PAGE, totalProducts);

        if (startIndex < totalProducts) {
            productsForCurrentPage = allSortedProducts.subList(startIndex, endIndex);
        }

        // Create a map of product IDs to image paths
        Map<Integer, String> productImages = new HashMap<>();
        for (Product product : productsForCurrentPage) {
            productImages.put(product.getId(), getProductImage(product));
        }

        // Set attributes for the JSP
        request.setAttribute("products", productsForCurrentPage);
        request.setAttribute("productImages", productImages);
        request.setAttribute("selectedCategory", selectedCategory);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalProducts", totalProducts);

        // Set available categories list
        List<String> availableCategories = getAvailableCategories();
        request.setAttribute("availableCategories", availableCategories);

        // Forward to category.jsp
        request.getRequestDispatcher("/pages/user/category.jsp").forward(request, response);
    }

    /**
     * Handle product detail page
     */
    private void handleProductDetail(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Get product ID from request
        String productIdParam = request.getParameter("id");

        if (productIdParam == null || productIdParam.isEmpty()) {
            // Redirect to home if no ID provided
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        try {
            int productId = Integer.parseInt(productIdParam);

            // Get product from database
            Product product = getProductById(productId);

            if (product == null) {
                // Product not found, redirect to home
                response.sendRedirect(request.getContextPath() + "/");
                return;
            }

            // Get product image
            String productImage = getProductImage(product);

            // Set attributes for the JSP
            request.setAttribute("product", product);
            request.setAttribute("productImage", productImage);

            // Forward to product detail JSP
            request.getRequestDispatcher("/pages/user/description.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Invalid ID format, redirect to home
            response.sendRedirect(request.getContextPath() + "/");
        }
    }

    /**
     * Get a product by ID from the database
     */
    private Product getProductById(int productId) {
        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT * FROM products WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            Product product = null;
            if (rs.next()) {
                product = new Product(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("description") != null ? rs.getString("description") : "",
                    rs.getDouble("price"),
                    0, // stock might not be in database
                    "", // size might not be in database
                    rs.getString("category"),
                    rs.getString("photo")
                );
            }

            rs.close();
            stmt.close();
            conn.close();
            return product;

        } catch (Exception e) {
            System.err.println("ERROR retrieving product with ID " + productId + ": " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Get products filtered by category
     */
    private List<Product> getProductsByCategory(String category) {
        return Product.getProductsByCategory(category);
    }

    /**
     * Sort products based on selected sort option
     */
    private void sortProducts(List<Product> products, String sortBy) {
        if ("newest".equals(sortBy)) {
            // Sort by ID in descending order (newest first)
            Collections.sort(products, Comparator.comparingInt(Product::getId).reversed());
        } else if ("price-low-to-high".equals(sortBy)) {
            // Sort by price from lowest to highest
            Collections.sort(products, Comparator.comparingDouble(Product::getPrice));
        } else if ("price-high-to-low".equals(sortBy)) {
            // Sort by price from highest to lowest
            Collections.sort(products, Comparator.comparingDouble(Product::getPrice).reversed());
        }
    }

    /**
     * Get list of available categories
     */
    private List<String> getAvailableCategories() {
        List<String> availableCategories = new java.util.ArrayList<>();
        availableCategories.add("T-Shirt");
        availableCategories.add("Shorts");
        availableCategories.add("Jeans");
        availableCategories.add("Shirt");
        availableCategories.add("Hoodie");
        availableCategories.add("Other");
        return availableCategories;
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
