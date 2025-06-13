<%@ page import="models.Product" %><%
    Product product = (Product) request.getAttribute("product");
%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../../styles/edit.css">
    <title>Edit Product</title>
</head>
<body>
<div class="add">
    <div class="container">
        <h1>Edit Product</h1>
        <form action="edit-product" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= product.getId() %>">

            <label for="product-name">Product Name</label>
            <input type="text" id="product-name" name="product-name" required placeholder="Product Name"
                   value="<%= product.getName() %>">

            <div class="price-category">
                <div class="price">
                    <label for="product-price">Product Price</label>
                    <input type="text" id="product-price" name="product-price" required placeholder="Product Price"
                           value="<%= product.getPrice() %>">
                </div>
                <div class="category">
                    <label for="category">Category</label>
                    <select id="category" name="category">
                        <option value="t-shirt" <%= product.getCategory().equals("t-shirt") ? "selected" : "" %>>T-Shirt</option>
                        <option value="short" <%= product.getCategory().equals("short") ? "selected" : "" %>>Short</option>
                        <option value="shirt" <%= product.getCategory().equals("shirt") ? "selected" : "" %>>Shirt</option>
                        <option value="hoodie" <%= product.getCategory().equals("hoodie") ? "selected" : "" %>>Hoodie</option>
                        <option value="other" <%= product.getCategory().equals("other") ? "selected" : "" %>>Other</option>
                    </select>
                </div>
            </div>

            <label for="description">Product Description</label>
            <input type="text" id="description" name="description" required placeholder="Product Description"
                   value="<%= product.getDescription() %>">

            <label for="photo">Product Photo</label>
            <input type="file" id="photo" name="photo" accept="image/*">

            <div class="button">
                <a href="product-list">
                    <button type="button">Batal</button>
                </a>
                <button type="submit">Update</button>
            </div>
        </form>

    </div>
</div>
</body>
</html>
