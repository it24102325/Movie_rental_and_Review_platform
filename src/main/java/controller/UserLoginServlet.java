package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import services.UserDao;
import models.User;

import java.io.IOException;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {

    // Handles login requests
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the username and password from the request
        String username = request.getParameter("uname");
        String password = request.getParameter("pwd");

        // Authenticate the user
        User user = UserDao.authenticateUser(username, password);  // Call to UserDao to validate the user

        if (user != null) {
            // Create a session for the user
            HttpSession session = request.getSession();
            session.setAttribute("user", user);  // Store user in the session
            session.setAttribute("userRole", user.getRole());  // Store user role in the session

            // Redirect based on role
            if (user.getRole().equals("ADMIN")) {
                response.sendRedirect("admin-dashboard.jsp");
            } else {
                response.sendRedirect("home.jsp");
            }
        } else {
            // Authentication failed - invalid credentials
            request.setAttribute("error", "Invalid username or password.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }

    // Redirects to login page for GET requests
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
