<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental History - MovieRental</title>
    <link rel="stylesheet" href="assets/styles/rental-history.css">
</head>
<body>
<h2>Your Rental History</h2>

<div class="rental-history">
    <c:forEach var="rental" items="${rentals}">
        <div class="rental-item">
            <p>Movie: ${rental.movieTitle}</p>
            <p>Rental Date: ${rental.rentalDate}</p>
            <p>Return Date: ${rental.returnDate}</p>
        </div>
    </c:forEach>
</div>
</body>
</html>
