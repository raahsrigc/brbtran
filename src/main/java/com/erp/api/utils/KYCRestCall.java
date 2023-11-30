package com.erp.api.utils;

import java.io.IOException;
import java.time.LocalTime;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.advice.RestCallApi;
import com.erp.api.dto.MultipartInputStreamFileResource;
import com.erp.api.dto.UploadImageTrDto;
import com.erp.api.models.GetKycDataModel;
import com.erp.api.models.GetKycStatusModel;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class KYCRestCall {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RestCallApi restCallApi;
    
    @Value("${api.quotation.doc}")
	private String quotationDoc;
    
    @Value("${device.policy.certificate.api}")
	private String policyCertificate;
    
    @Value("${device.upload.excel.api}")
	private String uploadExcel;
    

    public ResponseEntity<String> kycDetails(GetKycStatusModel getKycStatusModel, String token) throws JSONException, JSONException {
        String url = restCallApi.getKycDetails();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("trackingReference", getKycStatusModel.getTrackingRefNo());
        jsonObject1.put("firstName", getKycStatusModel.getFirstName());
        jsonObject1.put("lastName", getKycStatusModel.getLastName());
        jsonObject1.put("idType", getKycStatusModel.getIdType());
        jsonObject1.put("phoneNumber", getKycStatusModel.getPhoneNumber());
        jsonObject1.put("dob", getKycStatusModel.getDob());
        jsonObject1.put("sessionId", token);
        jsonObject1.put("idNumber", getKycStatusModel.getIdNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
        ResponseEntity<String> resThirdParty=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        return resThirdParty;
    }

    public ResponseEntity<String> uploadImage(UploadImageTrDto uploadImageObj)throws JSONException, JSONException {
        String url = restCallApi.getUploadImage();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("fileName", "Device"+uploadImageObj.getUserId()+"."+uploadImageObj.getFormat());
        jsonObject1.put("base64String", uploadImageObj.getImageBase64());
        jsonObject1.put("format", uploadImageObj.getFormat());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
        ResponseEntity<String> resThirdParty=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
        return resThirdParty;
    }

	public ResponseEntity<String> getkycStatus(GetKycDataModel record, String token) throws JSONException {
        String url = restCallApi.getKycDetails();
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("trackingReference", record.getTrackingRefNo());
        jsonObject1.put("firstName", record.getFirstName());
        jsonObject1.put("lastName", record.getLastName());
        jsonObject1.put("idType", record.getIdType());
        jsonObject1.put("phoneNumber", record.getPhoneNumber());
        jsonObject1.put("dob", record.getDob());
        jsonObject1.put("sessionId", token);
        jsonObject1.put("idNumber", record.getIdNumber());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
        ResponseEntity<String> resThirdParty=restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);

        return resThirdParty;
    }

	public ResponseEntity<String> generateQuotationDoc(int quotationId, String token) {
		JSONObject jsonObject1 = new JSONObject();
		HttpHeaders headers = new HttpHeaders();
		headers.add("token", token);
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject1.toString(), headers);
		log.info(quotationDoc + "---========creating request body for get Quotation Document -- " + httpEntity);
		ResponseEntity<String> resThirdParty = restTemplate.exchange(quotationDoc + quotationId, HttpMethod.GET, httpEntity,String.class);
		log.info("========get response  for Quotation Document status -- " + httpEntity);

		return resThirdParty;
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

	public ResponseEntity<String> uploadExcelFile(MultipartFile file, String token) throws IOException {
		LocalTime time = LocalTime.now();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("token", token);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
		body.add("fileName", "uploadexcel/"+file.getOriginalFilename().replace(".xlsx", "")+time);
		System.out.println("----------------------"+file.getOriginalFilename()+time);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(uploadExcel, HttpMethod.POST, requestEntity, String.class);
	}

}
