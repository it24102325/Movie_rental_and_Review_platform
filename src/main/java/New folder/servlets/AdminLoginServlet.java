package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.UserDao;

import java.io.IOException;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        System.out.println("Admin login attempt - Username: " + username);

        // Basic input validation
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            System.out.println("Admin login failed: Empty credentials");
            request.setAttribute("error", "Username and password cannot be empty.");
            getServletContext().getRequestDispatcher("/admin-login.jsp").forward(request, response);
            return;
        }

        // Authenticate the admin using the UserDao class
        User admin = UserDao.loginUser(username, password);
        System.out.println("Admin login result: " + (admin != null ? "User found" : "User not found"));

        if (admin != null && admin.getRole().equalsIgnoreCase("admin")) {
            System.out.println("Admin role verified: " + admin.getRole());
            // Create a session and store admin object in the session
            HttpSession session = request.getSession();
            session.setAttribute("user", admin);
            session.setAttribute("role", "admin");
            session.setAttribute("username", admin.getUsername());
            System.out.println("Admin session created, redirecting to dashboard");
            response.sendRedirect(request.getContextPath() + "/admin/dashboard");
        } else {
            System.out.println("Admin login failed: Invalid credentials or not an admin");
            request.setAttribute("error", "Invalid admin credentials.");
            getServletContext().getRequestDispatcher("/admin-login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/admin-login.jsp").forward(request, response);
    }
} 