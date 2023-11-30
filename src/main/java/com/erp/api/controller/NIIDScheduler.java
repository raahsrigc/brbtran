package com.erp.api.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.erp.api.dao.EngineeringClaimDB;
import com.erp.api.dto.PolicyUpdateDetailsDto;
import com.erp.api.dto.ResponseMessage;
import com.erp.api.utils.RestTemplateUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class NIIDScheduler {

	@Autowired
	EngineeringClaimDB engineeringClaimDB;

	@Autowired
	RestTemplateUtil restTemplateUtil;

	@Value("${policy.update.url}")
	private String updatePolicyUrl;

	@JsonIgnore
	ResponseMessage obj;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	// @GetMapping("pendingAccounts")

	@Scheduled(fixedRate = 18000000)
	public void NIIDScheduler() {

		ResponseMessage responseMessage1 = new ResponseMessage();
		List<PolicyUpdateDetailsDto> array = new ArrayList<>();
		responseMessage1 = engineeringClaimDB.getNIIDPendingRecords(responseMessage1);
		if (responseMessage1.isSuccess()) {

			array = (List<PolicyUpdateDetailsDto>) responseMessage1.getData();

			for (int i = 0; i < array.size(); i++) {

				PolicyUpdateDetailsDto createAccountObj = new PolicyUpdateDetailsDto();

				createAccountObj = array.get(i);

				responseMessage1 = ProcessNIIDRequest(createAccountObj, responseMessage1);
				if (responseMessage1.isSuccess())
					sendWebhook(createAccountObj, responseMessage1, responseMessage1.getData());

			}
		}
	}

	public ResponseMessage ProcessNIIDRequest(PolicyUpdateDetailsDto policyUpdateDetailsDto, ResponseMessage response) {

		ObjectMapper mapper = new ObjectMapper();

		try {
			String json = mapper.writeValueAsString(policyUpdateDetailsDto);

			// JSONObject req=new JSONObject(insuredCustomerDTO.toString());

			String response1 = restTemplateUtil.callPostAPIwithToken(json, updatePolicyUrl,
					policyUpdateDetailsDto.getToken());
			if (response1 != null) {
				try {

					JSONObject res = new JSONObject(response1);
					response.setSuccess(res.getBoolean("status"));
					response.setMessage(res.getString("message"));
					if (res.has("data")) {
						response.setData(res.get("data"));
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return response;

	}

	public void sendWebhook(PolicyUpdateDetailsDto policyUpdateDetailsDto, ResponseMessage response, Object data) {
		log.info("request is inside sending webhook for setteled claims");
		JSONObject responseJson = new JSONObject();

		try {
			// JSONObject claimRes=new JSONObject(response.getData().toString());
			// responseJson.put("policyNumber", policyUpdateDetailsDto.getPolicyNumber());

			SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			System.out.println(formatter.format(date));
			responseJson.put("date", formatter.format(date));

			responseJson.put("message", response.getMessage());
			responseJson.put("status", response.isSuccess());

			ObjectMapper mapper = new ObjectMapper();

			if (response.getData() != null) {

				// String json = mapper.writeValueAsString(data);
				JSONObject dt = new JSONObject(data.toString());
				if (policyUpdateDetailsDto.getPolicyNumber() != null)
					responseJson.put("policyNumber", policyUpdateDetailsDto.getPolicyNumber());
				if (dt.has("certificateUrl"))
					responseJson.put("certificateUrl", dt.getString("certificateUrl"));
				// if(dt.has("POLICY_ID"))
				responseJson.put("policyId", policyUpdateDetailsDto.getPolicyId());
				if (dt.has("documentUrl"))
					responseJson.put("documentUrl", dt.getString("documentUrl"));

			}
			JSONObject creditResponse = new JSONObject();

			creditResponse.put("event", "policy.update.notification");// freeze
			creditResponse.put("data", responseJson);
			// RestTemplateUtil.callBackAPI(creditResponse.toString(),response.getString("url"));
			if (policyUpdateDetailsDto.getWebHookUrl() != null) {

				try {

					String responseData = restTemplateUtil.callBackAPIwithHeader(creditResponse.toString(),
							policyUpdateDetailsDto.getWebHookUrl(), policyUpdateDetailsDto.getToken());
					JSONObject resData = new JSONObject(responseData);
					// response=engineeringClaimDB.updateSettlementResponse(claimRegistrationDto.getClaimId(),
					// "1", "success", response);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.info("response is sent for webhook for settled claim");
	}

}
