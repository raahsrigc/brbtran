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
import org.springframework.web.bind.annotation.RestController;

import com.erp.api.dto.FetchWalletTrDto;
import com.erp.api.dto.PaymentDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.service.WalletService;


@RestController
@RequestMapping("/v1")
@CrossOrigin("*")
public class walletController {

    @Autowired
    private WalletService walletService;

//    @GetMapping("/SOA")
//    public ResponseEntity<ResponseDto> getSOA(@RequestParam String fromDate,
//                                              @RequestParam String toDate,
//                                              @RequestHeader(defaultValue = "10", required = false) Integer pageCount,
//                                              @RequestHeader(defaultValue = "1", required = false) Integer pageNumber,
//                                              @RequestHeader(required = false) boolean search,
//                                              @RequestHeader(required = false) String columnName,
//                                              @RequestHeader(required = false) String columnValue,
//                                              @RequestHeader String token)
//    {
//        return new ResponseEntity<>(walletService.getSOA(token,fromDate,toDate,pageCount,pageNumber,search,columnName,columnValue), HttpStatus.OK);
//    }
    
    @GetMapping("/SOA")
    public ResponseEntity<ResponseDto> getSOA(@RequestBody FetchWalletTrDto soaObj,@RequestHeader String token)
    {
        return new ResponseEntity<>(walletService.getSOA(token,soaObj), HttpStatus.OK);
    }

    @PostMapping("/credit")
    public ResponseEntity<ResponseDto> insertCreditRequest(@RequestBody PaymentDto paymentDto,@RequestHeader String token) throws JSONException {

        return new ResponseEntity<>(walletService.addCreditRequest(paymentDto,token), HttpStatus.OK);

    }
}
