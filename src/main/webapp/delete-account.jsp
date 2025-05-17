<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account delete</title>
</head>
<body>
  <%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");

    if(session.getAttribute("user") == null){
      response.sendRedirect("login.jsp");
    }
  %>
  <h2>Your account has been deleted successfully</h2>
  <p><a href="register.jsp">Register Again</a></p>
</body>
</html>
