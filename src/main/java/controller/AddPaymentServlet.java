// === AddPaymentServlet.java ===
package controller;

import dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Payment;

import java.io.IOException;
import java.time.LocalDate;
import java.util.UUID;

@WebServlet("/addPayment")
public class AddPaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rentalId = request.getParameter("rentalId");
        String userId = request.getParameter("userId");
        String amount = request.getParameter("amount");
        String paymentId = UUID.randomUUID().toString();
        String status = "Pending";
        String paymentDate = LocalDate.now().toString();

        Payment payment = new Payment(paymentId, rentalId, userId, amount, status, paymentDate);
        new PaymentDao().addPayment(payment);

        response.sendRedirect("paymentSuccess.jsp");
    }
}
