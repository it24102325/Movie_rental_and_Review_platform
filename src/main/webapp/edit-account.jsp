<%@ page import="models.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit account</title>
    <link rel="stylesheet" href="assets/styles/edit-account.css">

</head>
<body>
    <%
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

        User user = (User) session.getAttribute("user");

        if(session.getAttribute("user") == null){
            response.sendRedirect("login.jsp");
        }

    %>
    <p>${error}</p>
    <form action="update" method="post">

        Name: <input type="text" name="name" value="<%=user.getName()%>" required><br>
        Username: <input type="text" name="uname" value="<%=user.getUsername()%>" required><br>
        Email: <input type="email" name="email" value="<%=user.getEmail()%>" disabled><br>
        New Password: <input type="password" name="pwd" required><br>
        <button type="submit">Save</button>
    </form>

</body>
</html>
