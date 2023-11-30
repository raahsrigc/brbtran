package com.erp.api.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.dto.NaiComResposeDto;
import com.erp.api.dto.NiidStatusResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class JsonBuilders {
	
	@Value("${nai.com.sid}")
	private String naicomSid;
	
	@Value("${nai.com.token}")
	private String naiComToken;
	
	@Autowired
	private ObjectMapper objectMapper;


	public JSONObject getNaiComNewInsurance(NaiComDBResposeDto naiDbResponse, JSONObject responseJsonObj) throws Exception {
		log.info("-------getNaiComNewInsurance---------"+naiDbResponse.getNaiComData().toString());
		
		Map<String, Object> request = objectMapper.readValue(new Gson().toJson(naiDbResponse.getNaiComData()), new TypeReference<Map<String, Object>>(){});
//		JSONObject request = new JSONObject(niidResponseObj.getData().toString());
		log.info("-------getNaiComNewInsurance---------"+request);
		
		
		
		JSONObject mainObj = new JSONObject();
		JSONArray mainArray = new JSONArray();
		mainObj.put("SID", naicomSid);
		mainObj.put("Token", naiComToken);
		mainObj.put("Type", "Auto");
		
		System.out.println(naiDbResponse.getNaiComData().toString());
		////////////////////////////////

		 JSONObject basicInfoObj = new JSONObject();
		 JSONArray attArray = new JSONArray();
		 JSONObject attArrayObj1 = new JSONObject();
		 JSONObject attArrayObj2 = new JSONObject();
		 JSONObject attArrayObj3 = new JSONObject();
		 JSONObject attArrayObj4 = new JSONObject();
		 JSONObject attArrayObj5 = new JSONObject();
		 JSONObject attArrayObj6 = new JSONObject();
		 
		 basicInfoObj.put("GroupName", "Basic Info");
		 basicInfoObj.put("GroupTag", 0);
		 basicInfoObj.put("GroupCount", 0);
		 
		 
	     
		 attArrayObj1.put("Name", "TypeID");
		 attArrayObj1.put("Value", request.get("TypeID"));
	     
		 attArrayObj2.put("Name", "CoverageStartDate");
		 attArrayObj2.put("Value", request.get("CoverageStartDate"));
	     
		 attArrayObj3.put("Name", "CoverageEndDate");
		 attArrayObj3.put("Value", request.get("CoverageEndDate"));
	     
		 attArrayObj4.put("Name", "PolicyInternalID");
		 attArrayObj4.put("Value", request.get("PolicyInternalID"));
	     
		 attArrayObj5.put("Name", "PolicyDescription");
		 attArrayObj5.put("Value", request.get("PolicyDescription"));
		 
		 attArrayObj6.put("Name", "RecorderType");
		 attArrayObj6.put("Value", "Company");
	     
	     attArray.put(attArrayObj1);
	     attArray.put(attArrayObj2);
	     attArray.put(attArrayObj3);
	     attArray.put(attArrayObj4);
	     attArray.put(attArrayObj5);
	     attArray.put(attArrayObj6);
	     
	     basicInfoObj.put("AttArray", attArray);
	     mainArray.put(basicInfoObj);

		//////////////////////////////////////////// *************DETAIL INFO**************/////////////////////////////////////////
		
		 
		 JSONObject detailInfoObj = new JSONObject();
		 JSONArray attArrayDetailInfo = new JSONArray();
		 JSONObject attArrayDetailInfo1 = new JSONObject();
		 JSONObject attArrayDetailInfo2 = new JSONObject();
		 JSONObject attArrayDetailInfo3 = new JSONObject();
		 JSONObject attArrayDetailInfo4 = new JSONObject();
		 JSONObject attArrayDetailInfo5 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo6 = new JSONObject();
		 JSONObject attArrayDetailInfo7 = new JSONObject();
		 JSONObject attArrayDetailInfo8 = new JSONObject();
		 JSONObject attArrayDetailInfo9 = new JSONObject();
		 JSONObject attArrayDetailInfo10 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo11 = new JSONObject();
		 JSONObject attArrayDetailInfo12 = new JSONObject();
		 JSONObject attArrayDetailInfo13 = new JSONObject();
		 JSONObject attArrayDetailInfo14 = new JSONObject();
		 JSONObject attArrayDetailInfo15 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo16 = new JSONObject();
		 JSONObject attArrayDetailInfo17 = new JSONObject();
		 JSONObject attArrayDetailInfo18 = new JSONObject();
		 JSONObject attArrayDetailInfo19 = new JSONObject();
		 JSONObject attArrayDetailInfo20 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo21 = new JSONObject();
		 JSONObject attArrayDetailInfo22 = new JSONObject();
		 JSONObject attArrayDetailInfo23 = new JSONObject();
		 JSONObject attArrayDetailInfo24 = new JSONObject();
		 JSONObject attArrayDetailInfo25 = new JSONObject();
		 
		 
		 detailInfoObj.put("GroupName", "Detail Info");
		 detailInfoObj.put("GroupTag", 1);
		 detailInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayDetailInfo1.put("Name", "CoverageType");
		 attArrayDetailInfo1.put("Value", request.get("CoverageType"));
	     
		 attArrayDetailInfo2.put("Name", "OwnerType");
		 attArrayDetailInfo2.put("Value", request.get("OwnerType"));
	     
		 attArrayDetailInfo3.put("Name", "OwnerLicense");
		 attArrayDetailInfo3.put("Value", request.get("OwnerLicense"));
	     
		 attArrayDetailInfo4.put("Name", "PersonNameLast");
		 attArrayDetailInfo4.put("Value", request.get("PersonNameLast"));
	     
		 attArrayDetailInfo5.put("Name", "PersonNameFirst");
		 attArrayDetailInfo5.put("Value", request.get("PersonNameFirst"));
		 
		 
		 //---------------
		 attArrayDetailInfo6.put("Name", "OrgType");
		 attArrayDetailInfo6.put("Value", request.get("OrgType"));
	     
		 attArrayDetailInfo7.put("Name", "OrgName");
		 attArrayDetailInfo7.put("Value", request.get("OrgName"));
	     
		 attArrayDetailInfo8.put("Name", "OrgID");
		 attArrayDetailInfo8.put("Value", request.get("OrgID"));
	     
		 attArrayDetailInfo9.put("Name", "AddressLine");
		 attArrayDetailInfo9.put("Value", request.get("AddressLine"));
	     
		 attArrayDetailInfo10.put("Name", "CityLGA");
		 attArrayDetailInfo10.put("Value", request.get("CityLGA"));
		 
		 
		 attArrayDetailInfo11.put("Name", "State");
		 attArrayDetailInfo11.put("Value", request.get("State"));
	     
		 attArrayDetailInfo12.put("Name", "PostCode");
		 attArrayDetailInfo12.put("Value", request.get("PostCode"));
	     
		 attArrayDetailInfo13.put("Name", "Phone");
		 attArrayDetailInfo13.put("Value", request.get("Phone"));
	     
		 attArrayDetailInfo14.put("Name", "Email");
		 attArrayDetailInfo14.put("Value", request.get("Email"));
	     
		 attArrayDetailInfo15.put("Name", "InsuredValue");
		 attArrayDetailInfo15.put("Value", request.get("InsuredValue"));
		 
		 attArrayDetailInfo16.put("Name", "Premium");
		 attArrayDetailInfo16.put("Value", request.get("Premium"));
	     
		 attArrayDetailInfo17.put("Name", "CommissionFee");
		 attArrayDetailInfo17.put("Value", request.get("CommissionFee"));
	     
		 attArrayDetailInfo18.put("Name", "ExtraFee");
		 attArrayDetailInfo18.put("Value", request.get("ExtraFee"));
	     
		 attArrayDetailInfo19.put("Name", "PremiumNote");
		 attArrayDetailInfo19.put("Value",request.get("PremiumNote"));
	     
		 attArrayDetailInfo20.put("Name", "Terms");
		 attArrayDetailInfo20.put("Value", request.get("Terms"));
		 
		 
		 attArrayDetailInfo21.put("Name", "Preamble");
		 attArrayDetailInfo21.put("Value", request.get("Preamble"));
	     
		 attArrayDetailInfo22.put("Name", "Endorsements");
		 attArrayDetailInfo22.put("Value", request.get("Endorsements"));
	     
		 attArrayDetailInfo23.put("Name", "Exclusions");
		 attArrayDetailInfo23.put("Value", request.get("Exclusions"));
	     
		 attArrayDetailInfo24.put("Name", "Exceptions");
		 attArrayDetailInfo24.put("Value", request.get("Exceptions"));
	     
		 attArrayDetailInfo25.put("Name", "Conditions");
		 attArrayDetailInfo25.put("Value", request.get("Conditions"));
		 
	
		 
		 
		 //=====
	     
		 attArrayDetailInfo.put(attArrayDetailInfo1);
		 attArrayDetailInfo.put(attArrayDetailInfo2);
		 attArrayDetailInfo.put(attArrayDetailInfo3);
		 attArrayDetailInfo.put(attArrayDetailInfo4);
		 attArrayDetailInfo.put(attArrayDetailInfo5);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo6);
		 attArrayDetailInfo.put(attArrayDetailInfo7);
		 attArrayDetailInfo.put(attArrayDetailInfo8);
		 attArrayDetailInfo.put(attArrayDetailInfo9);
		 attArrayDetailInfo.put(attArrayDetailInfo10);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo11);
		 attArrayDetailInfo.put(attArrayDetailInfo12);
		 attArrayDetailInfo.put(attArrayDetailInfo13);
		 attArrayDetailInfo.put(attArrayDetailInfo14);
		 attArrayDetailInfo.put(attArrayDetailInfo15);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo16);
		 attArrayDetailInfo.put(attArrayDetailInfo17);
		 attArrayDetailInfo.put(attArrayDetailInfo18);
		 attArrayDetailInfo.put(attArrayDetailInfo19);
		 attArrayDetailInfo.put(attArrayDetailInfo20);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo21);
		 attArrayDetailInfo.put(attArrayDetailInfo22);
		 attArrayDetailInfo.put(attArrayDetailInfo23);
		 attArrayDetailInfo.put(attArrayDetailInfo24);
		 attArrayDetailInfo.put(attArrayDetailInfo25);
	     
	     detailInfoObj.put("AttArray", attArrayDetailInfo);
	     mainArray.put(detailInfoObj);
		 
		 
