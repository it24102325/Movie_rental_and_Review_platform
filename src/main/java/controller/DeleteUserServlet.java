package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.UserDao;
import java.io.IOException;

@WebServlet("/delete-user")
public class DeleteUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            request.setAttribute("error", "Only admins can delete users.");
            request.getRequestDispatcher("manage-users.jsp").forward(request, response);
            return;
        }

        String username = request.getParameter("id");
        if (username == null || username.trim().isEmpty()) {
            response.sendRedirect("manage-users.jsp");
            return;
        }

        try {
            UserDao.deleteUser(username);
            request.setAttribute("success", "User deleted successfully!");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to delete user: " + e.getMessage());
        }

        response.sendRedirect("manage-users.jsp");
    }
} 