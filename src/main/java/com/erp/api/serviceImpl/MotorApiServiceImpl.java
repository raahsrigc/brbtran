package com.erp.api.serviceImpl;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.erp.api.dao.MotorApiDao;
import com.erp.api.dto.EndorsementPolicyDto;
import com.erp.api.dto.InsertDashPolicyTrDto;
import com.erp.api.dto.InsertPolicyDto;
import com.erp.api.dto.InsertQuotationDto;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.KycResponseDto;
import com.erp.api.dto.NaiComResposeDto;
import com.erp.api.dto.NiidResponseDto;
import com.erp.api.dto.NiidStatusResponseDto;
import com.erp.api.dto.PolicyDto;
import com.erp.api.dto.PolicyResponseDto;
import com.erp.api.dto.QuotationDto;
import com.erp.api.dto.RenewalDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SchedulerPolicyDto;
import com.erp.api.dto.SchedulerResposeDto;
import com.erp.api.dto.UpdateNiidRequestDto;
import com.erp.api.dto.UpdatePolicyDto;
import com.erp.api.service.MotorApiService;
import com.erp.api.utils.MotorApiUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MotorApiServiceImpl implements MotorApiService {

	@Autowired
	private MotorApiDao motorDao;
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private MotorApiUtils motorUtils;
	
	@Value("${niidnai.start}")
	private int niidWork;
	
	@Value("${niidnai.start.document}")
	private int niidWorkDoc;
	
	
	

	@Override
	public ResponseDto getPlan(String token, ResponseDto responseMessage) {
		return motorDao.getPlan(token,responseMessage);
	}

	@Override
	public ResponseDto getQuotationById(Long quotationId, String token, ResponseDto responseMessage) {
		return motorDao.getQuotationById(quotationId,token,responseMessage);
	}

	@Override
	public ResponseDto getPolicyById(String token, Long policyId, ResponseDto responseMessage) {
		return motorDao.getPolicyById(token,policyId,responseMessage);
	}

	@Override
	public ResponseDto allIssuesPolicy(String token, IssuedPolicyDto issuedobj, ResponseDto responseMessage) {
		return motorDao.allIssuesPolicy(token,issuedobj,responseMessage);
	}


	@Override
	public ResponseDto getAllQuotation(String token, QuotationDto quodobj, ResponseDto responseMessage) {
		
		return motorDao.getAllQuotation(token,quodobj,responseMessage);
	}

	@Override
	public ResponseDto getCardSummary(String token, ResponseDto responseMessage) {
		
		return motorDao.getCardSummary(token,responseMessage);
	}

	@Override
	public ResponseDto searchQuotation(String token, String emailMobile, ResponseDto responseMessage) {
		
		return motorDao.searchQuotation(token,emailMobile,responseMessage);
	}

	@Override
	public ResponseDto productList(String token, String lobId, ResponseDto responseMessage) {
		
		return motorDao.productList(token,lobId,responseMessage);
	}

	@Override
	public ResponseDto pendingForTrApproval(String token, ResponseDto responseMessage) {
		
		return motorDao.pendingForTrApproval(token,responseMessage);
	}
	
	@Override
	public ResponseDto cityStateService(String token, String serviceType, ResponseDto responseMessage) {
		return motorDao.cityStateDao(token,serviceType,responseMessage);
	}

	@Override
	public ResponseDto getTitleService(ResponseDto responseMessage) {
		return motorDao.getTitleDao(responseMessage);
	}

	@Override
	public ResponseDto generateQuotation(String token, InsertQuotationDto motorobj,ResponseDto responseMessage) throws Exception {
		
		responseMessage=motorDao.generateQuotation(token,motorobj,responseMessage);
		if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
			responseMessage.setStatus(false);
			responseMessage.setData(null);
			responseMessage.setMessage(responseMessage.getMessage());
			responseMessage.setResponseCode(responseMessage.getResponseCode());
			return responseMessage;
		}
		Gson gson = new GsonBuilder().create();
		JsonObject policyDetails = gson.toJsonTree(responseMessage.getData()).getAsJsonObject();
		JSONObject jsonObj = new JSONObject(policyDetails.toString());
		int quotationId=jsonObj.optInt("QUOTATION_ID");
		String quotationNumber=jsonObj.optString("QUOTATION_NUMBER");
		String premiumAmount=jsonObj.optString("PREMIUM_AMOUNT");
		
		ResponseEntity<String> generateDoc = motorUtils.generatePolicyQuotation(quotationId, token);
		
		String generateCertificate = generateDoc.getBody();
		JSONObject jsonObject1 = new JSONObject(generateCertificate);
		String certificateData = jsonObject1.optString("data");
		JSONObject jsonObject2 = new JSONObject(certificateData);
		String policyCertificate = jsonObject2.optString("certificateUrl");
		
		JSONObject doc_cer = new JSONObject();
		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) {
			doc_cer.put("QUOTATION_DOC", "");
		}else {
			doc_cer.put("QUOTATION_DOC", policyCertificate);
		}
		
		doc_cer.put("QUOTATION_ID", quotationId);
		doc_cer.put("QUOTATION_NUMBER", quotationNumber);
		doc_cer.put("PREMIUM_AMOUNT", premiumAmount);
		responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
		
		return responseMessage;
	}


	
	
	@Override
	public ResponseDto generatePolicy(String token, InsertPolicyDto insertobj, ResponseDto responseMessage)throws JSONException, JsonMappingException, JsonProcessingException {

		KycResponseDto kycResponse = new KycResponseDto();
		kycResponse = motorDao.insertInsureDetails(token, insertobj, kycResponse);

		PolicyResponseDto policyResponse = new PolicyResponseDto();
		PolicyDto policyDtoObj = new PolicyDto();
		if (kycResponse.getKycStatus() == 1) {
			policyResponse = motorDao.insertAllRequest(token, insertobj, 1, "KYC DATA has been updated Successfully !",policyResponse);
			if (!"200".equalsIgnoreCase(policyResponse.getResponseCode())) {
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage(policyResponse.getMessage());
				responseMessage.setResponseCode(policyResponse.getResponseCode());
				return responseMessage;
			}
			
		} else {
			ResponseEntity<String> kycThirdPartyResponse =   motorUtils.insertKycDetails(insertobj);

			if (kycThirdPartyResponse == null || "".equalsIgnoreCase(kycThirdPartyResponse.toString())) {

				policyResponse = motorDao.insertAllRequest(token, insertobj, 102, "Kyc status is pending !",policyResponse);
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage("KYC data successfully submitted.  Updates by WebHook are coming!");
				responseMessage.setResponseCode("KYC-9001");
				return responseMessage;
			}

			String body = kycThirdPartyResponse.getBody();
			JSONObject jsonObject = new JSONObject(body);
			int kycidStatus = jsonObject.optInt("idStatus");
			String kycmessage = jsonObject.optString("message");
			policyResponse = motorDao.insertAllRequest(token, insertobj, kycidStatus, kycmessage, policyResponse);
			log.info(" 7777777777------- INSERT KYC RESPONSE FOR GENERATE POLICY  ********************"+ responseMessage);
			if (!"200".equalsIgnoreCase(policyResponse.getResponseCode())) {
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage(policyResponse.getMessage());
				responseMessage.setResponseCode(policyResponse.getResponseCode());
				return responseMessage;
			}
		}
		if (policyResponse.getData() != null) {
			Gson gson = new GsonBuilder().create();
			JsonObject policyDetails = gson.toJsonTree(policyResponse.getData()).getAsJsonObject();
			JSONObject jsonObj = new JSONObject(policyDetails.toString());
			policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
			policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
		}

		// ******************************************************NIID Call
		String policyCertificate = "";
		String niidResCode = "";
		if (policyResponse.getKycStatus() == 1) {

			if (niidWork == 1) {

				responseMessage = motorDao.insertNiidRequestDataDao("NIID", "/api/motorinsurance", token,policyResponse.getNiidData().toString(), responseMessage);
				log.info(" 7777777777------- insertThirdPartyRequestDao method is completed ********************");
				if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(responseMessage.getMessage());
					responseMessage.setResponseCode(responseMessage.getResponseCode());
					return responseMessage;
				}

				NiidResponseDto niidResponseObj = new NiidResponseDto();
				ResponseEntity<String> thirdPartyResponse = motorUtils.niidB2BCreatePolicyUtils(policyResponse.getNiidData());
				if (thirdPartyResponse == null || "".equalsIgnoreCase(thirdPartyResponse.toString())) {
					log.info(" †††††††††††† NIID IS GIVING NULL VALUE PLEASE CHECK LOG ");
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, policyDtoObj.getPolicyId(),"Response is pending from the regulators.", "102", responseMessage);
//					responseMessage.setStatus(false);
//					responseMessage.setData(null);
//					responseMessage.setMessage("KYC data successfully submitted.  Updates by WebHook are coming!");
//					responseMessage.setResponseCode("KYC-9001");
//					return responseMessage;
				} else {

					log.info(" 7777777777------- niidCreatePolicyUtils method is completed ********************");
					String niidbody = thirdPartyResponse.getBody();
					JSONObject niidjsonObject = new JSONObject(niidbody);
					String niidResMesg = niidjsonObject.optString("ResponseMessage");
					niidResCode = niidjsonObject.optString("ResponseCode");

					responseMessage = motorDao.insertThirdPartyResponseDataDao("NIID", "/api/motorinsurance", token,niidbody, responseMessage);
					log.info(" 7777777777------- insertThirdPartyResponseDao method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(responseMessage.getMessage());
						responseMessage.setResponseCode(responseMessage.getResponseCode());
						return responseMessage;
					}

//final Save
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, policyDtoObj.getPolicyId(),niidResMesg, niidResCode, responseMessage);
					log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(responseMessage.getMessage());
						responseMessage.setResponseCode(responseMessage.getResponseCode());
						return responseMessage;
					}
				}
				if ("100".equalsIgnoreCase(niidResCode)) {
					ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
					String generateCertificate = generateDoc.getBody();
					JSONObject jsonObject1 = new JSONObject(generateCertificate);
					String certificateData = jsonObject1.optString("data");
					JSONObject jsonObject2 = new JSONObject(certificateData);
					policyCertificate = jsonObject2.optString("certificateUrl");
				}

			}
		} else {
			responseMessage.setStatus(false);
			responseMessage.setData(null);
			responseMessage.setMessage("kyc is pending.please connect to technical team.");
			responseMessage.setResponseCode("B2B- 1001");
			return responseMessage;

		}
		
		if (niidWorkDoc == 1 && kycResponse.getKycStatus() == 1) {
			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
			String generateCertificate = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateCertificate);
			String certificateData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(certificateData);
			policyCertificate = jsonObject2.optString("certificateUrl");
		}
		JSONObject doc_cer = new JSONObject();
		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty()|| policyCertificate.toString().trim().isEmpty()) {
			doc_cer.put("POLICY_CERTIFICATE", "");
		} else {
			doc_cer.put("POLICY_CERTIFICATE", policyCertificate);
		}

		doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
		doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());

		responseMessage.setStatus(policyResponse.isStatus());
		responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
		responseMessage.setMessage(policyResponse.getMessage());
		responseMessage.setResponseCode(policyResponse.getResponseCode());
		return responseMessage;
	}
	
	@Override
	public ResponseDto updatepolicy(String token, UpdatePolicyDto updateObj, ResponseDto responseMessage)throws JsonMappingException, JsonProcessingException, JSONException {

		String policyCertificate = "";
		String policyDocument="";
		String niidResCode = "";
		
		UpdateNiidRequestDto niidRequestRes=new UpdateNiidRequestDto();
		niidRequestRes=motorDao.insertRequestUpdatePolicyMotor(token,updateObj,niidRequestRes);
		if (!"200".equalsIgnoreCase(niidRequestRes.getResponseCode())) {
			
			responseMessage.setStatus(false);
			responseMessage.setData(null);
			responseMessage.setMessage(niidRequestRes.getMessage());
			responseMessage.setResponseCode(niidRequestRes.getResponseCode());
			return responseMessage;
		}
		PolicyDto policyDtoObj=new PolicyDto();
		if (niidRequestRes.getData() != null) {
			Gson gson = new GsonBuilder().create();
			JsonObject policyDetails = gson.toJsonTree(niidRequestRes.getData()).getAsJsonObject();
			JSONObject jsonObj = new JSONObject(policyDetails.toString());

			policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
			policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
		}
		
		if (niidRequestRes.getNiidStatus() != 1) {

			if (niidWork == 1) {
//				UpdateNiidDto updateNiidRes=new UpdateNiidDto();
				responseMessage = motorDao.insertNiidRequestDataDao("NIID", "/api/motorinsurance", token,niidRequestRes.getNiidData().toString(), responseMessage);
				if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(responseMessage.getMessage());
					responseMessage.setResponseCode(responseMessage.getResponseCode());
					return responseMessage;
				}

				ResponseEntity<String> thirdPartyResponse = motorUtils.niidB2BUpdatePolicyUtils(niidRequestRes.getNiidData());
				if (thirdPartyResponse == null || "".equalsIgnoreCase(thirdPartyResponse.toString())) {
					log.info(" †††††††††††† NIID IS GIVING NULL VALUE PLEASE CHECK LOG ");
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, updateObj.getPolicyId(),"Response is pending from the regulators.", "102", responseMessage);

				} else {

					log.info(" 7777777777------- niidCreatePolicyUtils method is completed ********************");
					String niidbody = thirdPartyResponse.getBody();
					JSONObject niidjsonObject = new JSONObject(niidbody);
					String niidResMesg = niidjsonObject.optString("ResponseMessage");
					niidResCode = niidjsonObject.optString("ResponseCode");

					responseMessage = motorDao.insertThirdPartyResponseDataDao("NIID", "/api/motorinsurance", token,niidbody, responseMessage);
					log.info(" 7777777777------- insertThirdPartyResponseDao method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						return responseMessage;
					}

//final Save
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, updateObj.getPolicyId(),niidResMesg, niidResCode, responseMessage);
					log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						return responseMessage;
					}
				}
				if ("100".equalsIgnoreCase(niidResCode)) {
					ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(updateObj.getPolicyId(),token);
					String generateCertificate = generateDoc.getBody();
					JSONObject jsonObject1 = new JSONObject(generateCertificate);
					String certificateData = jsonObject1.optString("data");
					JSONObject jsonObject2 = new JSONObject(certificateData);
					policyCertificate = jsonObject2.optString("certificateUrl");
				}

			}
		} 
		
		if (niidWorkDoc == 1) {
			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(updateObj.getPolicyId(),token);
			String generateCertificate = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateCertificate);
			String certificateData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(certificateData);
			policyCertificate = jsonObject2.optString("certificateUrl");
		}
		log.info("------------------------------ START CALLING NAICOM DATA -------------------------------------------------");
		
		if (niidRequestRes.getNiidStatus() == 1) {
			
			NiidStatusResponseDto niidStatus = new NiidStatusResponseDto();
			boolean thirdPartyStatus =false;
			niidStatus = motorDao.checkNiidStatus(token, updateObj.getPolicyId(), niidStatus);
			if (niidStatus.getNiidStatus() == 1) {
				if (niidWork == 1) {
				if (niidStatus.getNaiComStatus() != 1) {

					NaiComResposeDto naiComResponse = new NaiComResposeDto();
					naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,niidStatus.getNaiComData().toString(), naiComResponse);
					if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(naiComResponse.getMessage());
						responseMessage.setResponseCode(naiComResponse.getResponseCode());
						return responseMessage;
					}

					ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComSchedulerPolicyUtils(niidStatus);
					if (naiComThirdPartResponse == null || "".equalsIgnoreCase(naiComThirdPartResponse.toString())) {
						log.info(" †††††††††††† NAI COM IS GIVING NULL VALUE PLEASE CHECK LOG ");
						naiComResponse = motorDao.saveNaiComResponse("", "", token,updateObj.getPolicyId(), thirdPartyStatus,"Response is pending from the regulators.", "102", naiComResponse);

					} else {
						String body = naiComThirdPartResponse.getBody();
						JSONObject jsonObject = new JSONObject(body);
						thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
						String msg = jsonObject.optString("ErrMsgs");
						String errCode = jsonObject.optString("ErrCodes");
						String naiComPolicyID = jsonObject.optString("PolicyID");
						String policyUniqueID = jsonObject.optString("PolicyUniqueID");

						naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
						if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
							responseMessage.setStatus(false);
							responseMessage.setData(null);
							responseMessage.setMessage(naiComResponse.getMessage());
							responseMessage.setResponseCode(naiComResponse.getResponseCode());
							return responseMessage;
						}
						naiComResponse = motorDao.saveNaiComResponse(naiComPolicyID, policyUniqueID, token,updateObj.getPolicyId(), thirdPartyStatus, msg, errCode, naiComResponse);
						if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
							responseMessage.setStatus(false);
							responseMessage.setData(null);
							responseMessage.setMessage(naiComResponse.getMessage());
							responseMessage.setResponseCode(naiComResponse.getResponseCode());
							return responseMessage;
						}
					}
					if (thirdPartyStatus == true) {
						ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(updateObj.getPolicyId(), token);
						String generateDocument = generateDoc.getBody();
						JSONObject jsonObject1 = new JSONObject(generateDocument);
						String documentData = jsonObject1.optString("data");
						JSONObject jsonObject2 = new JSONObject(documentData);
						policyDocument = jsonObject2.optString("documentUrl");
					}

				}
			}
			}
		}
		
		
		if (niidWorkDoc == 1) {
			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(updateObj.getPolicyId(), token);
			String generateDocument = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateDocument);
			String documentData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(documentData);
			policyDocument = jsonObject2.optString("documentUrl");
		}
		
		log.info("------------------------------ END  CALLING NAICOM DATA -------------------------------------------------");
		
		
		
		
		
		JSONObject doc_cer = new JSONObject();
		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
		doc_cer.put("POLICY_CERTIFICATE", "");
		}else {
			doc_cer.put("POLICY_CERTIFICATE", policyCertificate);
		}
		
		if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
			doc_cer.put("POLICY_DOCUMENT", "");
		}else {
			doc_cer.put("POLICY_DOCUMENT", policyDocument);
		}

		doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
		doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());

		responseMessage.setStatus(niidRequestRes.isStatus());
		responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
		responseMessage.setMessage(niidRequestRes.getMessage());
		responseMessage.setResponseCode(niidRequestRes.getResponseCode());
		return responseMessage;
	}
	
	@Override
	public ResponseDto generatedashboardpolicy(String token, InsertDashPolicyTrDto insertobj,ResponseDto responseMessage) throws JSONException, JsonMappingException, JsonProcessingException {

		KycResponseDto kycResponse = new KycResponseDto();
		kycResponse = motorDao.insertDashInsureDetails(token, insertobj, kycResponse);

		PolicyResponseDto policyResponse = new PolicyResponseDto();
		if (kycResponse.getKycStatus() == 1) {
			policyResponse = motorDao.insertDashAllRequest(token, insertobj, 1, "Data has been updated Successfully !",policyResponse);
			if (!"200".equalsIgnoreCase(policyResponse.getResponseCode())) {
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage(policyResponse.getMessage());
				responseMessage.setResponseCode(policyResponse.getResponseCode());
				return responseMessage;
			}
			
		} else {
			ResponseEntity<String> kycThirdPartyResponse =   motorUtils.insertDashKycDetails(insertobj);

			if (kycThirdPartyResponse == null || "".equalsIgnoreCase(kycThirdPartyResponse.toString())) {

				policyResponse = motorDao.insertDashAllRequest(token, insertobj, 102, "Kyc status is pending !",policyResponse);
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage("KYC data successfully submitted.  Updates by WebHook are coming!");
				responseMessage.setResponseCode("KYC-8001");
				return responseMessage;
			}

			String body = kycThirdPartyResponse.getBody();
			JSONObject jsonObject = new JSONObject(body);
			int kycidStatus = jsonObject.optInt("idStatus");
			String kycmessage = jsonObject.optString("message");
			policyResponse = motorDao.insertDashAllRequest(token, insertobj, kycidStatus, kycmessage, policyResponse);
			log.info(" 7777777777------- INSERT KYC RESPONSE FOR GENERATE POLICY  ********************"+ responseMessage);
			if (!"200".equalsIgnoreCase(policyResponse.getResponseCode())) {
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage(policyResponse.getMessage());
				responseMessage.setResponseCode(policyResponse.getResponseCode());
				return responseMessage;
			}
		}
		PolicyDto policyDtoObj=new PolicyDto();
		if (policyResponse.getData() != null) {
			Gson gson = new GsonBuilder().create();
			JsonObject policyDetails = gson.toJsonTree(policyResponse.getData()).getAsJsonObject();
			JSONObject jsonObj = new JSONObject(policyDetails.toString());
			policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
			policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
		}

		// ******************************************************NIID Call
		String policyCertificate = "";
		String niidResCode = "";
		String policyDocument="";
		if (policyResponse.getKycStatus() == 1) {

			if (niidWork == 1) {

				responseMessage = motorDao.insertNiidRequestDataDao("NIID", "/api/motorinsurance", token,policyResponse.getNiidData().toString(), responseMessage);
				log.info(" 7777777777------- insertThirdPartyRequestDao method is completed ********************");
				if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(responseMessage.getMessage());
					responseMessage.setResponseCode(responseMessage.getResponseCode());
					return responseMessage;
				}

				NiidResponseDto niidResponseObj = new NiidResponseDto();
				ResponseEntity<String> thirdPartyResponse = motorUtils.niidB2BCreatePolicyUtils(policyResponse.getNiidData());
				if (thirdPartyResponse == null || "".equalsIgnoreCase(thirdPartyResponse.toString())) {
					log.info(" †††††††††††† NIID IS GIVING NULL VALUE PLEASE CHECK LOG ");
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, policyDtoObj.getPolicyId(),"Response is pending from the regulators.", "102", responseMessage);
//					responseMessage.setStatus(false);
//					responseMessage.setData(null);
//					responseMessage.setMessage("KYC data successfully submitted.  Updates by WebHook are coming!");
//					responseMessage.setResponseCode("KYC-9001");
//					return responseMessage;
				} else {

					log.info(" 7777777777------- niidCreatePolicyUtils method is completed ********************");
					String niidbody = thirdPartyResponse.getBody();
					JSONObject niidjsonObject = new JSONObject(niidbody);
					String niidResMesg = niidjsonObject.optString("ResponseMessage");
					niidResCode = niidjsonObject.optString("ResponseCode");

					responseMessage = motorDao.insertThirdPartyResponseDataDao("NIID", "/api/motorinsurance", token,niidbody, responseMessage);
					log.info(" 7777777777------- insertThirdPartyResponseDao method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(responseMessage.getMessage());
						responseMessage.setResponseCode(responseMessage.getResponseCode());
						return responseMessage;
					}

//final Save
					responseMessage = motorDao.saveCreatePolicyNiidDataResponse(token, policyDtoObj.getPolicyId(),niidResMesg, niidResCode, responseMessage);
					log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
					if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(responseMessage.getMessage());
						responseMessage.setResponseCode(responseMessage.getResponseCode());
						return responseMessage;
					}
				}
				if ("100".equalsIgnoreCase(niidResCode)) {
					ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
					String generateCertificate = generateDoc.getBody();
					JSONObject jsonObject1 = new JSONObject(generateCertificate);
					String certificateData = jsonObject1.optString("data");
					JSONObject jsonObject2 = new JSONObject(certificateData);
					policyCertificate = jsonObject2.optString("certificateUrl");
				}

			}
		}
		
		
		if (niidWorkDoc == 1) {
			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
			String generateCertificate = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateCertificate);
			String certificateData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(certificateData);
			policyCertificate = jsonObject2.optString("certificateUrl");
			
		}
		
		log.info("------------------------------ START CALLING NAICOM DATA -------------------------------------------------");
		
		if (policyResponse.getKycStatus() == 1) {
			
			NiidStatusResponseDto niidStatus = new NiidStatusResponseDto();
			boolean thirdPartyStatus =false;
			niidStatus = motorDao.checkNiidStatus(token, policyDtoObj.getPolicyId(), niidStatus);
			if (niidStatus.getNiidStatus() == 1) {

				if (niidStatus.getNaiComStatus() != 1) {

					NaiComResposeDto naiComResponse = new NaiComResposeDto();
					naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,niidStatus.getNaiComData().toString(), naiComResponse);
					if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(naiComResponse.getMessage());
						responseMessage.setResponseCode(naiComResponse.getResponseCode());
						return responseMessage;
					}

					ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComSchedulerPolicyUtils(niidStatus);
					if (naiComThirdPartResponse == null || "".equalsIgnoreCase(naiComThirdPartResponse.toString())) {
						log.info(" †††††††††††† NAI COM IS GIVING NULL VALUE PLEASE CHECK LOG ");
						naiComResponse = motorDao.saveNaiComResponse("", "", token,policyDtoObj.getPolicyId(), thirdPartyStatus,"Response is pending from the regulators.", "102", naiComResponse);

						naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token,"technical error", naiComResponse);

					} else {
						String body = naiComThirdPartResponse.getBody();
						JSONObject jsonObject = new JSONObject(body);
						thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
						String msg = jsonObject.optString("ErrMsgs");
						String errCode = jsonObject.optString("ErrCodes");
						String naiComPolicyID = jsonObject.optString("PolicyID");
						String policyUniqueID = jsonObject.optString("PolicyUniqueID");

						naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
						if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
							responseMessage.setStatus(false);
							responseMessage.setData(null);
							responseMessage.setMessage(naiComResponse.getMessage());
							responseMessage.setResponseCode(naiComResponse.getResponseCode());
							return responseMessage;
						}
						naiComResponse = motorDao.saveNaiComResponse(naiComPolicyID, policyUniqueID, token,policyDtoObj.getPolicyId(), thirdPartyStatus, msg, errCode, naiComResponse);
						if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
							responseMessage.setStatus(false);
							responseMessage.setData(null);
							responseMessage.setMessage(naiComResponse.getMessage());
							responseMessage.setResponseCode(naiComResponse.getResponseCode());
							return responseMessage;
						}
					}
					if (thirdPartyStatus == true) {
						ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(policyDtoObj.getPolicyId(), token);
						String generateDocument = generateDoc.getBody();
						JSONObject jsonObject1 = new JSONObject(generateDocument);
						String documentData = jsonObject1.optString("data");
						JSONObject jsonObject2 = new JSONObject(documentData);
						policyDocument = jsonObject2.optString("documentUrl");
					}

				}
			}
		}
		
		if (niidWorkDoc == 1) {
			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(policyDtoObj.getPolicyId(), token);
			String generateDocument = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateDocument);
			String documentData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(documentData);
			policyDocument = jsonObject2.optString("documentUrl");
			
		}
		
		log.info("------------------------------ END  CALLING NAICOM DATA -------------------------------------------------");
		
		
		
		
		
		JSONObject doc_cer = new JSONObject();
		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
		doc_cer.put("POLICY_CERTIFICATE", "");
		}else {
			doc_cer.put("POLICY_CERTIFICATE", policyCertificate);
		}
		
		if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
			doc_cer.put("POLICY_DOCUMENT", "");
		}else {
			doc_cer.put("POLICY_DOCUMENT", policyDocument);
		}

		doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
		doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());

		responseMessage.setStatus(policyResponse.isStatus());
		responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
		responseMessage.setMessage(policyResponse.getMessage());
		responseMessage.setResponseCode(policyResponse.getResponseCode());
		return responseMessage;
	}
	


	
	
	@Override
	public ResponseDto schedulerPolicy(String token, SchedulerPolicyDto schedulerObj,ResponseDto responseMessage) throws Exception {
		
		String policyCertificate="";
		String policyDocument="";
		SchedulerResposeDto scherResponseObj=new SchedulerResposeDto();
		
		scherResponseObj=motorDao.insertRequestSchedulerPolicy(token,schedulerObj,scherResponseObj);
		if (!"200".equalsIgnoreCase(scherResponseObj.getResponseCode())) {
			responseMessage.setStatus(false);
			responseMessage.setData(null);
			responseMessage.setMessage(scherResponseObj.getMessage());
			responseMessage.setResponseCode(scherResponseObj.getResponseCode());
			return responseMessage;
		}
    	
		//**********************************************************     NIID     *************************************************************************
		
		if (scherResponseObj.getKycStatus() == 1) {
			
			if (scherResponseObj.getNiidStatus() != 1) {

				NiidResponseDto niidResponseObj = new NiidResponseDto();
				niidResponseObj = motorDao.insertSchedulerNiidRequestDao("NIID", "/api/motorinsurance", token,scherResponseObj.getNiidData().toString(), niidResponseObj);
				log.info("7777777777------- insertSchedulerNiidRequestDao method is completed ********************");
				if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(niidResponseObj.getMessage());
					responseMessage.setResponseCode(niidResponseObj.getResponseCode());
					return responseMessage;
				}

				
				ResponseEntity<String> thirdPartyResponse = motorUtils.niidSchedulerPolicyUtils(scherResponseObj.getNiidData());
				log.info("7777777777------- NIIDCreatePolicyUtils method is completed ********************");
				String niidbody = thirdPartyResponse.getBody();
				JSONObject niidjsonObject = new JSONObject(niidbody);
				String niidResMesg = niidjsonObject.optString("ResponseMessage");
				String niidResCode = niidjsonObject.optString("ResponseCode");
				
				

				niidResponseObj = motorDao.insertThirdPartyResponseDao("NIID", "/api/motorinsurance", token, niidbody,niidResponseObj);
				log.info("7777777777------- insertThirdPartyResponseDao method is completed ********************");
				if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(niidResponseObj.getMessage());
					responseMessage.setResponseCode(niidResponseObj.getResponseCode());
					return responseMessage;
				}
				niidResponseObj = motorDao.saveCreatePolicyNiidResponse(token, schedulerObj.getPolicyId(), niidResMesg,niidResCode, niidResponseObj);
				log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
				if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
					responseMessage.setStatus(false);
					responseMessage.setData(null);
					responseMessage.setMessage(niidResponseObj.getMessage());
					responseMessage.setResponseCode(niidResponseObj.getResponseCode());
					return responseMessage;
				}
				
				// Calling ankit api for Certificate
				if ("100".equalsIgnoreCase(niidResCode)) {
					ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(schedulerObj.getPolicyId(), token);
					String generateCertificate = generateDoc.getBody();
					JSONObject jsonObject1 = new JSONObject(generateCertificate);
					String certificateData = jsonObject1.optString("data");
					JSONObject jsonObject2 = new JSONObject(certificateData);
					policyCertificate = jsonObject2.optString("certificateUrl");
				}
			}
		}
		
		//********************************************************** END  NIID     *************************************************************************
		//========================================================== Start NAICOM  =========================================================================
		
		if (scherResponseObj.getKycStatus() == 1) {
			NiidStatusResponseDto niidStatus=new NiidStatusResponseDto();
			niidStatus=motorDao.checkNiidStatus(token,schedulerObj.getPolicyId(),niidStatus);
			if (niidStatus.getNiidStatus() == 1) {
				
				if (niidStatus.getNaiComStatus() != 1) {
					
					NaiComResposeDto naiComResponse = new NaiComResposeDto();
					naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,niidStatus.getNaiComData().toString(), naiComResponse);
					if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(naiComResponse.getMessage());
						responseMessage.setResponseCode(naiComResponse.getResponseCode());
						return responseMessage;
					}
					

					ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComSchedulerPolicyUtils(niidStatus);
					
					String body = naiComThirdPartResponse.getBody();
					JSONObject jsonObject = new JSONObject(body);
					boolean thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
					String msg = jsonObject.optString("ErrMsgs");
					String errCode = jsonObject.optString("ErrCodes");
					String naiComPolicyID = jsonObject.optString("PolicyID");
					String policyUniqueID = jsonObject.optString("PolicyUniqueID");

					naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
					if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(naiComResponse.getMessage());
						responseMessage.setResponseCode(naiComResponse.getResponseCode());
						return responseMessage;
					}
					naiComResponse = motorDao.saveNaiComResponse(naiComPolicyID, policyUniqueID, token,schedulerObj.getPolicyId(), thirdPartyStatus, msg, errCode, naiComResponse);
					if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
						responseMessage.setStatus(false);
						responseMessage.setData(null);
						responseMessage.setMessage(naiComResponse.getMessage());
						responseMessage.setResponseCode(naiComResponse.getResponseCode());
						return responseMessage;
					}
					if (thirdPartyStatus == true) {
						ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(schedulerObj.getPolicyId(), token);
						String generateDocument = generateDoc.getBody();
						JSONObject jsonObject1 = new JSONObject(generateDocument);
						String documentData = jsonObject1.optString("data");
						JSONObject jsonObject2 = new JSONObject(documentData);
						policyDocument = jsonObject2.optString("documentUrl");
					}
					
				}
			}
		}
		
		//================END NAI COM=============
		
		
		
		JSONObject doc_cer = new JSONObject();
		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
		doc_cer.put("policyCertificate", "");
		}else {
			doc_cer.put("policyCertificate", policyCertificate);
		}
		
		if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
			doc_cer.put("policyDocument", "");
		}else {
			doc_cer.put("policyDocument", policyDocument);
		}
		responseMessage.setStatus(scherResponseObj.isStatus());
		responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
		responseMessage.setMessage(scherResponseObj.getMessage());
		responseMessage.setResponseCode(scherResponseObj.getResponseCode());
		return responseMessage;
	}
	
	@Override
	public ResponseDto verificationVehicleService(String token, String searchValue, String searchType,ResponseDto responseMessage) throws Exception {
		
		String thirdPartyResponse = motorUtils.getNiidVehicleDetails(searchValue, searchType, responseMessage);
		log.info("-----------thied party vehicle validation response-----" + thirdPartyResponse);
		if("\"null\"".equalsIgnoreCase(thirdPartyResponse.trim()) || "".equalsIgnoreCase(thirdPartyResponse)) {
//			responseMessage = motorDao.verificationVehicleDao(searchValue, searchType, null, token, responseMessage);
//			if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage("No Record Found !");
				responseMessage.setResponseCode("NI-404");
				return responseMessage;
//			}
		}

		Object objString = objectMapper.readValue(thirdPartyResponse, Object.class);
		JSONObject jsonObj = new JSONObject(objString.toString());

		responseMessage = motorDao.verificationVehicleDao(searchValue, searchType, jsonObj, token, responseMessage);
		if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
			responseMessage.setStatus(false);
			responseMessage.setData(null);
			responseMessage.setMessage(responseMessage.getMessage());
			responseMessage.setResponseCode(responseMessage.getResponseCode());
			return responseMessage;
		}
		responseMessage.setStatus(responseMessage.isStatus());
		responseMessage.setData(responseMessage.getData());
		responseMessage.setMessage(responseMessage.getMessage());
		responseMessage.setResponseCode(responseMessage.getResponseCode());
		return responseMessage;
	}
	
	
	
	

	@Override
	public ResponseDto endorsementPolicyTr(String token, EndorsementPolicyDto endorsementobj,ResponseDto responseMessage) {
		
//		responseMessage=motorDao.endorsementPolicyTr(token,endorsementobj,responseMessage);
//		if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(responseMessage.getMessage());
//			responseMessage.setResponseCode(responseMessage.getResponseCode());
//			return responseMessage;
//		}
//		
//		Gson gson = new GsonBuilder().create();
//		JsonObject policyDetails = gson.toJsonTree(responseMessage.getData()).getAsJsonObject();
//		JSONObject jsonObj = new JSONObject(policyDetails.toString());
//		int quotationId=jsonObj.optInt("QUOTATION_ID");
//		String quotationNumber=jsonObj.optString("QUOTATION_NUMBER");
//		String premiumAmount=jsonObj.optString("PREMIUM_AMOUNT");
//		
//		ResponseEntity<String> generateDoc = motorUtils.generatePolicyQuotation(quotationId, token);
//		
//		String generateCertificate = generateDoc.getBody();
//		JSONObject jsonObject1 = new JSONObject(generateCertificate);
//		String certificateData = jsonObject1.optString("data");
//		JSONObject jsonObject2 = new JSONObject(certificateData);
//		String policyCertificate = jsonObject2.optString("certificateUrl");
//		
//		JSONObject doc_cer = new JSONObject();
//		if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) {
//			doc_cer.put("QUOTATION_DOC", "");
//		}else {
//			doc_cer.put("QUOTATION_DOC", policyCertificate);
//		}
//		
//		doc_cer.put("QUOTATION_ID", quotationId);
//		doc_cer.put("QUOTATION_NUMBER", quotationNumber);
//		doc_cer.put("PREMIUM_AMOUNT", premiumAmount);
//		responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
		
		return responseMessage;
	}

	@Override
	public ResponseDto getInsuredDetails(Long quotationId, String token, ResponseDto responseMessage) {
		return motorDao.getInsuredDetails(quotationId,token,responseMessage);
	}

	@Override
	public ResponseDto getplanPremium(long lobId, long productId, long planId, BigDecimal amount, String token,
			ResponseDto responseMessage) {
		return motorDao.getplanPremium(lobId,productId,planId,amount,token,responseMessage);
	}

	@Override
	public ResponseDto getRenewalList(String token, RenewalDto renewalobj, ResponseDto responseMessage) {
		return motorDao.getRenewalList(token,renewalobj,responseMessage);
	}



}




