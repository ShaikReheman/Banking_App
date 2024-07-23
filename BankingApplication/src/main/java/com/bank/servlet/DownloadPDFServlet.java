package com.bank.servlet;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;
import com.bank.util.PDFGenerationUtility;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

@WebServlet("/DownloadPDFServlet")
public class DownloadPDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accountNo") == null) {
            response.sendRedirect("customerLogin.jsp");
            return;
        }

        int accountNo = (Integer) session.getAttribute("accountNo");
        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getLastTransactions(accountNo, 10);

        byte[] pdfBytes = PDFGenerationUtility.generateTransactionsPDF(transactions);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");
        response.setContentLength(pdfBytes.length);

        try (OutputStream os = response.getOutputStream()) {
            os.write(pdfBytes);
        }
    }
}
