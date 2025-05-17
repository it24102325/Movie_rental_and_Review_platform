<%--
  Created by IntelliJ IDEA.
  User: Muditha
  Date: 5/14/2025
  Time: 11:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page import="models.Movie" %>
<%@ page import="services.MovieDao" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    
    // Get the movie ID from the request parameter
    String movieId = request.getParameter("id");
    Movie movie = null;
    
    if (movieId != null) {
        movie = MovieDao.getMovieById(movieId);
    }
    
    if (movie == null) {
        response.sendRedirect("movies.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rent Movie - <%= movie.getTitle() %> | Cineverse</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
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

        .rental-container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 2rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

        .movie-details {
            display: flex;
            gap: 2rem;
            margin-bottom: 2rem;
            padding-bottom: 2rem;
            border-bottom: 1px solid #eee;
        }

        .movie-poster {
            width: 300px;
            height: 450px;
            object-fit: cover;
            border-radius: 8px;
        }

        .movie-info {
            flex: 1;
        }

        .movie-info h1 {
            color: #333;
            margin-bottom: 1rem;
        }

        .movie-info p {
            color: #666;
            margin-bottom: 0.5rem;
            font-size: 1.1rem;
        }

        .rental-details {
            background: #f8f9fa;
            padding: 1.5rem;
            border-radius: 8px;
            margin-top: 2rem;
        }

        .rental-details h2 {
            color: #333;
            margin-bottom: 1rem;
        }

        .price-details {
            display: flex;
            justify-content: space-between;
            margin-bottom: 1rem;
            padding-bottom: 1rem;
            border-bottom: 1px solid #ddd;
        }

        .total-price {
            font-size: 1.5rem;
            font-weight: 600;
            color: #007bff;
        }

        .action-buttons {
            display: flex;
            gap: 1rem;
            margin-top: 2rem;
        }

        .btn {
            padding: 0.8rem 2rem;
            border: none;
            border-radius: 5px;
            font-size: 1rem;
            cursor: pointer;
            text-decoration: none;
            display: inline-flex;
            align-items: center;
            gap: 0.5rem;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background: #007bff;
            color: white;
        }

        .btn-secondary {
            background: #6c757d;
            color: white;
        }

        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }

        @media (max-width: 768px) {
            .movie-details {
                flex-direction: column;
            }

            .movie-poster {
                width: 100%;
                height: auto;
                max-width: 300px;
                margin: 0 auto;
            }

            .action-buttons {
                flex-direction: column;
            }

            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <nav class="navbar">
        <a href="home.jsp" class="logo">Cineverse<span>.</span></a>
    </nav>

    <div class="rental-container">
        <div class="movie-details">
            <img src="assets/images/movies_img/default.jpg" 
                 alt="<%= movie.getTitle() %>" 
                 class="movie-poster"
                 onerror="this.src='assets/images/movies_img/default.jpg'">
            
            <div class="movie-info">
                <h1><%= movie.getTitle() %></h1>
                <p><strong>Genre:</strong> <%= movie.getGenre() %></p>
                <p><strong>Duration:</strong> <%= movie.getDuration() %> minutes</p>
                <p><strong>Rating:</strong> <%= movie.getRating() %>/10</p>
                <p><strong>Director:</strong> <%= movie.getDirector() %></p>
                <p><strong>Cast:</strong> <%= movie.getCast() %></p>
                
                <div class="rental-details">
                    <h2>Rental Details</h2>
                    <div class="price-details">
                        <span>Rental Period:</span>
                        <span>7 days</span>
                    </div>
                    <div class="price-details">
                        <span>Rental Price:</span>
                        <span class="total-price">$4.99</span>
                    </div>
                </div>
            </div>
        </div>

        <div class="action-buttons">
            <form action="process-rental" method="POST" style="flex: 1;">
                <input type="hidden" name="movieId" value="<%= movie.getMovieId() %>">
                <button type="submit" class="btn btn-primary" style="width: 100%;">
                    <i class="fas fa-shopping-cart"></i>
                    Confirm Rental
                </button>
            </form>
            <a href="movies.jsp" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i>
                Back to Movies
            </a>
        </div>
    </div>
</body>
</html>
