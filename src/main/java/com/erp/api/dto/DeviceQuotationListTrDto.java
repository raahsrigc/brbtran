package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class DeviceQuotationListTrDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private int pageCount;
	private int pageNumber;
	private boolean isSearch;
	private String columnName;
	private String columnValue;
	
}
