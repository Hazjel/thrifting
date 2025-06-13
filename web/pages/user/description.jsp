<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="models.user.Product" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Product Detail | Sekenly</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/navbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/description.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&family=Adamina&family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
</head>
<body>
    <%
        // Get product from request attribute
        Product product = (Product) request.getAttribute("product");
        String productImage = (String) request.getAttribute("productImage");

        // If product is not set, redirect to home page
        if (product == null) {
            response.sendRedirect(request.getContextPath() + "/");
            return;
        }
    %>

    <jsp:include page="/components/navbar.jsp" />

    <div class="product-container">
        <div class="breadcrumb">
            <a href="<%=request.getContextPath()%>/">Home</a> &gt;
            <a href="<%=request.getContextPath()%>/categories?action=category">Shop</a> &gt;
            <a href="<%=request.getContextPath()%>/categories?action=category&category=<%= product.getCategory() %>"><%= product.getCategory() %></a> &gt;
            <span><%= product.getName() %></span>
        </div>

        <div class="product-detail">
            <div class="product-image">
                <img src="<%=request.getContextPath()%>/<%= productImage %>" alt="<%= product.getName() %>">
            </div>

            <div class="product-info">
                <h1><%= product.getName() %></h1>
                <div class="product-meta">
                    <p class="product-category"><%= product.getCategory() %></p>
                </div>
                <div class="product-price">
                    <h2>Rp <%= String.format("%,.0f", product.getPrice()) %></h2>
                </div>

                <div class="product-description">
                    <h3>Product Description</h3>
                    <p><%= product.getDescription() %></p>
                </div>

                <div class="product-actions">
                    <button class="add-to-cart-btn">
                        Beli Sekarang
                    </button>
                </div>
            </div>
        </div>

        <div class="additional-info">
            <div class="info-tabs">
                <button class="tab-btn active" data-tab="details">Product Details</button>
            </div>

            <div class="tab-content active" id="details-content">
                <h3>Product Details</h3>
                <p><%= product.getDescription() %></p>
                <div class="product-attributes">
                    <div class="attribute">
                        <span class="attribute-name">Category:</span>
                        <span class="attribute-value"><%= product.getCategory() %></span>
                    </div>
                    <% if (product.getSize() != null && !product.getSize().isEmpty()) { %>
                    <div class="attribute">
                        <span class="attribute-name">Size:</span>
                        <span class="attribute-value"><%= product.getSize() %></span>
                    </div>
                    <% } %>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/components/footer.jsp" />

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Tab functionality
            const tabBtns = document.querySelectorAll('.tab-btn');
            const tabContents = document.querySelectorAll('.tab-content');

            tabBtns.forEach(btn => {
                btn.addEventListener('click', function() {
                    // Remove active class from all buttons and contents
                    tabBtns.forEach(b => b.classList.remove('active'));
                    tabContents.forEach(c => c.classList.remove('active'));

                    // Add active class to clicked button and corresponding content
                    this.classList.add('active');
                    const tabId = this.getAttribute('data-tab');
                    document.getElementById(tabId + '-content').classList.add('active');
                });
            });
        });
    </script>
</body>
</html>
