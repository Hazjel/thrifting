<%@ page import="java.util.List" %>
<%@ page import="models.ManualPayment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<ManualPayment> payments = (List<ManualPayment>) request.getAttribute("payments");
%>
<html>
<head>
    <title>Manual Payments</title>
    <link rel="stylesheet" href="../../styles/list.css">
</head>
<body>
<h1>Manual Payment List</h1>

<table border="1" cellpadding="10" cellspacing="0">
    <tr>
        <th>No</th>
        <th>Buyer</th>
        <th>Product</th>
        <th>Price</th>
        <th>Address</th>
        <th>Status</th>
        <th>Proof</th>
        <th>Action</th>
    </tr>
    <%
        int no = 1;
        for (ManualPayment m : payments) {
    %>
    <tr>
        <td><%= no++ %></td>
        <td><%= m.getBuyerName() %></td>
        <td><%= m.getProductName() %></td>
        <td>Rp <%= m.getPrice() %></td>
        <td><%= m.getAddress() %></td>
        <td><%= m.getStatus() %></td>
        <td>
            <img src="<%= request.getContextPath() + "/" + m.getProofImage() %>" width="100" alt="Bukti">
        </td>
        <td>
            <a href="view-payment?id=<%= m.getId() %>">Detail</a> |
            <a href="approve-payment?id=<%= m.getId() %>">Setujui</a> |
            <a href="reject-payment?id=<%= m.getId() %>" onclick="return confirm('Tolak pembayaran ini?')">Tolak</a>
        </td>
    </tr>
    <% } %>
</table>

</body>
</html>
