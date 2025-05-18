package models;

public class Review {
    private String reviewId;
    private String movieId;
    private String userId;
    private String rating;
    private String feedback;
    private String userName;

    // Constructor
    public Review(String reviewId, String movieId, String userId, String rating, String feedback) {
        this.reviewId = reviewId;
        this.movieId = movieId;
        this.userId = userId;
        this.rating = rating;
        this.feedback = feedback;
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Convert review to a string format for storage (e.g., for writing to file)
    public String toFileFormat() {
        return this.reviewId + "," + this.movieId + "," + this.userId + "," + this.rating + "," + this.feedback;
    }

    // Static method to convert a line from file storage back into a Review object
    public static Review fromFileFormat(String line) {
        String[] data = line.split(",");
        return new Review(data[0], data[1], data[2], data[3], data[4]);
    }
}
