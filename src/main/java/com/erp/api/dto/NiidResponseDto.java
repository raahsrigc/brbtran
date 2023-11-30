package com.erp.api.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Getter @Setter @ToString @NoArgsConstructor @JsonInclude(Include.NON_NULL)  @AllArgsConstructor @JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class NiidResponseDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private boolean status;
	private String  message;
	private Object  data;
	private Object  niid;
	private int  kycStatus;
	private int  niidStatus;
	private String  responseCode;

}
