package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class InsertPolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;


	private long quotationId;
	private String fromDate;
//	private String toDate;
	
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
	
	private String vehicleId;
	private String make;
	private String model;
	private String registrationDate;
	private String registrationEndDate;
	
	private String autoType;
	private int yearOfMake;
	private String chassisNo; 
	private String vehicleCategory;
	private String mobileNumber;

//	private String plateNo;
}
