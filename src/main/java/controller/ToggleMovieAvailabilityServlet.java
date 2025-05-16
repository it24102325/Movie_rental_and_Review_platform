package controller;


import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/admin/toggle-movie-availability")
public class ToggleMovieAvailabilityServlet extends HttpServlet {
    private MovieDao movieDao;

    @Override
    public void init() {
        movieDao = new MovieDao();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String movieId = request.getParameter("movieId");
            movieDao.toggleAvailability(movieId);
            request.getSession().setAttribute("success", "Movie availability updated successfully!");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to update movie availability: " + e.getMessage());
        }

        // Redirect back to dashboard
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
} 