package com.erp.api.utility;

import java.io.IOException;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.erp.api.dto.MultipartInputStreamFileResource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RestCallUtils {

    @Autowired
    private RestTemplate restTemplate;

   
    
    @Value("${device.upload.excel.api}")
	private String uploadExcel;
    

	public ResponseEntity<String> uploadExcelFile(MultipartFile file, String token, String folderName) throws IOException {
		LocalTime time = LocalTime.now();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.set("token", token);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", new MultipartInputStreamFileResource(file.getInputStream(), file.getOriginalFilename()));
		if("personalAccident".equalsIgnoreCase(folderName)) {
			body.add("fileName", "uploadexcelpersonalAccident/"+file.getOriginalFilename().replace(".xlsx", "")+time);
		}else {
			body.add("fileName", "uploadexcelcreditlife/"+file.getOriginalFilename().replace(".xlsx", "")+time);
		}
		log.info("----------------------"+file.getOriginalFilename()+time);
		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		return restTemplate.exchange(uploadExcel, HttpMethod.POST, requestEntity, String.class);
	}

}
