package com.erp.api.utils;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.erp.api.models.FlutterWaveResponseModel;



@Service
public class PaymentsRestCall {
    @Autowired
    private RestTemplate restTemplate;
    public ResponseEntity<FlutterWaveResponseModel> makePayments(JSONObject jsonObject){
        String url= "https://api.flutterwave.com/v3/payments";
        HttpHeaders headers = new HttpHeaders();
        System.out.println(jsonObject);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer FLWSECK_TsdfdsEST-b745058db6e3e78b8ef6a913ae06fa20-X");
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject.toString(), headers);
        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, FlutterWaveResponseModel.class);
    }


    public ResponseEntity<FlutterWaveResponseModel> verifyPayments(String txnRefNo){
       // String url= "https://api.flutterwave.com/v3/transactions/{txnId}/verify";
        String url = "https://api.flutterwave.com/v3/transactions/verify_by_reference?tx_ref={tx_ref}";
        HttpHeaders headers = new HttpHeaders();
//        Map<String, String> uriVariables = new HashMap<>();
//        uriVariables.put("tx_ref", txnRefNo);

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Authorization","Bearer FLWSfdsdECK_TEST-b745058db6e3e78b8ef6a913ae06fa20-X");
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        return restTemplate.exchange(url, HttpMethod.GET, httpEntity, FlutterWaveResponseModel.class,txnRefNo);
    }


//	public ResponseEntity<String> insertpaymanrt(MakePaymentNewDto makePaymentDto) {
//		ResponseEntity<String> responseFrom=null;
//		try {
//			System.out.println("---------------get response from db layer for creating niid request---"+makePaymentDto.toString());
//			Gson gson = new GsonBuilder().create();
//			JsonObject pdateSmiGroup = gson.toJsonTree(makePaymentDto).getAsJsonObject();
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.setContentType(MediaType.APPLICATION_JSON);
//			headers.add("Authorization","Bearer sk_test_954b4ff8ce3655776f064106b54112cfd4716b67");
//			headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
//			HttpEntity<String> entity = new HttpEntity<String>(pdateSmiGroup.toString(), headers);
//			System.out.println( "&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------CREATE REQUEST BODY FOR NIID WHILE CREATING POLICY :- " + entity);
//			responseFrom = restTemplate.exchange("https://api.paystack.co/transaction/initialize", HttpMethod.POST, entity, String.class);
//			System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&-------GET RESPONSE FROM NIID WHILE CREATING POLICY :-" + responseFrom);
//
//		} catch (Exception e) {
//			System.out.println("???????????????? GETTING ERROR FORM THIRD PARTY API METHOD IS  niidCreatePolicyUtils" + e);
//			return null;
//		}
//		return responseFrom;
//	}
}
