package com.bank.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.bank.model.Transaction;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class PDFGenerationUtility {

    public static byte[] generateTransactionsPDF(List<Transaction> transactions) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, baos);
            document.open();

            // Add title
            Paragraph title = new Paragraph("Last 10 Transactions", 
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, BaseColor.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Create table
            PdfPTable table = new PdfPTable(4); // 4 columns

            // Add table headers
            table.addCell(createHeaderCell("Transaction ID"));
            table.addCell(createHeaderCell("Amount"));
            table.addCell(createHeaderCell("Transaction Type"));
            table.addCell(createHeaderCell("Date"));

            // Add table rows
            for (Transaction transaction : transactions) {
                table.addCell(createDataCell(String.valueOf(transaction.getTransactionId())));
                table.addCell(createDataCell(String.valueOf(transaction.getAmount())));
                table.addCell(createDataCell(transaction.getTransactionType()));
                table.addCell(createDataCell(transaction.getTransactionDate().toString()));
            }

            document.add(table);

        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        return baos.toByteArray();
    }

    private static PdfPCell createHeaderCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA_BOLD)));
        cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }

    private static PdfPCell createDataCell(String text) {
        PdfPCell cell = new PdfPCell(new Phrase(text, FontFactory.getFont(FontFactory.HELVETICA)));
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
}
