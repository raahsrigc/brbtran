package com.erp.api.service;
import java.math.BigDecimal;

import org.json.JSONException;

import com.erp.api.dto.EndorsementPolicyDto;
import com.erp.api.dto.InsertDashPolicyTrDto;
import com.erp.api.dto.InsertPolicyDto;
import com.erp.api.dto.InsertQuotationDto;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.QuotationDto;
import com.erp.api.dto.RenewalDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SchedulerPolicyDto;
import com.erp.api.dto.UpdatePolicyDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface MotorApiService {

	ResponseDto getPlan(String token, ResponseDto responseMessage);

	
	ResponseDto getAllQuotation(String token, QuotationDto quodobj, ResponseDto responseMessage);

	ResponseDto getPolicyById(String token, Long policyId, ResponseDto responseMessage);

	ResponseDto allIssuesPolicy(String token, IssuedPolicyDto issuedobj, ResponseDto responseMessage);

	

	ResponseDto getCardSummary(String token, ResponseDto responseMessage);


	ResponseDto searchQuotation(String token, String emailMobile, ResponseDto responseMessage);

	ResponseDto productList(String token, String lobId, ResponseDto responseMessage);

	ResponseDto pendingForTrApproval(String token, ResponseDto responseMessage);

	ResponseDto generateQuotation(String token, InsertQuotationDto motorobj, ResponseDto responseMessage) throws  Exception;

	ResponseDto generatePolicy(String token, InsertPolicyDto insertobj, ResponseDto responseMessage) throws JSONException, JsonMappingException, JsonProcessingException;

	ResponseDto verificationVehicleService(String token, String searchValue, String searchType,
			ResponseDto responseMessage) throws  Exception;

	ResponseDto updatepolicy(String token, UpdatePolicyDto updateObj, ResponseDto responseMessage) throws JsonMappingException, JsonProcessingException, JSONException;

	ResponseDto schedulerPolicy(String token, SchedulerPolicyDto schedulerObj,
			ResponseDto responseMessage) throws Exception;

	ResponseDto cityStateService(String token, String serviceType, ResponseDto responseMessage);

	ResponseDto getTitleService(ResponseDto responseMessage);

	ResponseDto getQuotationById(Long quotationId, String token, ResponseDto responseMessage);


	ResponseDto endorsementPolicyTr(String token, EndorsementPolicyDto endorsementobj, ResponseDto responseMessage);


	ResponseDto getInsuredDetails(Long quotationId, String token, ResponseDto responseMessage);


	ResponseDto generatedashboardpolicy(String token, InsertDashPolicyTrDto insertobj, ResponseDto responseMessage) throws JSONException, JsonMappingException, JsonProcessingException;


	ResponseDto getplanPremium(long lobId, long productId, long planId, BigDecimal amount, String token,
			ResponseDto responseMessage);


	ResponseDto getRenewalList(String token, RenewalDto renewalobj, ResponseDto responseMessage);

	

	

}
