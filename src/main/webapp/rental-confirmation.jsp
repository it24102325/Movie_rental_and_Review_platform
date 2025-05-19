<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental Confirmation</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .confirmation-box {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 30px;
            text-align: center;
            margin-top: 50px;
        }
        .success-icon {
            color: #28a745;
            font-size: 48px;
            margin-bottom: 20px;
        }
        .confirmation-title {
            color: #28a745;
            font-size: 24px;
            margin-bottom: 20px;
        }
        .confirmation-message {
            color: #666;
            font-size: 18px;
            margin-bottom: 30px;
        }
        .btn-primary {
            background-color: #007bff;
            color: white;
            padding: 12px 30px;
            font-size: 1.1em;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
            text-decoration: none;
            display: inline-block;
        }
        .btn-primary:hover {
            background-color: #0056b3;
            text-decoration: none;
            color: white;
        }
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <c:if test="${sessionScope.paymentSuccess}">
            <div class="confirmation-box">
                <div class="success-icon">✓</div>
                <h2 class="confirmation-title">Rental Confirmed!</h2>
                <p class="confirmation-message">${sessionScope.rentalConfirmation}</p>
                <p>Thank you for your rental. You can now enjoy your movie!</p>
                <div style="margin-top: 30px;">
                    <a href="movie-list" class="btn btn-primary">Back to Movies</a>
                </div>
            </div>
        </c:if>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html> 