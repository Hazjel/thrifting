<%@ page import="models.user.User" %>
<%@ page import="models.user.Order" %>
<%@ page import="dao.OrderDAO" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%
    User currentUser = (User) session.getAttribute("user");
    String displayName = "Guest";
    int userId = 0;

    if (currentUser != null) {
        displayName = currentUser.getUsername();
        userId = currentUser.getId();
    } else {
        // Redirect to login if not logged in
        response.sendRedirect(request.getContextPath() + "/auth/login");
        return;
    }

    // Get user's order history
    OrderDAO orderDAO = new OrderDAO();
    List<Order> userOrders = orderDAO.getOrdersByUserId(userId);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

    // Get count of user's orders
    int totalOrders = userOrders != null ? userOrders.size() : 0;

    // Calculate total spending if orders exist
    double totalSpending = 0;
    if (userOrders != null) {
        for (Order order : userOrders) {
            totalSpending += order.getProductPrice();
        }
    }
%>

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Riwayat Pembelian - Thrifting Store</title>
    <link rel="stylesheet" href="../../styles/components/user-sidebar.css">
    <link rel="stylesheet" href="../../styles/dashboard-admin.css">
    <style>
        .action-buttons {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-top: 15px;
        }

        .action-button {
            display: flex;
            align-items: center;
            padding: 12px 24px;
            border-radius: 8px;
            text-decoration: none;
            font-weight: 500;
            border: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
            font-size: 16px;
        }

        .logout-button {
            background-color: #ffebee;
            color: #c62828;
        }

        .logout-button:hover {
            background-color: #ffcdd2;
        }

        .login-button {
            background-color: #e3f2fd;
            color: #1976d2;
        }

        .login-button:hover {
            background-color: #bbdefb;
        }

        .home-button {
            background-color: #e8f5e9;
            color: #2e7d32;
        }

        .home-button:hover {
            background-color: #c8e6c9;
        }

        .action-button svg {
            margin-right: 8px;
        }

        .header-actions {
            display: flex;
            align-items: center;
            gap: 16px;
            margin-top: 15px;
        }

        .status-menunggu-konfirmasi {
            background-color: #feecdc;
            color: #ff5a1f;
        }

        .status-dikonfirmasi {
            background-color: #e1effe;
            color: #3f83f8;
        }

        .status-diproses {
            background-color: #fdf6b2;
            color: #c27803;
        }

        .status-dikirim {
            background-color: #def7ec;
            color: #0e9f6e;
        }

        .status-selesai {
            background-color: #d1fae5;
            color: #047857;
        }

        .status-dibatalkan {
            background-color: #fee2e2;
            color: #e02424;
        }

        .user-stats-card {
            background: linear-gradient(135deg, #4a6cf7 0%, #2d3a94 100%);
            color: white;
            position: relative;
            overflow: hidden;
        }

        .user-stats-card::after {
            content: '';
            position: absolute;
            top: -50%;
            right: -50%;
            width: 100%;
            height: 200%;
            background: rgba(255, 255, 255, 0.1);
            transform: rotate(30deg);
            pointer-events: none;
        }

        .user-stats-card .stat-info h3,
        .user-stats-card .stat-value {
            color: white;
        }

        .user-stats-card .stat-icon {
            background-color: rgba(255, 255, 255, 0.2);
            color: white;
        }

        .welcome-text-highlight {
            color: #4a6cf7;
            font-weight: 700;
        }

        .section-title-bar {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
        }

        .section-title-bar h2 {
            position: relative;
            padding-left: 15px;
            font-size: 22px;
            color: #333;
        }

        .section-title-bar h2:before {
            content: '';
            position: absolute;
            left: 0;
            top: 0;
            height: 100%;
            width: 5px;
            background-color: #4a6cf7;
            border-radius: 3px;
        }
    </style>
</head>
<body>
<jsp:include page="../../components/user-sidebar.jsp"/>

<div class="main-content">
    <div class="welcome-header">
        <h1>Riwayat Pembelian <span class="welcome-text-highlight"><%= displayName %></span></h1>
        <p>Berikut adalah ringkasan aktivitas belanja anda di Thrifting Store</p>

        <div class="action-buttons">
            <a href="${pageContext.request.contextPath}/" class="action-button home-button">
                <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M3 9l9-7 9 7v11a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2z"></path>
                    <polyline points="9 22 9 12 15 12 15 22"></polyline>
                </svg>
                Homepage
            </a>
            <% if (currentUser != null) { %>
                <form action="<%= request.getContextPath() %>/auth/logout" method="GET">
                    <button class="action-button logout-button" type="submit">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                            <path d="M9 21H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h4"></path>
                            <polyline points="16 17 21 12 16 7"></polyline>
                            <line x1="21" y1="12" x2="9" y2="12"></line>
                        </svg>
                        Logout
                    </button>
                </form>
            <% } else { %>
                <a href="${pageContext.request.contextPath}/auth/login" class="action-button login-button">
                    <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                        <path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4"></path>
                        <polyline points="10 17 15 12 10 7"></polyline>
                        <line x1="15" y1="12" x2="3" y2="12"></line>
                    </svg>
                    Login
                </a>
            <% } %>
        </div>
    </div>

    <div class="dashboard-stats">
        <div class="stat-card user-stats-card">
            <div class="stat-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M6 2L3 6v14a2 2 0 0 0 2 2h14a2 2 0 0 0 2-2V6l-3-4z"></path>
                    <line x1="3" y1="6" x2="21" y2="6"></line>
                    <path d="M16 10a4 4 0 0 1-8 0"></path>
                </svg>
            </div>
            <div class="stat-info">
                <h3>Total Pesanan</h3>
                <p class="stat-value"><%= totalOrders %></p>
            </div>
        </div>
        <div class="stat-card user-stats-card">
            <div class="stat-icon">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <line x1="12" y1="1" x2="12" y2="23"></line>
                    <path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"></path>
                </svg>
            </div>
            <div class="stat-info">
                <h3>Total Belanja</h3>
                <p class="stat-value">Rp <%= String.format("%,.0f", totalSpending) %></p>
            </div>
        </div>
    </div>

    <div class="dashboard-sections">
        <div class="section">
            <div class="section-title-bar">
                <h2>Riwayat Pembelian</h2>
            </div>
            <div class="data-table">
                <table>
                    <thead>
                        <tr>
                            <th>Tanggal</th>
                            <th>Nama Produk</th>
                            <th>Harga</th>
                            <th>Alamat Pengiriman</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% if(userOrders != null && !userOrders.isEmpty()) { %>
                            <% for(Order order : userOrders) { %>
                                <tr>
                                    <td><%= dateFormat.format(order.getCreatedAt()) %></td>
                                    <td><%= order.getProductName() %></td>
                                    <td>Rp <%= String.format("%,.0f", order.getProductPrice()) %></td>
                                    <td><%= order.getShippingAddress() %></td>
                                    <td>
                                        <span class="status-badge status-<%= order.getOrderStatus().toLowerCase().replace(" ", "-") %>">
                                            <%= order.getOrderStatus() %>
                                        </span>
                                    </td>
                                </tr>
                            <% } %>
                        <% } else { %>
                            <tr>
                                <td colspan="5">
                                    <div class="no-data">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="48" height="48" viewBox="0 0 24 24" fill="none" stroke="#999" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                            <circle cx="9" cy="21" r="1"></circle>
                                            <circle cx="20" cy="21" r="1"></circle>
                                            <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                        </svg>
                                        <p style="margin-top: 15px;">Anda belum memiliki riwayat pembelian</p>
                                        <a href="${pageContext.request.contextPath}/" class="add-btn" style="margin-top: 15px; display: inline-flex;">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                                                <circle cx="9" cy="21" r="1"></circle>
                                                <circle cx="20" cy="21" r="1"></circle>
                                                <path d="M1 1h4l2.68 13.39a2 2 0 0 0 2 1.61h9.72a2 2 0 0 0 2-1.61L23 6H6"></path>
                                            </svg>
                                            Belanja Sekarang
                                        </a>
                                    </div>
                                </td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

</body>
</html>
