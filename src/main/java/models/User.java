package models;

public class User {
    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role;

    // Constructor
    public User(String userId, String name, String username, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and setters for each field
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    // Convert user to a string format for storage (e.g., for writing to file)
    public String toFileFormat() {
        // Escape commas if necessary to avoid CSV breaking (optional, depends on your data)
        return String.join(",", userId, name, username, email, password, role);
    }

    // Static method to convert a line from file storage back into a User object
    public static User fromFileFormat(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        String[] data = line.split(",", -1); // -1 to keep trailing empty strings if any
        if (data.length < 6) {
            throw new IllegalArgumentException("Invalid user data format");
        }
        return new User(data[0], data[1], data[2], data[3], data[4], data[5]);
    }
}
