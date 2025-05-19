package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.MovieDao;
import models.Movie;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@WebServlet("/add-movie")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 5 * 1024 * 1024,  // 5MB
        maxRequestSize = 10 * 1024 * 1024 // 10MB
)
public class MovieAddServlet extends HttpServlet {

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
            request.setAttribute("error", "Only admins can add movies.");
            request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
            return;
        }

        String title = request.getParameter("title");
        String genre = request.getParameter("genre");
        String description = request.getParameter("description");
        String priceStr = request.getParameter("price");

        if (title == null || title.trim().isEmpty() || genre == null || genre.trim().isEmpty() || 
            priceStr == null || priceStr.trim().isEmpty()) {
            request.setAttribute("error", "Title, genre, and price are required.");
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

        Part filePart = request.getPart("movieImage");
        String fileName = null;

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
                request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
                return;
            }
        }

        try {
            MovieDao.addMovie(title, genre, description, fileName, price);
            request.setAttribute("success", "Movie added successfully!");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to add movie: " + e.getMessage());
        }

        // Get updated movie list
        List<Movie> movies = MovieDao.getAllMovies();
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
    }

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

        // Get all movies
        List<Movie> movies = MovieDao.getAllMovies();
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("manage-movies.jsp").forward(request, response);
    }
}
