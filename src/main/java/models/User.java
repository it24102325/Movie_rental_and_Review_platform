package models;

import java.util.Date;

public class User {
    private String userId;
    private String name;
    private String username;
    private String email;
    private String password;
    private String role; // "user" or "admin"
    private String department; // Only for admins
    private String accessLevel; // Only for admins
    private boolean active = true; // Default to true for new users
    private Date joinDate; // Date when the user joined

    // Constants for roles
    public static final String ROLE_USER = "user";
    public static final String ROLE_ADMIN = "admin";

    // Getters and setters
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
        return role != null ? role.toLowerCase() : ROLE_USER;
    }

    public void setRole(String role) {
        this.role = role != null ? role.toLowerCase() : ROLE_USER;
    }

    public boolean isAdmin() {
        return ROLE_ADMIN.equals(getRole());
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    // Admin specific methods
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    /**
     * @return The date when the user joined
     */
    public Date getJoinDate() {
        return joinDate;
    }

    /**
     * @param joinDate The date when the user joined
     */
    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

    // Constructor with 4 parameters
    public User(String userId, String email, String password, String role) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        setRole(role);
    }

    // Constructor with 6 parameters
    public User(String userId, String name, String username, String email, String password, String role) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        setRole(role);
    }

    // Constructor with 8 parameters
    public User(String userId, String name, String username, String email, String password, String role, String department, String accessLevel) {
        this(userId, email, password, role);
        this.name = name;
        this.username = username;
        this.department = department;
        this.accessLevel = accessLevel;
    }
}
