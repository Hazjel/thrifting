package controllers;

import models.user.Product;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/purchase/*")
public class PurchaseController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);

        switch (action) {
            case "checkout":
                handleCheckout(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = getAction(request);

        switch (action) {
            case "checkout":
                handleCheckout(request, response);
                break;
            case "submit":
                handleSubmitPurchase(request, response);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    private void handleCheckout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            // User not logged in - redirect to login page with return URL parameter
            String returnUrl = request.getContextPath() + "/ProductControllers?action=detail&id=" + request.getParameter("productId");
            session.setAttribute("redirectAfterLogin", returnUrl);
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Get product ID from request
        String productId = request.getParameter("productId");
        String productName = request.getParameter("productName");
        String productPrice = request.getParameter("productPrice");
        String productImage = request.getParameter("productImage");

        if (productId == null || productId.isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }

        // Store product details in session for purchase process
        session.setAttribute("checkout_productId", productId);
        session.setAttribute("checkout_productName", productName);
        session.setAttribute("checkout_productPrice", productPrice);
        session.setAttribute("checkout_productImage", productImage);

        // Forward to the detail-pembelian page
        request.getRequestDispatcher("/pages/user/detail-pembelian.jsp").forward(request, response);
    }

    private void handleSubmitPurchase(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Check if user is logged in
        HttpSession session = request.getSession();
        if (session.getAttribute("user") == null) {
            // User not logged in - redirect to login page with return URL parameter
            session.setAttribute("redirectAfterLogin", request.getRequestURI());
            response.sendRedirect(request.getContextPath() + "/auth/login");
            return;
        }

        // Get the user ID from the session
        models.user.User currentUser = (models.user.User) session.getAttribute("user");
        int userId = currentUser.getId();

        // Debug output to check user ID
        System.out.println("Current user ID from session: " + userId);

        // Validate user ID
        if (userId <= 0) {
            System.out.println("Invalid user ID: " + userId);
            request.setAttribute("error", "Invalid user account. Please try logging in again.");
            request.getRequestDispatcher("/pages/user/detail-pembelian.jsp").forward(request, response);
            return;
        }

        // Get form data
        String fullName = request.getParameter("fullName");
        String phone = request.getParameter("phone");
        String address = request.getParameter("address");
        String notes = request.getParameter("notes");

        // Get product details from the request
        String productIdStr = request.getParameter("productId");

        try {
            // Parse product ID and price
            int productId = Integer.parseInt(productIdStr);

            // Debug output for product details
            System.out.println("Product ID: " + productId);

            // Get other product details from the database or form
            String productName = request.getParameter("productName");
            String priceStr = request.getParameter("productPrice");

            // Clean price string - remove "Rp " and commas
            priceStr = priceStr.replace("Rp ", "").replace(",", "");
            System.out.println("Price string after cleaning: " + priceStr);

            double productPrice = Double.parseDouble(priceStr);
            System.out.println("Parsed product price: " + productPrice);

            // Debug all parameters being sent to createOrder
            System.out.println("Creating order with parameters:");
            System.out.println("Product ID: " + productId);
            System.out.println("Product Name: " + productName);
            System.out.println("Product Price: " + productPrice);
            System.out.println("Customer Name: " + fullName);
            System.out.println("Phone: " + phone);
            System.out.println("Address: " + address);
            System.out.println("Notes: " + notes);
            System.out.println("User ID: " + userId);

            // Save order to database
            dao.OrderDAO orderDao = new dao.OrderDAO();
            boolean success = orderDao.createOrder(
                productId, productName, productPrice,
                fullName, phone, address, notes, userId
            );

            if (success) {
                // Order created successfully
                request.setAttribute("orderSuccess", true);
                request.getRequestDispatcher("/pages/user/purchase-confirmation.jsp").forward(request, response);
            } else {
                // Order creation failed
                System.out.println("Failed to create order in database");
                request.setAttribute("error", "Failed to process your order. Please try again.");
                request.getRequestDispatcher("/pages/user/detail-pembelian.jsp").forward(request, response);
            }

        } catch (Exception e) {
            // Handle errors
            System.out.println("Exception in handleSubmitPurchase: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
            request.getRequestDispatcher("/pages/user/detail-pembelian.jsp").forward(request, response);
        }
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return "checkout";
        }
        return pathInfo.substring(1);
    }
}
