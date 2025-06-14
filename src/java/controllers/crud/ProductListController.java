package controllers.crud;

import dao.ProductDAO;
import dao.OrderDAO;
import models.Product;
import models.user.Order;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/product-list")
public class ProductListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int page = 1;
        int recordsPerPage = 5;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        ProductDAO dao = new ProductDAO();
        int totalRecords = dao.getTotalProductCount();
        int totalPages = (int) Math.ceil((double) totalRecords / recordsPerPage);

        // 🔒 Batasin halaman biar gak bisa lebih dari totalPages
        if (page > totalPages) {
            page = totalPages;
        }
        if (page < 1) {
            page = 1;
        }

        List<Product> products = dao.getProductsPaginated((page - 1) * recordsPerPage, recordsPerPage);

        // Get recent orders
        OrderDAO orderDAO = new OrderDAO();
        List<Order> recentOrders = orderDAO.getRecentOrders(5); // Get 5 most recent orders

        request.setAttribute("products", products);
        request.setAttribute("recentOrders", recentOrders);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("pages/admin/view.jsp").forward(request, response);
    }
}
