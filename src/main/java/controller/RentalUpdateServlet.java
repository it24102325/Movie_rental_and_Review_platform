package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.RentalDao;

import java.io.IOException;

@WebServlet("/update-rental-status")
public class RentalUpdateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rentalId = request.getParameter("rentalId");
        String status = request.getParameter("status");

        // Update the rental status
        boolean isUpdated = RentalDao.updateRentalStatus(rentalId, status);
        if (isUpdated) {
            response.sendRedirect("rentals.jsp");
        } else {
            request.setAttribute("error", "Failed to update rental status.");
            getServletContext().getRequestDispatcher("/rentals.jsp").forward(request, response);
        }
    }
}
