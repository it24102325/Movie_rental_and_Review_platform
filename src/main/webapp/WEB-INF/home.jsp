<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Movie" %>
<%@ page import="services.MovieDao" %>
<%@ page import="services.ReviewDao" %>
<%@ page import="models.Review" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.stream.Collectors" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    
    // Get movies list once at the beginning
    List<Movie> movies = MovieDao.getAllMovies();
    System.out.println("Number of movies retrieved: " + movies.size());
    
    // Get all reviews
    List<Review> allReviews = ReviewDao.getAllReviews();
    Map<String, List<Review>> reviewsByMovie = allReviews.stream()
        .collect(Collectors.groupingBy(Review::getMovieId));
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cineverse - Home</title>
    <link rel="stylesheet" href="assets/styles/home.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .movies-section {
            padding: 2rem;
            background: #f8f9fa;
            min-height: 100vh;
        }

        .movies-section h2 {
            text-align: center;
            margin-bottom: 2rem;
            color: #333;
        }

        .movies-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 1.5rem;
            padding: 1rem;
            max-width: 1200px;
            margin: 0 auto;
        }
        
        .movie-card {
            background: white;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s ease;
            height: 100%;
            display: flex;
            flex-direction: column;
            position: relative;
            max-width: 300px;
            margin: 0 auto;
        }
        
        .movie-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        
        .movie-card img {
            width: 100%;
            height: 300px;
            object-fit: cover;
            display: block;
            background-color: #f0f0f0;
            transition: opacity 0.3s ease;
        }

        .movie-card img.loading {
            opacity: 0.6;
        }

        .movie-card img.loaded {
            opacity: 1;
        }
        
        .movie-info {
            padding: 1rem;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .movie-info h3 {
            margin: 0 0 0.5rem 0;
            font-size: 1.1rem;
            color: #333;
            line-height: 1.3;
        }
        
        .movie-details {
            display: flex;
            gap: 0.5rem;
            margin: 0.5rem 0;
            flex-wrap: wrap;
        }
        
        .badge {
            padding: 0.4rem 0.6rem;
            border-radius: 4px;
            font-size: 0.8rem;
            display: inline-flex;
            align-items: center;
            gap: 0.3rem;
        }

        .bg-primary {
            background: #007bff;
            color: white;
        }

        .bg-warning {
            background: #ffc107;
            color: #000;
        }

        .bg-info {
            background: #17a2b8;
            color: white;
        }
        
        .movie-description {
            margin: 0.5rem 0;
            font-size: 0.85rem;
            color: #666;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
            overflow: hidden;
            flex-grow: 1;
        }

        .movie-info p {
            margin: 0.3rem 0;
            font-size: 0.85rem;
        }

        .movie-info p strong {
            color: #333;
        }
        
        .btn-container {
            display: flex;
            justify-content: space-between;
            gap: 0.5rem;
            margin-top: 0.8rem;
        }
        
        .btn {
            padding: 0.4rem 0.8rem;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
            display: inline-flex;
            align-items: center;
            gap: 0.3rem;
            flex: 1;
            justify-content: center;
            font-size: 0.85rem;
        }
        
        .btn-primary {
            background: #007bff;
            color: white;
        }
        
        .btn-success {
            background: #28a745;
            color: white;
        }
        
        .btn:hover {
            opacity: 0.9;
            transform: translateY(-2px);
        }

        @media (max-width: 768px) {
            .movies-container {
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                gap: 1rem;
                padding: 0.5rem;
            }

            .movie-info {
                padding: 0.8rem;
            }

            .btn-container {
                flex-direction: column;
            }

            .movie-card img {
                height: 250px;
            }
        }

        @media (min-width: 1200px) {
            .movies-container {
                grid-template-columns: repeat(4, 1fr);
            }
        }
    </style>
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="home.jsp">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <li><a href="home.jsp">Home</a></li>
            <li><a href="movies.jsp">Movies</a></li>
            <li><a href="reviews.jsp">Reviews</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="contact.jsp">Contact Us</a></li>
            <li><a href="logout.jsp">Logout</a></li>
            <li><a href="payment.jsp">Payment</a></li>
        </ul>
        <span id="hamburger-btn" class="material-icons">menu</span>
    </nav>
</header>

<section id="home" class="hero-section">
    <div class="content">
        <h2>Discover & Review Your Favorite Movies</h2>
        <form action="search.jsp" method="GET">
            <input type="text" name="query" placeholder="Search Movies">
            <button class="search-btn" type="submit">Search</button>
        </form>
    </div>
</section>

<section id="movies" class="movies-section">
    <h2>Browse Our Movie Collection</h2>
    <div class="movies-container">
        <% 
        System.out.println("Starting to display movies");
        for (Movie movie : movies) { 
            System.out.println("Processing movie: " + movie.getTitle());
            // Calculate image number (1-10) with better distribution
            int imgNum = Math.abs(movie.getMovieId().hashCode() % 10) + 1;
            // Format the image number with leading zero
            String formattedNum = String.format("%02d", imgNum);
            // Determine image extension based on number
            String imgExt;
            if (imgNum == 1 || imgNum == 8) {
                imgExt = "webp";
            } else if (imgNum == 2) {
                imgExt = "avif";
            } else {
                imgExt = "jpg";
            }
            System.out.println("Image path: assets/images/movies_img/img" + formattedNum + "." + imgExt);
        %>
            <div class="movie-card">
                <img src="assets/images/movies_img/img<%= formattedNum %>.<%= imgExt %>" 
                     alt="<%= movie.getTitle() %>"
                     loading="lazy"
                     class="loading"
                     onload="this.classList.remove('loading'); this.classList.add('loaded')"
                     onerror="this.src='assets/images/movies_img/default.jpg'; this.classList.remove('loading'); this.classList.add('loaded')">
                <div class="movie-info">
                    <h3><%= movie.getTitle() %></h3>
                    <div class="movie-details">
                        <span class="badge bg-primary"><%= movie.getGenre() %></span>
                        <span class="badge bg-warning">
                            <i class="fas fa-star"></i> <%= movie.getRating() %>/10
                        </span>
                        <span class="badge bg-info"><%= movie.getDuration() %> min</span>
                    </div>
                    <p class="movie-description"><%= movie.getDescription() %></p>
                    <div class="movie-info">
                        <p><strong>Director:</strong> <%= movie.getDirector() %></p>
                        <p><strong>Cast:</strong> <%= movie.getCast() %></p>
                    </div>
                    <div class="btn-container">
                        <a href="movie-detail.jsp?id=<%= movie.getMovieId() %>" class="btn btn-primary">
                            <i class="fas fa-info-circle"></i>View Details
                        </a>
                        <a href="rent-movie?id=<%= movie.getMovieId() %>" class="btn btn-success">
                            <i class="fas fa-shopping-cart"></i>Rent Now
                        </a>
                    </div>
                </div>
            </div>
        <% } %>
    </div>
</section>

<script>
    const header = document.querySelector("header");
    const hamburgerBtn = document.querySelector("#hamburger-btn");

    hamburgerBtn.addEventListener("click", () => header.classList.toggle("show-mobile-menu"));

    // Enhanced image loading handling
    document.addEventListener('DOMContentLoaded', function() {
        const images = document.querySelectorAll('.movie-card img');
        images.forEach(img => {
            if (img.complete) {
                img.classList.remove('loading');
                img.classList.add('loaded');
            }
            
            // Add error handling for images that fail to load
            img.addEventListener('error', function() {
                this.src = 'assets/images/movies_img/default.jpg';
                this.classList.remove('loading');
                this.classList.add('loaded');
            });
        });
    });
</script>
</body>
</html>
