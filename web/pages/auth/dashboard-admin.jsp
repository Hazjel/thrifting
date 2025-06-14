<%@ page import="models.user.User" %>
<%@ page import="models.Product" %>
<%@ page import="models.ManualPayment" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.ProductDAO" %>
<%@ page import="dao.PaymentDAO" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    User currentUser = (User) session.getAttribute("user");
    String displayName = "Admin";
    if (currentUser != null) {
        displayName = currentUser.getUsername();
    }

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
        latestPayments = paymentDAO.getLatestPayments(5);
        totalPayments = paymentDAO.getTotalPayments();
    } catch (Exception e) {
        e.printStackTrace();
    }

    // Ensure lists are not null
    if (latestProducts == null) latestProducts = new ArrayList<>();
    if (latestPayments == null) latestPayments = new ArrayList<>();

    // Format for dates
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy HH:mm");
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
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

        @keyframes fadeIn {
            from {opacity: 0;}
            to {opacity: 1;}
        }

        @keyframes fadeOut {
            from {opacity: 1;}
            to {opacity: 0;}
        }

        /* Payment section styling */
        .payment-item {
            background: white;
            border-radius: 8px;
            padding: 15px;
            margin-bottom: 15px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.05);
            display: flex;
            align-items: center;
            gap: 15px;
            transition: transform 0.2s;
        }

        .payment-item:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .payment-icon {
            background: #e3f2fd;
            color: #1976d2;
            width: 50px;
            height: 50px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            flex-shrink: 0;
        }

        .payment-details {
            flex: 1;
        }

        .payment-details h3 {
            margin: 0 0 5px 0;
            font-size: 16px;
            color: #333;
        }

        .payment-info {
            display: flex;
            flex-wrap: wrap;
            gap: 12px;
            font-size: 14px;
        }

        .payment-info span {
            display: flex;
            align-items: center;
            color: #666;
        }

        .payment-info strong {
            color: #1976d2;
            margin-right: 4px;
        }

        .payment-price {
            font-weight: 700;
            color: #2e7d32;
        }

        .no-data-message {
            text-align: center;
            padding: 30px;
            background: #f5f5f7;
            border-radius: 8px;
            color: #666;
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
                            <% if (!latestProducts.isEmpty()) { %>
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
                            <% } else { %>
                                <tr>
                                    <td colspan="5">
                                        <div class="no-data-message">Belum ada produk untuk ditampilkan</div>
                                    </td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
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

            <div class="section payments-section">
                <div class="section-header">
                    <h2>Pembayaran Terbaru</h2>
                    <a href="${pageContext.request.contextPath}/manual-payment-list" class="view-all">Lihat Semua</a>
                </div>

                <% if (!latestPayments.isEmpty()) { %>
                    <% for(ManualPayment payment : latestPayments) { %>
                    <div class="payment-item">
                        <div class="payment-icon">
                            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                <rect x="1" y="4" width="22" height="16" rx="2" ry="2"></rect>
                                <line x1="1" y1="10" x2="23" y2="10"></line>
                            </svg>
                        </div>
                        <div class="payment-details">
                            <h3><%= payment.getProductName() != null ? payment.getProductName() : "Produk" %></h3>
                            <div class="payment-info">
                                <span><strong>Pembeli:</strong> <%= payment.getBuyerName() != null ? payment.getBuyerName() : "User" %></span>
                                <span class="payment-price"><strong>Rp</strong> <%= String.format("%,.0f", (double)payment.getPrice()) %></span>
                                <span><strong>Alamat:</strong> <%= payment.getAddress() != null ? payment.getAddress() : "-" %></span>
                            </div>
                        </div>
                    </div>
                    <% } %>
                <% } else { %>
                    <div class="no-data-message">
                        <p>Belum ada pembayaran untuk ditampilkan</p>
                    </div>
                <% } %>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Handle notification close
            var notification = document.querySelector('.notification');
            if (notification) {
                notification.querySelector('.close').addEventListener('click', function() {
                    notification.style.display = 'none';
                });

                // Auto-hide after 5 seconds
                setTimeout(function() {
                    notification.style.display = 'none';
                }, 5000);
            }
        });
    </script>
</body>
</html>

