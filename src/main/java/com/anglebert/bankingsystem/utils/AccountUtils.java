package com.anglebert.bankingsystem.utils;

import java.time.Year;
import java.util.Random;

public class AccountUtils {
    public static  final String ACCOUNT_EXIST_CODE="001";
    public static  final String ACCOUNT_EXIST_MESSAGE="This User Already Has An Account Created!";

    public static  final String ACCOUNT_CREATION_SUCCESS="002";
    public static  final String ACCOUNT_CREATION_MESSAGE="Account Has Been Successfully Created!";

    public static  final String ACCOUNT_NOT_EXIST_CODE="003";
    public static  final String ACCOUNT_NOT_EXIST_MESSAGE="User With Provided Account Number Does Not Exist";


    public static  final String ACCOUNT_FOUND_CODE="004";
    public static  final String ACCOUNT_FOUND_MESSAGE="User Account Found!";

    public static  final String ACCOUNT_CREDITED_SUCCESS="005";
    public static  final String ACCOUNT_CREDITED_SUCCESS_MESSAGE="User Account Credited Successfully!";

//    generate account number

    public static String generateAccountNumber() {
        // Get the current year
        int currentYear = Year.now().getValue();

        // Generate three random numbers between 0 and 999
        Random random = new Random();
        int random1 = random.nextInt(1000);
        int random2 = random.nextInt(1000);
        int random3 = random.nextInt(1000);

        // Format the account number with dashes
        String accountNumber = String.format("%04d-%03d-%03d-%03d", currentYear, random1, random2, random3);

        return accountNumber;
    }

}
