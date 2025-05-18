package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.UserDao;
import java.io.IOException;

@WebServlet("/register")
public class UserRegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String username = request.getParameter("uname");
        String email = request.getParameter("email");
        String password = request.getParameter("pwd");

        // Validate password
        String validationResult = UserDao.passwordValidation(password);
        if ("length".equals(validationResult)) {
            request.setAttribute("error", "Password should be at least 6 characters.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        } else if ("regex".equals(validationResult)) {
            request.setAttribute("error", "Password must contain at least one uppercase letter and one number.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        try {
            UserDao
                    .registerUser(name, username, email, password);
            response.sendRedirect("login.jsp");
        } catch (IllegalArgumentException e) {
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
