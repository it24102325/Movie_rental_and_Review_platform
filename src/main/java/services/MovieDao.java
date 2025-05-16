package services;

import models.Movie;
import data.MovieArray;
import java.io.*;

public class MovieDao {
    private static final String MOVIES_FILE = "data/movies.txt";
    private static final String DELIMITER = "|";

    public static Movie[] getAllMovies() {
        MovieArray movies = new MovieArray();
        try {
            File file = new File(MOVIES_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int id = 1;
                while ((line = reader.readLine()) != null) {
                    if (!line.trim().isEmpty()) {
                        String[] parts = line.split("\\" + DELIMITER);
                        if (parts.length >= 5) {
                            try {
                                String title = parts[0].trim();
                                String description = parts[3].trim();
                                
                                // Only add movies that have been properly added (have a title and description)
                                if (!title.isEmpty() && !description.isEmpty()) {
                                    Movie movie = new Movie();
                                    movie.setMovieId(String.valueOf(id++));
                                    movie.setTitle(title);
                                    movie.setGenre(parts[1].trim());
                                    movie.setRating(Integer.parseInt(parts[2].trim()));
                                    movie.setDescription(description);
                                    movie.setAvailable(Boolean.parseBoolean(parts[4].trim()));
                                    movies.add(movie);
                                }
                            } catch (NumberFormatException e) {
                                System.err.println("Error parsing movie data: " + line);
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return movies.toArray();
    }

    public static void updateMovie(String movieId, String title, String genre, int rating, String description, boolean available) {
        Movie[] movies = getAllMovies();
        try {
            File file = new File(MOVIES_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Movie movie : movies) {
                    if (movie.getMovieId().equals(movieId)) {
                        // Update the movie with new values
                        String movieData = String.join(DELIMITER,
                                title,
                                genre,
                                String.valueOf(rating),
                                description,
                                String.valueOf(available)
                        );
                        writer.write(movieData);
                    } else {
                        // Write the existing movie data
                        String movieData = String.join(DELIMITER,
                                movie.getTitle(),
                                movie.getGenre(),
                                String.valueOf(movie.getRating()),
                                movie.getDescription(),
                                String.valueOf(movie.isAvailable())
                        );
                        writer.write(movieData);
                    }
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update movie: " + e.getMessage());
        }
    }

    public static Movie[] searchMovies(String query) {
        MovieArray movies = new MovieArray();
        Movie[] allMovies = getAllMovies();
        for (Movie movie : allMovies) {
            movies.add(movie);
        }
        return movies.search(query);
    }

    public void addMovie(Movie movie) {
        try {
            File file = new File(MOVIES_FILE);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                String movieData = String.join(DELIMITER,
                        movie.getTitle(),
                        movie.getGenre(),
                        String.valueOf(movie.getRating()),
                        movie.getDescription(),
                        String.valueOf(movie.isAvailable())
                );
                writer.write(movieData);
                writer.newLine();
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add movie: " + e.getMessage());
        }
    }

    public static void updateMovie(Movie movie) {
        Movie[] movies = getAllMovies();
        try {
            File file = new File(MOVIES_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Movie m : movies) {
                    if (m.getMovieId().equals(movie.getMovieId())) {
                        m = movie;
                    }
                    String movieData = String.join(DELIMITER,
                            m.getTitle(),
                            m.getGenre(),
                            String.valueOf(m.getRating()),
                            m.getDescription(),
                            String.valueOf(m.isAvailable())
                    );
                    writer.write(movieData);
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to update movie: " + e.getMessage());
        }
    }

    public static void deleteMovie(String movieId) {
        Movie[] movies = getAllMovies();
        try {
            File file = new File(MOVIES_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Movie movie : movies) {
                    if (!movie.getMovieId().equals(movieId)) {
                        String movieData = String.join(DELIMITER,
                                movie.getTitle(),
                                movie.getGenre(),
                                String.valueOf(movie.getRating()),
                                movie.getDescription(),
                                String.valueOf(movie.isAvailable())
                        );
                        writer.write(movieData);
                        writer.newLine();
                    }
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to delete movie: " + e.getMessage());
        }
    }

    public static Movie getMovieById(String movieId) {
        Movie[] movies = getAllMovies();
        for (Movie movie : movies) {
            if (movie.getMovieId().equals(movieId)) {
                return movie;
            }
        }
        return null;
    }

    public void toggleAvailability(String movieId) {
        Movie[] movies = getAllMovies();
        try {
            File file = new File(MOVIES_FILE);
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (Movie movie : movies) {
                    if (movie.getMovieId().equals(movieId)) {
                        movie.setAvailable(!movie.isAvailable());
                    }
                    String movieData = String.join(DELIMITER,
                            movie.getTitle(),
                            movie.getGenre(),
                            String.valueOf(movie.getRating()),
                            movie.getDescription(),
                            String.valueOf(movie.isAvailable())
                    );
                    writer.write(movieData);
                    writer.newLine();
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to toggle movie availability: " + e.getMessage());
        }
    }
}