package controller;

import dao.MovieDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/admin/dashboard")
public class AdminDashboardServlet extends HttpServlet {
    private MovieDao movieDao;

    @Override
    public void init() {
        movieDao = new MovieDao();
    }

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
            request.setAttribute("movies", movieDao.getAllMovies());
            
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