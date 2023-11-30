package com.erp.api.dto;

import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class PolicyUpdateDetailsDto {
	
	
	
	
	private long quotationId;
	private String fromDate;
	private String toDate;
	
	private String firstName;
	private String lastName;
	private String dob;
	private String address;
	private String idTpye;
	private String idNumber;
	private String vehicleId;
	private String make;
	private String model;
	private String regisrtrationDate;
	
	private String regisrtrationEndDate;
	private String autoType;
	private int YearOfMake;
	private String chassisNo;
	private String vehicleCategory;
	private String mobileNumber;
	private String title;
	private String city;
	private String state;
	private String country;
	private String policyNumber;
	private String token;
	private String policyId;
	private String webHookUrl;
	public long getQuotationId() {
		return quotationId;
	}

	public void setQuotationId(long quotationId) {
		this.quotationId = quotationId;
	}

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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIdTpye() {
		return idTpye;
	}

	public void setIdTpye(String idTpye) {
		this.idTpye = idTpye;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getVehicleId() {
		return vehicleId;
	}

	public void setVehicleId(String vehicleId) {
		this.vehicleId = vehicleId;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getRegisrtrationDate() {
		return regisrtrationDate;
	}

	public void setRegisrtrationDate(String regisrtrationDate) {
		this.regisrtrationDate = regisrtrationDate;
	}

	public String getRegisrtrationEndDate() {
		return regisrtrationEndDate;
	}

	public void setRegisrtrationEndDate(String regisrtrationEndDate) {
		this.regisrtrationEndDate = regisrtrationEndDate;
	}

	public String getAutoType() {
		return autoType;
	}

	public void setAutoType(String autoType) {
		this.autoType = autoType;
	}

	

	public String getChassisNo() {
		return chassisNo;
	}

	public void setChassisNo(String chassisNo) {
		this.chassisNo = chassisNo;
	}

	public String getVehicleCategory() {
		return vehicleCategory;
	}

	public void setVehicleCategory(String vehicleCategory) {
		this.vehicleCategory = vehicleCategory;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPolicyNumber() {
		return policyNumber;
	}

	public void setPolicyNumber(String policyNumber) {
		this.policyNumber = policyNumber;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWebHookUrl() {
		return webHookUrl;
	}

	public void setWebHookUrl(String webHookUrl) {
		this.webHookUrl = webHookUrl;
	}

	public int getYearOfMake() {
		return YearOfMake;
	}

	public void setYearOfMake(int yearOfMake) {
		YearOfMake = yearOfMake;
	}
	
	
	

}
