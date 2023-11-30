package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.CreditLifeExcelDto;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.PerAccidentExcelDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.utility.UtilityFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class ExcelUploadDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseDto uploadFileQuotation(List<CreditLifeExcelDto> bulkFileModel, String token, String sessionId, boolean b2bBulk, ResponseDto responseDto) {
    	log.info("&&&&&&&&&&&&&&&& CREDIT LIFE BULK UPLOAD DATA &&&&&&&&&&& token"+token +"   sessionId"+sessionId);
        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(bulkFileModel).getAsJsonArray();

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[CREDIT_LIFE].[INSERT_CLIENT_BULK_POLICY]" + "(?,?,?,?)}")) {
                    	if(token == null  ||  "".equalsIgnoreCase(token)) {
                    		callableStatement.setString(1, sessionId);
                    	}
                    	if(sessionId == null  ||  "".equalsIgnoreCase(sessionId)) {
                    		callableStatement.setString(1, token);
                    	}
                        String batchId ="TBK"+ UtilityFunctions.getAlphaNumericString(4) + UtilityFunctions.getTimeStamp();
                        callableStatement.setString(2, batchId);
                        callableStatement.setString(3, String.valueOf(myCustomArray));
                        callableStatement.setBoolean(4, b2bBulk);

                        log.info("&&&&&&&&&&&&&&&& CREDIT LIFE BULK UPLOAD DATA &&&&&&&&&&&"+myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("&&&&&&&&&&&&&&&& GET DATA FROM DB SIDE  CREDIT LIFE&&&&&&&&&&&"+data.toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(null);
                                
                            }else if (data.getString("RESPONSE_CODE").equals("201")) {
                                responseDto.setStatus(false);
                                responseDto.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            }
                            else {
                            	responseDto.setData(data.get("DATA").toString());
                            }
                            responseDto.setMessage(data.getString("RESPONSE_MESSAGE"));
                            responseDto.setResponseCode(data.getString("RESPONSE_CODE"));
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }

        return responseDto;
    }

	public ResponseDto uploadFilePersonalAccident(List<PerAccidentExcelDto> bulkFileModel, String token, String sessionId,boolean b2bBulk, ResponseDto responseDto) {
		log.info("&&&&&&&&&&&&&&&& PERSONAL ACCIDENT BULK UPLOAD DATA &&&&&&&&&&& token"+token +"   sessionId"+sessionId);
        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(bulkFileModel).getAsJsonArray();

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[GENERAL_ACCIDENT_PAI_API].[INSERT_BULK_QUOTATION_REQUEST]" + "(?,?,?,?)}")) {
                    	if(token == null  ||  "".equalsIgnoreCase(token)) {
                    		callableStatement.setString(1, sessionId);
                    	}
                    	if(sessionId == null  ||  "".equalsIgnoreCase(sessionId)) {
                    		callableStatement.setString(1, token);
                    	}
                        String batchId ="TBK"+ UtilityFunctions.getAlphaNumericString(4) + UtilityFunctions.getTimeStamp();
                        callableStatement.setString(2, batchId);
                        callableStatement.setString(3, String.valueOf(myCustomArray));
                        callableStatement.setBoolean(4, b2bBulk);

                        log.info("&&&&&&&&&&&&&&&& PERSONAL ACCIDENT BULK UPLOAD DATA &&&&&&&&&&&"+myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("&&&&&&&&&&&&&&&& GET DATA FROM DB SIDE PERSONAL ACCIDENT &&&&&&&&&&&"+data.toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(null);
                                
                            }else if (data.getString("RESPONSE_CODE").equals("201")) {
                                responseDto.setStatus(false);
                                responseDto.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class));
                            }
                            else {
                            	responseDto.setData(data.get("DATA").toString());
                            }
                            responseDto.setMessage(data.getString("RESPONSE_MESSAGE"));
                            responseDto.setResponseCode(data.getString("RESPONSE_CODE"));
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }

        return responseDto;
    }

	public ResponseDto getCreditBatchList(IssuedPolicyDto fatchDto, String token, String sessionId, boolean b2bList,ResponseDto responseDto) {
		log.info("&&&&&&&&&&&&&&&& CREDIT LIFE GET BATCH LIST &&&&&&&&&&& token"+token +"   sessionId"+sessionId+ "Dto Data"+fatchDto.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CREDIT_LIFE].[GET_POLICY_BATCH_LIST]" + "(?,?,?,?,?,?,?,?,?)}")) {
                    	if(token == null  ||  "".equalsIgnoreCase(token)) {
                    		callst.setString(1, sessionId);
                    	}
                    	if(sessionId == null  ||  "".equalsIgnoreCase(sessionId)) {
                    		callst.setString(1, token);
                    	}
                        callst.setString(2, fatchDto.getFromDate());
                        callst.setString(3, fatchDto.getToDate());
                        callst.setInt(4, fatchDto.getPageCount());
                        callst.setInt(5, fatchDto.getPageNumber());
                        callst.setBoolean(6, fatchDto.isSearchKey());
                        callst.setString(7, fatchDto.getColumnName());
                        callst.setString(8, fatchDto.getColumnValue());
                        callst.setBoolean(9, b2bList);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  CREDIT_LIFE.GET_POLICY_BATCH_LIST **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseDto.setData(null);
                            }
                            responseDto.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseDto.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseDto;
    }

	public ResponseDto getBatchPolicyList(String batchId, String token, String sessionId, boolean b2bList,IssuedPolicyDto issueDto, ResponseDto responseDto) {
		log.info("&&&&&&&&&&&&&&&& CREDIT LIFE GET POLICY BATCH LIST &&&&&&&&&&& token"+token +"   sessionId"+sessionId+ "Dto Data"+batchId);
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CREDIT_LIFE].[GET_BULK_CREDIT_LIFE_DETAILS_BASED_ON BATCH_NUMBER]" + "(?,?,?,?,?,?,?,?,?,?)}")) {
                    	if(token == null  ||  "".equalsIgnoreCase(token)) {
                    		callst.setString(1, sessionId);
                    	}
                    	if(sessionId == null  ||  "".equalsIgnoreCase(sessionId)) {
                    		callst.setString(1, token);
                    	}
                        callst.setString(2, batchId);
                        callst.setBoolean(3, b2bList);
                        
                        callst.setString(4, issueDto.getFromDate());
                        callst.setString(5, issueDto.getToDate());
                        callst.setInt(6, issueDto.getPageCount());
                        callst.setInt(7, issueDto.getPageNumber());
                        callst.setBoolean(8, issueDto.isSearchKey());
                        callst.setString(9, issueDto.getColumnName());
                        callst.setString(10, issueDto.getColumnValue());
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- GET DB RESPONSE FOR  CREDIT_LIFE.GET_BULK_CREDIT_LIFE_DETAILS_BASED_ON BATCH_NUMBER **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(objectMapper.readValue(data.get("DATA").toString(),Object.class))   ;
                            }
                            else {
                            	responseDto.setData(null);
                            }
                            responseDto.setMessage(data.get("RESPONSE_MESSAGE").toString());
                            responseDto.setResponseCode(data.get("RESPONSE_CODE").toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return responseDto;
    }

	
}
