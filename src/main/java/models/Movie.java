package models;

/**
 * Represents a movie in the rental system.
 * This class contains the basic attributes and behaviors of a movie.
 */
public class Movie {
    // Unique identifier for the movie
    private String movieId;
    // Title of the movie
    private String title;
    // Genre/category of the movie (e.g., Action, Drama, Comedy)
    private String genre;
    // Rating of the movie (0-9)
    private int rating;
    // Brief description of the movie's plot
    private String description;
    // Flag indicating if the movie is available for rental
    private boolean available;
    // URL for the movie's poster image
    private String posterUrl;

    // Getters and Setters with documentation
    /**
     * @return The unique identifier of the movie
     */
    public String getMovieId() {
        return movieId;
    }

    /**
     * @param movieId The unique identifier to set
     */
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    /**
     * @return The title of the movie
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title The title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return The genre of the movie
     */
    public String getGenre() {
        return genre;
    }

    /**
     * @param genre The genre to set
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * @return The rating of the movie (0-9)
     */
    public int getRating() {
        return rating;
    }

    /**
     * Sets the movie rating, ensuring it stays within the valid range (0-9)
     * @param rating The rating to set
     */
    public void setRating(int rating) {
        this.rating = Math.max(0, Math.min(9, rating));
    }

    /**
     * @return The description of the movie
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return Whether the movie is available for rental
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * @param available The availability status to set
     */
    public void setAvailable(boolean available) {
        this.available = available;
    }

    /**
     * @return The URL of the movie's poster image
     */
    public String getPosterUrl() {
        return posterUrl;
    }

    /**
     * @param posterUrl The URL of the movie's poster image to set
     */
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
} 