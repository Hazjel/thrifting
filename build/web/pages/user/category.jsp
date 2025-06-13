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