////////////////////////////////////////////*************Insured Info**************/////////////////////////////////////////
	     JSONObject insuredInfoObj = new JSONObject();
		 JSONArray attArrayinsuredInfo = new JSONArray();
		 JSONObject attArrayinsuredInfo1 = new JSONObject();
		 JSONObject attArrayinsuredInfo2 = new JSONObject();
		 JSONObject attArrayinsuredInfo3 = new JSONObject();
		 JSONObject attArrayinsuredInfo4 = new JSONObject();
		 JSONObject attArrayinsuredInfo5 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo6 = new JSONObject();
		 JSONObject attArrayinsuredInfo7 = new JSONObject();
		 JSONObject attArrayinsuredInfo8 = new JSONObject();
		 JSONObject attArrayinsuredInfo9 = new JSONObject();
		 JSONObject attArrayinsuredInfo10 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo11 = new JSONObject();
		 JSONObject attArrayinsuredInfo12 = new JSONObject();
		 JSONObject attArrayinsuredInfo13 = new JSONObject();
		 JSONObject attArrayinsuredInfo14 = new JSONObject();
		 JSONObject attArrayinsuredInfo15 = new JSONObject();
	     
		 insuredInfoObj.put("GroupName", "Insured Info");
		 insuredInfoObj.put("GroupTag", 2);
		 insuredInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayinsuredInfo1.put("Name", "InsuredNo");
		 attArrayinsuredInfo1.put("Value", request.get("InsuredNo"));
	     
		 attArrayinsuredInfo2.put("Name", "VehicleID");
		 attArrayinsuredInfo2.put("Value", request.get("VehicleID"));
	     
		 attArrayinsuredInfo3.put("Name", "PlateNo");
		 attArrayinsuredInfo3.put("Value", request.get("PlateNo"));
	     
		 attArrayinsuredInfo4.put("Name", "RegNo");
		 attArrayinsuredInfo4.put("Value", request.get("RegNo"));
	     
