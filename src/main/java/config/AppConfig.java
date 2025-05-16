package config;

import java.io.File;

public class AppConfig {
    private static final String BASE_PATH = System.getProperty("user.home") + File.separator + "movie_rental_data";
    public static final String DATA_DIR = BASE_PATH + File.separator + "data";

    public static final String REVIEWS_FILE = DATA_DIR + File.separator + "reviews.txt";
    public static final String MOVIES_FILE = DATA_DIR + File.separator + "movies.txt";
    public static final String RENTALS_FILE = DATA_DIR + File.separator + "rentals.txt";
    public static final String USERS_FILE = DATA_DIR + File.separator + "users.txt";
    public static final String PAYMENTS_FILE = DATA_DIR + File.separator + "payments.txt";
    public static final String ADMINS_FILE = DATA_DIR + File.separator + "admins.txt";
    public static final String ADMIN_LOGS_FILE = DATA_DIR + File.separator + "admin_logs.txt";

    static {
        try {
            System.out.println("Base directory: " + BASE_PATH);
            System.out.println("Data directory: " + DATA_DIR);

            // Create data directory if it doesn't exist
            File dataDir = new File(DATA_DIR);
            if (!dataDir.exists()) {
                System.out.println("Creating data directory...");
                boolean created = dataDir.mkdirs();
                System.out.println("Data directory created: " + created);
            } else {
                System.out.println("Data directory already exists");
            }

            // Print all file paths
            System.out.println("Reviews file: " + REVIEWS_FILE);
            System.out.println("Movies file: " + MOVIES_FILE);
            System.out.println("Rentals file: " + RENTALS_FILE);
            System.out.println("Users file: " + USERS_FILE);
            System.out.println("Payments file: " + PAYMENTS_FILE);
            System.out.println("Admins file: " + ADMINS_FILE);
            System.out.println("Admin logs file: " + ADMIN_LOGS_FILE);
        } catch (Exception e) {
            System.err.println("Error in AppConfig initialization: " + e.getMessage());
            e.printStackTrace();
        }
    }
}