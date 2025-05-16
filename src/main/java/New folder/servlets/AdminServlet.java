package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register-admin")
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String adminUsername = request.getParameter("username");
        String adminPassword = request.getParameter("password");

        // For simplicity, only check if the username and password match a hardcoded value
        if ("admin1".equals(adminUsername) && "admin123".equals(adminPassword)) {
            // Logic for registering the admin (in a real app, store the admin in a database)
            response.sendRedirect("admin/dashboard.jsp");
        } else {
            request.setAttribute("error", "Invalid admin credentials");
            getServletContext().getRequestDispatcher("/admin-register.jsp").forward(request, response);
        }
    }
}
