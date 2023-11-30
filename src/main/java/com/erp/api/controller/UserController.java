package com.erp.api.controller;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.api.dto.ChangePasswordTrDto;
import com.erp.api.dto.LoginTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.UploadImageTrDto;
import com.erp.api.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/profile/{userId}")
    public ResponseEntity<ResponseDto> getProfile(@PathVariable String userId, @RequestHeader(name = "sessionId") String sessionRefNo) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = userService.getProfileDetails(userId, sessionRefNo);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@RequestHeader String userId) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = userService.logout(userId);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<ResponseDto> login(@RequestBody LoginTrDto loginObj) throws JSONException {

        ResponseDto responseMessage = userService.deviceLoginTrService(loginObj);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    @PostMapping(value = "/changePassword")
    public ResponseEntity<ResponseDto> changePassword(@RequestBody ChangePasswordTrDto changePassObj) throws JSONException {

        ResponseDto responseMessage = userService.changePasswordTrService(changePassObj);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);

    }

    @PostMapping(value = "/uploadImage")
    public ResponseEntity<ResponseDto> uploadImage(@RequestHeader("sessionId") String sessionId, @RequestBody UploadImageTrDto uploadImageObj) throws JSONException {
        ResponseDto responseMessage = userService.uploadImageTrService(sessionId,uploadImageObj);
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }







}
