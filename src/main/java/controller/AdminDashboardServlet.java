package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.MovieDao;
import data.MovieArray;
import models.Movie;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("../login.jsp");
            return;
        }

        // Check if user is admin
        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            response.sendRedirect("../login.jsp");
            return;
        }

        try {
            // Load all movies
            Movie[] movies = MovieDao.getAllMovies();
            
            // Check if sort by rating is requested
            String sortByRating = request.getParameter("sortByRating");
            if ("true".equals(sortByRating)) {
                MovieArray movieArray = new MovieArray();
                for (Movie movie : movies) {
                    movieArray.add(movie);
                }
                movieArray.sortByRating();
                movies = movieArray.toArray();
            }
            
            request.setAttribute("movies", Arrays.asList(movies));
            
            // Forward to dashboard.jsp
            request.getRequestDispatcher("/admin/dashboard.jsp").forward(request, response);
        } catch (Exception e) {
            session.setAttribute("error", "Failed to load movies: " + e.getMessage());
            response.sendRedirect("dashboard.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
} 