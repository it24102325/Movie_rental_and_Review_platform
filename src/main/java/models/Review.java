package models;

public class Review {
    //review attributes
    private String reviewId;
    private String userId;
    private String movieId;
    private String content;
    private String rating; // e.g., "1", "2", "3", "4", "5"

    // No-args constructor
    public Review() {
    }

    // Constructor
    public Review(String reviewId, String userId, String movieId, String content, String rating) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.movieId = movieId;
        this.content = content;
        this.rating = rating;
    }

    // Getters and Setters
    public String getReviewId() {
        return reviewId;
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    // Convenience methods for integer rating
    public int getRatingAsInt() {
        try {
            return Integer.parseInt(rating);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public void setRating(int rating) {
        this.rating = String.valueOf(rating);
    }
}
