<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Review" %>
<%@ page import="java.util.List" %>
<%
    // Ensure that the user is an admin
    if (session.getAttribute("user") == null || !((models.User) session.getAttribute("user")).getRole().equalsIgnoreCase("admin")) {
        response.sendRedirect("login.jsp");  // Redirect to login if the user is not an admin
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Reviews - Cineverse</title>
    <link rel="stylesheet" href="assets/styles/admin.css">
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="#">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="manage-users.jsp">Manage Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Manage Movies</a></li>
            <li><a href="manage-rentals.jsp">Manage Rentals</a></li>
            <li><a href="manage-reviews.jsp" class="active">Manage Reviews</a></li>
            <li><a href="logout.jsp">Logout</a></li>
        </ul>
    </nav>
</header>

<section class="manage-reviews">
    <h2>Manage Reviews</h2>
    
    <div class="search-section">
        <input type="text" id="searchReview" placeholder="Search reviews...">
        <button onclick="searchReviews()">Search</button>
    </div>

    <div class="reviews-table">
        <table>
            <thead>
                <tr>
                    <th>Review ID</th>
                    <th>User</th>
                    <th>Movie</th>
                    <th>Rating</th>
                    <th>Comment</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Review> reviews = (List<Review>) request.getAttribute("reviews");
                    if (reviews != null) {
                        for (Review review : reviews) {
                %>
                    <tr>
                        <td><%= review.getReviewId() %></td>
                        <td><%= review.getUserId() %></td>
                        <td><%= review.getMovieId() %></td>
                        <td><%= review.getRating() %></td>
                        <td><%= review.getContent() %></td>
                        <td>
                            <button class="btn btn-primary" onclick="editReview('<%= review.getReviewId() %>')">Edit</button>
                            <form action="manage-reviews" method="POST" style="display: inline;">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="reviewId" value="<%= review.getReviewId() %>">
                                <button type="submit" class="btn btn-danger" onclick="return confirm('Are you sure you want to delete this review?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</section>

<style>
    .manage-reviews {
        padding: 2rem;
        max-width: 1200px;
        margin: 0 auto;
    }

    .search-section {
        margin-bottom: 2rem;
        display: flex;
        gap: 1rem;
    }

    .search-section input {
        padding: 0.5rem;
        border: 1px solid #ddd;
        border-radius: 4px;
        flex-grow: 1;
    }

    .search-section button {
        padding: 0.5rem 1rem;
        background: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .reviews-table {
        background: white;
        border-radius: 8px;
        box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        overflow-x: auto;
    }

    table {
        width: 100%;
        border-collapse: collapse;
    }

    th, td {
        padding: 1rem;
        text-align: left;
        border-bottom: 1px solid #ddd;
    }

    th {
        background: #f8f9fa;
        font-weight: 600;
    }

    .btn {
        padding: 0.5rem 1rem;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    .btn-primary {
        background: #007bff;
        color: white;
    }

    .btn-danger {
        background: #dc3545;
        color: white;
    }
</style>

<script>
    function searchReviews() {
        const searchTerm = document.getElementById('searchReview').value;
        // TODO: Implement review search functionality
        console.log('Searching for:', searchTerm);
    }

    function editReview(reviewId) {
        // TODO: Implement edit review functionality
        console.log('Edit review:', reviewId);
    }
</script>
</body>
</html> 