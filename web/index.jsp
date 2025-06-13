<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="models.user.Product" %>
<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sekenly | Home</title>
    <%-- Auto Refresh Tiap 5 Detik --%>
    <meta http-equiv="refresh" content="5">

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

            <a class="btn" href="ProductControllers">Explore</a>
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

                    if (products == null) {
                        response.sendRedirect("ProductControllers");
                        return;
                    } else if (products.isEmpty()) {
                        out.println("<div style='color:orange'>products kosong. Data tidak ditemukan di database.</div>");
                    } else {
                        // Loop melalui produk
                        for (Product item : products) {
                %>
                            <div class="item">
                                <a href="pages/user/description.jsp?id=<%= item.getId() %>">
                                    <%
                                    // Get image path from controller
                                    String imageName = "item1.png"; // Default fallback
                                    if (productImages != null && productImages.containsKey(item.getId())) {
                                        imageName = productImages.get(item.getId());
                                    }
                                    %>
                                    <img src="<%= request.getContextPath() %>/assets/<%= imageName %>" alt="<%= item.getName() %>">
                                </a>
                                <div class="desc">
                                    <div class="item-info">
                                        <h3>Rp <%= String.format("%,.0f", item.getPrice()) %></h3>
                                        <p><%= item.getName() %></p>
                                        <p><%= item.getSize() %></p>
                                    </div>
                                    <a href="#" class="fav">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="37" height="37" viewBox="0 0 37 37"
                                            fill="none">
                                            <path
                                                d="M6.53634 18.8855C5.92678 18.28 5.44382 17.5592 5.11562 16.7652C4.78741 15.9711 4.62052 15.1197 4.62467 14.2605C4.62467 12.5228 5.31498 10.8563 6.54374 9.6275C7.77249 8.39875 9.43904 7.70844 11.1768 7.70844C13.6126 7.70844 15.7401 9.03428 16.8655 11.0076H18.5922C19.1643 10.0041 19.9922 9.17008 20.9915 8.59055C21.9907 8.01102 23.1258 7.70665 24.2809 7.70844C26.0186 7.70844 27.6852 8.39875 28.9139 9.6275C30.1427 10.8563 30.833 12.5228 30.833 14.2605C30.833 16.0643 30.0622 17.7293 28.9213 18.8855L17.7288 30.0626L6.53634 18.8855ZM30.0005 19.9801C31.4651 18.5001 32.3747 16.4959 32.3747 14.2605C32.3747 12.1139 31.5219 10.0553 30.0041 8.53738C28.4862 7.01951 26.4275 6.16678 24.2809 6.16678C21.583 6.16678 19.1934 7.47719 17.7288 9.51219C16.9813 8.47388 15.9969 7.62877 14.8575 7.04696C13.718 6.46515 12.4562 6.16341 11.1768 6.16678C9.03016 6.16678 6.97148 7.01951 5.45361 8.53738C3.93574 10.0553 3.08301 12.1139 3.08301 14.2605C3.08301 16.4959 3.99259 18.5001 5.45717 19.9801L17.7288 32.2518L30.0005 19.9801Z"
                                                fill="black"></path>
                                        </svg>
                                    </a>
                                </div>
                            </div>
                <%      }
                    }
                %>
            </div>
            <div class="btn">
                <a href="ProductControllers">See More</a>
            </div>
        </div>
    </div>

    <div class="new-arrival" id="new-arrival">
        <h1>New Arrival</h1>
        <div class="container">
            <div class="new-arrival-item">
                <%
                    // Mengambil produk terbaru dari database (4 produk terbaru)
                    List<Product> newArrivals = (List<Product>) request.getAttribute("hotItems");
                    Map<Integer, String> hotItemsImages = (Map<Integer, String>) request.getAttribute("hotItemsImages");

                    if (newArrivals == null) {
                        newArrivals = Product.getHotItems();
                    }

                    if (newArrivals == null || newArrivals.isEmpty()) {
                        out.println("<div style='color:orange'>Produk terbaru tidak ditemukan di database.</div>");
                    } else {
                        // Loop melalui produk terbaru
                        for (Product item : newArrivals) {
                %>
                    <div class="item">
                        <a href="pages/user/description.jsp?id=<%= item.getId() %>">
                            <%
                            // Get image from controller or use default
                            String imageName = "item1.png"; // default image
                            if (hotItemsImages != null && hotItemsImages.containsKey(item.getId())) {
                                imageName = hotItemsImages.get(item.getId());
                            }
                            %>
                            <img src="<%= request.getContextPath() %>/assets/<%= imageName %>" alt="<%= item.getName() %>">
                        </a>
                        <div class="desc">
                            <div class="item-info">
                                <h3>Rp <%= String.format("%,.0f", item.getPrice()) %></h3>
                                <p><%= item.getName() %></p>
                                <p><%= item.getSize() %></p>
                            </div>
                            <a href="#" class="fav">
                                <svg xmlns="http://www.w3.org/2000/svg" width="37" height="37" viewBox="0 0 37 37"
                                    fill="none">
                                    <path
                                        d="M6.53634 18.8855C5.92678 18.28 5.44382 17.5592 5.11562 16.7652C4.78741 15.9711 4.62052 15.1197 4.62467 14.2605C4.62467 12.5228 5.31498 10.8563 6.54374 9.6275C7.77249 8.39875 9.43904 7.70844 11.1768 7.70844C13.6126 7.70844 15.7401 9.03428 16.8655 11.0076H18.5922C19.1643 10.0041 19.9922 9.17008 20.9915 8.59055C21.9907 8.01102 23.1258 7.70665 24.2809 7.70844C26.0186 7.70844 27.6852 8.39875 28.9139 9.6275C30.1427 10.8563 30.833 12.5228 30.833 14.2605C30.833 16.0643 30.0622 17.7293 28.9213 18.8855L17.7288 30.0626L6.53634 18.8855ZM30.0005 19.9801C31.4651 18.5001 32.3747 16.4959 32.3747 14.2605C32.3747 12.1139 31.5219 10.0553 30.0041 8.53738C28.4862 7.01951 26.4275 6.16678 24.2809 6.16678C21.583 6.16678 19.1934 7.47719 17.7288 9.51219C16.9813 8.47388 15.9969 7.62877 14.8575 7.04696C13.718 6.46515 12.4562 6.16341 11.1768 6.16678C9.03016 6.16678 6.97148 7.01951 5.45361 8.53738C3.93574 10.0553 3.08301 12.1139 3.08301 14.2605C3.08301 16.4959 3.99259 18.5001 5.45717 19.9801L17.7288 32.2518L30.0005 19.9801Z"
                                    fill="black"></path>
                                </svg>
                            </a>
                        </div>
                    </div>
                <%
                        }
                    }
                %>
            </div>
            <div class="btn">
                <a href="ProductControllers">See More</a>
            </div>
        </div>
    </div>

    <jsp:include page="./components/footer.jsp" />
</body>

</html>