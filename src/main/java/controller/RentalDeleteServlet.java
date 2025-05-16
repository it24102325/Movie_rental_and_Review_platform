package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.RentalDao;

import java.io.IOException;

@WebServlet("/delete-rental")
public class RentalDeleteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rentalId = request.getParameter("rentalId");

        // Delete the rental transaction
        boolean isDeleted = RentalDao.deleteRental(rentalId);
        if (isDeleted) {
            response.sendRedirect("rentals.jsp");
        } else {
            request.setAttribute("error", "Failed to delete rental transaction.");
            getServletContext().getRequestDispatcher("/rentals.jsp").forward(request, response);
        }
    }
}
