package com.erp.api.service;

import org.json.JSONException;

import com.erp.api.dto.ChangePasswordTrDto;
import com.erp.api.dto.LoginTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.UploadImageTrDto;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface UserService {

    ResponseDto getProfileDetails(String userId, String sessionRefNo) throws JsonProcessingException;

    ResponseDto deviceLoginTrService(LoginTrDto loginObj);

    ResponseDto logout(String userId);

	ResponseDto changePasswordTrService(ChangePasswordTrDto changePassObj);

    ResponseDto uploadImageTrService(String sessionId, UploadImageTrDto uploadImageObj) throws JSONException;




}
