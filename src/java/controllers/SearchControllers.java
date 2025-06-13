package controllers;

import classes.JDBC;
import models.user.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search")
public class SearchControllers extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String query = request.getParameter("query");

        if (query == null || query.trim().isEmpty()) {
            // If no search query, redirect to home page
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Search for products containing the query in their name
        List<Product> searchResults = searchProducts(query);

        // Create a map of product IDs to image paths
        Map<Integer, String> productImages = new HashMap<>();
        for (Product product : searchResults) {
            productImages.put(product.getId(), getProductImage(product));
        }

        // Set attributes for the JSP
        request.setAttribute("products", searchResults);
        request.setAttribute("productImages", productImages);
        request.setAttribute("searchQuery", query);

        // Forward to search results page
        request.getRequestDispatcher("/pages/user/search-results.jsp").forward(request, response);
    }

    private List<Product> searchProducts(String query) {
        List<Product> results = new ArrayList<>();
        String searchQuery = "%" + query.toLowerCase() + "%";

        try {
            Connection conn = JDBC.getConnection();
            String sql = "SELECT * FROM products WHERE LOWER(name) LIKE ? ORDER BY id DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, searchQuery);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description") != null ? rs.getString("description") : "",
                        rs.getDouble("price"),
                        0, // stock might not be in database
                        "", // size might not be in database
                        rs.getString("category"),
                        rs.getString("photo")
                );
                results.add(product);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            System.err.println("Error searching for products: " + e.getMessage());
            e.printStackTrace();
        }

        return results;
    }

    /**
     * Selects the appropriate image for a product based on its name
     * @param product The product to find an image for
     * @return The image filename to display with appropriate path
     */
    private String getProductImage(Product product) {
        // If product has a photo path from database, use it
        if (product.getPhoto() != null && !product.getPhoto().isEmpty()) {
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
}
