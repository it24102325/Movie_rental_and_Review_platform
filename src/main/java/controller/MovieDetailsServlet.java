package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;
import services.ReviewDao;
import models.Movie;
import models.Review;
import java.io.IOException;
import java.util.List;

@WebServlet("/movie-details")
public class MovieDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String movieId = request.getParameter("id");
        
        if (movieId == null || movieId.trim().isEmpty()) {
            response.sendRedirect("movie-list");
            return;
        }
        
        try {
            Movie movie = MovieDao.getMovieById(movieId);
            if (movie == null) {
                request.setAttribute("error", "Movie not found");
                response.sendRedirect("movie-list");
                return;
            }
            
            // Fetch reviews for this movie
            List<Review> reviews = ReviewDao.getReviewsForMovie(movieId);
            
            request.setAttribute("movie", movie);
            request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("movie-details.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error loading movie details: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error loading movie details: " + e.getMessage());
            response.sendRedirect("movie-list");
        }
    }
} 