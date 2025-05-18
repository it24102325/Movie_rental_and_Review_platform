package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.ReviewDao;
import models.Review;
import java.io.IOException;
import java.util.List;

@WebServlet("/manage-reviews")
public class ManageReviewsServlet extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is admin
        if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        try {
            // Get all reviews
            List<Review> reviews = ReviewDao.getAllReviews();
            request.setAttribute("reviews", reviews);
            request.getRequestDispatcher("manage-reviews.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Error loading reviews: " + e.getMessage());
            request.getRequestDispatcher("manage-reviews.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is admin
        if (session.getAttribute("user") == null || !"ADMIN".equals(session.getAttribute("userRole"))) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        String reviewId = request.getParameter("reviewId");
        
        if ("delete".equals(action) && reviewId != null && !reviewId.trim().isEmpty()) {
            try {
                boolean success = ReviewDao.deleteReview(reviewId);
                if (success) {
                    response.sendRedirect("manage-reviews?message=Review deleted successfully");
                } else {
                    response.sendRedirect("manage-reviews?error=Failed to delete review");
                }
            } catch (Exception e) {
                response.sendRedirect("manage-reviews?error=Error deleting review: " + e.getMessage());
            }
        } else {
            response.sendRedirect("manage-reviews?error=Invalid action");
        }
    }
} 