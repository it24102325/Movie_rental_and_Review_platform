<%--
  Created by IntelliJ IDEA.
  User: yeshi
  Date: 5/12/2025
  Time: 9:04 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.*, models.Payment, dao.PaymentDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String userID = request.getParameter("userID");
    if (userID == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    PaymentDao dao = new PaymentDao();
    List<Payment> payments = dao.getPaymentsByUser(userID);
%>
<html>
<head>
    <title>Transaction History</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="assets/styles/payment.css">
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="home.jsp">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <span id="close-menu-btn" class="material-symbols-outlined">close</span>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="movies.jsp">Movies</a></li>
            <li><a href="reviews.jsp">Reviews</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="contact.jsp">Contact Us</a></li>
            <li><a href="logout.jsp">Logout</a></li>
            <li><a href="payment.jsp">Payment</a></li>
        </ul>
        <span id="hamburger-btn" class="material-symbols-outlined">menu</span>
    </nav>
</header>

<section class="payment-section">
    <div class="payment-card">
        <h2>Transaction History for User: <%= userID %></h2>
        <% if (payments.isEmpty()) { %>
        <p>No transactions found.</p>
        <% } else { %>
        <table class="transaction-table">
            <tr>
                <th>Payment ID</th>
                <th>Rental ID</th>
                <th>User ID</th>
                <th>Amount</th>
                <th>Payment Method</th>
                <th>Date</th>
                <th>Movie Title</th>
                <th>Status</th>
            </tr>
            <% for (Payment p : payments) { %>
            <tr>
                <td><%= p.getPaymentID() %></td>
                <td><%= p.getRentalID() %></td>
                <td><%= p.getUserId() %></td>
                <td><%= p.getAmount() %></td>
                <td><%= p.getPaymentMethod() %></td>
                <td><%= p.getPaymentDate() != null ? p.getPaymentDate() : "Date Missing" %></td>
                <td><%= p.getMovieTitle() %></td>
                <td><%= p.getStatus() %></td>
            </tr>
            <% } %>
        </table>
        <% } %>
    </div>
</section>

<script>
    const header = document.querySelector("header");
    const hamburgerBtn = document.querySelector("#hamburger-btn");
    const closeMenuBtn = document.querySelector("#close-menu-btn");

    hamburgerBtn.addEventListener("click", () => header.classList.toggle("show-mobile-menu"));
    closeMenuBtn.addEventListener("click", () => header.classList.remove("show-mobile-menu"));
</script>
</body>
</html>