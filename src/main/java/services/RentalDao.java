package services;

import config.AppConfig;
import models.Rental;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RentalDao {

    private static final String filePath = AppConfig.RENTALS_FILE;

    // Rent a movie
    public static boolean rentMovie(String movieId, String rentalDate, User user) {
        String rentalId = "RNT-" + System.currentTimeMillis(); // Generate a unique rental ID
        Rental rental = new Rental(rentalId, user.getUserId(), movieId, rentalDate, "rented");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(rental.getRentalId() + "," + rental.getUserId() + "," + rental.getMovieId() + "," + rental.getRentalDate() + "," + rental.getRentalStatus());
            writer.newLine();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get rented movies by a user
    public static List<String> getRentedMovies(String userId) {
        List<String> rentedMovies = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rentalData = line.split(",");
                if (rentalData[1].equals(userId) && rentalData[4].equals("rented")) {
                    rentedMovies.add(rentalData[2]); // Add movie ID to the list
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rentedMovies;
    }

    // Update rental status (e.g., from "rented" to "returned")
    public static boolean updateRentalStatus(String rentalId, String status) {
        List<String> rentals = new ArrayList<>();
        boolean isUpdated = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] rentalData = line.split(",");
                if (rentalData[0].equals(rentalId)) {
                    rentals.add(rentalId + "," + rentalData[1] + "," + rentalData[2] + "," + rentalData[3] + "," + status);
                    isUpdated = true;
                } else {
                    rentals.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (isUpdated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
                for (String rental : rentals) {
                    writer.write(rental);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return isUpdated;
    }

    public static List<Rental> getAllRentals() {
        List<Rental> rentals = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Rentals file does not exist: " + filePath);
                return rentals;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        Rental rental = new Rental(parts[0], parts[1], parts[2], parts[3], parts[4]);
                        rentals.add(rental);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading rentals: " + e.getMessage());
            e.printStackTrace();
        }
        return rentals;
    }

    public static boolean deleteRental(String rentalId) {
        if (rentalId == null || rentalId.isEmpty()) {
            System.err.println("Invalid rental ID for deletion");
            return false;
        }

        List<Rental> rentals = getAllRentals();
        boolean removed = rentals.removeIf(rental -> rental.getRentalId().equals(rentalId));
        
        if (removed) {
            try {
                File file = new File(filePath);
                if (!file.exists()) {
                    System.err.println("Rentals file does not exist: " + filePath);
                    return false;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (Rental rental : rentals) {
                        writer.write(String.format("%s,%s,%s,%s,%s\n",
                            rental.getRentalId(),
                            rental.getUserId(),
                            rental.getMovieId(),
                            rental.getRentalDate(),
                            rental.getRentalStatus()));
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error deleting rental: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    public static List<Rental> getUserRentals(String userId) {
        List<Rental> userRentals = new ArrayList<>();
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Rentals file does not exist: " + filePath);
                return userRentals;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5 && parts[1].equals(userId)) {
                        Rental rental = new Rental(parts[0], parts[1], parts[2], parts[3], parts[4]);
                        userRentals.add(rental);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading user rentals: " + e.getMessage());
            e.printStackTrace();
        }
        return userRentals;
    }
}
