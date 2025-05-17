package services;

import models.User;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;


public class UserDao {
    private static String FILE_PATH;
    // Static admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private static final String ADMIN_EMAIL = "admin@cineverse.com";

    static {
        try {
            // Get the web application's root directory
            String webAppRoot = System.getProperty("user.dir");
            System.out.println("Web app root: " + webAppRoot);
            
            // Create data directory in the web application root
            File dataDir = new File(webAppRoot, "data");
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                System.out.println("Created data directory: " + created);
            }
            
            // Set the file path
            FILE_PATH = new File(dataDir, "users.txt").getAbsolutePath();
            System.out.println("Users file path: " + FILE_PATH);
            
            // Create users file if it doesn't exist
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                boolean created = file.createNewFile();
                System.out.println("Created users file: " + created);
            }
        } catch (Exception e) {
            System.err.println("Error initializing UserDao: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Create a unique user ID based on the timestamp
    public static String createUserId() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMyyyyddHHmmss");
        return "UID-" + sdf.format(new Date());
    }

    // Login user
    public static User loginUser(String username, String password) {
        if (FILE_PATH == null) {
            System.err.println("FILE_PATH is not initialized");
            return null;
        }

        System.out.println("Attempting login for username: " + username);
        
        // Check admin credentials first
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            System.out.println("Admin login successful");
            User adminUser = new User("ADMIN-001", "System Admin", ADMIN_USERNAME, 
                                    ADMIN_EMAIL, ADMIN_PASSWORD, "admin");
            adminUser.setRole("admin"); // Ensure role is set to admin
            adminUser.setActive(true); // Admin is always active
            return adminUser;
        }
        
        if (username == null || password == null || 
            username.trim().isEmpty() || password.trim().isEmpty()) {
            System.out.println("Login failed: Empty username or password");
            return null;
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("Login failed: Users file does not exist");
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 6 && 
                    userData[2].equals(username.trim()) && 
                    userData[4].equals(password)) {
                    System.out.println("Login successful for user: " + username);
                    // Create user with proper role
                    User user = new User(userData[0], userData[1], userData[2], 
                                      userData[3], userData[4], userData[5]);
                    user.setRole(userData[5]); // Ensure role is set correctly
                    // Set active status if available, otherwise default to true
                    user.setActive(userData.length > 6 ? Boolean.parseBoolean(userData[6]) : true);
                    return user;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Login failed: No matching user found");
        return null;
    }

    // Password validation (length and regex)
    public static String passwordValidation(String password) {
        if (password == null || password.trim().isEmpty()) {
            return "empty";
        }
        String regex = "^(?=.*[A-Z])(?=.*\\d).+$";
        if (password.length() < 6) {
            return "length";
        } else if (!password.matches(regex)) {
            return "regex";
        }
        return "valid";
    }

    // Register a new user
    public static boolean getRegisterDetails(String name, String username, String email, String password) {
        if (name == null || username == null || email == null || password == null ||
            name.trim().isEmpty() || username.trim().isEmpty() || email.trim().isEmpty()) {
            System.out.println("Error: Required fields are empty");
            return false;
        }

        // Check if username is admin
        if (ADMIN_USERNAME.equals(username)) {
            System.out.println("Error: Username 'admin' is reserved");
            return false;
        }

        String role = "user"; // Default role for users
        String userId = createUserId();
        User user = new User(userId, name.trim(), username.trim(), email.trim(), password, role);
        return registerUser(user);
    }

    // Register the user by writing to the file
    public static boolean registerUser(User user) {
        if (FILE_PATH == null) {
            System.err.println("FILE_PATH is not initialized");
            return false;
        }

        try {
            File file = new File(FILE_PATH);
            System.out.println("Attempting to create/write to file: " + file.getAbsolutePath());
            
            // Create file if it doesn't exist
            if (!file.exists()) {
                System.out.println("Users file does not exist. Creating...");
                boolean fileCreated = file.createNewFile();
                System.out.println("Users file created: " + fileCreated);
                if (!fileCreated) {
                    System.out.println("Failed to create users file!");
                    return false;
                }
            }

            // Check if file is writable
            if (!file.canWrite()) {
                System.out.println("Users file is not writable!");
                return false;
            }

            // Write user data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                String userData = user.getUserId() + "," + user.getName() + "," + 
                                user.getUsername() + "," + user.getEmail() + "," + 
                                user.getPassword() + "," + user.getRole() + "," +
                                user.isActive();
                System.out.println("Writing user: " + user.getUsername());
                writer.write(userData);
                writer.newLine();
                writer.flush(); // Ensure data is written to disk
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to users file: " + e.getMessage());
            System.err.println("Error occurred at: " + e.getStackTrace()[0]);
            e.printStackTrace();
        }
        return false;
    }

    // Check if a user with the same email already exists
    public static boolean isUserExist(String email) {
        if (FILE_PATH == null || email == null || email.trim().isEmpty()) {
            return false;
        }

        // Check admin email
        if (ADMIN_EMAIL.equals(email)) {
            return true;
        }

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length >= 4 && userData[3].equals(email.trim())) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users file: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Get all users
    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        if (FILE_PATH == null) {
            System.err.println("FILE_PATH is not initialized");
            return users;
        }

        // Add admin user first
        User adminUser = new User("ADMIN-001", "System Admin", ADMIN_USERNAME, 
                                ADMIN_EMAIL, ADMIN_PASSWORD, "admin");
        adminUser.setActive(true); // Admin is always active
        users.add(adminUser);

        try {
            File file = new File(FILE_PATH);
            if (!file.exists()) {
                System.out.println("Users file does not exist: " + FILE_PATH);
                return users;
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 6) {
                        User user = new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                        // Set active status if available, otherwise default to true
                        user.setActive(parts.length > 6 ? Boolean.parseBoolean(parts[6]) : true);
                        users.add(user);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading users: " + e.getMessage());
            e.printStackTrace();
        }
        return users;
    }

    // Delete user account
    public static boolean deleteUser(String userId) {
        if (FILE_PATH == null || userId == null || userId.isEmpty()) {
            System.err.println("Invalid user ID for deletion");
            return false;
        }

        // Prevent admin deletion
        if ("ADMIN-001".equals(userId)) {
            System.err.println("Cannot delete admin account");
            return false;
        }

        List<User> users = getAllUsers();
        boolean removed = users.removeIf(user -> user.getUserId().equals(userId));
        
        if (removed) {
            try {
                File file = new File(FILE_PATH);
                if (!file.exists()) {
                    System.err.println("Users file does not exist: " + FILE_PATH);
                    return false;
                }

                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    for (User user : users) {
                        if (!"ADMIN-001".equals(user.getUserId())) { // Skip admin when writing back
                            writer.write(String.format("%s,%s,%s,%s,%s,%s,%s\n",
                                user.getUserId(),
                                user.getName(),
                                user.getUsername(),
                                user.getEmail(),
                                user.getPassword(),
                                user.getRole(),
                                user.isActive()));
                        }
                    }
                }
                return true;
            } catch (IOException e) {
                System.err.println("Error deleting user: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return false;
    }

    // Update user details
    public static boolean updateUser(String oldEmail, String newName, String newUserName, String newPassword) {
        if (FILE_PATH == null) {
            System.err.println("FILE_PATH is not initialized");
            return false;
        }

        // Prevent admin update
        if (ADMIN_EMAIL.equals(oldEmail)) {
            System.err.println("Cannot update admin account");
            return false;
        }

        List<String> users = new ArrayList<>();
        boolean updated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] user = line.split(",");
                if (user[3].equals(oldEmail)) {
                    users.add(user[0] + "," + newName + "," + newUserName + "," + oldEmail + "," + newPassword + "," + user[5] + "," + user[6]);
                    updated = true;
                } else {
                    users.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
                for (String user : users) {
                    writer.write(user);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return updated;
    }

    public static User getUserByUsername(String username) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    public static User getUserByEmail(String email) {
        List<User> users = getAllUsers();
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
