package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.EmailDetails;
import com.anglebert.bankingsystem.entity.Transaction;
import com.anglebert.bankingsystem.entity.UserEntity;
import com.anglebert.bankingsystem.repository.TransactionRepository;
import com.anglebert.bankingsystem.repository.UserRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Slf4j
public class BankStatement {
    private TransactionRepository transactionRepository;
    private UserRepository userRepository;
   private EmailService emailService;
    private static  final String FILE = "C:\\Users\\D'anglebert\\Documents\\Books\\bank\\statement.pdf";

    public List<Transaction> generateStatement(String accountNumber , String startDate , String endDate) throws Exception {
        // Parsing start date string into a LocalDate object
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
        // Parsing end date string into a LocalDate object
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        // Retrieve list of transactions
        List<Transaction> transactionList = transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> {
                    LocalDateTime transactionTime = transaction.getTimeOfTransaction();
                    return !transactionTime.isBefore(start.atStartOfDay()) && !transactionTime.isAfter(end.atTime(23, 59, 59));
                })
                .collect(Collectors.toList());

        // Generate bank statement pdf
        UserEntity user = userRepository.findByAccountNumber(accountNumber);
        String customerName = user.getFirstName() + " " + user.getLastName();

        try (OutputStream outputStream = new FileOutputStream(FILE)) {
            Rectangle statementSize = new Rectangle(PageSize.A4);
            Document document = new Document(statementSize);
            PdfWriter.getInstance(document, outputStream);
            document.open();
            Font tableFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

            PdfPTable bankInfoTable = new PdfPTable(1);
            PdfPCell bankName= new PdfPCell(new Phrase("Java Banking System" , tableFont));
            bankName.setBorder(0);
            bankName.setBackgroundColor(BaseColor.LIGHT_GRAY);
            bankName.setPadding(20f);

            PdfPCell bankAddress = new PdfPCell(new Phrase("73,kkk 123st , Kigali Rwanda" , tableFont));
            bankAddress.setBorder(0);
            bankInfoTable.addCell(bankName);
            bankInfoTable.addCell(bankAddress);

            PdfPTable statementInfo = new PdfPTable(2);
            PdfPCell customerInfo =  new PdfPCell(new Phrase("START DATE: " + startDate ,tableFont));
            customerInfo.setBorder(0);
            PdfPCell statement = new PdfPCell(new Phrase("STATEMENT ACCOUNT: " + accountNumber ,tableFont));
            statement.setBorder(0);
            PdfPCell stopDate = new PdfPCell(new Phrase("END DATE: " + endDate , tableFont));
            stopDate.setBorder(0);

            PdfPCell name = new PdfPCell(new Phrase("CUSTOMER NAME : " +customerName ,tableFont));
            name.setBorder(0);
            PdfPCell space = new PdfPCell();
            space.setBorder(0);
            PdfPCell address = new PdfPCell(new Phrase("CUSTOMER ADDRESS : " +user.getAddress(),tableFont));
            address.setBorder(0);

            PdfPTable transactionTable = new PdfPTable(4);
            transactionTable.setWidthPercentage(100);

            String[] headers = {"DATE", "TRANSACTION TYPE", "TRANSACTION AMOUNT", "STATUS"};
            for (String header : headers) {
                PdfPCell headerCell = new PdfPCell(new Phrase(header, tableFont));
                headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                headerCell.setBorderWidth(1f);
                headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(headerCell);
            }

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");
            transactionList.forEach(transaction -> {
                PdfPCell dateCell = new PdfPCell(new Phrase(transaction.getTimeOfTransaction().format(formatter), tableFont));
                dateCell.setBorderWidth(1f);
                dateCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(dateCell);

                PdfPCell typeCell = new PdfPCell(new Phrase(transaction.getTransactionType(), tableFont));
                typeCell.setBorderWidth(1f);
                typeCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(typeCell);

                PdfPCell amountCell = new PdfPCell(new Phrase(transaction.getAmount().toString(), tableFont));
                amountCell.setBorderWidth(1f);
                amountCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(amountCell);

                PdfPCell statusCell = new PdfPCell(new Phrase(transaction.getStatus(), tableFont));
                statusCell.setBorderWidth(1f);
                statusCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                transactionTable.addCell(statusCell);
            });

            statementInfo.addCell(customerInfo);
            statementInfo.addCell(statement);
            statementInfo.addCell(stopDate);
            statementInfo.addCell(endDate);
            statementInfo.addCell(name);
            statementInfo.addCell(address);
            statementInfo.addCell(space);


            document.add(bankInfoTable);
            document.add(statementInfo);
            document.add(transactionTable);

            document.close();

            EmailDetails emailDetails =  EmailDetails.builder()
                    .recipient(user.getEmail())
                    .subject("BANK STATEMENT")
                    .messageBody("Requested bank statement attached!")
                    .attachment(FILE)
                    .build();

            emailService.sendEmailAlertWithPdf(emailDetails);
        } catch (Exception e) {
            log.error("Error generating bank statement: {}", e.getMessage());
            throw e;
        }

        return transactionList;
    }
}
