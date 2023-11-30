package com.erp.api.dto;

import org.json.JSONArray;
import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class ClaimRegistrationDto {

	private static final long serialVersionUID = 1L;
	
	public String notificationDate;
	public String lossDate;
	public String policyNumber;
	public String policyId;
	public String lossRemarks;
	public String notificationMode;
	public String intimatorName;
	public String mobile;
	public String email;
	public String address;
	public String state;
	public String city;
	public String pinCode;
	public String surveyorCode;
	public double estimateAmount;
	public String natureOfLoss;
	public String causeOfLoss;
	public String notificationNumber;
	public String insuredCode;
	public String businessType;
	public double settlementAmount;
	public String claimId;
	public String claimNumber;
	public String url;
	public String token;
	public String settlementDate;
	public Object imageData;
	public String getNotificationDate() {
		return notificationDate;
	}
	public void setNotificationDate(String notificationDate) {
		this.notificationDate = notificationDate;
	}
	public String getLossDate() {
		return lossDate;
	}
	public void setLossDate(String lossDate) {
		this.lossDate = lossDate;
	}
	public String getPolicyNumber() {
		return policyNumber;
	}
	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}
	public String getLossRemarks() {
		return lossRemarks;
	}
	public void setLossRemarks(String lossRemarks) {
		this.lossRemarks = lossRemarks;
	}
	public String getNotificationMode() {
		return notificationMode;
	}
	public void setNotificationMode(String notificationMode) {
		this.notificationMode = notificationMode;
	}
	public String getIntimatorName() {
		return intimatorName;
	}
	public void setIntimatorName(String intimatorName) {
		this.intimatorName = intimatorName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPinCode() {
		return pinCode;
	}
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}
	public String getSurveyorCode() {
		return surveyorCode;
	}
	public void setSurveyorCode(String surveyorCode) {
		this.surveyorCode = surveyorCode;
	}
	
	public String getNatureOfLoss() {
		return natureOfLoss;
	}
	public void setNatureOfLoss(String natureOfLoss) {
		this.natureOfLoss = natureOfLoss;
	}
	public String getCauseOfLoss() {
		return causeOfLoss;
	}
	public void setCauseOfLoss(String causeOfLoss) {
		this.causeOfLoss = causeOfLoss;
	}
	public String getNotificationNumber() {
		return notificationNumber;
	}
	public void setNotificationNumber(String notificationNumber) {
		this.notificationNumber = notificationNumber;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public double getEstimateAmount() {
		return estimateAmount;
	}
	public void setEstimateAmount(double estimateAmount) {
		this.estimateAmount = estimateAmount;
	}
	public String getPolicyId() {
		return policyId;
	}
	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public double getSettlementAmount() {
		return settlementAmount;
	}
	public void setSettlementAmount(double settlementAmount) {
		this.settlementAmount = settlementAmount;
	}
	public String getClaimId() {
		return claimId;
	}
	public void setClaimId(String claimId) {
		this.claimId = claimId;
	}
	public String getClaimNumber() {
		return claimNumber;
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getSettlementDate() {
		return settlementDate;
	}
	public void setSettlementDate(String settlementDate) {
		this.settlementDate = settlementDate;
	}
	public Object getImageData() {
		return imageData;
	}
	public void setImageData(Object imageData) {
		this.imageData = imageData;
	}
	
	
	
	
}
