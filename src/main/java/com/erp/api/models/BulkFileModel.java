package com.erp.api.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BulkFileModel {


    private int sr_no;
    private String device_serial_number;
    private String device_type;
    private String device_make;
    private String device_modal;
    private String date_of_purchase;
    private String imei_number;
    private double device_value;
    private String invoice_url;
    private String email;
    private String phone_number;
    private String id_type;
    private String id_number;
    private String title;
    private String first_name;
    private String middle_name;
    private String last_name;
    private String device_premium_amount;
    private String gender;
    private String dob;
    private String errorMessage;
    private String errorCode;
}
