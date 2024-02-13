package com.anglebert.bankingsystem.service.impl;


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

import java.io.FileNotFoundException;
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
    private static  final String FILE = "C:\\Users\\D'anglebert\\Documents\\Books\\bank\\statement.pdf";

    public List<Transaction> generateStatement(String accountNumber , String startDate , String endDate) throws FileNotFoundException, DocumentException {
        // Parsing start date string into a LocalDate object
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_LOCAL_DATE);
// Parsing end date string into a LocalDate object
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_LOCAL_DATE);

        //    retrieve list of transactions
        List<Transaction> transactionList = transactionRepository
                .findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(transaction -> {
                    LocalDateTime transactionTime = transaction.getTimeOfTransaction();
                    return !transactionTime.isBefore(start.atStartOfDay()) && !transactionTime.isAfter(end.atTime(23, 59, 59));
                })
                .collect(Collectors.toList());

        //    generate bank statement pdf
        UserEntity user = userRepository.findByAccountNumber(accountNumber);
                String customerName = user.getFirstName() + " " + user.getLastName() ;

                Rectangle statementSize = new Rectangle(PageSize.A4);
                Document document = new Document(statementSize);
                OutputStream outputStream = new FileOutputStream(FILE);
                PdfWriter.getInstance(document, outputStream);
                document.open();

                PdfPTable bankInfoTable = new PdfPTable(1);
                PdfPCell bankName= new PdfPCell(new Phrase("Java Banking System"));
                bankName.setBorder(0);
                bankName.setBackgroundColor(BaseColor.LIGHT_GRAY);
                bankName.setPadding(20f);

                PdfPCell bankAddress = new PdfPCell(new Phrase("73,kkk 123st , Kigali Rwanda"));
                bankName.setBorder(0);
                bankInfoTable.addCell(bankName);
                bankInfoTable.addCell(bankAddress);

                PdfPTable statementInfo = new PdfPTable(2);
                PdfPCell customerInfo =  new PdfPCell(new Phrase("START DATE: " + startDate));
                customerInfo.setBorder(0);
                PdfPCell statement = new PdfPCell(new Phrase( "STATEMENT ACCOUNT: " + accountNumber ));
                statement.setBorder(0);
                PdfPCell stopDate = new PdfPCell(new Phrase(" END DATE: " + endDate));
                stopDate.setBorder(0);

                PdfPCell name = new PdfPCell(new Phrase("CUSTOMER NAME : " + customerName));
                name.setBorder(0);
                PdfPCell address = new PdfPCell(new Phrase("CUSTOMER ADDRESS : " + user.getAddress()));
                address.setBorder(0);






        return transactionList;
    }


//    send it via email
}
