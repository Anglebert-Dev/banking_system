package com.anglebert.bankingsystem.utils;

import java.time.Year;

public class AccountUtils {
    public static  final String ACCOUNT_EXIST_CODE="001";
    public static  final String ACCOUNT_EXIST_MESSAGE="This User Already Has An Account Created!";

    public static  final String ACCOUNT_CREATION_SUCCESS="002";
    public static  final String ACCOUNT_CREATION_MESSAGE="Account Has Been Successfully Created!";
//    generate account number

    public  static  String generateAccountNumber(){

        //    get the current year
        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

//    generate random number btn min and max
        int randomNumber = (int) Math.floor(Math.random() * (max-min+1)+min);

//    convert year and randomNumber to string

        String year = String.valueOf(currentYear);
        String randomNumbers  = String.valueOf(randomNumber);

        StringBuilder accountNumber= new StringBuilder();
        return accountNumber.append(year).append(randomNumbers).toString();
    }

}
