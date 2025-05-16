// === PaymentDao.java ===
package dao;

import models.Payment;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDao {
    private final String filePath = "data/payments.txt";

    public List<Payment> getAllPayments() {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    Payment p = new Payment(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                    payments.add(p);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public void addPayment(Payment payment) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            String line = String.join(",",
                    payment.getPaymentId(),
                    payment.getRentalId(),
                    payment.getUserId(),
                    payment.getAmount(),
                    payment.getStatus(),
                    payment.getPaymentDate());
                    payment.getCategory();
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Payment getPaymentById(String id) {
        for (Payment p : getAllPayments()) {
            if (p.getPaymentId().equals(id)) return p;
        }
        return null;
    }

    public void updatePayment(Payment updated) {
        List<Payment> all = getAllPayments();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Payment p : all) {
                if (p.getPaymentId().equals(updated.getPaymentId())) {
                    p = updated;
                }
                String line = String.join(",",
                        p.getPaymentId(),
                        p.getRentalId(),
                        p.getUserId(),
                        p.getAmount(),
                        p.getStatus(),
                        p.getPaymentDate());
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deletePayment(String id) {
        List<Payment> all = getAllPayments();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Payment p : all) {
                if (!p.getPaymentId().equals(id)) {
                    String line = String.join(",",
                            p.getPaymentId(),
                            p.getRentalId(),
                            p.getUserId(),
                            p.getAmount(),
                            p.getStatus(),
                            p.getPaymentDate());
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