//		 attArrayinsuredInfo5.put("Name", "RegDate");
//		 attArrayinsuredInfo5.put("Value", dateFormat(request.get("RegDate")));
		 
		 attArrayinsuredInfo5.put("Name", "RegDate");
		 attArrayinsuredInfo5.put("Value", request.get("RegDate"));
		 
		 
		 //---------------
//		 attArrayinsuredInfo6.put("Name", "RegExpDate");
//		 attArrayinsuredInfo6.put("Value", dateFormat(request.get("RegExpDate")));
		 
		 attArrayinsuredInfo6.put("Name", "RegExpDate");
		 attArrayinsuredInfo6.put("Value",request.get("RegExpDate"));
	     
		 attArrayinsuredInfo7.put("Name", "RegMileage");
		 attArrayinsuredInfo7.put("Value", request.get("RegMileage"));
	     
		 attArrayinsuredInfo8.put("Name", "AutoType");
		 attArrayinsuredInfo8.put("Value", request.get("AutoType"));
	     
		 attArrayinsuredInfo9.put("Name", "AutoMake");
		 attArrayinsuredInfo9.put("Value", request.get("AutoMake"));
	     
		 attArrayinsuredInfo10.put("Name", "AutoModel");
		 attArrayinsuredInfo10.put("Value", request.get("AutoModel"));
		 
		 
		 attArrayinsuredInfo11.put("Name", "AutoColor");
		 attArrayinsuredInfo11.put("Value", request.get("AutoColor"));
	     
		 attArrayinsuredInfo12.put("Name", "AutoYear");
		 attArrayinsuredInfo12.put("Value", request.get("AutoYear"));
	     
		 attArrayinsuredInfo13.put("Name", "EngineCap");
		 attArrayinsuredInfo13.put("Value", request.get("EngineCap"));
	     
		 attArrayinsuredInfo14.put("Name", "SeatCap");
		 attArrayinsuredInfo14.put("Value", request.get("SeatCap"));
	     
		 attArrayinsuredInfo15.put("Name", "AutoNote");
		 attArrayinsuredInfo15.put("Value", request.get("AutoNote"));
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo1);
		 attArrayinsuredInfo.put(attArrayinsuredInfo2);
		 attArrayinsuredInfo.put(attArrayinsuredInfo3);
		 attArrayinsuredInfo.put(attArrayinsuredInfo4);
		 attArrayinsuredInfo.put(attArrayinsuredInfo5);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo6);
		 attArrayinsuredInfo.put(attArrayinsuredInfo7);
		 attArrayinsuredInfo.put(attArrayinsuredInfo8);
		 attArrayinsuredInfo.put(attArrayinsuredInfo9);
		 attArrayinsuredInfo.put(attArrayinsuredInfo10);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo11);
		 attArrayinsuredInfo.put(attArrayinsuredInfo12);
		 attArrayinsuredInfo.put(attArrayinsuredInfo13);
		 attArrayinsuredInfo.put(attArrayinsuredInfo14);
		 attArrayinsuredInfo.put(attArrayinsuredInfo15);
		 
		 insuredInfoObj.put("AttArray", attArrayinsuredInfo);
	     mainArray.put(insuredInfoObj);
	     
	     /////////////////////////////////////////////////////////////////////
	     
		 
	     mainObj.put("DataGroup", mainArray);
	      
	     System.out.println(mainObj.toString());
	     return mainObj;
	      
	}

	public static String  dateFormat(String key) throws ParseException {
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date date = originalFormat.parse(key);
		String formattedDate = targetFormat.format(date);
		System.out.println("---------------"+formattedDate);
		return formattedDate;
	}

	public JSONObject updateNaiComData(NaiComResposeDto responseMessage, JSONObject responseJsonObj) throws JSONException, ParseException, JsonMappingException, JsonProcessingException {
		log.info("-------getNaiComNewInsurance---------"+responseMessage.getNaiComData().toString());
		
		Map<String, Object> request = objectMapper.readValue(new Gson().toJson(responseMessage.getNaiComData()), new TypeReference<Map<String, Object>>(){});
//		JSONObject request = new JSONObject(niidResponseObj.getData().toString());
		log.info("-------getNaiComNewInsurance---------"+request);
		
		
		
		JSONObject mainObj = new JSONObject();
		JSONArray mainArray = new JSONArray();
		mainObj.put("SID", naicomSid);
		mainObj.put("Token", naiComToken);
		mainObj.put("Type", "Auto");
		
		System.out.println(responseMessage.getNaiComData().toString());
		////////////////////////////////

		 JSONObject basicInfoObj = new JSONObject();
		 JSONArray attArray = new JSONArray();
		 JSONObject attArrayObj1 = new JSONObject();
		 JSONObject attArrayObj2 = new JSONObject();
		 JSONObject attArrayObj3 = new JSONObject();
		 JSONObject attArrayObj4 = new JSONObject();
		 JSONObject attArrayObj5 = new JSONObject();
		 JSONObject attArrayObj6 = new JSONObject();
		 
		 basicInfoObj.put("GroupName", "Basic Info");
		 basicInfoObj.put("GroupTag", 0);
		 basicInfoObj.put("GroupCount", 0);
		 
		 
	     
		 attArrayObj1.put("Name", "TypeID");
		 attArrayObj1.put("Value", request.get("TypeID"));
	     
		 attArrayObj2.put("Name", "CoverageStartDate");
		 attArrayObj2.put("Value", request.get("CoverageStartDate"));
	     
		 attArrayObj3.put("Name", "CoverageEndDate");
		 attArrayObj3.put("Value", request.get("CoverageEndDate"));
	     
		 attArrayObj4.put("Name", "PolicyInternalID");
		 attArrayObj4.put("Value", request.get("PolicyInternalID"));
	     
		 attArrayObj5.put("Name", "PolicyDescription");
		 attArrayObj5.put("Value", request.get("PolicyDescription"));
		 
		 attArrayObj6.put("Name", "RecorderType");
		 attArrayObj6.put("Value", "Company");
	     
	     attArray.put(attArrayObj1);
	     attArray.put(attArrayObj2);
	     attArray.put(attArrayObj3);
	     attArray.put(attArrayObj4);
	     attArray.put(attArrayObj5);
	     attArray.put(attArrayObj6);
	     
	     basicInfoObj.put("AttArray", attArray);
	     mainArray.put(basicInfoObj);

		//////////////////////////////////////////// *************DETAIL INFO**************/////////////////////////////////////////
		
		 
		 JSONObject detailInfoObj = new JSONObject();
		 JSONArray attArrayDetailInfo = new JSONArray();
		 JSONObject attArrayDetailInfo1 = new JSONObject();
		 JSONObject attArrayDetailInfo2 = new JSONObject();
		 JSONObject attArrayDetailInfo3 = new JSONObject();
		 JSONObject attArrayDetailInfo4 = new JSONObject();
		 JSONObject attArrayDetailInfo5 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo6 = new JSONObject();
		 JSONObject attArrayDetailInfo7 = new JSONObject();
		 JSONObject attArrayDetailInfo8 = new JSONObject();
		 JSONObject attArrayDetailInfo9 = new JSONObject();
		 JSONObject attArrayDetailInfo10 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo11 = new JSONObject();
		 JSONObject attArrayDetailInfo12 = new JSONObject();
		 JSONObject attArrayDetailInfo13 = new JSONObject();
		 JSONObject attArrayDetailInfo14 = new JSONObject();
		 JSONObject attArrayDetailInfo15 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo16 = new JSONObject();
		 JSONObject attArrayDetailInfo17 = new JSONObject();
		 JSONObject attArrayDetailInfo18 = new JSONObject();
		 JSONObject attArrayDetailInfo19 = new JSONObject();
		 JSONObject attArrayDetailInfo20 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo21 = new JSONObject();
		 JSONObject attArrayDetailInfo22 = new JSONObject();
		 JSONObject attArrayDetailInfo23 = new JSONObject();
		 JSONObject attArrayDetailInfo24 = new JSONObject();
		 JSONObject attArrayDetailInfo25 = new JSONObject();
		 
		 
		 detailInfoObj.put("GroupName", "Detail Info");
		 detailInfoObj.put("GroupTag", 1);
		 detailInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayDetailInfo1.put("Name", "CoverageType");
		 attArrayDetailInfo1.put("Value", request.get("CoverageType"));
	     
		 attArrayDetailInfo2.put("Name", "OwnerType");
		 attArrayDetailInfo2.put("Value", request.get("OwnerType"));
	     
		 attArrayDetailInfo3.put("Name", "OwnerLicense");
		 attArrayDetailInfo3.put("Value", request.get("OwnerLicense"));
	     
		 attArrayDetailInfo4.put("Name", "PersonNameLast");
		 attArrayDetailInfo4.put("Value", request.get("PersonNameLast"));
	     
		 attArrayDetailInfo5.put("Name", "PersonNameFirst");
		 attArrayDetailInfo5.put("Value", request.get("PersonNameFirst"));
		 
		 
		 //---------------
		 attArrayDetailInfo6.put("Name", "OrgType");
		 attArrayDetailInfo6.put("Value", request.get("OrgType"));
	     
		 attArrayDetailInfo7.put("Name", "OrgName");
		 attArrayDetailInfo7.put("Value", request.get("OrgName"));
	     
		 attArrayDetailInfo8.put("Name", "OrgID");
		 attArrayDetailInfo8.put("Value", request.get("OrgID"));
	     
		 attArrayDetailInfo9.put("Name", "AddressLine");
		 attArrayDetailInfo9.put("Value", request.get("AddressLine"));
	     
		 attArrayDetailInfo10.put("Name", "CityLGA");
		 attArrayDetailInfo10.put("Value", request.get("CityLGA"));
		 
		 
		 attArrayDetailInfo11.put("Name", "State");
		 attArrayDetailInfo11.put("Value", request.get("State"));
	     
		 attArrayDetailInfo12.put("Name", "PostCode");
		 attArrayDetailInfo12.put("Value", request.get("PostCode"));
	     
		 attArrayDetailInfo13.put("Name", "Phone");
		 attArrayDetailInfo13.put("Value", request.get("Phone"));
	     
		 attArrayDetailInfo14.put("Name", "Email");
		 attArrayDetailInfo14.put("Value", request.get("Email"));
	     
		 attArrayDetailInfo15.put("Name", "InsuredValue");
		 attArrayDetailInfo15.put("Value", request.get("InsuredValue"));
		 
		 attArrayDetailInfo16.put("Name", "Premium");
		 attArrayDetailInfo16.put("Value", request.get("Premium"));
	     
		 attArrayDetailInfo17.put("Name", "CommissionFee");
		 attArrayDetailInfo17.put("Value", request.get("CommissionFee"));
	     
		 attArrayDetailInfo18.put("Name", "ExtraFee");
		 attArrayDetailInfo18.put("Value", request.get("ExtraFee"));
	     
		 attArrayDetailInfo19.put("Name", "PremiumNote");
		 attArrayDetailInfo19.put("Value",request.get("PremiumNote"));
	     
		 attArrayDetailInfo20.put("Name", "Terms");
		 attArrayDetailInfo20.put("Value", request.get("Terms"));
		 
		 
		 attArrayDetailInfo21.put("Name", "Preamble");
		 attArrayDetailInfo21.put("Value", request.get("Preamble"));
	     
		 attArrayDetailInfo22.put("Name", "Endorsements");
		 attArrayDetailInfo22.put("Value", request.get("Endorsements"));
	     
		 attArrayDetailInfo23.put("Name", "Exclusions");
		 attArrayDetailInfo23.put("Value", request.get("Exclusions"));
	     
		 attArrayDetailInfo24.put("Name", "Exceptions");
		 attArrayDetailInfo24.put("Value", request.get("Exceptions"));
	     
		 attArrayDetailInfo25.put("Name", "Conditions");
		 attArrayDetailInfo25.put("Value", request.get("Conditions"));
		 
	
		 
		 
		 //=====
	     
		 attArrayDetailInfo.put(attArrayDetailInfo1);
		 attArrayDetailInfo.put(attArrayDetailInfo2);
		 attArrayDetailInfo.put(attArrayDetailInfo3);
		 attArrayDetailInfo.put(attArrayDetailInfo4);
		 attArrayDetailInfo.put(attArrayDetailInfo5);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo6);
		 attArrayDetailInfo.put(attArrayDetailInfo7);
		 attArrayDetailInfo.put(attArrayDetailInfo8);
		 attArrayDetailInfo.put(attArrayDetailInfo9);
		 attArrayDetailInfo.put(attArrayDetailInfo10);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo11);
		 attArrayDetailInfo.put(attArrayDetailInfo12);
		 attArrayDetailInfo.put(attArrayDetailInfo13);
		 attArrayDetailInfo.put(attArrayDetailInfo14);
		 attArrayDetailInfo.put(attArrayDetailInfo15);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo16);
		 attArrayDetailInfo.put(attArrayDetailInfo17);
		 attArrayDetailInfo.put(attArrayDetailInfo18);
		 attArrayDetailInfo.put(attArrayDetailInfo19);
		 attArrayDetailInfo.put(attArrayDetailInfo20);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo21);
		 attArrayDetailInfo.put(attArrayDetailInfo22);
		 attArrayDetailInfo.put(attArrayDetailInfo23);
		 attArrayDetailInfo.put(attArrayDetailInfo24);
		 attArrayDetailInfo.put(attArrayDetailInfo25);
	     
	     detailInfoObj.put("AttArray", attArrayDetailInfo);
	     mainArray.put(detailInfoObj);
		 
		 
