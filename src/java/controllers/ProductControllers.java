/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controllers;

import java.io.IOException;
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
     * @return The image filename to display
     */
    private String getProductImage(Product product) {
        // Jika produk memiliki path gambar dari database, gunakan itu
        if (product.getPhoto() != null && !product.getPhoto().isEmpty()) {
            System.out.println("Menggunakan photo dari database: " + product.getPhoto());
            // Pastikan path relatif, tidak termasuk 'images/'
            String photoPath = product.getPhoto();
            if (photoPath.startsWith("images/")) {
                return photoPath.substring(7); // Hapus 'images/' dari awal path
            }
            return photoPath;
        }
        return "clothes.png"; // Default image if no photo is available
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
