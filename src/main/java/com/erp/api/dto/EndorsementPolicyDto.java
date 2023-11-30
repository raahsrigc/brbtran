package com.erp.api.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class EndorsementPolicyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long policyId;
	
	private String title;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String emailId;
	
	private String dob;
	private String address;
	private String city;
	private String state;
	private String country;
	
	private String idType;
	private String idNumber;
	
	private String registrationDate;
	private String registrationEndDate;
	
	private String vehicleId;
	private String mobileNumber;
	private String endorsementType;
	
	

}