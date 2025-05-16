package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.ReviewDao;
import models.User;
import models.Review;

import java.io.IOException;

@WebServlet("/submit-review")
public class ReviewServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            try {
                String movieId = request.getParameter("movieId");
                String content = request.getParameter("content");
                int rating = Integer.parseInt(request.getParameter("rating"));
                String userId = ((User) session.getAttribute("user")).getUserId();

                Review review = new Review();
                review.setMovieId(movieId);
                review.setUserId(userId);
                review.setContent(content);
                review.setRating(rating);

                // Add review
                if (ReviewDao.submitReview(review)) {
                    response.sendRedirect("reviews.jsp");
                } else {
                    request.setAttribute("error", "Failed to submit review. Please try again.");
                    request.getRequestDispatcher("reviews.jsp").forward(request, response);
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid rating value. Please try again.");
                request.getRequestDispatcher("reviews.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
