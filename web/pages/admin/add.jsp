<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="../../styles/add.css">
    <title>Tambah Produk</title>
</head>
<body>
<div class="add">
    <div class="container">
        <h1>Add New Product</h1>
        <form action="<%= request.getContextPath() %>/add-product" method="POST" enctype="multipart/form-data">
            <label for="product">Product Name</label>
            <input type="text" id="product-name" name="product-name" required placeholder="Product Name">

            <div class="price-category">
                <div class="price">
                    <label for="product">Product Price</label>
                    <input type="text" id="product-price" name="product-price" required placeholder="Product Price">
                </div>
                <div class="category">
                    <label for="product">Category</label>
                    <select id="category" name="category">
                        <option value="t-shirt">T-Shirt</option>
                        <option value="short">Short</option>
                        <option value="shirt">Shirt</option>
                        <option value="hoodie">Hoodie</option>
                        <option value="other">Other</option>
                    </select>
                </div>
            </div>

            <label for="description">Product Description</label>
            <input type="text" id="description" name="description" required placeholder="Product Description">

            <label for="photo">Product Photo</label>
            <input type="file" id="photo" name="photo" accept="image/*" required>

            <div class="button">
                <button type="reset">Batal</button>
                <button type="submit">Add</button>
            </div>
        </form>

    </div>
</div>
</body>
</html>