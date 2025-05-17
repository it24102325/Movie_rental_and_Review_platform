<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Review" %>
<%@ page import="models.User" %>
<%@ page import="models.Movie" %>
<%@ page import="services.ReviewDao" %>
<%@ page import="services.MovieDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Arrays" %>
<%
    // Check if user is logged in
    User currentUser = (User) session.getAttribute("user");
    if (currentUser == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    // Get all reviews
    List<Review> reviews = ReviewDao.getAllReviews();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Reviews - Cineverse</title>
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="assets/styles/shared.css">
    <style>
        /* Add any reviews-specific styles here */
    </style>
</head>
<body>
<header class="site-header">
    <div class="header-container">
        <a class="logo" href="home.jsp">
            Cineverse<span class="logo-dot">.</span>
        </a>
        <nav class="main-nav">
            <ul class="nav-links">
                <li><a href="home.jsp" class="nav-link">
                    <span class="material-icons">home</span>
                    <span>Home</span>
                </a></li>
                <li><a href="movies.jsp" class="nav-link">
                    <span class="material-icons">movie</span>
                    <span>Movies</span>
                </a></li>
                <li><a href="reviews.jsp" class="nav-link active">
                    <span class="material-icons">star_rate</span>
                    <span>Reviews</span>
                </a></li>
                <li><a href="profile.jsp" class="nav-link">
                    <span class="material-icons">person</span>
                    <span>Profile</span>
                </a></li>
                <li><a href="contact.jsp" class="nav-link">
                    <span class="material-icons">mail</span>
                    <span>Contact</span>
                </a></li>
                <li><a href="logout.jsp" class="nav-link">
                    <span class="material-icons">logout</span>
                    <span>Logout</span>
                </a></li>
            </ul>
        </nav>
        <button class="mobile-menu-toggle" id="hamburger-btn">
            <span class="material-icons">menu</span>
        </button>
    </div>
</header>

<main class="reviews-section">
    <div class="page-header">
        <h1>Movie Reviews</h1>
        <p>Share your thoughts about the movies you've watched</p>
    </div>

    <div class="submit-review">
        <h2>Write a Review</h2>
        <% if (request.getAttribute("error") != null) { %>
            <div class="error-message">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>
        <form action="submit-review" method="POST">
            <div class="form-group">
                <label for="movieId">Select Movie</label>
                <select name="movieId" id="movieId" required>
                    <option value="">Choose a movie...</option>
                    <% for (Movie movie : MovieDao.getAllMovies()) { %>
                        <option value="<%= movie.getMovieId() %>"><%= movie.getTitle() %></option>
                    <% } %>
                </select>
            </div>
            <div class="form-group rating-group">
                <label>Rating</label>
                <div class="star-rating">
                    <input type="radio" id="star5" name="rating" value="5" required>
                    <label for="star5" title="5 stars">★</label>
                    <input type="radio" id="star4" name="rating" value="4">
                    <label for="star4" title="4 stars">★</label>
                    <input type="radio" id="star3" name="rating" value="3">
                    <label for="star3" title="3 stars">★</label>
                    <input type="radio" id="star2" name="rating" value="2">
                    <label for="star2" title="2 stars">★</label>
                    <input type="radio" id="star1" name="rating" value="1">
                    <label for="star1" title="1 star">★</label>
                </div>
            </div>
            <div class="form-group">
                <label for="content">Your Review</label>
                <textarea name="content" id="content" rows="4" placeholder="Share your thoughts about the movie..." required></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Submit Review</button>
        </form>
    </div>

    <div class="reviews-container">
        <div class="reviews-header">
            <h2>All Reviews</h2>
            <button onclick="sortReviewsByRating()" class="btn btn-primary">
                <span class="material-icons">sort</span> Sort by Rating
            </button>
        </div>
        <% if (reviews != null && !reviews.isEmpty()) { %>
            <% for (Review review : reviews) { %>
                <div class="review-card" data-rating="<%= review.getRatingAsInt() %>">
                    <div class="review-header">
                        <h3><%= java.util.Arrays.stream(MovieDao.getAllMovies())
                            .filter(m -> m.getMovieId().equals(review.getMovieId()))
                            .findFirst()
                            .map(m -> m.getTitle())
                            .orElse("Unknown Movie") %></h3>
                        <div class="rating">
                            <% for (int i = 0; i < review.getRatingAsInt(); i++) { %>
                                <span class="star">★</span>
                            <% } %>
                            <% for (int i = review.getRatingAsInt(); i < 5; i++) { %>
                                <span class="star empty">☆</span>
                            <% } %>
                        </div>
                    </div>
                    <p class="review-meta">
                        By <%= review.getUserId() %>
                    </p>
                    <p class="review-content"><%= review.getContent() %></p>
                    <% if (currentUser.getUserId().equals(review.getUserId())) { %>
                        <div class="review-actions">
                            <button class="btn btn-edit" onclick="editReview('<%= review.getReviewId() %>')">
                                <span class="material-icons">edit</span> Edit
                            </button>
                            <form action="delete-review" method="POST" style="display: inline;">
                                <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                                <button type="submit" class="btn btn-delete" onclick="return confirm('Are you sure you want to delete this review?')">
                                    <span class="material-icons">delete</span> Delete
                                </button>
                            </form>
                        </div>
                    <% } %>
                </div>
            <% } %>
        <% } else { %>
            <div class="no-reviews">
                <span class="material-icons">rate_review</span>
                <h3>No Reviews Yet</h3>
                <p>Be the first to share your thoughts about a movie!</p>
            </div>
        <% } %>
    </div>
</main>

<!-- Edit Review Modal -->
<div id="editReviewModal" class="modal">
    <div class="modal-content">
        <div class="modal-header">
            <h2>Edit Review</h2>
            <span class="close-modal">&times;</span>
        </div>
        <form id="editReviewForm">
            <input type="hidden" id="editReviewId" name="reviewId">
            <div class="form-group rating-group">
                <label>Rating</label>
                <div class="star-rating">
                    <input type="radio" id="editStar5" name="rating" value="5">
                    <label for="editStar5" title="5 stars">★</label>
                    <input type="radio" id="editStar4" name="rating" value="4">
                    <label for="editStar4" title="4 stars">★</label>
                    <input type="radio" id="editStar3" name="rating" value="3">
                    <label for="editStar3" title="3 stars">★</label>
                    <input type="radio" id="editStar2" name="rating" value="2">
                    <label for="editStar2" title="2 stars">★</label>
                    <input type="radio" id="editStar1" name="rating" value="1">
                    <label for="editStar1" title="1 star">★</label>
                </div>
            </div>
            <div class="form-group">
                <label for="editContent">Your Review</label>
                <textarea id="editContent" name="content" rows="4" required></textarea>
            </div>
            <div class="modal-actions">
                <button type="button" class="btn btn-secondary close-modal">Cancel</button>
                <button type="submit" class="btn btn-primary">Save Changes</button>
            </div>
        </form>
    </div>
</div>

<style>
    /* Base styles */
    body {
        font-family: 'Poppins', sans-serif;
        margin: 0;
        padding: 0;
        background-color: #f8f9fa;
    }

    /* Header styles */
    .site-header {
        background: #fff;
        box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        position: sticky;
        top: 0;
        z-index: 1000;
    }

    .header-container {
        max-width: 1200px;
        margin: 0 auto;
        padding: 0.75rem 2rem;
        display: flex;
        align-items: center;
        justify-content: space-between;
    }

    .logo {
        font-size: 1.75rem;
        font-weight: 700;
        color: #2c3e50;
        text-decoration: none;
        display: flex;
        align-items: center;
        transition: color 0.3s ease;
    }

    .logo:hover {
        color: #3498db;
    }

    .logo-dot {
        color: #3498db;
        margin-left: 2px;
    }

    /* Navigation styles */
    .main-nav {
        flex: 1;
        margin-left: 2rem;
    }

    .nav-links {
        list-style: none;
        margin: 0;
        padding: 0;
        display: flex;
        gap: 1rem;
        justify-content: center;
    }

    .nav-link {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.5rem 1rem;
        color: #666;
        text-decoration: none;
        border-radius: 8px;
        transition: all 0.3s ease;
    }

    .nav-link:hover {
        color: #3498db;
        background: rgba(52, 152, 219, 0.1);
    }

    .nav-link.active {
        color: #3498db;
        background: rgba(52, 152, 219, 0.1);
        font-weight: 500;
    }

    .nav-link .material-icons {
        font-size: 1.25rem;
    }

    /* Mobile menu toggle */
    .mobile-menu-toggle {
        display: none;
        background: none;
        border: none;
        color: #2c3e50;
        cursor: pointer;
        padding: 0.5rem;
    }

    .mobile-menu-toggle:hover {
        color: #3498db;
    }

    /* Responsive styles */
    @media (max-width: 1024px) {
        .nav-links {
            gap: 0.5rem;
        }

        .nav-link {
            padding: 0.5rem 0.75rem;
        }
    }

    @media (max-width: 768px) {
        .header-container {
            padding: 0.75rem 1rem;
        }

        .main-nav {
            display: none;
            position: absolute;
            top: 100%;
            left: 0;
            right: 0;
            background: white;
            padding: 1rem;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        .nav-links {
            flex-direction: column;
            align-items: stretch;
        }

        .nav-link {
            padding: 0.75rem 1rem;
            border-radius: 4px;
        }

        .mobile-menu-toggle {
            display: block;
        }

        .show-mobile-menu .main-nav {
            display: block;
        }
    }

    .reviews-section {
        max-width: 1200px;
        margin: 0 auto;
        padding: 2rem;
    }

    .page-header {
        text-align: center;
        margin-bottom: 3rem;
    }

    .page-header h1 {
        font-size: 2.5rem;
        color: #2c3e50;
        margin-bottom: 0.5rem;
    }

    .page-header p {
        color: #666;
        font-size: 1.1rem;
    }

    .submit-review {
        background: white;
        border-radius: 12px;
        padding: 2rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        margin-bottom: 3rem;
    }

    .submit-review h2 {
        color: #2c3e50;
        margin-bottom: 1.5rem;
        font-size: 1.5rem;
    }

    .form-group {
        margin-bottom: 1.5rem;
    }

    .form-group label {
        display: block;
        margin-bottom: 0.5rem;
        color: #34495e;
        font-weight: 500;
    }

    .form-group select,
    .form-group textarea {
        width: 100%;
        padding: 0.75rem;
        border: 1px solid #ddd;
        border-radius: 8px;
        font-size: 1rem;
        transition: border-color 0.3s ease;
    }

    .form-group select:focus,
    .form-group textarea:focus {
        border-color: #3498db;
        outline: none;
    }

    .star-rating {
        display: inline-flex;
        flex-direction: row-reverse;
        gap: 0.25rem;
    }

    .star-rating input {
        display: none;
    }

    .star-rating label {
        font-size: 2rem;
        color: #ddd;
        cursor: pointer;
        transition: color 0.2s ease;
        user-select: none;
    }

    .star-rating label:hover,
    .star-rating label:hover ~ label,
    .star-rating input:checked ~ label,
    .star-rating label.active {
        color: #f1c40f;
    }

    .reviews-container {
        display: grid;
        grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
        gap: 2rem;
    }

    .review-card {
        background: white;
        border-radius: 12px;
        padding: 1.5rem;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        transition: transform 0.2s ease, box-shadow 0.2s ease;
    }

    .review-card:hover {
        transform: translateY(-5px);
        box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
    }

    .review-header {
        display: flex;
        justify-content: space-between;
        align-items: flex-start;
        margin-bottom: 1rem;
    }

    .review-header h3 {
        margin: 0;
        color: #2c3e50;
        font-size: 1.25rem;
    }

    .rating {
        color: #f1c40f;
        font-size: 1.2rem;
    }

    .star.empty {
        color: #ddd;
    }

    .review-meta {
        color: #666;
        font-size: 0.9rem;
        margin-bottom: 1rem;
    }

    .review-content {
        color: #444;
        line-height: 1.6;
        margin-bottom: 1.5rem;
    }

    .review-actions {
        display: flex;
        gap: 1rem;
        margin-top: auto;
    }

    .btn {
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.5rem 1rem;
        border: none;
        border-radius: 6px;
        font-size: 0.9rem;
        cursor: pointer;
        transition: background-color 0.2s ease;
    }

    .btn-primary {
        background: #3498db;
        color: white;
    }

    .btn-primary:hover {
        background: #2980b9;
    }

    .btn-edit {
        background: #2ecc71;
        color: white;
    }

    .btn-edit:hover {
        background: #27ae60;
    }

    .btn-delete {
        background: #e74c3c;
        color: white;
    }

    .btn-delete:hover {
        background: #c0392b;
    }

    .no-reviews {
        grid-column: 1 / -1;
        text-align: center;
        padding: 4rem 2rem;
        background: white;
        border-radius: 12px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
    }

    .no-reviews .material-icons {
        font-size: 4rem;
        color: #3498db;
        margin-bottom: 1rem;
    }

    .no-reviews h3 {
        color: #2c3e50;
        margin-bottom: 0.5rem;
    }

    .no-reviews p {
        color: #666;
    }

    .error-message {
        background-color: #fee;
        color: #e74c3c;
        padding: 1rem;
        border-radius: 8px;
        margin-bottom: 1rem;
        border: 1px solid #e74c3c;
    }

    /* Modal styles */
    .modal {
        display: none;
        position: fixed;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: 1000;
        overflow: auto;
    }

    .modal-content {
        background-color: #fff;
        margin: 10% auto;
        padding: 2rem;
        border-radius: 12px;
        max-width: 500px;
        position: relative;
        animation: modalSlideIn 0.3s ease-out;
    }

    @keyframes modalSlideIn {
        from {
            transform: translateY(-10%);
            opacity: 0;
        }
        to {
            transform: translateY(0);
            opacity: 1;
        }
    }

    .modal-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 1.5rem;
    }

    .modal-header h2 {
        margin: 0;
        color: #2c3e50;
    }

    .close-modal {
        font-size: 1.5rem;
        color: #666;
        cursor: pointer;
        padding: 0.5rem;
        line-height: 1;
        transition: color 0.2s ease;
    }

    .close-modal:hover {
        color: #e74c3c;
    }

    .modal-actions {
        display: flex;
        justify-content: flex-end;
        gap: 1rem;
        margin-top: 2rem;
    }

    .btn-secondary {
        background: #95a5a6;
        color: white;
    }

    .btn-secondary:hover {
        background: #7f8c8d;
    }

    /* Toast notification */
    .toast {
        position: fixed;
        bottom: 2rem;
        right: 2rem;
        padding: 1rem 2rem;
        background: #2ecc71;
        color: white;
        border-radius: 8px;
        box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        display: none;
        z-index: 1000;
        animation: toastSlideIn 0.3s ease-out;
    }

    @keyframes toastSlideIn {
        from {
            transform: translateX(100%);
            opacity: 0;
        }
        to {
            transform: translateX(0);
            opacity: 1;
        }
    }

    .toast.error {
        background: #e74c3c;
    }

    /* Improved form validation styles */
    .form-group input:invalid,
    .form-group textarea:invalid {
        border-color: #e74c3c;
    }

    .form-group input:focus:invalid,
    .form-group textarea:focus:invalid {
        box-shadow: 0 0 0 2px rgba(231, 76, 60, 0.2);
    }

    .form-group input:valid,
    .form-group textarea:valid {
        border-color: #2ecc71;
    }

    .reviews-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
        margin-bottom: 2rem;
        padding: 0 1rem;
    }

    .reviews-header h2 {
        margin: 0;
        color: #2c3e50;
    }

    .reviews-header .btn {
        display: inline-flex;
        align-items: center;
        gap: 0.5rem;
        padding: 0.5rem 1rem;
        background: #3498db;
        color: white;
        border: none;
        border-radius: 6px;
        cursor: pointer;
        transition: background-color 0.2s ease;
    }

    .reviews-header .btn:hover {
        background: #2980b9;
    }

    .reviews-header .material-icons {
        font-size: 1.2rem;
    }
</style>

<div id="toast" class="toast"></div>

<script>
    const header = document.querySelector(".site-header");
    const hamburgerBtn = document.querySelector("#hamburger-btn");

    hamburgerBtn.addEventListener("click", () => {
        header.classList.toggle("show-mobile-menu");
    });

    // Close mobile menu when clicking outside
    document.addEventListener("click", (e) => {
        if (!header.contains(e.target) && header.classList.contains("show-mobile-menu")) {
            header.classList.remove("show-mobile-menu");
        }
    });

    function editReview(reviewId) {
        fetch('get-review?reviewId=' + reviewId)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Failed to fetch review');
                }
                return response.json();
            })
            .then(review => {
                document.getElementById('editReviewId').value = review.reviewId;
                document.getElementById('editContent').value = review.content;
                
                const rating = review.ratingAsInt || parseInt(review.rating) || 5;
                const ratingInput = document.querySelector(`#editReviewForm input[value="${rating}"]`);
                if (ratingInput) {
                    ratingInput.checked = true;
                } else {
                    document.querySelector('#editStar5').checked = true;
                }
                
                showModal();
            })
            .catch(error => {
                console.error('Error:', error);
                showToast('Error loading review: ' + error.message, true);
            });
    }

    // Modal functionality
    const modal = document.getElementById('editReviewModal');
    const closeButtons = document.querySelectorAll('.close-modal');
    
    function showModal() {
        if (modal) {
            modal.style.display = 'block';
            document.body.style.overflow = 'hidden';
        }
    }

    function hideModal() {
        if (modal) {
            modal.style.display = 'none';
            document.body.style.overflow = '';
            document.getElementById('editReviewForm').reset();
        }
    }

    if (closeButtons.length > 0) {
        closeButtons.forEach(button => {
            button.addEventListener('click', hideModal);
        });
    }

    if (modal) {
        window.addEventListener('click', (e) => {
            if (e.target === modal) {
                hideModal();
            }
        });
    }

    // Handle form submission
    const editForm = document.getElementById('editReviewForm');
    if (editForm) {
        editForm.addEventListener('submit', function(e) {
            e.preventDefault();
            const formData = new FormData(this);

            const content = formData.get('content');
            const rating = formData.get('rating');
            const reviewId = formData.get('reviewId');

            if (!content || !rating || !reviewId) {
                showToast('Please fill in all fields', true);
                return;
            }

            // Validate content
            if (content.trim().length === 0) {
                showToast('Review content cannot be empty', true);
                return;
            }

            fetch('update-review', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: new URLSearchParams({
                    reviewId: reviewId,
                    content: content.trim(),
                    rating: rating
                })
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Server returned ' + response.status);
                }
                return response.json();
            })
            .then(data => {
                if (data.success) {
                    hideModal();
                    showToast('Review updated successfully!');
                    setTimeout(() => window.location.reload(), 1500);
                } else {
                    throw new Error('Failed to update review');
                }
            })
            .catch(error => {
                console.error('Error:', error);
                showToast('Error: ' + error.message, true);
            });
        });
    }

    // Toast notification
    const toast = document.getElementById('toast');
    function showToast(message, isError = false) {
        if (toast) {
            toast.textContent = message;
            toast.className = 'toast' + (isError ? ' error' : '');
            toast.style.display = 'block';
            
            setTimeout(() => {
                if (toast) {
                    toast.style.display = 'none';
                }
            }, 3000);
        }
    }

    // Initialize star ratings
    function initializeStarRatings() {
        const starContainers = document.querySelectorAll('.star-rating');
        starContainers.forEach(container => {
            const inputs = container.querySelectorAll('input[type="radio"]');
            inputs.forEach(input => {
                input.addEventListener('change', function() {
                    const value = this.value;
                    const labels = container.querySelectorAll('label');
                    labels.forEach(label => {
                        const labelValue = label.getAttribute('for').replace(/[^\d]/g, '');
                        label.classList.toggle('active', labelValue <= value);
                    });
                });
            });
        });
    }

    // Call initialization when document is ready
    document.addEventListener('DOMContentLoaded', initializeStarRatings);

    function bubbleSort(reviews) {
        const n = reviews.length;
        for (let i = 0; i < n - 1; i++) {
            for (let j = 0; j < n - i - 1; j++) {
                const rating1 = parseInt(reviews[j].getAttribute('data-rating'));
                const rating2 = parseInt(reviews[j + 1].getAttribute('data-rating'));
                if (rating1 < rating2) {
                    // Swap the elements
                    reviews[j].parentNode.insertBefore(reviews[j + 1], reviews[j]);
                }
            }
        }
    }

    function sortReviewsByRating() {
        const reviewsContainer = document.querySelector('.reviews-container');
        const reviews = Array.from(reviewsContainer.getElementsByClassName('review-card'));
        bubbleSort(reviews);
    }
</script>
<script src="assets/js/shared.js"></script>
</body>
</html>
