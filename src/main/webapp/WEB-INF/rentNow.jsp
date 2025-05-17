<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%@ page import="models.Movie" %>
<%@ page import="services.MovieDao" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
    
    // Get the movie ID from the request parameter
    String movieId = request.getParameter("id");
    Movie movie = null;
    
    if (movieId != null) {
        movie = MovieDao.getMovieById(movieId);
    }
    
    if (movie == null) {
        response.sendRedirect("movies.jsp");
        return;
    }

    // Calculate rental dates
    LocalDate rentDate = LocalDate.now();
    LocalDate expireDate = rentDate.plusDays(7);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    // Calculate image number (1-10) with better distribution
    int imgNum = Math.abs(movie.getMovieId().hashCode() % 10) + 1;
    // Format the image number with leading zero
    String formattedNum = String.format("%02d", imgNum);
    // Determine image extension based on number
    String imgExt;
    if (imgNum == 1 || imgNum == 8) {
        imgExt = "webp";
    } else if (imgNum == 2) {
        imgExt = "avif";
    } else {
        imgExt = "jpg";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rent Movie - <%= movie.getTitle() %></title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
</head>
<body>
<%

    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MMMM d, yyyy");
    String currentDate = dateFormat.format(new java.util.Date());
%>
<div class="bg-white flex items-center justify-center min-h-screen p-4">
    <div class="border border-black rounded-lg p-6 max-w-4xl w-full flex flex-col items-center space-y-6">
        <h1 class="text-center text-black text-xl font-normal">
            <%= movie.getTitle() %>
        </h1>
        <img src="assets/images/movies_img/img<%= formattedNum %>.<%= imgExt %>" 
             alt="<%= movie.getTitle() %>" 
             class="w-[600px] h-[600px] object-cover border border-black loading" 
             height="600" 
             width="600"
             loading="lazy"
             onload="this.classList.remove('loading'); this.classList.add('loaded')"
             onerror="this.src='assets/images/movies_img/default.jpg'; this.classList.remove('loading'); this.classList.add('loaded')"/>
        <div class="text-center text-black text-base space-y-1">
            <p>Rent Date: <%=currentDate%></p>
            <p>Expire date: <%= expireDate.format(formatter) %></p>
        </div>
        <form action="rentCart.jsp" method="GET" class="w-full">
            <input type="hidden" name="id" value="<%= movie.getMovieId() %>">
            <input type="hidden" name="rentDate" value="<%= rentDate.format(formatter) %>">
            <input type="hidden" name="expireDate" value="<%= expireDate.format(formatter) %>">
            <button class="w-full rounded-full py-2 text-white text-base font-normal bg-blue-600 hover:bg-blue-700" 
                    type="submit">
                Rent
            </button>
        </form>
        <a href="movies.jsp" class="w-full text-center rounded-full py-2 text-blue-600 text-base font-normal border border-blue-600 hover:bg-blue-50">
            Back to Movies
        </a>
    </div>
</div>

<style>
    .loading {
        opacity: 0.6;
        transition: opacity 0.3s ease;
    }
    .loaded {
        opacity: 1;
    }
</style>

<script>
    // Enhanced image loading handling
    document.addEventListener('DOMContentLoaded', function() {
        const img = document.querySelector('img');
        if (img.complete) {
            img.classList.remove('loading');
            img.classList.add('loaded');
        }
    });
</script>
</body>
</html>
