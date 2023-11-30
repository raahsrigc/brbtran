package com.erp.api.utils;

import java.util.concurrent.ThreadLocalRandom;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.erp.api.dto.InsertDashPolicyTrDto;
import com.erp.api.dto.InsertPolicyDto;
import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.dto.NaiComResposeDto;
import com.erp.api.dto.NiidStatusResponseDto;
import com.erp.api.dto.ResponseDto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class MotorApiUtils {
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private JsonBuilders jsonBuilder;
	
	@Value("${api.kyc_details}")
	private String kycurl;
	
	@Value("${insert.policy.niid}")
	private String insertPolicyUrl;
	
	@Value("${policy.certificate.api}")
	private String policyCertificate;
	
	@Value("${policy.quotation.api}")
	private String policyQuotation;
	
	@Value("${policy.document.api}")
	private String policyDocument;
	
	
	@Value("${niid.baseUrl}")
	private String niidBaseUrl;
	
	@Value("${niid.userName}")
	private String niidUserName;
	
	@Value("${niid.password}")
	private String niidPassword;
	
	@Value("${insert.policy.naicom}")
	private String naicomInsertPolicyUrl;
	
	

	
	
	public ResponseEntity<String> insertKycDetails(InsertPolicyDto insertobj) throws JSONException {
		JSONObject jsonObject1 = new JSONObject();
		ResponseEntity<String> resThirdParty = null;
		try {
			jsonObject1.put("trackingReference", ThreadLocalRandom.current().nextInt());
			jsonObject1.put("firstName", insertobj.getFirstName());
			jsonObject1.put("lastName", insertobj.getLastName());
			jsonObject1.put("idType", insertobj.getIdType());
			jsonObject1.put("phoneNumber", insertobj.getMobileNumber());
			jsonObject1.put("dob", insertobj.getDob());
			jsonObject1.put("sessionId", "");
			jsonObject1.put("idNumber", insertobj.getIdNumber());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
			log.info(kycurl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATING REQUEST BODY FOR KYC ====:- " + httpEntity);
			resThirdParty = restTemplate.exchange(kycurl, HttpMethod.POST, httpEntity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM THIRDPARTY FOR KYC STATUS====:-" + httpEntity);
		} catch (Exception e) {
			log.error("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidCreatePolicyUtils" + e);
			return null;
		}
		return resThirdParty;
	}
	
	
	
	public ResponseEntity<String> niidCreatePolicyUtils(Object data) {
		ResponseEntity<String> responseFrom=null;
		try {
			System.out.println("---------------get response from db layer for creating niid request---"+data.toString());
			Gson gson = new GsonBuilder().create();
			JsonObject pdateSmiGroup = gson.toJsonTree(data).getAsJsonObject();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
			log.info(insertPolicyUrl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATE REQUEST BODY FOR NIID WHILE CREATING POLICY :- " + entity);
			responseFrom = restTemplate.exchange(insertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM NIID WHILE CREATING POLICY :-" + responseFrom);

		} catch (Exception e) {
			log.error("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidCreatePolicyUtils" + e);
			return null;
		}
		return responseFrom;
	}
	
	public ResponseEntity<String> naiComCreatePolicyUtils(NaiComDBResposeDto naiDbResponse) {
		ResponseEntity<String> responseFrom=null;
		try {
			JSONObject responseJsonObj = new JSONObject();
			responseJsonObj=jsonBuilder.getNaiComNewInsurance(naiDbResponse,responseJsonObj);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(responseJsonObj.toString(), headers);
			log.info(naicomInsertPolicyUrl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------creating request before hitting create insurence from naicom =======" + entity);
			responseFrom = restTemplate.exchange(naicomInsertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&------- get response from NAICOM CREATE INSURENCE ==== " + responseFrom);
			
		} catch (Exception e) {
			log.error("getting error from saveInsureMaterialUtils method " + e);
			return null;
		}
		return responseFrom;
	}
	
	public ResponseEntity<String> generatePolicyDoc(long policyId, String token) {
		JSONObject jsonObject1 = new JSONObject();
		log.info("=============================::::-" + policyId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("token", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
		log.info(policyCertificate + "---========creating request body for get policy certificate -- " + httpEntity);
		ResponseEntity<String> resThirdParty = restTemplate.exchange(policyCertificate + policyId, HttpMethod.GET, httpEntity,String.class);
		log.info("========get response  for policy certificate status -- " + httpEntity);

		return resThirdParty;
	}
	public String getNiidVehicleDetails(String searchValue, String searchType, ResponseDto responseMessage) {

		JSONObject requestedQuotationListUtils = new JSONObject();
		String responseFrom="";
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(requestedQuotationListUtils.toString(), headers);
			String tranUrl = niidBaseUrl + "Verification?sv="+searchValue+"&st="+searchType+"&un="+niidUserName+"&pw="+niidPassword;
			log.info(tranUrl + "========creating request body for niid vehicle details " + entity);
			responseFrom = restTemplate.exchange(tranUrl, HttpMethod.GET, entity, String.class).getBody();
			log.info("========== get response from transaction layer for search quotation list " + responseFrom);

//			JSONObject jsonObjectrequestfetchByIdSmi = new JSONObject(responseFrom);
//			responseMessage.setStatus(jsonObjectrequestfetchByIdSmi.optBoolean("status"));
//			responseMessage.setMessage(jsonObjectrequestfetchByIdSmi.optString("message"));
//			responseMessage.setResponseCode(jsonObjectrequestfetchByIdSmi.optString("responseCode"));
//			String s = jsonObjectrequestfetchByIdSmi.optString("data");
//			ObjectMapper mapper = new ObjectMapper();
//			Object oo = mapper.readValue(s, Object.class);
//			responseMessage.setData(oo);

		} catch (Exception e) {
			log.error("getting error from searchQuotationListUtils method " + e);
		}
		return responseFrom;
	
		
	}
	
	
	

//	public ResponseEntity<String> updateKycDetaisl(UpdatePolicyDto updateObj) throws JSONException {
//		JSONObject jsonObject1 = new JSONObject();
//
//		jsonObject1.put("trackingReference", ThreadLocalRandom.current().nextInt());
//		jsonObject1.put("firstName", updateObj.getFirstName());
//		jsonObject1.put("lastName", updateObj.getLastName());
//		jsonObject1.put("idType", updateObj.getIdType());
//		jsonObject1.put("phoneNumber", updateObj.getMobileNumber());
//		jsonObject1.put("dob", updateObj.getDob());
//		jsonObject1.put("sessionId", "");
//		jsonObject1.put("idNumber", updateObj.getIdNumber());
//
//		HttpHeaders headers = new HttpHeaders();
//		headers.setContentType(MediaType.APPLICATION_JSON);
//		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
//		log.info(kycurl + "========creating request body for check kyc status -- " + httpEntity);
//		ResponseEntity<String> resThirdParty = restTemplate.exchange(kycurl, HttpMethod.POST, httpEntity, String.class);
//		log.info("========get response  for check kyc status -- " + httpEntity);
//
//		return resThirdParty;
//	}
	
	public ResponseEntity<String> niidUpdatePolicyUtils(Object niidData) {
		ResponseEntity<String> responseFrom=null;
		try {
			System.out.println("---------------get response from db layer for creating niid request---"+niidData.toString());
			Gson gson = new GsonBuilder().create();
			JsonObject pdateSmiGroup = gson.toJsonTree(niidData).getAsJsonObject();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
			log.info(insertPolicyUrl + "========creating request body for create policy from niid -- " + entity);
			responseFrom = restTemplate.exchange(insertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("========== NIID CREATE POLICY RESPONSE FROM NIID ================" + responseFrom);

		} catch (Exception e) {
			log.error("getting error from saveInsureMaterialUtils method " + e);
			return null;
		}
		return responseFrom;
	}
	
	
	public ResponseEntity<String> naiComUpdatePolicyUtils(NaiComResposeDto responseMessage) {
		log.info("=====================update naicom======================="+responseMessage.getNaiComData());
		ResponseEntity<String> responseFrom=null;
		try {
			JSONObject responseJsonObj = new JSONObject();
			responseJsonObj=jsonBuilder.updateNaiComData(responseMessage,responseJsonObj);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(responseJsonObj.toString(), headers);
			log.info(naicomInsertPolicyUrl + "========creating request before hitting create insurence from naicom =======" + entity);
			responseFrom = restTemplate.exchange(naicomInsertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("========== get response from NAICOM CREATE INSURENCE ==== " + responseFrom);
			
		} catch (Exception e) {
			log.error("getting error from saveInsureMaterialUtils method " + e);
			return null;
		}
		return responseFrom;
	}



	public ResponseEntity<String> generatePolicyDocumant(long policyId, String token) {
		JSONObject jsonObject1 = new JSONObject();
		log.info("=============================::::-" + policyId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("token", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
		log.info(policyCertificate + "---========creating request body for get policy Document -- " + httpEntity);
		ResponseEntity<String> resThirdParty = restTemplate.exchange(policyDocument + policyId, HttpMethod.GET, httpEntity,String.class);
		log.info("========get response  for policy Document status -- " + httpEntity);

		return resThirdParty;
	}



	public ResponseEntity<String> niidSchedulerPolicyUtils(Object niidData) {
		ResponseEntity<String> responseFrom=null;
		try {
			System.out.println("---------------get response from db layer for creating niid request---"+niidData.toString());
			Gson gson = new GsonBuilder().create();
			JsonObject pdateSmiGroup = gson.toJsonTree(niidData).getAsJsonObject();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
			log.info(insertPolicyUrl + "========creating request body for create policy from niid -- " + entity);
			responseFrom = restTemplate.exchange(insertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("========== NIID CREATE POLICY RESPONSE FROM NIID ================" + responseFrom);

		} catch (Exception e) {
			log.error("getting error from saveInsureMaterialUtils method " + e);
			return null;
		}
		return responseFrom;
	}



//	public ResponseEntity<String> naiComSchedulerPolicyUtils(SchedulerResposeDto scherResponseObj) {
//		log.info("=====================scheduler naicom======================="+scherResponseObj.getNaiComData());
//		ResponseEntity<String> responseFrom=null;
//		try {
//			JSONObject responseJsonObj = new JSONObject();
//			responseJsonObj=jsonBuilder.schedulerNaiComData(scherResponseObj,responseJsonObj);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//
//			HttpEntity<String> entity = new HttpEntity<String>(responseJsonObj.toString(), headers);
//			log.info(naicomInsertPolicyUrl + "========creating request before hitting create insurence from naicom =======" + entity);
//			responseFrom = restTemplate.exchange(naicomInsertPolicyUrl, HttpMethod.POST, entity, String.class);
//			log.info("========== get response from NAICOM CREATE INSURENCE ==== " + responseFrom);
//			
//		} catch (Exception e) {
//			log.error("getting error from saveInsureMaterialUtils method " + e);
//			return null;
//		}
//		return responseFrom;
//	}



	public ResponseEntity<String> naiComSchedulerPolicyUtils(NiidStatusResponseDto niidStatus) {
		log.info("=====================scheduler naicom======================="+niidStatus.getNaiComData());
		ResponseEntity<String> responseFrom=null;
		try {
			JSONObject responseJsonObj = new JSONObject();
			responseJsonObj=jsonBuilder.schedulerNaiComData(niidStatus,responseJsonObj);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(responseJsonObj.toString(), headers);
			log.info(naicomInsertPolicyUrl + "========creating request before hitting create insurence from naicom =======" + entity);
			responseFrom = restTemplate.exchange(naicomInsertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("========== get response from NAICOM CREATE INSURENCE ==== " + responseFrom);
			
		} catch (Exception e) {
			log.error("getting error from saveInsureMaterialUtils method " + e);
			return null;
		}
		return responseFrom;
	}



	public ResponseEntity<String> generatePolicyQuotation(int quotationId, String token) {
		JSONObject jsonObject1 = new JSONObject();
		log.info("=============================::::-" + quotationId);
		HttpHeaders headers = new HttpHeaders();
		headers.add("token", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
		log.info(policyCertificate + "---========creating request body for get policy certificate -- " + httpEntity);
		ResponseEntity<String> resThirdParty = restTemplate.exchange(policyQuotation + quotationId, HttpMethod.GET, httpEntity,String.class);
		log.info("========get response  for policy policyQuotation status -- " + httpEntity);

		return resThirdParty;
	}



	public ResponseEntity<String> niidB2BCreatePolicyUtils(Object niidData) {
		ResponseEntity<String> responseFrom=null;
		try {
			System.out.println("---------------get response from db layer for creating B2B niid request---"+niidData.toString());
			Gson gson = new GsonBuilder().create();
			JsonObject pdateSmiGroup = gson.toJsonTree(niidData).getAsJsonObject();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
			log.info(insertPolicyUrl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATE REQUEST BODY FOR NIID WHILE CREATING B2B MOTOR POLICY :- " + entity);
			responseFrom = restTemplate.exchange(insertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM NIID WHILE CREATING B2B MOTOR POLICY :-" + responseFrom);

		} catch (Exception e) {
			log.error("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidB2BCreatePolicyUtils" + e);
			return null;
		}
		return responseFrom;
	}



	public ResponseEntity<String> niidB2BUpdatePolicyUtils(Object niidData) {
		ResponseEntity<String> responseFrom=null;
		try {
			System.out.println("---------------get response from db layer for creating B2B niid request---"+niidData.toString());
			Gson gson = new GsonBuilder().create();
			JsonObject pdateSmiGroup = gson.toJsonTree(niidData).getAsJsonObject();

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
			log.info(insertPolicyUrl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATE REQUEST BODY FOR NIID WHILE CREATING B2B MOTOR POLICY :- " + entity);
			responseFrom = restTemplate.exchange(insertPolicyUrl, HttpMethod.POST, entity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM NIID WHILE CREATING B2B MOTOR POLICY :-" + responseFrom);

		} catch (Exception e) {
			log.error("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidB2BCreatePolicyUtils" + e);
			return null;
		}
		return responseFrom;
	}



	public ResponseEntity<String> insertDashKycDetails(InsertDashPolicyTrDto insertobj) {
		JSONObject jsonObject1 = new JSONObject();
		ResponseEntity<String> resThirdParty = null;
		try {
			jsonObject1.put("trackingReference", ThreadLocalRandom.current().nextInt());
			jsonObject1.put("firstName", insertobj.getFirstName());
			jsonObject1.put("lastName", insertobj.getLastName());
			jsonObject1.put("idType", insertobj.getIdType());
			jsonObject1.put("phoneNumber", insertobj.getMobileNumber());
			jsonObject1.put("dob", insertobj.getDob());
			jsonObject1.put("sessionId", "");
			jsonObject1.put("idNumber", insertobj.getIdNumber());

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
			log.info(kycurl + "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATING REQUEST BODY FOR KYC ====:- " + httpEntity);
			resThirdParty = restTemplate.exchange(kycurl, HttpMethod.POST, httpEntity, String.class);
			log.info("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM THIRDPARTY FOR KYC STATUS====:-" + httpEntity);
		} catch (Exception e) {
			log.error("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidCreatePolicyUtils" + e);
			return null;
		}
		return resThirdParty;
	}

}
