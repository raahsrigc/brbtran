package com.erp.api.errorHandlers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.erp.api.dto.ResponseDto;

//@ControllerAdvice
public class FileExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ResponseDto> handleMaxSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        return new ResponseEntity<>(new ResponseDto(false,"File uploaded is too large.",null,"501"), HttpStatus.OK);


    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    public ResponseEntity<ResponseDto> handleFileSizeException(
            MaxUploadSizeExceededException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        return new ResponseEntity<>(new ResponseDto(false,"File uploaded is too large.",null,"501"), HttpStatus.OK);
    }
}

