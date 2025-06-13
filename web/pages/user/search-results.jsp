<%@ page import="models.user.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Search Results - Sekenly</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/navbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/footer.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/style.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/category.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/search.css">

    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Abril+Fatface&family=Adamina&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Plus+Jakarta+Sans:ital,wght@0,200..800;1,200..800&family=Roboto:ital,wght@0,100..900;1,100..900&display=swap" rel="stylesheet">
</head>

<body>
<jsp:include page="../../components/navbar.jsp" />

<div class="search-results">
    <div class="search-heading">
        <h1>Search Results</h1>
        <p>Showing results for: "<%= request.getAttribute("searchQuery") %>"</p>
    </div>

    <div class="item-content">
        <%
            List<Product> products = (List<Product>) request.getAttribute("products");
            Map<Integer, String> productImages = (Map<Integer, String>) request.getAttribute("productImages");

            if (products == null || products.isEmpty()) {
        %>
        <div class="no-results">
            <p>No products found matching your search criteria.</p>
            <a href="<%= request.getContextPath() %>/">Return to Home</a>
        </div>
        <%
        } else {
        %>
        <div class="item-list">
            <%
                for (Product item : products) {
                    String productName = item.getName();
                    double price = item.getPrice();
                    int productId = item.getId();

                    // Get image path
                    String imagePath = request.getContextPath() + "/" +
                            (productImages != null && productImages.containsKey(productId) ?
                                    productImages.get(productId) : "images/clothes.png");
            %>
            <div class="item">
                <a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= productId %>">
                    <img src="<%= imagePath %>" alt="<%= productName %>">
                </a>
                <div class="item-desc">
                    <h3><a href="<%= request.getContextPath() %>/ProductControllers?action=detail&id=<%= productId %>"><%= productName %></a></h3>
                    <p>Rp <%= String.format("%,.0f", price) %></p>
                </div>
            </div>
            <%
                }
            %>
        </div>
        <%
            }
        %>
    </div>
</div>

<jsp:include page="../../components/footer.jsp" />
</body>

</html>
