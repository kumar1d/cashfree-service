package com.paymentdetails.verification.model;

import lombok.Data;

@Data
public class GSTINVerificationRequest {
    private String GSTIN;
    private String businessName;

    // Getters and Setters

}

