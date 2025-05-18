<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment - MovieRental</title>
    <link rel="stylesheet" href="assets/styles/payment.css">
</head>
<body>
<h2>Payment for Rental</h2>
<form action="process-payment" method="post">
    <input type="hidden" name="rentalId" value="${rental.rentalId}">
    <button type="submit">Pay Now</button>
</form>
</body>
</html>
