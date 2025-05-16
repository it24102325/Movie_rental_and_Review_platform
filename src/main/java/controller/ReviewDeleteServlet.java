package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.ReviewDao;

import java.io.IOException;

@WebServlet("/delete-review")
public class ReviewDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String reviewId = request.getParameter("reviewId");

        if (reviewId == null) {
            request.setAttribute("error", "Invalid review ID");
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        // Delete review
        boolean success = ReviewDao.deleteReview(reviewId);
        if (!success) {
            request.setAttribute("error", "Failed to delete review. You may not have permission to delete this review.");
            getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            return;
        }

        response.sendRedirect("reviews.jsp");
    }
}
