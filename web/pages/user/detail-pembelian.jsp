<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Detail Pembelian | Sekenly</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/navbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/detail-pembayaran.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;600&display=swap" rel="stylesheet">
</head>
<body>
<%
    // Check if user is logged in
    if (session.getAttribute("user") == null) {
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }

    // Get product details from request parameters
    String productId = request.getParameter("productId");
    String productName = request.getParameter("productName");
    String productPrice = request.getParameter("productPrice");
    String productImage = request.getParameter("productImage");

    // If we don't have product details from request, try to get from session
    if (productId == null || productName == null || productPrice == null) {
        productId = (String) session.getAttribute("checkout_productId");
        productName = (String) session.getAttribute("checkout_productName");
        productPrice = (String) session.getAttribute("checkout_productPrice");
        productImage = (String) session.getAttribute("checkout_productImage");
    }

    // If still no product details, redirect to home
    if (productId == null || productName == null || productPrice == null) {
        response.sendRedirect(request.getContextPath() + "/");
        return;
    }

    // Display error if any
    String error = (String) request.getAttribute("error");
%>

<jsp:include page="../../components/navbar.jsp" />

<div class="purchase-container">
    <h1>Detail Pembelian</h1>

    <% if (error != null) { %>
        <div class="error-message"><%= error %></div>
    <% } %>

    <div class="product-summary">
        <div class="product-image">
            <img src="<%=request.getContextPath()%>/<%= productImage %>" alt="<%= productName %>">
        </div>
        <div class="product-details">
            <h2><%= productName %></h2>
            <p class="product-price">Rp <%= productPrice %></p>
        </div>
    </div>

    <form action="<%=request.getContextPath()%>/purchase/submit" method="post">
        <input type="hidden" name="productId" value="<%= productId %>">
        <input type="hidden" name="productName" value="<%= productName %>">
        <input type="hidden" name="productPrice" value="<%= productPrice %>">
        <input type="hidden" name="productImage" value="<%= productImage %>">

        <div class="form-group">
            <label for="fullName">Nama Lengkap</label>
            <input type="text" id="fullName" name="fullName" placeholder="Contoh: Asep Kurnia" required>
        </div>

        <div class="form-group">
            <label for="phone">No Hp</label>
            <input type="tel" id="phone" name="phone" placeholder="Contoh: 081234567890" required>
        </div>

        <div class="form-group">
            <label for="address">Alamat Lengkap</label>
            <input type="text" id="address" name="address" placeholder="Contoh: Jl. Telekomunikasi No.1, Bandung" required>
        </div>

        <div class="form-group">
            <label for="notes">Catatan (Opsional)</label>
            <textarea id="notes" name="notes" placeholder="Contoh: Tolong kirim sore hari."></textarea>
        </div>

        <button type="submit" class="submit-btn">Kirim</button>
    </form>
</div>

<jsp:include page="../../components/footer.jsp" />
</body>
</html>