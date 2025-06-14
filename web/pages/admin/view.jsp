<%@ page import="java.util.List" %>
<%@ page import="models.Product" %>
<%@ page import="models.user.Order" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Daftar Produk | Admin Thrifting Store</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
    <link rel="stylesheet" href="../../styles/list.css">
    <link rel="stylesheet" href="../../styles/components/admin-sidebar.css">
</head>
<body>
    <jsp:include page="../../components/admin-sidebar.jsp"/>

    <div class="main-content">
        <div class="page-header">
            <div>
                <div class="breadcrumb">
                    <a href="dashboard-admin.jsp">Dashboard</a> / Daftar Produk
                </div>
                <h1>Daftar Semua Produk</h1>
            </div>
            <a href="${pageContext.request.contextPath}/pages/admin/add.jsp" class="add-product-btn">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="12" y1="5" x2="12" y2="19"></line>
                    <line x1="5" y1="12" x2="19" y2="12"></line>
                </svg>
                Tambah Produk
            </a>
        </div>

        <div class="dashboard-container">
            <div class="product-section">
                <div class="product-grid">
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        for (Product p : products) {
                    %>
                    <div class="product-card">
                        <div class="product-image">
                            <img src="${pageContext.request.contextPath}/<%= p.getPhoto() %>" alt="<%= p.getName() %>">
                        </div>
                        <div class="product-details">
                            <h3 class="product-name"><%= p.getName() %></h3>
                            <div class="product-price">Rp <%= String.format("%,.0f", p.getPrice()) %></div>
                            <span class="product-category"><%= p.getCategory() %></span>
                            <p class="product-description"><%= p.getDescription() %></p>
                            <div class="product-actions">
                                <a href="${pageContext.request.contextPath}/edit-product?id=<%= p.getId() %>" class="edit-btn">Edit</a>
                                <a href="${pageContext.request.contextPath}/delete-product?id=<%= p.getId() %>" class="delete-btn" onclick="return confirm('Anda yakin ingin menghapus produk ini?')">Hapus</a>
                            </div>
                        </div>
                    </div>
                    <% } %>
                </div>

                <%
                    int currentPage = (Integer) request.getAttribute("currentPage");
                    int totalPages = (Integer) request.getAttribute("totalPages");
                %>

                <div class="pagination">
                    <% if (currentPage > 1) { %>
                    <a href="${pageContext.request.contextPath}/product-list?page=<%= currentPage - 1 %>">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <polyline points="15 18 9 12 15 6"></polyline>
                        </svg>
                        Prev
                    </a>
                    <% } %>

                    <%
                        // Display a limited range of page numbers
                        int startPage = Math.max(1, currentPage - 2);
                        int endPage = Math.min(totalPages, currentPage + 2);

                        if (startPage > 1) { %>
                            <a href="${pageContext.request.contextPath}/product-list?page=1">1</a>
                            <% if (startPage > 2) { %>
                                <span>...</span>
                            <% } %>
                        <% } %>

                        <% for (int i = startPage; i <= endPage; i++) { %>
                            <a href="${pageContext.request.contextPath}/product-list?page=<%= i %>" class="<%= (i == currentPage) ? "active" : "" %>"><%= i %></a>
                        <% } %>

                        <% if (endPage < totalPages) { %>
                            <% if (endPage < totalPages - 1) { %>
                                <span>...</span>
                            <% } %>
                            <a href="${pageContext.request.contextPath}/product-list?page=<%= totalPages %>"><%= totalPages %></a>
                        <% } %>

                    <% if (currentPage < totalPages) { %>
                    <a href="${pageContext.request.contextPath}/product-list?page=<%= currentPage + 1 %>">
                        Next
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <polyline points="9 18 15 12 9 6"></polyline>
                        </svg>
                    </a>
                    <% } %>
                </div>
            </div>

            <div class="orders-sidebar">
                <div class="orders-header">
                    <h2>Pembayaran Terbaru</h2>
                    <a href="${pageContext.request.contextPath}/manual-payment-list" class="view-all-orders">Lihat Semua</a>
                </div>
                <div class="orders-list">
                    <%
                        List<Order> recentOrders = (List<Order>) request.getAttribute("recentOrders");
                        if (recentOrders != null && !recentOrders.isEmpty()) {
                            for (Order order : recentOrders) {
                    %>
                    <div class="order-item">
                        <div class="order-customer">
                            <span class="label">Pembeli:</span>
                            <span class="value"><%= order.getCustomerName() %></span>
                        </div>
                        <div class="order-product">
                            <span class="label">Produk:</span>
                            <span class="value"><%= order.getProductName() %></span>
                        </div>
                        <div class="order-price">
                            <span class="label">Harga:</span>
                            <span class="value">Rp <%= String.format("%,.0f", order.getProductPrice()) %></span>
                        </div>
                        <div class="order-date">
                            <span class="label">Tanggal:</span>
                            <span class="value"><%= order.getCreatedAt() %></span>
                        </div>
                    </div>
                    <%
                            }
                        } else {
                    %>
                    <div class="no-orders">Tidak ada pembayaran terbaru</div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
