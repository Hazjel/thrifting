<%@ page import="models.user.User" %>
<%@ page import="models.Product" %>
<%@ page import="models.ManualPayment" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="dao.PaymentDAO" %>
<%@ page import="java.util.ArrayList" %>
<%
    User currentUser = (User) session.getAttribute("user");
    String displayName = "Admin";
    if (currentUser != null) {
        displayName = currentUser.getUsername();
    }

    // Check if we're in "view all payments" mode
    boolean viewAllPayments = "true".equals(request.getParameter("viewAllPayments"));

    // Get product data
    ProductDAO productDAO = new ProductDAO();
    List<Product> latestProducts = new ArrayList<>();
    int totalProducts = 0;

    try {
        latestProducts = productDAO.getLatestProducts(5);
        totalProducts = productDAO.getTotalProducts();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Get payment data
    PaymentDAO paymentDAO = new PaymentDAO();
    List<ManualPayment> latestPayments = new ArrayList<>();
    int totalPayments = 0;

    try {
        // If viewAllPayments is true, get all payments, otherwise just the latest 5
        latestPayments = viewAllPayments ? paymentDAO.getAllPayments() : paymentDAO.getLatestPayments(5);
        totalPayments = paymentDAO.getTotalPayments();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Ensure lists are not null
    if (latestProducts == null) latestProducts = new ArrayList<>();
    if (latestPayments == null) latestPayments = new ArrayList<>();

    // Check for success messages
    String successParam = request.getParameter("success");
    String successMessage = null;
    if (successParam != null) {
        switch (successParam) {
            case "product_added":
                successMessage = "Produk berhasil ditambahkan!";
                break;
            case "product_updated":
                successMessage = "Produk berhasil diperbarui!";
                break;
            case "product_deleted":
                successMessage = "Produk berhasil dihapus!";
                break;
        }
    }
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <link rel="stylesheet" href="../../styles/components/admin-sidebar.css">
    <link rel="stylesheet" href="../../styles/dashboard-admin.css">
    <title>Admin Dashboard - Thrifting Store</title>
    <style>
        .notification {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            justify-content: space-between;
            animation: fadeIn 0.5s, fadeOut 0.5s 5s forwards;
        }

        .notification.success {
            background-color: #e8f5e9;
            color: #2e7d32;
            border-left: 4px solid #2e7d32;
        }

        .notification .message {
            display: flex;
            align-items: center;
        }

        .notification .icon {
            margin-right: 12px;
            display: flex;
            align-items: center;
        }

        .notification .close {
            cursor: pointer;
            font-size: 18px;
            font-weight: bold;
        }

        .back-btn {
            display: inline-flex;
            align-items: center;
            background-color: #f0f0f0;
            color: #333;
            padding: 8px 16px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            margin-bottom: 15px;
            transition: background-color 0.3s;
        }

        .back-btn:hover {
            background-color: #e0e0e0;
        }

        .back-btn svg {
            margin-right: 6px;
        }

        @keyframes fadeIn {
            from {opacity: 0;}
            to {opacity: 1;}
        }

        @keyframes fadeOut {
            from {opacity: 1;}
            to {opacity: 0;}
        }
    </style>
</head>
<body>
    <jsp:include page="../../components/admin-sidebar.jsp"/>

    <div class="main-content">
        <div class="welcome-header">
            <h1>Selamat Datang, <%= displayName %>!</h1>
            <p>Berikut adalah ringkasan aktivitas toko anda</p>
        </div>

        <% if (successMessage != null) { %>
        <div class="notification success" id="notification">
            <div class="message">
                <div class="icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M22 11.08V12a10 10 0 1 1-5.93-9.14"></path>
                        <polyline points="22 4 12 14.01 9 11.01"></polyline>
                    </svg>
                </div>
                <%= successMessage %>
            </div>
            <div class="close" onclick="closeNotification()">&times;</div>
        </div>
        <% } %>

        <% if (!viewAllPayments) { %>
        <div class="dashboard-stats">
            <div class="stat-card">
                <div class="stat-icon products-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <rect x="2" y="3" width="20" height="14" rx="2" ry="2"></rect>
                        <line x1="8" y1="21" x2="16" y2="21"></line>
                        <line x1="12" y1="17" x2="12" y2="21"></line>
                    </svg>
                </div>
                <div class="stat-info">
                    <h3>Total Produk</h3>
                    <p class="stat-value"><%= totalProducts %></p>
                </div>
            </div>
            <div class="stat-card">
                <div class="stat-icon orders-icon">
                    <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path>
                        <line x1="3" y1="6" x2="21" y2="6"></line>
                        <path d="M16 10a4 4 0 0 1-8 0"></path>
                    </svg>
                </div>
                <div class="stat-info">
                    <h3>Total Pesanan</h3>
                    <p class="stat-value"><%= totalPayments %></p>
                </div>
            </div>
        </div>

        <div class="dashboard-sections">
            <div class="section products-section">
                <div class="section-header">
                    <h2>Produk Terbaru</h2>
                    <a href="${pageContext.request.contextPath}/product-list" class="view-all">Lihat Semua</a>
                </div>
                <div class="data-table">
                    <table>
                        <thead>
                            <tr>
                                <th>Gambar</th>
                                <th>Nama Produk</th>
                                <th>Harga</th>
                                <th>Kategori</th>
                                <th>Aksi</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Product product : latestProducts) { %>
                            <tr>
                                <td class="product-image">
                                    <img src="${pageContext.request.contextPath}/<%= product.getPhoto() %>" alt="<%= product.getName() %>">
                                </td>
                                <td><%= product.getName() %></td>
                                <td>Rp <%= String.format("%,.0f", product.getPrice()) %></td>
                                <td><span class="category-badge"><%= product.getCategory() %></span></td>
                                <td class="actions">
                                    <a href="${pageContext.request.contextPath}/edit-product?id=<%= product.getId() %>" class="edit-btn">Edit</a>
                                    <a href="${pageContext.request.contextPath}/delete-product?id=<%= product.getId() %>" class="delete-btn" onclick="return confirm('Anda yakin ingin menghapus produk ini?')">Hapus</a>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% if(latestProducts.isEmpty()) { %>
                        <div class="no-data">Tidak ada produk untuk ditampilkan</div>
                    <% } %>
                </div>
                <div class="add-new">
                    <a href="${pageContext.request.contextPath}/pages/admin/add.jsp" class="add-btn">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <line x1="12" y1="5" x2="12" y2="19"></line>
                            <line x1="5" y1="12" x2="19" y2="12"></line>
                        </svg>
                        Tambah Produk Baru
                    </a>
                </div>
            </div>
        <% } %>

            <div class="section payments-section">
                <div class="section-header">
                    <h2>Pembayaran <%= viewAllPayments ? "Semua" : "Terbaru" %></h2>
                    <% if (viewAllPayments) { %>
                        <a href="${pageContext.request.contextPath}/pages/admin/dashboard-admin.jsp" class="view-all">Kembali</a>
                    <% } else { %>
                        <a href="${pageContext.request.contextPath}/pages/admin/dashboard-admin.jsp?viewAllPayments=true" class="view-all">Lihat Semua</a>
                    <% } %>
                </div>

                <% if (viewAllPayments) { %>
                <a href="${pageContext.request.contextPath}/pages/admin/dashboard-admin.jsp" class="back-btn">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <line x1="19" y1="12" x2="5" y2="12"></line>
                        <polyline points="12 19 5 12 12 5"></polyline>
                    </svg>
                    Kembali ke Dashboard
                </a>
                <% } %>

                <div class="data-table">
                    <table>
                        <thead>
                            <tr>
                                <th>Nama Pembeli</th>
                                <th>Produk</th>
                                <th>Harga</th>
                                <th>Alamat Pengiriman</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(ManualPayment payment : latestPayments) { %>
                            <tr>
                                <td><%= payment.getBuyerName() != null ? payment.getBuyerName() : "User" %></td>
                                <td><%= payment.getProductName() != null ? payment.getProductName() : "Produk" %></td>
                                <td>Rp <%= String.format("%,.0f", (double)payment.getPrice()) %></td>
                                <td><%= payment.getAddress() != null ? payment.getAddress() : "-" %></td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                    <% if(latestPayments.isEmpty()) { %>
                        <div class="no-data">Tidak ada pembayaran untuk ditampilkan</div>
                    <% } %>
                </div>
            </div>
        <% if (!viewAllPayments) { %>
        </div>
        <% } %>
    </div>

    <script>
        // Add event listeners to ensure edit and delete buttons work properly
        document.addEventListener('DOMContentLoaded', function() {
            // Fix for edit buttons
            document.querySelectorAll('.edit-btn').forEach(function(btn) {
                btn.addEventListener('click', function(e) {
                    const href = this.getAttribute('href');
                    if (href) {
                        window.location.href = href;
                    }
                    e.preventDefault();
                });
            });

            // Fix for delete buttons with confirmation
            document.querySelectorAll('.delete-btn').forEach(function(btn) {
                btn.addEventListener('click', function(e) {
                    const href = this.getAttribute('href');
                    if (confirm('Anda yakin ingin menghapus item ini?')) {
                        if (href) {
                            window.location.href = href;
                        }
                    }
                    e.preventDefault();
                });
            });
        });

        function closeNotification() {
            const notification = document.getElementById('notification');
            if (notification) {
                notification.style.display = 'none';
            }
        }
    </script>
</body>
</html>