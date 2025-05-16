package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import models.Movie;
import services.MovieDao;

@WebServlet("/admin/get-movie")
public class GetMovieServlet extends HttpServlet {
    private Gson gson;

    @Override
    public void init() {
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String movieId = request.getParameter("id");
            Movie movie = MovieDao.getMovieById(movieId);
            
            if (movie != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(movie));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Movie not found");
            }
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
} 