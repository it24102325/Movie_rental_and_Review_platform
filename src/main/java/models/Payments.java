package models;

public class Payments {
    private String paymentId;
    private String userId;
    private String rentalId;
    private String amount;
    private String paymentDate;

    // Constructor
    public Payments(String paymentId, String userId, String rentalId, String amount, String paymentDate) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.rentalId = rentalId;
        this.amount = amount;
        this.paymentDate = paymentDate;
    }

    // Getters and setters for each field
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRentalId() {
        return rentalId;
    }

    public void setRentalId(String rentalId) {
        this.rentalId = rentalId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }

    // Convert payment to a string format for storage (e.g., for writing to file)
    public String toFileFormat() {
        return this.paymentId + "," + this.userId + "," + this.rentalId + "," + this.amount + "," + this.paymentDate;
    }

    // Static method to convert a line from file storage back into a Payment object
    public static Payment fromFileFormat(String line) {
        String[] data = line.split(",");
        return new Payment(data[0], data[1], data[2], data[3], data[4]);
    }
}