//@Override
//public ResponseDto generatePolicy(String token, InsertPolicyDto insertobj, ResponseDto responseMessage)throws JSONException, JsonMappingException, JsonProcessingException {
//	
//	NiidResponseDto niidResponseObj=new NiidResponseDto();
//	String policyCertificate="";
//	String policyDocument="";
//	NaiComResposeDto naicomRespo=new NaiComResposeDto();
//	// ----------------INSERT REQUEST BODY -------------------------
//	naicomRespo=motorDao.insertRequestGeneratePolicy(token,insertobj,naicomRespo);
//	log.info(" 7777777777------- GET RESPONSE FROM DB LAYER ********************");
//	if (!"200".equalsIgnoreCase(naicomRespo.getResponseCode())) {
//		responseMessage.setStatus(false);
//		responseMessage.setData(null);
//		responseMessage.setMessage(naicomRespo.getMessage());
//		responseMessage.setResponseCode(naicomRespo.getResponseCode());
//		return responseMessage;
//	}
//	
//	Gson gson = new GsonBuilder().create();
//	JsonObject policyDetails = gson.toJsonTree(naicomRespo.getData()).getAsJsonObject();
//	JSONObject jsonObj = new JSONObject(policyDetails.toString());
//	PolicyDto policyDtoObj=new PolicyDto();
//	policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
//	policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
//	
//	
//	
//	KycResponseDto kycResponse=new KycResponseDto();
//	
//	if (naicomRespo.getKycStatus() == 1) {
//		
//		kycResponse=motorDao.insertKycResponse(token,1,"Data has been updated Successfully !",kycResponse,policyDtoObj.getPolicyId());
//	}
//	else {
//		ResponseEntity<String> kycThirdPartyResponse = motorUtils.insertKycDetails(insertobj);
//		log.info(" 7777777777------- INSERT KYC STATUS FROM THIRD PARTY FOR GENERATE POLICY ********************:-------"+kycThirdPartyResponse);
//		String body = kycThirdPartyResponse.getBody();
//		JSONObject jsonObject = new JSONObject(body);
//		int kycidStatus = jsonObject.optInt("idStatus");
//		String kycmessage = jsonObject.optString("message");
//		kycResponse=motorDao.insertKycResponse(token,kycidStatus,kycmessage,kycResponse,policyDtoObj.getPolicyId());
//		log.info(" 7777777777------- INSERT KYC RESPONSE FOR GENERATE POLICY  ********************"+responseMessage);
//		if (!"200".equalsIgnoreCase(kycResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(kycResponse.getMessage());
//			responseMessage.setResponseCode(kycResponse.getResponseCode());
//			return responseMessage;
//		}
//	}
//    
//	//******************************************************
//	
//	if (kycResponse.getKycStatus() == 1 ) {
//		
//		if(niidWork == 1) {
//
//		niidResponseObj = motorDao.insertNiidRequestDao("NIID", "/api/motorinsurance", token,kycResponse.getNiid().toString(), niidResponseObj);
//		log.info(" 7777777777------- insertThirdPartyRequestDao method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//
//		ResponseEntity<String> thirdPartyResponse = motorUtils.niidCreatePolicyUtils(kycResponse.getNiid());
//		
//		log.info(" 7777777777------- niidCreatePolicyUtils method is completed ********************");
//		String niidbody = thirdPartyResponse.getBody();
//		JSONObject niidjsonObject = new JSONObject(niidbody);
//		String niidResMesg = niidjsonObject.optString("ResponseMessage");
//		String niidResCode = niidjsonObject.optString("ResponseCode");
//
//		niidResponseObj = motorDao.insertThirdPartyResponseDao("NIID", "/api/motorinsurance", token, niidbody,niidResponseObj);
//		log.info(" 7777777777------- insertThirdPartyResponseDao method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//
//		// call to nai com api-------
//
////	
//		
//		
//		niidResponseObj = motorDao.saveCreatePolicyNiidResponse(token, policyDtoObj.getPolicyId(), niidResMesg, niidResCode,niidResponseObj);
//		log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//		
//		if ("100".equalsIgnoreCase(niidResCode)) {
//			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(), token);
//			String generateCertificate = generateDoc.getBody();
//			JSONObject jsonObject1 = new JSONObject(generateCertificate);
//			String certificateData = jsonObject1.optString("data");
//			JSONObject jsonObject2 = new JSONObject(certificateData);
//			policyCertificate = jsonObject2.optString("certificateUrl");
//		}
//		
//		}
//	}else {
//		responseMessage.setStatus(false);
//		responseMessage.setData(responseMessage.getData());
//		responseMessage.setMessage("kyc is pending.please connect to technical team.");
//		responseMessage.setResponseCode("B2B- 1001");
//		return responseMessage;
//		
//	}
//	//===============================NAICOM============================
////	if (kycResponse.getKycStatus() == 1) {
//////	if (kycResponse.getKycStatus() == 1 && niidResponseObj.getNiidStatus() == 1) {
////		NaiComResposeDto naiComResponse = new NaiComResposeDto();
////
////		naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,niidResponseObj.getData().toString(), naiComResponse);
////		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
////			responseMessage.setStatus(false);
////			responseMessage.setData(null);
////			responseMessage.setMessage(naiComResponse.getMessage());
////			responseMessage.setResponseCode(naiComResponse.getResponseCode());
////			return responseMessage;
////		}
////
////		ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComCreatePolicyUtils(niidResponseObj);
////		String body = naiComThirdPartResponse.getBody();
////		JSONObject jsonObject = new JSONObject(body);
////		boolean thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
////		String msg = jsonObject.optString("ErrMsgs");
////		String errCode = jsonObject.optString("ErrCodes");
////		String policyID = jsonObject.optString("PolicyID");
////		String policyUniqueID = jsonObject.optString("PolicyUniqueID");
////
////		naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
////		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
////			responseMessage.setStatus(false);
////			responseMessage.setData(null);
////			responseMessage.setMessage(naiComResponse.getMessage());
////			responseMessage.setResponseCode(naiComResponse.getResponseCode());
////			return responseMessage;
////		}
////
////		naiComResponse = motorDao.saveNaiComResponse(policyID, policyUniqueID, token, policyDtoObj.getPolicyId(),thirdPartyStatus, msg, errCode, naiComResponse);
////		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
////			responseMessage.setStatus(false);
////			responseMessage.setData(null);
////			responseMessage.setMessage(naiComResponse.getMessage());
////			responseMessage.setResponseCode(naiComResponse.getResponseCode());
////			return responseMessage;
////		}
////		
////		if (thirdPartyStatus == true) {
////			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(policyDtoObj.getPolicyId(), token);
////			String generateDocument = generateDoc.getBody();
////			JSONObject jsonObject1 = new JSONObject(generateDocument);
////			String documentData = jsonObject1.optString("data");
////			JSONObject jsonObject2 = new JSONObject(documentData);
////			policyDocument = jsonObject2.optString("documentUrl");
////		}
////	}
//	//================END NAI COM=============
//	
//	
//	
//	
//	JSONObject doc_cer = new JSONObject();
//	if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
//	doc_cer.put("policyCertificate", "");
//	}else {
//		doc_cer.put("policyCertificate", policyCertificate);
//	}
//	
//	
//	doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
//	doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
//	
//	
//	
//	responseMessage.setStatus(naicomRespo.isStatus());
//	responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
//	responseMessage.setMessage(naicomRespo.getMessage());
//	responseMessage.setResponseCode(naicomRespo.getResponseCode());
//	return responseMessage;
//}






