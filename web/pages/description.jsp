<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="styles.css">
    <title>Product Detail</title>
</head>
<body>
<header>
    <h1>SHOP.CO</h1>
    <nav>
        <a href="#">Shop</a>
        <a href="#">On Sale</a>
        <a href="#">New Arrivals</a>
        <a href="#">Brands</a>
    </nav>
</header>
<main>
    <div class="product-detail">
        <div class="product-image">
            <img src="path-to-tshirt-image.jpg" alt="One Life Graphic T-shirt">
        </div>
        <div class="product-info">
            <h2>One Life Graphic T-shirt</h2>
            <p class="price">$260</p>
            <p class="description">
                Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
            </p>
            <div class="quantity">
                <button>-</button>
                <input type="number" value="1">
                <button>+</button>
            </div>
            <button class="add-to-cart">Add to Cart</button>
        </div>
    </div>
</main>
<footer>
    <p>SHOP.CO © 2000-2023, All Rights Reserved</p>
    <p>We have clothes that suits your style and which you’re proud to wear. From women to men.</p>
</footer>
</body>
</html>