////////////////////////////////////////////*************Insured Info**************/////////////////////////////////////////
	     JSONObject insuredInfoObj = new JSONObject();
		 JSONArray attArrayinsuredInfo = new JSONArray();
		 JSONObject attArrayinsuredInfo1 = new JSONObject();
		 JSONObject attArrayinsuredInfo2 = new JSONObject();
		 JSONObject attArrayinsuredInfo3 = new JSONObject();
		 JSONObject attArrayinsuredInfo4 = new JSONObject();
		 JSONObject attArrayinsuredInfo5 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo6 = new JSONObject();
		 JSONObject attArrayinsuredInfo7 = new JSONObject();
		 JSONObject attArrayinsuredInfo8 = new JSONObject();
		 JSONObject attArrayinsuredInfo9 = new JSONObject();
		 JSONObject attArrayinsuredInfo10 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo11 = new JSONObject();
		 JSONObject attArrayinsuredInfo12 = new JSONObject();
		 JSONObject attArrayinsuredInfo13 = new JSONObject();
		 JSONObject attArrayinsuredInfo14 = new JSONObject();
		 JSONObject attArrayinsuredInfo15 = new JSONObject();
	     
		 insuredInfoObj.put("GroupName", "Insured Info");
		 insuredInfoObj.put("GroupTag", 2);
		 insuredInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayinsuredInfo1.put("Name", "InsuredNo");
		 attArrayinsuredInfo1.put("Value", request.get("InsuredNo"));
	     
		 attArrayinsuredInfo2.put("Name", "VehicleID");
		 attArrayinsuredInfo2.put("Value", request.get("VehicleID"));
	     
		 attArrayinsuredInfo3.put("Name", "PlateNo");
		 attArrayinsuredInfo3.put("Value", request.get("PlateNo"));
	     
		 attArrayinsuredInfo4.put("Name", "RegNo");
		 attArrayinsuredInfo4.put("Value", request.get("RegNo"));
	     
