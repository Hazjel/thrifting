<%@ page import="models.Product" %><%
    Product product = (Product) request.getAttribute("product");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Nama Barang</title>
    <link rel="stylesheet" href="../../styles/edit.css">
</head>
<body>
<div class="edit">
    <div class="container">
        <h1>Edit Nama Barang</h1>
        <form action="edit-product" method="post" enctype="multipart/form-data">
            <input type="hidden" name="id" value="<%= product.getId() %>">

            <label for="product-name">Nama Produk</label>
            <input type="text" id="product-name" name="product-name" required placeholder="Nama Produk"
                   value="<%= product.getName() %>">

            <div class="price-category">
                <div class="price">
                    <label for="product-price">Harga Barang</label>
                    <input type="text" id="product-price" name="product-price" required placeholder="Harga Barang"
                           value="<%= product.getPrice() %>">
                </div>
                <div class="category">
                    <label for="category">Kategori Barang</label>
                    <select id="category" name="category">
                        <option value="t-shirt" <%= product.getCategory().equals("t-shirt") ? "selected" : "" %>>T-Shirt</option>
                        <option value="short" <%= product.getCategory().equals("short") ? "selected" : "" %>>Short</option>
                        <option value="shirt" <%= product.getCategory().equals("shirt") ? "selected" : "" %>>Shirt</option>
                        <option value="hoodie" <%= product.getCategory().equals("hoodie") ? "selected" : "" %>>Hoodie</option>
                        <option value="other" <%= product.getCategory().equals("other") ? "selected" : "" %>>Other</option>
                    </select>
                </div>
            </div>

            <label for="description">Deskripsi Produk</label>
            <input type="text" id="description" name="description" required placeholder="Deskripsi Produk"
                   value="<%= product.getDescription() %>">

            <label for="photo">Foto Barang</label>
            <input type="file" id="photo" name="photo" accept="image/*">

            <div class="button">
                <a href="product-list" class="cancel-button">
                    Batal
                </a>
                <button type="submit" class="submit-button">Simpan</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
