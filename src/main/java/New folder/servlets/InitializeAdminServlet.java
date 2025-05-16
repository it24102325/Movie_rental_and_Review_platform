package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.UserDao;

import java.io.IOException;

@WebServlet("/initialize-admin")
public class InitializeAdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        try {
            // Create admin user using the static credentials
            String adminId = UserDao.createUserId();
            boolean success = UserDao.registerUser(new models.User(
                adminId,
                "System Admin",
                "admin",
                "admin@cineverse.com",
                "admin123",
                "admin"
            ));

            if (success) {
                session.setAttribute("success", "Admin account initialized successfully!");
            } else {
                session.setAttribute("error", "Failed to initialize admin account. It may already exist.");
            }
        } catch (Exception e) {
            session.setAttribute("error", "Error initializing admin account: " + e.getMessage());
        }
        
        response.sendRedirect("login.jsp");
    }
} 