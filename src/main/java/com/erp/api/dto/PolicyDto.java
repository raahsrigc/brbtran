package com.erp.api.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class PolicyDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long policyId;
	private String policyNumber;

}
