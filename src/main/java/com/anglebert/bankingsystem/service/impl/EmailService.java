package com.anglebert.bankingsystem.service.impl;

import com.anglebert.bankingsystem.dto.EmailDetails;

public interface EmailService {
    void sendEmailAlert(EmailDetails emailDetails);
    void sendEmailAlertWithPdf(EmailDetails emailDetails);
}
