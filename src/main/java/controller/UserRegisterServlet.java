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
        String password = request.getParameter("pwd");
        String email = request.getParameter("email");

        boolean isLengthError = UserDao.passwordValidation(password).contains("length");
        boolean isRegexError = UserDao.passwordValidation(password).contains("regex");

        if(isLengthError){
            request.setAttribute("error","Password should be at least 6 characters");
            doGet(request,response);
        }else if(isRegexError){
            request.setAttribute("error","Password must contain at least one uppercase letter and one number.");
            doGet(request,response);
        }else{
            if(UserDao.isUserExist(email)){
                request.setAttribute("error","Email already exist");
                doGet(request,response);
            }else{
                UserDao.getRegisterDetails(name, username, email, password);
                response.sendRedirect("login.jsp");
            }
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(request,response);
    }
}
