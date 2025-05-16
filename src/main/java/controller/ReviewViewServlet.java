package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.ReviewDao;

import java.io.IOException;

@WebServlet("/view-reviews")
public class ReviewViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String movieId = request.getParameter("movieId");

        // Get reviews for the movie
        request.setAttribute("reviews", ReviewDao.getReviews(movieId));
        getServletContext().getRequestDispatcher("/reviews.jsp").forward(request, response);
    }
}
