package com.erp.api.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDto {

    private double amount;
    private String txnRefNo;
}
