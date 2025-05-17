<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Rental" %>
<%@ page import="models.User" %>
<%@ page import="services.RentalDao" %>
<%@ page import="services.MovieDao" %>
<%@ page import="java.util.List" %>
<%
    // Check if user is logged in
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get user's rentals
    List<Rental> userRentals = RentalDao.getUserRentals(currentUser.getUserId());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Rentals - Cineverse</title>
    <link rel="stylesheet" href="assets/styles/home.css">
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="#">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <span id="close-menu-btn" class="material-symbols-outlined">close</span>
            <li><a href="home.jsp">Home</a></li>
            <li><a href="reviews.jsp">Reviews</a></li>
            <li><a href="rentals.jsp" class="active">Rentals</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="contact.jsp">Contact Us</a></li>
            <li><a href="logout.jsp">Logout</a></li>
        </ul>
        <span id="hamburger-btn" class="material-symbols-outlined">menu</span>
    </nav>
</header>

<section class="rentals-section">
    <h2>Your Rental History</h2>
    <div class="rentals-container">
        <% if (userRentals != null && !userRentals.isEmpty()) { %>
            <% for (Rental rental : userRentals) { %>
                <div class="rental-card">
                    <h3><%= MovieDao.getMovieTitle(rental.getMovieId()) %></h3>
                    <p>Rented on: <%= rental.getRentalDate() %></p>
                    <p>Status: <%= rental.getRentalStatus() %></p>
                    <% if ("rented".equalsIgnoreCase(rental.getRentalStatus())) { %>
                        <form action="return-movie" method="POST">
                            <input type="hidden" name="rentalId" value="<%= rental.getRentalId() %>">
                            <button type="submit" class="return-btn">Return Movie</button>
                        </form>
                    <% } %>
                </div>
            <% } %>
        <% } else { %>
            <div class="no-rentals">
                <p>You haven't rented any movies yet.</p>
                <a href="home.jsp" class="browse-btn">Browse Movies</a>
            </div>
        <% } %>
    </div>
</section>

<style>
    .rentals-section {
        padding: 2rem;
        max-width: 1200px;
        margin: 0 auto;
    }

    .rentals-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
        gap: 2rem;
        margin-top: 2rem;
    }

    .rental-card {
        background: white;
        border-radius: 8px;
        padding: 1.5rem;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .rental-card h3 {
        margin: 0 0 1rem 0;
        color: #333;
    }

    .rental-card p {
        margin: 0.5rem 0;
        color: #666;
    }

    .return-btn {
        background: #007bff;
        color: white;
        border: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 1rem;
    }

    .return-btn:hover {
        background: #0056b3;
    }

    .no-rentals {
        text-align: center;
        padding: 3rem;
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
    }

    .browse-btn {
        display: inline-block;
        background: #007bff;
        color: white;
        text-decoration: none;
        padding: 0.5rem 1rem;
        border-radius: 4px;
        margin-top: 1rem;
    }

    .browse-btn:hover {
        background: #0056b3;
    }
</style>

<script>
    const header = document.querySelector("header");
    const hamburgerBtn = document.querySelector("#hamburger-btn");
    const closeMenuBtn = document.querySelector("#close-menu-btn");

    hamburgerBtn.addEventListener("click", () => header.classList.toggle("show-mobile-menu"));
    closeMenuBtn.addEventListener("click", () => header.classList.remove("show-mobile-menu"));
</script>
</body>
</html>
