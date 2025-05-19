<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rental Details</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        .rental-details {
            background-color: #f8f9fa;
            border-radius: 8px;
            padding: 20px;
            margin-bottom: 20px;
        }
        .movie-info {
            display: flex;
            gap: 20px;
            margin-bottom: 20px;
        }
        .movie-poster {
            width: 150px;
            height: 225px;
            object-fit: cover;
            border-radius: 4px;
        }
        .movie-details {
            flex: 1;
        }
        .rental-form {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 20px;
        }
        .btn-checkout {
            background-color: #28a745;
            color: white;
            padding: 12px 30px;
            font-size: 1.1em;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s;
            width: 100%;
        }
        .btn-checkout:hover {
            background-color: #218838;
        }
        .error-message {
            color: #d32f2f;
            margin-top: 10px;
            padding: 10px;
            background-color: #ffebee;
            border-radius: 4px;
        }
        .back-link {
            display: inline-block;
            margin-bottom: 20px;
            color: #007bff;
            text-decoration: none;
        }
        .back-link:hover {
            text-decoration: underline;
        }
        .price-summary {
            background-color: #e9ecef;
            padding: 15px;
            border-radius: 4px;
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <jsp:include page="header.jsp" />
    
    <div class="container">
        <a href="javascript:history.back()" class="back-link">← Back to Movie Details</a>
        
        <c:if test="${not empty error}">
            <div class="error-message">${error}</div>
        </c:if>

        <div class="rental-details">
            <h2>Rental Details</h2>
            <div class="movie-info">
                <c:choose>
                    <c:when test="${not empty sessionScope.rentalMovie.imageFileName}">
                        <img src="images/movies/${sessionScope.rentalMovie.imageFileName}" alt="${sessionScope.rentalMovie.title}" class="movie-poster">
                    </c:when>
                    <c:otherwise>
                        <img src="images/default-movie.jpg" alt="Default Movie Poster" class="movie-poster">
                    </c:otherwise>
                </c:choose>
                <div class="movie-details">
                    <h3>${sessionScope.rentalMovie.title}</h3>
                    <p><strong>Genre:</strong> ${sessionScope.rentalMovie.genre}</p>
                    <p><strong>Rental Period:</strong> 7 days</p>
                    <p><strong>Start Date:</strong> ${startDate}</p>
                    <p><strong>End Date:</strong> ${endDate}</p>
                </div>
            </div>
        </div>

        <div class="rental-form">
            <h3>Rental Information</h3>
            <form action="payment" method="post">
                <input type="hidden" name="movieId" value="${sessionScope.rentalMovie.id}">
                <input type="hidden" name="startDate" value="${startDate}">
                <input type="hidden" name="endDate" value="${endDate}">
                <input type="hidden" name="price" value="${rentalPrice}">
                
                <div class="form-group">
                    <label for="cardName">Name on Card</label>
                    <input type="text" class="form-control" id="cardName" name="cardName" required>
                </div>
                
                <div class="form-group">
                    <label for="cardNumber">Card Number</label>
                    <input type="text" class="form-control" id="cardNumber" name="cardNumber" 
                           maxlength="19" required>
                </div>
                
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <label for="expiryDate">Expiry Date</label>
                        <input type="text" class="form-control" id="expiryDate" name="expiryDate" 
                               placeholder="MM/YY" pattern="(0[1-9]|1[0-2])\/([0-9]{2})" maxlength="5" required>
                    </div>
                    <div class="form-group col-md-6">
                        <label for="cvv">CVV</label>
                        <input type="text" class="form-control" id="cvv" name="cvv" 
                               pattern="[0-9]{3,4}" maxlength="4" required>
                    </div>
                </div>
                
                <div class="price-summary">
                    <h4>Price Summary</h4>
                    <p><strong>Rental Price:</strong> $${rentalPrice}</p>
                    <p><strong>Total Amount:</strong> $${rentalPrice}</p>
                </div>
                
                <button type="submit" class="btn btn-checkout">Proceed to Payment</button>
            </form>
        </div>
    </div>
    
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script>
        // Format card number with spaces
        document.getElementById('cardNumber').addEventListener('input', function(e) {
            let value = e.target.value.replace(/\s+/g, '').replace(/\D/g, '');
            if (value.length > 0) {
                value = value.match(new RegExp('.{1,4}', 'g')).join(' ');
            }
            e.target.value = value;
        });

        // Format expiry date
        document.getElementById('expiryDate').addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length >= 2) {
                value = value.substring(0,2) + '/' + value.substring(2);
            }
            e.target.value = value;
        });
    </script>
</body>
</html> 