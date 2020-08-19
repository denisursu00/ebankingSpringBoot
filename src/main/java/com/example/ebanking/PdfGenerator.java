package com.example.ebanking;

import com.example.ebanking.model.TransactionModel;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.stream.Stream;

public class PdfGenerator {
    public static ByteArrayInputStream getPdf(List<TransactionModel> transactions) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfWriter.getInstance(document, out);

            document.open();

            PdfPTable table = new PdfPTable(5);
            addTableHeader(table);
            addRows(table,transactions);

            document.add(table);
            document.close();

            return new ByteArrayInputStream(out.toByteArray());

        } catch (DocumentException ex) {
            throw new RuntimeException(ex);
        }
    }
    private static void addTableHeader(PdfPTable table) {
        Stream.of("Data","Cont Debitor/Creditor","Suma debit","Suma credit","Sold curent")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }
    private static void addRows(PdfPTable table,List<TransactionModel> transactions) {
        for (TransactionModel transaction:transactions) {
            String debitSum = "";
            String creditSum = "";
            if(transaction.getDebitSum()!=null) {
                debitSum = transaction.getDebitSum().toString();
            }
            if(transaction.getCreditSum()!=null) {
                creditSum = transaction.getCreditSum().toString();
            }
            table.addCell(transaction.getTransactionDate());
            table.addCell(transaction.getAccountId().toString());
            table.addCell(debitSum);
            table.addCell(creditSum);
            table.addCell(transaction.getCurrentSum().toString());
        }
    }
}
