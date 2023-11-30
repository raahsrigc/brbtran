package com.erp.api.errorHandlers;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;


//@ControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {


    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseDto> validationException(
            ValidationException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        return new ResponseEntity<>(new ResponseDto(false, exc.getResponseMessage(), exc.getResponseData(), exc.getStatusCode()), HttpStatus.OK);


    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDto> exceptions(
            Exception exc,
            HttpServletRequest request,
            HttpServletResponse response) {
        System.out.println(exc.getMessage());
        return new ResponseEntity<>(new ResponseDto(false, "Internal Server error.", null, "501"), HttpStatus.OK);


    }
}
