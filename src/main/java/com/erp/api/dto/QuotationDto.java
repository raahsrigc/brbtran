package com.erp.api.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter @ToString
public class QuotationDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private int pageCount;
	private int pageNumber;
	private boolean searchKey;
	private String columnName;
	private String columnValue;

}
