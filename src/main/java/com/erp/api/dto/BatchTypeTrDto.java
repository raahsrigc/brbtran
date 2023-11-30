package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class BatchTypeTrDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fromDate;
	private String toDate;
	private String type;
	private int pageCount;
	private int pageNumber;
	private String search;
	private String columnName;
	private String columnValue;

}
