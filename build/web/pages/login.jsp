<!DOCTYPE html>
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="refresh" content="5">
    <title>Login | Sekenly</title>

    <link rel="stylesheet" href="../styles/login.css">
</head>

<body>
    <div class="login">
        <div class="title">
            <h1>Login</h1>
        </div>

        <div class="container">
            <div class="form">
                <form action="#" method="#">
                    <div class="username">
                        <label for="username">Username</label>
                        <input type="text" id="username" name="username" placeholder="Input your username" required>
                    </div>
                    <div class="password">
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" placeholder="Input your password" required>
                    </div>

                    <button>
                        <p>Submit</p>
                    </button>
                </form>
            </div>

            <p>
                Dont have an account? <a href="register.jsp">Register</a>
            </p>

        </div>

        <div class="back">
            <a href="../index.jsp">Return to Homepage</a>
        </div>
    </div>
</body>

</html>