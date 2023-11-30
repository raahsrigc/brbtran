package com.erp.api.service;

import com.erp.api.dto.FetchWalletTrDto;
import com.erp.api.dto.PaymentDto;
import com.erp.api.dto.ResponseDto;


public interface WalletService {

//    ResponseDto getSOA(String token,String fromDate,String toDate,int pageCount,int pageNumber,boolean search,String columnName,String columnValue);

    ResponseDto addCreditRequest(PaymentDto paymentDto, String token);

    ResponseDto getSOA(String token, FetchWalletTrDto soaObj);
}
