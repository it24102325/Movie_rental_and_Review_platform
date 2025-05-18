<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");

    if (session.getAttribute("user") == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile - MovieRental</title>
    <link rel="stylesheet" href="assets/styles/profile.css">
    <style>
        .rental-history {
            margin-top: 2rem;
            padding: 1rem;
            background: #f8f9fa;
            border-radius: 8px;
        }
        
        .rental-item {
            background: white;
            padding: 1rem;
            margin-bottom: 1rem;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        
        .rental-item h4 {
            color: #2c3e50;
            margin: 0 0 0.5rem 0;
        }
        
        .rental-details {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 1rem;
            margin-top: 0.5rem;
        }
        
        .rental-detail {
            display: flex;
            flex-direction: column;
        }
        
        .rental-detail strong {
            color: #666;
            font-size: 0.9rem;
        }
        
        .rental-detail span {
            color: #2c3e50;
            font-size: 1rem;
        }
        
        .status-active {
            color: #27ae60;
            font-weight: bold;
        }
        
        .status-returned {
            color: #7f8c8d;
        }
    </style>
</head>
<body>
<div class="profile-container">
    <header class="profile-header">
        <h1>Welcome, ${sessionScope.user.name}</h1>
        <a href="home.jsp" class="back-link">← Back to Home</a>
    </header>

    <div class="profile-info">
        <p><strong>Email:</strong> ${sessionScope.user.email}</p>
        <p><strong>Role:</strong> ${sessionScope.user.role}</p>
    </div>

    <div class="profile-actions">
        <a href="edit-account.jsp" class="btn btn-primary">Edit Account</a>
        <a href="delete-account.jsp" class="btn btn-danger">Delete Account</a>
        <a href="logout" class="btn btn-logout">Logout</a>
    </div>

    <div class="rental-history">
        <h3>Your Rental History</h3>
        <c:if test="${empty rentals}">
            <p>No rentals found.</p>
        </c:if>
        <c:forEach var="rental" items="${rentals}">
            <div class="rental-item">
                <h4>${rental.movieTitle}</h4>
                <div class="rental-details">
                    <div class="rental-detail">
                        <strong>Rental ID</strong>
                        <span>${rental.rentalId}</span>
                    </div>
                    <div class="rental-detail">
                        <strong>Rental Date</strong>
                        <span>${rental.rentalDate}</span>
                    </div>
                    <div class="rental-detail">
                        <strong>Status</strong>
                        <span class="${rental.returnDate == 'Not yet returned' ? 'status-active' : 'status-returned'}">
                            ${rental.returnDate == 'Not yet returned' ? 'Currently Rented' : 'Returned'}
                        </span>
                    </div>
                    <div class="rental-detail">
                        <strong>Return Date</strong>
                        <span>${rental.returnDate}</span>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>
</body>
</html>
