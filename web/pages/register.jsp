<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>JSP Page</title>

    <link rel="stylesheet" href="../styles/register.css">
</head>

<body>
    <div class="container">
        <div class="register-text">
            <h1>Register</h1>
            <p>Welcome to our registration page! Please fill out the form below to create your account.</p>
        </div>
        <form action="" method="">
            <div class="input-container">
                <div class="input">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" placeholder="Enter your email" required>
                </div>
                <div class="input">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" placeholder="Enter your username" required>
                </div>
                <div class="input">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" placeholder="Enter your password" required>
                </div>
                <div class="input">
                    <label for="confirm-password">Confirm Password</label>
                    <input type="password" id="confirm-password" name="confirm-password" placeholder="Confirm your password" required>
                </div>
            </div>
            <button type="submit">Submit</button>
        </form>

        <p>Already have account? <a href="login.jsp"> Login Here!</a></p>

        <div class="back">
            <a href="../index.jsp">Return to Homepage</a>
        </div>
    </div>
</body>

</html>