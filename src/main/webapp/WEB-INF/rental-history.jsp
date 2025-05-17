<%--
  Created by IntelliJ IDEA.
  User: Muditha
  Date: 5/13/2025
  Time: 10:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Rental" %>
<%@ page import="services.RentalDao" %>
<%@ page import="models.Movie" %>
<%@ page import="services.MovieDao" %>
<%@ page import="models.User" %>
<%@ page import="java.util.List" %>
<%
    // Check if user is logged in
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get user's rental history
    List<Rental> rentals = RentalDao.getUserRentals(user.getUserId());
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental History | Cineverse</title>
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link href="https://fonts.googleapis.com/icon?family=Material+Symbols+Outlined" rel="stylesheet">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body {
            background-color: #f8f9fa;
            min-height: 100vh;
        }

        .navbar {
            background: #1a1a1a;
            padding: 1rem 2rem;
            position: sticky;
            top: 0;
            z-index: 1000;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .navbar .logo {
            color: #fff;
            text-decoration: none;
            font-size: 1.5rem;
            font-weight: 600;
        }

        .navbar .logo span {
            color: #007bff;
        }

        .menu-links {
            list-style: none;
            display: flex;
            gap: 2rem;
        }

        .menu-links li a {
            color: #fff;
            text-decoration: none;
            font-size: 1rem;
            transition: color 0.3s ease;
        }

        .menu-links li a:hover {
            color: #007bff;
        }

        .rental-history {
            max-width: 1200px;
            margin: 2rem auto;
            padding: 0 1rem;
        }

        .rental-history h1 {
            color: #333;
            margin-bottom: 2rem;
            text-align: center;
        }

        .rental-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 2rem;
        }

        .rental-card {
            background: white;
            border-radius: 10px;
            padding: 1.5rem;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .rental-card h3 {
            color: #333;
            margin-bottom: 1rem;
        }

        .rental-info {
            color: #666;
            margin-bottom: 0.5rem;
        }

        .rental-status {
            display: inline-block;
            padding: 0.25rem 0.75rem;
            border-radius: 20px;
            font-size: 0.875rem;
            margin-top: 1rem;
        }

        .status-active {
            background: #e3f2fd;
            color: #0d47a1;
        }

        .status-expired {
            background: #ffebee;
            color: #c62828;
        }

        .no-rentals {
            text-align: center;
            padding: 3rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .no-rentals p {
            color: #666;
            margin-bottom: 1rem;
        }

        .browse-btn {
            display: inline-block;
            padding: 0.8rem 2rem;
            background: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .browse-btn:hover {
            background: #0056b3;
        }

        @media (max-width: 768px) {
            .rental-grid {
                grid-template-columns: 1fr;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <a href="home.jsp" class="logo">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <li><a href="home.jsp">Home</a></li>
            <li><a href="movies.jsp">Movies</a></li>
            <li><a href="reviews.jsp">Reviews</a></li>
            <li><a href="rental-history.jsp">Rental History</a></li>
            <li><a href="payment.jsp">Payment</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="contact.jsp">Contact Us</a></li>
            <li><a href="logout.jsp">Logout</a></li>
        </ul>
    </nav>

    <div class="rental-history">
        <h1>Your Rental History</h1>
        
        <% if (rentals != null && !rentals.isEmpty()) { %>
            <div class="rental-grid">
                <% for (Rental rental : rentals) {
                    Movie movie = MovieDao.getMovieById(rental.getMovieId());
                    if (movie != null) {
                %>
                    <div class="rental-card">
                        <h3><%= movie.getTitle() %></h3>
                        <p class="rental-info"><strong>Rental Date:</strong> <%= rental.getRentalDate() %></p>
                        <p class="rental-info"><strong>Duration:</strong> 7 days</p>
                        <p class="rental-info"><strong>Rental ID:</strong> <%= rental.getRentalId() %></p>
                        <span class="rental-status <%= rental.getRentalStatus().equalsIgnoreCase("rented") ? "status-active" : "status-expired" %>">
                            <%= rental.getRentalStatus().toUpperCase() %>
                        </span>
                    </div>
                <% 
                    }
                } %>
            </div>
        <% } else { %>
            <div class="no-rentals">
                <p>You haven't rented any movies yet.</p>
                <a href="movies.jsp" class="browse-btn">Browse Movies</a>
            </div>
        <% } %>
    </div>
</body>
</html>

