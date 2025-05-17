<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Cineverse | Free Movies & Reviews</title>
  <link rel="stylesheet" href="assets/styles/index.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap">
</head>
<body>
<header>
  <nav class="navbar">
    <a class="logo" href="#">Cineverse<span>.</span></a>
    <ul class="menu-links">
      <span id="close-menu-btn" class="material-symbols-outlined">close</span>
      <li><a href="home.jsp">Home</a></li>
      <li><a href="#">Reviews</a></li>
      <li><a href="profile.jsp">Profile</a></li>
      <li><a href="#">Contact Us</a></li>
    </ul>
    <span id="hamburger-btn" class="material-symbols-outlined">menu</span>
  </nav>
</header>
<section class="hero-section">
  <div class="content">
    <h2>Discover & Review Your Favorite Movies</h2>
    <p>
      Cineverse is your ultimate platform for watching and reviewing movies. Explore trending films, write reviews, and connect with movie lovers worldwide.
    </p>
    <a href="login.jsp">
      <button>Sign In</button>
    </a>  </div>
</section>
<script>
  const header = document.querySelector("header");
  const hamburgerBtn = document.querySelector("#hamburger-btn");
  const closeMenuBtn = document.querySelector("#close-menu-btn");

  // Toggle mobile menu on hamburger button click
  hamburgerBtn.addEventListener("click", () => header.classList.toggle("show-mobile-menu"));

  // Close mobile menu on close button click
  closeMenuBtn.addEventListener("click", () => hamburgerBtn.click());
</script>
</body>
</html>
