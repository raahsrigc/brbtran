package com.erp.api.dao;



	import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.ClaimRegistrationDto;
import com.erp.api.dto.ClaimSettlementDto;
import com.erp.api.dto.PaginationDTO;
import com.erp.api.dto.PolicyHeaderDetailsDto;
import com.erp.api.dto.PolicyUpdateDetailsDto;
import com.erp.api.dto.RenewalEndorsementDto;
import com.erp.api.dto.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

	@Repository
	public class EngineeringClaimDB {

		@Autowired
		EntityManager entityManager;
		
		
		
	private final Logger log = LoggerFactory.getLogger(this.getClass());
		
		
	
	
		
		public ResponseMessage updateEngineeringInsuredDetails(String sessionId,String policyId,PolicyHeaderDetailsDto policyHeaderDetailsDto,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updateEngineeringInsuredDetails ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[UPDATE_POLICY_HEADER_DETAILS]"+"(?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setInt(2,Integer.valueOf(policyId));
						//callst.setInt(3,Integer.valueOf(policyHeaderDetailsDto.getBusinessType()));
						//callst.setInt(4, Integer.valueOf(policyHeaderDetailsDto.getQuotationNumber()));
						//callst.setString(5,policyHeaderDetailsDto.getBrokerCode());
						//callst.setString(6,policyHeaderDetailsDto.getProductCode());
						//callst.setString(7,policyHeaderDetailsDto.getInsuredCode());
						callst.setBigDecimal(3,BigDecimal.valueOf(policyHeaderDetailsDto.getSIAmount()));
						//callst.setBigDecimal(9,BigDecimal.valueOf(policyHeaderDetailsDto.getPremiumAmount()));
						callst.setBigDecimal(4,BigDecimal.valueOf(policyHeaderDetailsDto.getPremiumPercentage()));
						callst.setBigDecimal(5,BigDecimal.valueOf(policyHeaderDetailsDto.getDiscountPercentage()));
						//callst.setInt(12,Integer.valueOf(policyHeaderDetailsDto.getBusinessType()));
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from UPDATE_POLICY_HEADER_DETAILS: "+abc);
							JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject();
							data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=json.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updateEngineeringInsuredDetails details");
			
				return responseMessage;
			
			
		}
		
		
	
		
		public ResponseMessage getAllPendingRegisteredClaims(String sessionId,String lob,String businessType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getAllPendingRegisteredClaims ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[GET_ALL_POLICY_LIST_FOR_APPROVAL]"+"(?,?,?,?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2,paginationDTO.getFromDate());
						callst.setString(3,paginationDTO.getToDate());
						callst.setInt(4,paginationDTO.getPageCount());
						callst.setInt(5,paginationDTO.getPageNumber());
						callst.setBoolean(6,paginationDTO.getIsSearch());
						callst.setString(7,paginationDTO.getColumnName());
						callst.setString(8,paginationDTO.getValue());
						callst.setInt(9,Integer.valueOf(lob));
						callst.setInt(10,Integer.valueOf(businessType));
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_ALL_POLICY_LIST_FOR_APPROVAL: "+abc);
							JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject();
							data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							JSONArray data2=new JSONArray();
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=json.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getAllPendingRegisteredClaims details");
			
				return responseMessage;
			
			
		}
		
		
		public ResponseMessage saveClaimSettlement(String sessionId,ClaimSettlementDto claimSettlementDto,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for saveClaimSettlement ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[CLAIM].[INSERT_CLAIM_SETTLEMENT_DETAILS]"+"(?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2,claimSettlementDto.getClaimNumber());
						callst.setDate(3, Date.valueOf(claimSettlementDto.getSettlementDate()));
						callst.setBigDecimal(4, BigDecimal.valueOf(claimSettlementDto.getSettlementAmount()));
						callst.setString(5,claimSettlementDto.getRemarks());
						callst.setString(6,claimSettlementDto.getDvEmail());
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from INSERT_CLAIM_SETTLEMENT_DETAILS: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							JSONObject json=new JSONObject(data.getString("DATA"));
							
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=json.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer saveClaimSettlement details");
			
				return responseMessage;
			
			
		}
		
		
		
		
		public ResponseMessage getAllRegisteredClaims(String sessionId,String lob,String businessType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getAllRegisteredClaims ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[GET_ALL_ISSUED_POLICY_LIST]"+"(?,?,?,?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2,paginationDTO.getFromDate());
						callst.setString(3,paginationDTO.getToDate());
						callst.setInt(4,paginationDTO.getPageCount());
						callst.setInt(5,paginationDTO.getPageNumber());
						callst.setBoolean(6,paginationDTO.getIsSearch());
						callst.setString(7,paginationDTO.getColumnName());
						callst.setString(8,paginationDTO.getValue());
						callst.setInt(9,Integer.valueOf(lob));
						callst.setInt(10,Integer.valueOf(businessType));
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_ALL_ISSUED_POLICY_LIST: "+abc);
							JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject();
							data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							JSONArray data2=new JSONArray();
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=json.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getAllRegisteredClaims details");
			
				return responseMessage;
			
			
		}
		
	
		
		public ResponseMessage GetRegisteredClaimById(String sessionId,String claimId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for GetRegisteredClaimById details");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						
						callst=connection.prepareCall("{call "+"[CLAIM].[GET_CLAIM_DETAILS_BY_ID_FOR_ENGINEERING]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setInt(2,Integer.valueOf(claimId));
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_CLAIM_DETAILS_BY_ID_FOR_ENGINEERING: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							JSONObject dataArray=new JSONObject();
							JSONArray data2=new JSONArray(data.get("DATA").toString());
							dataArray=data2.getJSONObject(0);
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=dataArray.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
								responseMessage.setMessage("successfully fetched the data");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer GetRegisteredClaimById details");
			
				return responseMessage;
			
			
		}
		
	
		
		public ResponseMessage generateCreditDebitNote(String sessionId,String claimId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for generateCreditDebitNote details");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
					
						//	callst=connection.prepareCall("{call "+"[CLAIM].[GET_TRANACTION_HISTORY_FOR_CLAIM_BY_ID]"+"(?,?)}");
							callst=connection.prepareCall("{call "+"[CLAIM].[GET_TRANACTION_HISTORY_FOR_CLAIM_BY_ID]"+"(?,?)}");
							
						callst.setString(1,sessionId);
						callst.setInt(2,Integer.valueOf(claimId));
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_TRANACTION_HISTORY_FOR_CLAIM_BY_ID: "+abc);
							JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject();
							data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							JSONArray dataArray=new JSONArray(data.get("DATA").toString());
							JSONArray data2=new JSONArray();
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=dataArray.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
							
								responseMessage.setMessage("successfully fetched the data");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer generateCreditDebitNote details");
			
				return responseMessage;
			
			
		}
		
		
		
		public ResponseMessage updatePolicyEndorsement(String sessionId,String policyId,String endorsementType,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updatePolicyEndorsement ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[UPDATE_POLICY_TO_ENDORSMENT]"+"(?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setInt(2,Integer.valueOf(endorsementType));
						callst.setInt(3,Integer.valueOf(policyId));
						//callst.setString(4,policyData);
						
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from UPDATE_POLICY_TO_ENDORSMENT: "+abc);
							JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject();
							data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							JSONArray data2=new JSONArray();
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
								String s1=json.toString();
							//	String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updatePolicyEndorsement details");
			
				return responseMessage;
			
			
		}
		
		
	
	
		
		
		
		public ResponseMessage getNAICOMEngineeringPolicyData(String sessionId,String claimNumber,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getNAICOMEngineeringPolicyData ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[GET_NAICOM_CLAIM_UPLOAD_REQUEST_FOR_ENGINEERING]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, claimNumber);
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_NAICOM_CLAIM_UPLOAD_REQUEST_FOR_ENGINEERING: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							JSONObject temp=new JSONObject();
							temp=json.getJSONObject(0);
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=temp.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getNAICOMEngineeringPolicyData details");
			
				return responseMessage;
			
			
		}
		
		
		public ResponseMessage updateNAICOMEngineeringPolicyData(String sessionId,String serviceType,String policyId,Boolean status,String responseCode,String responseMsg,String policyIdNaicom,String policyUniqueId ,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updateNAICOMEngineeringPolicyData ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[UPDATE_NAICOM_MOTOR_UPLOAD_RESPONSE]"+"(?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						callst.setBoolean(3,status);
						callst.setString(4,policyIdNaicom);
						callst.setString(5,policyUniqueId);
						callst.setString(6,responseCode);
						callst.setString(7,responseMsg);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from UPDATE_NAICOM_CLAIM_UPLOAD_RESPONSE: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updateNAICOMEngineeringPolicyData details");
			
				return responseMessage;
			
			
		}
		
		
		
		
		
		public ResponseMessage renewEngineeringPolicyDetails(String sessionId,String policyId, String renewPolicyData,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for renewEngineeringPolicyDetails ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[INSERT_POLICY_HEADER_DETAILS_FOR_ENGINEERING]"+"(?,?,?,?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setInt(2, Integer.valueOf(policyId));
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from INSERT_POLICY_HERADER_DETAILS_FOR_ENGINEERING: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=json.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer renewEngineeringPolicyDetails details");
			
				return responseMessage;
			
			
		}
		
		public ResponseMessage updateRenewNAICOMPolicyResponse(String sessionId,String serviceType,String policyId,Boolean status,String responseCode,String responseMsg,String policyIdNaicom,String policyUniqueId ,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updateRenewNAICOMPolicyResponse ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[Update_NAICOM_ENGG_UPLOAD_RESPONSE_FOR_POLICY_RENEWAL]"+"(?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2,policyId);
						callst.setBoolean(3,status);
						callst.setString(4,policyIdNaicom);
						callst.setString(5,policyUniqueId);
						callst.setString(6,responseCode);
						callst.setString(7,responseMsg);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from Update_NAICOM_ENGG_UPLOAD_RESPONSE_FOR_POLICY_RENEWAL: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=json.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updateRenewNAICOMPolicyResponse details");
			
				return responseMessage;
			
			
		}
		
		public ResponseMessage cancelEngineeringPolicy(String sessionId,String policyId, String cancelPolicyData,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for cancelEngineeringPolicy ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[UW].[INSERT_POLICY_HEADER_DETAILS_FOR_ENGINEERING]"+"(?,?,?,?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setInt(2, Integer.valueOf(policyId));
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from INSERT_POLICY_HERADER_DETAILS_FOR_ENGINEERING: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONObject json=new JSONObject(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=json.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer cancelEngineeringPolicy details");
			
				return responseMessage;
			
			
		}
		
		
		
		
		public ResponseMessage getRenewNAICOMEngineeringPolicyData(String sessionId,String policyId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getRenewNAICOMEngineeringPolicyData ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[GET_NAICOM_ENGG_UPLOAD_REQUEST_FOR_POLICY_RENEWAL]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_NAICOM_ENGG_UPLOAD_REQUEST_FOR_POLICY_RENEWAL: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							JSONObject data2=new JSONObject();
							data2=json.getJSONObject(0);
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getRenewNAICOMEngineeringPolicyData details");
			
				return responseMessage;
			
			
		}
		
		
		
		
		public ResponseMessage getCancelNAICOMEngineeringPolicyData(String sessionId,String policyId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getCancelNAICOMEngineeringPolicyData ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[GET_NAICOM_POLICY_CANCILATION_REQUEST]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_NAICOM_POLICY_CANCILATION_REQUEST: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							JSONObject data2=new JSONObject();
							data2=json.getJSONObject(0);
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getCancelNAICOMEngineeringPolicyData details");
			
				return responseMessage;
			
			
		}
		
		
		public ResponseMessage updateCancelNAICOMPolicyResponse(String sessionId,String serviceType,String policyId,Boolean status,String responseCode,String responseMsg,String policyIdNaicom,String policyUniqueId ,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updateCancelNAICOMPolicyResponse ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[UPDATE_NAICOM_POLICY_CANCILATION_RESPONSE]"+"(?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						callst.setBoolean(3,status);
						callst.setString(4,policyIdNaicom);
						callst.setString(5,policyUniqueId);
						callst.setString(6,responseCode);
						callst.setString(7,responseMsg);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from UPDATE_NAICOM_POLICY_CANCILATION_RESPONSE: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updateCancelNAICOMPolicyResponse details");
			
				return responseMessage;
			
			
		}
		
		
		
		public ResponseMessage getNAICOMEUpdatEngineeringClaimData(String sessionId,String policyId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getNAICOMEUpdatEngineeringPolicyData ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[GET_NAICOM_CLAIM_SETTLEMENT_UPLOAD_REQUEST_FOR_ENGINEERING]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_NAICOM_CLAIM_SETTLEMENT_UPLOAD_REQUEST_FOR_ENGINEERING: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							//JSONArray data2=new JSONArray(data.get("DATA").toString());
//							
							JSONObject response=new JSONObject();
							JSONObject temp=new JSONObject();
							temp=json.getJSONObject(0);
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=temp.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getNAICOMEUpdatEngineeringPolicyData details");
			
				return responseMessage;
			
			
		}
		
		
		public ResponseMessage updateNAICOMUpdateEngineeringPolicyResponse(String sessionId,String serviceType,String policyId,Boolean status,String responseCode,String responseMsg,String policyIdNaicom,String policyUniqueId ,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for updateNAICOMUpdateEngineeringPolicyResponse ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[UPDATE_NAICOM_ENGG_UPLOAD_RESPONSE_FOR_UPDATE]"+"(?,?,?,?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						callst.setBoolean(3,status);
						callst.setString(4,policyIdNaicom);
						callst.setString(5,policyUniqueId);
						callst.setString(6,responseCode);
						callst.setString(7,responseMsg);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from UPDATE_NAICOM_ENGG_UPLOAD_RESPONSE_FOR_UPDATE: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer updateNAICOMUpdateEngineeringPolicyResponse details");
			
				return responseMessage;
			
			
		}
		
		
		
		public ResponseMessage getNAICOMpolicyStatus(String sessionId,String policyId,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for getNAICOMpolicyStatus ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[REGULATION].[GET_NAICOM_POLICY_STATUS]"+"(?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, policyId);
						
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from GET_NAICOM_POLICY_STATUS: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							JSONArray json=new JSONArray(data.getString("DATA"));
							
							JSONObject data2=new JSONObject();
							data2=json.getJSONObject(0);
							JSONObject response=new JSONObject();
							ObjectMapper mapper1=new ObjectMapper();

							try {
							//	String s1=json.toString();
								String s1=data2.toString();
									Object o1=mapper1.readValue(s1, Object.class);
									response.put("DATA", o1);	
									responseMessage.setData(o1);
							} catch (JsonMappingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (JsonProcessingException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							
								responseMessage.setMessage("Data has been fetched successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer getNAICOMpolicyStatus details");
			
				return responseMessage;
			
			
		}
		
		
		
		
	
		//////////////////////////////////////////////
		
		
		
		public ResponseMessage insertThirdPartyRequest(String sessionId,String serviceType,String requestData,String serviceProvider,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for insertThirdPartyRequest ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]"+"(?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, serviceType);
						callst.setString(3,requestData);
						callst.setString(4,serviceProvider);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from INSERT_REQUEST_LOG_THIRD_PARTY: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer insertThirdPartyRequest details");
			
				return responseMessage;
			
			
		}
		

		public ResponseMessage insertThirdPartyResponse(String sessionId,String serviceType,String responseData,String serviceProvider,ResponseMessage responseMessage) {
			log.info("Request is inside db layer for insertThirdPartyResponse ");
			Session session=entityManager.unwrap(Session.class);
			//String procName="insert_virtual_account_request";
			
			try {
				
			session.doWork(new Work() {

				@Override
				public void execute(Connection connection) throws SQLException {
					// TODO Auto-generated method stub
					CallableStatement callst=null;
					ResultSet resultSet=null;
					try {
						callst=connection.prepareCall("{call "+"[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]"+"(?,?,?,?)}");
						
						callst.setString(1,sessionId);
						callst.setString(2, serviceType);
						callst.setString(3,responseData);
						callst.setString(4,serviceProvider);
						callst.execute();
						resultSet=callst.getResultSet();
						while(resultSet.next()) {
							String abc=resultSet.getObject("Data").toString();
							log.info("response from INSERT_RESPONSE_LOG_THIRD_PARTY: "+abc);
							//JSONArray array=new JSONArray(abc);
							
							JSONObject data=new JSONObject(abc);
							//data=array.getJSONObject(0);
							if(data.getString("RESPONSE_CODE").equals("200")) {
							responseMessage.setSuccess(true);
							
							
							
								responseMessage.setMessage("Data has been submitted successfully");
						}else {
							responseMessage.setSuccess(false);
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}
						}
						
						
					}
					catch(Exception e) {
						responseMessage.setSuccess(false);
						e.printStackTrace();
					}
					
					finally {
						if(callst!=null) {
							callst.close();
						}
					}
					
				}
				
			});
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			log.info("exiting from db layer insertThirdPartyResponse details");
			
				return responseMessage;
			
			
		}
		
		
		
		
		
		//////////////////////////
		
		
		
		public ResponseMessage insertReqestNiidDao(int claimId, String sessionId, ResponseMessage responseMessage1) {
	        log.info("insert request and get response for niid   :-" + claimId);
	        Session session = entityManager.unwrap(Session.class);
	        try {
	            session.doWork(new Work() {
	                @Override
	                public void execute(Connection connection) throws SQLException {
	                    CallableStatement callst = null;
	                    ResultSet resultSet = null;
	                    try {
	                        callst = connection.prepareCall("{call " + "[REGULATION].[GET_NIID_REQUEST_FOR_MOTOR_CLAIM]" + "(?,?)}");
	                        callst.setString(1, sessionId);
	                        callst.setInt(2, claimId);
	                        
	                        
	                        callst.execute();
	                        resultSet = callst.getResultSet();
	                        JSONObject  data = null;
	                        while (resultSet.next()) {
	                            String deleteBene=resultSet.getObject("Data").toString();
	                            log.info("!!!!!!!!!! response from REGULATION.GET_NIID_REQUEST_FOR_MOTOR_CLAIM:------- "+deleteBene);
	                            String bulkInsert=resultSet.getObject("Data").toString();
	                            data = new JSONObject(bulkInsert.toString());
	                            if(data.getString("RESPONSE_CODE").equals("200")) {
	                                responseMessage1.setSuccess(true);;
	                                ObjectMapper mapper=new ObjectMapper();
	                                String queryRemitterRequest=data.optString("DATA");
	                                Object mapperObj=mapper.readValue(queryRemitterRequest, Object.class);
	                                responseMessage1.setData(mapperObj);
	                                responseMessage1.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                                responseMessage1.setResponseCode(data.get("RESPONSE_CODE").toString());
	                        }else {
	                            responseMessage1.setSuccess(false);
	                            responseMessage1.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                            responseMessage1.setResponseCode(data.get("RESPONSE_CODE").toString());
	                            responseMessage1.setData(null);
	                        }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    } finally {
	                        if (callst != null) {
	                            callst.close();
	                        }
	                    }
	                }
	            });
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        log.info("exiting from db layer delete provicsion  motor");
	        return responseMessage1;
	    }
	    public ResponseMessage insertThirdPartyRequestDao(String thirdPartyName, String baseUrl, String sessionId, String thirdPartyData, ResponseMessage responseMessage) {
	        log.info("Save third party request data :-" + thirdPartyName+"baseUrl"+baseUrl+"sessionId:-"+sessionId);
	        Session session = entityManager.unwrap(Session.class);
	        try {
	            session.doWork(new Work() {
	                @Override
	                public void execute(Connection connection) throws SQLException {
	                    CallableStatement callst = null;
	                    ResultSet resultSet = null;
	                    try {
	                        callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
	                        callst.setString(1, sessionId);
	                        callst.setString(2, baseUrl);
	                        callst.setString(3, thirdPartyData);
	                        callst.setString(4, thirdPartyName);
	                        
	                        
	                        callst.execute();
	                        resultSet = callst.getResultSet();
	                        JSONObject  data = null;
	                        while (resultSet.next()) {
	                            String deleteBene=resultSet.getObject("Data").toString();
	                            log.info("!!!!!!!!!! response from DATA_LOG.INSERT_REQUEST_LOG_THIRD_PARTY:------- "+deleteBene);
	                            String bulkInsert=resultSet.getObject("Data").toString();
	                            data = new JSONObject(bulkInsert.toString());
	                            if(data.getString("RESPONSE_CODE").equals("200")) {
	                                responseMessage.setSuccess(true);
	                                responseMessage.setData(data.optString("DATA"));
	                                responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                                responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                        }else {
	                            responseMessage.setSuccess(false);
	                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                            responseMessage.setData(null);
	                        }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    } finally {
	                        if (callst != null) {
	                            callst.close();
	                        }
	                    }
	                }
	            });
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        log.info("exiting from db layer delete provicsion  motor");
	        return responseMessage;
	    }
	    public ResponseMessage insertThirdPartyResponseDao(String thirdPartyName, String baseUrl, String sessionId, String finalResponseBody, ResponseMessage responseMessage) {
	        log.info("save third party response data :-" + thirdPartyName+"baseUrl"+baseUrl+"sessionId:-"+sessionId);
	        Session session = entityManager.unwrap(Session.class);
	        try {
	            session.doWork(new Work() {
	                @Override
	                public void execute(Connection connection) throws SQLException {
	                    CallableStatement callst = null;
	                    ResultSet resultSet = null;
	                    try {
	                        callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
	                        callst.setString(1, sessionId);
	                        callst.setString(2, baseUrl);
	                        callst.setString(3, finalResponseBody);
	                        callst.setString(4, thirdPartyName);
	                        
	                        
	                        callst.execute();
	                        resultSet = callst.getResultSet();
	                        JSONObject  data = null;
	                        while (resultSet.next()) {
	                            String deleteBene=resultSet.getObject("Data").toString();
	                            log.info("!!!!!!!!!! response from DATA_LOG.INSERT_RESPONSE_LOG_THIRD_PARTY:------- "+deleteBene);
	                            String bulkInsert=resultSet.getObject("Data").toString();
	                            data = new JSONObject(bulkInsert.toString());
	                            if(data.getString("RESPONSE_CODE").equals("200")) {
	                                responseMessage.setSuccess(true);
	                                responseMessage.setData(data.optString("DATA"));
	                                responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                                responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                        }else {
	                            responseMessage.setSuccess(false);
	                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                            responseMessage.setData(null);
	                        }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    } finally {
	                        if (callst != null) {
	                            callst.close();
	                        }
	                    }
	                }
	            });
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        log.info("exiting from db layer delete provicsion  motor");
	        return responseMessage;
	    }
	    public ResponseMessage insertFinalResponseDao(int claimId, String sessionId, String finalResponseBody,
	    		ResponseMessage responseMessage) {
	        log.info("Final insert request body for niid claim  claimId  :-" + claimId+"finalResponseBody"+finalResponseBody+"sessionId:-"+sessionId);
	        Session session = entityManager.unwrap(Session.class);
	        try {
	            session.doWork(new Work() {
	                @Override
	                public void execute(Connection connection) throws SQLException {
	                    CallableStatement callst = null;
	                    ResultSet resultSet = null;
	                    try {
	                        callst = connection.prepareCall("{call " + "[REGULATION].[UPDATE_NIID_RESPONSE_FOR_MOTOR_CLAIM]" + "(?,?,?,?)}");
	                        callst.setString(1, sessionId);
	                        callst.setString(2, String.valueOf(claimId));
	                        callst.setString(3, finalResponseBody);
	                        callst.setString(4, "");
	                        
	                        
	                        callst.execute();
	                        resultSet = callst.getResultSet();
	                        JSONObject  data = null;
	                        while (resultSet.next()) {
	                            String deleteBene=resultSet.getObject("Data").toString();
	                            log.info("!!!!!!!!!! response from REGULATION.UPDATE_NIID_RESPONSE_FOR_MOTOR_CLAIM:------- "+deleteBene);
	                            String bulkInsert=resultSet.getObject("Data").toString();
	                            data = new JSONObject(bulkInsert.toString());
	                            if(data.getString("RESPONSE_CODE").equals("200")) {
	                                responseMessage.setSuccess(true);
	                                responseMessage.setData(data.optString("DATA"));
	                                responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                                responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                        }else {
	                            responseMessage.setSuccess(false);
	                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
	                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
	                            responseMessage.setData(null);
	                        }
	                        }
	                    } catch (Exception e) {
	                        e.printStackTrace();
	                    } finally {
	                        if (callst != null) {
	                            callst.close();
	                        }
	                    }
	                }
	            });
	        } catch (Exception e1) {
	            e1.printStackTrace();
	        }
	        log.info("exiting from db layer insertFinalResponseDao ");
	        return responseMessage;
	    }

	    
	    
	  
			
			
	////////////////////////////////		
			
			
			
			public ResponseMessage claimRegistration(String sessionId,ClaimRegistrationDto claimRegistrationDto,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for claimRegistration details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[INSERT_CLAIM_REGISTRATION_DETAILS_API]"+"(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,claimRegistrationDto.getPolicyNumber());
							callst.setString(3,claimRegistrationDto.getNotificationNumber());
							callst.setDate(4, Date.valueOf(claimRegistrationDto.getLossDate()) );
							callst.setDate(5,Date.valueOf(claimRegistrationDto.getNotificationDate()));
							callst.setInt(6, Integer.valueOf(claimRegistrationDto.getCauseOfLoss()));
							callst.setInt(7,Integer.valueOf(claimRegistrationDto.getNatureOfLoss()));
							callst.setInt(8,Integer.valueOf(claimRegistrationDto.getInsuredCode())); 
							callst.setString(9,claimRegistrationDto.getLossRemarks());
							callst.setString(10,claimRegistrationDto.getAddress());
							callst.setInt(11,Integer.valueOf(claimRegistrationDto.getState()));
							callst.setInt(12,Integer.valueOf(claimRegistrationDto.getCity()));
							callst.setBigDecimal(13, BigDecimal.valueOf(claimRegistrationDto.getEstimateAmount()));
							
							ObjectMapper mapper2=new ObjectMapper();
							String temp=mapper2.writeValueAsString(claimRegistrationDto.getImageData());
							
								
							callst.setString(14,temp);
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from INSERT_CLAIM_REGISTRATION_DETAILS: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONObject dataArray=new JSONObject(data.get("DATA").toString());
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
									String s2=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
										responseMessage.setResponseCode("200");
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully submitted the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer claimRegistration details");
				
					return responseMessage;
				
				
			}
			
			
			
			public ResponseMessage GetClaimById(String sessionId,String claimId,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for GetClaimById details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_CLAIM_DETAILS_BY_ID_FOR_ENGINEERING_API]"+"(?,?)}");
							
							callst.setString(1,sessionId);
							callst.setInt(2,Integer.valueOf(claimId));
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CLAIM_DETAILS_BY_ID_FOR_ENGINEERING: "+abc);
								//JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject(abc);
								//data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONObject dataArray=new JSONObject();
								JSONArray data2=new JSONArray(data.get("DATA").toString());
								dataArray=data2.getJSONObject(0);
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
									String s2=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
										responseMessage.setResponseCode("200");
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer GetClaimById details");
				
					return responseMessage;
				
				
			}
				
			
			public ResponseMessage getAllClaims(String sessionId,String lob,String businessType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllClaims ");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_ALL_CLAIM_LIST_FOR_LOB]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,paginationDTO.getLobId());
							//callst.setInt(10,Integer.valueOf(businessType));
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_CLAIM_LIST_FOR_LOB: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								
								JSONObject json=new JSONObject(data.getString("DATA"));
								
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=json.toString();
									String s2=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);	
										responseMessage.setData(o1);
										responseMessage.setResponseCode("200");
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
									responseMessage.setMessage("Data has been fetched successfully");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllClaims details");
				
					return responseMessage;
				
				
			}	
			
			
			
			
			public ResponseMessage getCauseOfLoss(String sessionId,String lob,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getCauseOfLoss details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM].[GET_CAUSE_OF_LOSS_FOR_POLICY]"+"(?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,lob);
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CAUSE_OF_LOSS_FOR_POLICY: "+abc);
								//JSONArray array=new JSONArray(abc);
								JSONObject data=new JSONObject(abc);
								//data=array.getJSONObject(0);
								
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONArray dataArray=new JSONArray(data.get("DATA").toString());
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
									String s2=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getCauseOfLoss details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getNatureOfLoss(String sessionId,String lob,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getNatureOfLoss details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM].[GET_NATURE_OF_LOSS_LIST_BY_LOB_ID]"+"(?)}");
							
							//callst.setString(1,sessionId);
							callst.setInt(1,Integer.valueOf(lob));
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_NATURE_OF_LOSS_LIST_BY_LOB_ID: "+abc);
								JSONArray array=new JSONArray(abc);
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONArray dataArray=new JSONArray(data.get("DATA").toString());
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
									String s2=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getNatureOfLoss details");
				
					return responseMessage;
				
				
			}
			
			
			
			
			public ResponseMessage getMasterCodesPagination(String sessionId,String serviceType,PaginationDTO paginationDTO,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getMasterCodesPagination ");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[MST].[GET_CODE_MASTER_DETAILS]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setString(9,serviceType);
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CODE_MASTER_DETAILS: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
									responseMessage.setSuccess(true);
									
									JSONObject json=new JSONObject(data.getString("DATA"));
									
									JSONObject response=new JSONObject();
									ObjectMapper mapper1=new ObjectMapper();

									try {
										String s1=json.toString();
									//	String s1=data2.toString();
											Object o1=mapper1.readValue(s1, Object.class);
											response.put("DATA", o1);	
											responseMessage.setData(o1);
									} catch (JsonMappingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (JsonProcessingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
										responseMessage.setMessage("Data has been fetched successfully");
								}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getMasterCodesPagination details");
				
					return responseMessage;
				
				
			}
			
			
			public ResponseMessage getVehicleModel(String sessionId,String makeCode,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getVehicleModel ");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[QUOTATION].[GET_VEHICLE_MOD_BASED_VEHICLE_MAKE]"+"(?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,makeCode);
							
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_VEHICLE_MOD_BASED_VEHICLE_MAKE: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
									responseMessage.setSuccess(true);
									
									JSONArray json=new JSONArray(data.getString("DATA"));
									
									JSONObject response=new JSONObject();
									ObjectMapper mapper1=new ObjectMapper();

									try {
										String s1=json.toString();
									//	String s1=data2.toString();
											Object o1=mapper1.readValue(s1, Object.class);
											response.put("DATA", o1);	
											responseMessage.setData(o1);
									} catch (JsonMappingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (JsonProcessingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
										responseMessage.setMessage("Data has been fetched successfully");
								}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getVehicleModel details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getVehicleType(String sessionId,String category,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getVehicleType details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[MST].[GET_VEHICLE_TYPE_LIST]"+"(?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,category);
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_VEHICLE_TYPE_LIST: "+abc);
							//	JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject(abc);
							//	data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONArray dataArray=new JSONArray(data.get("DATA").toString());
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getVehicleType details");
				
					return responseMessage;
				
				
			}
			
			
			public ResponseMessage getClaimsSummary(String sessionId,String lobId,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getClaimsSummary details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_LOB_CARDS_FOR_CLAIMS]"+"(?,?)}");
							
							callst.setString(1,sessionId);
							callst.setInt(2,Integer.valueOf(lobId));
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_LOB_CARDS_FOR_CLAIMS: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								JSONArray dataArray=new JSONArray(data.get("DATA").toString());
								JSONArray data2=new JSONArray();
//								
								JSONObject response=new JSONObject();
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=dataArray.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getClaimsSummary details");
				
					return responseMessage;
				
				
			}
			
			
			
			public ResponseMessage getWalletSOA(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getWalletSOA details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[FINANCE].[GET_CUSTOMER_SOA]"+"(?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CUSTOMER_SOA: "+abc);
								
								//JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject(abc);
								//data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getWalletSOA details");
				
					return responseMessage;
				
				
			}
			
			
			public ResponseMessage getWalletSOAforProduct(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getWalletSOAforProduct details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[FINANCE].[GET_CUSTOMER_SOA_BY_PRODUCT]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,paginationDTO.getProductId());
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CUSTOMER_SOA_BY_PRODUCT: "+abc);
								
								//JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject(abc);
								//data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getWalletSOAforProduct details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getAllApprovedPolicies(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllApprovedPolicies details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[UW].[GET_ALL_APPROVED_POLICY]"+"(?,?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setString(9,"10");
							callst.setInt(10,2);
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_APPROVED_POLICY: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllApprovedPolicies details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getAllSettledClaims(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllSettledClaims details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_ALL_SETTLE_CLAIM_BY_LOB_CODE]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,paginationDTO.getLobId());
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_SETTLE_CLAIM_BY_LOB_CODE: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllSettledClaims details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getAllQuotations(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllQuotations details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CBA_API].[GET_ALL_QUOTATION_LIST_FOR_LOB]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,1);
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_QUOTATION_LIST_FOR_LOB: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllQuotations details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getAllApprovedClaims(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllApprovedClaims details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_ALL_APPROVED_CLAIMS_BY_LOB]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,paginationDTO.getLobId());
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_APPROVED_CLAIMS_BY_LOB: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllApprovedClaims details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getAllPendingClaims(String sessionId,PaginationDTO paginationDTO,  ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllPendingClaims details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CLAIM_API].[GET_REGISTERED_LIST_OF_CLAIMS_BY_LOB_CODE]"+"(?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,paginationDTO.getFromDate());
							callst.setString(3,paginationDTO.getToDate());
							callst.setInt(4,paginationDTO.getPageCount());
							callst.setInt(5,paginationDTO.getPageNumber());
							callst.setBoolean(6,paginationDTO.getIsSearch());
							callst.setString(7,paginationDTO.getColumnName());
							callst.setString(8,paginationDTO.getValue());
							callst.setInt(9,paginationDTO.getLobId());
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_REGISTERED_LIST_OF_CLAIMS_BY_LOB_CODE: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllPendingClaims details");
				
					return responseMessage;
				
				
			}
			
			
			public ResponseMessage updateRenewalDates(String sessionId,RenewalEndorsementDto endorsementDto,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for updateRenewalDates details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[UW].[UPDATE_RENEWAL_ENDORSEMENT]"+"(?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setDate(2,Date.valueOf(endorsementDto.getStartDate()));
							callst.setDate(3,Date.valueOf(endorsementDto.getEndDate()));
							callst.setString(4,endorsementDto.getPolicyNumber());
							callst.setDate(5,Date.valueOf(endorsementDto.getEndorsementDate()));
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from UPDATE_RENEWAL_ENDORSEMENT: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								JSONObject dataArray=new JSONObject(data.get("DATA").toString());
//								JSONArray data2=new JSONArray();
////								
//								JSONObject response=new JSONObject();
//								ObjectMapper mapper1=new ObjectMapper();
	//
//								try {
//									String s1=dataArray.toString();
//								//	String s1=data2.toString();
//										Object o1=mapper1.readValue(s1, Object.class);
//										response.put("DATA", o1);
//										responseMessage.setData(o1);
//								} catch (JsonMappingException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (JsonProcessingException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								
//								
								
									responseMessage.setMessage("successfully submitted the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer updateRenewalDates details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getEndorsementDetails(String sessionId,String endorsementNumber,String policyNumber,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getEndorsementDetails details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[UW].[GET_ALL_RENEWAL_ENDORSEMENT_DETAILS]"+"(?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setString(2,policyNumber);
							callst.setString(3,endorsementNumber);
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_ALL_RENEWAL_ENDORSEMENT_DETAILS: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								JSONObject response=new JSONObject(data.get("DATA").toString());
								ObjectMapper mapper1=new ObjectMapper();

								try {
									String s1=response.toString();
								//	String s1=data2.toString();
										Object o1=mapper1.readValue(s1, Object.class);
										response.put("DATA", o1);
										responseMessage.setData(o1);
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								
								
									responseMessage.setMessage("successfully fetched the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getEndorsementDetails details");
				
					return responseMessage;
				
				
			}
			
			
			public ResponseMessage updateRenewalEndorsement(String sessionId,String policyId,PolicyHeaderDetailsDto policyHeaderDetailsDto,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for updateRenewalEndorsement ");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[MOTOR_API_PMI].[UPDATE_POLICY_HEADER_DETAILS_MOTOR]"+"(?,?,?,?,?,?,?,?,?,?,?,?)}");
							
							callst.setString(1,sessionId);
							callst.setInt(2, Integer.valueOf(policyId));
						//	callst.setInt(3,Integer.valueOf(policyHeaderDetailsDto.getBusinessType()));
						//	callst.setInt(3, Integer.valueOf(policyHeaderDetailsDto.getQuotationNumber()));
							callst.setString(3,policyHeaderDetailsDto.getBrokerCode());
							callst.setString(4,policyHeaderDetailsDto.getProductCode());
							callst.setString(5,policyHeaderDetailsDto.getInsuredCode());
							callst.setBigDecimal(6,BigDecimal.valueOf(policyHeaderDetailsDto.getSIAmount()));
							callst.setBigDecimal(7,BigDecimal.valueOf(policyHeaderDetailsDto.getPremiumAmount()));
							callst.setBigDecimal(8,BigDecimal.valueOf(policyHeaderDetailsDto.getPremiumPercentage()));
							callst.setBigDecimal(9,BigDecimal.valueOf(policyHeaderDetailsDto.getDiscountPercentage()));
						//	callst.setInt(12,Integer.valueOf(policyHeaderDetailsDto.getPlanId()));
							callst.setDate(10, Date.valueOf(policyHeaderDetailsDto.getStartDate()));
							callst.setDate(11, Date.valueOf(policyHeaderDetailsDto.getEndDate()));
							callst.setDate(12, Date.valueOf(policyHeaderDetailsDto.getEndorsementDate()));
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from UPDATE_POLICY_HEADER_DETAILS_MOTOR: "+abc);
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
								
								//JSONObject json=new JSONObject(data.getString("DATA"));
								
//								JSONArray data2=new JSONArray(data.get("DATA").toString());
////								
//								JSONObject response=new JSONObject();
//								ObjectMapper mapper1=new ObjectMapper();
	//
//								try {
//								//	String s1=json.toString();
//									String s1=data2.toString();
//										Object o1=mapper1.readValue(s1, Object.class);
//										response.put("DATA", o1);	
//										responseMessage.setData(o1);
//								} catch (JsonMappingException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								} catch (JsonProcessingException e) {
//									// TODO Auto-generated catch block
//									e.printStackTrace();
//								}
//								
								
									responseMessage.setMessage("Data has been submitted successfully");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer updateRenewalEndorsement details");
				
					return responseMessage;
				
				
			}
		
			
			
			public ResponseMessage getAllSettledClaims(ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getAllSettledClaims details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CBA_API].[GET_CLAIM_SETTLEMENTLIST]"+"}");
							
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_CLAIM_SETTLEMENTLIST: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject temp=new JSONObject();
								temp=array.getJSONObject(0);
								if(temp.getString("RESPONSE_CODE").equals("200")) {
									JSONArray data2=new JSONArray(temp.get("DATA").toString());
								List<ClaimRegistrationDto> array1 = new ArrayList<ClaimRegistrationDto>();
								for(int i=0;i<data2.length();i++) {
									ClaimRegistrationDto virtualAccountDto=new ClaimRegistrationDto();
									JSONObject data=data2.getJSONObject(i);
									
										responseMessage.setSuccess(true);
										responseMessage.setMessage("success");
							
										//virtualAccountDto.setPolicyId(data.getString("policyId"));
										virtualAccountDto.setPolicyNumber(data.getString("policyNumber"));
										virtualAccountDto.setClaimId(data.getString("claimId"));
										virtualAccountDto.setClaimNumber(data.getString("claimNumber"));
										virtualAccountDto.setEstimateAmount(data.getDouble("claimAmount"));
										virtualAccountDto.setNotificationDate(data.getString("claimDate"));
										virtualAccountDto.setSettlementAmount(data.getDouble("settlementAmount"));
										virtualAccountDto.setSettlementDate(data.getString("settlementDate"));
										
										array1.add(virtualAccountDto);
										
									}
								responseMessage.setData(array1);
								}else {
										responseMessage.setSuccess(false);
										responseMessage.setMessage(temp.getString("RESPONSE_MESSAGE"));
										responseMessage.setData(null);
									}
									
								}
								
					
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getAllSettledClaims details");
				
					return responseMessage;
				
				
			}
				
			
			
			
			public ResponseMessage updateSettlementResponse(String claimId,String responsecode,String responseMsg,ResponseMessage responseMessage) {
				log.info("Request is inside db layer for updateSettlementResponse details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[CBA_API].[UPDATE_CLAIM_SETTLEMENT_WEBHOOK_STATUS]"+"(?,?,?)}");
							
							callst.setInt(1, Integer.valueOf(claimId));
							callst.setInt(2, Integer.valueOf(responsecode));
							callst.setString(3,responseMsg);
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from UPDATE_CLAIM_SETTLEMENT_WEBHOOK_STATUS: "+abc);
								
								JSONArray array=new JSONArray(abc);
								
								JSONObject data=new JSONObject();
								data=array.getJSONObject(0);
								if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setSuccess(true);
//								
								
									responseMessage.setMessage("successfully updated the data");
							}else {
								responseMessage.setSuccess(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							}
							}
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer updateSettlementResponse details");
				
					return responseMessage;
				
				
			}
			
			
			
			public ResponseMessage getNIIDPendingRecords(ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getNIIDPendingRecords details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[B2B_SCHEDULER].[GET_NIID_MOTOR_UPLOAD_REQUEST_SCHEDULAR]"+"}");
							
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_NIID_MOTOR_UPLOAD_REQUEST_SCHEDULAR: "+abc);
								
								//JSONArray array=new JSONArray(abc);
								
								JSONObject temp=new JSONObject(abc);
								//temp=array.getJSONObject(0);
								if(temp.getString("RESPONSE_CODE").equals("200")) {
									JSONArray data2=new JSONArray(temp.get("DATA").toString());
								List<PolicyUpdateDetailsDto> array1 = new ArrayList<PolicyUpdateDetailsDto>();
								for(int i=0;i<data2.length();i++) {
									PolicyUpdateDetailsDto virtualAccountDto=new PolicyUpdateDetailsDto();
									JSONObject data=data2.getJSONObject(i);
									
										responseMessage.setSuccess(true);
										responseMessage.setMessage("success");
							
										virtualAccountDto.setPolicyId(data.getString("POLICY_ID"));
										virtualAccountDto.setPolicyNumber(data.getString("POLICY_NUMBER"));
										virtualAccountDto.setQuotationId(data.getInt("QUOTATION_ID"));
										virtualAccountDto.setFromDate(data.getString("FROM_DATE"));
										virtualAccountDto.setToDate(data.getString("TO_DATE"));
										virtualAccountDto.setFirstName(data.getString("FIRST_NAME"));
										virtualAccountDto.setLastName(data.getString("LAST_NAME"));
										virtualAccountDto.setDob(data.getString("DOB"));
										virtualAccountDto.setAddress(data.getString("ADDRESS"));
										virtualAccountDto.setIdTpye(data.getString("ID_TYPE"));
										virtualAccountDto.setIdNumber(data.getString("ID_NUMBER"));
										virtualAccountDto.setVehicleId(data.getString("VEHICLE_ID"));
										virtualAccountDto.setMake(data.getString("VEHICLE_MAKE"));
										virtualAccountDto.setModel(data.getString("VEHICLE_MODEL"));
										virtualAccountDto.setRegisrtrationDate(data.getString("RegDate"));
										virtualAccountDto.setRegisrtrationEndDate(data.getString("RegExpDate"));
										virtualAccountDto.setAutoType(data.getString("AutoType"));
										virtualAccountDto.setYearOfMake(data.getInt("AutoYear"));
										virtualAccountDto.setChassisNo(data.getString("ChassisNo"));
										virtualAccountDto.setVehicleCategory(data.getString("AUTO_CATEGORY"));
										virtualAccountDto.setMobileNumber(data.getString("MOBILE_NUMBER"));
										virtualAccountDto.setToken(data.getString("TOKEN_ID"));
										virtualAccountDto.setWebHookUrl(data.getString("webhookUrl"));
										virtualAccountDto.setCountry(data.getString("COUNTRY"));
										virtualAccountDto.setState(data.getString("STATE"));
										virtualAccountDto.setCity(data.getString("CITY"));
										virtualAccountDto.setTitle(data.getString("TITLE"));
										array1.add(virtualAccountDto);
										
									}
								responseMessage.setData(array1);
								}else {
										responseMessage.setSuccess(false);
										responseMessage.setMessage(temp.getString("RESPONSE_MESSAGE"));
										responseMessage.setData(null);
									}
									
								}
								
					
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getNIIDPendingRecords details");
				
					return responseMessage;
				
				
			}
			
			public ResponseMessage getNAICOMPendingRecords(ResponseMessage responseMessage) {
				log.info("Request is inside db layer for getNAICOMPendingRecords details");
				Session session=entityManager.unwrap(Session.class);
				//String procName="insert_virtual_account_request";
				
				try {
					
				session.doWork(new Work() {

					@Override
					public void execute(Connection connection) throws SQLException {
						// TODO Auto-generated method stub
						CallableStatement callst=null;
						ResultSet resultSet=null;
						try {
							callst=connection.prepareCall("{call "+"[B2B_SCHEDULER].[GET_NAICOM_MOTOR_UPLOAD_REQUEST_SCHEDULAR]"+"}");
							
							
							callst.execute();
							resultSet=callst.getResultSet();
							while(resultSet.next()) {
								String abc=resultSet.getObject("Data").toString();
								log.info("response from GET_NAICOM_MOTOR_UPLOAD_REQUEST_SCHEDULAR: "+abc);
								
								//JSONArray array=new JSONArray(abc);
								
								JSONObject temp=new JSONObject(abc);
								//temp=array.getJSONObject(0);
								if(temp.getString("RESPONSE_CODE").equals("200")) {
									JSONArray data2=new JSONArray(temp.get("DATA").toString());
								List<PolicyUpdateDetailsDto> array1 = new ArrayList<PolicyUpdateDetailsDto>();
								for(int i=0;i<data2.length();i++) {
									PolicyUpdateDetailsDto virtualAccountDto=new PolicyUpdateDetailsDto();
									JSONObject data=data2.getJSONObject(i);
									
										responseMessage.setSuccess(true);
										responseMessage.setMessage("success");
							
										virtualAccountDto.setPolicyId(data.getString("POLICY_ID"));
										virtualAccountDto.setPolicyNumber(data.getString("POLICY_NUMBER"));
										virtualAccountDto.setQuotationId(data.getInt("QUOTATION_ID"));
										virtualAccountDto.setFromDate(data.getString("FROM_DATE"));
										virtualAccountDto.setToDate(data.getString("TO_DATE"));
										virtualAccountDto.setFirstName(data.getString("FIRST_NAME"));
										virtualAccountDto.setLastName(data.getString("LAST_NAME"));
										virtualAccountDto.setDob(data.getString("DOB"));
										virtualAccountDto.setAddress(data.getString("ADDRESS"));
										virtualAccountDto.setIdTpye(data.getString("ID_TYPE"));
										virtualAccountDto.setIdNumber(data.getString("ID_NUMBER"));
										virtualAccountDto.setVehicleId(data.getString("VEHICLE_ID"));
										virtualAccountDto.setMake(data.getString("VEHICLE_MAKE"));
										virtualAccountDto.setModel(data.getString("VEHICLE_MODEL"));
										virtualAccountDto.setRegisrtrationDate(data.getString("RegDate"));
										virtualAccountDto.setRegisrtrationEndDate(data.getString("RegExpDate"));
										virtualAccountDto.setAutoType(data.getString("AutoType"));
										virtualAccountDto.setYearOfMake(data.getInt("AutoYear"));
										virtualAccountDto.setChassisNo(data.getString("ChassisNo"));
										virtualAccountDto.setVehicleCategory(data.getString("AUTO_CATEGORY"));
										virtualAccountDto.setMobileNumber(data.getString("MOBILE_NUMBER"));
										virtualAccountDto.setToken(data.getString("TOKEN_ID"));
										virtualAccountDto.setWebHookUrl(data.getString("webhookUrl"));
										virtualAccountDto.setCountry(data.getString("COUNTRY"));
										virtualAccountDto.setState(data.getString("STATE"));
										virtualAccountDto.setCity(data.getString("CITY"));
										virtualAccountDto.setTitle(data.getString("TITLE"));
										
										array1.add(virtualAccountDto);
										
									}
								responseMessage.setData(array1);
								}else {
										responseMessage.setSuccess(false);
										responseMessage.setMessage(temp.getString("RESPONSE_MESSAGE"));
										responseMessage.setData(null);
									}
									
								}
								
					
							
							
						}
						catch(Exception e) {
							responseMessage.setSuccess(false);
							e.printStackTrace();
						}
						
						finally {
							if(callst!=null) {
								callst.close();
							}
						}
						
					}
					
				});
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				log.info("exiting from db layer getNAICOMPendingRecords details");
				
					return responseMessage;
				
				
			}
			
			
	}
