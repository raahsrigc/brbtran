package com.erp.api.dto;

import lombok.*;


@Getter @Setter @ToString
public class MakePaymentDto {

    private String redirectUrl;
    private long policyId;
    private boolean isBulk;
    private String batchNo;
    private String amount;
    private String profileEmail;
    private boolean isWallet;
    private String userId;

}
	