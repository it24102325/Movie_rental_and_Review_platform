<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Reviews</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .page-title {
            font-size: 2em;
            color: #333;
            margin-bottom: 30px;
            padding-bottom: 10px;
            border-bottom: 2px solid #007bff;
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
        .review-info {
            flex: 1;
        }
        .review-movie {
            font-weight: bold;
            color: #333;
            margin-bottom: 5px;
        }
        .review-user {
            color: #666;
            font-size: 0.9em;
        }
        .review-rating {
            color: #ffd700;
            font-size: 1.2em;
            margin-right: 20px;
        }
        .review-content {
            color: #333;
            line-height: 1.6;
            margin-bottom: 15px;
        }
        .review-actions {
            display: flex;
            gap: 10px;
        }
        .btn-delete {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
        }
        .btn-delete:hover {
            background-color: #c82333;
        }
        .message {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .message.success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .message.error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
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
        <h1 class="page-title">Manage Reviews</h1>
        
        <c:if test="${not empty message}">
            <div class="message success">${message}</div>
        </c:if>
        
        <c:if test="${not empty error}">
            <div class="message error">${error}</div>
        </c:if>
        
        <c:if test="${empty reviews}">
            <p class="text-center">No reviews found.</p>
        </c:if>
        
        <c:forEach items="${reviews}" var="review">
            <div class="review-card">
                <div class="review-header">
                    <div class="review-info">
                        <div class="review-movie">Movie ID: ${review.movieId}</div>
                        <div class="review-user">By ${review.userName != null ? review.userName : review.userId}</div>
                    </div>
                    <div class="review-rating">
                        <c:forEach begin="1" end="5" var="i">
                            <span class="star ${i <= review.rating ? 'filled' : ''}">★</span>
                        </c:forEach>
                    </div>
                </div>
                <div class="review-content">${review.feedback}</div>
                <div class="review-actions">
                    <form action="manage-reviews" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="reviewId" value="${review.reviewId}">
                        <button type="submit" class="btn-delete" onclick="return confirm('Are you sure you want to delete this review?')">
                            Delete Review
                        </button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
