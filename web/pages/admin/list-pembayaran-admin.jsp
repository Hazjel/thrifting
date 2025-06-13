<%@ page import="java.util.List" %>
<%@ page import="models.ManualPayment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ManualPayment> payments = (List<ManualPayment>) request.getAttribute("payments");
    int currentPage = (Integer) request.getAttribute("currentPage");
    int totalPages = (Integer) request.getAttribute("totalPages");
%>
<!DOCTYPE html>
<html>
<head>
    <title>List Pembayaran</title>
    <link rel="stylesheet" href="../../styles/list-pembayaran-admin.css">
</head>
<body>
<jsp:include page="../../components/admin-sidebar.jsp"/>

<div class="main-content">
    <div class="header">
        <h1>List Pembayaran</h1>
    </div>

    <div class="payment-list">
        <div class="payment-row header">
            <div class="photo"></div>
            <div>Nama Pembeli</div>
            <div>Nama Barang</div>
            <div>Harga Barang</div>
            <div>Alamat Pembeli</div>
        </div>

        <% for (ManualPayment m : payments) { %>
        <div class="payment-row">
            <div class="photo">
                <img src="<%= request.getContextPath() + "/" + m.getProofImage() %>" alt="Bukti">
            </div>
            <div><%= m.getBuyerName() %></div>
            <div><%= m.getProductName() %></div>
            <div>Rp <%= m.getPrice() %></div>
            <div><%= m.getAddress() %></div>
        </div>
        <% } %>
    </div>

    <div class="pagination">
        <% if (currentPage > 1) { %>
        <a class="pagination-button" href="manual-payment-list?page=<%= currentPage - 1 %>">Previous</a>
        <% } %>

        <% for (int i = 1; i <= totalPages; i++) { %>
        <a class="pagination-number <%= (i == currentPage) ? "active" : "" %>" href="manual-payment-list?page=<%= i %>"><%= i %></a>
        <% } %>

        <% if (currentPage < totalPages) { %>
        <a class="pagination-button" href="manual-payment-list?page=<%= currentPage + 1 %>">Next</a>
        <% } %>
    </div>
</div>

</body>
</html>