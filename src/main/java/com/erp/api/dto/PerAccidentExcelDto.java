package com.erp.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter
@Setter
@ToString
public class PerAccidentExcelDto implements Serializable {

	/**
	* 28
	*/ 
	private static final long serialVersionUID = 1L;
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
	private String country;
	private String state;
	private String lga;
	
	// policy information
	
	private String startDate;
	private String idType;
	private String idNumber;
	private String buildingName;
	private String insurerLocation;
	private String insurerOccupation;
	private String insurerBusinessType;
	private int workHourRange;
	private String personalSpecialisation;
	private BigDecimal estimateWages;
	private BigDecimal estimateEarning;
	private String nextToKin;
	private String nokRelationship;
	private String nokPhoneNumber;
	private String nokAddress;
	private BigDecimal sumInsuredAmount;
	
	

	private String errorMessage;
	private String errorCode;

}
