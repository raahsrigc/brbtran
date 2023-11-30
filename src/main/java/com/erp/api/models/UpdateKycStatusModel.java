package com.erp.api.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateKycStatusModel {

    private Long insuredId;
    private int isKyc;
    private long policyId;
    private double sumInsured;
    private double premium;
    private String batchNumber;
}
