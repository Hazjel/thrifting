package controllers;

import dao.ProductDAO;
import models.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/add-product")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 2,  // 2MB
        maxFileSize = 1024 * 1024 * 10,       // 10MB
        maxRequestSize = 1024 * 1024 * 50     // 50MB
)
public class AddProductControllers extends HttpServlet {
    private final String uploadDir = "webapp/uploads";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("product-name");
        double price = Double.parseDouble(request.getParameter("product-price"));
        String category = request.getParameter("category");
        String description = request.getParameter("description");

        Part filePart = request.getPart("photo");
        String fileName = getFileName(filePart);

        // simpan file ke folder /images
        String uploadPath = getServletContext().getRealPath("") + File.separator + "images";
        File uploadDirFile = new File(uploadPath);
        if (!uploadDirFile.exists()) uploadDirFile.mkdirs();

        String filePath = uploadPath + File.separator + fileName;
        filePart.write(filePath);

        // simpan path relatif (buat ditampilin di view nantinya)
        String relativePath = "images/" + fileName;

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setCategory(category);
        product.setDescription(description);
        product.setPhoto(relativePath);

        ProductDAO dao = new ProductDAO();
        dao.insertProduct(product);

        response.sendRedirect("pages/success.jsp"); // redirect ke halaman sukses (bisa lu custom nanti)
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        for (String token : contentDisp.split(";")) {
            if (token.trim().startsWith("filename")) {
                return new File(token.substring(token.indexOf("=") + 2, token.length()-1)).getName();
            }
        }
        return "";
    }
}
