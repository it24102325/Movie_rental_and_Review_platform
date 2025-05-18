package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Movie;
import services.MovieDao;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/rent-movie")
public class RentMovieServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String movieId = request.getParameter("movieId");
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
            
            // Set movie and rental details in session
            session.setAttribute("rentalMovie", movie);
            
            // Calculate rental dates
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7); // Default 7-day rental
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            request.setAttribute("startDate", startDate.format(formatter));
            request.setAttribute("endDate", endDate.format(formatter));
            request.setAttribute("rentalPrice", movie.getPrice());
            
            // Forward to rental form
            request.getRequestDispatcher("rental-form.jsp").forward(request, response);
            
        } catch (Exception e) {
            System.err.println("Error processing rental: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error processing rental: " + e.getMessage());
            response.sendRedirect("movie-list");
        }
    }
} 