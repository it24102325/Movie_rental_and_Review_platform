package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import services.UserDao;
import java.io.IOException;

@WebServlet("/delete-account")
public class UserDeleteAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        String username = request.getParameter("username");
        if (username == null || username.trim().isEmpty()) {
            request.setAttribute("error", "Username is required.");
            request.getRequestDispatcher("profile.jsp").forward(request, response);
            return;
        }

        try {
            UserDao.deleteUser(username);
            // Invalidate session after successful deletion
            session.invalidate();
            response.sendRedirect("login.jsp");
        } catch (IOException e) {
            request.setAttribute("error", "Failed to delete account: " + e.getMessage());
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        }
    }
}
