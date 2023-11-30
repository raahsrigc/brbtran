package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.models.BulkFileModel;
import com.erp.api.utility.UtilityFunctions;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class FilesDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseDto uploadFile(List<BulkFileModel> bulkFileModels, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(bulkFileModels).getAsJsonArray();

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[INSERT_BULK_DEVICE_INSURANCE_POLICY]" + "(?,?,?)}")) {
                        callableStatement.setString(1, token);
                        String batchId ="TBK"+ UtilityFunctions.getAlphaNumericString(4) + UtilityFunctions.getTimeStamp();
                        callableStatement.setString(2, batchId);
                        callableStatement.setString(3, String.valueOf(myCustomArray));

                        System.out.println(myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                JSONObject object = new JSONObject(data.get("DATA").toString());
                                responseDto.setData(object.getString("INSURER_DETAILS"));
                            }
                            else {
                                responseDto.setData(data.getString("DATA"));
                            }


                            responseDto.setMessage(data.getString("RESPONSE_MESSAGE"));
                            responseDto.setResponseCode(data.getString("RESPONSE_CODE"));

//                            if(!Objects.isNull(data.getString("DATA")))
//                            {
//                                Object data1 = objectMapper.readValue(data.getString("DATA"),Object.class);
//                                responseDto.setData(data1);
//                            }


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

	public ResponseDto uploadFileQuotation(List<BulkFileModel> bulkFileModel, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(bulkFileModel).getAsJsonArray();

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_NEW_API].[INSERT_BULK_DEVICE_INSURANCE_POLICY]" + "(?,?,?)}")) {
                        callableStatement.setString(1, token);
                        String batchId ="TBK"+ UtilityFunctions.getAlphaNumericString(4) + UtilityFunctions.getTimeStamp();
                        callableStatement.setString(2, batchId);
                        callableStatement.setString(3, String.valueOf(myCustomArray));

                        System.out.println(myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                JSONObject object = new JSONObject(data.get("DATA").toString());
                                responseDto.setData(object.toString());
                                
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

	public ResponseDto makePaymentDetailsTr(String token, long batchId, ResponseDto responseDto) {
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

	public NaiComDBResposeDto getKycStatus(String token, NaiComDBResposeDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[VALIDATE_AGENT_KYC_STATUS]" + "(?)}")) {
                    	callst.setString(1, token);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("7777777777-- DEVICE_NEW_API.VALIDATE_AGENT_KYC_STATUS **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(null);
                            	responseDto.setKycStatus(data.getInt("IS_KYC_STATUS"));
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
