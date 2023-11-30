package com.erp.api.dao;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.EndorsementPolicyDto;
import com.erp.api.dto.InsertDashPolicyTrDto;
import com.erp.api.dto.InsertPolicyDto;
import com.erp.api.dto.InsertQuotationDto;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.KycResponseDto;
import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.dto.NaiComResposeDto;
import com.erp.api.dto.NiidResponseDto;
import com.erp.api.dto.NiidStatusResponseDto;
import com.erp.api.dto.PolicyResponseDto;
import com.erp.api.dto.QuotationDto;
import com.erp.api.dto.RenewalDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SchedulerPolicyDto;
import com.erp.api.dto.SchedulerResposeDto;
import com.erp.api.dto.UpdateNiidRequestDto;
import com.erp.api.dto.UpdatePolicyDto;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MotorApiDao {
	
	@Autowired
	EntityManager entityManager;
	
	@Autowired
	private ObjectMapper objectMapper;

	public ResponseDto getPlan(String token, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_PLAN_API]" + "(?)}")) {
                    	callst.setString(1, token);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- MOTOR_API_PMI.GET_ALL_PLAN_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto getQuotationById(Long quotationId, String token, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_QUOTATION_DETAILS_BY_ID_API]" + "(?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, quotationId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_QUOTATION_DETAILS_BY_ID_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto getPolicyById(String token, Long policyId, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_POLICY_DETAILS_BY_ID_FOR_MOTOR_API]" + "(?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, policyId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_POLICY_DETAILS_BY_ID_FOR_MOTOR_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto allIssuesPolicy(String token, IssuedPolicyDto issuedobj, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_ISSUED_POLICY_LIST_API]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, issuedobj.getFromDate());
                        callst.setString(3, issuedobj.getToDate());
                        callst.setInt(4, issuedobj.getPageCount());
                        callst.setInt(5, issuedobj.getPageNumber());
                        callst.setBoolean(6, issuedobj.isSearchKey());
                        callst.setString(7, issuedobj.getColumnName());
                        callst.setString(8, issuedobj.getColumnValue());
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  MOTOR_API_PMI.GET_ALL_ISSUED_POLICY_LIST_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto getAllQuotation(String token, QuotationDto quodobj, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_QUOTATION_LIST]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, quodobj.getFromDate());
                        callst.setString(3, quodobj.getToDate());
                        callst.setInt(4, quodobj.getPageCount());
                        callst.setInt(5, quodobj.getPageNumber());
                        callst.setBoolean(6, quodobj.isSearchKey());
                        callst.setString(7, quodobj.getColumnName());
                        callst.setString(8, quodobj.getColumnValue());
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_ALL_QUOTATION_LIST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }


	public NaiComResposeDto insertThirdPartyRequestDao(String thirdPartyName, String baseUrl, String token, String thirdPartyData, NaiComResposeDto responseMessage) {
		
		log.info("Save nai com insurence third party request data API Implementation:-" + thirdPartyName+"baseUrl"+baseUrl+"token:-"+token);
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, thirdPartyData);
						callst.setString(4, thirdPartyName);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR DATA_LOG.INSERT_REQUEST_LOG_THIRD_PARTY **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								responseMessage.setData(data.optString("DATA"));
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							responseMessage.setStatus(false);
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
		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
		return responseMessage;
	}

	public NiidResponseDto insertThirdPartyResponseDao(String thirdPartyName, String baseUrl, String token, String finalResponseBody, NiidResponseDto responseMessage) {
		log.info("save third party response data :-" + thirdPartyName+"baseUrl"+baseUrl+"token:-"+token);
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, finalResponseBody);
						callst.setString(4, thirdPartyName);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR DATA_LOG.INSERT_RESPONSE_LOG_THIRD_PARTY **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								responseMessage.setData(data.optString("DATA"));
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							responseMessage.setStatus(false);
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

	public NiidResponseDto saveCreatePolicyNiidResponse(String sessionId, long policyId, String niidResMesg, String niidResCode, NiidResponseDto responseMessage) {
		log.info("Request is inside db layer for get saveCreatePolicyNiidResponse Dao");
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_NIID_MOTOR_UPLOAD_RESPONSE]" + "(?,?,?,?)}");
						callst.setString(1, sessionId);
						callst.setLong(2, policyId);
						callst.setString(3, niidResCode);
						callst.setString(4, niidResMesg);
						callst.execute();

						resultSet = callst.getResultSet();
						JSONObject data=null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.UPDATE_NIID_MOTOR_UPLOAD_RESPONSE ********** "+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								ObjectMapper mapper=new ObjectMapper();
								String queryRemitterRequest=data.optString("DATA");
								Object mapperObj=mapper.readValue(queryRemitterRequest, Object.class);
								responseMessage.setData(mapperObj);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
								responseMessage.setNiidStatus(data.getInt("NIID_STATUS"));
						}else {
							responseMessage.setStatus(false);
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
		log.info("exiting from db layer get niid request Dao");
		return responseMessage;
	}

	public ResponseDto getCardSummary(String token, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_DOMESTIC_SUMMARY_DASHBOARD]" + "(?)}")) {
                    	callst.setString(1, token);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_DOMESTIC_SUMMARY_DASHBOARD **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto searchQuotation(String token, String emailMobile, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CBA_API].[GET_ALL_QUOTATION_FOR_INSURED_FROM_MOBILE_EMAIL]" + "(?,?)}")) {
                    	callst.setString(1, token);
                    	callst.setString(2, emailMobile);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  CBA_API.GET_ALL_QUOTATION_FOR_INSURED_FROM_MOBILE_EMAIL **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto productList(String token, String lobId, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CBA_API].[GET_ALL_LOB_PRODUCT_LIST]" + "(?)}")) {
                    	callst.setString(1, lobId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  CBA_API.GET_ALL_LOB_PRODUCT_LIST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto pendingForTrApproval(String token, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_POLICY_PENDING_FOR_APPROVAL]" + "(?)}")) {
                    	callst.setString(1, token);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_ALL_POLICY_PENDING_FOR_APPROVAL **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto generateQuotation(String token, InsertQuotationDto motorobj,
			ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_QUOTATION_REQUEST_MOTOR_PMI_26092023]" + "(?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, motorobj.getVehicleRegistrationNumber());
                        callst.setString(3, motorobj.getVehicleValue());
                        callst.setString(4, motorobj.getEmail());
                        callst.setString(5, motorobj.getMobileNumber());
                        callst.setString(6, motorobj.getInsuredName());
                        callst.setString(7, motorobj.getVehicleTypeCode());
                        callst.setString(8, "1");//b2b api mai hum sirf year ka lege is liye yha se hum 1 pass kar rhe hai 1 ka matb year hai dashboard mai
                        callst.setInt(9, 0);
                        callst.setInt(10, motorobj.getPlanId());
                        callst.setString(11, motorobj.getVehicleMake());
                        callst.setString(12, motorobj.getVehicleModel());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_QUOTATION_REQUEST_MOTOR_PMI **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
