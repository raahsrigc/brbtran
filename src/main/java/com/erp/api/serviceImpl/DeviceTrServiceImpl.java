package com.erp.api.serviceImpl;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.erp.api.dao.DeviceDao;
import com.erp.api.dao.KYCDao;
import com.erp.api.dto.BatchTrDto;
import com.erp.api.dto.FetchPolicyTrDto;
import com.erp.api.dto.FetchQuotationTrDto;
import com.erp.api.dto.InsertHistoryTrDto;
import com.erp.api.dto.InsertQuotationTrDto;
import com.erp.api.dto.PolicyDto;
import com.erp.api.dto.QuotSearchFilterDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SaveDevicePolicyTrDto;
import com.erp.api.models.GetKycStatusModel;
import com.erp.api.service.DeviceTrService;
import com.erp.api.utils.KYCRestCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DeviceTrServiceImpl implements DeviceTrService {

    @Autowired
    private DeviceDao claimIarDao;

    @Autowired
    private KYCRestCall restCall;

    @Autowired
    private KYCDao kycDao;
    
    @Autowired
	private ObjectMapper objectMapper;

    @Override
    public ResponseDto summaryDataTrService(String token) {
        return claimIarDao.getSummaryDataDao(token, new ResponseDto());
    }
    
    @Override
    public ResponseDto insertQuotTrService(InsertQuotationTrDto insertQObj, String token) throws JSONException, JsonMappingException, JsonProcessingException {
    	ResponseDto responseMessage=new ResponseDto();
    	responseMessage=claimIarDao.insertQuotationDao(insertQObj, token, new ResponseDto());

		if ("200".equalsIgnoreCase(responseMessage.getResponseCode())) {
			Gson gson = new GsonBuilder().create();
			JsonObject policyDetails = gson.toJsonTree(responseMessage.getData()).getAsJsonObject();
			JSONObject jsonObj = new JSONObject(policyDetails.toString());
			int quotationId = jsonObj.optInt("ID");
			String quotationNumber = jsonObj.optString("QUOTATION_NUMBER");
			String premiumAmount = jsonObj.optString("PREMIUM_AMOUNT");
			String serialNumber = jsonObj.optString("DEVICE_SERIAL_NUMBER");

			ResponseEntity<String> generateDoc = restCall.generateQuotationDoc(quotationId, token);

			String generateCertificate = generateDoc.getBody();
			JSONObject jsonObject1 = new JSONObject(generateCertificate);
			String certificateData = jsonObject1.optString("data");
			JSONObject jsonObject2 = new JSONObject(certificateData);
			String policyCertificate = jsonObject2.optString("documentUrl");

			JSONObject doc_cer = new JSONObject();
			if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty()|| policyCertificate.toString().trim().isEmpty()) {
				doc_cer.put("QUOTATION_DOC", "");
			} else {
				doc_cer.put("QUOTATION_DOC", policyCertificate);
			}

			doc_cer.put("ID", quotationId);
			doc_cer.put("QUOTATION_NUMBER", quotationNumber);
			doc_cer.put("PREMIUM_AMOUNT", premiumAmount);
			doc_cer.put("DEVICE_SERIAL_NUMBER", serialNumber);
			responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
		}
		
		return responseMessage;
    }
    
    @Override
    public ResponseDto getPolicyById(Long policyId, String token) {
    	
    	return claimIarDao.getByPolicyId(token, policyId, new ResponseDto());
    }
    
    @Override
    public ResponseDto getBatchDetails(String batchNo, int isPolicy, String token) {

        return claimIarDao.getPolicyBulkFilesData(batchNo,isPolicy, token, new ResponseDto());

    }
    @Override
	public ResponseDto getKycDetailById(int insuredId, String token) {
		 return claimIarDao.getKycDetailById(insuredId,token, new ResponseDto());
	}
    @Override
   	public ResponseDto getDeviceHistory(int deviceId, String token) {
   		return claimIarDao.getDeviceHistory(deviceId,token, new ResponseDto());
   	}
    
    @Override
    public ResponseDto getCommentHistory(String objectUid, String recordId, String token) {
        return claimIarDao.getCommentHistory(objectUid,recordId,token, new ResponseDto());
    }
    
    @Override
    public ResponseDto insertHistoryService(InsertHistoryTrDto historyObj, String sessionId) {
        return claimIarDao.insertHistoryDao(historyObj,sessionId, new ResponseDto());
    }

    @Override
    public ResponseDto saveDevicePolicyTrService(SaveDevicePolicyTrDto saveDeviceObj, String token) throws JSONException, JsonMappingException, JsonProcessingException {
        ResponseDto responseMessage = new ResponseDto();
        String policyCertificate = "";
        log.info("-------save policy-----------------"+saveDeviceObj.toString());
        ResponseDto kycStatus = kycDao.getKycStatus(token,saveDeviceObj, new ResponseDto());
        PolicyDto policyDtoObj = new PolicyDto();
        if ("200".equalsIgnoreCase(kycStatus.getResponseCode())) {
            GetKycStatusModel getKycStatusModel = new GetKycStatusModel();
            getKycStatusModel.setDob(saveDeviceObj.getDob());
            getKycStatusModel.setFirstName(saveDeviceObj.getFirstName());
            getKycStatusModel.setIdNumber(saveDeviceObj.getIdNumber());
            getKycStatusModel.setIdType(saveDeviceObj.getIdType());
            getKycStatusModel.setPhoneNumber(saveDeviceObj.getMobileNumber());
            getKycStatusModel.setLastName(saveDeviceObj.getLastName());
            getKycStatusModel.setTrackingRefNo(Integer.parseInt(saveDeviceObj.getInsuredId()));

            ResponseEntity<String> responseData = restCall.kycDetails(getKycStatusModel, token);
            if(responseData.toString() == null || "".equalsIgnoreCase(responseData.toString().trim())) {
            	responseMessage = claimIarDao.savePolicyDao(saveDeviceObj, 102, token,"Kyc status is pending !" ,new ResponseDto());
				responseMessage.setStatus(false);
				responseMessage.setData(null);
				responseMessage.setMessage("KYC data successfully submitted!");
				responseMessage.setResponseCode("KYC-9001");
				return responseMessage;
            }else {
            	JSONObject jsObject = new JSONObject(responseData.getBody());
                int idStatus = jsObject.optInt("idStatus");
                String message = jsObject.optString("message").replaceAll("\"", " ");
                responseMessage = claimIarDao.savePolicyDao(saveDeviceObj, idStatus, token,message, new ResponseDto());
                if (responseMessage.getData() != null) {
                	Gson gson = new GsonBuilder().create();
        			JsonObject policyDetails = gson.toJsonTree(responseMessage.getData()).getAsJsonObject();
        			JSONObject jsonObj = new JSONObject(policyDetails.toString());
        			policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
        			policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
        			
        			ResponseEntity<String> generateDoc = restCall.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
        			String generateCertificate = generateDoc.getBody();
        			JSONObject jsonObject1 = new JSONObject(generateCertificate);
        			String certificateData = jsonObject1.optString("data");
        			JSONObject jsonObject2 = new JSONObject(certificateData);
        			policyCertificate = jsonObject2.optString("documentUrl");
        			
        			JSONObject doc_cer = new JSONObject();
        			if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty()|| policyCertificate.toString().trim().isEmpty()) {
        				doc_cer.put("POLICY_CERTIFICATE", "");
        			} else {
        				doc_cer.put("POLICY_CERTIFICATE", policyCertificate);
        			}

        			doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
        			doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
        			responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
        		}
                return responseMessage;
            }
            
        } else {
            responseMessage = claimIarDao.savePolicyDao(saveDeviceObj, 1, token,"KYC Success" ,new ResponseDto());
            if (responseMessage.getData() != null) {
            	Gson gson = new GsonBuilder().create();
    			JsonObject policyDetails = gson.toJsonTree(responseMessage.getData()).getAsJsonObject();
    			JSONObject jsonObj = new JSONObject(policyDetails.toString());
    			policyDtoObj.setPolicyId(jsonObj.optInt("POLICY_ID"));
    			policyDtoObj.setPolicyNumber(jsonObj.optString("POLICY_NUMBER"));
    			
    			ResponseEntity<String> generateDoc = restCall.generatePolicyDoc(policyDtoObj.getPolicyId(),token);
    			String generateCertificate = generateDoc.getBody();
    			JSONObject jsonObject1 = new JSONObject(generateCertificate);
    			String certificateData = jsonObject1.optString("data");
    			JSONObject jsonObject2 = new JSONObject(certificateData);
    			policyCertificate = jsonObject2.optString("certificateUrl");
    			
    			JSONObject doc_cer = new JSONObject();
    			if (policyCertificate.toString().trim() == null || policyCertificate.toString().isEmpty()|| policyCertificate.toString().trim().isEmpty()) {
    				doc_cer.put("POLICY_CERTIFICATE", "");
    			} else {
    				doc_cer.put("POLICY_CERTIFICATE", policyCertificate);
    			}

    			doc_cer.put("POLICY_ID", policyDtoObj.getPolicyId());
    			doc_cer.put("POLICY_NUMBER", policyDtoObj.getPolicyNumber());
    			responseMessage.setData(objectMapper.readValue(doc_cer.toString(), Object.class));
    		}
            
            return responseMessage;
        }
    }

	@Override
	public ResponseDto quotationByMail(String mobileOrEmail, String sessionRefNo) {
		return claimIarDao.quotationByMail(mobileOrEmail,sessionRefNo, new ResponseDto());
	}

	@Override
	public ResponseDto quotationList(QuotSearchFilterDto searchObj, String sessionRefNo) {
		return claimIarDao.quotationList(searchObj,sessionRefNo, new ResponseDto());
	}

	@Override
	public ResponseDto getQuotationsList(FetchQuotationTrDto insertQOb, String token) {
        return claimIarDao.deviceQuotationListDao(insertQOb, token, new ResponseDto());
	}

	@Override
	public ResponseDto getPolicyList(FetchPolicyTrDto polObj, String token) {
        return claimIarDao.devicePolicyListDao(polObj, token, new ResponseDto());

	}

	@Override
	public ResponseDto getBatches(BatchTrDto batchDto, String token) {
        return claimIarDao.getBatches( batchDto,token, new ResponseDto());

	}

	@Override
	public ResponseDto kycStatusCheck(Long insuredId, String token) {
		return claimIarDao.kycStatusCheck( insuredId,token, new ResponseDto());
	}

	@Override
	public ResponseDto getQuotationDById(int quotationId, String token) {
		return claimIarDao.getQuotationDById( quotationId,token, new ResponseDto());
	}

	@Override
	public ResponseDto getBatchList(BatchTrDto batchDto, String token) {
		return claimIarDao.getBatchList( batchDto,token, new ResponseDto());
	}

	@Override
	public ResponseDto bulkQuatationByIdTr(long quotationId, String token) {
		return claimIarDao.bulkQuatationByIdTr( quotationId,token, new ResponseDto());
	}

	@Override
	public ResponseDto getInsuredTr(long quotationId, long insuredId, int bulkKycId, String token) {
		return claimIarDao.getInsuredTr( quotationId,insuredId,bulkKycId,token, new ResponseDto());
	}

	

	@Override
	public ResponseDto getPaymentDetailsTr(long policyId, String serviceType, String token) {
		return claimIarDao.getPaymentDetailsTr( policyId,token, serviceType,new ResponseDto());
	}


}