//		 attArrayinsuredInfo5.put("Name", "RegDate");
//		 attArrayinsuredInfo5.put("Value", dateFormat(request.get("RegDate")));
		 
		 attArrayinsuredInfo5.put("Name", "RegDate");
		 attArrayinsuredInfo5.put("Value", request.get("RegDate"));
		 
		 
		 //---------------
//		 attArrayinsuredInfo6.put("Name", "RegExpDate");
//		 attArrayinsuredInfo6.put("Value", dateFormat(request.get("RegExpDate")));
		 
		 attArrayinsuredInfo6.put("Name", "RegExpDate");
		 attArrayinsuredInfo6.put("Value",request.get("RegExpDate"));
	     
		 attArrayinsuredInfo7.put("Name", "RegMileage");
		 attArrayinsuredInfo7.put("Value", request.get("RegMileage"));
	     
		 attArrayinsuredInfo8.put("Name", "AutoType");
		 attArrayinsuredInfo8.put("Value", request.get("AutoType"));
	     
		 attArrayinsuredInfo9.put("Name", "AutoMake");
		 attArrayinsuredInfo9.put("Value", request.get("AutoMake"));
	     
		 attArrayinsuredInfo10.put("Name", "AutoModel");
		 attArrayinsuredInfo10.put("Value", request.get("AutoModel"));
		 
		 
		 attArrayinsuredInfo11.put("Name", "AutoColor");
		 attArrayinsuredInfo11.put("Value", request.get("AutoColor"));
	     
		 attArrayinsuredInfo12.put("Name", "AutoYear");
		 attArrayinsuredInfo12.put("Value", request.get("AutoYear"));
	     
		 attArrayinsuredInfo13.put("Name", "EngineCap");
		 attArrayinsuredInfo13.put("Value", request.get("EngineCap"));
	     
		 attArrayinsuredInfo14.put("Name", "SeatCap");
		 attArrayinsuredInfo14.put("Value", request.get("SeatCap"));
	     
		 attArrayinsuredInfo15.put("Name", "AutoNote");
		 attArrayinsuredInfo15.put("Value", request.get("AutoNote"));
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo1);
		 attArrayinsuredInfo.put(attArrayinsuredInfo2);
		 attArrayinsuredInfo.put(attArrayinsuredInfo3);
		 attArrayinsuredInfo.put(attArrayinsuredInfo4);
		 attArrayinsuredInfo.put(attArrayinsuredInfo5);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo6);
		 attArrayinsuredInfo.put(attArrayinsuredInfo7);
		 attArrayinsuredInfo.put(attArrayinsuredInfo8);
		 attArrayinsuredInfo.put(attArrayinsuredInfo9);
		 attArrayinsuredInfo.put(attArrayinsuredInfo10);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo11);
		 attArrayinsuredInfo.put(attArrayinsuredInfo12);
		 attArrayinsuredInfo.put(attArrayinsuredInfo13);
		 attArrayinsuredInfo.put(attArrayinsuredInfo14);
		 attArrayinsuredInfo.put(attArrayinsuredInfo15);
		 
		 insuredInfoObj.put("AttArray", attArrayinsuredInfo);
	     mainArray.put(insuredInfoObj);
	     
	     /////////////////////////////////////////////////////////////////////
	     
		 
	     mainObj.put("DataGroup", mainArray);
	      
	     System.out.println(mainObj.toString());
	     return mainObj;
	      
	}
	
	public static String  dateFormat1(String key) throws ParseException {
		DateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
		DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
		Date date = originalFormat.parse(key);
		String formattedDate = targetFormat.format(date);
		System.out.println("---------------"+formattedDate);
		return formattedDate;
	}

	public JSONObject schedulerNaiComData(NiidStatusResponseDto niidStatus, JSONObject responseJsonObj) throws Exception {
		log.info("-------getNaiComNewInsurance---------"+niidStatus.getNaiComData().toString());
		
		Map<String, Object> request = objectMapper.readValue(new Gson().toJson(niidStatus.getNaiComData()), new TypeReference<Map<String, Object>>(){});
//		JSONObject request = new JSONObject(niidResponseObj.getData().toString());
		log.info("-------getNaiComNewInsurance---------"+request);
		
		
		
		JSONObject mainObj = new JSONObject();
		JSONArray mainArray = new JSONArray();
		mainObj.put("SID", naicomSid);
		mainObj.put("Token", naiComToken);
		mainObj.put("Type", "Auto");
		
		System.out.println(niidStatus.getNaiComData().toString());
		////////////////////////////////

		 JSONObject basicInfoObj = new JSONObject();
		 JSONArray attArray = new JSONArray();
		 JSONObject attArrayObj1 = new JSONObject();
		 JSONObject attArrayObj2 = new JSONObject();
		 JSONObject attArrayObj3 = new JSONObject();
		 JSONObject attArrayObj4 = new JSONObject();
		 JSONObject attArrayObj5 = new JSONObject();
		 JSONObject attArrayObj6 = new JSONObject();
		 
		 basicInfoObj.put("GroupName", "Basic Info");
		 basicInfoObj.put("GroupTag", 0);
		 basicInfoObj.put("GroupCount", 0);
		 
		 
	     
		 attArrayObj1.put("Name", "TypeID");
		 attArrayObj1.put("Value", request.get("TypeID"));
	     
		 attArrayObj2.put("Name", "CoverageStartDate");
		 attArrayObj2.put("Value", request.get("CoverageStartDate"));
	     
		 attArrayObj3.put("Name", "CoverageEndDate");
		 attArrayObj3.put("Value", request.get("CoverageEndDate"));
	     
		 attArrayObj4.put("Name", "PolicyInternalID");
		 attArrayObj4.put("Value", request.get("PolicyInternalID"));
	     
		 attArrayObj5.put("Name", "PolicyDescription");
		 attArrayObj5.put("Value", request.get("PolicyDescription"));
		 
		 attArrayObj6.put("Name", "RecorderType");
		 attArrayObj6.put("Value", "Company");
	     
	     attArray.put(attArrayObj1);
	     attArray.put(attArrayObj2);
	     attArray.put(attArrayObj3);
	     attArray.put(attArrayObj4);
	     attArray.put(attArrayObj5);
	     attArray.put(attArrayObj6);
	     
	     basicInfoObj.put("AttArray", attArray);
	     mainArray.put(basicInfoObj);

		//////////////////////////////////////////// *************DETAIL INFO**************/////////////////////////////////////////
		
		 
		 JSONObject detailInfoObj = new JSONObject();
		 JSONArray attArrayDetailInfo = new JSONArray();
		 JSONObject attArrayDetailInfo1 = new JSONObject();
		 JSONObject attArrayDetailInfo2 = new JSONObject();
		 JSONObject attArrayDetailInfo3 = new JSONObject();
		 JSONObject attArrayDetailInfo4 = new JSONObject();
		 JSONObject attArrayDetailInfo5 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo6 = new JSONObject();
		 JSONObject attArrayDetailInfo7 = new JSONObject();
		 JSONObject attArrayDetailInfo8 = new JSONObject();
		 JSONObject attArrayDetailInfo9 = new JSONObject();
		 JSONObject attArrayDetailInfo10 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo11 = new JSONObject();
		 JSONObject attArrayDetailInfo12 = new JSONObject();
		 JSONObject attArrayDetailInfo13 = new JSONObject();
		 JSONObject attArrayDetailInfo14 = new JSONObject();
		 JSONObject attArrayDetailInfo15 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo16 = new JSONObject();
		 JSONObject attArrayDetailInfo17 = new JSONObject();
		 JSONObject attArrayDetailInfo18 = new JSONObject();
		 JSONObject attArrayDetailInfo19 = new JSONObject();
		 JSONObject attArrayDetailInfo20 = new JSONObject();
		 
		 JSONObject attArrayDetailInfo21 = new JSONObject();
		 JSONObject attArrayDetailInfo22 = new JSONObject();
		 JSONObject attArrayDetailInfo23 = new JSONObject();
		 JSONObject attArrayDetailInfo24 = new JSONObject();
		 JSONObject attArrayDetailInfo25 = new JSONObject();
		 
		 
		 detailInfoObj.put("GroupName", "Detail Info");
		 detailInfoObj.put("GroupTag", 1);
		 detailInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayDetailInfo1.put("Name", "CoverageType");
		 attArrayDetailInfo1.put("Value", request.get("CoverageType"));
	     
		 attArrayDetailInfo2.put("Name", "OwnerType");
		 attArrayDetailInfo2.put("Value", request.get("OwnerType"));
		 
			if (request.get("OwnerLicense") == null || "".equalsIgnoreCase(request.get("OwnerLicense").toString())) {
				attArrayDetailInfo3.put("Name", "OwnerLicense");
				attArrayDetailInfo3.put("Value", "N/A");
			}else {
				attArrayDetailInfo3.put("Name", "OwnerLicense");
				attArrayDetailInfo3.put("Value", request.get("OwnerLicense"));
			}
			
			if (request.get("PersonNameLast") == null || "".equalsIgnoreCase(request.get("PersonNameLast").toString())) {
				attArrayDetailInfo4.put("Name", "PersonNameLast");
				attArrayDetailInfo4.put("Value", "N/A");
			}else {
				attArrayDetailInfo4.put("Name", "PersonNameLast");
				attArrayDetailInfo4.put("Value", request.get("PersonNameLast"));
			}
			
			if (request.get("PersonNameFirst") == null || "".equalsIgnoreCase(request.get("PersonNameFirst").toString())) {
				attArrayDetailInfo5.put("Name", "PersonNameFirst");
				attArrayDetailInfo4.put("Value", "N/A");
			}else {
				attArrayDetailInfo5.put("Name", "PersonNameFirst");
				attArrayDetailInfo5.put("Value", request.get("PersonNameFirst"));
			}
		
	     
		 
		 
		 
		 //---------------
		 attArrayDetailInfo6.put("Name", "OrgType");
		 attArrayDetailInfo6.put("Value", request.get("OrgType"));
	     
		 attArrayDetailInfo7.put("Name", "OrgName");
		 attArrayDetailInfo7.put("Value", request.get("OrgName"));
	     
		 attArrayDetailInfo8.put("Name", "OrgID");
		 attArrayDetailInfo8.put("Value", request.get("OrgID"));
	     
		 attArrayDetailInfo9.put("Name", "AddressLine");
		 attArrayDetailInfo9.put("Value", request.get("AddressLine"));
	     
		 attArrayDetailInfo10.put("Name", "CityLGA");
		 attArrayDetailInfo10.put("Value", request.get("CityLGA"));
		 
		 
		 attArrayDetailInfo11.put("Name", "State");
		 attArrayDetailInfo11.put("Value", request.get("State"));
	     
		 attArrayDetailInfo12.put("Name", "PostCode");
		 attArrayDetailInfo12.put("Value", request.get("PostCode"));
	     
		 attArrayDetailInfo13.put("Name", "Phone");
		 attArrayDetailInfo13.put("Value", request.get("Phone"));
	     
		 attArrayDetailInfo14.put("Name", "Email");
		 attArrayDetailInfo14.put("Value", request.get("Email"));
	     
		 attArrayDetailInfo15.put("Name", "InsuredValue");
		 attArrayDetailInfo15.put("Value", request.get("InsuredValue"));
		 
		 attArrayDetailInfo16.put("Name", "Premium");
		 attArrayDetailInfo16.put("Value", request.get("Premium"));
	     
		 attArrayDetailInfo17.put("Name", "CommissionFee");
		 attArrayDetailInfo17.put("Value", request.get("CommissionFee"));
	     
		 attArrayDetailInfo18.put("Name", "ExtraFee");
		 attArrayDetailInfo18.put("Value", request.get("ExtraFee"));
	     
		 attArrayDetailInfo19.put("Name", "PremiumNote");
		 attArrayDetailInfo19.put("Value",request.get("PremiumNote"));
	     
		 attArrayDetailInfo20.put("Name", "Terms");
		 attArrayDetailInfo20.put("Value", request.get("Terms"));
		 
		 
		 attArrayDetailInfo21.put("Name", "Preamble");
		 attArrayDetailInfo21.put("Value", request.get("Preamble"));
	     
		 attArrayDetailInfo22.put("Name", "Endorsements");
		 attArrayDetailInfo22.put("Value", request.get("Endorsements"));
	     
		 attArrayDetailInfo23.put("Name", "Exclusions");
		 attArrayDetailInfo23.put("Value", request.get("Exclusions"));
	     
		 attArrayDetailInfo24.put("Name", "Exceptions");
		 attArrayDetailInfo24.put("Value", request.get("Exceptions"));
	     
		 attArrayDetailInfo25.put("Name", "Conditions");
		 attArrayDetailInfo25.put("Value", request.get("Conditions"));
		 
	
		 
		 
		 //=====
	     
		 attArrayDetailInfo.put(attArrayDetailInfo1);
		 attArrayDetailInfo.put(attArrayDetailInfo2);
		 attArrayDetailInfo.put(attArrayDetailInfo3);
		 attArrayDetailInfo.put(attArrayDetailInfo4);
		 attArrayDetailInfo.put(attArrayDetailInfo5);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo6);
		 attArrayDetailInfo.put(attArrayDetailInfo7);
		 attArrayDetailInfo.put(attArrayDetailInfo8);
		 attArrayDetailInfo.put(attArrayDetailInfo9);
		 attArrayDetailInfo.put(attArrayDetailInfo10);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo11);
		 attArrayDetailInfo.put(attArrayDetailInfo12);
		 attArrayDetailInfo.put(attArrayDetailInfo13);
		 attArrayDetailInfo.put(attArrayDetailInfo14);
		 attArrayDetailInfo.put(attArrayDetailInfo15);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo16);
		 attArrayDetailInfo.put(attArrayDetailInfo17);
		 attArrayDetailInfo.put(attArrayDetailInfo18);
		 attArrayDetailInfo.put(attArrayDetailInfo19);
		 attArrayDetailInfo.put(attArrayDetailInfo20);
		 
		 attArrayDetailInfo.put(attArrayDetailInfo21);
		 attArrayDetailInfo.put(attArrayDetailInfo22);
		 attArrayDetailInfo.put(attArrayDetailInfo23);
		 attArrayDetailInfo.put(attArrayDetailInfo24);
		 attArrayDetailInfo.put(attArrayDetailInfo25);
	     
	     detailInfoObj.put("AttArray", attArrayDetailInfo);
	     mainArray.put(detailInfoObj);
		 
		 