//                                responseMessage.setNiid(objectMapper.readValue(data.get("NIID").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public NiidResponseDto generatePolicy(String token, InsertPolicyDto insertobj,
			NiidResponseDto responseMessage, int kycidStatus, String kycmessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, kycidStatus);
                        callst.setString(22, kycmessage);
                        callst.setString(23, insertobj.getTitle());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                                responseMessage.setNiid(objectMapper.readValue(data.get("NIID").toString(),Object.class));
                            }
                            else {
                            	responseMessage.setData(null);
                            	responseMessage.setNiid(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto verificationVehicleDao(String searchValue, String searchType, JSONObject jsonObj, String token,
			ResponseDto responseMessage) {
//		log.info("Request is inside db layer for get  vehicle details :-" + jsonObj.toString());
		Session session = entityManager.unwrap(Session.class);
		
		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[CBA_API].[INSERT_VEHICLE_DETAILS_FOR_NIID]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, (jsonObj.optString("PolicyNumber") == null ? "":jsonObj.optString("PolicyNumber")));
						callst.setString(3, (jsonObj.optString("InsuredName") == null ? "":jsonObj.optString("InsuredName")));
						callst.setString(4, (jsonObj.optString("NewRegistrationNumber") == null ? "":jsonObj.optString("NewRegistrationNumber")));
						callst.setString(5, (jsonObj.optString("RegistrationNumber") == null ? "":jsonObj.optString("RegistrationNumber")));
						callst.setString(6, (jsonObj.optString("VehicleType") == null ? "":jsonObj.optString("VehicleType")));
						callst.setString(7, (jsonObj.optString("VehicleMake") == null ? "":jsonObj.optString("VehicleMake")));
						callst.setString(8, (jsonObj.optString("VehicleModel") == null ? "":jsonObj.optString("VehicleModel")));
						callst.setString(9, (jsonObj.optString("Color") == null ? "":jsonObj.optString("Color")));
						callst.setString(10, (jsonObj.optString("ChassisNumber") == null ? "":jsonObj.optString("ChassisNumber")));
						callst.setString(11, (jsonObj.optString("IssueDate") == null ? "":jsonObj.optString("IssueDate")));
						callst.setString(12, (jsonObj.optString("ExpiryDate") == null ? "":jsonObj.optString("ExpiryDate")));
						callst.setString(13, (jsonObj.optString("LicenseStatus") == null ? "":jsonObj.optString("LicenseStatus")));
						callst.setString(14, (jsonObj.optString("UploadDate") == null ? "":jsonObj.optString("UploadDate")));
						callst.setString(15, (jsonObj.optString("UploadTime") == null ? "":jsonObj.optString("UploadTime")));
						callst.setString(16, (jsonObj.optString("CoverType") == null ? "":jsonObj.optString("CoverType")));
						callst.execute();

						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR CBA_API.INSERT_VEHICLE_DETAILS_FOR_NIID **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								ObjectMapper mapper=new ObjectMapper();
								String queryRemitterRequest=data.optString("DATA");
								Object mapperObj=mapper.readValue(queryRemitterRequest, Object.class);
								responseMessage.setData(mapperObj);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							responseMessage.setStatus(false);
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
		log.info("exiting from db layer get fetch Quotation");
		return responseMessage;
	}

	public NaiComResposeDto insertRequestGeneratePolicy(String token, InsertPolicyDto insertobj,
			NaiComResposeDto responseMessage) {
		log.info(" *************** insertRequestGeneratePolicy method is completed ********************"+insertobj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR_REQUEST]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, 0);
                        callst.setString(22, "");
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                                responseMessage.setKycStatus(data.getInt("KYC_STATUS"));
                            }
                            else {
                            	responseMessage.setData(null);
                            	responseMessage.setNiidStatus(0);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public KycResponseDto insertKycResponse(String token, int kycStatus, String kycMesg, KycResponseDto kycResponse,long policyId) throws JSONException {
        try (Session session = entityManager.unwrap(Session.class)) {
        	//rakesh

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_KYC_STATUS]" + "(?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, policyId);
                        callst.setInt(3, kycStatus);
                        callst.setString(4, kycMesg);
                        
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.UPDATE_KYC_STATUS **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	kycResponse.setStatus(true);
                                kycResponse.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                                kycResponse.setNiid(objectMapper.readValue(data.get("NIID").toString(),Object.class));
                                kycResponse.setKycStatus(data.getInt("KYC_STATUS"));
                            }
                            else {
                            	kycResponse.setData(null);
                            }
                            kycResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            kycResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return kycResponse;
    }
	
	public NaiComResposeDto saveNaiComResponse(String naiComPolicyID, String policyUniqueID, String sessionId, long policyId,
			boolean thirdPartyStatus, String msg, String errCode, NaiComResposeDto responseMessage) {
		log.info("Request is inside db layer for get NiidRecordTr Dao");
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[REGULATION].[UPDATE_NAICOM_MOTOR_UPLOAD_RESPONSE]" + "(?,?,?,?,?,?,?)}");
						callst.setString(1, sessionId);
						callst.setLong(2, policyId);
						callst.setBoolean(3, thirdPartyStatus);
						callst.setString(4, naiComPolicyID);
						callst.setString(5, policyUniqueID);
						callst.setString(6, errCode);
						callst.setString(7, msg);
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject data=null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR REGULATION.UPDATE_NAICOM_MOTOR_UPLOAD_RESPONSE ********** "+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								ObjectMapper mapper=new ObjectMapper();
								String queryRemitterRequest=data.optString("DATA");
								Object mapperObj=mapper.readValue(queryRemitterRequest, Object.class);
								responseMessage.setData(mapperObj);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							responseMessage.setStatus(false);
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
		log.info("exiting from db layer get niid request Dao");
		return responseMessage;
	}

	public NaiComResposeDto insertNaiomResponseDao(String thirdPartyName, String baseUrl, String token, String finalResponseBody,NaiComResposeDto naiComResponse) {
		log.info("save third party response data :-" + thirdPartyName+"baseUrl"+baseUrl+"token:-"+token);
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, finalResponseBody);
						callst.setString(4, thirdPartyName);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR  DATA_LOG.INSERT_RESPONSE_LOG_THIRD_PARTY **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								naiComResponse.setStatus(true);
								naiComResponse.setData(data.optString("DATA"));
								naiComResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
								naiComResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							naiComResponse.setStatus(false);
							naiComResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
							naiComResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
							naiComResponse.setData(null);
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
		return naiComResponse;
	}

	public NiidResponseDto insertNiidRequestDao(String thirdPartyName, String baseUrl, String token, String thirdPartyData,NiidResponseDto niidResponseObj) {
		
		log.info("Save nai com insurence third party request data API Implementation:-" + thirdPartyName+"baseUrl"+baseUrl+"thirdPartyData:-"+thirdPartyData);
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, thirdPartyData);
						callst.setString(4, thirdPartyName);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR  DATA_LOG.INSERT_REQUEST_LOG_THIRD_PARTY **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								niidResponseObj.setStatus(true);
								niidResponseObj.setData(data.optString("DATA"));
								niidResponseObj.setMessage(data.get("RESPONSE_MESSAGE").toString());
								niidResponseObj.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							niidResponseObj.setStatus(false);
							niidResponseObj.setMessage(data.get("RESPONSE_MESSAGE").toString());
							niidResponseObj.setResponseCode(data.get("RESPONSE_CODE").toString());
							niidResponseObj.setData(null);
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
		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
		return niidResponseObj;
	}

