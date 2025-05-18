package models;

public class Rental {
    private String rentalId;
    private String userId;
    private String movieId;
    private String movieTitle;
    private String rentalDate;
    private String returnDate;

    // Default constructor
    public Rental() {
    }

    // Constructor with all fields
    public Rental(String rentalId, String userId, String movieId, String rentalDate, String returnDate) {
        this.rentalId = rentalId;
        this.userId = userId;
        this.movieId = movieId;
        this.rentalDate = rentalDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
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

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(String rentalDate) {
        this.rentalDate = rentalDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    // Convert rental to a string format for storage
    public String toFileFormat() {
        return String.join(",", rentalId, userId, movieId, rentalDate, returnDate);
    }

    // Static method to create a Rental from a file line
    public static Rental fromFileFormat(String line) {
        String[] data = line.split(",");
        return new Rental(data[0], data[1], data[2], data[3], data[4]);
    }
}
