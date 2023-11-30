package com.erp.api.models;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetKycDataModel implements Serializable {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String trackingRefNo;
	    private String firstName;
	    private String lastName;
	    private String idType;
	    private String dob;
	    private String phoneNumber;
	    private long id;
	    private String idNumber;
	    private String email;
	    private String batchNumber;
	    
	    
	    private String gender;
	    private int kycStatus;
	    private long policyId;
	    
	    private double sumInsured;
	    private double premium;
	    

}
