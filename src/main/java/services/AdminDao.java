package services;

import models.User;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {

    private static final String filePath = "C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\admins.txt";

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
            
            // Create admins.txt if it doesn't exist
            File adminsFile = new File(filePath);
            if (!adminsFile.exists()) {
                System.out.println("Creating admins.txt file: " + adminsFile.getAbsolutePath());
                if (!adminsFile.createNewFile()) {
                    System.err.println("Failed to create admins.txt file");
                }
            }
            System.out.println("Admins file path: " + adminsFile.getAbsolutePath());
            System.out.println("Admins file exists: " + adminsFile.exists());
            System.out.println("Admins file can read: " + adminsFile.canRead());
            System.out.println("Admins file can write: " + adminsFile.canWrite());
        } catch (IOException e) {
            System.err.println("Error initializing admin data file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Add a new admin
    public static void addAdmin(String name, String username, String email, String password) {
        if (isAdminExist(email)) {
            throw new IllegalArgumentException("Admin with this email already exists.");
        }

        String adminId = "A" + System.currentTimeMillis();
        User admin = new User(adminId, name, username, email, password, "ADMIN");
        saveAdmin(admin);
    }

    private static void saveAdmin(User admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(admin.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving admin: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to save admin", e);
        }
    }

    // Check if admin exists by email
    public static boolean isAdminExist(String email) {
        File file = new File(filePath);
        if (!file.exists()) {
            return false;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4 && parts[3].equalsIgnoreCase(email)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.err.println("Error checking admin existence: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    // Authenticate admin by username and password
    public static User authenticateAdmin(String username, String password) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.err.println("Admins file does not exist at: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    String storedUsername = parts[2];
                    String storedPassword = parts[4];
                    String role = parts[5];
                    if (storedUsername.equals(username) && storedPassword.equals(password) && "ADMIN".equals(role)) {
                        System.out.println("Admin authenticated successfully: " + username);
                        return new User(parts[0], parts[1], storedUsername, parts[3], storedPassword, role);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error authenticating admin: " + e.getMessage());
            e.printStackTrace();
        }
        System.err.println("Admin authentication failed for user: " + username);
        return null;
    }

    // Get all admins
    public static List<User> getAllAdmins() {
        List<User> admins = new ArrayList<>();
        File adminsFile = new File(filePath);
        
        if (!adminsFile.exists()) {
            System.err.println("Error: admins.txt does not exist at: " + filePath);
            return admins;
        }
        
        if (adminsFile.length() == 0) {
            System.out.println("Warning: admins.txt is empty");
            return admins;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                admins.add(User.fromFileFormat(line));
            }
        } catch (IOException e) {
            System.err.println("Error reading admins: " + e.getMessage());
            e.printStackTrace();
        }
        return admins;
    }

    // Update admin details
    public static boolean updateAdmin(String oldEmail, String newName, String newUsername, String newPassword) {
        List<String> admins = new ArrayList<>();
        boolean updated = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 6) {
                    if (parts[3].equalsIgnoreCase(oldEmail)) {
                        admins.add(parts[0] + "," + newName + "," + newUsername + "," + oldEmail + "," + newPassword + "," + parts[5]);
                        updated = true;
                    } else {
                        admins.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (updated) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
                for (String admin : admins) {
                    writer.write(admin);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return updated;
    }

    // Delete admin
    public static boolean deleteAdmin(String email) {
        List<String> admins = new ArrayList<>();
        boolean deleted = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    if (parts[3].equalsIgnoreCase(email)) {
                        deleted = true;
                    } else {
                        admins.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        if (deleted) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
                for (String admin : admins) {
                    writer.write(admin);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return deleted;
    }
}
