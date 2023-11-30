package com.erp.api.controller;
import java.math.BigDecimal;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.erp.api.service.MotorApiService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")
@RestController
@Slf4j
public class MotorApiController {
	
	@Autowired
	private MotorApiService motorApiService;
	
	@GetMapping("/apitr/plan")
	public ResponseEntity<ResponseDto> getPlan(@RequestHeader("token") String token) {
		log.info("1111111111------Request landed for /apitr/Plan in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getPlan(token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/quotationById")
	public ResponseEntity<ResponseDto> getQuotationById(@RequestParam Long quotationId,@RequestHeader("token") String token) {
		log.info("1111111111------Request landed for /apitr/quotationById in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getQuotationById(quotationId,token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/apitr/allQuotation")
	public ResponseEntity<ResponseDto> getAllQuotation(@RequestHeader("token") String token,@RequestBody QuotationDto quodobj) {
		log.info("1111111111------Request landed for /apitr/allQuotationin transaction layer"+quodobj.toString());
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getAllQuotation(token,quodobj,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	
	
	@GetMapping("/apitr/policyById")
	public ResponseEntity<ResponseDto> policyById(@RequestHeader("token") String token,@RequestParam Long policyId) {
		log.info("1111111111------Request landed for /api/policyById in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getPolicyById(token,policyId,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/apitr/allPolicy")
	public ResponseEntity<ResponseDto> allAllPolicy(@RequestHeader("token") String token,@RequestBody IssuedPolicyDto issuedobj) {

		log.info("1111111111------Request landed for /apitr/allPolicy in transaction layer Session id" + token+ "Dto " + issuedobj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.allIssuesPolicy(token, issuedobj, responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/cardSummary")
	public ResponseEntity<ResponseDto> getCardSummary(@RequestHeader("token") String token) {
		log.info("1111111111------Request landed for /apitr/cardSummary in transaction layer  token:- "+token);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getCardSummary(token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/searchQuotation")
	public ResponseEntity<ResponseDto> searchQuotation(@RequestHeader("token") String token,@RequestParam String emailMobile) {
		log.info("1111111111------Request landed for /api/searchQuotation in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.searchQuotation(token,emailMobile,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/productList")
	public ResponseEntity<ResponseDto> productList(@RequestHeader("token") String token,@RequestParam String lobId) {
		log.info("1111111111------Request landed for /api/productList in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.productList(token,lobId,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/pending-for-approval")
	public ResponseEntity<ResponseDto> pendingForTrApproval(@RequestHeader("token") String token) {
		log.info("1111111111------Request landed for /api/productList in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.pendingForTrApproval(token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/api/generatequotation")
	public ResponseEntity<ResponseDto> generateQuotation(@RequestHeader("token") String token,@RequestBody InsertQuotationDto motorobj) throws Exception {

		log.info("1111111111------Request landed for /api/generatequotation in transaction layer Session id" + token+ "Dto " + motorobj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.generateQuotation(token, motorobj, responseMessage);
		log.info("**********Response is sent for /api/generatequotation at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/api/generatepolicy")
	public ResponseEntity<ResponseDto> generatePolicy(@RequestHeader("token") String token,@RequestBody InsertPolicyDto insertobj) throws JSONException, JsonMappingException, JsonProcessingException {

		log.info("1111111111------Request landed for /api/generatepolicy in transaction layer Session id" + token+ "Dto " + insertobj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.generatePolicy(token, insertobj, responseMessage);
		log.info("**********Response is sent for /api/generatepolicy at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/api/generatedashboardpolicy")
	public ResponseEntity<ResponseDto> generatedashboardpolicy(@RequestHeader("token") String token,@RequestBody InsertDashPolicyTrDto insertobj) throws JSONException, JsonMappingException, JsonProcessingException {

		log.info("1111111111------Request landed for /api/generatepolicy in transaction layer Session id" + token+ "Dto " + insertobj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.generatedashboardpolicy(token, insertobj, responseMessage);
		log.info("**********Response is sent for /api/generatepolicy at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/apitr/update-policy")
	public ResponseEntity<ResponseDto> updatepolicy(@RequestHeader("token") String token,@RequestBody UpdatePolicyDto updateObj) throws JSONException, JsonMappingException, JsonProcessingException {

		log.info("1111111111------Request landed for /api/update-policy in transaction layer Session id" + token+ "Dto " + updateObj.toString());
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.updatepolicy(token, updateObj, responseMessage);
		log.info("**********Response is sent for /api/update-policy at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	// this api use by rahul and this api only check niid and naicom. we are not checking kyc data.
	@PostMapping("/apitr/scheduler-policy")
	public ResponseEntity<ResponseDto> schedulerPolicy(@RequestHeader("token") String token,@RequestBody SchedulerPolicyDto schedulerObj) throws Exception {

		log.info("1111111111------Request landed for /api/scheduler-policy in transaction layer Session id" + token+ "Dto " + schedulerObj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.schedulerPolicy(token, schedulerObj, responseMessage);
		log.info("**********Response is sent for /api/scheduler-policy at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping(value = "/api/verification-vehicle")
	public ResponseEntity<ResponseDto> verificationVehicleTr(@RequestHeader("token") String token,@RequestParam("searchValue") String searchValue,@RequestParam("searchType") String searchType) throws Exception {

		log.info("1111111111------Request landed for /verification-vehicle in transaction layer Session id" + token+ "searchType " + searchType+"searchValue"+searchValue);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.verificationVehicleService(token, searchValue,searchType,responseMessage);
		log.info("**********Response is sent for /verification-vehicle at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);

	}
	
	@GetMapping(value = "/api/city-state")
	public ResponseEntity<ResponseDto> cityStateTr(@RequestHeader("token") String token,@RequestParam("serviceType") String serviceType) throws Exception {

		log.info("1111111111------Request landed for /city-state in transaction layer Token id" + token+ "serviceType " + serviceType);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.cityStateService(token, serviceType,responseMessage);
		log.info("**********Response is sent for /city-state at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);

	}
	
	@GetMapping(value = "/api/get-title")
	public ResponseEntity<ResponseDto> getTitleTr() throws Exception {

		log.info("1111111111------Request landed for /get-title in transaction layer Token id" );
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getTitleService(responseMessage);
		log.info("**********Response is sent for /get-title at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);

	}
	
	@PostMapping("/api/endorsement-policy")
	public ResponseEntity<ResponseDto> endorsementPolicyTr(@RequestHeader("token") String token,@RequestBody EndorsementPolicyDto endorsementobj) throws JSONException, JsonMappingException, JsonProcessingException {

		log.info("1111111111------Request landed for /api/generatepolicy in transaction layer Session id" + token+ "Dto " + endorsementobj);
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.endorsementPolicyTr(token, endorsementobj, responseMessage);
		log.info("**********Response is sent for /api/generatepolicy at Txn Manager**********");
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@GetMapping("/apitr/get-insured-details")
	public ResponseEntity<ResponseDto> getInsuredDetails(@RequestParam Long quotationId,@RequestHeader("token") String token) {
		log.info("1111111111------Request landed for /get-insured-details in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getInsuredDetails(quotationId,token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	@GetMapping("/apitr/get-plan-premium")
	public ResponseEntity<ResponseDto> getplanPremium(@RequestHeader("token") String token,@RequestHeader long lobId,@RequestHeader long productId,@RequestHeader long planId,@RequestHeader BigDecimal amount) {
		log.info("1111111111------Request landed for /get-plan-premium in transaction layer");
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getplanPremium(lobId,productId,planId,amount,token,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
	
	@PostMapping("/apitr/renewal-list")
	public ResponseEntity<ResponseDto> getRenewalList(@RequestHeader("token") String token,@RequestBody RenewalDto renewalobj) {
		log.info("1111111111------Request landed for /apitr/renewal-list transaction layer"+renewalobj.toString());
		ResponseDto responseMessage = new ResponseDto();
		responseMessage = motorApiService.getRenewalList(token,renewalobj,responseMessage);
		return new ResponseEntity<>(responseMessage, HttpStatus.OK);
	}
}
