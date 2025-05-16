package servlets;

import models.Review;
import models.User;
import services.ReviewDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/manage-reviews")
public class ManageReviewsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Check if user is admin
        User currentUser = (User) request.getSession().getAttribute("user");
        if (currentUser == null || !currentUser.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get all reviews
        List<Review> reviews = ReviewDao.getAllReviews();
        request.setAttribute("reviews", reviews);
        request.getRequestDispatcher("manage-reviews.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("delete".equals(action)) {
            String reviewId = request.getParameter("reviewId");
            if (reviewId != null && !reviewId.isEmpty()) {
                ReviewDao.deleteReview(reviewId);
            }
        }
        
        // Redirect back to manage reviews page
        response.sendRedirect("manage-reviews");
    }
} 