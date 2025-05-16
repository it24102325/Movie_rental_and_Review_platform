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

@WebServlet("/rent-movie")
public class RentalServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            String movieId = request.getParameter("movieId");
            String rentalDate = request.getParameter("rentalDate");

            // Get the user from the session
            User user = (User) session.getAttribute("user");

            // Rent the movie
            boolean isRented = RentalDao.rentMovie(movieId, rentalDate, user);
            if (isRented) {
                response.sendRedirect("rentals.jsp"); // Redirect to rentals page
            } else {
                request.setAttribute("error", "Failed to rent the movie.");
                getServletContext().getRequestDispatcher("/movies.jsp").forward(request, response);
            }
        } else {
            response.sendRedirect("login.jsp");
        }
    }
}
