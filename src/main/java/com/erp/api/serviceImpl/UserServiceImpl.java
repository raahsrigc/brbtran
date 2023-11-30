package com.erp.api.serviceImpl;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.erp.api.dao.UserDao;
import com.erp.api.dto.ChangePasswordTrDto;
import com.erp.api.dto.LoginTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.UploadImageTrDto;
import com.erp.api.service.UserService;
import com.erp.api.utils.KYCRestCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    KYCRestCall kycRestCall;
    @Autowired
    ObjectMapper objectMapper;

    @Override
    public ResponseDto getProfileDetails(String userId, String sessionRefNo) throws JsonProcessingException {
        ResponseDto responseDto = new ResponseDto();
        userDao.getProfileDetailsDao(userId,sessionRefNo, responseDto);
        HashMap<String,String> list = objectMapper.readValue(responseDto.getData().toString(),
                new TypeReference<HashMap<String, String>>() {
                });
        responseDto.setData(list);
        return responseDto;
    }

    @Override
    public ResponseDto deviceLoginTrService(LoginTrDto loginObj) {

        return userDao.deviceLoginDao(loginObj, new ResponseDto());

    }
    @Override
    public ResponseDto logout(String userId) {
        return userDao.logoutDao(userId, new ResponseDto());
    }

	@Override
	public ResponseDto changePasswordTrService(ChangePasswordTrDto changePassObj) {
		return userDao.changePasswordDao(changePassObj, new ResponseDto());
	}

    @Override
    public ResponseDto uploadImageTrService(String sessionId, UploadImageTrDto uploadImageObj) throws JSONException, JSONException {

        ResponseEntity<String> response = kycRestCall.uploadImage(uploadImageObj);
        JSONObject jsObject = new JSONObject(response.getBody());

        String  imageUrl = jsObject.optString("data");

        return userDao.uploadImageDao(sessionId,uploadImageObj,imageUrl, new ResponseDto());
    }

	
	

}
