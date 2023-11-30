package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class SchedulerPolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private long quotationId;
	private long policyId;
	private String fromDate;
	private String toDate;
	private String firstName;
	private String lastName;
	private String dob;
	private String address;
	private String idTpye;
	private String idNumber;
	private String vehicleId;
	private String make;
	private String model;
	private String regisrtrationDate;
	private String regisrtrationEndDate;
	private String autoType;
	private int yearOfMake;
	private String chassisNo;
	private String vehicleCategory;
	private String mobileNumber;
	private String title;
	private String city;
	private String state;
	private String country;

}
