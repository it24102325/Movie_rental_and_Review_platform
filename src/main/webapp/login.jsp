<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign in</title>
    <link rel="stylesheet" href="assets/styles/login.css">
</head>
<body>
<div class="hero"></div>
<a href="index.jsp" class="logo">cine<span>Verse</span></a>
<div class="wrapper">
    <form class="login-form" action="login" method="post">
        <h2>Login</h2>

        <p class="login-message-area">${error}</p>

        <div class="input-field">
            <input type="text" id="uname" name="uname" required>
            <label>Username</label>
        </div>

        <div class="input-field">
            <input type="password" id="pass" name="pwd" required>
            <label>Password</label>
        </div>

        <div class="forget">
            <input type="checkbox" id="remember" name="remember" value="Remember">
            <label for="remember">Remember</label><br>
            <a href="#">Forgot password?</a>
        </div>

        <button type="submit">Log In</button>

        <div class="register">
            <p>Don't have an account? <a href="register.jsp">Register</a></p>
        </div>
    </form>
</div>

</body>
</html>
