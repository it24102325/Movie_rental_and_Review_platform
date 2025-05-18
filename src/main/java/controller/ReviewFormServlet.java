package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.MovieDao;
import models.Movie;
import java.io.IOException;

@WebServlet("/review-form")
public class ReviewFormServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String movieId = request.getParameter("movieId");
        if (movieId == null || movieId.trim().isEmpty()) {
            response.sendRedirect("movie-list?error=Invalid movie ID");
            return;
        }
        
        try {
            Movie movie = MovieDao.getMovieById(movieId);
            if (movie == null) {
                response.sendRedirect("movie-list?error=Movie not found");
                return;
            }
            
            request.setAttribute("movie", movie);
            request.getRequestDispatcher("review-form.jsp").forward(request, response);
        } catch (Exception e) {
            response.sendRedirect("movie-list?error=Error loading movie: " + e.getMessage());
        }
    }
} 