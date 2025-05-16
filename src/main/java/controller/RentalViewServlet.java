package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.RentalDao;

import java.io.IOException;
import java.util.List;

@WebServlet("/view-rentals")
public class RentalViewServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if the user is logged in
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            // Retrieve the user from the session
            User user = (User) session.getAttribute("user");

            // Fetch rented movies for the user
            List<String> rentedMovies = RentalDao.getRentedMovies(user.getUserId());

            // If no rented movies found, set a message
            if (rentedMovies.isEmpty()) {
                request.setAttribute("message", "You have no rented movies.");
            } else {
                request.setAttribute("rentals", rentedMovies);
            }

            // Forward the request to the rentals.jsp page
            getServletContext().getRequestDispatcher("/rentals.jsp").forward(request, response);
        } else {
            // Redirect to login page if the user is not logged in
            response.sendRedirect("login.jsp");
        }
    }
}
