package com.erp.api.serviceImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.async.BulkKycStatus;
import com.erp.api.dao.FilesDao;
import com.erp.api.dto.NaiComDBResposeDto;
import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.erp.api.helpers.ExcelHelper;
import com.erp.api.models.BulkFileModel;
import com.erp.api.models.GetKycStatusModel;
import com.erp.api.service.FileService;
import com.erp.api.utils.KYCRestCall;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FilesDao filesDao;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    KYCRestCall kycRestCall;

    @Autowired
    BulkKycStatus bulkKycStatus;

    @Override
    public ResponseDto save(MultipartFile file, String token) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (!ExcelHelper.hasExcelFormat(file)) {
                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "File should be of proper format.");
            }
            List<BulkFileModel> bulkFileModel = ExcelHelper.excelToBulkFileModel(file.getInputStream());
            if (bulkFileModel.size() == 0) {

                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "Invalid file.");

            }
            filesDao.uploadFile(bulkFileModel, token, responseDto);

            if (responseDto.isStatus()) {

                ArrayList<GetKycStatusModel> list = objectMapper.readValue(responseDto.getData().toString(), new TypeReference<ArrayList<GetKycStatusModel>>() {
                });
                if (!Objects.isNull(list) && list.size() != 0) {
                    responseDto.setData(null);
                    bulkKycStatus.checkKycStatus(token, list);
                }


            } else {
                responseDto.setData(objectMapper.readValue(responseDto.getData().toString(), Object.class));

            }


        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
////        catch (ValidationException e2) {
////
////            responseDto.setResponseCode(e2.getStatusCode());
////            responseDto.setMessage(e2.getResponseMessage());
////            responseDto.setData(e2.getResponseData());
//
//        }
        catch (IllegalStateException e1) {

            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setData(e1.getMessage());
            responseDto.setResponseCode("201");


        } catch (NullPointerException e3) {
            responseDto.setStatus(false);
            responseDto.setMessage("Validation failed for the uploaded excel Sheet.");
            responseDto.setResponseCode("201");
        }
        return responseDto;
    }

	@Override
	public ResponseDto saveQuotation(MultipartFile file, String token) {
        ResponseDto responseDto = new ResponseDto();
        try {
            if (!ExcelHelper.hasExcelFormat(file)) {
                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "File should be of proper format.");
            }
            String fileName = file.getOriginalFilename();
            System.out.println("---------------------"+fileName);
            ResponseEntity<String> generateDoc = kycRestCall.uploadExcelFile(file, token);
            
            NaiComDBResposeDto responseeeDto = new NaiComDBResposeDto();
            responseeeDto=filesDao.getKycStatus(token,responseeeDto);
            
            List<BulkFileModel> bulkFileModel = ExcelHelper.excelToBulkFileModelQuotation(file.getInputStream(),token,responseeeDto);
            if (bulkFileModel.size() == 0) {

                throw new ValidationException("201", "Validation failed for the uploaded excel Sheet.", "Invalid file.");

            }
            filesDao.uploadFileQuotation(bulkFileModel, token, responseDto);

        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        } catch (IllegalStateException e1) {
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
	public ResponseDto makePaymentDetailsTr(long batchId, String token) {
		ResponseDto responseDto = new ResponseDto();
		return filesDao.makePaymentDetailsTr(token, batchId,responseDto);
	}


}
