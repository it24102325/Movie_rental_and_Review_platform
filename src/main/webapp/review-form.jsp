<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Write a Review</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .review-form-container {
            background: white;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-top: 20px;
        }
        .movie-info {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 1px solid #eee;
        }
        .movie-title {
            font-size: 1.8em;
            font-weight: bold;
            color: #333;
            margin-bottom: 10px;
        }
        .movie-genre {
            color: #666;
            font-size: 1.1em;
            margin-bottom: 15px;
        }
        .rating-input {
            display: flex;
            flex-direction: row-reverse;
            gap: 5px;
            margin-bottom: 20px;
        }
        .rating-input input {
            display: none;
        }
        .rating-input label {
            color: #ddd;
            font-size: 30px;
            cursor: pointer;
            transition: color 0.2s;
        }
        .rating-input input:checked ~ label,
        .rating-input label:hover,
        .rating-input label:hover ~ label {
            color: #ffd700;
        }
        .btn-submit {
            background-color: #28a745;
            color: white;
            padding: 12px 30px;
            font-size: 1.1em;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .btn-submit:hover {
            background-color: #218838;
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
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <a href="movie-details?id=${movie.id}" class="back-link">← Back to Movie Details</a>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <div class="review-form-container">
            <div class="movie-info">
                <h1 class="movie-title">${movie.title}</h1>
                <div class="movie-genre">${movie.genre}</div>
            </div>

            <form action="add-review" method="post">
                <input type="hidden" name="movieId" value="${movie.id}">
                
                <div class="form-group">
                    <label>Rating:</label>
                    <div class="rating-input">
                        <c:forEach begin="1" end="5" var="i">
                            <input type="radio" name="rating" value="${i}" id="rating${i}" required>
                            <label for="rating${i}">★</label>
                        </c:forEach>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="feedback">Your Review:</label>
                    <textarea id="feedback" name="feedback" rows="6" class="form-control" required 
                              placeholder="Share your thoughts about the movie..."></textarea>
                </div>
                
                <button type="submit" class="btn btn-submit">Submit Review</button>
            </form>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 