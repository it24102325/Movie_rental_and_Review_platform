<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="assets/styles/profile.css">
</head>
<body>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
%>

<div class="profile-container">
    <h1>Hello ${user.username}</h1>

    <form method="get" action="logout">
        <input type="submit" value="Log out">
    </form>

    <p class="error-message">${error}</p>

    <form action="delete" method="post" onsubmit="return confirm('Are you sure you want to delete your account?')">
        <input type="submit" value="Remove Account">
    </form>

    <p><a href="edit-account.jsp" class="edit-btn">Edit Account</a></p>
</div>
</body>
</html>