////////////////////////////////////////////*************Insured Info**************/////////////////////////////////////////
	     JSONObject insuredInfoObj = new JSONObject();
		 JSONArray attArrayinsuredInfo = new JSONArray();
		 JSONObject attArrayinsuredInfo1 = new JSONObject();
		 JSONObject attArrayinsuredInfo2 = new JSONObject();
		 JSONObject attArrayinsuredInfo3 = new JSONObject();
		 JSONObject attArrayinsuredInfo4 = new JSONObject();
		 JSONObject attArrayinsuredInfo5 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo6 = new JSONObject();
		 JSONObject attArrayinsuredInfo7 = new JSONObject();
		 JSONObject attArrayinsuredInfo8 = new JSONObject();
		 JSONObject attArrayinsuredInfo9 = new JSONObject();
		 JSONObject attArrayinsuredInfo10 = new JSONObject();
		 
		 JSONObject attArrayinsuredInfo11 = new JSONObject();
		 JSONObject attArrayinsuredInfo12 = new JSONObject();
		 JSONObject attArrayinsuredInfo13 = new JSONObject();
		 JSONObject attArrayinsuredInfo14 = new JSONObject();
		 JSONObject attArrayinsuredInfo15 = new JSONObject();
	     
		 insuredInfoObj.put("GroupName", "Insured Info");
		 insuredInfoObj.put("GroupTag", 2);
		 insuredInfoObj.put("GroupCount", 0);
	     
	     
		 attArrayinsuredInfo1.put("Name", "InsuredNo");
		 attArrayinsuredInfo1.put("Value", request.get("InsuredNo"));
	     
		 attArrayinsuredInfo2.put("Name", "VehicleID");
		 attArrayinsuredInfo2.put("Value", request.get("VehicleID"));
	     
		 attArrayinsuredInfo3.put("Name", "PlateNo");
		 attArrayinsuredInfo3.put("Value", request.get("PlateNo"));
	     
		 attArrayinsuredInfo4.put("Name", "RegNo");
		 attArrayinsuredInfo4.put("Value", request.get("RegNo"));
	     
