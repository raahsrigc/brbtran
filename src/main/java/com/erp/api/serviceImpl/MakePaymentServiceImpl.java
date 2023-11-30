package com.erp.api.serviceImpl;

import java.util.ArrayList;
import java.util.Objects;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import com.erp.api.async.BulkKycStatus;
import com.erp.api.dao.MakePaymentDao;
import com.erp.api.dao.UserDao;
import com.erp.api.dto.InsureDetailsDto;
import com.erp.api.dto.MakePaymentDto;
import com.erp.api.dto.PaymentOnlineTrDto;
import com.erp.api.dto.PaymentWalletTrDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.models.FlutterWaveResponseModel;
import com.erp.api.models.GetKycDataModel;
import com.erp.api.models.MakePaymentModel;
import com.erp.api.models.UserProfileModel;
import com.erp.api.service.MakePaymentService;
import com.erp.api.utils.PaymentsRestCall;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MakePaymentServiceImpl implements MakePaymentService {

    @Autowired
    private MakePaymentDao makePaymentDao;

    @Autowired
    private PaymentsRestCall restCall;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserDao userDao;
    
    @Autowired
    BulkKycStatus bulkKycStatus;

    @Override
    public ResponseDto makePaymentWallet(MakePaymentDto makePaymentDto, String token) throws JSONException, JsonProcessingException {

        if(Objects.isNull(makePaymentDto.getAmount()))
        {
            throw new ValidationException("301","Please enter valid amount",null);
        }

        //checking if enough wallet amount is there
        ResponseDto responseDto1 = new ResponseDto();
        userDao.getProfileDetailsDao(makePaymentDto.getUserId(),token,responseDto1);
        UserProfileModel userProfileModel = objectMapper.readValue(responseDto1.getData().toString(),UserProfileModel.class);
        if(Double.parseDouble(makePaymentDto.getAmount()) > userProfileModel.getWalletAmount())
        {
            throw new ValidationException("401","Please credit your wallet.",null);
        }

        // making payment
        ResponseDto responseDto = new ResponseDto();
        if(makePaymentDto.isBulk())
        {
            makePaymentDao.makePaymentWithWallet(makePaymentDto.getBatchNo(), makePaymentDto.isBulk(),token,responseDto);
        }
        else {
            MakePaymentModel makePaymentModel = new MakePaymentModel();
            makePaymentDao.getTxnRefNo(makePaymentDto.getPolicyId(),token,makePaymentModel);
            makePaymentDao.makePaymentWithWallet(makePaymentModel.getTx_ref(),makePaymentDto.isBulk(),token,responseDto);
        }

        return responseDto;
    }

    @Override
    public ResponseDto makePayment(MakePaymentDto makePaymentDto, String sessionId) throws JSONException, JsonProcessingException {
        MakePaymentModel makePaymentModel = new MakePaymentModel();
        if (makePaymentDto.isBulk()) {

            makePaymentModel.setAmount(makePaymentDto.getAmount());
            makePaymentModel.setCustomerEmail(makePaymentDto.getProfileEmail());
            makePaymentModel.setTx_ref(makePaymentDto.getBatchNo());
        } else {
            makePaymentDao.getTxnRefNo(makePaymentDto.getPolicyId(), sessionId, makePaymentModel);
        }


        ResponseDto responseDto = new ResponseDto();

        JSONObject jsonObject = new JSONObject();
        System.out.println(jsonObject);
        jsonObject.put("tx_ref", makePaymentModel.getTx_ref());
        jsonObject.put("redirect_url", makePaymentDto.getRedirectUrl());
        jsonObject.put("currency", "NGN");
        jsonObject.put("amount", makePaymentModel.getAmount());
        JSONObject data = new JSONObject();
        data.put("email", makePaymentModel.getCustomerEmail());
        data.put("phonenumber", makePaymentModel.getCustomerPhoneNumber());
        data.put("name", makePaymentModel.getCustomerName());
        jsonObject.put("customer", data);
        try {
            ResponseEntity<FlutterWaveResponseModel> response = restCall.makePayments(jsonObject);

            if (response.getStatusCode() == HttpStatus.OK) {
                FlutterWaveResponseModel body = response.getBody();


                if (Objects.equals(body.getStatus(), "success")) {
                    responseDto.setResponseCode("200");

                    makePaymentDao.updatePaymentLink(makePaymentDto.getPolicyId(), sessionId, body.getData().get("link").toString());
                    responseDto.setData(body.getData());
                    responseDto.setStatus(true);
                    responseDto.setMessage(body.getMessage());
                } else {
                    responseDto.setResponseCode("401");
                    responseDto.setMessage(body.getMessage());
                }


            }
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            e.printStackTrace();

            responseDto.setStatus(false);
            responseDto.setResponseCode(e.getStatusCode().toString());
            responseDto.setMessage(e.getMessage());


        } catch (Exception e) {
            responseDto.setStatus(false);
            responseDto.setResponseCode("201");
            responseDto.setMessage("Technical Error occurred.Please try again");
        }

        return responseDto;

    }

    @Override
    public ResponseDto verifyPayment(String txnId, String txnRef, Boolean isBulk, Long policyId, String sessionRefNo) throws JSONException, JsonProcessingException {

        if (!Objects.isNull(policyId)) {
            MakePaymentModel makePaymentModel = new MakePaymentModel();
            makePaymentDao.getTxnRefNo(policyId, sessionRefNo, makePaymentModel);
            txnRef = makePaymentModel.getTx_ref();

        }
        if (Objects.isNull(txnRef)) {
            throw new RuntimeException("BAD REQUEST");
        }
        JSONObject jsonObject = makePaymentDao.getPaymentStatus(isBulk,txnRef, sessionRefNo);
        if ("201".equals(jsonObject.get("RESPONSE_CODE")) || "200".equals(jsonObject.get("RESPONSE_CODE"))) {
            JSONObject jsonObject1 = jsonObject.getJSONObject("DATA");


            return new ResponseDto(true,
                    jsonObject.getString("RESPONSE_MESSAGE"), objectMapper.readValue(jsonObject1.toString(), Object.class), jsonObject.getString("RESPONSE_CODE"));

//        } else if (Objects.equals(jsonObject.get("RESPONSE_CODE"), "999")) {
//            return new ResponseDto(false,
//                    jsonObject.getString("RESPONSE_MESSAGE"), null, jsonObject.getString("RESPONSE_CODE"));
        }
        ResponseDto responseDto = new ResponseDto();
        try {
            ResponseEntity<FlutterWaveResponseModel> response = restCall.verifyPayments(txnRef);
            makePaymentDao.updatePaymentStatus(txnRef, isBulk, sessionRefNo, response.getBody(), responseDto);
            FlutterWaveResponseModel body = response.getBody();
            if(body == null)
            {
                return new ResponseDto(false,"Error occurred.Please re-initiate payment",null,"203");
            }
            if (responseDto.isStatus()) {

                JSONObject jsonObject2 = new JSONObject(responseDto.getData().toString());
                if (!body.getData().isEmpty()) {
                    jsonObject2.put("amount", body.getData().get("amount"));
                    jsonObject2.put("status", body.getData().get("status"));
                    jsonObject2.put("message", body.getData().get("processor_response"));
                }
                responseDto.setData(objectMapper.readValue(jsonObject2.toString(), Object.class));

            }
            else {
                responseDto.setStatus(true);
                JSONObject jsonObject2 = new JSONObject();
                jsonObject2.put("status", body.getStatus());
                jsonObject2.put("message", body.getMessage());
                responseDto.setResponseCode("200");
                responseDto.setMessage("SUCCESS");
            }

        } catch (HttpClientErrorException | HttpServerErrorException e) {

            if (e.getRawStatusCode() == 400) {
                responseDto.setStatus(false);
                responseDto.setResponseCode(e.getStatusCode().toString());
                responseDto.setMessage("No transaction found for this id.");
            } else {
                responseDto.setStatus(false);
                responseDto.setResponseCode(e.getStatusCode().toString());
                responseDto.setMessage(e.getMessage());
            }


        } catch (Exception e) {
            responseDto.setStatus(false);
            responseDto.setResponseCode("201");
            responseDto.setMessage("Technical Error occurred.Please try again");
        }


        return responseDto;
    }

	@Override
	public ResponseDto makePaymentWalletTr(PaymentWalletTrDto makePaymentDto, String token) throws JsonMappingException, JsonProcessingException {

        if(Objects.isNull(makePaymentDto.getAmount()))
        {
            throw new ValidationException("301","Please enter valid amount",null);
        }
        // making payment
        
        ResponseDto responseDto = new ResponseDto();
        InsureDetailsDto kycStatus=new InsureDetailsDto();
        if(makePaymentDto.isBulk())
        {
        	kycStatus=makePaymentDao.paymentWallet(makePaymentDto,token,kycStatus);
            
            if ("201".equalsIgnoreCase(kycStatus.getResponseCode())) {
				if ("1".equalsIgnoreCase(kycStatus.getKycStatus())) {
					ArrayList<GetKycDataModel> list = objectMapper.readValue(kycStatus.getData().toString(),new TypeReference<ArrayList<GetKycDataModel>>() {});
					if (!Objects.isNull(list) && list.size() != 0) {
						responseDto.setData(null);
						responseDto.setMessage(kycStatus.getMessage());
						responseDto.setResponseCode(kycStatus.getResponseCode());
						responseDto.setStatus(kycStatus.isStatus());
						bulkKycStatus.checkKycStatusWallet(token, list);
					}
				}else {
					responseDto.setData(null);
					responseDto.setMessage(kycStatus.getMessage());
					responseDto.setResponseCode(kycStatus.getResponseCode());
					responseDto.setStatus(kycStatus.isStatus());
            	}

            }
        }
        else {
        	log.info("$$$$$$$$$$$ Pay by wallet in service layer $$$$$$$$$$$"+ makePaymentDto.toString());
        	kycStatus=makePaymentDao.paymentWallet(makePaymentDto,token,kycStatus);
        	responseDto.setData(kycStatus.getData());
            responseDto.setMessage(kycStatus.getMessage());
            responseDto.setResponseCode(kycStatus.getResponseCode());
            responseDto.setStatus(kycStatus.isStatus());
        }

        return responseDto;
    }

	@Override
	public ResponseDto makePaymentOnlineTr(PaymentOnlineTrDto makeOnlineDto, String token) throws JsonMappingException, JsonProcessingException {

        if(Objects.isNull(makeOnlineDto.getAmount()))
        {
            throw new ValidationException("301","Please enter valid amount",null);
        }
        // making payment
        
        ResponseDto responseDto = new ResponseDto();
        InsureDetailsDto kycStatus=new InsureDetailsDto();
        if(makeOnlineDto.isBulk())
        {
        	kycStatus=makePaymentDao.paymentOnline(makeOnlineDto,token,kycStatus);
            
            if ("201".equalsIgnoreCase(kycStatus.getResponseCode())) {
				if ("1".equalsIgnoreCase(kycStatus.getKycStatus())) {
					ArrayList<GetKycDataModel> list = objectMapper.readValue(kycStatus.getData().toString(),new TypeReference<ArrayList<GetKycDataModel>>() {});
					if (!Objects.isNull(list) && list.size() != 0) {
						responseDto.setData(null);
						responseDto.setMessage(kycStatus.getMessage());
						responseDto.setResponseCode(kycStatus.getResponseCode());
						responseDto.setStatus(kycStatus.isStatus());
						bulkKycStatus.checkKycStatusOnline(token, list);
					}
				}else {
					responseDto.setData(null);
					responseDto.setMessage(kycStatus.getMessage());
					responseDto.setResponseCode(kycStatus.getResponseCode());
					responseDto.setStatus(kycStatus.isStatus());
            	}

            }
        }
        else {
        	log.info("$$$$$$$$$$$ Pay by wallet in service layer $$$$$$$$$$$"+ makeOnlineDto.toString());
        	kycStatus=makePaymentDao.paymentOnline(makeOnlineDto,token,kycStatus);
        	responseDto.setData(kycStatus.getData());
            responseDto.setMessage(kycStatus.getMessage());
            responseDto.setResponseCode(kycStatus.getResponseCode());
            responseDto.setStatus(kycStatus.isStatus());
        }

        return responseDto;
    }


}
