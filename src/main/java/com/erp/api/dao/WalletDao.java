package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.FetchWalletTrDto;
import com.erp.api.dto.PaymentDto;
import com.erp.api.dto.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class WalletDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;


    public ResponseDto getSOA(String token, FetchWalletTrDto soaObj, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[FINANCE].[GET_CUSTOMER_SOA]" + "(?,?,?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, soaObj.getFromDate());
                        callst.setString(3, soaObj.getToDate());
                        callst.setInt(4, soaObj.getPageCount());
                        callst.setInt(5, soaObj.getPageNumber());
                        Boolean b1=Boolean.valueOf(soaObj.getSearch());
                        callst.setBoolean(6, b1);
                        callst.setString(7, soaObj.getColumnName());
                        callst.setString(8, soaObj.getColumnValue());

                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                                JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                Object list = objectMapper.readValue(jsonObject.getString("DATA"),
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

    public ResponseDto addCreditRequest(String token, PaymentDto paymentDto, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {

            session.doWork(connection -> {
                ResultSet res = null;
                try (CallableStatement callst = connection.prepareCall("{call " + "[FINANCE].[INSERT_CREDIT_REQUEST_FOR_WALLET_CUSTOMER]" + "(?,?,?)}")) {
                    callst.setString(1, token);
                    callst.setDouble(2, paymentDto.getAmount());
                    callst.setString(3, paymentDto.getTxnRefNo());
                    callst.execute();
                    res = callst.getResultSet();
                    while (res.next()) {
                        JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                        if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                            responseDto.setStatus(true);
                            Object list = objectMapper.readValue(jsonObject.getString("DATA"),
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

        }
        return responseDto;
    }
}
