
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="models.user.Product" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sekenly | Home</title>

    <jsp:include page="./components/navbar.jsp" />

    <link rel="stylesheet" href="styles/navbar.css">
    <link rel="stylesheet" href="styles/footer.css">
    <link rel="stylesheet" href="styles/style.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&family=Adamina&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Plus+Jakarta+Sans:ital,wght@0,200..800;1,200..800&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>

<body>
<div class="dashboard">
    <div class="container">
        <div class="text-dashboard">
            <h1>Shop Preloved Fashion, Save More in Style</h1>
            <p>Temukan fashion favoritmu tanpa bikin dompet tipis. Barang second, kualitas juara!</p>
        </div>

        <a class="btn" href="<%= request.getContextPath() %>/ProductControllers?action=category">Explore</a>
    </div>
    <div class="image-wrapper">
        <img src="assets/clothes.png" alt="">
        <div class="image-gradient"></div>
    </div>
</div>

<div class="new-arrival" id="new-arrival">
    <h1>Products</h1>
    <div class="container">
        <div class="new-arrival-item">
            <%
                List<Product> products = (List<Product>) request.getAttribute("products");
                Map<Integer, String> productImages = (Map<Integer, String>) request.getAttribute("productImages");

                if (products == null || products.isEmpty()) {
                    out.println("<div style='color:orange'>Products kosong. Data tidak ditemukan di database.</div>");
                } else {
                    // Loop melalui produk
                    for (Product item : products) {
            %>
            <div class="item">
                <a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= item.getId() %>">
                    <%
                        // Get image path from controller
                        String imageName = "images/clothes.png"; // Default fallback
                        if (productImages != null && productImages.containsKey(item.getId())) {
                            imageName = productImages.get(item.getId());
                        }
                    %>
                    <img src="<%= request.getContextPath() %>/<%= imageName %>" alt="<%= item.getName() %>">
                </a>
                <div class="desc">
                    <div class="item-info">
                        <h3>Rp <%= String.format("%,.0f", item.getPrice()) %></h3>
                        <p><a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= item.getId() %>"><%= item.getName() %></a></p>
                        <p><%= item.getSize() %></p>
                    </div>
                </div>
            </div>
            <%      }
            }
            %>
        </div>
        <div class="btn">
            <a href="<%= request.getContextPath() %>/ProductControllers?action=category">See More</a>
        </div>
    </div>
</div>

<div class="new-arrival" id="new-arrival">
    <h1>New Arrival</h1>
    <div class="container">
        <div class="new-arrival-item">
            <%
                List<Product> hotItems = (List<Product>) request.getAttribute("hotItems");
                Map<Integer, String> hotItemsImages = (Map<Integer, String>) request.getAttribute("hotItemsImages");

                if (hotItems == null || hotItems.isEmpty()) {
                    out.println("<div style='color:orange'>Produk terbaru tidak ditemukan di database.</div>");
                } else {
                    // Loop melalui produk terbaru
                    for (Product item : hotItems) {
            %>
            <div class="item">
                <a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= item.getId() %>">
                    <%
                        // Get image from controller
                        String imageName = "images/clothes.png"; // default image
                        if (hotItemsImages != null && hotItemsImages.containsKey(item.getId())) {
                            imageName = hotItemsImages.get(item.getId());
                        }
                    %>
                    <img src="<%= request.getContextPath() %>/<%= imageName %>" alt="<%= item.getName() %>">
                </a>
                <div class="desc">
                    <div class="item-info">
                        <h3>Rp <%= String.format("%,.0f", item.getPrice()) %></h3>
                        <p><a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= item.getId() %>"><%= item.getName() %></a></p>
                        <p><%= item.getSize() %></p>
                    </div>
                </div>
            </div>
            <%
                    }
                }
            %>
        </div>
        <div class="btn">
            <a href="<%= request.getContextPath() %>/ProductControllers?action=category">See More</a>
        </div>
    </div>
</div>

<jsp:include page="./components/footer.jsp" />
</body>

</html>