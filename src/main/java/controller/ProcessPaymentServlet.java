package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@WebServlet("/process-payment")
public class ProcessPaymentServlet extends HttpServlet {
    private static final String TRANSACTIONS_FILE = "src/main/resources/transactions.txt";

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        String amount = request.getParameter("amount");
        String paymentMethod = request.getParameter("payment_method");
        String movieTitle = request.getParameter("movie_title");
        String rentalId = request.getParameter("rentalID");
        String userId = request.getParameter("userID");

        // Validate user session
        HttpSession session = request.getSession(false);
        if (session == null || userId == null || amount == null || rentalId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Get current date and time
        String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        // Generate a unique transaction ID
        String transactionId = String.valueOf(System.currentTimeMillis());

        // Prepare transaction data
        String transactionData = String.join(",",
                transactionId, rentalId, userId, amount, paymentMethod, dateTime, movieTitle, "Completed");

        // Write to file with debugging
        boolean isSuccess = false;
        File file = new File(TRANSACTIONS_FILE);
        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("Created directory: " + parentDir.getAbsolutePath());
        }
        if (!file.exists()) {
            file.createNewFile();
            System.out.println("Created file: " + file.getAbsolutePath());
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            writer.write(transactionData);
            writer.newLine();
            System.out.println("Wrote transaction: " + transactionData);
            isSuccess = true;
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            e.printStackTrace();
        }

        // Redirect based on success or failure
        if (isSuccess) {
            request.setAttribute("amount", amount);
            request.setAttribute("payment_method", paymentMethod);
            request.setAttribute("movie_title", movieTitle);
            request.setAttribute("date_time", dateTime);
            RequestDispatcher rd = request.getRequestDispatcher("paymentSuccess.jsp");
            rd.forward(request, response);
        } else {
            request.setAttribute("error", "Payment failed due to a file error. Please try again.");
            RequestDispatcher rd = request.getRequestDispatcher("paymentError.jsp");
            rd.forward(request, response);
        }
    }
}