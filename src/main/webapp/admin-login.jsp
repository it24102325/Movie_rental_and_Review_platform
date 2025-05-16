<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%@ page import="models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
    
    // Check if user is already logged in as admin
    User user = (User) session.getAttribute("user");
    if (user != null && user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/admin/dashboard.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Login - CineVerse</title>
    <link rel="stylesheet" href="assets/styles/register.css">
</head>
<body>
<div class="hero"></div>

<a href="index.jsp" class="logo">cine<span>Verse</span></a>

<div class="wrapper">
    <form class="login-form" action="admin-login" method="post" autocomplete="off">
        <h2>Admin Login</h2>

        <c:if test="${not empty sessionScope.error}">
            <div class="error-message">
                ${sessionScope.error}
            </div>
            <c:remove var="error" scope="session"/>
        </c:if>

        <div class="input-field">
            <input type="text" id="username" name="username" required>
            <label>Admin Username</label>
        </div>

        <div class="input-field">
            <input type="password" id="password" name="password" required>
            <label>Admin Password</label>
        </div>

        <button type="submit">Login as Admin</button>

        <div class="sign-in-btn">
            <p>Not an admin? <a href="login.jsp">User Login</a></p>
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