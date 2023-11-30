package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SaveDevicePolicyTrDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class KYCDao {

    @Autowired
    private EntityManager entityManager;

    public ResponseDto getKycStatus(String token, SaveDevicePolicyTrDto saveDeviceObj, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE_API].[GET_KYC_STATUS]" + "(?,?,?,?,?,?)}")) {
                        callst.setString(1, token);
                        callst.setString(2, saveDeviceObj.getIdNumber());
                        callst.setString(3, saveDeviceObj.getIdType());
                        callst.setString(4, saveDeviceObj.getFirstName());
                        callst.setString(5, saveDeviceObj.getLastName());
                        callst.setString(6, saveDeviceObj.getDob());
                        
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            JSONObject jsonObject = new JSONObject(res.getObject("Data").toString());
                            log.info("7777777777-- DEVICE_INSURANCE_API.GET_KYC_STATUS **********"+jsonObject.toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setResponseCode(jsonObject.getString("RESPONSE_CODE"));
                                responseDto.setMessage(jsonObject.getString("RESPONSE_MESSAGE"));
                            }
                            responseDto.setStatus(false);
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


}
