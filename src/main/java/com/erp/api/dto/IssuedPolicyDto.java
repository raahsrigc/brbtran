package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class IssuedPolicyDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private int pageCount;
	private int pageNumber;
	private boolean searchKey;
	private String columnName;
	private String columnValue;

}
  