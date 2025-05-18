<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Movie</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 800px;
            margin: 50px auto;
            padding: 20px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .movie-poster {
            max-width: 200px;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2 class="mb-4">Edit Movie</h2>
        
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        
        <form action="edit-movie" method="post" enctype="multipart/form-data">
            <input type="hidden" name="movieId" value="${movie.id}">
            
            <div class="form-group">
                <label for="title">Movie Title</label>
                <input type="text" class="form-control" id="title" name="title" value="${movie.title}" required>
            </div>
            
            <div class="form-group">
                <label for="genre">Genre</label>
                <input type="text" class="form-control" id="genre" name="genre" value="${movie.genre}" required>
            </div>
            
            <div class="form-group">
                <label for="description">Description</label>
                <textarea class="form-control" id="description" name="description" rows="4">${movie.description}</textarea>
            </div>
            
            <div class="form-group">
                <label for="price">Price</label>
                <input type="number" class="form-control" id="price" name="price" 
                       value="${movie.price}" step="0.01" min="0" required>
            </div>
            
            <div class="form-group">
                <label>Current Poster</label>
                <div>
                    <img src="uploads/${movie.imageFileName}" alt="${movie.title}" class="movie-poster">
                </div>
            </div>
            
            <div class="form-group">
                <label for="movieImage">New Poster (optional)</label>
                <input type="file" class="form-control-file" id="movieImage" name="movieImage" accept="image/*">
                <small class="form-text text-muted">Leave empty to keep the current poster</small>
            </div>
            
            <div class="form-group">
                <button type="submit" class="btn btn-primary">Update Movie</button>
                <a href="manage-movies.jsp" class="btn btn-secondary">Cancel</a>
            </div>
        </form>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 