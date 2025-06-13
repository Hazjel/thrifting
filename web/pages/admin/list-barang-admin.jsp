<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Product List</title>
    <link rel="stylesheet" href="../../styles/components/admin-sidebar.css">
    <link rel="stylesheet" href="../../styles/list-barang-admin.css">
</head>
<body>

<jsp:include page="../../components/admin-sidebar.jsp"/>

<div class="main-content">
    <div class="header">
        <h1>List Barang</h1>
        <a href="./pages/admin/add.jsp" class="add-button">Tambah Produk</a>
    </div>

    <div class="product-list">
        <div class="product-row header">
            <div class="product-photo"></div>
            <div>Nama Produk</div>
            <div>Harga Barang</div>
            <div>Kategori Barang</div>
            <div>Status</div>
            <div>Action</div>
        </div>

        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            for (Product p : products) {
        %>
        <div class="product-row">
            <div class="product-photo">
                <img src="../<%= p.getPhoto() %>" alt="foto" width="60" height="60">
            </div>
            <div><%= p.getName() %></div>
            <div>Rp <%= p.getPrice() %></div>
            <div><%= p.getCategory() %></div>
<%--            <div><%= p.getStatus() %></div>--%>
            <div>
                <a href="edit-product?id=<%= p.getId() %>">Edit</a> |
                <a href="delete-product?id=<%= p.getId() %>" onclick="return confirm('Yakin hapus?')">Delete</a>
            </div>
        </div>
        <% } %>
    </div>

    <div class="pagination">
        <%
            int currentPage = (Integer) request.getAttribute("currentPage");
            int totalPages = (Integer) request.getAttribute("totalPages");
        %>

        <% if (currentPage > 1) { %>
        <a href="product-list?page=<%= currentPage - 1 %>" class="pagination-button">Previous</a>
        <% } %>

        <% for (int i = 1; i <= totalPages; i++) { %>
        <a href="product-list?page=<%= i %>" class="pagination-number <%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
        <% } %>

        <% if (currentPage < totalPages) { %>
        <a href="product-list?page=<%= currentPage + 1 %>" class="pagination-button">Next</a>
        <% } %>
    </div>
</div>

</body>
</html>
