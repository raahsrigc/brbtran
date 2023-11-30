package com.erp.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.service.BulkUploadService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")
@RestController
@Validated
public class BulkUploadTrController {

    @Autowired
    private BulkUploadService creditService;

    @PostMapping("/creditlife/bulk-upload")
    public ResponseEntity<ResponseDto> uploadBulkCreditLife(@RequestParam(name = "file") MultipartFile file, @RequestHeader(name = "token",required=false) String token, @RequestHeader(name = "sessionId",required=false) String sessionId,@RequestHeader boolean b2bBulk) {
        ResponseDto responseDto = creditService.creditLifeSave(file, token,sessionId,b2bBulk);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    @PostMapping("/personalaccident/bulk-upload")
    public ResponseEntity<ResponseDto> uploadBulkPersonalAccident(@RequestParam(name = "file") MultipartFile file, @RequestHeader(name = "token",required=false) String token, @RequestHeader(name = "sessionId",required=false) String sessionId,@RequestHeader boolean b2bBulk) {
        ResponseDto responseDto = creditService.personalAccidentSave(file,token,sessionId,b2bBulk);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    
    
    //============================================================================
    
    
    @PostMapping("/creditlife/get-batch-list")
	public ResponseEntity<ResponseDto> getBatchList(@RequestHeader(name = "token",required=false) String token, @RequestHeader(name = "sessionId",required=false) String sessionId,@RequestHeader boolean b2bList,@RequestBody IssuedPolicyDto fatchDto) {
    	ResponseDto responseDto = creditService.getCreditBatchList(fatchDto, token,sessionId,b2bList);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
    
    @PostMapping("/creditlife/get-batch-policy-list")
	public ResponseEntity<ResponseDto> getBatchPolicyList(@RequestHeader(name = "token",required=false) String token, @RequestHeader(name = "sessionId",required=false) String sessionId,@RequestHeader boolean b2bList,@RequestHeader String batchId,@RequestBody IssuedPolicyDto issueDto) {
    	ResponseDto responseDto = creditService.getBatchPolicyList(batchId, token,sessionId,b2bList,issueDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
	}
    
    

}
