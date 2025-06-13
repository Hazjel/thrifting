QQ<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tambah Barang Baru</title>
    <link rel="stylesheet" href="../../styles/add-product.css">
</head>
<body>
<div class="form-container">
    <h1>Tambah Barang Baru</h1>

    <form action="<%= request.getContextPath() %>/add-product" method="POST" enctype="multipart/form-data">
        <label for="product-name">Nama Produk</label>
        <input type="text" id="product-name" name="product-name" placeholder="Nama Produk" required>

        <div class="row">
            <div class="col">
                <label for="product-price">Harga Barang</label>
                <input type="text" id="product-price" name="product-price" placeholder="Harga Barang" required>
            </div>
            <div class="col">
                <label for="category">Kategori Barang</label>
                <select id="category" name="category" required>
                    <option disabled selected hidden>Kategori Barang</option>
                    <option value="t-shirt">T-Shirt</option>
                    <option value="short">Short</option>
                    <option value="shirt">Shirt</option>
                    <option value="hoodie">Hoodie</option>
                    <option value="jeans">Jeans</option>
                    <option value="other">Other</option>
                </select>
            </div>
        </div>

        <label for="description">Deskripsi Produk</label>
        <input type="text" id="description" name="description" placeholder="Deskripsi Produk" required>

        <div class="buttons">
            <a href="${pageContext.request.contextPath}/product-list" class="cancel-btn">← Batal</a>
            <button type="submit" class="submit-btn">Tambahkan</button>
        </div>
    </form>
</div>
</body>
</html>
