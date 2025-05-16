package services;

import config.AppConfig;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PaymentService {

    private static final String paymentFilePath = AppConfig.PAYMENTS_FILE;

    public static boolean processPayment(String rentalId, String amount) {
        if (rentalId == null || amount == null) {
            System.out.println("Error: rentalId or amount is null");
            return false;
        }

        try {
            // Create the file and its parent directories if they don't exist
            File file = new File(paymentFilePath);
            System.out.println("Attempting to create/write to file: " + file.getAbsolutePath());
            
            // Check if parent directory exists
            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                System.out.println("Parent directory does not exist. Creating...");
                boolean dirCreated = parentDir.mkdirs();
                System.out.println("Parent directory created: " + dirCreated);
                if (!dirCreated) {
                    System.out.println("Failed to create parent directory!");
                    return false;
                }
            }

            // Create file if it doesn't exist
            if (!file.exists()) {
                System.out.println("Payment file does not exist. Creating...");
                boolean fileCreated = file.createNewFile();
                System.out.println("Payment file created: " + fileCreated);
                if (!fileCreated) {
                    System.out.println("Failed to create payment file!");
                    return false;
                }
            }

            // Check if file is writable
            if (!file.canWrite()) {
                System.out.println("Payment file is not writable!");
                return false;
            }

            // Write payment data
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                String paymentId = "PID-" + System.currentTimeMillis();
                String paymentDetails = paymentId + "," + rentalId + "," + amount + "," + new Date();
                System.out.println("Writing payment: " + paymentDetails);
                writer.write(paymentDetails);
                writer.newLine();
                writer.flush(); // Ensure data is written to disk
                return true;
            }
        } catch (IOException e) {
            System.err.println("Error writing to payment file: " + e.getMessage());
            System.err.println("Error occurred at: " + e.getStackTrace()[0]);
            e.printStackTrace();
        }
        return false;
    }

    // Add a method to read payments for testing
    public static List<String> readPayments() {
        List<String> payments = new ArrayList<>();
        File file = new File(paymentFilePath);
        
        System.out.println("Attempting to read from file: " + file.getAbsolutePath());
        
        if (!file.exists()) {
            System.out.println("Payment file does not exist at: " + file.getAbsolutePath());
            return payments;
        }

        if (!file.canRead()) {
            System.out.println("Payment file is not readable!");
            return payments;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                payments.add(line);
            }
            System.out.println("Successfully read " + payments.size() + " payments");
        } catch (IOException e) {
            System.err.println("Error reading payment file: " + e.getMessage());
            System.err.println("Error occurred at: " + e.getStackTrace()[0]);
            e.printStackTrace();
        }
        return payments;
    }
}
