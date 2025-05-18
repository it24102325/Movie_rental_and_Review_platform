package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.UserDao;
import java.io.IOException;
import java.util.List;
import models.User;

@WebServlet("/manage-users")
public class ManageUsersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ManageUsersServlet: Processing request");
        
        // Test file access first
        System.out.println("\nTesting file access...");
        UserDao.testFileWriting();
        System.out.println("\nContinuing with normal processing...");
        
        // Check if user is admin
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            System.out.println("ManageUsersServlet: No session or user not logged in");
            response.sendRedirect("login.jsp");
            return;
        }

        String userRole = (String) session.getAttribute("userRole");
        if (userRole == null || !userRole.equals("ADMIN")) {
            System.out.println("ManageUsersServlet: User is not an admin");
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // Get all users
            System.out.println("ManageUsersServlet: Fetching users");
            List<User> users = UserDao.getAllUsers();
            
            // Debug information
            System.out.println("ManageUsersServlet: Number of users found: " + (users != null ? users.size() : 0));
            if (users != null) {
                for (User user : users) {
                    System.out.println("User: " + user.getUsername() + ", Role: " + user.getRole());
                }
            }
            
            // Set users list in request attribute
            request.setAttribute("users", users);
            
            // Forward to manage-users.jsp
            System.out.println("ManageUsersServlet: Forwarding to manage-users.jsp");
            request.getRequestDispatcher("manage-users.jsp").forward(request, response);
        } catch (Exception e) {
            // Log the error
            System.err.println("ManageUsersServlet: Error occurred");
            e.printStackTrace();
            request.setAttribute("error", "Error loading users: " + e.getMessage());
            request.getRequestDispatcher("manage-users.jsp").forward(request, response);
        }
    }
}
