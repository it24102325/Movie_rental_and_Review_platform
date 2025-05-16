<%@ page import="dao.PaymentDao" %>
<%@ page import="models.Payment" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String id = request.getParameter("id");
    Payment payment = new PaymentDao().getPaymentById(id);
%>
<html>
<head>
    <title>Edit Payment</title>
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
        <h2>Edit Payment Status</h2>
        <form method="post" action="updatePayment" class="payment-form">
            <input type="hidden" name="paymentId" value="<%= payment.getPaymentId() %>">

            <div class="form-group">
                <label>Status</label>
                <select name="status">
                    <option value="Pending" <%= "Pending".equals(payment.getStatus()) ? "selected" : "" %>>Pending</option>
                    <option value="Completed" <%= "Completed".equals(payment.getStatus()) ? "selected" : "" %>>Completed</option>
                    <option value="Failed" <%= "Failed".equals(payment.getStatus()) ? "selected" : "" %>>Failed</option>
                </select>
            </div>

            <button type="submit" class="btn-primary">Update</button>
        </form>
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