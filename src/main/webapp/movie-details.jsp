<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${movie.title} - Movie Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .movie-details {
            display: flex;
            gap: 30px;
            margin-top: 20px;
        }
        .movie-poster {
            flex: 0 0 300px;
            height: 450px;
            object-fit: cover;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }
        .movie-info {
            flex: 1;
        }
        .movie-title {
            font-size: 2em;
            font-weight: bold;
            margin-bottom: 10px;
            color: #333;
        }
        .movie-genre {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 15px;
        }
        .movie-price {
            color: #28a745;
            font-weight: bold;
            font-size: 1.5em;
            margin-bottom: 20px;
        }
        .movie-description {
            color: #444;
            font-size: 1.1em;
            line-height: 1.6;
            margin-bottom: 30px;
        }
        .btn-rent {
            background-color: #28a745;
            color: white;
            padding: 12px 30px;
            font-size: 1.1em;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .btn-rent:hover {
            background-color: #218838;
        }
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .reviews-section {
            margin-top: 40px;
            padding-top: 20px;
            border-top: 1px solid #eee;
        }
        .section-title {
            font-size: 1.8em;
            color: #333;
            margin-bottom: 20px;
            padding-bottom: 10px;
            border-bottom: 2px solid #007bff;
        }
        .no-reviews {
            color: #666;
            font-style: italic;
            text-align: center;
            padding: 20px;
        }
        .review-card {
            background: white;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .review-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        .review-rating {
            color: #ffd700;
            font-size: 1.2em;
        }
        .review-user {
            color: #666;
            font-size: 0.9em;
        }
        .review-content {
            color: #333;
            line-height: 1.6;
        }
        .star {
            color: #ddd;
        }
        .star.filled {
            color: #ffd700;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <a href="movie-list" class="back-link">← Back to Movie List</a>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <c:if test="${not empty movie}">
            <div class="movie-details">
                <c:choose>
                    <c:when test="${not empty movie.imageFileName}">
                        <img src="uploads/${movie.imageFileName}" alt="${movie.title}" class="movie-poster">
                    </c:when>
                    <c:otherwise>
                        <img src="images/default-movie.jpg" alt="Default Movie Poster" class="movie-poster">
                    </c:otherwise>
                </c:choose>
                
                <div class="movie-info">
                    <h1 class="movie-title">${movie.title}</h1>
                    <div class="movie-genre">${movie.genre}</div>
                    <div class="movie-price">$${movie.price}</div>
                    <div class="movie-description">${movie.description}</div>
                    
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="login.jsp" class="btn btn-rent">Login to Rent</a>
                        </c:when>
                        <c:otherwise>
                            <div class="button-group">
                                <form action="rent-movie" method="post" style="display: inline-block; margin-right: 10px;">
                                    <input type="hidden" name="movieId" value="${movie.id}">
                                    <button type="submit" class="btn btn-rent">Rent Movie</button>
                                </form>
                                <a href="review-form?movieId=${movie.id}" class="btn btn-review">Write a Review</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </c:if>

        <!-- Reviews Section -->
        <div class="reviews-section">
            <h2 class="section-title">Reviews</h2>
            
            <c:if test="${empty reviews}">
                <p class="no-reviews">No reviews yet. Be the first to review this movie!</p>
            </c:if>
            
            <c:forEach items="${reviews}" var="review">
                <div class="review-card">
                    <div class="review-header">
                        <div class="review-rating">
                            <c:forEach begin="1" end="5" var="i">
                                <span class="star ${i <= review.rating ? 'filled' : ''}">★</span>
                            </c:forEach>
                        </div>
                        <div class="review-user">By ${review.userId}</div>
                    </div>
                    <div class="review-content">${review.feedback}</div>
                </div>
            </c:forEach>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