//	public NaiComResposeDto insertRequestUpdatePolicy(String token, UpdatePolicyDto updateObj,NaiComResposeDto responseMessage) {
//        try (Session session = entityManager.unwrap(Session.class)) {
//
//            try {
//                session.doWork(connection -> {
//                    ResultSet resultSet = null;
//                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_POLICY_FOR_MOTOR_REQUEST]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
//                        callst.setString(1, token);
//                        callst.setLong(2, updateObj.getQuotationId());
//                        callst.setDate(3, Date.valueOf(updateObj.getFromDate()));
//                        callst.setDate(4, null);
//                        
//                        callst.setString(5, updateObj.getFirstName());
//                        callst.setString(6, updateObj.getLastName());
//                        callst.setDate(7, Date.valueOf(updateObj.getDob()));
//                        callst.setString(8, updateObj.getAddress());
//                        callst.setString(9, updateObj.getIdType());
//                        callst.setString(10, updateObj.getIdNumber());
//                        callst.setString(11, updateObj.getVehicleId());
//                        callst.setString(12, updateObj.getMake());
//                        callst.setString(13, updateObj.getModel());
//                        callst.setDate(14, Date.valueOf(updateObj.getRegistrationDate()));
//                        
//                        callst.setDate(15, Date.valueOf(updateObj.getRegistrationEndDate()));
//                        callst.setString(16, updateObj.getAutoType());
//                        callst.setInt(17, updateObj.getYearOfMake());
//                        callst.setString(18, updateObj.getChassisNo());
//                        callst.setString(19, updateObj.getVehicleCategory());
//                        callst.setString(20, updateObj.getMobileNumber());
//                        callst.setInt(21, 0);
//                        callst.setString(22, "");
//                        callst.setString(23, updateObj.getTitle());
//                        callst.setString(24, updateObj.getCity());
//                        callst.setString(25, updateObj.getState());
//                        callst.setString(26, updateObj.getCountry());
//                        callst.setLong(27, updateObj.getPolicyId());
//                        callst.setString(28, updateObj.getMiddleName());
//                        callst.setString(29, updateObj.getGender());
//                        callst.setString(30, updateObj.getEmailId());
//                        
//                        
//
//                        callst.execute();
//
//                        resultSet = callst.getResultSet();
//                        while (resultSet.next()) {
//                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
//                            log.info("7777777777-- GET DB RESPONSE FOR  MOTOR_API_PMI.UPDATE_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
//                            if (data.getString("RESPONSE_CODE").equals("200")) {
//                            	responseMessage.setStatus(true);
//                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
//                                responseMessage.setNaiComData(objectMapper.readValue(data.get("NAICOM_DATA").toString(),Object.class));
//                                responseMessage.setNiidData(objectMapper.readValue(data.get("NIID_DATA").toString(),Object.class));
//                                responseMessage.setKycStatus(data.getInt("KYC_STATUS"));
//                                responseMessage.setNaiComStatus(data.getInt("NAICOM_STATUS"));
//                                responseMessage.setNiidStatus(data.getInt("NIID_STATUS"));
//                                
//                            }
//                            else {
//                            	responseMessage.setData(data.getString("DATA"));
//                            	responseMessage.setKycStatus(data.getInt("KYC_STATUS"));
//                                responseMessage.setNaiComStatus(data.getInt("NAICOM_STATUS"));
//                                responseMessage.setNiidStatus(data.getInt("NIID_STATUS"));
//                            }
//                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
//                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                });
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
//        return responseMessage;
//    }

	public SchedulerResposeDto insertRequestSchedulerPolicy(String token, SchedulerPolicyDto schedulerObj,SchedulerResposeDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_POLICY_FOR_MOTOR_REQUEST]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, schedulerObj.getQuotationId());
                        callst.setDate(3, Date.valueOf(schedulerObj.getFromDate()));
                        callst.setDate(4, Date.valueOf(schedulerObj.getToDate()));
                        
                        callst.setString(5, schedulerObj.getFirstName());
                        callst.setString(6, schedulerObj.getLastName());
                        callst.setDate(7, Date.valueOf(schedulerObj.getDob()));
                        callst.setString(8, schedulerObj.getAddress());
                        callst.setString(9, schedulerObj.getIdTpye());
                        callst.setString(10, schedulerObj.getIdNumber());
                        callst.setString(11, schedulerObj.getVehicleId());
                        callst.setString(12, schedulerObj.getMake());
                        callst.setString(13, schedulerObj.getModel());
                        callst.setDate(14, Date.valueOf(schedulerObj.getRegisrtrationDate()));
                        
                        callst.setDate(15, Date.valueOf(schedulerObj.getRegisrtrationEndDate()));
                        callst.setString(16, schedulerObj.getAutoType());
                        callst.setInt(17, schedulerObj.getYearOfMake());
                        callst.setString(18, schedulerObj.getChassisNo());
                        callst.setString(19, schedulerObj.getVehicleCategory());
                        callst.setString(20, schedulerObj.getMobileNumber());
                        callst.setInt(21, 0);
                        callst.setString(22, "");
                        callst.setString(23, schedulerObj.getTitle());
                        callst.setString(24, schedulerObj.getCity());
                        callst.setString(25, schedulerObj.getState());
                        callst.setString(26, schedulerObj.getCountry());
                        callst.setLong(27, 	 schedulerObj.getPolicyId());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.UPDATE_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                                responseMessage.setNaiComData(objectMapper.readValue(data.get("NAICOM_DATA").toString(),Object.class));
                                responseMessage.setNiidData(objectMapper.readValue(data.get("NIID_DATA").toString(),Object.class));
                                responseMessage.setKycStatus(data.getInt("KYC_STATUS"));
                                responseMessage.setNaiComStatus(data.getInt("NAICOM_STATUS"));
                                responseMessage.setNiidStatus(data.getInt("NIID_STATUS"));
                                
                            }
                            else {
                            	responseMessage.setData(null);
                            	responseMessage.setNiidStatus(0);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public NiidResponseDto insertSchedulerNiidRequestDao(String thirdPartyName, String baseUrl, String token, String thirdPartyData,NiidResponseDto niidResponseObj) {
		
		log.info("Save nai com insurence third party request data API Implementation:-" + thirdPartyName+"baseUrl"+baseUrl+"thirdPartyData:-"+thirdPartyData);
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, thirdPartyData);
						callst.setString(4, thirdPartyName);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR DATA_LOG.INSERT_REQUEST_LOG_THIRD_PARTY **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								niidResponseObj.setStatus(true);
								niidResponseObj.setData(data.optString("DATA"));
								niidResponseObj.setMessage(data.get("RESPONSE_MESSAGE").toString());
								niidResponseObj.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							niidResponseObj.setStatus(false);
							niidResponseObj.setMessage(data.get("RESPONSE_MESSAGE").toString());
							niidResponseObj.setResponseCode(data.get("RESPONSE_CODE").toString());
							niidResponseObj.setData(null);
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
		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
		return niidResponseObj;
	}

	public NiidStatusResponseDto checkNiidStatus(String token, long policyId, NiidStatusResponseDto niidStatus) {
		
		log.info("Save nai com insurence third party request data API Implementation:-" );
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_NAICOM_MOTOR_DATA]" + "(?,?)}");
						callst.setString(1, token);
						callst.setLong(2, policyId);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_NAICOM_MOTOR_DATA **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								niidStatus.setStatus(true);
								niidStatus.setNaiComData(objectMapper.readValue(data.get("NAICOM_DATA").toString(),Object.class));
                                niidStatus.setNaiComStatus(data.getInt("NAICOM_STATUS"));
                                niidStatus.setNiidStatus(data.getInt("NIID_STATUS"));
                                
                            }else {
							niidStatus.setStatus(false);
							niidStatus.setMessage(data.get("RESPONSE_MESSAGE").toString());
							niidStatus.setResponseCode(data.get("RESPONSE_CODE").toString());
							niidStatus.setData(null);
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
		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
		return niidStatus;
	}

	public ResponseDto cityStateDao(String token, String serviceType, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    CallableStatement callst = null;
                    try {
						callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_CITY_STATE_DETAILS]" + "(?,?)}");
                    	callst.setString(1, token);
						callst.setString(2, serviceType);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_CITY_STATE_DETAILS **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto getTitleDao(ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    CallableStatement callst = null;
                    try {
						callst = connection.prepareCall("{call " + "[MST].[GET_TITLE_LIST]" + "}");
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MST.GET_TITLE_LIST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public NaiComDBResposeDto getNaiComResponse(NaiComDBResposeDto naiDbResponse, String token, long policyId) {
		
		log.info("get nai com insurence third party request data API Implementation:-" );
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[CBA_API].[GET_POLICY_NAICOM_NIID_STATUS]" + "(?,?)}");
						callst.setString(1, token);
						callst.setLong(2, policyId);
						
						
						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject  data = null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_NAICOM_MOTOR_DATA **********"+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								naiDbResponse.setStatus(true);
								naiDbResponse.setNaiComData(objectMapper.readValue(data.get("NAICOM_DATA").toString(),Object.class));
								naiDbResponse.setNaiComStatus(data.getInt("NAICOM_STATUS"));
								naiDbResponse.setNiidStatus(data.getInt("NIID_STATUS"));
                                
                            }else {
                            	naiDbResponse.setStatus(false);
                            	naiDbResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            	naiDbResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
                            	naiDbResponse.setData(null);
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
		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
		return naiDbResponse;
	}

	public NaiComResposeDto insertRequestGeneratefordashboarPolicy(String token, InsertPolicyDto insertobj,
			NaiComResposeDto naicomRespo) {
		log.info(" *************** insertRequestGeneratePolicy method is completed ********************"+insertobj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR_REQUEST_FOR_DASHBOARD]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, 0);
                        callst.setString(22, "");
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	naicomRespo.setStatus(true);
                            	naicomRespo.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            	naicomRespo.setKycStatus(data.getInt("KYC_STATUS"));
                            }
                            else {
                            	naicomRespo.setData(null);
                            	naicomRespo.setNiidStatus(0);
                            }
                            naicomRespo.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            naicomRespo.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return naicomRespo;
    }

	public ResponseDto endorsementPolicyTr(String token, EndorsementPolicyDto endorsementobj,ResponseDto responseMessage) {
//		log.info(" *************** insertRequestGeneratePolicy method is completed ********************"+insertobj.toString());
//        try (Session session = entityManager.unwrap(Session.class)) {
//
//            try {
//                session.doWork(connection -> {
//                    ResultSet resultSet = null;
//                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR_ENDORSEMENT]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
//                        callst.setString(1, token);
//                        callst.setLong(2, insertobj.getQuotationId());
//                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
//                        callst.setDate(4, Date.valueOf(insertobj.getToDate()));
//                        
//                        callst.setString(5, insertobj.getFirstName());
//                        callst.setString(6, insertobj.getLastName());
//                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
//                        callst.setString(8, insertobj.getAddress());
//                        callst.setString(9, insertobj.getIdType());
//                        callst.setString(10, insertobj.getIdNumber());
//                        callst.setString(11, insertobj.getVehicleId());
//                        callst.setString(12, insertobj.getMake());
//                        callst.setString(13, insertobj.getModel());
//                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
//                        
//                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
//                        callst.setString(16, insertobj.getAutoType());
//                        callst.setInt(17, insertobj.getYearOfMake());
//                        callst.setString(18, insertobj.getChassisNo());
//                        callst.setString(19, insertobj.getVehicleCategory());
//                        callst.setString(20, insertobj.getMobileNumber());
//                        callst.setInt(21, 0);
//                        callst.setString(22, "");
//                        callst.setString(23, insertobj.getTitle());
//                        callst.setString(24, insertobj.getCity());
//                        callst.setString(25, insertobj.getState());
//                        callst.setString(26, insertobj.getCountry());
//                        callst.setString(27, insertobj.getMiddleName());
//                        callst.setString(28, insertobj.getGender());
//                        callst.setString(29, insertobj.getEmailId());
//
//                        callst.execute();
//
//                        resultSet = callst.getResultSet();
//                        while (resultSet.next()) {
//                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
//                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
//                            if (data.getString("RESPONSE_CODE").equals("200")) {
//                            	naicomRespo.setStatus(true);
//                            	naicomRespo.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
//                            	naicomRespo.setKycStatus(data.getInt("KYC_STATUS"));
//                            }
//                            else {
//                            	naicomRespo.setData(data.getString("DATA"));
//                            	naicomRespo.setNiidStatus(0);
//                            }
//                            naicomRespo.setMessage(data.get("RESPONSE_MESSAGE").toString());
//                            naicomRespo.setResponseCode(data.get("RESPONSE_CODE").toString());
//                        }
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                });
//            } catch (Exception e1) {
//                e1.printStackTrace();
//            }
//        }
        return responseMessage;
    }

	public KycResponseDto insertInsureDetails(String token, InsertPolicyDto insertobj, KycResponseDto kycResponse) {

		log.info(" INSERT INSURE DETAILS IN DATABASE :- "+insertobj.toString());
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[CBA_API].[VALIDATE_INSURED_DETAILS]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, 0);
                        callst.setString(22, "");
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());

						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject data = null;
						while (resultSet.next()) {
							String deleteBene = resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR CBA_API.VALIDATE_INSURED_DETAILS **********"+ deleteBene);
							String bulkInsert = resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if (data.getString("RESPONSE_CODE").equals("200")) {
								kycResponse.setStatus(true);
								kycResponse.setData(objectMapper.readValue(data.get("DATA").toString(), Object.class));
								kycResponse.setKycStatus(data.getInt("KYC_STATUS"));
							} else {
								kycResponse.setStatus(false);
								kycResponse.setData(null);
								kycResponse.setKycStatus(101);
							}
							kycResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
							kycResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
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
		log.info("EXIT FROM DB LAYER METHOD IS:-insertInsureDetails");
		return kycResponse;
	}

	public PolicyResponseDto insertAllRequest(String token, InsertPolicyDto insertobj, int kycStatus, String kycMessage,PolicyResponseDto policyResponse) {
		log.info(" *************** insertRequestGeneratePolicy method is completed ********************"+insertobj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR_REQUEST_HIMANSHU_26092023]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, kycStatus);
                        callst.setString(22, kycMessage);
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());
                        
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	policyResponse.setStatus(true);
                            	policyResponse.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            	policyResponse.setKycStatus(data.getInt("KYC_STATUS"));
                            	policyResponse.setNiidData(objectMapper.readValue(data.get("NIID_DATA").toString(),Object.class));
                            }
                            else {
                            	policyResponse.setData(null);
                            }
                            policyResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            policyResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return policyResponse;
    }

