package data;

import models.Movie;

public class MovieArray {
    private Movie[] movies;
    private int size;
    private int capacity;
    private static final int INITIAL_CAPACITY = 10;

    public MovieArray() {
        this.capacity = INITIAL_CAPACITY;
        this.movies = new Movie[capacity];
        this.size = 0;
    }

    public void add(Movie movie) {
        if (size == capacity) {
            resize();
        }
        movies[size++] = movie;
    }

    public Movie get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return movies[index];
    }

    public Movie getById(String movieId) {
        for (int i = 0; i < size; i++) {
            if (movies[i].getMovieId().equals(movieId)) {
                return movies[i];
            }
        }
        return null;
    }

    public void remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }

        // Shift elements to the left
        for (int i = index; i < size - 1; i++) {
            movies[i] = movies[i + 1];
        }

        movies[--size] = null; // Clear the last element
    }

    public void removeById(String movieId) {
        for (int i = 0; i < size; i++) {
            if (movies[i].getMovieId().equals(movieId)) {
                remove(i);
                return;
            }
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Movie[] toArray() {
        Movie[] result = new Movie[size];
        System.arraycopy(movies, 0, result, 0, size);
        return result;
    }

    private void resize() {
        capacity *= 2;
        Movie[] newArray = new Movie[capacity];
        System.arraycopy(movies, 0, newArray, 0, size);
        movies = newArray;
    }

    public void clear() {
        movies = new Movie[INITIAL_CAPACITY];
        size = 0;
        capacity = INITIAL_CAPACITY;
    }

    public Movie[] search(String query) {
        if (query == null || query.trim().isEmpty()) {
            return toArray();
        }

        String searchTerm = query.toLowerCase().trim();
        MovieArray results = new MovieArray();

        for (int i = 0; i < size; i++) {
            Movie movie = movies[i];
            if (movie.getTitle().toLowerCase().contains(searchTerm) ||
                    movie.getGenre().toLowerCase().contains(searchTerm) ||
                    movie.getDescription().toLowerCase().contains(searchTerm)) {
                results.add(movie);
            }
        }

        return results.toArray();
    }

    public void sortByRating() {
        // Bubble sort implementation
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (movies[j].getRating() < movies[j + 1].getRating()) {
                    // Swap movies
                    Movie temp = movies[j];
                    movies[j] = movies[j + 1];
                    movies[j + 1] = temp;
                }
            }
        }
    }
}