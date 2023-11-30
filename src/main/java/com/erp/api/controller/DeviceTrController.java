package com.erp.api.controller;

import javax.validation.Valid;
import org.json.JSONException;
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
import com.erp.api.dto.BatchTrDto;
import com.erp.api.dto.FetchPolicyTrDto;
import com.erp.api.dto.FetchQuotationTrDto;
import com.erp.api.dto.InsertHistoryTrDto;
import com.erp.api.dto.InsertQuotationTrDto;
import com.erp.api.dto.QuotSearchFilterDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SaveDevicePolicyTrDto;
import com.erp.api.service.DeviceTrService;
import com.erp.api.service.FileService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/v1")
@RestController
@Validated
public class DeviceTrController {

    @Autowired
    private DeviceTrService deviceTrService;

    @Autowired
    private FileService fileService;


    
    @GetMapping(value = "/summaryData")
    public ResponseEntity<ResponseDto> getSummaryData(@RequestHeader("token") String token) {
    	
    	ResponseDto responseDto = deviceTrService.summaryDataTrService(token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    

    @PostMapping(value = "/quotation")
    public ResponseEntity<ResponseDto> addQuotation(@RequestHeader("token") String token,@RequestBody InsertQuotationTrDto insertQObj) throws JSONException, JsonMappingException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.insertQuotTrService(insertQObj, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @PostMapping(value = "/quotations")
    public ResponseEntity<ResponseDto> getQuotations(@RequestHeader("token") String token,@RequestBody FetchQuotationTrDto insertQOb) throws JSONException {

        ResponseDto responseDto = deviceTrService.getQuotationsList(insertQOb, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
    
    @GetMapping(value = {"/policy"})
    public ResponseEntity<ResponseDto> getPolicies(@RequestHeader("token") String token,@RequestParam(required = true) Long policyId) throws JSONException {

        ResponseDto responseDto = deviceTrService.getPolicyById(policyId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
    
    @GetMapping("/batch")
    public ResponseEntity<ResponseDto> getBatchDetails(@RequestParam(required = true) String batchId,  @RequestHeader String token,@RequestHeader int isPolicy) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getBatchDetails(batchId,isPolicy, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/kycDetailById")
    public ResponseEntity<ResponseDto> getKycDetailById(@RequestParam(required = true) int insuredId,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getKycDetailById(insuredId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @PostMapping(value = {"/policies"})
    public ResponseEntity<ResponseDto> getPolicies(@RequestHeader("token") String token,@RequestBody FetchPolicyTrDto polObj) throws JSONException {

        ResponseDto responseDto = deviceTrService.getPolicyList(polObj, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }

    @PostMapping({"/batchlist"})
    public ResponseEntity<ResponseDto> getBatch(@RequestBody BatchTrDto batchDto,@RequestHeader(name = "token") String token) {
        ResponseDto responseDto = deviceTrService.getBatches(batchDto, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/history")
    public ResponseEntity<ResponseDto> getDeviceHistory(@RequestParam(required = true) int deviceId,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getDeviceHistory(deviceId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/comment")
    public ResponseEntity<ResponseDto> getCommentHistory(@RequestParam String  objectUid,@RequestParam String recordId ,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getCommentHistory(objectUid,recordId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping(value = "/insertComment")
    public ResponseEntity<ResponseDto> insertHistory(@RequestHeader(name = "token") String token, @RequestBody InsertHistoryTrDto historyObj) throws JSONException {
        ResponseDto responseDto = deviceTrService.insertHistoryService(historyObj, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @PostMapping(value = "/policyCompleteKyc")
    public ResponseEntity<ResponseDto> addPolicy(@RequestHeader("token") String token, @RequestBody @Valid SaveDevicePolicyTrDto saceDeviceObj) throws JSONException, JsonMappingException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.saveDevicePolicyTrService(saceDeviceObj, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);

    }
    
    @PostMapping("/quotationlist")
    public ResponseEntity<ResponseDto> quotationList(@RequestHeader(name = "sessionId") String sessionRefNo,@RequestBody QuotSearchFilterDto searchObj) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.quotationList(searchObj, sessionRefNo);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    @GetMapping("/kyc-status")
    public ResponseEntity<ResponseDto> kycStatusCheck(@RequestHeader(name = "token") String token,@RequestParam Long insuredId) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.kycStatusCheck(insuredId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/quotationById")
    public ResponseEntity<ResponseDto> getQuotationDById(@RequestParam(required = true) int quotationId,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getQuotationDById(quotationId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    
    
    
    
    

   

//    @PostMapping("/bulk-upload")
//    public ResponseEntity<ResponseDto> uploadBulk(@RequestParam(name = "file") MultipartFile file, @RequestHeader(name = "token") String token) {
//        ResponseDto responseDto = fileService.save(file, token);
//        return new ResponseEntity<>(responseDto, HttpStatus.OK);
//    }

    @PostMapping("/bulk-upload")
    public ResponseEntity<ResponseDto> uploadBulk(@RequestParam(name = "file") MultipartFile file, @RequestHeader(name = "token") String token) {
        ResponseDto responseDto = fileService.saveQuotation(file, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    @PostMapping("/make-payment-details")
    public ResponseEntity<ResponseDto> makePaymentDetailsTr(@RequestParam long batchId, @RequestHeader(name = "token") String token) {
        ResponseDto responseDto = fileService.makePaymentDetailsTr(batchId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    
    
    
    @GetMapping("/quotationbymail")
    public ResponseEntity<ResponseDto> quotationByMail(@RequestHeader(name = "sessionId") String sessionRefNo,@RequestHeader String mobileOrEmail) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.quotationByMail(mobileOrEmail, sessionRefNo);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @PostMapping({"/search-batchlist"})
    public ResponseEntity<ResponseDto> getBatchList(@RequestBody BatchTrDto batchDto,@RequestHeader(name = "token") String token) {
        ResponseDto responseDto = deviceTrService.getBatchList(batchDto, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/bulk-quotation-by-id")
    public ResponseEntity<ResponseDto> bulkQuatationByIdTr(@RequestParam(required = true) long quotationId,@RequestHeader(name = "token") String token) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.bulkQuatationByIdTr(quotationId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/get-insured-details")
    public ResponseEntity<ResponseDto> getInsuredTr(@RequestHeader String token,@RequestHeader long insuredId,@RequestHeader long quotationId,@RequestHeader int bulkKycId) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getInsuredTr(quotationId,insuredId,bulkKycId, token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    @GetMapping("/get-payment-history")
    public ResponseEntity<ResponseDto> getPaymentDetailsTr(@RequestHeader String token,@RequestHeader long policyId,@RequestHeader String serviceType) throws JSONException, JsonProcessingException {
        ResponseDto responseDto = deviceTrService.getPaymentDetailsTr(policyId, serviceType,token);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
    
    

}
