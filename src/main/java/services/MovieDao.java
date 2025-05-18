package services;

import models.Movie;
import models.Review;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class MovieDao {
    // Path to movies' data file
    private static final String filePath = "C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\movies.txt";

    static {
        // Create data directory if it doesn't exist
        File dataDir = new File("C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\movies.text");
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }
        
        // Create movies.txt if it doesn't exist
        File moviesFile = new File(filePath);
        if (!moviesFile.exists()) {
            try {
                moviesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Add a new movie
    public static void addMovie(String title, String genre, String description, String imageFileName, double price) {
        String movieId = "M" + System.currentTimeMillis();
        Movie movie = new Movie(movieId, title, genre, description, imageFileName, price);
        saveMovie(movie);
    }

    // Save movie to file
    private static void saveMovie(Movie movie) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(movie.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Get all movies from the file
    public static List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();
        File moviesFile = new File(filePath);
        
        if (!moviesFile.exists()) {
            System.err.println("Error: movies.txt does not exist at: " + filePath);
            return movies;
        }
        
        if (moviesFile.length() == 0) {
            System.out.println("Warning: movies.txt is empty");
            return movies;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            System.out.println("Reading movies from file: " + filePath);
            String line;
            int lineNumber = 0;
            
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                System.out.println("Reading line " + lineNumber + ": " + line);
                
                if (line.trim().isEmpty()) {
                    System.out.println("Skipping empty line " + lineNumber);
                    continue;
                }
                
                String[] movieData = line.split(",");
                System.out.println("Line " + lineNumber + " has " + movieData.length + " fields");
                
                if (movieData.length >= 4) {
                    try {
                        // Format: movieId,title,genre,description,posterPath,price
                        String movieId = movieData[0].trim();
                        String title = movieData[1].trim();
                        String genre = movieData[2].trim();
                        String description = movieData[3].trim();
                        String imageFileName = "";
                        double price = 0.0;
                        
                        // Handle image file name if it exists
                        if (movieData.length > 4) {
                            imageFileName = movieData[4].trim();
                            // Remove any path prefixes if they exist
                            if (imageFileName.contains("/")) {
                                imageFileName = imageFileName.substring(imageFileName.lastIndexOf("/") + 1);
                            }
                        }
                        
                        // Handle price if it exists
                        if (movieData.length > 5) {
                            try {
                                price = Double.parseDouble(movieData[5].trim());
                            } catch (NumberFormatException e) {
                                System.err.println("Invalid price format at line " + lineNumber + ": " + movieData[5]);
                            }
                        }
                        
                        Movie movie = new Movie(movieId, title, genre, description, imageFileName, price);
                        System.out.println("Created movie object: " + movie.getTitle() + 
                                         ", Image: " + movie.getImageFileName() + 
                                         ", Price: " + movie.getPrice());
                        movies.add(movie);
                    } catch (Exception e) {
                        System.err.println("Error processing movie at line " + lineNumber + ": " + e.getMessage());
                    }
                } else {
                    System.err.println("Invalid movie data format at line " + lineNumber + 
                                     ". Expected at least 4 fields, got " + movieData.length);
                }
            }
            System.out.println("Total movies loaded: " + movies.size());
        } catch (IOException e) {
            System.err.println("Error reading movies file: " + e.getMessage());
            e.printStackTrace();
        }
        return movies;
    }

    // Get a movie by ID
    public static Movie getMovieById(String id) {
        List<Movie> movies = getAllMovies();
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                return movie;
            }
        }
        return null;
    }

    // Search movies by keyword in title or genre
    public static List<Movie> searchMovies(String keyword) {
        List<Movie> movies = new ArrayList<>();
        if (keyword == null) keyword = "";
        keyword = keyword.toLowerCase();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = Movie.fromFileFormat(line);
                if (movie.getTitle().toLowerCase().contains(keyword) || movie.getGenre().toLowerCase().contains(keyword)) {
                    movies.add(movie);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies;
    }

    // Update a movie
    public static void updateMovie(String id, String title, String genre, String description, String imageFileName, double price) throws IOException {
        List<Movie> movies = getAllMovies();
        boolean found = false;
        
        for (Movie movie : movies) {
            if (movie.getId().equals(id)) {
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setDescription(description);
                movie.setImageFileName(imageFileName);
                movie.setPrice(price);
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new IOException("Movie not found with ID: " + id);
        }
        
        // Write updated movies back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Movie movie : movies) {
                writer.write(String.format("%s,%s,%s,%s,%s,%.2f\n",
                    movie.getId(),
                    movie.getTitle(),
                    movie.getGenre(),
                    movie.getDescription(),
                    movie.getImageFileName(),
                    movie.getPrice()));
            }
        }
    }

    // Delete a movie
    public static void deleteMovie(String movieId) {
        List<String> movies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Movie movie = Movie.fromFileFormat(line);
                if (!movie.getMovieId().equals(movieId)) {
                    movies.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String movie : movies) {
                writer.write(movie);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Calculate average rating for a movie
    public static double getAverageRating(String movieId) {
        List<Review> reviews = ReviewDao.getReviewsForMovie(movieId);
        if (reviews.isEmpty()) {
            return 0.0;
        }
        
        double sum = 0.0;
        for (Review review : reviews) {
            sum += Double.parseDouble(review.getRating());
        }
        return sum / reviews.size();
    }

    // Get movies sorted by rating
    public static List<Movie> getMoviesSortedByRating(boolean ascending) {
        List<Movie> movies = getAllMovies();
        Map<String, Double> movieRatings = new HashMap<>();
        
        // Calculate average rating for each movie
        for (Movie movie : movies) {
            movieRatings.put(movie.getId(), getAverageRating(movie.getId()));
        }
        
        // Sort movies based on their average ratings
        movies.sort((m1, m2) -> {
            double rating1 = movieRatings.get(m1.getId());
            double rating2 = movieRatings.get(m2.getId());
            return ascending ? 
                   Double.compare(rating1, rating2) : 
                   Double.compare(rating2, rating1);
        });
        
        return movies;
    }
}
