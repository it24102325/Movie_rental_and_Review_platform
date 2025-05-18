package models;

public class Movie {
    private String movieId;
    private String title;
    private String genre;
    private String description;
    private String imageFileName;  // stores uploaded image filename
    private double price;  // stores movie rental price

    public Movie(String movieId, String title, String genre, String description, String imageFileName, double price) {
        this.movieId = movieId;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.imageFileName = imageFileName;
        this.price = price;
    }

    // Getters and setters
    public String getMovieId() {
        return movieId;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageFileName() {
        return imageFileName;
    }
    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }

    public String getId() {
        return movieId;
    }

    // Format movie object as CSV line for storage
    public String toFileFormat() {
        return movieId + "," + title + "," + genre + "," + description + "," + 
               (imageFileName == null ? "" : imageFileName) + "," + price;
    }

    // Parse CSV line to Movie object
    public static Movie fromFileFormat(String line) {
        String[] parts = line.split(",", 6); // limit 6 parts to handle commas in description safely
        String image = parts.length > 4 ? parts[4] : "";
        double price = parts.length > 5 ? Double.parseDouble(parts[5]) : 0.0;
        return new Movie(parts[0], parts[1], parts[2], parts[3], image, price);
    }
}
