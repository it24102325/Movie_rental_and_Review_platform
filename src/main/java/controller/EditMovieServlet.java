package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/admin/edit-movie")
public class EditMovieServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            // Get form data
            String movieId = request.getParameter("movieId");
            String title = request.getParameter("title");
            String genre = request.getParameter("genre");
            int rating = Integer.parseInt(request.getParameter("rating"));
            String description = request.getParameter("description");
            boolean available = request.getParameter("available") != null;

            // Validate input
            if (title == null || title.trim().isEmpty() ||
                genre == null || genre.trim().isEmpty() ||
                description == null || description.trim().isEmpty()) {
                request.getSession().setAttribute("error", "All fields are required");
                response.sendRedirect(request.getContextPath() + "/admin/dashboard");
                return;
            }

            // Update the movie
            MovieDao.updateMovie(movieId, title.trim(), genre.trim(), rating, description.trim(), available);
            
            // Set success message and redirect back to dashboard
            request.getSession().setAttribute("success", "Movie updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } catch (Exception e) {
            // Log the error and redirect with error message
            e.printStackTrace();
            request.getSession().setAttribute("error", "Failed to update movie: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        }
    }
} 