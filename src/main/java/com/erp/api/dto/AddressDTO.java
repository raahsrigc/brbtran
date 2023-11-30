package com.erp.api.dto;

import org.springframework.stereotype.Component;

@Component
public class AddressDTO {
	
	private String address;
	private String city;
	private String state;
	private String country;
	private String phoneNumber;
	private String faxNumber;
	private String contact1;
	private String contact2;
	private String officeMail1;
	private String officeMail2;
	
	
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
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getContact1() {
		return contact1;
	}
	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}
	public String getContact2() {
		return contact2;
	}
	public void setContact2(String contact2) {
		this.contact2 = contact2;
	}
	public String getOfficeMail1() {
		return officeMail1;
	}
	public void setOfficeMail1(String officeMail1) {
		this.officeMail1 = officeMail1;
	}
	public String getOfficeMail2() {
		return officeMail2;
	}
	public void setOfficeMail2(String officeMail2) {
		this.officeMail2 = officeMail2;
	}
	
	
	

}
