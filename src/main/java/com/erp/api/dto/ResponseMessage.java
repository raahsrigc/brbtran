package com.erp.api.dto;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.ToString;

@Component
@JsonInclude(Include.NON_NULL)
@ToString
public class ResponseMessage {
	private boolean success;
	private String message;
	private Object data;
	private Object meta;
	private String errorCode;
	private String responseCode;

	private static final long serialVersionUID = 1L;	
	
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public Object getMeta() {
		return meta;
	}
	public void setMeta(Object meta) {
		this.meta = meta;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	
	public ResponseMessage() {
	}
	public ResponseMessage(boolean success, String message) {
		this();
		this.success = success;
		this.message = message;
	}
	public ResponseMessage(boolean success, String message, Object data) {
		this.success = success;
		this.message = message;
		this.data = data;
	}
	
	
	

}
