package com.erp.api.async;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.erp.api.dto.ResponseDto;
import com.erp.api.models.GetKycDataModel;
import com.erp.api.models.GetKycStatusModel;
import com.erp.api.models.SaveKycStatusModel;
import com.erp.api.models.UpdateKycStatusModel;
import com.erp.api.utils.KYCRestCall;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class BulkKycStatus {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private KYCRestCall kycRestCall;


    @Async
    public void checkKycStatus(String sessionRefNo,ArrayList<GetKycStatusModel> list)
    {

        List<UpdateKycStatusModel> updateKycStatus = new ArrayList<>();
        for(GetKycStatusModel record: list)
        {
            UpdateKycStatusModel updateRecord = new UpdateKycStatusModel();
            try {
                ResponseEntity<String> response = kycRestCall.kycDetails(record, sessionRefNo);
                if(response.getStatusCode()== HttpStatus.OK)
                {

                    JSONObject object = new JSONObject(response.getBody());
                    if(object.getBoolean("success"))
                    {
                        updateRecord.setInsuredId(Long.valueOf(record.getTrackingRefNo()));
                        updateRecord.setIsKyc(object.getInt("idStatus"));
                    }
                    else {
                        updateRecord.setInsuredId(Long.valueOf(record.getTrackingRefNo()));
                        updateRecord.setIsKyc(0);
                    }
                }
                else {
                    updateRecord.setInsuredId(Long.valueOf(record.getTrackingRefNo()));
                    updateRecord.setIsKyc(0);
                }
            }
            catch (JSONException e) {
                updateRecord.setInsuredId(Long.valueOf(record.getTrackingRefNo()));
                updateRecord.setIsKyc(0);
            }
            updateRecord.setBatchNumber(record.getBatchNumber());
            updateRecord.setPremium(record.getPremium());
            updateRecord.setSumInsured(record.getSumInsured());
            updateRecord.setPolicyId(record.getPolicyId());
            updateKycStatus.add(updateRecord);


        }
        ResponseDto responseDto = new ResponseDto();
        updateKycStatusOfBatch(updateKycStatus,sessionRefNo,responseDto);
    }


    public void updateKycStatusOfBatch(List<UpdateKycStatusModel> kycStatusList, String sessionId, ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(kycStatusList).getAsJsonArray();

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_NEW_API].[UPDATE_KYC_STATUS]" + "(?,?)}")) {
                        callableStatement.setString(1, sessionId);
                        callableStatement.setString(2, String.valueOf(myCustomArray));

                        System.out.println(myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));

                            if (data.getString("RESPONSE_CODE").equals("200")) {

                                responseDto.setStatus(true);


                            }

                            responseDto.setData(objectMapper.readValue(data.get("DATA").toString(), new TypeReference<ArrayList<Object>>() {
                            }));
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

    }

    @Async
	public void checkKycStatusWallet(String token, ArrayList<GetKycDataModel> list) {

        List<SaveKycStatusModel> updateKycStatus = new ArrayList<>();
        for(GetKycDataModel record: list)
        {
        	SaveKycStatusModel updateRecord = new SaveKycStatusModel();
            try {
                ResponseEntity<String> response = kycRestCall.getkycStatus(record, token);
                if(response.getStatusCode()== HttpStatus.OK)
                {

                    JSONObject object = new JSONObject(response.getBody());
                    if(object.getBoolean("success"))
                    {
                        updateRecord.setId(record.getId());
                        updateRecord.setIsKyc(object.getInt("idStatus"));
                        updateRecord.setKycRemark(object.optString("message"));
                        updateRecord.setBatchNumber(record.getBatchNumber());
                    }
                    else {
                    	updateRecord.setId(record.getId());
                        updateRecord.setIsKyc(object.getInt("idStatus"));
                        updateRecord.setKycRemark(object.optString("message"));
                        updateRecord.setBatchNumber(record.getBatchNumber());
                    }
                }else {
                	updateRecord.setId(record.getId());
                    updateRecord.setIsKyc(102);
                    updateRecord.setKycRemark("Technical Issues");
                    updateRecord.setBatchNumber(record.getBatchNumber());
                }
            }
            catch (JSONException e) {
            	updateRecord.setId(record.getId());
                updateRecord.setIsKyc(102);
                updateRecord.setKycRemark("Technical Issues");
                updateRecord.setBatchNumber(record.getBatchNumber());
            }
            updateRecord.setBatchNumber(record.getBatchNumber());
            updateRecord.setPremium(record.getPremium());
            updateRecord.setSumInsured(record.getSumInsured());
            updateRecord.setPolicyId(record.getPolicyId());
            updateKycStatus.add(updateRecord);
        }
        ResponseDto responseDto = new ResponseDto();
        updateKycStatusOfWalletBatch(updateKycStatus,token,responseDto);
        
    }


	private void updateKycStatusOfWalletBatch(List<SaveKycStatusModel> updateKycStatus, String token,ResponseDto responseDto) {

        try (Session session = entityManager.unwrap(Session.class)) {
            Gson gson = new GsonBuilder().create();
            JsonArray myCustomArray = gson.toJsonTree(updateKycStatus).getAsJsonArray();
            log.info("++++++++++++++++++++++ UPDATE KYC DATA WHILE DONE WALLET PAYMENT ++++++++++++++++++++++"+ myCustomArray.toString());

            try {
                session.doWork(connection -> {

                    try (CallableStatement callableStatement = connection.prepareCall("{call " + "[DEVICE_NEW_API].[UPDATE_KYC_STATUS]" + "(?,?)}")) {
                        callableStatement.setString(1, token);
                        callableStatement.setString(2, String.valueOf(myCustomArray));

                        System.out.println(myCustomArray);
                        callableStatement.execute();

                        ResultSet resultSet = callableStatement.getResultSet();
                        while (resultSet.next()) {
                            JSONObject data = new JSONObject(resultSet.getString("DATA"));

                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                            }

                            responseDto.setData(objectMapper.readValue(data.get("DATA").toString(), new TypeReference<ArrayList<Object>>() {
                            }));
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

    }

	@Async
	public void checkKycStatusOnline(String token, ArrayList<GetKycDataModel> list) {

        List<SaveKycStatusModel> updateKycStatus = new ArrayList<>();
        for(GetKycDataModel record: list)
        {
        	SaveKycStatusModel updateRecord = new SaveKycStatusModel();
            try {
                ResponseEntity<String> response = kycRestCall.getkycStatus(record, token);
                if(response.getStatusCode()== HttpStatus.OK)
                {

                    JSONObject object = new JSONObject(response.getBody());
                    if(object.getBoolean("success"))
                    {
                        updateRecord.setId(record.getId());
                        updateRecord.setIsKyc(object.getInt("idStatus"));
                        updateRecord.setKycRemark(object.optString("message"));
                        updateRecord.setBatchNumber(record.getBatchNumber());
                    }
                    else {
                    	updateRecord.setId(record.getId());
                        updateRecord.setIsKyc(object.getInt("idStatus"));
                        updateRecord.setKycRemark(object.optString("message"));
                        updateRecord.setBatchNumber(record.getBatchNumber());
                    }
                }
                else {
                	updateRecord.setId(record.getId());
                    updateRecord.setIsKyc(102);
                    updateRecord.setKycRemark("Technical Issues");
                    updateRecord.setBatchNumber(record.getBatchNumber());
                }
            }
            catch (JSONException e) {
            	updateRecord.setId(record.getId());
                updateRecord.setIsKyc(102);
                updateRecord.setKycRemark("Technical Issues");
                updateRecord.setBatchNumber(record.getBatchNumber());
            }
            updateRecord.setBatchNumber(record.getBatchNumber());
            updateRecord.setPremium(record.getPremium());
            updateRecord.setSumInsured(record.getSumInsured());
            updateRecord.setPolicyId(record.getPolicyId());
            updateKycStatus.add(updateRecord);


        }
        ResponseDto responseDto = new ResponseDto();
        updateKycStatusOfWalletBatch(updateKycStatus,token,responseDto);
    }

}
