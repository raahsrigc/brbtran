package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.BatchTrDto;
import com.erp.api.dto.FetchPolicyTrDto;
import com.erp.api.dto.FetchQuotationTrDto;
import com.erp.api.dto.InsertHistoryTrDto;
import com.erp.api.dto.InsertQuotationTrDto;
import com.erp.api.dto.QuotSearchFilterDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SaveDevicePolicyTrDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DeviceDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ObjectMapper mapper;
    
    // 1
    public ResponseDto getSummaryDataDao(String token, ResponseDto responseMessage) {
        log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR SUMMARY DATA TOKEN :-" + token);
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_DEVICE_INSURANCE_POLICY_SUMMARY]" + "(?)}")) {
                        callst.setString(1, token);

                        callst.execute();
                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {

                            JSONArray dataArray = new JSONArray(resultSet.getString("Data"));
                            JSONObject data = dataArray.getJSONObject(0);

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseMessage.setStatus(true);

                                String queryRemitterRequest = data.optString("DATA");
                                Object mapperObj = mapper.readValue(queryRemitterRequest, Object.class);
                                responseMessage.setData(mapperObj);

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
        log.info("exiting from db layer get summary data policy method");
        return responseMessage;
    }
    
    // 2
    public ResponseDto insertQuotationDao(InsertQuotationTrDto insertQObj, String token, ResponseDto responseMessage) {
        log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR INSERT QUOTATION:-" + insertQObj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[INSERT_QUOTATION_REQUEST_DEVICE_INSURANCE]" + "(?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, insertQObj.getDeviceSerialNumber());
                        callst.setString(3, insertQObj.getDeviceValue());
                        callst.setString(4, insertQObj.getMobileNo());
                        callst.setString(5, insertQObj.getEmail());

                        callst.execute();
                        resultSet = callst.getResultSet();

                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("Data"));

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseMessage.setStatus(true);
                                String queryRemitterRequest = data.optString("DATA");
                                Object mapperObj = mapper.readValue(queryRemitterRequest, Object.class);
                                responseMessage.setData(mapperObj);

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
        log.info("exiting from db layer login method");
        return responseMessage;
    }

    // 3
	public ResponseDto deviceQuotationListDao(FetchQuotationTrDto insertQOb, String token, ResponseDto responseDto) {
		log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR QUOTATION LIST :-" + insertQOb.toString());
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[GET_PENDING_QUOT_LIST_FOR_DEVICE_INSURANCE]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, insertQOb.getFromDate());
                        callst.setString(3, insertQOb.getToDate());
                        callst.setInt(4, insertQOb.getPageCount());
                        callst.setInt(5, insertQOb.getPageNumber());
                        Boolean b1=Boolean.valueOf(insertQOb.getSearch());
                        callst.setBoolean(6, b1);
                        callst.setString(7, insertQOb.getColumnName());
                        callst.setString(8, insertQOb.getColumnValue());

                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            String dataArray = resultSet.getObject("Data").toString();

                            JSONArray array = new JSONArray(dataArray);
                            JSONObject data = array.getJSONObject(0);

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);

                                String queryRemitterRequest = data.optString("DATA");
                                Object mapperObj = mapper.readValue(queryRemitterRequest, Object.class);
                                responseDto.setData(mapperObj);

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
        log.info("exiting from db layer get quotation marine list Quotation");
        return responseDto;
    }
    
    // 4
    public ResponseDto getByPolicyId( String token,Long policyId,ResponseDto responseDto) {
    	log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR GET POLICY BY ID POLICYID:-" + policyId);
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_DEVICE_INSURANCE_DETAILS_BY_ID]" + "(?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, policyId);


                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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

    // 5
    public ResponseDto getPolicyBulkFilesData(String batchNo, int isPolicy, String token, ResponseDto responseDto) {
    	log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR GET POLICY BULK FILE DATA BATCHNO:-" + batchNo);
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_BULK_DEVICE_DETAILS_BASED_ON BATCH_NUMBER]" + "(?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, batchNo);
                        callst.setInt(3, isPolicy);

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                ArrayList<Object> list = mapper.readValue(jsonObject.getString("DATA"),
                                        new TypeReference<ArrayList<Object>>() {
                                        });
                                responseDto.setData(list);
                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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
    
    public ResponseDto getKycDetailById(int insuredId, String token, ResponseDto responseDto) {
    	log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR GET KYC DETAILS BY ID INSUREDID:-" + insuredId);
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_INSURED_KYC_DETAILS_BY_ID]" + "(?,?)}")) {
                        callst.setString(1, token);
                        callst.setInt(2, insuredId);


                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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
    
    public ResponseDto devicePolicyListDao(FetchPolicyTrDto polObj, String token, ResponseDto responseDto) {
    	log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR POLICY LIST");
		try (Session session = entityManager.unwrap(Session.class)) {

			try {
				session.doWork(connection -> {
					ResultSet resultSet = null;
					try (CallableStatement callst = connection.prepareCall(
							"{call " + "[DEVICE_INSURANCE_API].[GET_ISSUED_POLICY_LIST_FOR_DEVICE_INSURANCE]"+ "(?,?,?,?,?,?,?,?)}")) {
						
						callst.setString(1, token);
                        callst.setString(2, polObj.getFromDate());
                        callst.setString(3, polObj.getToDate());
                        callst.setInt(4, polObj.getPageCount());
                        callst.setInt(5, polObj.getPageNumber());
                        Boolean b1=Boolean.valueOf(polObj.getSearch());
                        callst.setBoolean(6, b1);
                        callst.setString(7, polObj.getColumnName());
                        callst.setString(8, polObj.getColumnValue());

						callst.execute();

						resultSet = callst.getResultSet();
						while (resultSet.next()) {
							JSONArray dataArray = new JSONArray(resultSet.getString("Data"));
							JSONObject data = dataArray.getJSONObject(0);

							if (data.getString("RESPONSE_CODE").equals("200")) {
								responseDto.setStatus(true);

								String queryRemitterRequest = data.optString("DATA");
								Object mapperObj = mapper.readValue(queryRemitterRequest, Object.class);
								responseDto.setData(mapperObj);

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
    public ResponseDto savePolicyDao(SaveDevicePolicyTrDto saceDeviceObj, int idStatus, String token,String message, ResponseDto responseMessage) {
    	log.info("7777777777--B2B DEVICE--- REQUEST IS INSIDE DB LAYER FOR INSERT POLICY LIST:-"+saceDeviceObj.toString());
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[INSERT_DEVICE_INSURANCE_POLICY]" + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setInt(2, saceDeviceObj.getQuotationId());
                        callst.setString(3, saceDeviceObj.getDeviceType());
                        callst.setString(4, saceDeviceObj.getDeviceMake());
                        callst.setString(5, saceDeviceObj.getDeviceModal());
                        callst.setString(6, saceDeviceObj.getImeiNumber());
                        callst.setString(7, saceDeviceObj.getInvoiceProofUrl());
                        callst.setString(8, saceDeviceObj.getDateOfPurchase());
                        callst.setString(9, saceDeviceObj.getFirstName());
                        callst.setString(10, saceDeviceObj.getLastName());
                        callst.setString(11, saceDeviceObj.getGender());
                        callst.setString(12, saceDeviceObj.getIdNumber());
                        callst.setString(13, saceDeviceObj.getDeviceValue());
                        callst.setString(14, saceDeviceObj.getDeviceSerialNumber());
                        callst.setString(15, saceDeviceObj.getIdType());
                        callst.setString(16, saceDeviceObj.getDob());
                        if(idStatus==1) {
                            callst.setInt(17, 1);
                        }else {
                            callst.setInt(17, idStatus);
                        }
                        callst.setString(18, message);
                        callst.setString(19, saceDeviceObj.getMiddleName());
                        callst.setString(20, saceDeviceObj.getTitle());
                        callst.execute();
                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("Data"));
                            log.info("---------get all data from db side----------"+data.toString());
                          
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseMessage.setStatus(true);
                                responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                                responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                                String queryRemitterRequest = data.optString("DATA");
                                Object mapperObj = mapper.readValue(queryRemitterRequest, Object.class);
                                responseMessage.setData(mapperObj);
                            }else {
                                responseMessage.setStatus(false);
                                responseMessage.setMessage(data.get("RESPONSE_MESSAGE").toString());
                                responseMessage.setResponseCode(data.get("RESPONSE_CODE").toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        log.info("exiting from db layer insert policy method");
        return responseMessage;
    }

  




   

    public ResponseDto getBatches(BatchTrDto batchDto, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[GET_POLICY_BATCH_LIST]" + "(?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, batchDto.getFromDate());
                        callst.setString(3, batchDto.getToDate());
                        callst.setInt(4, batchDto.getPageCount());
                        callst.setInt(5, batchDto.getPageNumber());
                        Boolean search=Boolean.valueOf(batchDto.getSearch());
                        callst.setBoolean(6, search);
                        callst.setString(7, batchDto.getColumnName());
                        callst.setString(8, batchDto.getColumnValue());
                        callst.setBoolean(9,("ALL".equals(batchDto.getType())));

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);
                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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

 


	

    public ResponseDto getCommentHistory(String objectUid, String recordId, String token,ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_COMMENT_HISTORY]" + "(?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, recordId);
                        callst.setString(3, objectUid);
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);
                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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
    
    
    public ResponseDto insertHistoryDao(InsertHistoryTrDto historyObj, String sessionId, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[HISTORY].[INSERT_COMMENT_HISTORY]" + "(?,?,?,?)}")) {
                        callst.setString(1, sessionId);
                        callst.setString(2, historyObj.getId());
                        callst.setInt(3, historyObj.getObjectId());
                        callst.setString(4, historyObj.getComment());
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
//                                Object list = mapper.readValue(jsonObject.getString("DATA"),
//                                        Object.class);
                                responseDto.setData(null);
                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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


    public ResponseDto getDeviceHistory(int deviceId, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_DEVICE_HISTORY_DETAILS]" + "(?,?)}")) {
                        callst.setString(1, token);
                        callst.setInt(2, deviceId);
                        

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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


	public ResponseDto quotationByMail(String mobileOrEmail, String sessionRefNo, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[GET_ALL_QUOTATION_FOR_INSURED_FROM_MOBILE_EMAIL]" + "(?,?)}")) {
                        callst.setString(1, sessionRefNo);
                        callst.setString(2, mobileOrEmail);
                        

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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


	public ResponseDto quotationList(QuotSearchFilterDto searchObj, String sessionRefNo, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[GET_ALL_QUOTATION_LIST]" + "(?,?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, sessionRefNo);
                        callst.setString(2, searchObj.getFromDate());
                        callst.setString(3, searchObj.getToDate());
                        callst.setInt(4, searchObj.getPageCount());
                        callst.setInt(5, searchObj.getPageNumber());
                        callst.setBoolean(6, searchObj.isQuotationSearch());
                        callst.setString(7, searchObj.getColumnName());
                        callst.setString(8, searchObj.getColumnValue());
                        callst.setString(9, searchObj.getProductCode());
                        

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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

	public ResponseDto kycStatusCheck(Long insuredId, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_INSURED_KYC_DETAILS_BY_ID]" + "(?,?)}")) {
                        callst.setString(1, token);
                        callst.setLong(2, insuredId);
                        

                        callst.execute();

                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());


                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);

                                Object list = mapper.readValue(jsonObject.getString("DATA"),
                                        Object.class);
                                responseDto.setData(list);

                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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

	public ResponseDto getQuotationDById(int quotationId, String token, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_DEVICE_INSURANCE_QUOTATION_DETAILS_BY_ID]" + "(?,?)}")) {
                    	callst.setString(1, token);
                        callst.setInt(2, quotationId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("********** GET DB RESPONSE FOR MOTOR_API_PMI.GET_QUOTATION_DETAILS_BY_ID_API **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(mapper.readValue(data.get("DATA").toString(),Object.class))   ;
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

	public ResponseDto getBatchList(BatchTrDto batchDto, String token, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[GET_PENDING_BULK_QUOT_LIST_FOR_DEVICE_INSURANCE]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, batchDto.getFromDate());
                        callst.setString(3, batchDto.getToDate());
                        callst.setInt(4, batchDto.getPageCount());
                        callst.setInt(5, batchDto.getPageNumber());
                        Boolean search=Boolean.valueOf(batchDto.getSearch());
                        callst.setBoolean(6, search);
                        callst.setString(7, batchDto.getColumnName());
                        callst.setString(8, batchDto.getColumnValue());

                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {

                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                Object list = mapper.readValue(jsonObject.getString("DATA"),Object.class);
                                responseDto.setData(list);
                            }
                            responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                            responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
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

	public ResponseDto bulkQuatationByIdTr(long quotationId, String token, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[GET_DEVICE_INSURANCE_QUOTATION_DETAILS_BY_ID]" + "(?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, quotationId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("********** GET DB RESPONSE FOR DEVICE_NEW_API.GET_DEVICE_INSURANCE_QUOTATION_DETAILS_BY_ID **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(mapper.readValue(data.get("DATA").toString(),Object.class))   ;
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

	public ResponseDto getInsuredTr(long quotationId, long insuredId, int bulkKycId, String token,
			ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_NEW_API].[GET_DEVICE_INSURED_KYC_DETAILS_BY_ID]" + "(?,?,?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, quotationId);
                        callst.setLong(3, insuredId);
                        callst.setInt(4, bulkKycId);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("********** GET DB RESPONSE FOR DEVICE_NEW_API.GET_DEVICE_INSURED_KYC_DETAILS_BY_ID **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(mapper.readValue(data.get("DATA").toString(),Object.class))   ;
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

	public ResponseDto getPaymentDetailsTr(long policyId, String token, String serviceType, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[CBA_API].[GET_PAYMENT_DETAILS_BY_POLICY_ID]" + "(?,?,?)}")) {
                    	callst.setString(1, token);
                        callst.setLong(2, policyId);
                        callst.setString(3, serviceType);
                        callst.execute();

                        resultSet = callst.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));
                            log.info("********** GET DB RESPONSE FOR CBA_API.GET_PAYMENT_DETAILS_BY_POLICY_ID **********"+data.toString());
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                            	responseDto.setStatus(true);
                            	responseDto.setData(mapper.readValue(data.get("DATA").toString(),Object.class))   ;
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
