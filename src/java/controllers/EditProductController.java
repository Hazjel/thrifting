package controllers;

import dao.ProductDAO;
import models.Product;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@WebServlet("/edit-product")
public class EditProductController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Product product = new ProductDAO().getProductById(id);
        request.setAttribute("product", product);
        request.getRequestDispatcher("pages/admin/edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(request)) {
            showError(request, response, "Form tidak mendukung file upload.");
            return;
        }

        try {
            Product updatedProduct = processFormData(request);
            new ProductDAO().updateProduct(updatedProduct);
            response.sendRedirect("product-list");
        } catch (Exception e) {
            e.printStackTrace();
            showError(request, response, "Error: " + e.getMessage());
        }
    }

    private Product processFormData(HttpServletRequest request) throws Exception {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);

        Product product = new Product();

        for (FileItem item : items) {
            if (item.isFormField()) {
                mapFormField(product, item);
            } else if (item.getSize() > 0) {
                String photoPath = savePhoto(item, request);
                product.setPhoto(photoPath);
            }
        }

        return product;
    }

    private void mapFormField(Product product, FileItem item) throws UnsupportedEncodingException {
        String name = item.getFieldName();
        String value = item.getString("UTF-8");

        switch (name) {
            case "id":
                product.setId(Integer.parseInt(value));
                break;
            case "product-name":
                product.setName(value);
                break;
            case "product-price":
                product.setPrice(Double.parseDouble(value));
                break;
            case "category":
                product.setCategory(value);
                break;
            case "description":
                product.setDescription(value);
                break;
        }
    }

    private String savePhoto(FileItem fileItem, HttpServletRequest request) throws Exception {
        String fileName = FilenameUtils.getName(fileItem.getName());
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + "uploads";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdir();

        String filePath = uploadPath + File.separator + fileName;
        fileItem.write(new File(filePath));

        return "uploads/" + fileName;
    }

    private void showError(HttpServletRequest request, HttpServletResponse response, String message)
            throws ServletException, IOException {
        request.setAttribute("message", message);
        request.getRequestDispatcher("edit.jsp").forward(request, response);
    }
}
