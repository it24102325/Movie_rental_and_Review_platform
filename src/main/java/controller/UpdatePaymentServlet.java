// === UpdatePaymentServlet.java ===
package controller;

import dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Payment;

import java.io.IOException;

@WebServlet("/updatePayment")
public class UpdatePaymentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String id = request.getParameter("paymentId");
        String status = request.getParameter("status");

        PaymentDao dao = new PaymentDao();
        Payment payment = dao.getPaymentById(id);

        if (payment != null) {
            payment.setStatus(status);
            dao.updatePayment(payment);
        }

        response.sendRedirect("payments");
    }
}
