package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Review;
import services.ReviewDao;
import java.io.IOException;
import java.util.List;

@WebServlet("/add-review")
public class ReviewServlet extends HttpServlet {

    // Handle POST request to add or update a review
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieId = request.getParameter("movieId"); // Movie ID to associate the review with
        String userId = (String) request.getSession().getAttribute("userId"); // Get the user ID from session
        String rating = request.getParameter("rating"); // Rating provided by the user
        String feedback = request.getParameter("feedback"); // Feedback provided by the user

        // Check if the review already exists (you may want to use reviewId or movieId + userId for uniqueness)
        String reviewId = request.getParameter("reviewId"); // Check if it's an existing review

        boolean isSuccess = false;

        // If reviewId is present, update the review, otherwise add a new one
        if (reviewId != null && !reviewId.isEmpty()) {
            // Updating the review
            isSuccess = ReviewDao.updateReview(reviewId, rating, feedback);
        } else {
            // Adding a new review
            isSuccess = ReviewDao.addReview(movieId, userId, rating, feedback);
        }

        // Redirect back to the movie details page with success or error message
        if (isSuccess) {
            response.sendRedirect("movie-details.jsp?movieId=" + movieId + "&success=Review added/updated successfully");
        } else {
            response.sendRedirect("movie-details.jsp?movieId=" + movieId + "&error=Failed to add/update review");
        }
    }

    // Handle GET request to display the review form and existing reviews
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieId = request.getParameter("movieId"); // Get movieId from the request

        // Fetch the existing reviews for the movie
        List<Review> reviews = ReviewDao.getReviewsForMovie(movieId);

        // Set the reviews in the request to display them in the JSP
        request.setAttribute("reviews", reviews);

        // Forward to the movie details page
        request.getRequestDispatcher("movie-details.jsp").forward(request, response);
    }
}
