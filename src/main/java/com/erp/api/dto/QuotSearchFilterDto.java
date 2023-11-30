package com.erp.api.dto;


import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class QuotSearchFilterDto implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private int pageCount;
	private int pageNumber;
	private boolean quotationSearch;
	private String columnName;
	private String columnValue;
	private String productCode;
	

}