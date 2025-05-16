package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.User;
import services.AdminDao;
import services.UserDao;

import java.io.IOException;

@WebServlet("/admin/management/*")
public class AdminManagementServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String action = request.getPathInfo();
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        User currentUser = (User) session.getAttribute("user");
        if (!currentUser.getRole().equalsIgnoreCase("admin")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        switch (action) {
            case "/add-admin":
                handleAddAdmin(request, response);
                break;
            case "/update-admin":
                handleUpdateAdmin(request, response);
                break;
            case "/delete-admin":
                handleDeleteAdmin(request, response);
                break;
            case "/toggle-admin-status":
                handleToggleStatus(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "GET method not supported");
    }

    private void handleAddAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String adminLevel = request.getParameter("adminLevel");
        String[] permissions = request.getParameterValues("permissions");

        if (username == null || email == null || password == null || adminLevel == null) {
            request.getSession().setAttribute("error", "All fields are required");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
            return;
        }

        // If no permissions are selected, set default permissions
        if (permissions == null || permissions.length == 0) {
            permissions = new String[]{"manage_users", "manage_movies", "manage_rentals", "manage_reviews"};
        }

        try {
            // Check if username or email already exists
            if (UserDao.getUserByUsername(username) != null) {
                request.getSession().setAttribute("error", "Username already exists");
                response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
                return;
            }

            if (UserDao.getUserByEmail(email) != null) {
                request.getSession().setAttribute("error", "Email already exists");
                response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
                return;
            }

            AdminDao.addAdmin(username, email, password, adminLevel, permissions);
            AdminDao.logAdminActivity(((User) request.getSession().getAttribute("user")).getUserId(), 
                                    "Added new admin: " + username);
            request.getSession().setAttribute("success", "Admin added successfully");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to add admin: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        }
    }

    private void handleUpdateAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String adminId = request.getParameter("adminId");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String adminLevel = request.getParameter("adminLevel");
        String[] permissions = request.getParameterValues("permissions");

        if (adminId == null || username == null || email == null || adminLevel == null || permissions == null) {
            request.getSession().setAttribute("error", "All fields are required");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
            return;
        }

        try {
            AdminDao.updateAdminPermissions(adminId, permissions);
            AdminDao.logAdminActivity(((User) request.getSession().getAttribute("user")).getUserId(), 
                                    "Updated admin: " + username);
            request.getSession().setAttribute("success", "Admin updated successfully");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to update admin: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        }
    }

    private void handleDeleteAdmin(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String adminId = request.getParameter("adminId");

        if (adminId == null) {
            request.getSession().setAttribute("error", "Admin ID is required");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
            return;
        }

        try {
            AdminDao.deleteAdmin(adminId);
            AdminDao.logAdminActivity(((User) request.getSession().getAttribute("user")).getUserId(), 
                                    "Deleted admin: " + adminId);
            request.getSession().setAttribute("success", "Admin deleted successfully");
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        } catch (Exception e) {
            request.getSession().setAttribute("error", "Failed to delete admin: " + e.getMessage());
            response.sendRedirect(request.getContextPath() + "/admin/admins.jsp");
        }
    }

    private void handleToggleStatus(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String adminId = request.getParameter("adminId");
        boolean active = Boolean.parseBoolean(request.getParameter("active"));

        if (adminId == null) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"Admin ID is required\"}");
            return;
        }

        try {
            AdminDao.updateAdminStatus(adminId, active);
            AdminDao.logAdminActivity(((User) request.getSession().getAttribute("user")).getUserId(), 
                                    "Toggled admin status: " + adminId);
            
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": true}");
        } catch (Exception e) {
            response.setContentType("application/json");
            response.getWriter().write("{\"success\": false, \"message\": \"" + e.getMessage() + "\"}");
        }
    }
} 