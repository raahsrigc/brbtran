package com.erp.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileModel {
    @JsonProperty("MOBILE_NUMBER_PRIMARY")
    private String primaryMobileNo;

    @JsonProperty("MARTIAL_STATUS")
    private String maritalStatus;

    @JsonProperty("DOB")
    private String dob;

    @JsonProperty("WALLET_AMOUNT")
    private Double walletAmount;

    @JsonProperty("GENDER")
    private String gender;

    @JsonProperty("EMAIL_ID")
    private String emailId;

    @JsonProperty("USER_NAME")
    private String userName;

    @JsonProperty("USER_PROFILE_IMAGE")
    private String userProfileImage;

    @JsonProperty("IS_KYC")
    private String isKyc;
}
