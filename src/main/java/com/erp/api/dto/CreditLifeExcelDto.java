package com.erp.api.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CreditLifeExcelDto {


    private int srNo;
    private String title;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String mobileNumber;
    private String email;
    private String dob;
    private String address;
    private String masterPolicyNo;
    private String borrowerType;
    private String premiumType;
    private BigDecimal loanAmount;
    private int loanTenure;
    private String startDate;
    private boolean criticalillness;
    private boolean deathOnly;
    private boolean permanentDisability;
    private boolean jobLoss;
    private boolean lossOfBusiness;
    private String loanNumberThirdParty;
    private String narration;
    private String trackingRefrence;
    
    
    private String errorMessage;
    private String errorCode;
}
