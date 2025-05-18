package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.MovieDao;
import services.RentalDao;
import models.Movie;
import models.User;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Get payment details
        String cardName = request.getParameter("cardName");
        String cardNumber = request.getParameter("cardNumber");
        String expiryDate = request.getParameter("expiryDate");
        String cvv = request.getParameter("cvv");
        
        // Get rental details from session
        Movie movie = (Movie) session.getAttribute("rentalMovie");
        if (movie == null) {
            response.sendRedirect("movie-list");
            return;
        }
        
        try {
            // Here you would typically:
            // 1. Validate payment details
            // 2. Process payment through a payment gateway
            
            // Create the rental record
            RentalDao.rentMovie(user.getUserId(), movie.getId());
            
            // Set success message
            session.setAttribute("paymentSuccess", true);
            
            // Calculate rental dates
            LocalDate startDate = LocalDate.now();
            LocalDate endDate = startDate.plusDays(7);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            
            // Create confirmation message
            String confirmationMessage = String.format(
                "Your rental for '%s' has been confirmed! Rental period: %s to %s",
                movie.getTitle(),
                startDate.format(formatter),
                endDate.format(formatter)
            );
            session.setAttribute("rentalConfirmation", confirmationMessage);
            
            // Redirect to confirmation page
            response.sendRedirect("rental-confirmation");
            
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("error", "Error processing payment: " + e.getMessage());
            request.getRequestDispatcher("rental-form.jsp").forward(request, response);
        }
    }
}
