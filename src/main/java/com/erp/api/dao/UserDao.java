package com.erp.api.dao;

import java.sql.CallableStatement;
import java.sql.ResultSet;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.erp.api.dto.ChangePasswordTrDto;
import com.erp.api.dto.LoginTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.UploadImageTrDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class UserDao {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ObjectMapper objectMapper;

    public ResponseDto getProfileDetailsDao(String userId, String sessionRefNo, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[DEVICE_INSURANCE].[GET_USER_PROFILE]" + "(?,?)}")) {
                        callst.setString(1, sessionRefNo);
                        callst.setString(2, userId);
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            JSONObject jsonObject = new JSONObject(res.getObject("DATA").toString());
                            if (jsonObject.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(jsonObject.getString("DATA"));
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

    public ResponseDto deviceLoginDao(LoginTrDto loginObj, ResponseDto responseMessage) {
        try (Session session = entityManager.unwrap(Session.class)) {

            try {
                session.doWork(connection -> {
                    ResultSet resultSet = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[LOGIN].[VALIDATE_USER_LOGIN_DETAILS_FOR_WEB]" + "(?,?,?,?,?)}")) {
                        callst.setString(1, loginObj.getUserId());
                        callst.setString(2, loginObj.getPassword());
                        callst.setString(3,loginObj.getDeviceType());
                        callst.setString(4,loginObj.getIpAddress());
                        callst.setString(5,loginObj.getBrowserType());

                        callst.execute();
                        resultSet = callst.getResultSet();

                        while (resultSet.next()) {
                            String bulkInsert = resultSet.getObject("Data").toString();
                            JSONObject data = new JSONObject(bulkInsert);
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseMessage.setStatus(true);

                                String queryRemitterRequest = data.optString("DATA");
                                Object mapperObj = objectMapper.readValue(queryRemitterRequest, Object.class);
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

        return responseMessage;
    }

    public ResponseDto logoutDao(String userId, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[LOGIN].[LOGOUT]" + "(?)}")) {
                        callst.setString(1, userId);
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            String bulkInsert=res.getObject("Data").toString();

                            JSONArray array=new JSONArray(bulkInsert);
                            JSONObject data=new JSONObject();

                            data=array.getJSONObject(0);
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(null);
                            }
                            responseDto.setResponseCode(data.getString("RESPONSE_CODE"));
                            responseDto.setMessage("");
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

	public ResponseDto changePasswordDao(ChangePasswordTrDto changePassObj, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[LOGIN].[UPDATE_CHANGE_USER_PASSWORD]" + "(?,?,?)}")) {
                        callst.setString(1, changePassObj.getUserId());
                        callst.setString(2, changePassObj.getOldPassword());
                        callst.setString(3, changePassObj.getNewPassword());
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            String bulkInsert=res.getObject("Data").toString();

                            JSONArray array=new JSONArray(bulkInsert);
                            JSONObject data=new JSONObject();

                            data=array.getJSONObject(0);
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(null);
                            }
                            responseDto.setMessage(data.get("RESPONSE_MESSAGE").toString());
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

    public ResponseDto uploadImageDao(String sessionId, UploadImageTrDto uploadImageObj, String imageUrl, ResponseDto responseDto) {
        try (Session session = entityManager.unwrap(Session.class)) {
            try {
                session.doWork(connection -> {
                    ResultSet res = null;
                    try (CallableStatement callst = connection.prepareCall("{call " + "[LOGIN].[UPDATE_CHANGE_PROFILE_IMAGE]" + "(?,?,?)}")) {
                        callst.setString(1, sessionId);
                        callst.setString(2, uploadImageObj.getUserId());
                        callst.setString(3, imageUrl);
                        callst.execute();
                        res = callst.getResultSet();
                        while (res.next()) {
                            String bulkInsert=res.getObject("Data").toString();
                            JSONArray array=new JSONArray(bulkInsert);
                            JSONObject data=new JSONObject();
                            data=array.getJSONObject(0);
                            if (data.getString("RESPONSE_CODE").equals("200")) {
                                responseDto.setStatus(true);
                                responseDto.setData(null);
                            }
                            responseDto.setMessage(data.get("RESPONSE_MESSAGE").toString());
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
}
