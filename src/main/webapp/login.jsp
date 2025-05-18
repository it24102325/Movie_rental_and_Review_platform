<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Sign in - CineVerse</title>
    <link rel="stylesheet" href="assets/styles/login.css">
</head>
<body>
<div class="hero"></div>

<a href="home.jsp" class="logo">cine<span>Verse</span></a>

<div class="wrapper">
    <form class="login-form" action="login" method="post" autocomplete="off">
        <h2>Login</h2>

        <% if(request.getAttribute("error") != null) { %>
        <div class="error-message">
            <%= request.getAttribute("error") %>
        </div>
        <% } %>

        <div class="input-field">
            <input type="text" id="uname" name="uname" required
                   value="<%= request.getParameter("uname") != null ? request.getParameter("uname") : "" %>">
            <label for="uname">Username</label>
        </div>

        <div class="input-field">
            <input type="password" id="pass" name="pwd" required>
            <label for="pass">Password</label>
        </div>

        <button type="submit">Log In</button>

        <div class="register">
            <p>Don't have an account? <a href="register.jsp">Register</a></p>
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
