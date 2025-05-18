package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.MovieDao;
import java.io.IOException;

@WebServlet("/delete-movie")
public class DeleteMovieServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Redirect GET requests to POST
        doPost(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Check if user is admin
        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            response.sendRedirect("movie-list?error=Access denied. Admin privileges required.");
            return;
        }
        
        String movieId = request.getParameter("id");
        if (movieId == null || movieId.trim().isEmpty()) {
            response.sendRedirect("manage-movies.jsp?error=Invalid movie ID");
            return;
        }
        
        try {
            MovieDao.deleteMovie(movieId);
            response.sendRedirect("add-movie?success=Movie deleted successfully");
        } catch (Exception e) {
            response.sendRedirect("manage-movies.jsp?error=Error deleting movie: " + e.getMessage());
        }
    }
} 