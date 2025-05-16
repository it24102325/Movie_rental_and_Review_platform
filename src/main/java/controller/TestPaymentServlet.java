package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import services.PaymentService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/test-payment")
public class TestPaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><body>");
        out.println("<h2>Testing Payment File Writing</h2>");
        
        // Try to write a test payment
        boolean success = PaymentService.processPayment("TEST-RENTAL-001", "100.00");
        
        if (success) {
            out.println("<p style='color: green;'>Successfully wrote test payment!</p>");
            
            // Read and display all payments
            out.println("<h3>Current Payments:</h3>");
            out.println("<ul>");
            for (String payment : PaymentService.readPayments()) {
                out.println("<li>" + payment + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p style='color: red;'>Failed to write test payment!</p>");
        }
        
        out.println("</body></html>");
    }
} 