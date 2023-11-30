package com.erp.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor @AllArgsConstructor @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResponseDto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean status;
	private String  message;
	private Object  data;
	private String  responseCode;
	
//	@JsonIgnore
//	private String code;

}
