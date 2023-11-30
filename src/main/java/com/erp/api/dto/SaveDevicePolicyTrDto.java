package com.erp.api.dto;


import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString
public class SaveDevicePolicyTrDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int quotationId;
	
	
	
	private String deviceType;
	private String deviceMake;
	private String deviceModal;
	private String imeiNumber;
	private String invoiceProofUrl;
	@Pattern(message = "dateOfPurchase is invalid.Allowed format yyyy-MM-dd", regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])")

	private String dateOfPurchase;
	

	private String middleName;
	private String title;
	
	@NotBlank(message = "First Name can not be blank")
	@NotNull(message = "first Name can not be null")
	private String firstName;
	
	@NotBlank(message = "last Name can not be blank")
	@NotNull(message = "last Name can not be null")
	private String lastName;
	
	
	
	@NotBlank(message = "gender can not be blank")
	@NotNull(message = "gender can not be blank")

	private String gender;
	
	
	@NotBlank(message = "idNumber Name can not be blank")
	@NotNull(message = "idNumber Name can not be null")


	private String idNumber;
	private String deviceValue;
	private String deviceSerialNumber;
	
	@Pattern(message = "Id Type is invalid", regexp = "drivers_license|dl|DRIVERS_LICENSE|pvc|PVC|international_passport|INTERNATIONAL_PASSPORT|bvn|BVN|passport")
	private String idType;
	
	@Pattern(message = "dateOfBirth is invalid.Allowed format yyyy-MM-dd", regexp = "\\d{4}-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|[3][01])")

	private String dob;
	private String insuredId;
	private String mobileNumber;
}
