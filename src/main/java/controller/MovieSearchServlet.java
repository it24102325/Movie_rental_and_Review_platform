package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.MovieDao;
import models.Movie;

import java.io.IOException;
import dsa.MyArrayList;
import java.util.List;

@WebServlet("/search-movie")
public class MovieSearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String keyword = request.getParameter("search");
        if (keyword == null) {
            keyword = "";
        }
        MyArrayList<Movie> movies = (MyArrayList<Movie>) MovieDao.searchMovies(keyword);
        request.setAttribute("movies", movies);
        request.getRequestDispatcher("movie-list.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect("movie-list.jsp");
    }
}
