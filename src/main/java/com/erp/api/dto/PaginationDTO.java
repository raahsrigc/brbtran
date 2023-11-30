package com.erp.api.dto;

import org.springframework.stereotype.Component;

@Component
public class PaginationDTO {
	 
	private String fromDate;
	private String toDate;
	private int pageCount;
	private int pageNumber;
	private Boolean isSearch;
	private String columnName;
	private String value;
	private int productId;
	private int lobId;
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Boolean getIsSearch() {
		return isSearch;
	}
	public void setIsSearch(Boolean isSearch) {
		this.isSearch = isSearch;
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getLobId() {
		return lobId;
	}
	public void setLobId(int lobId) {
		this.lobId = lobId;
	}
	
	
	
	

}
