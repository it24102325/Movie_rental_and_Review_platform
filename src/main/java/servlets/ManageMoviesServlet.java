package servlets;

import models.Movie;
import models.User;
import services.MovieDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/manage-movies")
public class ManageMoviesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // Get all movies
        List<Movie> movies = MovieDao.getAllMovies();
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("/admin/manage-movies.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            String movieId = request.getParameter("movieId");
            if (movieId != null && !movieId.isEmpty()) {
                MovieDao.deleteMovie(movieId);
            }
        }
        
        // Redirect back to manage movies page
        response.sendRedirect(request.getContextPath() + "/admin/manage-movies");
    }
} 