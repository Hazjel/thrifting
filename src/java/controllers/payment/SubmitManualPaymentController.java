package controllers.payment;

import dao.ManualPaymentDAO;
import models.ManualPayment;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@WebServlet("/submit-payment")
@MultipartConfig
public class SubmitManualPaymentController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));
        int productId = Integer.parseInt(request.getParameter("product_id"));
        String address = request.getParameter("address");

        Part filePart = request.getPart("proof_image");
        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
        String uploadPath = getServletContext().getRealPath("/") + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = "uploads/" + System.currentTimeMillis() + "_" + fileName;
        filePart.write(uploadPath + "/" + System.currentTimeMillis() + "_" + fileName);

        ManualPaymentDAO dao = new ManualPaymentDAO();
        int productPrice = dao.getProductPrice(productId); // ambil harga dari DB

        ManualPayment payment = new ManualPayment();
        payment.setUserId(userId);
        payment.setProductId(productId);
        payment.setPrice(productPrice); // otomatis ngisi harga
        payment.setAddress(address);
        payment.setProofImage(filePath);
        payment.setStatus("pending");

        dao.insertPayment(payment);

        response.sendRedirect("payment-success.jsp");
    }
}