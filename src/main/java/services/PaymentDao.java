package services;

import models.Payment;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {

    // Path to payments' data file
    private static final String paymentFilePath = "C:\\Users\\Muditha\\Desktop\\jacka (1)\\jacka (1)\\movie_rental_and_review_platform_05\\data\\payments.txt";

    // Process payment for a rental
    public static void processPayment(String userId, String rentalId) {
        String paymentId = "P" + System.currentTimeMillis();  // Generate unique payment ID
        String amount = "10.00";  // Hardcoded for now (you can calculate based on rental duration)
        String paymentDate = new java.util.Date().toString();  // Get current date

        Payment payment = new Payment(paymentId, userId, rentalId, amount, paymentDate);
        savePayment(payment);
    }

    // Save payment details to the payment file
    private static void savePayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paymentFilePath, true))) {
            writer.write(payment.toFileFormat() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // View all payments for a user
    public static List<Payment> viewPayments(String userId) {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(paymentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] paymentData = line.split(",");
                if (paymentData[1].equals(userId)) {
                    payments.add(new Payment(paymentData[0], paymentData[1], paymentData[2], paymentData[3], paymentData[4]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payments;
    }

    // Refund payment (optional)
    public static void refundPayment(String paymentId) {
        List<String> payments = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(paymentFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] paymentData = line.split(",");
                if (!paymentData[0].equals(paymentId)) {
                    payments.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(paymentFilePath, false))) {
            for (String payment : payments) {
                writer.write(payment);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
