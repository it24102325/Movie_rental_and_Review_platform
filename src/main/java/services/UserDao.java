package services;

import models.User;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    // Path to users' data file - using relative path
    private static final String filePath = "C:\\Users\\Muditha\\Desktop\\movie rental platform\\jacka (1)\\movie_rental_and_review_platform_05\\data\\users.txt";

    static {
        try {
            // Create data directory if it doesn't exist
            File dataDir = new File("data");
            if (!dataDir.exists()) {
                System.out.println("Creating data directory: " + dataDir.getAbsolutePath());
                if (!dataDir.mkdirs()) {
                    System.err.println("Failed to create data directory");
                }
            }
            
            // Create users.txt if it doesn't exist
            File usersFile = new File(filePath);
            if (!usersFile.exists()) {
                System.out.println("Creating users.txt file: " + usersFile.getAbsolutePath());
                if (!usersFile.createNewFile()) {
                    System.err.println("Failed to create users.txt file");
                }
            }
            System.out.println("Users file path: " + usersFile.getAbsolutePath());
            System.out.println("Users file exists: " + usersFile.exists());
            System.out.println("Users file can read: " + usersFile.canRead());
            System.out.println("Users file can write: " + usersFile.canWrite());
        } catch (IOException e) {
            System.err.println("Error initializing user data file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Create a unique user ID
    public static String createUserId() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyyddHHmmss");
        return "UID-" + sdf.format(new java.util.Date());
    }

    // Password validation (checks length and regex for uppercase and number)
    public static String passwordValidation(String password) {
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        if (password.length() < 6) {
            return "length";
        } else if (!password.matches(regex)) {
            return "regex";
        }
        return "valid";
    }

    // Register a user (create user and store in file)
    public static void registerUser(String name, String username, String email, String password) {
        if (isUserExist(email)) {
            throw new IllegalArgumentException("Email already exists! Please choose a different one.");
        }

        String userId = createUserId();  // Generate a unique user ID
        User user = new User(userId, name, username, email, password, "user");  // Default role is "user"
        saveUser(user);  // Save user to file
    }

    // Save user to the file
    private static void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(user.toFileFormat() + "\n");
        } catch (IOException e) {
            System.err.println("Error saving user: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save user", e);
        }
    }

    // Check if a user already exists by email
    public static boolean isUserExist(String email) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user.length >= 4 && user[3].equals(email)) {
                    return true;  // Email exists
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;  // Email doesn't exist
    }

    // Authenticate a user by username and password
    public static User authenticateUser(String username, String password) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Users file does not exist at: " + file.getAbsolutePath());
            return null;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user.length >= 6 && user[2].equals(username) && user[4].equals(password)) {
                    System.out.println("User authenticated successfully: " + username);
                    return new User(user[0], user[1], username, user[3], user[4], user[5]);
                }
            }
        } catch (IOException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
        System.err.println("Authentication failed for user: " + username);
        return null;  // Authentication failed
    }

    // Get all users from the file
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        File usersFile = new File(filePath);
        
        if (!usersFile.exists()) {
            System.err.println("Error: users.txt does not exist at: " + filePath);
            return users;
        }
        
        if (usersFile.length() == 0) {
            System.out.println("Warning: users.txt is empty");
            return users;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(User.fromFileFormat(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    // Get user by username
    public static User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    // Update user details
    public static void updateUser(String username, String email, String password, String role) throws IOException {
        List<User> users = getAllUsers();
        boolean found = false;
        
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                user.setEmail(email);
                if (password != null && !password.trim().isEmpty()) {
                    user.setPassword(password);
                }
                user.setRole(role);
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new IOException("User not found with username: " + username);
        }
        
        // Write updated users back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                    user.getUserId(),
                    user.getName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()));
            }
        }
    }

    // Delete user
    public static void deleteUser(String username) throws IOException {
        List<User> users = getAllUsers();
        boolean found = false;
        
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                users.remove(i);
                found = true;
                break;
            }
        }
        
        if (!found) {
            throw new IOException("User not found with username: " + username);
        }
        
        // Write updated users back to file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : users) {
                writer.write(String.format("%s,%s,%s,%s,%s,%s\n",
                    user.getUserId(),
                    user.getName(),
                    user.getUsername(),
                    user.getEmail(),
                    user.getPassword(),
                    user.getRole()));
            }
        }
    }

    // Test method to verify file writing
    public static void testFileWriting() {
        try {
            File usersFile = new File(filePath);
            System.out.println("Testing file writing...");
            System.out.println("File exists: " + usersFile.exists());
            System.out.println("File path: " + usersFile.getAbsolutePath());
            System.out.println("File size: " + usersFile.length() + " bytes");
            System.out.println("File can read: " + usersFile.canRead());
            System.out.println("File can write: " + usersFile.canWrite());
            
            // Try to write a test line
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write("TEST-USER,Test,TestUser,test@test.com,Test123,user\n");
                System.out.println("Test line written successfully");
            }
            
            // Read back the file
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                System.out.println("\nFile contents:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error in testFileWriting: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
