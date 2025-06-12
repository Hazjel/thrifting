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
            <form action="" method="">
                <label for="product">Product Name</label>
                <input type="text" id="product-name" name="product-name" required placeholder="Product Name">
                <div class="price-category">
                    <div class="price">
                        <label for="product">Product Price</label>
                        <input type="text" id="product-price" name="product-price" required placeholder="Product Price">
                    </div>
                    <div class="category">
                        <label for="product">Category</label>
                        <select id="category">
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
            </form>
        </div>

        <div class="button">
            <button>
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="25" viewBox="0 0 24 25" fill="none">
                    <mask id="mask0_156_1107" style="mask-type:alpha" maskUnits="userSpaceOnUse" x="0" y="0" width="24" height="25">
                        <rect y="0.5" width="24" height="24" fill="#D9D9D9"/>
                    </mask>
                    <g mask="url(#mask0_156_1107)">
                        <path d="M7.37295 13.2501L12.5422 18.4193C12.6909 18.568 12.7643 18.742 12.7625 18.9413C12.7605 19.1406 12.682 19.3179 12.527 19.4731C12.3718 19.6179 12.1961 19.6929 12 19.6981C11.8038 19.7032 11.6281 19.6282 11.473 19.4731L5.1327 13.1328C5.03904 13.0391 4.97304 12.9404 4.9347 12.8366C4.8962 12.7327 4.87695 12.6206 4.87695 12.5001C4.87695 12.3796 4.8962 12.2674 4.9347 12.1636C4.97304 12.0597 5.03904 11.961 5.1327 11.8673L11.473 5.52705C11.6115 5.38855 11.783 5.31772 11.9875 5.31455C12.192 5.31139 12.3718 5.38222 12.527 5.52705C12.682 5.68222 12.7595 5.86039 12.7595 6.06155C12.7595 6.26289 12.682 6.44114 12.527 6.5963L7.37295 11.7501H18.75C18.9628 11.7501 19.141 11.8219 19.2845 11.9656C19.4281 12.1091 19.5 12.2872 19.5 12.5001C19.5 12.7129 19.4281 12.8911 19.2845 13.0346C19.141 13.1782 18.9628 13.2501 18.75 13.2501H7.37295Z" fill="#1C1B1F"/>
                    </g>
                </svg>
                Batal
            </button>
            <button>
                Add
            </button>
        </div>
    </div>
</body>
</html>