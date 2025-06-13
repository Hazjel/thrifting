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
%>

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
        .empty-state {
            text-align: center;
            padding: 30px;
            color: #666;
            font-style: italic;
        }

        .status-badge {
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 12px;
            font-weight: 500;
            display: inline-block;
            min-width: 120px;
            text-align: center;
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
    </style>
</head>
<body>
<jsp:include page="../../components/user-sidebar.jsp"/>

<div class="container-dashboard" id="dashboard">
    <p class="welcome-text">
        Selamat datang, <%= displayName %>!
    </p>

    <div class="content">
        <p>Pembelian Terakhir</p>

        <table>
            <thead>
                <tr>
                    <th scope="col">Tanggal</th>
                    <th scope="col">Nama Produk</th>
                    <th scope="col">Harga</th>
                    <th scope="col">Alamat Pengiriman</th>
                    <th scope="col">Status</th>
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
                        <td colspan="5" class="empty-state">Anda belum memiliki riwayat pembelian</td>
                    </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
