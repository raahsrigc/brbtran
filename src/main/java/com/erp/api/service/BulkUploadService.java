package com.erp.api.service;

import org.springframework.web.multipart.MultipartFile;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.ResponseDto;

public interface BulkUploadService {

	ResponseDto creditLifeSave(MultipartFile file, String token, String sessionId, boolean b2bBulk);

	ResponseDto personalAccidentSave(MultipartFile file, String token, String sessionId, boolean b2bBulk);

	ResponseDto getCreditBatchList(IssuedPolicyDto fatchDto, String token, String sessionId, boolean b2bList);

	ResponseDto getBatchPolicyList(String batchId, String token, String sessionId, boolean b2bList,IssuedPolicyDto issueDto);

}
