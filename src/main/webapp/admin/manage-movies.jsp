<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="models.Movie" %>
<%@ page import="java.util.List" %>
<%
    // Ensure that the user is an admin
    if (session.getAttribute("user") == null || !((models.User) session.getAttribute("user")).getRole().equalsIgnoreCase("admin")) {
        response.sendRedirect(request.getContextPath() + "/login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Movies - Cineverse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/admin.css">
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="${pageContext.request.contextPath}/admin/dashboard">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <li><a href="${pageContext.request.contextPath}/admin/dashboard">Dashboard</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/manage-users">Manage Users</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/manage-movies" class="active">Manage Movies</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/manage-rentals">Manage Rentals</a></li>
            <li><a href="${pageContext.request.contextPath}/admin/manage-reviews">Manage Reviews</a></li>
            <li><a href="${pageContext.request.contextPath}/logout">Logout</a></li>
        </ul>
    </nav>
</header>

<section class="manage-movies">
    <h2>Manage Movies</h2>
    
    <div class="search-section">
        <input type="text" id="searchMovie" placeholder="Search movies...">
        <button onclick="searchMovies()">Search</button>
    </div>

    <div class="movies-table">
        <table>
            <thead>
                <tr>
                    <th>Movie ID</th>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Rating</th>
                    <th>Description</th>
                    <th>Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
                    if (movies != null) {
                        for (Movie movie : movies) {
                %>
                    <tr data-movie-id="<%= movie.getMovieId() %>">
                        <td><%= movie.getMovieId() %></td>
                        <td><%= movie.getTitle() %></td>
                        <td><%= movie.getGenre() %></td>
                        <td><%= movie.getRating() %></td>
                        <td><%= movie.getDescription() %></td>
                        <td><%= movie.isAvailable() ? "Yes" : "No" %></td>
                        <td>
                            <button class="btn btn-sm btn-primary" onclick="showEditModal('<%= movie.getMovieId() %>', '<%= movie.getTitle() %>', '<%= movie.getGenre() %>', '<%= movie.getRating() %>', '<%= movie.getDescription() %>', <%= movie.isAvailable() %>)">Edit</button>
                            <form action="${pageContext.request.contextPath}/admin/delete-movie" method="post" style="display: inline;">
                                <input type="hidden" name="movieId" value="<%= movie.getMovieId() %>">
                                <button type="submit" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to delete this movie?')">Delete</button>
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

    <div class="action-buttons">
        <button class="btn btn-primary" onclick="showAddMovieModal()">Add New Movie</button>
    </div>
</section>

<!-- Add Movie Modal -->
<div id="addMovieModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Add New Movie</h2>
        <form action="${pageContext.request.contextPath}/admin/add-movie" method="post">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" name="title" required>
            </div>
            <div class="form-group">
                <label for="genre">Genre:</label>
                <input type="text" id="genre" name="genre" required>
            </div>
            <div class="form-group">
                <label for="rating">Rating:</label>
                <input type="number" id="rating" name="rating" min="0" max="9" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <div class="form-group">
                <label for="available">Available:</label>
                <input type="checkbox" id="available" name="available" checked>
            </div>
            <button type="submit" class="btn">Add Movie</button>
        </form>
    </div>
</div>

<!-- Edit Movie Modal -->
<div id="editMovieModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <h2>Edit Movie</h2>
        <form id="editMovieForm" target="hiddenFrame" onsubmit="return handleEditSubmit(event)">
            <input type="hidden" id="editMovieId" name="movieId">
            <div class="form-group">
                <label for="editTitle">Title:</label>
                <input type="text" id="editTitle" name="title" required>
            </div>
            <div class="form-group">
                <label for="editGenre">Genre:</label>
                <input type="text" id="editGenre" name="genre" required>
            </div>
            <div class="form-group">
                <label for="editRating">Rating:</label>
                <input type="number" id="editRating" name="rating" min="0" max="9" required>
            </div>
            <div class="form-group">
                <label for="editDescription">Description:</label>
                <textarea id="editDescription" name="description" required></textarea>
            </div>
            <div class="form-group">
                <label for="editAvailable">Available:</label>
                <input type="checkbox" id="editAvailable" name="available">
            </div>
            <button type="submit" class="btn">Update Movie</button>
        </form>
    </div>
</div>

<!-- Hidden iframe for form submission -->
<iframe name="hiddenFrame" style="display:none;"></iframe>

<script>
    function searchMovies() {
        const searchTerm = document.getElementById('searchMovie').value;
        window.location.href = '${pageContext.request.contextPath}/admin/manage-movies?search=' + encodeURIComponent(searchTerm);
    }

    // Get modal elements
    var addMovieModal = document.getElementById("addMovieModal");
    var editMovieModal = document.getElementById("editMovieModal");
    var closeButtons = document.getElementsByClassName("close");

    // Close modal when clicking the X
    for (var i = 0; i < closeButtons.length; i++) {
        closeButtons[i].onclick = function() {
            addMovieModal.style.display = "none";
            editMovieModal.style.display = "none";
        }
    }

    // Close modal when clicking outside
    window.onclick = function(event) {
        if (event.target == addMovieModal) {
            addMovieModal.style.display = "none";
        }
        if (event.target == editMovieModal) {
            editMovieModal.style.display = "none";
        }
    }

    function showAddMovieModal() {
        addMovieModal.style.display = "block";
    }

    function showEditModal(movieId, title, genre, rating, description, available) {
        document.getElementById('editMovieId').value = movieId;
        document.getElementById('editTitle').value = title;
        document.getElementById('editGenre').value = genre;
        document.getElementById('editRating').value = rating;
        document.getElementById('editDescription').value = description;
        document.getElementById('editAvailable').checked = available;
        editMovieModal.style.display = "block";
    }

    function handleEditSubmit(event) {
        event.preventDefault();
        const form = event.target;
        const formData = new FormData(form);
        const movieId = formData.get('movieId');
        
        // Update the table row with new data
        const row = document.querySelector(`tr[data-movie-id="${movieId}"]`);
        if (row) {
            row.querySelector('td:nth-child(2)').textContent = formData.get('title');
            row.querySelector('td:nth-child(3)').textContent = formData.get('genre');
            row.querySelector('td:nth-child(4)').textContent = formData.get('rating');
            row.querySelector('td:nth-child(5)').textContent = formData.get('description');
            row.querySelector('td:nth-child(6)').textContent = formData.get('available') === 'on' ? 'Yes' : 'No';
        }
        
        // Submit the form to update the database
        fetch('${pageContext.request.contextPath}/admin/edit-movie', {
            method: 'POST',
            body: formData
        }).then(() => {
            // Close the modal after successful update
            editMovieModal.style.display = "none";
        });
        
        return false;
    }
</script>
</body>
</html> 