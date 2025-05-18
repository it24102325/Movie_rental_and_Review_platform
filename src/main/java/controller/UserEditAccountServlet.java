package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.UserDao;
import models.User;
import java.io.IOException;

@WebServlet("/edit-account")
public class UserEditAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get the logged-in user
        User loggedInUser = (User) session.getAttribute("user");
        String username = loggedInUser.getUsername();
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = loggedInUser.getRole(); // Keep the same role

        if (email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Email is required.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        try {
            UserDao.updateUser(username, email, password, role);
            // Update the session with new user details
            loggedInUser.setEmail(email);
            if (password != null && !password.trim().isEmpty()) {
                loggedInUser.setPassword(password);
            }
            session.setAttribute("user", loggedInUser);

            request.setAttribute("success", "Account updated successfully!");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (IOException e) {
            request.setAttribute("error", "Failed to update account: " + e.getMessage());
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("edit-account.jsp").forward(request, response);
    }
}
