package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;

import java.io.IOException;

@WebServlet("/search-movie")
public class MovieSearchServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String query = request.getParameter("query");
        if (query != null && !query.isEmpty()) {
            request.setAttribute("movies", MovieDao.searchMovies(query));
            getServletContext().getRequestDispatcher("/movies.jsp").forward(request, response);
        } else {
            response.sendRedirect("movies.jsp");
        }
    }
}
