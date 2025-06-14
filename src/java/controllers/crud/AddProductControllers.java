package controllers.crud;

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

        try {
            String name = request.getParameter("product-name");
            String priceStr = request.getParameter("product-price");
            String category = request.getParameter("category");
            String description = request.getParameter("description");

            // Validasi input
            if (name == null || name.trim().isEmpty() || 
                priceStr == null || priceStr.trim().isEmpty() ||
                category == null || description == null) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/add.jsp?error=missing_fields");
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/add.jsp?error=invalid_price");
                return;
            }

            Part filePart = request.getPart("photo");
            String fileName = getFileName(filePart);

            if (fileName == null || fileName.trim().isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/pages/admin/add.jsp?error=no_image");
                return;
            }

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
            boolean success = dao.insertProduct(product);

            if (success) {
                // Redirect to dashboard with success notification
                response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp?success=product_added");
            } else {
                response.sendRedirect(request.getContextPath() + "/pages/admin/add.jsp?error=database_error");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect(request.getContextPath() + "/pages/admin/add.jsp?error=system_error");
        }
    }

    private String getFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        if (contentDisp != null) {
            for (String token : contentDisp.split(";")) {
                if (token.trim().startsWith("filename")) {
                    String fileName = token.substring(token.indexOf("=") + 2, token.length()-1);
                    return new File(fileName).getName();
                }
            }
        }
        return "";
    }
}