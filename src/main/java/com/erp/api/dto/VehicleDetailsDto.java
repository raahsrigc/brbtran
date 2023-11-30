package com.erp.api.dto;

import org.springframework.stereotype.Component;

@Component
public class VehicleDetailsDto {
	
	public String registrationNumber;
	public String make;
	public String model;
	public String cubicCapacity;
	public String chassisNumber;
	public String registrationDate;
	public int mgfYear;
	public double idv;
	public String productId;
	public String agencyType;
	public String agencyCode;
	public String registrationPlaces;
	public String officeCode;
	public String departmentCode;
	public String insuredCode;
	public String insuredName;
	public String mobile;
	public String email;
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
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
	public String getCubicCapacity() {
		return cubicCapacity;
	}
	public void setCubicCapacity(String cubicCapacity) {
		this.cubicCapacity = cubicCapacity;
	}
	public String getChassisNumber() {
		return chassisNumber;
	}
	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}
	public String getRegistrationDate() {
		return registrationDate;
	}
	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}
	
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getAgencyType() {
		return agencyType;
	}
	public void setAgencyType(String agencyType) {
		this.agencyType = agencyType;
	}
	public String getAgencyCode() {
		return agencyCode;
	}
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	public int getMgfYear() {
		return mgfYear;
	}
	public void setMgfYear(int mgfYear) {
		this.mgfYear = mgfYear;
	}
	
	public String getRegistrationPlaces() {
		return registrationPlaces;
	}
	public void setRegistrationPlaces(String registrationPlaces) {
		this.registrationPlaces = registrationPlaces;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public double getIdv() {
		return idv;
	}
	public void setIdv(double idv) {
		this.idv = idv;
	}
	
	
	

}
