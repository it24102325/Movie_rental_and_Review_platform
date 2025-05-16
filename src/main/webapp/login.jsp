<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login - Cineverse</title>
    <link rel="stylesheet" href="assets/styles/register.css">
</head>
<body>
<div class="hero"></div>

<a href="index.jsp" class="logo">cine<span>Verse</span></a>

<div class="wrapper">
    <form class="login-form" action="login" method="post">
        <h2>Login</h2>

        <% if (session.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= session.getAttribute("error") %>
            </div>
            <% session.removeAttribute("error"); %>
        <% } %>

        <% if (session.getAttribute("success") != null) { %>
            <div class="success-message">
                <%= session.getAttribute("success") %>
            </div>
            <% session.removeAttribute("success"); %>
        <% } %>

        <div class="input-field">
            <input type="text" id="uname" name="uname" required>
            <label>Username</label>
        </div>

        <div class="input-field">
            <input type="password" id="pwd" name="pwd" required>
            <label>Password</label>
        </div>

        <button type="submit">Sign In</button>

        <div class="sign-in-btn">
            <p>Don't have an account? <a href="register.jsp">Sign Up</a></p>
            <p class="mt-2">
                <a href="admin-login.jsp">Are you an admin?</a>
            </p>
        </div>
    </form>
</div>

<script>
    // Prevent form resubmission on page refresh
    if (window.history.replaceState) {
        window.history.replaceState(null, null, window.location.href);
    }
</script>
</body>
</html>
