package com.erp.api.dto;

import org.springframework.stereotype.Component;

@Component
public class ClaimSettlementDto {

	
	public String claimNumber;
	public String settlementDate;
	public double settlementAmount;
	public String remarks;
	public String dvEmail;
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getDvEmail() {
		return dvEmail;
	}
	public void setDvEmail(String dvEmail) {
		this.dvEmail = dvEmail;
	}
	
	
	
	
}
