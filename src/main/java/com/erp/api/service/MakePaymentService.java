package com.erp.api.service;

import org.json.JSONException;

import com.erp.api.dto.MakePaymentDto;
import com.erp.api.dto.PaymentOnlineTrDto;
import com.erp.api.dto.PaymentWalletTrDto;
import com.erp.api.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


public interface MakePaymentService {

    ResponseDto makePaymentWallet(MakePaymentDto makePaymentDto,String token) throws JSONException, JsonProcessingException;

    ResponseDto makePayment(MakePaymentDto makePaymentDto,String sessionRefNo) throws JSONException, JsonProcessingException;

    ResponseDto verifyPayment(String txnId,String txnRef,Boolean isBulk,Long PolicyId,String sessionRefNo) throws JSONException, JsonProcessingException;

    ResponseDto makePaymentWalletTr(PaymentWalletTrDto makePaymentDto, String token) throws JsonMappingException, JsonProcessingException;

    ResponseDto makePaymentOnlineTr(PaymentOnlineTrDto makeOnlineDto, String token) throws JsonMappingException, JsonProcessingException;

}
