package com.bank.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@WebServlet("/DownloadTransactionsPDF")
public class GeneratePDFServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accountNo = (Integer) request.getSession().getAttribute("accountNo");

        TransactionDAO transactionDAO = new TransactionDAO();
        List<Transaction> transactions = transactionDAO.getLast10Transactions(accountNo);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=transactions.pdf");

        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            // Add title
            document.add(new Paragraph("Last 10 Transactions"));

            // Add table
            PdfPTable table = new PdfPTable(4); // Number of columns
            table.addCell(new PdfPCell(new Paragraph("Transaction ID")));
            table.addCell(new PdfPCell(new Paragraph("Amount")));
            table.addCell(new PdfPCell(new Paragraph("Transaction Type")));
            table.addCell(new PdfPCell(new Paragraph("Date")));

            for (Transaction transaction : transactions) {
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(transaction.getTransactionId()))));
                table.addCell(new PdfPCell(new Paragraph(String.valueOf(transaction.getAmount()))));
                table.addCell(new PdfPCell(new Paragraph(transaction.getTransactionType())));
                table.addCell(new PdfPCell(new Paragraph(transaction.getTransactionDate().toString())));
            }

            document.add(table);
        } catch (DocumentException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error generating PDF");
        } finally {
            document.close();
        }
    }
}
