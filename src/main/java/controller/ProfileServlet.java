package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import models.Rental;
import services.RentalDao;

import java.io.IOException;
import java.util.List;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Debug logging
        System.out.println("Loading rentals for user: " + user.getUserId());
        
        // Get user's rental history
        List<Rental> rentals = RentalDao.getUserRentals(user.getUserId());
        
        // Debug logging
        System.out.println("Found " + rentals.size() + " rentals for user");
        for (Rental rental : rentals) {
            System.out.println("Rental: " + rental.getRentalId() + 
                             ", Movie: " + rental.getMovieTitle() + 
                             ", Date: " + rental.getRentalDate());
        }
        
        request.setAttribute("rentals", rentals);
        
        // Forward to profile page
        request.getRequestDispatcher("profile.jsp").forward(request, response);
    }
} 