package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.MovieDao;
import models.Movie;
import java.io.*;

@WebServlet("/update-movie")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class MovieUpdateServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            request.setAttribute("error", "Only admins can update movies.");
            request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
            return;
        }

        String movieId = request.getParameter("movieId");
        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");

        if (movieId == null || title == null || title.trim().isEmpty() || 
            genre == null || genre.trim().isEmpty() || priceStr == null || priceStr.trim().isEmpty()) {
            request.setAttribute("error", "Movie ID, title, genre, and price are required.");
            request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
            return;
        }

        double price;
        try {
            price = Double.parseDouble(priceStr);
            if (price < 0) {
                throw new NumberFormatException("Price cannot be negative");
            }
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Invalid price format.");
            request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
            return;
        }

        try {
            MovieDao.updateMovie(movieId, title, genre, description, null, price);
            request.setAttribute("success", "Movie updated successfully!");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to update movie: " + e.getMessage());
        }

        response.sendRedirect("manage-movies.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Show existing movie data on update form
        String movieId = request.getParameter("movieId");
        if (movieId == null || movieId.trim().isEmpty()) {
            response.sendRedirect("movie-list.jsp");
            return;
        }
        Movie movie = MovieDao.getMovieById(movieId);
        if (movie == null) {
            response.sendRedirect("movie-list.jsp");
            return;
        }
        request.setAttribute("movie", movie);
        request.getRequestDispatcher("edit-movie.jsp").forward(request, response);
    }
}
