package controllers.payment;

import dao.ManualPaymentDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/manual-payment/update-status")
public class UpdateManualPaymentStatusController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int paymentId = Integer.parseInt(request.getParameter("id"));
        String status = request.getParameter("status"); // 'approved' atau 'rejected'

        ManualPaymentDAO dao = new ManualPaymentDAO();
        dao.updateStatus(paymentId, status);

        response.sendRedirect("/admin/manual-payments");
    }
}
