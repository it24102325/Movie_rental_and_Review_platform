package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/admin/delete-movie")
public class DeleteMovieServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String movieId = request.getParameter("movieId");
            MovieDao.deleteMovie(movieId);
            request.getSession().setAttribute("success", "Movie deleted successfully!");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to delete movie: " + e.getMessage());
        }

        // Redirect back to dashboard
        response.sendRedirect(request.getContextPath() + "/admin/dashboard");
    }
} 