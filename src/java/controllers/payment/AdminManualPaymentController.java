package controllers.payment;

import dao.ManualPaymentDAO;
import models.ManualPayment;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manual-payments")
public class AdminManualPaymentController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ManualPaymentDAO dao = new ManualPaymentDAO();
        List<ManualPayment> payments = dao.getAllPayments();

        request.setAttribute("payments", payments);
        request.getRequestDispatcher("pages/admin/manual-payments.jsp").forward(request, response);
    }
}
