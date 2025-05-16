paymentCheckout.jsp<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Payment History</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
    <link rel="stylesheet" href="assets/styles/payment.css">
</head>
<body>
<%
    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MMMM d, yyyy");
    String currentDate = dateFormat.format(new java.util.Date());
%>
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
        <h2 class="enhanced-heading">Transaction History</h2>
        <a href="paymentCheckout.jsp" class="btn-primary enhanced-button">
            <span class="material-symbols-outlined">add</span> Make a Payment
        </a>

        <table class="transaction-table">
            <thead>
            <tr>
                <th>ID</th>
                <th>Rental</th>
                <th>User</th>
                <th>Amount</th>
                <th>Status</th>
                <th>Date</th>
                <th>Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="p" items="${payments}">
                <tr>
                    <td>${p.paymentId}</td>
                    <td>${p.rentalId}</td>
                    <td>${p.userId}</td>
                    <td>${p.amount}</td>
                    <td>${p.status}</td>
                    <td><%=currentDate%></td>
                    <td>
                        <a href="editPayment.jsp?id=${p.paymentId}" class="btn-secondary">
                            <span class="material-symbols-outlined">Edit</span>
                        </a>
                        <a href="deletePayment?id=${p.paymentId}" class="btn-secondary delete" onclick="return confirm('Are you sure?')">
                            <span class="material-symbols-outlined">Delete</span>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
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