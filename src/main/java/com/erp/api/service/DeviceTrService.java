package com.erp.api.service;

import org.json.JSONException;

import com.erp.api.dto.BatchTrDto;
import com.erp.api.dto.FetchPolicyTrDto;
import com.erp.api.dto.FetchQuotationTrDto;
import com.erp.api.dto.InsertHistoryTrDto;
import com.erp.api.dto.InsertQuotationTrDto;
import com.erp.api.dto.QuotSearchFilterDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.dto.SaveDevicePolicyTrDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface DeviceTrService {
	
	ResponseDto summaryDataTrService(String token);
	ResponseDto insertQuotTrService(InsertQuotationTrDto insertQObj, String token) throws JSONException, JsonMappingException, JsonProcessingException;
	ResponseDto getPolicyById(Long policyId, String token);
	ResponseDto getBatchDetails(String batchNo,int isPolicy, String token);
	ResponseDto getKycDetailById(int insuredId, String token);
	ResponseDto saveDevicePolicyTrService(SaveDevicePolicyTrDto saceDeviceObj, String token
			) throws JSONException, JsonMappingException, JsonProcessingException;

	ResponseDto getCommentHistory(String objectUid, String recordId, String token);
	ResponseDto insertHistoryService(InsertHistoryTrDto historyObj, String sessionId);

	ResponseDto getDeviceHistory(int deviceId, String sessionRefNo);

	ResponseDto quotationByMail(String mobileOrEmail, String sessionRefNo);

	ResponseDto quotationList(QuotSearchFilterDto searchObj, String sessionRefNo);
	ResponseDto getQuotationsList(FetchQuotationTrDto insertQOb, String token);
	ResponseDto getPolicyList(FetchPolicyTrDto polObj, String token);
	ResponseDto getBatches(BatchTrDto batchDto, String token);
	ResponseDto kycStatusCheck(Long insuredId, String token);
	ResponseDto getQuotationDById(int quotationId, String token);
	ResponseDto getBatchList(BatchTrDto batchDto, String token);
	ResponseDto bulkQuatationByIdTr(long quotationId, String token);
	ResponseDto getInsuredTr(long quotationId, long insuredId, int bulkKycId, String token);
	ResponseDto getPaymentDetailsTr(long policyId, String serviceType, String token);
	

	

}
