package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class InsertQuotationTrDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deviceSerialNumber;
	private String deviceValue;
	private String mobileNo;
	private String email;

}
