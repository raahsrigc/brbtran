package com.erp.api.models;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class SaveKycStatusModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long insuredId;
    private int isKyc;
    private long id;
    private long policyId;
    private double sumInsured;
    private double premium;
    private String batchNumber;
    private String kycRemark;

}
