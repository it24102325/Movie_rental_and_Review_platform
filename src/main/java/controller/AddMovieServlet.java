package controller;


import models.Movie;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/admin/add-movie")
public class AddMovieServlet extends HttpServlet {
    private MovieDao movieDao;

    @Override
    public void init() {
        movieDao = new MovieDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form data
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String description = request.getParameter("description");
            boolean available = request.getParameter("available") != null;
            String posterUrl = request.getParameter("posterUrl");

            // Validate input
            if (title == null || title.trim().isEmpty() ||
                genre == null || genre.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
                throw new IllegalArgumentException("All fields are required");
            }

            // Create and save movie
            Movie movie = new Movie();
            movie.setTitle(title.trim());
            movie.setGenre(genre.trim());
            movie.setRating(rating);
            movie.setDescription(description.trim());
            movie.setAvailable(available);
            movie.setPosterUrl(posterUrl != null && !posterUrl.trim().isEmpty() ? 
                             posterUrl.trim() : "assets/images/default-poster.jpg");

            movieDao.addMovie(movie);
            request.getSession().setAttribute("success", "Movie added successfully!");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to add movie: " + e.getMessage());
        }

        // Redirect back to dashboard
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
} 