package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/rental-confirmation")
public class RentalConfirmationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Check if user is logged in
        if (session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Check if payment was successful
        if (session.getAttribute("paymentSuccess") == null) {
            response.sendRedirect("movie-list");
            return;
        }
        
        // Forward to confirmation page
        request.getRequestDispatcher("rental-confirmation.jsp").forward(request, response);
    }
} 