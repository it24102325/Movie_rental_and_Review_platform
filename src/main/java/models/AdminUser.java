package models;

public class AdminUser extends User {
    //admin attributes
    private String adminLevel;
    private String[] permissions;
    private boolean active;

    public AdminUser(String userId, String username, String email, String password, 
                    String adminLevel, String[] permissions) {
        super(userId, username, username, email, password, "admin");
        this.adminLevel = adminLevel;
        this.permissions = permissions;
        this.active = true;
    }

    public String getAdminLevel() {
        return adminLevel;
    }

    public String[] getPermissions() {
        return permissions;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
} 