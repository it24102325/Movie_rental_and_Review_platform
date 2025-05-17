<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Movie" %>
<%@ page import="services.MovieDao" %>
<%@ page import="models.AdminUser" %>
<%@ page import="services.AdminDao" %>
<%@ page import="models.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // Check if user is logged in and is an admin
    User user = (User) session.getAttribute("user");
    if (user == null || !user.isAdmin()) {
        response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - CineVerse</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <script>
        tailwind.config = {
            darkMode: 'class',
            theme: {
                extend: {
                    colors: {
                        primary: '#3B82F6',
                        secondary: '#6B7280',
                        dark: '#1F2937',
                        light: '#F3F4F6'
                    }
                }
            }
        }
    </script>
    <style>
        body {
            background-color: #adc5cf !important;
        }
        .movie-card {
            transition: transform 0.2s, box-shadow 0.2s;
        }
        .movie-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
        }
        .fade-in {
            animation: fadeIn 0.3s ease-in;
        }
        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }
    </style>
</head>
<body class="bg-gray-50 dark:bg-dark min-h-screen">
    <div class="flex h-screen overflow-hidden">
        <!-- Sidebar -->
        <nav class="w-64 bg-white dark:bg-gray-800 shadow-lg hidden md:block">
            <div class="p-4">
                <div class="text-center mb-8">
                    <i class="fas fa-user-shield text-4xl text-primary dark:text-blue-400"></i>
                    <h5 class="mt-2 text-gray-700 dark:text-gray-200 font-semibold">Admin Panel</h5>
                </div>
                <ul class="space-y-2">
                    <li>
                        <a href="dashboard.jsp" class="flex items-center p-3 text-primary dark:text-blue-400 bg-blue-50 dark:bg-gray-700 rounded-lg">
                            <i class="fas fa-film w-6"></i>
                            <span class="ml-3">Movies</span>
                        </a>
                    </li>
                    <li>
                        <a href="users.jsp" class="flex items-center p-3 text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 rounded-lg">
                            <i class="fas fa-users w-6"></i>
                            <span class="ml-3">Users</span>
                        </a>
                    </li>
                    <li>
                        <a href="rentals.jsp" class="flex items-center p-3 text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 rounded-lg">
                            <i class="fas fa-shopping-cart w-6"></i>
                            <span class="ml-3">Rentals</span>
                        </a>
                    </li>
                    <li>
                        <a href="admins.jsp" class="flex items-center p-3 text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 rounded-lg">
                            <i class="fas fa-user-shield w-6"></i>
                            <span class="ml-3">Manage Admins</span>
                        </a>
                    </li>
                    <li>
                        <a href="../logout.jsp" class="flex items-center p-3 text-gray-600 dark:text-gray-300 hover:bg-gray-50 dark:hover:bg-gray-700 rounded-lg">
                            <i class="fas fa-sign-out-alt w-6"></i>
                            <span class="ml-3">Logout</span>
                        </a>
                    </li>
                </ul>
            </div>
        </nav>

        <!-- Main Content -->
        <main class="flex-1 overflow-y-auto p-6">
            <div class="flex justify-between items-center mb-6">
                <h1 class="text-2xl font-bold text-gray-800 dark:text-white">Movie Management</h1>
                <button class="bg-primary hover:bg-blue-600 text-white px-4 py-2 rounded-lg flex items-center transition-colors" data-bs-toggle="modal" data-bs-target="#addMovieModal">
                    <i class="fas fa-plus mr-2"></i>Add New Movie
                </button>
            </div>

            <!-- Search and Sort Section -->
            <div class="flex flex-wrap gap-4 mb-6">
                <div class="flex-1 min-w-[300px]">
                    <div class="flex">
                        <input type="text" id="searchMovie" class="flex-1 rounded-l-lg border border-gray-300 dark:border-gray-600 px-4 py-2 focus:outline-none focus:ring-2 focus:ring-primary dark:bg-gray-700 dark:text-white" placeholder="Search movies...">
                        <select id="searchType" class="border-t border-b border-gray-300 dark:border-gray-600 px-3 py-2 bg-white dark:bg-gray-700 dark:text-white">
                            <option value="all">All</option>
                            <option value="title">Title</option>
                            <option value="genre">Genre</option>
                            <option value="rating">Rating</option>
                        </select>
                        <button onclick="searchMovies()" class="bg-primary hover:bg-blue-600 text-white px-4 py-2 rounded-r-lg transition-colors">
                            <i class="fas fa-search"></i>
                        </button>
                    </div>
                </div>
                <a href="?sortByRating=true" class="bg-secondary hover:bg-gray-600 text-white px-4 py-2 rounded-lg transition-colors flex items-center">
                    <i class="fas fa-sort-numeric-down mr-2"></i>Sort by Rating
                </a>
            </div>

            <!-- Messages -->
            <c:if test="${not empty sessionScope.success}">
                <div class="bg-green-100 border border-green-400 text-green-700 px-4 py-3 rounded relative mb-4 fade-in" role="alert">
                    <span class="block sm:inline">${sessionScope.success}</span>
                    <button type="button" class="absolute top-0 bottom-0 right-0 px-4 py-3" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <c:remove var="success" scope="session"/>
            </c:if>

            <c:if test="${not empty sessionScope.error}">
                <div class="bg-red-100 border border-red-400 text-red-700 px-4 py-3 rounded relative mb-4 fade-in" role="alert">
                    <span class="block sm:inline">${sessionScope.error}</span>
                    <button type="button" class="absolute top-0 bottom-0 right-0 px-4 py-3" onclick="this.parentElement.remove()">
                        <i class="fas fa-times"></i>
                    </button>
                </div>
                <c:remove var="error" scope="session"/>
            </c:if>

            <!-- Movies List -->
            <div class="bg-white dark:bg-gray-800 rounded-lg shadow-md overflow-hidden">
                <div class="overflow-x-auto">
                    <table class="min-w-full divide-y divide-gray-200 dark:divide-gray-700">
                        <thead class="bg-gray-50 dark:bg-gray-700">
                            <tr>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Title</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Genre</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Rating</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Status</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Description</th>
                                <th scope="col" class="px-6 py-3 text-left text-xs font-medium text-gray-500 dark:text-gray-300 uppercase tracking-wider">Actions</th>
                            </tr>
                        </thead>
                        <tbody class="bg-white dark:bg-gray-800 divide-y divide-gray-200 dark:divide-gray-700">
                            <c:forEach items="${movies}" var="movie">
                                <tr class="hover:bg-gray-50 dark:hover:bg-gray-700 transition-colors">
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <div class="text-sm font-medium text-gray-900 dark:text-white">${movie.title}</div>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <div class="text-sm text-gray-500 dark:text-gray-300">${movie.genre}</div>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="bg-yellow-100 text-yellow-800 text-xs font-medium px-2.5 py-0.5 rounded dark:bg-yellow-900 dark:text-yellow-300">
                                            ${movie.rating}/9
                                        </span>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap">
                                        <span class="${movie.available ? 'text-green-600 dark:text-green-400' : 'text-red-600 dark:text-red-400'}">
                                            ${movie.available ? 'Available' : 'Not Available'}
                                        </span>
                                    </td>
                                    <td class="px-6 py-4">
                                        <div class="text-sm text-gray-500 dark:text-gray-300 max-w-xs truncate">${movie.description}</div>
                                    </td>
                                    <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                        <div class="flex space-x-2">
                                            <button onclick="editMovie('${movie.movieId}')" 
                                                class="text-green-600 hover:text-green-900 dark:text-green-400 dark:hover:text-green-300">
                                                <i class="fas fa-edit"></i>
                                            </button>
                                            <button onclick="deleteMovie('${movie.movieId}')" 
                                                class="text-red-600 hover:text-red-900 dark:text-red-400 dark:hover:text-red-300">
                                                <i class="fas fa-trash"></i>
                                            </button>
                                            <button onclick="toggleAvailability('${movie.movieId}')" 
                                                class="text-blue-600 hover:text-blue-900 dark:text-blue-400 dark:hover:text-blue-300">
                                                <i class="fas fa-${movie.available ? 'times' : 'check'}"></i>
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </main>
    </div>

    <!-- Add Movie Modal -->
    <div id="addMovieModal" class="modal" style="position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: none; justify-content: center; align-items: center;">
        <div style="background-color: white; padding: 20px; border-radius: 8px; width: 90%; max-width: 600px; max-height: 90vh; overflow-y: auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2 style="margin: 0; font-size: 1.5rem;">Add New Movie</h2>
                <button onclick="closeAddMovieModal()" style="background: none; border: none; font-size: 1.5rem; cursor: pointer;">&times;</button>
            </div>
            <form action="add-movie" method="post">
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Title:</label>
                    <input type="text" name="title" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Genre:</label>
                    <select name="genre" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                        <option value="Action">Action</option>
                        <option value="Comedy">Comedy</option>
                        <option value="Drama">Drama</option>
                        <option value="Horror">Horror</option>
                        <option value="Sci-Fi">Sci-Fi</option>
                    </select>
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Rating:</label>
                    <input type="number" name="rating" min="1" max="10" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Description:</label>
                    <textarea name="description" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; min-height: 100px;"></textarea>
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: flex; align-items: center; gap: 8px;">
                        <input type="checkbox" name="available" checked>
                        <span>Available for rent</span>
                    </label>
                </div>
                <div style="display: flex; justify-content: flex-end; gap: 10px;">
                    <button type="button" onclick="closeAddMovieModal()" style="padding: 8px 16px; border: 1px solid #ddd; border-radius: 4px; background: #f5f5f5; cursor: pointer;">Close</button>
                    <button type="submit" style="padding: 8px 16px; border: none; border-radius: 4px; background: #3B82F6; color: white; cursor: pointer;">Add Movie</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Edit Movie Modal -->
    <div id="editMovieModal" class="modal" style="position: fixed; z-index: 1000; left: 0; top: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: none; justify-content: center; align-items: center;">
        <div style="background-color: white; padding: 20px; border-radius: 8px; width: 90%; max-width: 600px; max-height: 90vh; overflow-y: auto;">
            <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
                <h2 style="margin: 0; font-size: 1.5rem;">Edit Movie</h2>
                <button onclick="closeEditMovieModal()" style="background: none; border: none; font-size: 1.5rem; cursor: pointer;">&times;</button>
            </div>
            <form action="edit-movie" method="post">
                <input type="hidden" id="editMovieId" name="movieId">
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Title:</label>
                    <input type="text" id="editTitle" name="title" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Genre:</label>
                    <select id="editGenre" name="genre" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                        <option value="Action">Action</option>
                        <option value="Comedy">Comedy</option>
                        <option value="Drama">Drama</option>
                        <option value="Horror">Horror</option>
                        <option value="Sci-Fi">Sci-Fi</option>
                    </select>
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Rating:</label>
                    <input type="number" id="editRating" name="rating" min="0" max="9" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px;">
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: block; margin-bottom: 5px;">Description:</label>
                    <textarea id="editDescription" name="description" required style="width: 100%; padding: 8px; border: 1px solid #ddd; border-radius: 4px; min-height: 100px;"></textarea>
                </div>
                <div style="margin-bottom: 15px;">
                    <label style="display: flex; align-items: center; gap: 5px;">
                        <input type="checkbox" id="editAvailable" name="available">
                        Available
                    </label>
                </div>
                <button type="submit" style="background: #007bff; color: white; border: none; padding: 10px 20px; border-radius: 4px; cursor: pointer;">Update Movie</button>
            </form>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Update search functionality for table view
        function searchMovies() {
            const searchTerm = document.getElementById('searchMovie').value.toLowerCase();
            const searchType = document.getElementById('searchType').value;
            const rows = document.querySelectorAll('tbody tr');
            let hasResults = false;
            
            rows.forEach(row => {
                let isVisible = false;
                const cells = row.querySelectorAll('td');
                
                switch(searchType) {
                    case 'title':
                        isVisible = cells[0].textContent.toLowerCase().includes(searchTerm);
                        break;
                    case 'genre':
                        isVisible = cells[1].textContent.toLowerCase().includes(searchTerm);
                        break;
                    case 'rating':
                        isVisible = cells[2].textContent.includes(searchTerm);
                        break;
                    default:
                        const title = cells[0].textContent.toLowerCase();
                        const genre = cells[1].textContent.toLowerCase();
                        const description = cells[4].textContent.toLowerCase();
                        isVisible = title.includes(searchTerm) || 
                                  genre.includes(searchTerm) || 
                                  description.includes(searchTerm);
                }
                
                row.style.display = isVisible ? '' : 'none';
                if (isVisible) hasResults = true;
            });
            
            let noResultsMsg = document.querySelector('.no-results');
            if (!hasResults) {
                if (!noResultsMsg) {
                    noResultsMsg = document.createElement('div');
                    noResultsMsg.className = 'no-results text-center p-4 text-gray-500 dark:text-gray-400';
                    noResultsMsg.textContent = 'No movies found matching your search criteria.';
                    document.querySelector('tbody').appendChild(noResultsMsg);
                }
            } else if (noResultsMsg) {
                noResultsMsg.remove();
            }
        }

        // Update sort functionality for table view
        function sortMovies() {
            const sortType = document.getElementById('sortType').value;
            const tbody = document.querySelector('tbody');
            const rows = Array.from(tbody.querySelectorAll('tr'));
            
            rows.sort((a, b) => {
                const cellsA = a.querySelectorAll('td');
                const cellsB = b.querySelectorAll('td');
                
                switch(sortType) {
                    case 'rating':
                        const ratingA = parseInt(cellsA[2].querySelector('span').textContent.split('/')[0]);
                        const ratingB = parseInt(cellsB[2].querySelector('span').textContent.split('/')[0]);
                        return ratingB - ratingA;
                    case 'title':
                        return cellsA[0].textContent.localeCompare(cellsB[0].textContent);
                    case 'genre':
                        return cellsA[1].textContent.localeCompare(cellsB[1].textContent);
                    default:
                        return 0;
                }
            });
            
            rows.forEach(row => tbody.appendChild(row));
        }

        // Movie management functions
        function editMovie(movieId) {
            fetch('get-movie?id=' + movieId)
                .then(response => response.json())
                .then(movie => {
                    document.getElementById('editMovieId').value = movie.movieId;
                    document.getElementById('editTitle').value = movie.title;
                    document.getElementById('editGenre').value = movie.genre;
                    document.getElementById('editRating').value = movie.rating;
                    document.getElementById('editDescription').value = movie.description;
                    document.getElementById('editAvailable').checked = movie.available;
                    
                    showEditMovieModal();
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('Failed to load movie details');
                });
        }

        function deleteMovie(movieId) {
            if (confirm('Are you sure you want to delete this movie ?')) {
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = 'delete-movie';
                
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'movieId';
                input.value = movieId;
                
                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }
        }

        function toggleAvailability(movieId) {
            const form = document.createElement('form');
            form.method = 'POST';
            form.action = 'toggle-movie-availability';
            
            const input = document.createElement('input');
            input.type = 'hidden';
            input.name = 'movieId';
            input.value = movieId;
            
            form.appendChild(input);
            document.body.appendChild(form);
            form.submit();
        }

        // Add Movie Modal Functions
        function showAddMovieModal() {
            const modal = document.getElementById('addMovieModal');
            modal.style.display = 'flex';
        }

        function closeAddMovieModal() {
            const modal = document.getElementById('addMovieModal');
            modal.style.display = 'none';
        }

        // Edit Movie Modal Functions
        function showEditMovieModal() {
            const modal = document.getElementById('editMovieModal');
            modal.style.display = 'flex';
        }

        function closeEditMovieModal() {
            document.getElementById('editMovieModal').style.display = 'none';
        }

        // Event Listeners
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchMovie');
            const searchType = document.getElementById('searchType');
            
            if (searchInput) {
                searchInput.addEventListener('input', searchMovies);
                
                searchType.addEventListener('change', function() {
                    const placeholders = {
                        title: 'Search by title...',
                        genre: 'Search by genre...',
                        rating: 'Enter rating (0-9)...',
                        all: 'Search movies...'
                    };
                    searchInput.placeholder = placeholders[this.value] || placeholders.all;
                });
            }

            // Add fade-in animation to movie cards
            const observer = new IntersectionObserver((entries) => {
                entries.forEach(entry => {
                    if (entry.isIntersecting) {
                        entry.target.classList.add('fade-in');
                        observer.unobserve(entry.target);
                    }
                });
            }, { threshold: 0.1 });

            document.querySelectorAll('.movie-card').forEach(card => {
                observer.observe(card);
            });

            // Replace the Bootstrap modal initialization with our simple modal
            document.querySelector('[data-bs-target="#addMovieModal"]').addEventListener('click', function(e) {
                e.preventDefault();
                showAddMovieModal();
            });

            // Close modal when clicking outside
            window.addEventListener('click', function(event) {
                const addModal = document.getElementById('addMovieModal');
                const editModal = document.getElementById('editMovieModal');
                if (event.target === addModal) {
                    closeAddMovieModal();
                }
                if (event.target === editModal) {
                    closeEditMovieModal();
                }
            });
        });
    </script>
</body>
</html> 