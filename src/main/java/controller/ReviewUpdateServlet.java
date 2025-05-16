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
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/update-review", "/get-review"})
public class ReviewUpdateServlet extends HttpServlet {
    private final Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String reviewId = request.getParameter("reviewId");
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Review review = ReviewDao.getReviewById(reviewId);
        if (review == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Check if the user owns this review
        User currentUser = (User) session.getAttribute("user");
        if (!review.getUserId().equals(currentUser.getUserId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(gson.toJson(review));
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String reviewId = request.getParameter("reviewId");
        String content = request.getParameter("content");
        String rating = request.getParameter("rating");
        User currentUser = (User) session.getAttribute("user");

        // Validate input
        if (reviewId == null || content == null || rating == null || content.trim().isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Get existing review
        Review existingReview = ReviewDao.getReviewById(reviewId);
        if (existingReview == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // Check if the user owns this review
        if (!existingReview.getUserId().equals(currentUser.getUserId())) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        // Update the review while preserving movieId and userId
        existingReview.setContent(content.trim());
        existingReview.setRating(rating);

        boolean success = ReviewDao.updateReview(existingReview);
        
        JsonObject jsonResponse = new JsonObject();
        jsonResponse.addProperty("success", success);
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print(jsonResponse.toString());
        out.flush();
    }
}
