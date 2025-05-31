<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>category</title>
    <meta http-equiv="refresh" content="5">
    <link rel="stylesheet" href="/styles/category.css">
    <link rel="stylesheet" href="/styles/navbar.css">
    <link rel="stylesheet" href="/styles/footer.css">
</head>

<body>
    <jsp:include page="/components/navbar.jsp" />

    <div class="content">
        <div class="breadcrumb">
            <a href="/">Home</a> &gt; <span>Category</span>
        </div>

        <div class="container">
            <div class="filter-container">
                <div class="filter-title">
                    <h1>Filter</h1>
                    <img src="../assets/filter-icon.png" alt="filter-icon">
                </div>
                <hr>
                <div class="item-category">
                    <a href="">T-Shirt</a>
                    <a href="">Shorts</a>
                    <a href="">Shirt</a>
                    <a href="">Hoodie</a>
                    <a href="">Jeans</a>
                </div>
                <button type="submit">Apply Filter</button>
            </div>

            <div class="category-item">
                <div class="category-title-item">
                    <h2>Casual</h2>
                    <div class="item-index">
                        <p>Showing 1-100 of 1000 Products</p>
                        <div class="short-by">
                            <label for="sort">Sort by:</label>
                            <select name="sort" id="sort">
                                <option value="popular">Popular</option>
                                <option value="newest">Newest</option>
                                <option value="price-low-to-high">Price: Low to High</option>
                                <option value="price-high-to-low">Price: High to Low</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="item-content">
                    <div class="item-list">
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                        <div class="item">
                            <img src="../assets/image-8.png" alt="Item 3">
                            <div class="item-desc">
                                <h3>Item 3</h3>
                                <p>$39.99</p>
                            </div>
                        </div>
                    </div>

                    <div class="pagination-container">
                        <hr>
                        <div class="pagination">
                            <a href="#">&laquo; Previous</a>
                            <div class="num-page">
                                <a href="#">1</a>
                                <a href="#">2</a>
                                <a href="#">3</a>
                                <span>...</span>
                                <a href="#">6</a>
                                <a href="#">7</a>
                            </div>
                            <a href="#">Next &raquo;</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <jsp:include page="/components/footer.jsp" />
</body>

</html>