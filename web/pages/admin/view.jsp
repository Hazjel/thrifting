<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="../../styles/list.css">
</head>
<body>
<h1>Product List</h1>
<a href="./pages/admin/add.jsp">+ Add New Product</a>
<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>No</th>
        <th>Name</th>
        <th>Price</th>
        <th>Category</th>
        <th>Description</th>
        <th>Photo</th>
        <th>Action</th>
    </tr>
    <%
        List<Product> products = (List<Product>) request.getAttribute("products");
        int no = 1;
        for (Product p : products) {
    %>
    <tr>
        <td><%= no++ %>
        </td>
        <td><%= p.getName() %>
        </td>
        <td>Rp <%= p.getPrice() %>
        </td>
        <td><%= p.getCategory() %>
        </td>
        <td><%= p.getDescription() %>
        </td>
        <td><img src="../<%= p.getPhoto() %>" alt="foto" width="100"></td>
        <td>
            <a href="edit-product?id=<%= p.getId() %>">Edit</a> |
            <a href="delete-product?id=<%= p.getId() %>" onclick="return confirm('Yakin hapus?')">Delete</a>
        </td>
    </tr>
    <% } %>
</table>

<%
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPages = (Integer) request.getAttribute("totalPages");
%>

<div class="pagination">
    <% if (currentPage > 1) { %>
    <a href="product-list?page=<%= currentPage - 1 %>">Previous</a>
    <% } %>

    <% for (int i = 1; i <= totalPages; i++) { %>
    <a href="product-list?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %>
    </a>
    <% } %>

    <% if (currentPage < totalPages) { %>
    <a href="product-list?page=<%= currentPage + 1 %>">Next</a>
    <% } %>
</div>


</body>
</html>
