<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Movie List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .movie-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(250px, 1fr));
            gap: 20px;
            margin-top: 20px;
        }
        .movie-card {
            border: 1px solid #ddd;
            border-radius: 8px;
            overflow: hidden;
            transition: transform 0.2s;
            background: white;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
            display: flex;
            flex-direction: column;
        }
        .movie-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.2);
        }
        .movie-poster {
            width: 100%;
            height: 300px;
            object-fit: cover;
        }
        .movie-info {
            padding: 15px;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }
        .movie-title {
            font-size: 1.2em;
            font-weight: bold;
            margin-bottom: 8px;
            color: #333;
        }
        .movie-genre {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 8px;
        }
        .movie-price {
            color: #28a745;
            font-weight: bold;
            font-size: 1.1em;
            margin-bottom: 8px;
        }
        .movie-description {
            color: #666;
            font-size: 0.9em;
            margin-bottom: 15px;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
            overflow: hidden;
            flex-grow: 1;
        }
        .movie-actions {
            margin-top: auto;
            padding-top: 10px;
        }
        .btn-view-details {
            width: 100%;
            background-color: #007bff;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .btn-view-details:hover {
            background-color: #0056b3;
        }
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
        .page-title {
            margin-bottom: 30px;
            color: #333;
        }
        .debug-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 4px;
            margin-bottom: 20px;
        }
        .sort-buttons {
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <h2 class="page-title">Available Movies</h2>
        
        <!-- Sort Buttons -->
        <div class="sort-buttons mb-4">
            <div class="btn-group" role="group">
                <a href="movie-list" class="btn btn-outline-primary">Default Order</a>
                <a href="movie-list?sort=rating_desc" class="btn btn-outline-primary">Highest Rated</a>
                <a href="movie-list?sort=rating_asc" class="btn btn-outline-primary">Lowest Rated</a>
            </div>
        </div>
        
        <!-- Debug Information -->
        <div class="debug-info">
            <h4>Debug Information:</h4>
            <p>Number of movies: ${movies.size()}</p>
            <p>Movies list is empty: ${empty movies}</p>
            <c:if test="${not empty error}">
                <p>Error message: ${error}</p>
            </c:if>
        </div>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <c:if test="${empty movies}">
            <div class="alert alert-warning">
                No movies found. This could be because:
                <ul>
                    <li>No movies have been added yet</li>
                    <li>There was an error loading the movies</li>
                    <li>The movies file is empty or not accessible</li>
                </ul>
            </div>
        </c:if>

        <div class="movie-grid">
            <c:forEach var="movie" items="${movies}">
                <div class="movie-card">
                    <c:choose>
                        <c:when test="${not empty movie.imageFileName}">
                            <img src="uploads/${movie.imageFileName}" alt="${movie.title}" class="movie-poster">
                        </c:when>
                        <c:otherwise>
                            <img src="images/default-movie.jpg" alt="Default Movie Poster" class="movie-poster">
                        </c:otherwise>
                    </c:choose>
                    <div class="movie-info">
                        <div class="movie-title">${movie.title}</div>
                        <div class="movie-genre">${movie.genre}</div>
                        <div class="movie-price">$${movie.price}</div>
                        <div class="movie-description">${movie.description}</div>
                        <div class="movie-actions">
                            <a href="movie-details?id=${movie.id}" class="btn btn-view-details">View Details</a>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
