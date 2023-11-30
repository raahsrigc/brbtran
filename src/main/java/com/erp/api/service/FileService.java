package com.erp.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dto.ResponseDto;

@Service
public interface FileService {

    ResponseDto save(MultipartFile file,String token);

	ResponseDto saveQuotation(MultipartFile file, String token);

	ResponseDto makePaymentDetailsTr(long batchId, String token);



}
