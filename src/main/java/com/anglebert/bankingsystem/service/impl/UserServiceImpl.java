package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.*;
import com.anglebert.bankingsystem.entity.UserEntity;
import com.anglebert.bankingsystem.repository.UserRepository;
import com.anglebert.bankingsystem.utils.AccountUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    EmailService emailService;
    //        create User and save in db
    @Override
    public BankResponse createAccount(UserRequest userRequest) {

//        check if user exist by email
        if(userRepository.existsByEmail(userRequest.getEmail())){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        UserEntity newUser = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .otherName(userRequest.getOtherName())
                .phoneNumber(userRequest.getPhoneNumber())
                .alternativePhoneNumber(userRequest.getAlternativePhoneNumber())
                .email(userRequest.getEmail())
                .stateOfOrigin(userRequest.getStateOfOrigin())
                .address(userRequest.getAddress())
                .gender(userRequest.getGender())
                .status("ACTIVE")
                .accountNumber(AccountUtils.generateAccountNumber())
                .accountBalance(BigDecimal.ZERO)
                .build();

        UserEntity savedUser = userRepository.save(newUser);
//        send email Alert
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("Congratulations! Your account has been successfully created!\n your Account Details: \n" +
                        "ACCOUNT NAME: " + savedUser.getLastName() + " " + savedUser.getLastName() + "\n" +
                        "ACCOUNT NUMBER: " + savedUser.getAccountNumber()  )
                .build();
        emailService.sendEmailAlert(emailDetails);
        return  BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_CREATION_SUCCESS)
                .responseMessage(AccountUtils.ACCOUNT_CREATION_MESSAGE)
                .accountInfo(AccountInfo.builder()
                        .accountBalance(savedUser.getAccountBalance())
                        .accountNumber(savedUser.getAccountNumber())
                        .accountName(savedUser.getFirstName() + " " + savedUser.getLastName())
                        .build())
                .build();
    }

    //    balance Enquiry , name Enquiry , debt , credit , transfer
    @Override
    public BankResponse balanceEnquiry(EnquiryRequest enquiryRequest) {
//       check if account number exists
        boolean accountExist= userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!accountExist){
            return BankResponse.builder()
                    .responseCode(AccountUtils.ACCOUNT_NOT_EXIST_CODE)
                    .responseMessage(AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE)
                    .accountInfo(null)
                    .build();
        }
        UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return BankResponse.builder()
                .responseCode(AccountUtils.ACCOUNT_FOUND_CODE)
                .responseMessage(AccountUtils.ACCOUNT_FOUND_MESSAGE)
                .accountInfo(
                      AccountInfo.builder()
                              .accountName(foundUser.getFirstName() + " " + foundUser.getLastName())
                              .accountNumber(enquiryRequest.getAccountNumber())
                              .accountBalance(foundUser.getAccountBalance())
                              .build()
                )
                .build();
    }

    @Override
    public String nameEnquiry(EnquiryRequest enquiryRequest) {
        boolean accountExist= userRepository.existsByAccountNumber(enquiryRequest.getAccountNumber());
        if(!accountExist){
            return AccountUtils.ACCOUNT_NOT_EXIST_MESSAGE;
        }

        UserEntity foundUser = userRepository.findByAccountNumber(enquiryRequest.getAccountNumber());
        return  foundUser.getFirstName() + " " + foundUser.getLastName() ;
    }


}