//		 attArrayinsuredInfo5.put("Name", "RegDate");
//		 attArrayinsuredInfo5.put("Value", dateFormat(request.get("RegDate")));
		 
		 attArrayinsuredInfo5.put("Name", "RegDate");
		 attArrayinsuredInfo5.put("Value", request.get("RegDate"));
		 
		 
		 //---------------
//		 attArrayinsuredInfo6.put("Name", "RegExpDate");
//		 attArrayinsuredInfo6.put("Value", dateFormat(request.get("RegExpDate")));
		 
		 attArrayinsuredInfo6.put("Name", "RegExpDate");
		 attArrayinsuredInfo6.put("Value",request.get("RegExpDate"));
	     
		 attArrayinsuredInfo7.put("Name", "RegMileage");
		 attArrayinsuredInfo7.put("Value", request.get("RegMileage"));
	     
		 attArrayinsuredInfo8.put("Name", "AutoType");
		 attArrayinsuredInfo8.put("Value", request.get("AutoType"));
	     
		 attArrayinsuredInfo9.put("Name", "AutoMake");
		 attArrayinsuredInfo9.put("Value", request.get("AutoMake"));
	     
		 attArrayinsuredInfo10.put("Name", "AutoModel");
		 attArrayinsuredInfo10.put("Value", request.get("AutoModel"));
		 
		 
		 attArrayinsuredInfo11.put("Name", "AutoColor");
		 attArrayinsuredInfo11.put("Value", request.get("AutoColor"));
	     
		 attArrayinsuredInfo12.put("Name", "AutoYear");
		 attArrayinsuredInfo12.put("Value", request.get("AutoYear"));
	     
		 attArrayinsuredInfo13.put("Name", "EngineCap");
		 attArrayinsuredInfo13.put("Value", request.get("EngineCap"));
	     
		 attArrayinsuredInfo14.put("Name", "SeatCap");
		 attArrayinsuredInfo14.put("Value", request.get("SeatCap"));
	     
		 attArrayinsuredInfo15.put("Name", "AutoNote");
		 attArrayinsuredInfo15.put("Value", request.get("AutoNote"));
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo1);
		 attArrayinsuredInfo.put(attArrayinsuredInfo2);
		 attArrayinsuredInfo.put(attArrayinsuredInfo3);
		 attArrayinsuredInfo.put(attArrayinsuredInfo4);
		 attArrayinsuredInfo.put(attArrayinsuredInfo5);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo6);
		 attArrayinsuredInfo.put(attArrayinsuredInfo7);
		 attArrayinsuredInfo.put(attArrayinsuredInfo8);
		 attArrayinsuredInfo.put(attArrayinsuredInfo9);
		 attArrayinsuredInfo.put(attArrayinsuredInfo10);
		 
		 attArrayinsuredInfo.put(attArrayinsuredInfo11);
		 attArrayinsuredInfo.put(attArrayinsuredInfo12);
		 attArrayinsuredInfo.put(attArrayinsuredInfo13);
		 attArrayinsuredInfo.put(attArrayinsuredInfo14);
		 attArrayinsuredInfo.put(attArrayinsuredInfo15);
		 
		 insuredInfoObj.put("AttArray", attArrayinsuredInfo);
	     mainArray.put(insuredInfoObj);
	     
	     /////////////////////////////////////////////////////////////////////
	     
		 
	     mainObj.put("DataGroup", mainArray);
	      
	     System.out.println(mainObj.toString());
	     return mainObj;
	      
	}
}

