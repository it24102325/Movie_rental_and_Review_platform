package services;

import models.Rental;
import models.Movie;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDao {

    private static final String rentalFilePath = "C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\rentals.txt";

    // Get all rentals for a user
    public static List<Rental> getUserRentals(String userId) {
        List<Rental> userRentals = new ArrayList<>();
        System.out.println("Reading rentals from: " + rentalFilePath);
        System.out.println("Looking for rentals for user: " + userId);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(rentalFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Reading line: " + line);
                String[] rentalData = line.split(",");
                if (rentalData.length >= 5 && rentalData[1].equals(userId)) {
                    System.out.println("Found matching rental for user");
                    Rental rental = new Rental();
                    rental.setRentalId(rentalData[0]);
                    rental.setUserId(rentalData[1]);
                    rental.setMovieId(rentalData[2]);
                    rental.setRentalDate(rentalData[3]);
                    rental.setReturnDate(rentalData[4]);
                    
                    // Get movie title
                    Movie movie = MovieDao.getMovieById(rental.getMovieId());
                    if (movie != null) {
                        rental.setMovieTitle(movie.getTitle());
                        System.out.println("Found movie title: " + movie.getTitle());
                    } else {
                        System.out.println("Movie not found for ID: " + rental.getMovieId());
                    }
                    
                    userRentals.add(rental);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading rentals file: " + e.getMessage());
            e.printStackTrace();
        }
        
        System.out.println("Total rentals found for user: " + userRentals.size());
        return userRentals;
    }

    // Rent a movie
    public static void rentMovie(String userId, String movieId) {
        String rentalId = "R" + System.currentTimeMillis();  // Generate a unique rental ID
        String rentalDate = new java.util.Date().toString();  // Get current date
        String returnDate = "Not yet returned";  // Set initial return status

        System.out.println("Creating new rental:");
        System.out.println("Rental ID: " + rentalId);
        System.out.println("User ID: " + userId);
        System.out.println("Movie ID: " + movieId);
        System.out.println("Rental Date: " + rentalDate);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rentalFilePath, true))) {
            String rentalLine = String.join(",", rentalId, userId, movieId, rentalDate, returnDate);
            writer.write(rentalLine + "\n");
            System.out.println("Successfully wrote rental to file");
        } catch (IOException e) {
            System.err.println("Error writing rental to file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Return a movie
    public static void returnMovie(String rentalId) {
        List<String> rentals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(rentalFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rentalData = line.split(",");
                if (rentalData[0].equals(rentalId)) {
                    rentalData[4] = new java.util.Date().toString();  // Set the return date
                }
                rentals.add(String.join(",", rentalData));
            }
        } catch (IOException e) {
            System.err.println("Error reading rentals file for return: " + e.getMessage());
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rentalFilePath, false))) {
            for (String rental : rentals) {
                writer.write(rental);
                writer.newLine();
            }
            System.out.println("Successfully updated rental return date");
        } catch (IOException e) {
            System.err.println("Error writing updated rentals to file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
