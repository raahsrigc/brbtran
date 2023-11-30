package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManager;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.InsureDetailsDto;
import com.erp.api.dto.PaymentOnlineTrDto;
import com.erp.api.dto.PaymentWalletTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.models.FlutterWaveResponseModel;
import com.erp.api.models.MakePaymentModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class MakePaymentDao {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public void getTxnRefNo(Long policyId, String sessionId,MakePaymentModel makePaymentModel) {

//        AtomicReference<MakePaymentModel> makePaymentModel = new AtomicReference<>();
//        AtomicReference<String> value = new AtomicReference<>("");
        try (Session session = entityManager.unwrap(Session.class)) {


            
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[GET_PAYMENT_REQUEST]" + "(?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setLong(2, policyId);

                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                ObjectMapper objectMapper = new ObjectMapper();
                                MakePaymentModel model1 = objectMapper.readValue(data.get("DATA").toString(), MakePaymentModel.class);
                                makePaymentModel.setTx_ref(model1.getTx_ref());
                                makePaymentModel.setAmount(model1.getAmount());
                                makePaymentModel.setCustomerEmail(model1.getCustomerEmail());
                                makePaymentModel.setCustomerName(model1.getCustomerName());
                                makePaymentModel.setCustomerPhoneNumber(model1.getCustomerPhoneNumber());


                            } else {

//                                throw new RuntimeException("EXCEPTION OCCURRED");
                            	throw new ValidationException(data.getString("RESPONSE_CODE"), data.getString("RESPONSE_MESSAGE"),null);
                            }
                        }

                    } catch (ValidationException e1) {
                        throw new ValidationException(e1.getStatusCode(),e1.getResponseMessage(),e1.getResponseData());
                    }
                     catch (Exception e) {
                        throw new RuntimeException(e);
                    }finally {}

                });
           
        }
        log.info("exiting from db layer get quotation marine list Quotation");
