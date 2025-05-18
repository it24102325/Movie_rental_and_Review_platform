<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ page import="java.net.URLEncoder" %>
<%@ page import="java.nio.charset.StandardCharsets" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Write a Review</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css">
    <style>
        :root {
            --primary-color: #2196f3;
            --secondary-color: #1976d2;
            --background-color: #f5f7fa;
            --text-color: #2c3e50;
            --border-radius: 12px;
        }

        body {
            background-color: var(--background-color);
            color: var(--text-color);
            font-family: 'Segoe UI', system-ui, -apple-system, sans-serif;
        }

        .container {
            max-width: 1000px;
            margin: 2rem auto;
            padding: 20px;
        }

        .review-form-container {
            background: white;
            padding: 2.5rem;
            border-radius: var(--border-radius);
            box-shadow: 0 8px 30px rgba(0,0,0,0.08);
            margin-top: 20px;
            transition: transform 0.3s ease;
        }

        .review-form-container:hover {
            transform: translateY(-5px);
        }

        .review-grid {
            display: grid;
            grid-template-columns: 1fr 2fr;
            gap: 2rem;
            align-items: start;
        }

        .movie-poster {
            width: 100%;
            border-radius: var(--border-radius);
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            margin-bottom: 1rem;
        }

        .movie-info {
            margin-bottom: 2rem;
            padding-bottom: 1.5rem;
            border-bottom: 2px solid #eef2f7;
        }

        .movie-title {
            font-size: 2.2em;
            font-weight: 700;
            color: var(--text-color);
            margin-bottom: 0.5rem;
            line-height: 1.2;
        }

        .movie-genre {
            color: #64748b;
            font-size: 1.1em;
            margin-bottom: 1rem;
            font-weight: 500;
        }

        .rating-container {
            margin-bottom: 2rem;
        }

        .rating-label {
            font-size: 1.1em;
            font-weight: 600;
            margin-bottom: 0.5rem;
            color: var(--text-color);
        }

        .rating-input {
            display: flex;
            flex-direction: row-reverse;
            gap: 0.5rem;
            margin-bottom: 1rem;
        }

        .rating-input input {
            display: none;
        }

        .rating-input label {
            color: #ddd;
            font-size: 2.5rem;
            cursor: pointer;
            transition: all 0.2s ease;
        }

        .rating-input input:checked ~ label,
        .rating-input label:hover,
        .rating-input label:hover ~ label {
            color: #ffd700;
            transform: scale(1.1);
            text-shadow: 0 0 10px rgba(255, 215, 0, 0.5);
        }

        .form-group label {
            font-weight: 600;
            color: var(--text-color);
            margin-bottom: 0.5rem;
        }

        textarea.form-control {
            border: 2px solid #eef2f7;
            border-radius: 12px;
            padding: 1rem;
            font-size: 1rem;
            transition: all 0.3s ease;
            resize: vertical;
            min-height: 150px;
        }

        textarea.form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 3px rgba(33, 150, 243, 0.1);
        }

        .char-counter {
            color: #64748b;
            font-size: 0.9em;
            text-align: right;
            margin-top: 0.5rem;
        }

        .btn-submit {
            background-color: var(--primary-color);
            color: white;
            padding: 1rem 2rem;
            font-size: 1.1em;
            font-weight: 600;
            border: none;
            border-radius: var(--border-radius);
            cursor: pointer;
            transition: all 0.3s ease;
            width: 100%;
            margin-top: 1rem;
        }

        .btn-submit:hover {
            background-color: var(--secondary-color);
            transform: translateY(-2px);
            box-shadow: 0 4px 15px rgba(33, 150, 243, 0.3);
        }

        .back-link {
            display: inline-flex;
            align-items: center;
            margin-bottom: 1.5rem;
            color: var(--primary-color);
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .back-link:hover {
            color: var(--secondary-color);
            transform: translateX(-5px);
        }

        .back-link svg {
            margin-right: 0.5rem;
        }

        .error-message {
            color: #dc3545;
            margin-top: 1rem;
            padding: 1rem;
            background-color: #fff5f5;
            border-radius: var(--border-radius);
            border-left: 4px solid #dc3545;
            animation: shake 0.5s ease-in-out;
        }

        @keyframes shake {
            0%, 100% { transform: translateX(0); }
            25% { transform: translateX(-10px); }
            75% { transform: translateX(10px); }
        }

        @media (max-width: 768px) {
            .review-grid {
                grid-template-columns: 1fr;
            }

            .movie-poster {
                max-width: 300px;
                margin: 0 auto 1.5rem;
            }

            .container {
                padding: 10px;
            }

            .review-form-container {
                padding: 1.5rem;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <c:choose>
            <c:when test="${empty movie}">
                <div class="error-message animate__animated animate__shakeX">
                    Movie information is not available at the moment. Please try again later.
                </div>
                <a href="${pageContext.request.contextPath}/movie-list" class="back-link">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M19 12H5M5 12L12 19M5 12L12 5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    Back to Movie List
                </a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/movie-details?id=${fn:escapeXml(movie.id)}" class="back-link">
                    <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                        <path d="M19 12H5M5 12L12 19M5 12L12 5" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"/>
                    </svg>
                    Back to Movie Details
                </a>

                <c:if test="${not empty requestScope.error}">
                    <div class="error-message animate__animated animate__shakeX">
                        ${fn:escapeXml(requestScope.error)}
                    </div>
                </c:if>

                <div class="review-form-container animate__animated animate__fadeIn">
                    <div class="review-grid">
                        <div class="movie-poster-container">
                            <div class="movie-info">
                                <h1 class="movie-title">${fn:escapeXml(movie.title)}</h1>
                                <div class="movie-genre">${fn:escapeXml(movie.genre)}</div>
                            </div>
                            
                            <c:if test="${not empty movie.posterUrl}">
                                <c:set var="posterPath" value="${pageContext.request.contextPath}/images/${fn:escapeXml(movie.posterUrl)}" />
                                <img src="${posterPath}" 
                                     alt="Movie poster for ${fn:escapeXml(movie.title)}" 
                                     class="movie-poster"
                                     onerror="this.onerror=null; this.src='${pageContext.request.contextPath}/images/default-movie-poster.jpg';">
                            </c:if>
                        </div>

                        <form action="${pageContext.request.contextPath}/add-review" method="post" id="reviewForm" onsubmit="return validateForm()">
                            <input type="hidden" name="movieId" value="${fn:escapeXml(movie.id)}">
                            
                            <div class="rating-container">
                                <label class="rating-label">Your Rating</label>
                                <div class="rating-input">
                                    <c:forEach begin="1" end="5" var="i">
                                        <input type="radio" name="rating" value="${i}" id="rating${i}" required>
                                        <label for="rating${i}" title="${i} stars">★</label>
                                    </c:forEach>
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <label for="feedback">Your Review</label>
                                <textarea id="feedback" name="feedback" class="form-control" required 
                                          placeholder="What did you think about the movie? Share your thoughts here..."
                                          maxlength="1000" minlength="10"></textarea>
                                <div class="char-counter">
                                    <span id="charCount">0</span>/1000 characters
                                </div>
                            </div>
                            
                            <button type="submit" class="btn btn-submit">
                                Submit Review
                            </button>
                        </form>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        document.getElementById('feedback').addEventListener('input', function() {
            const charCount = this.value.length;
            document.getElementById('charCount').textContent = charCount;
            
            // Visual feedback as user approaches the limit
            const counter = document.querySelector('.char-counter');
            if (charCount > 900) {
                counter.style.color = '#dc3545';
            } else if (charCount > 700) {
                counter.style.color = '#ffc107';
            } else {
                counter.style.color = '#64748b';
            }
        });

        // Form validation
        function validateForm() {
            const feedback = document.getElementById('feedback').value.trim();
            const rating = document.querySelector('input[name="rating"]:checked');
            
            if (!rating) {
                alert('Please select a rating for the movie.');
                return false;
            }
            
            if (feedback.length < 10) {
                alert('Please write a review with at least 10 characters.');
                return false;
            }
            
            return true;
        }

        // Smooth scroll to form when there's an error
        if (document.querySelector('.error-message')) {
            document.querySelector('.review-form-container').scrollIntoView({ 
                behavior: 'smooth',
                block: 'start'
            });
        }
    </script>
</body>
</html> 