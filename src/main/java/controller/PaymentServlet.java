// === PaymentServlet.java ===
package controller;

import dao.PaymentDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Payment;

import java.io.IOException;
import java.util.List;

@WebServlet("/payments")
public class PaymentServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Payment> payments = new PaymentDao().getAllPayments();
        request.setAttribute("payments", payments);
        request.getRequestDispatcher("payment.jsp").forward(request, response);
    }
}
