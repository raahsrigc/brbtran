package com.erp.api.dto;

import java.io.Serializable;
import lombok.*;

@Getter @Setter @ToString
public class ChangePasswordTrDto implements Serializable {
	private static final long serialVersionUID = -1324225323392250700L;
	private String userId;
	private String oldPassword;
	private String newPassword;

}
