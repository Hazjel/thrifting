<%@ page import="models.user.Product" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Category</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/category.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/navbar.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/styles/footer.css">
</head>

<body>
    <jsp:include page="/components/navbar.jsp" />

    <%
        // Get attributes from the controller
        String selectedCategory = (String)request.getAttribute("selectedCategory");
        String sortBy = (String)request.getAttribute("sortBy");
        if (sortBy == null) sortBy = "newest"; // Default if not set

        List<Product> products = (List<Product>)request.getAttribute("products");
        List<String> availableCategories = (List<String>)request.getAttribute("availableCategories");

        // If products is null, redirect to the controller
        if (products == null) {
            response.sendRedirect(request.getContextPath() + "/categories?action=category");
            return;
        }

        // Default title
        String pageTitle = "All Products";

        // If a category is selected, update page title
        if (selectedCategory != null && !selectedCategory.isEmpty()) {
            pageTitle = selectedCategory;
        }
    %>

    <div class="content">
        <div class="breadcrumb">
            <a href="<%=request.getContextPath()%>/">Home</a> &gt; <span>Category</span> <% if(selectedCategory != null) { %> &gt; <span><%= selectedCategory %></span> <% } %>
        </div>

        <div class="container">
            <div class="filter-container">
                <div class="filter-title">
                    <h1>Filter</h1>
                    <img src="<%=request.getContextPath()%>/assets/filter-icon.png" alt="filter-icon">
                </div>
                <hr>
                <div class="item-category">
                    <% for (String category : availableCategories) { %>
                    <a href="<%=request.getContextPath()%>/categories?action=category&category=<%= category %>" <%= category.equals(selectedCategory) ? "class='active'" : "" %>><%= category %></a>
                    <% } %>
                </div>
                <a href="<%=request.getContextPath()%>/categories?action=category" class="filter-button">
                    <button type="button">Clear Filter</button>
                </a>
            </div>

            <div class="category-item">
                <div class="category-title-item">
                    <h2><%= pageTitle %></h2>
                    <div class="item-index">
                        <p>Showing filtered products</p>
                        <div class="short-by">
                            <label for="sort">Sort by:</label>
                            <select name="sort" id="sort" onchange="location = this.value;">
                                <%
                                // Create proper URL for the sort options, handling cases when no category is selected
                                String baseUrl = request.getContextPath() + "/categories?action=category";
                                String categoryParam = selectedCategory != null && !selectedCategory.isEmpty() ? "&category=" + selectedCategory + "&" : "&";
                                %>
                                <option value="<%= baseUrl + categoryParam %>sort=newest" <%= "newest".equals(sortBy) ? "selected" : "" %>>Newest</option>
                                <option value="<%= baseUrl + categoryParam %>sort=price-low-to-high" <%= "price-low-to-high".equals(sortBy) ? "selected" : "" %>>Price: Low to High</option>
                                <option value="<%= baseUrl + categoryParam %>sort=price-high-to-low" <%= "price-high-to-low".equals(sortBy) ? "selected" : "" %>>Price: High to Low</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="item-content">
                    <div class="item-list">
                    <%
                        // Display message if no products found
                        if(products.isEmpty()) {
                    %>
                        <div class="no-products">
                            <p>No products found in this category.</p>
                        </div>
                    <%
                        } else {
                            // Get product images map from controller
                            Map<Integer, String> productImages = (Map<Integer, String>)request.getAttribute("productImages");

                            // Display each product
                            for(Product product : products) {
                                String productName = product.getName();
                                double price = product.getPrice();
                                int productId = product.getId();

                                // Get image path from the map provided by controller
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
                        }
                    %>
                    </div>

                    <div class="pagination-container">
                        <hr>
                        <div class="pagination">
                            <%
                            // Get pagination data from controller
                            Integer currentPage = (Integer)request.getAttribute("currentPage");
                            Integer totalPages = (Integer)request.getAttribute("totalPages");
                            Integer totalProducts = (Integer)request.getAttribute("totalProducts");

                            if (currentPage == null) currentPage = 1;
                            if (totalPages == null) totalPages = 1;
                            if (totalProducts == null) totalProducts = 0;

                            // Base URL for pagination (use a different variable name)
                            String paginationBaseUrl = request.getContextPath() + "/categories?action=category";
                            if (selectedCategory != null && !selectedCategory.isEmpty()) {
                                paginationBaseUrl += "&category=" + selectedCategory;
                            }
                            if (sortBy != null && !sortBy.isEmpty()) {
                                paginationBaseUrl += "&sort=" + sortBy;
                            }
                            %>

                            <!-- Previous page link -->
                            <a href="<%= paginationBaseUrl %>&page=<%= Math.max(1, currentPage - 1) %>" <%= currentPage <= 1 ? "class='disabled'" : "" %>>&laquo; Previous</a>

                            <div class="num-page">
                                <%
                                // Show page numbers
                                int startPage = Math.max(1, currentPage - 2);
                                int endPage = Math.min(totalPages, startPage + 4);
                                if (endPage - startPage < 4) {
                                    startPage = Math.max(1, endPage - 4);
                                }

                                for (int i = startPage; i <= endPage; i++) {
                                %>
                                <a href="<%= paginationBaseUrl %>&page=<%= i %>" <%= i == currentPage ? "class='active'" : "" %>><%= i %></a>
                                <%
                                }

                                // Show ellipsis if needed
                                if (endPage < totalPages) {
                                %>
                                <span>...</span>
                                <a href="<%= paginationBaseUrl %>&page=<%= totalPages %>"><%= totalPages %></a>
                                <%
                                }
                                %>
                            </div>

                            <!-- Next page link -->
                            <a href="<%= paginationBaseUrl %>&page=<%= Math.min(totalPages, currentPage + 1) %>" <%= currentPage >= totalPages ? "class='disabled'" : "" %>>Next &raquo;</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/components/footer.jsp" />
</body>

</html>
