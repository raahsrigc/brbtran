package com.erp.api.utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class RestTemplateUtil {
	
	//@Autowired
	// RestTemplate restTemplate1;
	
	//@Value("${claim.niid}")
	//private String niidClaimUrl;
	
	 RestTemplate restTemplate;

	    public RestTemplateUtil(RestTemplate restTemplate) {
	        this.restTemplate = restTemplate;
	    }
	
	
	public String callAPIwithHeader(String requestBody,String header,String url) {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	   // headers.set("Authorization", "Bearer "+header );
	    HttpEntity <String> entity = new HttpEntity<String>(headers);
	    System.out.println("---------------------------"+entity);
	    System.out.println(url+"---------------------------"+entity);
	//    String response=restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	 //   System.out.println("==========="+response);
	    return null;
		
		//return null;
		
	}
	
public String callAPI(String requestBody,String url) {
		
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
	    headers.set("Content-Type", "application/json" );
		//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
	    HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
	    UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url).queryParam("authtoken", "");
	    System.out.println("---------------------------"+entity);
	    System.out.println(url+"---------------------------"+entity);
	    String response= 
	    		this.restTemplate.exchange(
	    		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
	   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
	    System.out.println("==========="+response);
	    return response;
		
		//return null;
		
	}

public String callPostAPI(String requestBody,String url) {
	
	
	String response;
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json" );
		//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
		HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
		System.out.println("---------------------------"+entity);
		System.out.println(url+"---------------------------"+entity);
		response = this.restTemplate.exchange(
		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		System.out.println("==========="+response);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JSONObject temp=new JSONObject();
		try {
			temp.put("IsSucceed", false);
			temp.put("error", e.getMessage());
			temp.put("ErrCodes", "201");
			temp.put("ErrMsgs", e.getMessage());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response=temp.toString();
		
	}
    return response;
	
	//return null;
	
}

public String callPostAPIwithToken(String requestBody,String url,String token) {
	
	
	String response;
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		//headers.set("Content-Type", "application/json" );
		headers.set("token", token );
		//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
		HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
		System.out.println("---------------------------"+entity);
		System.out.println(url+"---------------------------"+entity);
		response = this.restTemplate.exchange(
		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		System.out.println("==========="+response);
	} catch (RestClientException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JSONObject temp=new JSONObject();
		try {
			temp.put("IsSucceed", false);
			temp.put("error", e.getMessage());
			temp.put("ErrCodes", "201");
			temp.put("ErrMsgs", e.getMessage());
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		response=temp.toString();
		
	}
    return response;
	
	//return null;
	
}

public String callGETAPI(String url,String token) {
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
    //headers.set("Authorization", "Bearer " + token);

    HttpEntity<String> entity = new HttpEntity<String>(headers);
	System.out.println("---------------------------" + entity);
	System.out.println(url + "---------------------------" + entity);
	String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	System.out.println("response :"+ "---------------------------" + response);

    return response;
	
	//return null;
	
}


public String callAPIwithToken(String requestBody,String url,String token) {
	
	
	String response=null;
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json" );
		
		headers.set("Authorization", "Bearer "+token );
   
		
		HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
		System.out.println("---------------------------"+entity);
		System.out.println(url+"---------------------------"+entity);
		response = this.restTemplate.exchange(
		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		System.out.println("==========="+response);
	} catch(HttpStatusCodeException e) {
		response= e.getResponseBodyAsString();
				//ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
               // .body(e.getResponseBodyAsString());
		
		
		
    }
    return response;
	
	//return null;
	
}


public String callAPIParam(String requestBody,String url) {
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
	//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
    HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
    UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url).queryParam("authtoken", "");
    System.out.println("---------------------------"+entity);
    System.out.println(url+"---------------------------"+entity);
    String response= 
    		this.restTemplate.exchange(
    		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    System.out.println("==========="+response);
    return response;
	
	//return null;
	
}



public String callGetAPIParam(String id,String url,String token) {
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
    headers.set("token", token);
	//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
    HttpEntity <String> entity = new HttpEntity<String>(headers);
    UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url).queryParam("policyId", id);
    System.out.println("---------------------------"+entity);
    System.out.println(url+"---------------------------"+entity);
    String response= 
    		this.restTemplate.exchange(
    		builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    System.out.println("==========="+response);
    return response;
	
	//return null;
	
}


public String callGETWithBodyAPI(String requestBody,String url) {
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
	//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
    HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
    UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
    System.out.println("---------------------------"+entity);
    System.out.println(url+"---------------------------"+entity);
    String response= 
    		this.restTemplate.exchange(
    		builder.toUriString(), HttpMethod.GET, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    System.out.println("==========="+response);
    return response;
	
	//return null;
	
}




//public ResponseEntity<String> getNiidClaimResponse(String sessionId, ResponseMessage responseMessage1) {
//    ResponseEntity<String> responseFrom=null;
//    try {
//        System.out.println("---------------getNiidClaim data---"+responseMessage1.getData().toString());
//        JSONObject jsonObject1 = new JSONObject(responseMessage1.getData().toString()); 
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>(jsonObject1.toString(), headers);
//        System.out.println(niidClaimUrl + "========creating request before hitting create claim niid request =======" + entity);
//        responseFrom = restTemplate.exchange(niidClaimUrl, HttpMethod.POST, entity, String.class);
//        System.out.println("========== GET RESPONSE FROM  NIID CLAIM API ==== " + responseFrom.getBody());
//        
//    } catch (Exception e) {
//    	System.out.println("getting error from getNiidClaimResponse method " + e);
//        return null;
//    }
//    return responseFrom;
//}




public String callGetAPIParam(String makeCode,String url) {
	
	url="https://rosalia.tangerine.insure/cbaGatewayEncrypt/v1/code/fetchList?parent=10001&childType=Vehicle Model";
	
	
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
    //headers.set("Authorization", "Bearer " + token);

    HttpEntity<String> entity = new HttpEntity<String>(headers);
	System.out.println("---------------------------" + entity);
	System.out.println(url + "---------------------------" + entity);
	String response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
	System.out.println("response :"+ "---------------------------" + response);

    return response;
	
	
	//return null;
	
}


public String callBackAPIwithHeader(String requestBody,String url,String token) {
	
	
	HttpHeaders headers = new HttpHeaders();
	headers.setContentType(MediaType.APPLICATION_JSON);
    headers.set("Content-Type", "application/json" );
    headers.set("token", token);
	//HttpEntity<Product> entity = new HttpEntity<Product>(product,headers);
    HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
   // UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
    System.out.println("---------------------------"+entity);
    System.out.println(url+"---------------------------"+entity);
    String response= 
    		this.restTemplate.exchange(
    				url, HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
    System.out.println("==========="+response);
    return response;
	
	//return null;
	
}


public String callAPIwithToken1(String requestBody,String url,String token) {
	
	
	String response=null;
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json" );
		
		headers.set("SessionId", token );
   
		
		HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
		System.out.println("---------------------------"+entity);
		System.out.println(url+"---------------------------"+entity);
		response = this.restTemplate.exchange(
		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		System.out.println("==========="+response);
	} catch(HttpStatusCodeException e) {
		response= e.getResponseBodyAsString();
				//ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
               // .body(e.getResponseBodyAsString());
		
		
		
    }
    return response;
	
	//return null;
	
}

public String callAPIwithToken2(String requestBody,String url,String token) {
	
	
	String response=null;
	try {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Content-Type", "application/json" );
		
		headers.set("sessionId", token );
   
		
		HttpEntity <String> entity = new HttpEntity<String>(requestBody,headers);
		UriComponentsBuilder builder=UriComponentsBuilder.fromUriString(url);
		System.out.println("---------------------------"+entity);
		System.out.println(url+"---------------------------"+entity);
		response = this.restTemplate.exchange(
		builder.toUriString(), HttpMethod.POST, entity, String.class).getBody();
   // String response=restTemplate.exchange(url, HttpMethod.POST, entity, String.class).getBody();
		System.out.println("==========="+response);
	} catch(HttpStatusCodeException e) {
		response= e.getResponseBodyAsString();
				//ResponseEntity.status(e.getRawStatusCode()).headers(e.getResponseHeaders())
               // .body(e.getResponseBodyAsString());
		
		
		
    }
    return response;
	
	//return null;
	
}

}