//@Override
//public ResponseDto updatepolicy(String token, UpdatePolicyDto updateObj, ResponseDto responseMessage)throws JsonMappingException, JsonProcessingException, JSONException {
//	String policyCertificate="";
//	String policyDocument="";
//	
//	NaiComResposeDto naicomRes=new NaiComResposeDto();
//	naicomRes=motorDao.insertRequestUpdatePolicy(token,updateObj,naicomRes);
//	if (!"200".equalsIgnoreCase(naicomRes.getResponseCode())) {
//		responseMessage.setStatus(false);
//		responseMessage.setData(null);
//		responseMessage.setMessage(naicomRes.getMessage());
//		responseMessage.setResponseCode(naicomRes.getResponseCode());
//		return responseMessage;
//	}
//	
//	Gson gson = new GsonBuilder().create();
//	JsonObject policyDetails = gson.toJsonTree(naicomRes.getData()).getAsJsonObject();
//	JSONObject jsonObj = new JSONObject(policyDetails.toString());
//	PolicyDto policyDtoObj=new PolicyDto();
//	policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
//	policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
//	
//	KycResponseDto kycResponse=new KycResponseDto();
//	
//	if (naicomRes.getKycStatus() == 1) {
//		
//		kycResponse=motorDao.insertKycResponse(token,1,"Data has been updated Successfully !",kycResponse,updateObj.getPolicyId());
//	}
//	else {
//		ResponseEntity<String> kycThirdPartyResponse = motorUtils.updateKycDetaisl(updateObj);
//		log.info("7777777777------- updatekyc method is completed ********************"+kycThirdPartyResponse);
//		String body = kycThirdPartyResponse.getBody();
//		JSONObject jsonObject = new JSONObject(body);
//		int kycidStatus = jsonObject.optInt("idStatus");
//		String kycmessage = jsonObject.optString("message");
//		kycResponse = motorDao.insertKycResponse(token, kycidStatus, kycmessage, kycResponse,updateObj.getPolicyId());
//		log.info("7777777777------- generatePolicy method is completed ********************"+responseMessage);
//		if (!"200".equalsIgnoreCase(responseMessage.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(kycResponse.getMessage());
//			responseMessage.setResponseCode(kycResponse.getResponseCode());
//			return responseMessage;
//		}
//	}
//	//**********************************************************     NIID     *************************************************************************
//
//	
//	if (naicomRes.getKycStatus() == 1) {
//
//		if (naicomRes.getNiidStatus() != 1) {
//
//			NiidResponseDto niidResponseObj = new NiidResponseDto();
//
//			niidResponseObj = motorDao.insertNiidRequestDao("NIID", "/api/motorinsurance", token,naicomRes.getNiidData().toString(), niidResponseObj);
//			log.info("7777777777------- insertThirdPartyRequestDao method is completed ********************");
//			if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//				responseMessage.setStatus(false);
//				responseMessage.setData(null);
//				responseMessage.setMessage(niidResponseObj.getMessage());
//				responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//				return responseMessage;
//			}
//
//			ResponseEntity<String> thirdPartyResponse = motorUtils.niidUpdatePolicyUtils(naicomRes.getNiidData());
//			log.info("7777777777------- NIIDCreatePolicyUtils method is completed ********************");
//			String niidbody = thirdPartyResponse.getBody();
//			JSONObject niidjsonObject = new JSONObject(niidbody);
//			String niidResMesg = niidjsonObject.optString("ResponseMessage");
//			String niidResCode = niidjsonObject.optString("ResponseCode");
//
//			niidResponseObj = motorDao.insertThirdPartyResponseDao("NIID", "/api/motorinsurance", token, niidbody,niidResponseObj);
//			log.info("7777777777------- insertThirdPartyResponseDao method is completed ********************");
//			if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//				responseMessage.setStatus(false);
//				responseMessage.setData(null);
//				responseMessage.setMessage(niidResponseObj.getMessage());
//				responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//				return responseMessage;
//			}
//
//			niidResponseObj = motorDao.saveCreatePolicyNiidResponse(token, updateObj.getPolicyId(), niidResMesg,niidResCode, niidResponseObj);
//			log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
//			if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//				responseMessage.setStatus(false);
//				responseMessage.setData(null);
//				responseMessage.setMessage(niidResponseObj.getMessage());
//				responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//				return responseMessage;
//			}
//			
//			// Calling ankit api for Certificate
//			if ("100".equalsIgnoreCase(niidResCode)) {
//				ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(updateObj.getPolicyId(), token);
//				String generateCertificate = generateDoc.getBody();
//				JSONObject jsonObject1 = new JSONObject(generateCertificate);
//				String certificateData = jsonObject1.optString("data");
//				JSONObject jsonObject2 = new JSONObject(certificateData);
//				policyCertificate = jsonObject2.optString("certificateUrl");
//			}
//		}
//
//	}
//	
//	//********************************************************** END  NIID     *************************************************************************
//	//===============================NAICOM============================
//	if (naicomRes.getNaiComStatus() != 1) {
//		NaiComResposeDto naiComResponse = new NaiComResposeDto();
//
//		naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,naicomRes.getNaiComData().toString(), naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//
//		ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComUpdatePolicyUtils(naicomRes);
//		String body = naiComThirdPartResponse.getBody();
//		JSONObject jsonObject = new JSONObject(body);
//		boolean thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
//		String msg = jsonObject.optString("ErrMsgs");
//		String errCode = jsonObject.optString("ErrCodes");
//		String naiComPolicyID = jsonObject.optString("PolicyID");
//		String policyUniqueID = jsonObject.optString("PolicyUniqueID");
//
//		naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//		
//		if (thirdPartyStatus == true) {
//		ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(updateObj.getPolicyId(), token);
//		String generateDocument = generateDoc.getBody();
//		JSONObject jsonObject1 = new JSONObject(generateDocument);
//		String documentData = jsonObject1.optString("data");
//		JSONObject jsonObject2 = new JSONObject(documentData);
//		policyDocument=jsonObject2.optString("documentUrl");
//		}
//		
//		
//		naiComResponse = motorDao.saveNaiComResponse(naiComPolicyID, policyUniqueID, token, updateObj.getPolicyId(),thirdPartyStatus, msg, errCode, naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//	}
//	
//	//================END NAI COM=============
//	
//	
//	
//	responseMessage.setStatus(responseMessage.isStatus());
//	
//	JSONObject doc_cer = new JSONObject();
//	if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
//	doc_cer.put("policyCertificate", "");
//	}else {
//		doc_cer.put("policyCertificate", policyCertificate);
//	}
//	
//	if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
//		doc_cer.put("policyDocument", "");
//	}else {
//		doc_cer.put("policyDocument", policyDocument);
//	}
//	
//	doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
//	doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
//	
//	
//	responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
//	
//	
//	responseMessage.setStatus(naicomRes.isStatus());
//	responseMessage.setMessage(naicomRes.getMessage());
//	responseMessage.setResponseCode(naicomRes.getResponseCode());
//	return responseMessage;
//}




