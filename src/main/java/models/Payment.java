package models;

public class Payment {
    private String paymentId;
    private String rentalId;
    private String userId;
    private String amount;
    private String status;
    private String category;
    private String paymentDate;

    // Constructor for file retrieval
    public Payment(String paymentId, String rentalId, String userId, String amount, String status, String paymentDate) {
        this.paymentId = paymentId;
        this.rentalId = rentalId;
        this.userId = userId;
        this.amount = amount;
        this.status = status;
        this.category = category;
        this.paymentDate = paymentDate;
    }

    public Payment(String datum, String datum1, String datum2, String datum3, String datum4, String datum5, String datum6, String datum7) {
    }




    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // Compatibility methods for transactionHistory.jsp
    public String getPaymentID() {
        return paymentId;
    }

    public String getRentalID() {
        return rentalId;
    }

    public boolean getPaymentMethod() {
        return false;
    }

    public boolean getMovieTitle() {
        return false;
    }

    public boolean getCategory() {
        return false;
    }

    public CharSequence getPaymentDate() {
        return null;
    }
}