//	public ResponseDto insertNiidRequestDataDao(String thirdPartyName, String baseUrl, String token, String thirdPartyData,ResponseDto responseMessage) {
//		
//		log.info("Save nai com insurence third party request data API Implementation:-" + thirdPartyName+"baseUrl"+baseUrl+"thirdPartyData:-"+thirdPartyData);
//		Session session = entityManager.unwrap(Session.class);
//
//		try {
//			session.doWork(new Work() {
//				@Override
//				public void execute(Connection connection) throws SQLException {
//					CallableStatement callst = null;
//					ResultSet resultSet = null;
//					try {
//						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
//						callst.setString(1, token);
//						callst.setString(2, baseUrl);
//						callst.setString(3, thirdPartyData);
//						callst.setString(4, thirdPartyName);
//						
//						
//						callst.execute();
//						resultSet = callst.getResultSet();
//						JSONObject  data = null;
//						while (resultSet.next()) {
//							String deleteBene=resultSet.getObject("Data").toString();
//							log.info("7777777777-- GET DB RESPONSE FOR  DATA_LOG.INSERT_REQUEST_LOG_THIRD_PARTY **********"+deleteBene);
//							String bulkInsert=resultSet.getObject("Data").toString();
//							data = new JSONObject(bulkInsert.toString());
//							if(data.getString("RESPONSE_CODE").equals("200")) {
//								responseMessage.setStatus(true);
//								responseMessage.setData(data.optString("DATA"));
//								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
//								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
//						}else {
//							responseMessage.setStatus(false);
//							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
//							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
//							responseMessage.setData(null);
//						}
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						if (callst != null) {
//							callst.close();
//						}
//					}
//
//				}
//
//			});
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		log.info("exiting from db layer insertThirdPartyRequestDao API IMPLEMENTATION motor");
//		return responseMessage;
//	}
	
	public ResponseDto insertNiidRequestDataDao(String thirdPartyName, String baseUrl, String token,String thirdPartyData, ResponseDto responseMessage) {
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_REQUEST_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, thirdPartyData);
						callst.setString(4, thirdPartyName);

						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject data = null;
						while (resultSet.next()) {
							String deleteBene = resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR CBA_API.VALIDATE_INSURED_DETAILS **********"+ deleteBene);
							String bulkInsert = resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if (data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								responseMessage.setData(data.optString("DATA"));
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							} else {
								responseMessage.setStatus(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
								responseMessage.setData(null);
							}
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
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
		log.info("EXIT FROM DB LAYER METHOD IS:-insertInsureDetails");
		return responseMessage;
	}
	public ResponseDto insertThirdPartyResponseDataDao(String thirdPartyName, String baseUrl, String token, String finalResponseBody, ResponseDto responseMessage) {

		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setString(2, baseUrl);
						callst.setString(3, finalResponseBody);
						callst.setString(4, thirdPartyName);

						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject data = null;
						while (resultSet.next()) {
							String deleteBene = resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR CBA_API.VALIDATE_INSURED_DETAILS **********"+ deleteBene);
							String bulkInsert = resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if (data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								responseMessage.setData(data.optString("DATA"));
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
							} else {
								responseMessage.setStatus(false);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
								responseMessage.setData(null);
							}
							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
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
		log.info("EXIT FROM DB LAYER METHOD IS:-insertInsureDetails");
		return responseMessage;
	}
	
//	public ResponseDto insertThirdPartyResponseDataDao(String thirdPartyName, String baseUrl, String token, String finalResponseBody, ResponseDto responseMessage) {
//		log.info("save third party response data :-" + thirdPartyName+"baseUrl"+baseUrl+"token:-"+token);
//		Session session = entityManager.unwrap(Session.class);
//
//		try {
//			session.doWork(new Work() {
//				@Override
//				public void execute(Connection connection) throws SQLException {
//					CallableStatement callst = null;
//					ResultSet resultSet = null;
//					try {
//						callst = connection.prepareCall("{call " + "[DATA_LOG].[INSERT_RESPONSE_LOG_THIRD_PARTY]" + "(?,?,?,?)}");
//						callst.setString(1, token);
//						callst.setString(2, baseUrl);
//						callst.setString(3, finalResponseBody);
//						callst.setString(4, thirdPartyName);
//						
//						
//						callst.execute();
//						resultSet = callst.getResultSet();
//						JSONObject  data = null;
//						while (resultSet.next()) {
//							String deleteBene=resultSet.getObject("Data").toString();
//							log.info("7777777777-- GET DB RESPONSE FOR DATA_LOG.INSERT_RESPONSE_LOG_THIRD_PARTY **********"+deleteBene);
//							String bulkInsert=resultSet.getObject("Data").toString();
//							data = new JSONObject(bulkInsert.toString());
//							if(data.getString("RESPONSE_CODE").equals("200")) {
//								responseMessage.setStatus(true);
//								responseMessage.setData(data.optString("DATA"));
//								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
//								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
//						}else {
//							responseMessage.setStatus(false);
//							responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
//							responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
//							responseMessage.setData(null);
//						}
//						}
//
//					} catch (Exception e) {
//						e.printStackTrace();
//					} finally {
//						if (callst != null) {
//							callst.close();
//						}
//					}
//
//				}
//
//			});
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
//		log.info("exiting from db layer delete provicsion  motor");
//		return responseMessage;
//	}

	public ResponseDto saveCreatePolicyNiidDataResponse(String token, long policyId, String niidResMesg,
			String niidResCode, ResponseDto responseMessage) {
		log.info("Request is inside db layer for get saveCreatePolicyNiidResponse Dao");
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_NIID_MOTOR_UPLOAD_RESPONSE]" + "(?,?,?,?)}");
						callst.setString(1, token);
						callst.setLong(2, policyId);
						callst.setString(3, niidResCode);
						callst.setString(4, niidResMesg);
						callst.execute();

						resultSet = callst.getResultSet();
						JSONObject data=null;
						while (resultSet.next()) {
							String deleteBene=resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.UPDATE_NIID_MOTOR_UPLOAD_RESPONSE ********** "+deleteBene);
							String bulkInsert=resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if(data.getString("RESPONSE_CODE").equals("200")) {
								responseMessage.setStatus(true);
								ObjectMapper mapper=new ObjectMapper();
								String queryRemitterRequest=data.optString("DATA");
								Object mapperObj=mapper.readValue(queryRemitterRequest, Object.class);
								responseMessage.setData(mapperObj);
								responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
								responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
						}else {
							responseMessage.setStatus(false);
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
		log.info("exiting from db layer get niid request Dao");
		return responseMessage;
	}

	public ResponseDto getInsuredDetails(Long quotationId, String token, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CBA_API].[GET_VALIDATE_INSURED_DETAILS]" + "(?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, quotationId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_VALIDATE_INSURED_DETAILS **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public PolicyResponseDto updateAllRequest(String token, UpdatePolicyDto updateObj, int i, String string,
			PolicyResponseDto policyResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	public UpdateNiidRequestDto insertRequestUpdatePolicyMotor(String token, UpdatePolicyDto updateObj,UpdateNiidRequestDto niidRequestRes) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[UPDATE_POLICY_FOR_MOTOR_REQUEST_HIMANSHU_26092023]" + "(?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, updateObj.getPolicyId());
                        callst.setString(3, updateObj.getVehicleId());
                        callst.setString(4, updateObj.getMake());
                        callst.setString(5, updateObj.getModel());
                        callst.setDate(6, Date.valueOf(updateObj.getRegistrationDate()));
                        callst.setDate(7, Date.valueOf(updateObj.getRegistrationEndDate()));
                        callst.setString(8, updateObj.getAutoType());
                        callst.setInt(9, updateObj.getYearOfMake());
                        callst.setString(10, updateObj.getChassisNo());
                        callst.setString(11, updateObj.getVehicleCategory());
                        callst.execute();
                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  MOTOR_API_PMI.UPDATE_POLICY_FOR_MOTOR_REQUEST_HIMANSHU_26092023 **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	niidRequestRes.setStatus(true);
                            	niidRequestRes.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            	niidRequestRes.setNiidStatus(data.getInt("NIID_STATUS"));
                            	niidRequestRes.setNiidData(objectMapper.readValue(data.get("NIID_DATA").toString(),Object.class));
                            	niidRequestRes.setNaiStatus(data.getInt("NAICOM_STATUS"));
                            	niidRequestRes.setNaiData(objectMapper.readValue(data.get("NAICOM_DATA").toString(),Object.class));
                            }
                            else {
                            	niidRequestRes.setData(null);
                            	niidRequestRes.setNiidStatus(data.getInt("NIID_STATUS"));
                            }
                            niidRequestRes.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            niidRequestRes.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return niidRequestRes;
    }

	public KycResponseDto insertDashInsureDetails(String token, InsertDashPolicyTrDto insertobj,KycResponseDto kycResponse) {

		log.info(" INSERT INSURE DETAILS IN DATABASE :- "+insertobj.toString());
		Session session = entityManager.unwrap(Session.class);

		try {
			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					CallableStatement callst = null;
					ResultSet resultSet = null;
					try {
						callst = connection.prepareCall("{call " + "[CBA_API].[VALIDATE_INSURED_DETAILS]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
						callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, 0);
                        callst.setString(22, "");
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());

						callst.execute();
						resultSet = callst.getResultSet();
						JSONObject data = null;
						while (resultSet.next()) {
							String deleteBene = resultSet.getObject("Data").toString();
							log.info("7777777777-- GET DB RESPONSE FOR CBA_API.VALIDATE_INSURED_DETAILS **********"+ deleteBene);
							String bulkInsert = resultSet.getObject("Data").toString();
							data = new JSONObject(bulkInsert.toString());
							if (data.getString("RESPONSE_CODE").equals("200")) {
								kycResponse.setStatus(true);
								kycResponse.setData(objectMapper.readValue(data.get("DATA").toString(), Object.class));
								kycResponse.setKycStatus(data.getInt("KYC_STATUS"));
							} else {
								kycResponse.setStatus(false);
								kycResponse.setData(null);
								kycResponse.setKycStatus(101);
							}
							kycResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
							kycResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
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
		log.info("EXIT FROM DB LAYER METHOD IS:-insertInsureDetails");
		return kycResponse;
	}

	public PolicyResponseDto insertDashAllRequest(String token, InsertDashPolicyTrDto insertobj, int kycStatus, String kycMessage,PolicyResponseDto policyResponse) {
		log.info(" *************** insertRequestGeneratePolicy method is completed ********************"+insertobj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[INSERT_POLICY_FOR_MOTOR_REQUEST_HIMANSHU_26092023]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insertobj.getQuotationId());
                        callst.setDate(3, Date.valueOf(insertobj.getFromDate()));
                        callst.setDate(4, null);
                        
                        callst.setString(5, insertobj.getFirstName());
                        callst.setString(6, insertobj.getLastName());
                        callst.setDate(7, Date.valueOf(insertobj.getDob()));
                        callst.setString(8, insertobj.getAddress());
                        callst.setString(9, insertobj.getIdType());
                        callst.setString(10, insertobj.getIdNumber());
                        callst.setString(11, insertobj.getVehicleId());
                        callst.setString(12, insertobj.getMake());
                        callst.setString(13, insertobj.getModel());
                        callst.setDate(14, Date.valueOf(insertobj.getRegistrationDate()));
                        
                        callst.setDate(15, Date.valueOf(insertobj.getRegistrationEndDate()));
                        callst.setString(16, insertobj.getAutoType());
                        callst.setInt(17, insertobj.getYearOfMake());
                        callst.setString(18, insertobj.getChassisNo());
                        callst.setString(19, insertobj.getVehicleCategory());
                        callst.setString(20, insertobj.getMobileNumber());
                        callst.setInt(21, kycStatus);
                        callst.setString(22, kycMessage);
                        callst.setString(23, insertobj.getTitle());
                        callst.setString(24, insertobj.getCity());
                        callst.setString(25, insertobj.getState());
                        callst.setString(26, insertobj.getCountry());
                        callst.setString(27, insertobj.getMiddleName());
                        callst.setString(28, insertobj.getGender());
                        callst.setString(29, insertobj.getEmailId());
                        
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.INSERT_POLICY_FOR_MOTOR_REQUEST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	policyResponse.setStatus(true);
                            	policyResponse.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            	policyResponse.setKycStatus(data.getInt("KYC_STATUS"));
//                            	policyResponse.setNiidStatus(data.getInt("NIID_STATUS"));
                            	policyResponse.setNiidData(objectMapper.readValue(data.get("NIID_DATA").toString(),Object.class));
                            }
                            else {
                            	policyResponse.setData(null);
//                            	kycResponse.setNiidStatus(0);
                            }
                            policyResponse.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            policyResponse.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return policyResponse;
    }

	public ResponseDto getplanPremium(long lobId, long productId, long planId, BigDecimal amount, String token,
			ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_PLAN_API_PREMIUM_RATE]" + "(?,?,?,?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, lobId);
                        callst.setLong(3, productId);
                        callst.setBigDecimal(4, amount);
                        callst.setLong(5, planId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR MOTOR_API_PMI.GET_ALL_PLAN_API_PREMIUM_RATE **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }

	public ResponseDto getRenewalList(String token, RenewalDto renewalobj, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[MOTOR_API_PMI].[GET_ALL_RENEWAL_POLICY_LIST_API]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, renewalobj.getFromDate());
                        callst.setString(3, renewalobj.getToDate());
                        callst.setInt(4, renewalobj.getPageCount());
                        callst.setInt(5, renewalobj.getPageNumber());
                        callst.setBoolean(6, renewalobj.isSearchKey());
                        callst.setString(7, renewalobj.getColumnName());
                        callst.setString(8, renewalobj.getColumnValue());
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  MOTOR_API_PMI.GET_ALL_RENEWAL_POLICY_LIST_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseMessage.setStatus(true);
                                responseMessage.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseMessage.setData(null);
                            }
                            responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseMessage;
    }


}
