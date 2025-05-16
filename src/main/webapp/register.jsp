<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link rel="stylesheet" href="assets/styles/register.css">
</head>
<body>
<div class="hero"></div>

<a href="#" class="logo">cine<span>Verse</span></a>

<div class="wrapper">
    <form class="reg-form" action="register" method="post">
        <h2>Register</h2>

        <p class="reg-message-area">${error}</p>

        <div class="input-field">
            <input type="text" name="name" id="name" required>
            <label>Name</label>
        </div>

        <div class="input-field">
            <input type="text" id="uname" name="uname" required>
            <label>Username</label>
        </div>

        <div class="input-field">
            <input type="email" name="email" id="email" required>
            <label>Email Address</label>
        </div>

        <div class="input-field">
            <input type="password" id="pass" name="pwd" required>
            <label>Password</label>
        </div>

        <button type="submit">Create Account</button>

        <div class="sign-in-btn">
            <p>Already have an account? <a href="login.jsp">Sign in</a></p>
        </div>
    </form>
</div>

</body>
</html>
