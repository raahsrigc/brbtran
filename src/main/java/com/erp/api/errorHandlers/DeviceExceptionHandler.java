package com.erp.api.errorHandlers;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import com.erp.api.dto.ResponseDto;
import com.erp.api.exceptions.ValidationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;


@ControllerAdvice
public class DeviceExceptionHandler {
	
//	@Autowired
//    LoggingService loggingService;
	
	
	@Autowired
	ObjectMapper objectMapper;
	
	
	
	
	  @Autowired
	   // LoggingServiceDao dao;

	private static final Logger LOGGER=LoggerFactory.getLogger(DeviceExceptionHandler.class);

	  
	  
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

	
    @ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ResponseEntity<ResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, WebRequest request) {
       
    	BindingResult result = ex.getBindingResult();
        LOGGER.info("request{}",request.getHeader("token"));
         ServletWebRequest swr=(ServletWebRequest) request;
         LOGGER.info("URI{}",swr.getRequest().getRequestURI());
         LOGGER.info("service{}","TG_1100");




    
        List<String> errorList = new ArrayList<>();
        result.getFieldErrors().forEach((fieldError) -> {
        	errorList.add(fieldError.getObjectName()+"."+fieldError.getField()+" : " +fieldError.getDefaultMessage() +" : rejected value [" +fieldError.getRejectedValue() +"]" );
        //	errorList.add(fieldError.getField().split(".")[fieldError.getField().lastIndexOf(".")]+" : " +fieldError.getDefaultMessage() +" :(rejected value [" +fieldError.getRejectedValue() +"])" );

        });
//        result.getGlobalErrors().forEach((fieldError) -> {
//        	errorList.add(fieldError.getObjectName().toString()+" : " +fieldError.getDefaultMessage() );
//        });
        String errorListString=String.join(",", errorList);
        
      //  Boolean resp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107",  new ResponseDto(false, errorListString,null , "TG_1101").toString());

        
        return new ResponseEntity<ResponseDto>(new ResponseDto(false, errorListString,null , "TG_602"), HttpStatus.BAD_REQUEST);

    }
    @ExceptionHandler(HttpStatusCodeException.class)
   	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
       @ResponseBody
       public ResponseEntity<ResponseDto> handleIOException(HttpStatusCodeException ex, WebRequest request) {
        ServletWebRequest swr=(ServletWebRequest) request;
 
    	ResponseDto resp;
		try {
			resp = objectMapper.readValue(ex.getResponseBodyAsString(), ResponseDto.class);
	         LOGGER.info("resp from api {}",resp.toString());

//	        Boolean booleanResp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107",  resp.toString());
//	        if(resp.getData()==null)
//	        {
//		           return new ResponseEntity<ResponseDto>(resp,ex.getStatusCode() );
//	
//	        }
	        Map map = objectMapper.readValue(resp.getData().toString(), Map.class);
	        resp.setData(map);
	           return new ResponseEntity<ResponseDto>(resp,ex.getStatusCode() );

		} catch (JsonMappingException e) {
	         LOGGER.info("Error occured is {}",e.getMessage());

			// TODO Auto-generated catch block
	      //  Boolean booleanResp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107",  new ResponseDto(false, ex.getMessage(),null , "TG_1102").toString());

		      return new ResponseEntity<ResponseDto>(new ResponseDto(false, ex.getMessage(),null , "TG_1102"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (JsonProcessingException e) {
	         LOGGER.info("Error occured is {}",e.getMessage());

			// TODO Auto-generated catch block
	    //    Boolean booleanResp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107",  new ResponseDto(false, ex.getMessage(),null , "TG_1102").toString());

		      return new ResponseEntity<ResponseDto>(new ResponseDto(false, ex.getMessage(),null , "TG_1102"), HttpStatus.INTERNAL_SERVER_ERROR);
		}


       }

    @ExceptionHandler(HttpMessageNotReadableException.class)
   	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
       @ResponseBody
       public ResponseEntity<ResponseDto> handleInvalidFormatException(InvalidFormatException ex, WebRequest request) {
        ServletWebRequest swr=(ServletWebRequest) request;
 
    	ResponseDto resp;
	        LOGGER.info("error occured {}{}",ex.getMessage(),ex.getCause());

	       // Boolean booleanResp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107", "Error Occured while Processing the request.Please verify the input fields");

		      return new ResponseEntity<ResponseDto>(new ResponseDto(false, "Error Occured while Processing the request.Please verify the input fields"
,null , "TG_1102"), HttpStatus.BAD_REQUEST);



       }
    
    
	@ExceptionHandler({ ConstraintViolationException.class })
	public ResponseEntity<ResponseDto> handleConstraintViolation(ConstraintViolationException ex, WebRequest request) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			errors.add(violation.getRootBeanClass().getName() + " " + violation.getPropertyPath() + ": "
					+ violation.getMessage());
		}

		
		
        String errorListString=String.join(",", errors);

        return new ResponseEntity<ResponseDto>(new ResponseDto(false, errorListString,null , "TG-602"), HttpStatus.BAD_REQUEST);

		// If you want to throw apiError directly, uncomment this
		//return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
	}
	
	
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ResponseDto> validationException(
            ValidationException exc,
            HttpServletRequest request,
            HttpServletResponse response) {

        return new ResponseEntity<>(new ResponseDto(false, exc.getResponseMessage(), exc.getResponseData(), exc.getStatusCode()), HttpStatus.OK);


    }
	
	
    @ExceptionHandler(Exception.class)
   	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
       @ResponseBody
       public ResponseEntity<ResponseDto> handleAllException(Exception ex, WebRequest request) {
        ServletWebRequest swr=(ServletWebRequest) request;
        String exceptionMessage="";
        ex.printStackTrace();
    	ResponseDto resp;
	        LOGGER.info("error occured {}",ex.getMessage());
	        
	        ex.printStackTrace();
if(request.getHeader("Authorization")==null) {
	// Boolean booleanResp=dao.logRequestResponse(null, swr.getRequest().getRequestURI(), "107", "Authorization/Bearer Token can not be null");
	 
     return new ResponseEntity<ResponseDto>(new ResponseDto(false,  ex.getMessage(),null , "TG_1102"), HttpStatus.BAD_REQUEST);

}

	   //     Boolean booleanResp=dao.logRequestResponse(request.getHeader("Authorization"), swr.getRequest().getRequestURI(), "107", "Some Error Occured .Please Contact Technical Team ");

		      return new ResponseEntity<ResponseDto>(new ResponseDto(false, "Some Error Occured .Please Contact Technical Team ",null , "TG_1102"), HttpStatus.INTERNAL_SERVER_ERROR);



       }
    
    


    
    
    
    

	

  
}
