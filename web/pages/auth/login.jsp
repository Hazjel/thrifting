<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.user.User" %>
<%
    User currentUser = (User) session.getAttribute("user");
    if (currentUser != null) {
        response.sendRedirect(request.getContextPath() + "/pages/admin/dashboard-admin.jsp");
        return;
    }

    String errorMessage = (String) request.getAttribute("error");
    String logoutMessage = request.getParameter("message");
%>


<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login | Sekenly</title>

    <link rel="stylesheet" href="../../styles/login.css">
</head>

<body>
<div class="login">
    <div class="title">
        <h1>Login</h1>
    </div>


    <% if (request.getAttribute("error") != null) { %>
    <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>

    <% if ("logout".equals(logoutMessage)) { %>
    <div class="message success">
        Anda telah berhasil keluar dari sistem
    </div>
    <% } %>

    <div class="container">
        <div class="form">
            <form action="${pageContext.request.contextPath}/auth/login" method="post">
                <div class="email">
                    <label for="email">Email</label>
                    <input type="email"
                           id="email"
                           name="email"
                           placeholder="contoh@email.com"
                           value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>"
                           required
                           autocomplete="email"
                    >
                </div>

                <div class="password">
                    <label for="password">Password</label>
                    <input
                            type="password"
                            id="password"
                            name="password"
                            placeholder="Masukkan password Anda"
                            required
                            autocomplete="current-password"
                    >
                </div>

                <button type="submit">
                    <p>Log In</p>
                </button>
            </form>
        </div>

        <p>
            Dont have an account? <a href="register.jsp">Register</a>
        </p>

    </div>


    <div class="back">
        <a href="../../index.jsp">Return to Homepage</a>
    </div>
</div>
</body>

</html>


