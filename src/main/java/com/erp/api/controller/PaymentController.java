package com.erp.api.controller;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.erp.api.dto.MakePaymentDto;
import com.erp.api.dto.PaymentOnlineTrDto;
import com.erp.api.dto.PaymentWalletTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.service.MakePaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class PaymentController {

    @Autowired
    MakePaymentService makePaymentService;


    @PostMapping("/policy/pay")
    public ResponseEntity<ResponseDto> makePayment(@RequestBody MakePaymentDto makePaymentDto,
                                                   @RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {

        if (makePaymentDto.isWallet()) {
            return new ResponseEntity<>(makePaymentService.makePaymentWallet(makePaymentDto, token), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(makePaymentService.makePayment(makePaymentDto, token), HttpStatus.OK);
        }

    }


    @GetMapping("/policy/verify")
    public ResponseEntity<ResponseDto> verifyPayment(@RequestParam(required = false) String txnId,
                                                     @RequestParam(required = false) String txnRef,
                                                     @RequestParam(required = false) Long policyId,
                                                     @RequestHeader Boolean isBulk,
                                                     @RequestHeader(name = "sessionId") String sessionRefNo) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = makePaymentService.verifyPayment(txnId, txnRef, isBulk, policyId, sessionRefNo);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    @PostMapping("/policy/pay-wallet")
    public ResponseEntity<ResponseDto> makePaymentWallet(@RequestBody PaymentWalletTrDto makePaymentDto,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
    	log.info("$$$$$$$$$$$ Get Request from middle layer for Pay by wallet $$$$$$$$$$$"+ makePaymentDto.toString());
    	return new ResponseEntity<>(makePaymentService.makePaymentWalletTr(makePaymentDto, token), HttpStatus.OK);
    }

    
    @PostMapping("/policy/pay-online")
    public ResponseEntity<ResponseDto> makePaymentOnline(@RequestBody PaymentOnlineTrDto makeOnlineDto,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
    	log.info("$$$$$$$$$$$ Get Request from middle layer for Pay by ONLINE $$$$$$$$$$$"+ makeOnlineDto.toString());
    	return new ResponseEntity<>(makePaymentService.makePaymentOnlineTr(makeOnlineDto, token), HttpStatus.OK);
    }

}
