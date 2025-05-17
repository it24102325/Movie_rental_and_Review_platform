<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page session="true" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    if(session.getAttribute("user") == null){
        response.sendRedirect("login.jsp");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>home</title>
    <link rel="stylesheet" href="assets/styles/home.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
</head>
<body>
<header>
    <nav class="navbar">
        <a class="logo" href="index.jsp">Cineverse<span>.</span></a>
        <ul class="menu-links">
            <span id="close-menu-btn" class="material-icons">close</span>
            <li><a href="#home">Home</a></li>
            <li><a href="#reviews">Reviews</a></li>
            <li><a href="profile.jsp">Profile</a></li>
            <li><a href="#contact">Contact Us</a></li>
        </ul>
        <span id="hamburger-btn" class="material-icons">menu</span>
    </nav>
</header>

<section id="home" class="hero-section">
    <div class="content">
        <h2>Discover & Review Your Favorite Movies</h2>
        <form action="search.jsp" method="GET">
            <input type="text" name="query" placeholder="Search Movies">
            <button class="search-btn" type="submit">Search</button>
        </form>
    </div>
</section>


<script>
    const header = document.querySelector("header");
    const hamburgerBtn = document.querySelector("#hamburger-btn");
    const closeMenuBtn = document.querySelector("#close-menu-btn");

    hamburgerBtn.addEventListener("click", () => header.classList.toggle("show-mobile-menu"));
    closeMenuBtn.addEventListener("click", () => header.classList.remove("show-mobile-menu"));
</script>
</body>
</html>
