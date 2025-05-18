<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Movie</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<div class="container mt-5">
    <h2>Add New Movie</h2>

    <% if (request.getAttribute("error") != null) { %>
    <div class="alert alert-danger"><%= request.getAttribute("error") %></div>
    <% } %>

    <form action="add-movie" method="post" enctype="multipart/form-data">
        <div class="mb-3">
            <label for="title" class="form-label">Title *</label>
            <input type="text" id="title" name="title" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="genre" class="form-label">Genre *</label>
            <input type="text" id="genre" name="genre" class="form-control" required />
        </div>

        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea id="description" name="description" rows="4" class="form-control"></textarea>
        </div>

        <div class="mb-3">
            <label for="movieImage" class="form-label">Movie Image (optional)</label>
            <input type="file" id="movieImage" name="movieImage" class="form-control" accept="image/*" />
        </div>

        <button type="submit" class="btn btn-success">Add Movie</button>
        <a href="movie-list.jsp" class="btn btn-secondary ms-2">Cancel</a>
    </form>
</div>
</body>
</html>
