<!-- header.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<style>
    .navbar {
        background-color: #2c2c2c;
        padding: 10px 30px;
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .navbar .logo {
        font-size: 24px;
        font-weight: bold;
        color: #ffffff;
    }


    .navbar ul {
        list-style: none;
        display: flex;
        margin: 0;
        padding: 0;
    }

    .navbar ul li {
        margin-left: 20px;
    }

    .navbar ul li a {
        color: white;
        text-decoration: none;
        font-weight: 500;
        transition: color 0.3s;
    }

    .navbar ul li a:hover {
        color: #007bff;
    }
</style>

<div class="navbar">
    <div class="logo">Cine<span>verse</span>.</div>
    <ul>
        <li><a href="home.jsp">Home</a></li>
        <li><a href="movies.jsp">Movies</a></li>
        <li><a href="reviews.jsp">Reviews</a></li>
        <li><a href="profile.jsp">Profile</a></li>
        <li><a href="contact.jsp">Contact Us</a></li>
        <li><a href="logout.jsp">Logout</a></li>
        <li><a href="payments">Payment</a></li>
    </ul>
</div>
