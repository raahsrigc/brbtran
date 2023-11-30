package com.erp.api.exceptions;

import lombok.Getter;

@Getter
public class ValidationException extends RuntimeException{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String statusCode;
    private String responseMessage;
    private Object responseData;

    public ValidationException(String statusCode, String responseMessage, Object responseData)
    {
        this.statusCode = statusCode;
        this.responseMessage = responseMessage;
        this.responseData = responseData;
    }
}
