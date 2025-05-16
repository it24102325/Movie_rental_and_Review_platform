<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Checkout</title>
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
        <h2>Make a Payment</h2>
        <form action="addPayment" method="post" class="payment-form">
            <div class="form-group">
                <label>Rental ID</label>
                <input type="text" name="rentalId" required>
            </div>

            <div class="form-group">
                <label>User ID</label>
                <input type="text" name="userId" required>
            </div>
            <div class="form-group">
                <label>Payment Method:</label>
                <select name="payment_method" required>
                    <option value="Credit Card">Credit Card</option>
                    <option value="Debit Card">Debit Card</option>
                    <option value="PayPal">PayPal</option>
                </select>
            </div>


            <div class="form-group">
                <label>Amount</label>
                <input type="text" name="amount" required>
            </div>

            <button type="submit" class="btn-primary">Submit Payment</button>
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