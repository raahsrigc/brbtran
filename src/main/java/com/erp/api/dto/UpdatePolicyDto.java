package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class UpdatePolicyDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long policyId;
	private String vehicleId;
	private String make;
	private String model;
	private String registrationDate;
	private String registrationEndDate;
	private String autoType;
	private int yearOfMake;
	private String chassisNo;
	private String vehicleCategory;
}
