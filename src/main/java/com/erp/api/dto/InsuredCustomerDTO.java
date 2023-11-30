package com.erp.api.dto;

import org.springframework.stereotype.Component;

import lombok.ToString;

@Component
@ToString
public class InsuredCustomerDTO {
	
	private String insuredCode;
	private String  categoryCode;
	private String  customerCode;
	private String nationality;
	private boolean defaultInsured;
	private boolean cemmercial;
	private String title;
	private String  civilId;
	private String referNo;
	private String passportNumber;
	private String mailingAddress;
	private String firstName;
	private String  middleName;
	private String lastName;
	private String contactType;
	private String  remarks;
	private String assuredBusinessSectrorCode;
	private String effectiveFromDate;
	private String effectiveToDate;
	private String insuredName;
	private String shortName;
	private String idNumber;
	private String dob;
	private String idType;
	public String email;
	private String address;
	private String city;
	private String state;
	private String country;
	private String phoneNumber;
	private String annualIncome;
	private String quotationId;
	private AddressDTO residenceAddress;
	private AddressDTO officeAddress;
	private String gender;
	public String getInsuredCode() {
		return insuredCode;
	}
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCustomerCode() {
		return customerCode;
	}
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public boolean isDefaultInsured() {
		return defaultInsured;
	}
	public void setDefaultInsured(boolean defaultInsured) {
		this.defaultInsured = defaultInsured;
	}
	public boolean isCemmercial() {
		return cemmercial;
	}
	public void setCemmercial(boolean cemmercial) {
		this.cemmercial = cemmercial;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCivilId() {
		return civilId;
	}
	public void setCivilId(String civilId) {
		this.civilId = civilId;
	}
	public String getReferNo() {
		return referNo;
	}
	public void setReferNo(String referNo) {
		this.referNo = referNo;
	}
	public String getPassportNumber() {
		return passportNumber;
	}
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContactType() {
		return contactType;
	}
	public void setContactType(String contactType) {
		this.contactType = contactType;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getAssuredBusinessSectrorCode() {
		return assuredBusinessSectrorCode;
	}
	public void setAssuredBusinessSectrorCode(String assuredBusinessSectrorCode) {
		this.assuredBusinessSectrorCode = assuredBusinessSectrorCode;
	}
	public String getEffectiveFromDate() {
		return effectiveFromDate;
	}
	public void setEffectiveFromDate(String effectiveFromDate) {
		this.effectiveFromDate = effectiveFromDate;
	}
	public String getEffectiveToDate() {
		return effectiveToDate;
	}
	public void setEffectiveToDate(String effectiveToDate) {
		this.effectiveToDate = effectiveToDate;
	}
	public String getInsuredName() {
		return insuredName;
	}
	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public AddressDTO getResidenceAddress() {
		return residenceAddress;
	}
	public void setResidenceAddress(AddressDTO residenceAddress) {
		this.residenceAddress = residenceAddress;
	}
	public AddressDTO getOfficeAddress() {
		return officeAddress;
	}
	public void setOfficeAddress(AddressDTO officeAddress) {
		this.officeAddress = officeAddress;
	}
	public String getMailingAddress() {
		return mailingAddress;
	}
	public void setMailingAddress(String mailingAddress) {
		this.mailingAddress = mailingAddress;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getDob() {
		return dob;
	}
	public void setDob(String dob) {
		this.dob = dob;
	}
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAnnualIncome() {
		return annualIncome;
	}
	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}
	public String getQuotationId() {
		return quotationId;
	}
	public void setQuotationId(String quotationId) {
		this.quotationId = quotationId;
	}
	
	
	

}
