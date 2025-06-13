<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>User Dashboard</title>
    <link rel="stylesheet" href="../../styles/components/user-sidebar.css">
    <link rel="stylesheet" href="../../styles/dashboard.css">

    <style>

    </style>
</head>
<body>
<jsp:include page="../../components/user-sidebar.jsp"/>

<div class="container-dashboard" id="dashboard">
    <p class="welcome-text">
        Selamat datang User!
    </p>

    <div class="content">
        <p>Pembelian Terakhir</p>

        <table>
            <thead>
                <tr>
                    <th scope="col">Gambar Produk</th>
                    <th scope="col">Nama Produk</th>
                    <th scope="col">Harga Produk</th>
                    <th scope="col">Kategori</th>
                    <th scope="col">Status</th>
                </tr>
            </thead>

            <tbody>
                <tr>
                    <td>
                        <div style="background: #D9D9D9; width: 98px; height: 98px;"></div>
                    </td>
                    <td>Nama Produk</td>
                    <td>Harga Produk</td>
                    <td>Kategori</td>
                    <td>Status</td>
                </tr>

                <tr>
                    <td>
                        <div style="background: #D9D9D9; width: 98px; height: 98px;"></div>
                    </td>
                    <td>Nama Produk</td>
                    <td>Harga Produk</td>
                    <td>Kategori</td>
                    <td>Status</td>
                </tr>

                <tr>
                    <td>
                        <div style="background: #D9D9D9; width: 98px; height: 98px;"></div>
                    </td>
                    <td>Nama Produk</td>
                    <td>Harga Produk</td>
                    <td>Kategori</td>
                    <td>Status</td>
                </tr>
            </tbody>
        </table>
    </div>
</div>

<div class="container-riwayat" id="riwayat">
    <p>History</p>
</div>


</body>
</html>
