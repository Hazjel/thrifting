<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.user.User" %>
<%
    String errorMessage = (String) request.getAttribute("error");
    String logoutMessage = request.getParameter("message");
%>

<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Thrifting Store</title>

    <link rel="stylesheet" href="../../styles/login.css">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f5f7fb;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }

        .login-container {
            display: flex;
            width: 100%;
            max-width: 1000px;
            height: 600px;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.1);
            border-radius: 20px;
            overflow: hidden;
        }

        .login-image {
            flex: 1;
            background-image: url('../../assets/clothes.png');
            background-size: cover;
            background-position: center;
            position: relative;
        }

        .login-image::after {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(135deg, rgba(74, 108, 247, 0.8) 0%, rgba(45, 58, 148, 0.8) 100%);
        }

        .login-image-content {
            position: absolute;
            bottom: 40px;
            left: 40px;
            z-index: 1;
            color: white;
        }

        .login-image-content h2 {
            font-size: 28px;
            margin-bottom: 10px;
        }

        .login-image-content p {
            font-size: 16px;
            max-width: 300px;
            line-height: 1.5;
        }

        .login-form {
            flex: 1;
            padding: 50px;
            background-color: white;
            display: flex;
            flex-direction: column;
            justify-content: center;
        }

        .login-header {
            margin-bottom: 30px;
        }

        .login-header h1 {
            font-size: 32px;
            color: #333;
            margin-bottom: 10px;
            font-weight: 700;
        }

        .login-header p {
            color: #666;
            font-size: 16px;
        }

        .form-group {
            margin-bottom: 25px;
        }

        .form-group label {
            display: block;
            margin-bottom: 8px;
            font-weight: 500;
            color: #333;
            font-size: 14px;
        }

        .form-group input {
            width: 100%;
            padding: 14px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border 0.3s ease;
            box-sizing: border-box;
        }

        .form-group input:focus {
            border-color: #4a6cf7;
            outline: none;
            box-shadow: 0 0 0 3px rgba(74, 108, 247, 0.2);
        }

        .login-button {
            background: linear-gradient(135deg, #4a6cf7 0%, #2d3a94 100%);
            color: white;
            border: none;
            border-radius: 8px;
            padding: 15px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            width: 100%;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            margin-top: 10px;
        }

        .login-button:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(74, 108, 247, 0.3);
        }

        .login-footer {
            margin-top: 30px;
            text-align: center;
            color: #666;
        }

        .login-footer a {
            color: #4a6cf7;
            text-decoration: none;
            font-weight: 500;
            transition: color 0.3s ease;
        }

        .login-footer a:hover {
            color: #2d3a94;
            text-decoration: underline;
        }

        .home-link {
            display: inline-flex;
            align-items: center;
            color: #4a6cf7;
            text-decoration: none;
            font-weight: 500;
            margin-top: 20px;
            transition: color 0.3s ease;
        }

        .home-link svg {
            margin-right: 8px;
            transition: transform 0.3s ease;
        }

        .home-link:hover {
            color: #2d3a94;
        }

        .home-link:hover svg {
            transform: translateX(-3px);
        }

        .error-message {
            background-color: #ffebee;
            color: #c62828;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid #c62828;
        }

        .success-message {
            background-color: #e8f5e9;
            color: #2e7d32;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 25px;
            border-left: 4px solid #2e7d32;
        }
    </style>
</head>

<body>
<div class="login-container">
    <div class="login-image">
        <div class="login-image-content">
            <h2>Welcome to Thrifting Store</h2>
            <p>Find the best second-hand fashion items at affordable prices.</p>
        </div>
    </div>

    <div class="login-form">
        <div class="login-header">
            <h1>Welcome Back</h1>
            <p>Please login to your account to continue shopping</p>
        </div>

        <% if (request.getAttribute("error") != null) { %>
        <div class="error-message">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <% if ("logout".equals(logoutMessage)) { %>
        <div class="success-message">
            Anda telah berhasil keluar dari sistem
        </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/auth/login" method="post">
            <div class="form-group">
                <label for="email">Email Address</label>
                <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="your@email.com"
                    value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>"
                    required
                    autocomplete="email"
                >
            </div>

            <div class="form-group">
                <label for="password">Password</label>
                <input
                    type="password"
                    id="password"
                    name="password"
                    placeholder="Enter your password"
                    required
                    autocomplete="current-password"
                >
            </div>

            <button type="submit" class="login-button">
                Sign In
            </button>
        </form>

        <div class="login-footer">
            <p>
                Don't have an account? <a href="${pageContext.request.contextPath}/pages/auth/register.jsp">Register Now</a>
            </p>

            <a href="${pageContext.request.contextPath}/" class="home-link">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round">
                    <path d="M19 12H5"></path>
                    <polyline points="12 19 5 12 12 5"></polyline>
                </svg>
                Return to Homepage
            </a>
        </div>
    </div>
</div>
</body>

</html>