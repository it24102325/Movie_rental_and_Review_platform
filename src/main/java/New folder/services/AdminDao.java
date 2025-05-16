package services;

import config.AppConfig;
import models.AdminUser;
import models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminDao {
    private static String filePath;
    
    static {
        // Initialize the file path and ensure the directory exists
        filePath = AppConfig.ADMINS_FILE;
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (!parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            System.out.println("Admin file path: " + filePath);
        } catch (IOException e) {
            System.err.println("Error initializing admin data file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void addAdmin(String username, String email, String password, 
                              String adminLevel, String[] permissions) {
        // First create a user account
        String adminId = "ADM-" + UUID.randomUUID().toString();
        User user = new User(adminId, username, username, email, password, "admin");
        boolean userRegistered = UserDao.registerUser(user);
        
        if (!userRegistered) {
            throw new RuntimeException("Failed to register admin user");
        }

        // Then create the admin record
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = String.format("%s|%s|%s|%s|%s|%s|%s|%s",
                adminId, username, email, password, adminLevel,
                String.join(",", permissions != null ? permissions : new String[0]), 
                "true", System.currentTimeMillis());
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to add admin: " + e.getMessage());
        }
    }

    public static List<AdminUser> getAllAdmins() {
        List<AdminUser> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    String[] parts = line.split("\\|");
                    if (parts.length >= 7) {
                        String adminId = parts[0];
                        String username = parts[1];
                        String email = parts[2];
                        String password = parts[3];
                        String adminLevel = parts[4];
                        String[] permissions = parts[5].isEmpty() ? new String[0] : parts[5].split(",");
                        boolean isActive = parts.length > 6 ? Boolean.parseBoolean(parts[6]) : true;
                        
                        // Create admin user with proper parameters
                        AdminUser admin = new AdminUser(adminId, username, email, password, adminLevel, permissions);
                        admin.setActive(isActive);
                        
                        // Add to the beginning of the list to show newest first
                        admins.add(0, admin);
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing admin record: " + line);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading admin data file: " + e.getMessage());
            e.printStackTrace();
        }
        return admins;
    }

    public static boolean updateAdminPermissions(String adminId, String[] newPermissions) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7 && parts[0].equals(adminId)) {
                    found = true;
                    parts[5] = String.join(",", newPermissions);
                    lines.add(String.join("|", parts));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return found;
    }

    public static boolean deleteAdmin(String adminId) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7 && parts[0].equals(adminId)) {
                    found = true;
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return found;
    }

    public static void logAdminActivity(String adminId, String action) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AppConfig.ADMIN_LOGS_FILE, true))) {
            String logEntry = String.format("%s|%s|%s|%s",
                adminId, action, new java.util.Date(), System.currentTimeMillis());
            writer.write(logEntry);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean updateAdminStatus(String adminId, boolean active) {
        List<String> lines = new ArrayList<>();
        boolean found = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length >= 7 && parts[0].equals(adminId)) {
                    found = true;
                    parts[6] = String.valueOf(active);
                    lines.add(String.join("|", parts));
                } else {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        if (found) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                for (String line : lines) {
                    writer.write(line);
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        return found;
    }
} 