<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Sekenly | Home</title>

    <jsp:include page="/components/navbar.jsp"/>

    <link rel="stylesheet" href="styles/navbar.css">
    <link rel="stylesheet" href="styles/footer.css">
    <link rel="stylesheet" href="/styles/style.css">
</head>
<body>
    <div class="category">
        <a href="#">Wanita</a>
        <a href="#">Pria</a>
        <a href="#">Branded</a>
        <a href="#">Anak</a>
    </div>

    <div class="dashboard">
        <div class="container">
            <div class="text-dashboard">
                <h1>Shop Preloved Fashion, Save More in Style</h1>
                <p>Temukan fashion favoritmu tanpa bikin dompet tipis. Barang second, kualitas juara!</p>
            </div>

            <a class="btn" href="#">Explore</a>
        </div>
        <div class="image-wrapper">
            <img src="assets/clothes.png" alt="">
            <div class="image-gradient"></div>
        </div>
    </div>

    <div class="hot-item">
        <div class="hot-item-title">
            <h1>HOT ITEMS</h1>

            <div class="hot-item-content">
                <div class="item">
                    <img src="assets/item1.png" alt="">
                    <button class="bookmark">
                        <svg xmlns="http://www.w3.org/2000/svg" width="40" height="40" viewBox="0 0 40 40" fill="none">
                            <path d="M10 10.3333C10 8.46667 10 7.53333 10.3633 6.82C10.6829 6.19282 11.1928 5.68291 11.82 5.36333C12.5333 5 13.4667 5 15.3333 5H24.6667C26.5333 5 27.4667 5 28.18 5.36333C28.8072 5.68291 29.3171 6.19282 29.6367 6.82C30 7.53333 30 8.46667 30 10.3333V32.5083C30 33.3183 30 33.7233 29.8317 33.945C29.7589 34.0414 29.6661 34.1208 29.5596 34.1778C29.4531 34.2348 29.3356 34.268 29.215 34.275C28.9367 34.2917 28.6 34.0667 27.9267 33.6183L20 28.3333L12.0733 33.6167C11.4 34.0667 11.0633 34.2917 10.7833 34.275C10.6631 34.2677 10.5458 34.2345 10.4396 34.1775C10.3334 34.1205 10.2409 34.0412 10.1683 33.945C10 33.7233 10 33.3183 10 32.5083V10.3333Z" fill="#C2C2C2" fill-opacity="0.7" stroke="#C2C2C2" stroke-opacity="0.7" stroke-width="1.5" stroke-linecap="round" stroke-linejoin="round"/>
                            <path opacity="0.5" d="M10 10.3333C10 8.46667 10 7.53333 10.3633 6.82C10.6829 6.19282 11.1928 5.68291 11.82 5.36333C12.5333 5 13.4667 5 15.3333 5H24.6667C26.5333 5 27.4667 5 28.18 5.36333C28.8072 5.68291 29.3171 6.19282 29.6367 6.82C30 7.53333 30 8.46667 30 10.3333V32.5083C30 33.3183 30 33.7233 29.8317 33.945C29.7589 34.0414 29.6661 34.1208 29.5596 34.1778C29.4531 34.2348 29.3356 34.268 29.215 34.275C28.9367 34.2917 28.6 34.0667 27.9267 33.6183L20 28.3333L12.0733 33.6167C11.4 34.0667 11.0633 34.2917 10.7833 34.275C10.6631 34.2677 10.5458 34.2345 10.4396 34.1775C10.3334 34.1205 10.2409 34.0412 10.1683 33.945C10 33.7233 10 33.3183 10 32.5083V10.3333Z" fill="#C2C2C2" fill-opacity="0.7"/>
                        </svg>
                    </button>
                    <h3>Rp 90.000</h3>
                    <p class="item-name">Oakley</p>
                    <p class="size">Other</p>
                </div>
            </div>
        </div>

        <hr>
    </div>

    <jsp:include page="/components/footer.jsp"/>
</body>
</html>
