package com.erp.api.serviceImpl;



	import java.text.SimpleDateFormat;
import java.util.Date;

import javax.sql.DataSource;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.erp.api.dao.EngineeringClaimDB;
import com.erp.api.dto.ClaimRegistrationDto;
import com.erp.api.dto.InsuredCustomerDTO;
import com.erp.api.dto.PaginationDTO;
import com.erp.api.dto.PolicyHeaderDetailsDto;
import com.erp.api.dto.RenewalEndorsementDto;
import com.erp.api.dto.ResponseMessage;
import com.erp.api.utils.RestTemplateUtil;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


	@Component
	public class EngineeringClaimService {
		
		
		
		@Autowired
		EngineeringClaimDB engineeringQuotationDB;
		
		@Autowired
		RestTemplateUtil restTemplateUtil;
		
		
		
		@Autowired
		ObjectMapper mapper;
		
		@Autowired
		DataSource dataSource;
		
	//	@Value("${deleteFile}")
	//	private String deleteFile;
		
	//	@Value("${model_URL}")
	//	private String modelUrl;
		
		@Value("${update_kyc_URL}")
		private String updateKYCURL;
		
		@Value("${claim_registration_webhook_url}")
		private String claimWebhookUrl;
		
		
		private final Logger log = LoggerFactory.getLogger(this.getClass());

		
	
		
		public ResponseMessage saveClaimRegistrationDetails(String sessionId,ClaimRegistrationDto claimRegistrationDto,ResponseMessage responseMessage) {
			
			
			//responseMessage=engineeringQuotationDB.saveClaimRegistrationDetails(sessionId,claimRegistrationDto, responseMessage);
			log.info("Request is exiting from saveClaimRegistrationDetails service");
			return responseMessage;
		}
		
		
		
			 
					 
		///////////////////////////////			 
					 
					 public ResponseMessage registerClaim(String sessionId,ClaimRegistrationDto claimRegistrationDto,ResponseMessage responseMessage) {
							
							//String decryptedReq=aesEncryptDecryptUtils.decrypt(encryptedRequest);
							
													log.info("Request is inside saveClaimRegistrationDetails service ");
							responseMessage=engineeringQuotationDB.claimRegistration(sessionId,claimRegistrationDto, responseMessage);
							boolean status=responseMessage.isSuccess();
							String msg=responseMessage.getMessage();
							String res=null;
							
							
							try {
								

								ObjectMapper mapper = new ObjectMapper();
	    
								try {
									res= mapper.writeValueAsString(responseMessage.getData());
								}
								catch (JsonGenerationException | JsonMappingException  e) {
	    // catch various errors
									e.printStackTrace();
								}
								
								
								
								
								sendWebhook(sessionId, claimRegistrationDto, status,msg,res,responseMessage.getResponseCode());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return responseMessage;
							}
							
							
							log.info("Request is exiting from saveClaimRegistrationDetails service");
							return responseMessage;
						}
					 
					 public ResponseMessage GetClaimById(String sessionId,String claimId,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside GetClaimById service ");
							
						
							responseMessage=engineeringQuotationDB.GetClaimById(sessionId, claimId, responseMessage);
							log.info("Request is exiting from GetClaimById service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllClaims(String sessionId,String lob,String businessType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllClaims service ");
							
						
							responseMessage=engineeringQuotationDB.getAllClaims(sessionId, lob, businessType, paginationDTO, responseMessage);
							log.info("Request is exiting from getAllClaims service");
							return responseMessage;
						} 
					 
					 
					 public ResponseMessage getCauseOfLoss(String sessionId,String lob,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getCauseOfLoss service ");
							
							responseMessage=engineeringQuotationDB.getCauseOfLoss(sessionId, lob, responseMessage);
							log.info("Request is exiting from getCauseOfLoss service");
							return responseMessage;
						}
					 
					 public ResponseMessage getNatureOfLoss(String sessionId,String lob,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getCauseOfLoss service ");
							
							
							responseMessage=engineeringQuotationDB.getNatureOfLoss(sessionId, lob, responseMessage);
							log.info("Request is exiting from getCauseOfLoss service");
							return responseMessage;
						}
					 
					 
					 public ResponseMessage getMastercodePagination(String sessionId,String serviceType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getInsuredCustomerDetailsPagination service ");
							responseMessage=engineeringQuotationDB.getMasterCodesPagination(sessionId, serviceType, paginationDTO, responseMessage);
							log.info("Request is exiting from getInsuredCustomerDetailsPagination service");
							return responseMessage;
						}
					 
					 public ResponseMessage getModel(String sessionId,String makeCode,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getInsuredCustomerDetailsPagination service ");
							
							//String res=restTemplateUtil.callGetAPIParam(makeCode,modelUrl);
							
							responseMessage=engineeringQuotationDB.getVehicleModel(sessionId, makeCode, responseMessage);
							log.info("Request is exiting from getInsuredCustomerDetailsPagination service");
							return responseMessage;
						}
					 
					 
					 public ResponseMessage getVehicleType(String sessionId,String category,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getVehicleType service ");
							responseMessage=engineeringQuotationDB.getVehicleType(sessionId, category, responseMessage);
							log.info("Request is exiting from getVehicleType service");
							return responseMessage;
						}
					 
					 public ResponseMessage getWalletSOA(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getWalletSOA service ");
							responseMessage=engineeringQuotationDB.getWalletSOA(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getWalletSOA service");
							return responseMessage;
						}
						
					 public ResponseMessage getWalletSOAforProduct(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getWalletSOAforProduct service ");
							responseMessage=engineeringQuotationDB.getWalletSOAforProduct(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getWalletSOAforProduct service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllApprovedPolicies(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllApprovedPolicies service ");
							responseMessage=engineeringQuotationDB.getAllApprovedPolicies(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getAllApprovedPolicies service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllSettledClaims(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllSettledClaims service ");
							responseMessage=engineeringQuotationDB.getAllSettledClaims(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getAllSettledClaims service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllQuotations(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllQuotations service ");
							responseMessage=engineeringQuotationDB.getAllQuotations(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getAllQuotations service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllApprovedClaims(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllApprovedClaims service ");
							responseMessage=engineeringQuotationDB.getAllApprovedClaims(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getAllApprovedClaims service");
							return responseMessage;
						}
					 
					 public ResponseMessage getAllPendingClaims(String sessionId,PaginationDTO paginationDTO, ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getAllPendingClaims service ");
							responseMessage=engineeringQuotationDB.getAllPendingClaims(sessionId,paginationDTO, responseMessage);
							log.info("Request is exiting from getAllPendingClaims service");
							return responseMessage;
						}
					 
					 public ResponseMessage updateRenewalDates(String sessionId,RenewalEndorsementDto endorsementDto,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside updateRenewalDates service ");
							responseMessage=engineeringQuotationDB.updateRenewalDates(sessionId, endorsementDto, responseMessage);
							log.info("Request is exiting from updateRenewalDates service");
							return responseMessage;
						}
					 
					 public ResponseMessage getEndorsementDetails(String sessionId,String endorsementNumber,String policyNumber,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getEndorsementDetails service ");
							responseMessage=engineeringQuotationDB.getEndorsementDetails(sessionId, endorsementNumber,policyNumber, responseMessage);
							log.info("Request is exiting from getEndorsementDetails service");
							return responseMessage;
						}
					 
					 
					 public ResponseMessage updateRenewalEndorsement(String sessionId,String policyId,PolicyHeaderDetailsDto policyHeaderDetailsDto,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside updateRenewalEndorsement service");
							responseMessage=engineeringQuotationDB.updateRenewalEndorsement(sessionId,policyId, policyHeaderDetailsDto, responseMessage);
							log.info("Request is exiting from updateRenewalEndorsement service");
							return responseMessage;
						}
					 
					
					 
					 
					 public void sendWebhook(String sessionId,ClaimRegistrationDto claimRegistrationDto,boolean status,String msg,String response,String responseCode) {	
							log.info("request is inside sending webhook for claim registration");
						JSONObject responseJson=new JSONObject();
						String res=null;
						
						
						try {
							
							

							
							
							JSONObject claimRes=new JSONObject(response);
						responseJson.put("policyNumber", claimRegistrationDto.getPolicyNumber());	
						responseJson.put("lossDate", claimRegistrationDto.getLossDate());
						if(status) {
						responseJson.put("Claim_number", claimRes.getString("Claim_number"));
						responseJson.put("responseCode", "200");
						}else {
							responseJson.put("responseCode", responseCode);
						}
						 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
						    Date date = new Date();  
						    System.out.println(formatter.format(date));  
						responseJson.put("date", formatter.format(date));
							
						responseJson.put("message", msg);
						responseJson.put("status", status);
						JSONObject creditResponse=new JSONObject();
						
						creditResponse.put("event", "claim.registration.notification");//freeze
						creditResponse.put("data", responseJson);
						//RestTemplateUtil.callBackAPI(creditResponse.toString(),response.getString("url"));
						
						
							try {
								String responseData=restTemplateUtil.callBackAPIwithHeader(creditResponse.toString(),claimWebhookUrl,sessionId);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						log.info("response is sent for webhook for claim registration");
						}
						
						
				
					 public ResponseMessage updateInsuredCustomer(InsuredCustomerDTO insuredCustomerDTO,String sessionId,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside updateInsuredCustomer service ");
							
								
								ObjectMapper mapper = new ObjectMapper();

								try {
									String json = mapper.writeValueAsString(insuredCustomerDTO);
								
								
								//JSONObject req=new JSONObject(insuredCustomerDTO.toString());
							
							
							String response=restTemplateUtil.callAPIwithToken1(json, updateKYCURL, sessionId);
							if(response!=null) {
							try {
								
								JSONObject res=new JSONObject(response);
								responseMessage.setSuccess(res.getBoolean("success"));
								responseMessage.setMessage(res.getString("message"));
//								if(res.has("data")) {
//									responseMessage.setData(res.get("data"));
//								}
								if(responseMessage.isSuccess())
								responseMessage.setResponseCode("200");
								else
									responseMessage.setResponseCode("201");
							
							}catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							}
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
					 
							
							log.info("Request is exiting from updateInsuredCustomer service");
							return responseMessage;
						}
	 
					 
					 
					 public ResponseMessage getClaimsSummary(String sessionId,String lobId,ResponseMessage responseMessage) {
							
							
							log.info("Request is inside getClaimsSummary service ");
							responseMessage=engineeringQuotationDB.getClaimsSummary(sessionId,lobId, responseMessage);
							log.info("Request is exiting from getClaimsSummary service");
							return responseMessage;
						}
					 
		
	}
