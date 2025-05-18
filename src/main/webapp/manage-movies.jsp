<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Movies - Admin</title>
    <link rel="stylesheet" href="assets/styles/manage-movies.css">
    <style>
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        .movie-form {
            max-width: 600px;
            margin: 20px auto;
            padding: 20px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        .form-group label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        .form-group input[type="text"],
        .form-group textarea {
            width: 100%;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
            font-size: 14px;
        }
        .form-group textarea {
            height: 100px;
            resize: vertical;
        }
        .form-group input[type="file"] {
            padding: 8px 0;
        }
        .submit-btn {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }
        .submit-btn:hover {
            background-color: #45a049;
        }
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
        .success-message {
            color: #388e3c;
            margin-top: 10px;
            padding: 10px;
            background-color: #e8f5e9;
            border-radius: 4px;
        }
        h2, h3 {
            color: #333;
            margin-bottom: 20px;
        }
        .movies-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .movies-table th,
        .movies-table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        .movies-table th {
            background-color: #f5f5f5;
            font-weight: bold;
            color: #333;
        }
        .movies-table tr:hover {
            background-color: #f9f9f9;
        }
        .movie-poster {
            width: 100px;
            height: 150px;
            object-fit: cover;
            border-radius: 4px;
        }
        .action-btn {
            padding: 6px 12px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 14px;
            margin-right: 5px;
            text-decoration: none;
            display: inline-block;
        }
        .edit-btn {
            background-color: #2196F3;
            color: white;
        }
        .edit-btn:hover {
            background-color: #1976D2;
        }
        .delete-btn {
            background-color: #f44336;
            color: white;
        }
        .delete-btn:hover {
            background-color: #d32f2f;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Manage Movies</h2>
        
        <div class="movie-form">
            <h3>Add New Movie</h3>
            <form action="add-movie" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="title">Movie Title:</label>
                    <input type="text" id="title" name="title" required 
                           placeholder="Enter movie title">
                </div>
                
                <div class="form-group">
                    <label for="genre">Genre:</label>
                    <input type="text" id="genre" name="genre" required 
                           placeholder="Enter movie genre">
                </div>
                
                <div class="form-group">
                    <label for="description">Description:</label>
                    <textarea id="description" name="description" required 
                              placeholder="Enter movie description"></textarea>
                </div>
                
                <div class="form-group">
                    <label for="price">Price:</label>
                    <input type="number" id="price" name="price" step="0.01" min="0" required 
                           placeholder="Enter rental price">
                </div>
                
                <div class="form-group">
                    <label for="movieImage">Movie Poster:</label>
                    <input type="file" id="movieImage" name="movieImage" 
                           accept="image/*" required>
                    <small>Maximum file size: 5MB. Supported formats: JPG, PNG, GIF</small>
                </div>
                
                <button type="submit" class="submit-btn">Add Movie</button>
            </form>
            
            <c:if test="${not empty error}">
                <div class="error-message">${error}</div>
            </c:if>
            
            <c:if test="${not empty success}">
                <div class="success-message">${success}</div>
            </c:if>
        </div>

        <h3>Movie List</h3>
        <table class="movies-table">
            <thead>
                <tr>
                    <th>Poster</th>
                    <th>Title</th>
                    <th>Genre</th>
                    <th>Description</th>
                    <th>Price</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="movie" items="${movies}">
                    <tr>
                        <td>
                            <c:if test="${not empty movie.imageFileName}">
                                <img src="uploads/${movie.imageFileName}" alt="${movie.title}" class="movie-poster">
                            </c:if>
                        </td>
                        <td>${movie.title}</td>
                        <td>${movie.genre}</td>
                        <td>${movie.description}</td>
                        <td>$${movie.price}</td>
                        <td>
                            <a href="edit-movie?id=${movie.movieId}" class="action-btn edit-btn">Edit</a>
                            <form action="delete-movie" method="post" style="display: inline;">
                                <input type="hidden" name="id" value="${movie.movieId}">
                                <button type="submit" class="action-btn delete-btn" 
                                        onclick="return confirm('Are you sure you want to delete this movie?')">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
