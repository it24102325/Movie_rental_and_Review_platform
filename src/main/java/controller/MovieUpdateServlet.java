package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/movie-update")
public class MovieUpdateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Get all movie details from the form
        String movieId = request.getParameter("movieId");
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        int rating = Integer.parseInt(request.getParameter("rating"));
        String description = request.getParameter("description");
        boolean available = Boolean.parseBoolean(request.getParameter("available"));

        try {
            // Update the movie with all parameters
            MovieDao.updateMovie(movieId, title, genre, rating, description, available);
            
            // Set success message
            request.getSession().setAttribute("success", "Movie updated successfully!");
        } catch (Exception e) {
            // Set error message
            request.getSession().setAttribute("error", "Failed to update movie: " + e.getMessage());
        }

        // Redirect back to the movies page
        response.sendRedirect("movies.jsp");
    }
}
