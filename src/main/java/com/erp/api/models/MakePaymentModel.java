package com.erp.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MakePaymentModel {

    private String customerEmail;
    private String amount;
    private String customerPhoneNumber;
    private String tx_ref;
    private String customerName;


}