//        return makePaymentModel.get();
    }


    public void updatePaymentLink(Long policyId, String sessionId,String paymentLink) {

        try (Session session = entityManager.unwrap(Session.class)) {


            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[UPDATE_PAYMENT_LINK]" + "(?,?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setLong(2, policyId);
                        callableStatement.setString(3,paymentLink);

                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                           // JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());


                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
        log.info("exiting from db layer get quotation marine list Quotation");

    }

    public void updatePaymentStatus(String txnRef,Boolean isBulk, String sessionId, FlutterWaveResponseModel model, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {


            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[UPDATE_PAYMENT_STATUS]" + "(?,?,?,?,?,?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setString(2, txnRef);
                        int status;
                        String message;
                        if("success".equals(model.getStatus()))
                        {
                            if("successful".equals(model.getData().get("status")))
                            {
                                status = 1;
                            }
                            else if("failed".equals(model.getData().get("status")))
                            {
                                status = 0;
                            }
                            else {
                                status =2;
                            }
                            message = (String) model.getData().get("processor_response");
                        }
                        else
                        {
                            status =1;
                            message = model.getMessage();
                        }




                        callableStatement.setInt(3, status);
                        callableStatement.setString(4,message);
                        callableStatement.setString(5,new Gson().toJson(model));
                        callableStatement.setBoolean(6,isBulk);
                        callableStatement.setInt(7,0);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {

                                responseDto.setStatus(true);
                                responseDto.setResponseCode(data.getString("RESPONSE_CODE"));
                                responseDto.setData(data.getJSONObject("DATA"));
                                responseDto.setMessage(data.getString("RESPONSE_MESSAGE"));


                            } else {

                                responseDto.setStatus(false);
                            }
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
        log.info("exiting from db layer get quotation marine list Quotation");

    }


    public JSONObject getPaymentStatus(Boolean isBulk,String txnRef, String sessionId) {
        AtomicReference<JSONObject> jsonObject = new AtomicReference<>(new JSONObject());
        try (Session session = entityManager.unwrap(Session.class)) {


            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[GET_POLICY_PAYMENT_REQUEST_STATUS]" + "(?,?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setString(2, txnRef);
                        callableStatement.setBoolean(3,isBulk);
                        callableStatement.execute();
                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            jsonObject.set(new JSONObject(resultSet.getString("DATA")));
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });
            } catch (Exception e1) {
                throw new RuntimeException(e1);
            }
        }
        log.info("exiting from db layer get quotation marine list Quotation");

        return jsonObject.get();
    }
    //old procedure 
    public void makePaymentWithWallet(String txnRef,Boolean isBulk, String sessionId, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[UPDATE_PAYMENT_STATUS]" + "(?,?,?,?,?,?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setString(2, txnRef);
                        callableStatement.setInt(3, 1);
                        callableStatement.setString(4,"Payment processed with wallet.");
                        callableStatement.setString(5,null);
                        callableStatement.setBoolean(6,isBulk);
                        callableStatement.setInt(7,1);

                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {

                                responseDto.setStatus(true);
                                responseDto.setResponseCode(data.getString("RESPONSE_CODE"));
                                responseDto.setData(objectMapper.readValue(data.getJSONObject("DATA").toString(),Object.class));
                                responseDto.setMessage(data.getString("RESPONSE_MESSAGE"));


                            } else {

                                responseDto.setStatus(false);
                            }
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });

        }


    }


	public InsureDetailsDto paymentWallet(PaymentWalletTrDto makePaymentDto, String token, InsureDetailsDto kycStatus) {

        try (Session session = entityManager.unwrap(Session.class)) {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_NEW_API].[UPDATE_PAYMENT_STATUS]" + "(?,?,?,?,?,?,?,?)}")) {
                        callableStatement.setString(1, token);
                        callableStatement.setLong(2, makePaymentDto.getQuotationId());
                        callableStatement.setString(3, makePaymentDto.getBatchNo());
                        callableStatement.setBigDecimal(4, makePaymentDto.getAmount());
                        callableStatement.setString(5,makePaymentDto.getProfileEmail());
                        callableStatement.setBoolean(6,makePaymentDto.isWallet());
                        callableStatement.setBoolean(7,makePaymentDto.isBulk());
                        callableStatement.setString(8, "");

                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());
                            log.info("7777777777-- DEVICE_NEW_API.UPDATE_PAYMENT_STATUS **********"+data.toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {

                                kycStatus.setStatus(true);
                                kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                                kycStatus.setData(objectMapper.readValue(data.getJSONObject("DATA").toString(),Object.class));
                                kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                                kycStatus.setKycStatus(null);

                            } else if(data.getString("RESPONSE_CODE").equals("201")) {
                            	kycStatus.setStatus(true);
                                kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                                kycStatus.setKycStatus(data.getString("IS_KYC_ENABLED"));
                                if("1".equalsIgnoreCase(kycStatus.getKycStatus())){
                                	JSONObject object = new JSONObject(data.get("DATA").toString());
                                	kycStatus.setData(object.getString("INSURED_DETAILS"));
                                	
                                }else {
                                	kycStatus.setData(null);
                                }
                                
                                kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                                
                            }else {
                            	kycStatus.setKycStatus(null);
                            	kycStatus.setStatus(false);
                            	kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                            	kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                            }
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });

        }
		return kycStatus;


    }


	public InsureDetailsDto paymentOnline(PaymentOnlineTrDto makeOnlineDto, String token, InsureDetailsDto kycStatus) {

        try (Session session = entityManager.unwrap(Session.class)) {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_NEW_API].[UPDATE_PAYMENT_STATUS]" + "(?,?,?,?,?,?,?,?)}")) {
                        callableStatement.setString(1, token);
                        callableStatement.setLong(2, makeOnlineDto.getQuotationId());
                        callableStatement.setString(3, makeOnlineDto.getBatchNo());
                        callableStatement.setBigDecimal(4, makeOnlineDto.getAmount());
                        callableStatement.setString(5,makeOnlineDto.getProfileEmail());
                        callableStatement.setBoolean(6,makeOnlineDto.isWallet());
                        callableStatement.setBoolean(7,makeOnlineDto.isBulk());
                        callableStatement.setString(8, makeOnlineDto.getPaymentStatus());
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getObject("DATA").toString());
                            log.info("7777777777-- DEVICE_NEW_API.UPDATE_PAYMENT_STATUS **********"+data.toString());

                            if (data.getString("RESPONSE_CODE").equals("200")) {

                                kycStatus.setStatus(true);
                                kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                                kycStatus.setData(objectMapper.readValue(data.getJSONObject("DATA").toString(),Object.class));
                                kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                                kycStatus.setKycStatus(null);

                            } else if(data.getString("RESPONSE_CODE").equals("201")) {
                            	kycStatus.setStatus(true);
                                kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                                kycStatus.setKycStatus(data.getString("IS_KYC_ENABLED"));
                                if("1".equalsIgnoreCase(kycStatus.getKycStatus())){
                                	JSONObject object = new JSONObject(data.get("DATA").toString());
                                	kycStatus.setData(object.getString("INSURED_DETAILS"));
                                	
                                }else {
                                	kycStatus.setData(null);
                                }
                                
                                kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                                
                            }else {
                            	kycStatus.setKycStatus(null);
                            	kycStatus.setStatus(false);
                            	kycStatus.setMessage(data.getString("RESPONSE_MESSAGE"));
                            	kycStatus.setResponseCode(data.getString("RESPONSE_CODE"));
                            }
                        }


                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                });

        }
		return kycStatus;


    }


}
