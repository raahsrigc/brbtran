package com.erp.api.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dao.ExcelUploadDao;
import com.erp.api.dto.CreditLifeExcelDto;
import com.erp.api.dto.IssuedPolicyDto;
import com.erp.api.dto.PerAccidentExcelDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.helpers.CreditLifeExcelHelper;
import com.erp.api.helpers.PersonalAccExcelHelper;
import com.erp.api.service.BulkUploadService;
import com.erp.api.utility.RestCallUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BulkUploadServiceImpl implements BulkUploadService {
	
	@Autowired
	RestCallUtils kycRestCall;
	
	@Autowired
	ExcelUploadDao excelDao;

	@Override
	public ResponseDto creditLifeSave(MultipartFile file, String token, String sessionId, boolean b2bBulk) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (!CreditLifeExcelHelper.hasExcelFormat(file)) {
                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "File should be of proper format.");
            }
            String fileName = file.getOriginalFilename();
            log.info("---------------------"+fileName);
            kycRestCall.uploadExcelFile(file, token,"creditLife");
            
            List<CreditLifeExcelDto> bulkFileModel = CreditLifeExcelHelper.creditLifeExcelUpload(file.getInputStream());
            if (bulkFileModel.size() == 0) {

                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "Invalid file.");

            }
            excelDao.uploadFileQuotation(bulkFileModel, token, sessionId,b2bBulk,responseDto);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        } catch (IllegalStateException e1) {
        	e1.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setData(e1.getMessage());
            responseDto.setResponseCode("201");
        } catch (NullPointerException e3) {
        	e3.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setResponseCode("201");
        }
        return responseDto;
    }

	@Override
	public ResponseDto personalAccidentSave(MultipartFile file, String token, String sessionId, boolean b2bBulk) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (!PersonalAccExcelHelper.hasExcelFormat(file)) {
                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "File should be of proper format.");
            }
            String fileName = file.getOriginalFilename();
            log.info("---------------------"+fileName);
            kycRestCall.uploadExcelFile(file, token,"personalAccident");
            
            List<PerAccidentExcelDto> bulkFileModel = PersonalAccExcelHelper.creditLifeExcelUpload(file.getInputStream());
            if (bulkFileModel.size() == 0) {

                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "Invalid file.");

            }
            excelDao.uploadFilePersonalAccident(bulkFileModel, token, sessionId,b2bBulk,responseDto);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        } catch (IllegalStateException e1) {
        	e1.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setData(e1.getMessage());
            responseDto.setResponseCode("201");
        } catch (NullPointerException e3) {
        	e3.printStackTrace();
            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setResponseCode("201");
        }
        return responseDto;
    }

	@Override
	public ResponseDto getCreditBatchList(IssuedPolicyDto fatchDto, String token, String sessionId, boolean b2bList) {
		return excelDao.getCreditBatchList(fatchDto,token,sessionId,b2bList, new ResponseDto());
	}

	@Override
	public ResponseDto getBatchPolicyList(String batchId, String token, String sessionId, boolean b2bList,IssuedPolicyDto issueDto) {
		return excelDao.getBatchPolicyList(batchId,token,sessionId,b2bList,issueDto,new ResponseDto());
	}

}
