package com.erp.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GetKycStatusModel {


    private String trackingRefNo;
    private String firstName;
    private String lastName;
    private String idType;
    private String phoneNumber;
    private String dob;
    private String idNumber;
    private String gender;
    private String emailId;
    private int kycStatus;
    private long policyId;
    private String batchNumber;
    private double sumInsured;
    private double premium;
   

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public long getPolicyId() {
        return policyId;
    }

    public void setPolicyId(long policyId) {
        this.policyId = policyId;
    }

    public double getSumInsured() {
        return sumInsured;
    }

    public void setSumInsured(double sumInsured) {
        this.sumInsured = sumInsured;
    }

    public double getPremium() {
        return premium;
    }

    public void setPremium(double premium) {
        this.premium = premium;
    }

    public String getTrackingRefNo() {
        return trackingRefNo;
    }

    @JsonProperty("INSURED_ID")
    public void setTrackingRefNo(int trackingRefNo) {
        this.trackingRefNo = String.valueOf(trackingRefNo);
    }

    public String getFirstName() {
        return firstName;
    }

    @JsonProperty("FIRST_NAME")
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @JsonProperty("LAST_NAME")
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getIdType() {
        return idType;
    }

    @JsonProperty("ID_TYPE")
    public void setIdType(String idType) {
        this.idType = idType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("MOBILE_NUMBER")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDob() {
        return dob;
    }

    @JsonProperty("DOB")
    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getIdNumber() {
        return idNumber;
    }

    @JsonProperty("ID_NUMBER")
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getGender() {
        return gender;
    }

    @JsonProperty("GENDER")
    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmailId() {
        return emailId;
    }

    @JsonProperty("EMAIL_ID")
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public int getKycStatus() {
        return kycStatus;
    }

    @JsonProperty("IS_KYC")
    public void setKycStatus(int kycStatus) {
        this.kycStatus = kycStatus;
    }


}
