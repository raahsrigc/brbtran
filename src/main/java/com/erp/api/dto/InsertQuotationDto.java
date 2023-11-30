package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class InsertQuotationDto implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String vehicleRegistrationNumber;
	private String vehicleValue;
	private String email;
	private String mobileNumber;
	private String insuredName;
	private String vehicleTypeCode;
//	private String premiumPeriodType;
//	private int numberOfDays;
	private int planId;
	private String vehicleMake;
	private String vehicleModel;
	private String requestId;
	
	
	
	
}
