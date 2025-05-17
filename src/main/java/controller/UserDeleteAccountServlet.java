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

@WebServlet("/delete")
public class UserDeleteAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session != null  && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            boolean isDeleted = UserDao.deleteUser(user.getEmail());
            session.invalidate();

            if (isDeleted) {
                response.sendRedirect("delete-account.jsp");
            }else{
                request.setAttribute("error", "Failed to delete account!");
                doGet(request, response);
            }
        }else {
            response.sendRedirect("login.jsp");
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}
