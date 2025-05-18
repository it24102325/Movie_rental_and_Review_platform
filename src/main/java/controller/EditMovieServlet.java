package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.MovieDao;
import models.Movie;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@WebServlet("/edit-movie")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,  // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class EditMovieServlet extends HttpServlet {

    private static final String UPLOAD_DIR = "uploads";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String movieId = request.getParameter("id");
        if (movieId == null || movieId.trim().isEmpty()) {
            response.sendRedirect("manage-movies.jsp");
            return;
        }

        Movie movie = MovieDao.getMovieById(movieId);
        if (movie == null) {
            request.setAttribute("error", "Movie not found.");
            response.sendRedirect("manage-movies.jsp");
            return;
        }

        request.setAttribute("movie", movie);
        request.getRequestDispatcher("edit-movie.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            request.setAttribute("error", "Only admins can edit movies.");
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
            request.getRequestDispatcher("edit-movie.jsp").forward(request, response);
            return;
        }

        // Get existing movie to keep the current image if no new one is uploaded
        Movie existingMovie = MovieDao.getMovieById(movieId);
        String fileName = existingMovie.getImageFileName();

        Part filePart = request.getPart("movieImage");
        if (filePart != null && filePart.getSize() > 0) {
            // Get the file extension
            String submittedFileName = filePart.getSubmittedFileName();
            String fileExtension = submittedFileName.substring(submittedFileName.lastIndexOf("."));
            
            // Generate unique filename
            fileName = UUID.randomUUID().toString() + fileExtension;
            
            // Create uploads directory if it doesn't exist
            String applicationPath = request.getServletContext().getRealPath("");
            String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            // Save the file
            String filePath = uploadPath + File.separator + fileName;
            try (InputStream input = filePart.getInputStream()) {
                Files.copy(input, Paths.get(filePath));
            } catch (IOException e) {
                request.setAttribute("error", "Failed to upload movie poster: " + e.getMessage());
                request.getRequestDispatcher("edit-movie.jsp").forward(request, response);
                return;
            }
        }

        try {
            MovieDao.updateMovie(movieId, title, genre, description, fileName, price);
            request.setAttribute("success", "Movie updated successfully!");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to update movie: " + e.getMessage());
        }

        response.sendRedirect("manage-movies.jsp");
    }
} 