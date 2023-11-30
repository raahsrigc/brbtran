package com.erp.api.controller;



	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.erp.api.dao.EngineeringClaimDB;
import com.erp.api.dto.ClaimRegistrationDto;
import com.erp.api.dto.ResponseMessage;
import com.erp.api.utils.RestTemplateUtil;

	@Component
	public class claimSettlementWebhook {
		
	
	@Autowired
	EngineeringClaimDB engineeringClaimDB;
		
		@Autowired
		RestTemplateUtil restTemplateUtil;
		
		
		
		
		
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());
		
		//@GetMapping("pendingAccounts")
		
	

	    @Scheduled(fixedRate = 18000000)
		public void settlementClaim() {
			
			ResponseMessage responseMessage1=new ResponseMessage();
			List<ClaimRegistrationDto> array=new ArrayList<>();
			responseMessage1=engineeringClaimDB.getAllSettledClaims(responseMessage1);
			if(responseMessage1.isSuccess()) {
				
			array=(List<ClaimRegistrationDto>)responseMessage1.getData();
			
			for(int i=0;i<array.size();i++) {
				
				ClaimRegistrationDto createAccountObj=new ClaimRegistrationDto();
				
				createAccountObj=array.get(i);
				
				sendWebhook(createAccountObj, responseMessage1);
				
				
				
				
				
			}
			}
	}
		
		
		
		public void sendWebhook(ClaimRegistrationDto claimRegistrationDto,ResponseMessage response) {	
			log.info("request is inside sending webhook for setteled claims");
		JSONObject responseJson=new JSONObject();
		
		
		try {
			//JSONObject claimRes=new JSONObject(response.getData().toString());
		responseJson.put("policyNumber", claimRegistrationDto.getPolicyNumber());	
		responseJson.put("lossDate", claimRegistrationDto.getLossDate());
		//responseJson.put("settlementAmount", claimRegistrationDto.getSettlementAmount());
		
		responseJson.put("Claim_number", claimRegistrationDto.getClaimNumber());
		responseJson.put("Claim_Id", claimRegistrationDto.getClaimId());
		responseJson.put("Claim_Amount", claimRegistrationDto.getEstimateAmount());
		responseJson.put("Settlement_Amount", claimRegistrationDto.getSettlementAmount());
		responseJson.put("responseCode", "200");
		
		 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
		    Date date = new Date();  
		    System.out.println(formatter.format(date));  
		responseJson.put("date", formatter.format(date));
			
		responseJson.put("message", response.getMessage());
		responseJson.put("status", response.isSuccess());
		JSONObject creditResponse=new JSONObject();
		
		creditResponse.put("event", "claim.settlement.notification");//freeze
		creditResponse.put("data", responseJson);
		//RestTemplateUtil.callBackAPI(creditResponse.toString(),response.getString("url"));
		if(claimRegistrationDto.getUrl()!=null) {
		
			try {
				
				
				String responseData=restTemplateUtil.callBackAPIwithHeader(creditResponse.toString(),claimRegistrationDto.getUrl(),claimRegistrationDto.getToken());
			JSONObject resData=new JSONObject(responseData);
			response=engineeringClaimDB.updateSettlementResponse(claimRegistrationDto.getClaimId(), "1", "success", response);
			
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