//@Override
//public ResponseDto generatedashboardpolicy(String token, InsertPolicyDto insertobj, ResponseDto responseMessage) throws JSONException, JsonMappingException, JsonProcessingException {
//	
//	NiidResponseDto niidResponseObj=new NiidResponseDto();
//	String policyCertificate="";
//	String policyDocument="";
//	NaiComResposeDto naicomRespo=new NaiComResposeDto();
//	// ----------------INSERT REQUEST BODY -------------------------
//	naicomRespo=motorDao.insertRequestGeneratefordashboarPolicy(token,insertobj,naicomRespo);
//	log.info(" 7777777777------- GET RESPONSE FROM DB LAYER ********************");
//	if (!"200".equalsIgnoreCase(naicomRespo.getResponseCode())) {
//		responseMessage.setStatus(false);
//		responseMessage.setData(null);
//		responseMessage.setMessage(naicomRespo.getMessage());
//		responseMessage.setResponseCode(naicomRespo.getResponseCode());
//		return responseMessage;
//	}
//	
//	Gson gson = new GsonBuilder().create();
//	JsonObject policyDetails = gson.toJsonTree(naicomRespo.getData()).getAsJsonObject();
//	JSONObject jsonObj = new JSONObject(policyDetails.toString());
//	PolicyDto policyDtoObj=new PolicyDto();
//	policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
//	policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
//	////////////////////////////////////
//	JSONObject doc_cer = new JSONObject();
//	if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
//	doc_cer.put("policyCertificate", "");
//	}else {
//		doc_cer.put("policyCertificate", policyCertificate);
//	}
//	
//	if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
//		doc_cer.put("policyDocument", "");
//	}else {
//		doc_cer.put("policyDocument", policyDocument);
//	}
//	
//	doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
//	doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
//	/////////////////////////////////////////////////
//	KycResponseDto kycResponse=new KycResponseDto();
//	
//	if (naicomRespo.getKycStatus() == 1) {
//		
//		kycResponse=motorDao.insertKycResponse(token,1,"Data has been updated Successfully !",kycResponse,policyDtoObj.getPolicyId());
//	}
//	else {
//		ResponseEntity<String> kycThirdPartyResponse = motorUtils.insertKycDetails(insertobj);
////		ResponseEntity<String> kycThirdPartyResponse = null;
//		log.info(" 7777777777------- INSERT KYC STATUS FROM THIRD PARTY FOR GENERATE POLICY ********************:-------"+kycThirdPartyResponse);
//		if(kycThirdPartyResponse == null || "".equalsIgnoreCase(kycThirdPartyResponse.toString())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
//			responseMessage.setMessage("Your Policy has been generated with number "+ policyDtoObj.getPolicyNumber()+" . Response is pending from the regulators.");
//			responseMessage.setResponseCode("KYC-9001");
//			return responseMessage;
//		}
//		String body = kycThirdPartyResponse.getBody();
//		JSONObject jsonObject = new JSONObject(body);
//		int kycidStatus = jsonObject.optInt("idStatus");
//		String kycmessage = jsonObject.optString("message");
//		kycResponse=motorDao.insertKycResponse(token,kycidStatus,kycmessage,kycResponse,policyDtoObj.getPolicyId());
//		log.info(" 7777777777------- INSERT KYC RESPONSE FOR GENERATE POLICY GET NIID DATA  ********************"+responseMessage);
//		if (!"200".equalsIgnoreCase(kycResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(kycResponse.getMessage());
//			responseMessage.setResponseCode(kycResponse.getResponseCode());
//			return responseMessage;
//		}
//	}
//    
//	//******************************************************
//	
//	if (kycResponse.getKycStatus() == 1) {
//		
//		if(niidWork == 1) {
//
//		niidResponseObj = motorDao.insertNiidRequestDao("NIID", "/api/motorinsurance", token,kycResponse.getNiid().toString(), niidResponseObj);
//		log.info(" 7777777777------- insertThirdPartyRequestDao method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//
//		ResponseEntity<String> thirdPartyResponse = motorUtils.niidCreatePolicyUtils(kycResponse.getNiid());
//		log.info(" 7777777777------- niidCreatePolicyUtils method is completed ********************");
//		String niidbody = thirdPartyResponse.getBody();
//		JSONObject niidjsonObject = new JSONObject(niidbody);
//		String niidResMesg = niidjsonObject.optString("ResponseMessage");
//		String niidResCode = niidjsonObject.optString("ResponseCode");
//
//		niidResponseObj = motorDao.insertThirdPartyResponseDao("NIID", "/api/motorinsurance", token, niidbody,niidResponseObj);
//		log.info(" 7777777777------- insertThirdPartyResponseDao method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//
//
//		
//		niidResponseObj = motorDao.saveCreatePolicyNiidResponse(token, policyDtoObj.getPolicyId(), niidResMesg, niidResCode,niidResponseObj);
//		log.info("7777777777------- saveCreatePolicyNiidResponse method is completed ********************");
//		if (!"200".equalsIgnoreCase(niidResponseObj.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(niidResponseObj.getMessage());
//			responseMessage.setResponseCode(niidResponseObj.getResponseCode());
//			return responseMessage;
//		}
//		
//		if ("100".equalsIgnoreCase(niidResCode)) {
//			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDoc(policyDtoObj.getPolicyId(), token);
//			String generateCertificate = generateDoc.getBody();
//			JSONObject jsonObject1 = new JSONObject(generateCertificate);
//			String certificateData = jsonObject1.optString("data");
//			JSONObject jsonObject2 = new JSONObject(certificateData);
//			policyCertificate = jsonObject2.optString("certificateUrl");
//		}
//		}
//	}else {
//		responseMessage.setStatus(false);
//		responseMessage.setData(responseMessage.getData());
//		responseMessage.setMessage("kyc is pending.please connect to technical team.");
//		responseMessage.setResponseCode("B2B- 1001");
//		return responseMessage;
//		
//	}
//	//===============================NAICOM============================
//	
//	
//	if (kycResponse.getKycStatus() == 1) {
//		NaiComDBResposeDto naiDbResponse=new NaiComDBResposeDto();
//		naiDbResponse=motorDao.getNaiComResponse(naiDbResponse,token,policyDtoObj.getPolicyId());
//		
//		if(naiDbResponse.getNiidStatus() == 1) {
//		NaiComResposeDto naiComResponse = new NaiComResposeDto();
//
//		naiComResponse = motorDao.insertThirdPartyRequestDao("NAICOM", "/api/v1/cp/policy/new", token,naiDbResponse.getNaiComData().toString(), naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//
//		ResponseEntity<String> naiComThirdPartResponse = motorUtils.naiComCreatePolicyUtils(naiDbResponse);
//		String body = naiComThirdPartResponse.getBody();
//		JSONObject jsonObject = new JSONObject(body);
//		boolean thirdPartyStatus = jsonObject.optBoolean("IsSucceed");
//		String msg = jsonObject.optString("ErrMsgs");
//		String errCode = jsonObject.optString("ErrCodes");
//		String policyID = jsonObject.optString("PolicyID");
//		String policyUniqueID = jsonObject.optString("PolicyUniqueID");
//
//		naiComResponse = motorDao.insertNaiomResponseDao("NAICOM", "/api/v1/cp/policy/new", token, body,naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//
//		naiComResponse = motorDao.saveNaiComResponse(policyID, policyUniqueID, token, policyDtoObj.getPolicyId(),thirdPartyStatus, msg, errCode, naiComResponse);
//		if (!"200".equalsIgnoreCase(naiComResponse.getResponseCode())) {
//			responseMessage.setStatus(false);
//			responseMessage.setData(null);
//			responseMessage.setMessage(naiComResponse.getMessage());
//			responseMessage.setResponseCode(naiComResponse.getResponseCode());
//			return responseMessage;
//		}
//		
//		if (thirdPartyStatus == true) {
//			ResponseEntity<String> generateDoc = motorUtils.generatePolicyDocumant(policyDtoObj.getPolicyId(), token);
//			String generateDocument = generateDoc.getBody();
//			JSONObject jsonObject1 = new JSONObject(generateDocument);
//			String documentData = jsonObject1.optString("data");
//			JSONObject jsonObject2 = new JSONObject(documentData);
//			policyDocument = jsonObject2.optString("documentUrl");
//		}
//		
//	}
//	}
//	//================END NAI COM=============
//	
//	
//	
//	
//	if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty() || policyCertificate.toString().trim().isEmpty()) { 
//	doc_cer.put("policyCertificate", "");
//	}else {
//		doc_cer.put("policyCertificate", policyCertificate);
//	}
//	
//	if (policyDocument.toString().trim() == null || policyDocument.toString().isEmpty() || policyDocument.toString().trim().isEmpty()) {
//		doc_cer.put("policyDocument", "");
//	}else {
//		doc_cer.put("policyDocument", policyDocument);
//	}
//	
//	doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
//	doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
//	
//	
//	
//	responseMessage.setStatus(naicomRespo.isStatus());
//	responseMessage.setData(objectMapper.readValue(doc_cer.toString(),Object.class));
//	responseMessage.setMessage(naicomRespo.getMessage());
//	responseMessage.setResponseCode(naicomRespo.getResponseCode());
//	return responseMessage;
//}



