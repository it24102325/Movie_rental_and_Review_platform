package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.UserDao;
import models.User;
import java.io.IOException;

@WebServlet("/edit-user")
public class EditUserServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("id");
        if (username == null || username.trim().isEmpty()) {
            response.sendRedirect("manage-users.jsp");
            return;
        }

        User user = UserDao.getUserByUsername(username);
        if (user == null) {
            request.setAttribute("error", "User not found.");
            response.sendRedirect("manage-users.jsp");
            return;
        }

        request.setAttribute("user", user);
        request.getRequestDispatcher("edit-user.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            request.setAttribute("error", "Only admins can edit users.");
            request.getRequestDispatcher("manage-users.jsp").forward(request, response);
            return;
        }

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role");

        if (username == null || username.trim().isEmpty() || 
            email == null || email.trim().isEmpty() || 
            role == null || role.trim().isEmpty()) {
            request.setAttribute("error", "Username, email, and role are required.");
            request.getRequestDispatcher("edit-user.jsp").forward(request, response);
            return;
        }

        try {
            UserDao.updateUser(username, email, password, role);
            request.setAttribute("success", "User updated successfully!");
        } catch (Exception e) {
            request.setAttribute("error", "Failed to update user: " + e.getMessage());
        }

        response.sendRedirect("manage-users.jsp");
    }
} 