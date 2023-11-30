package com.erp.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Getter@Setter@ToString
public class PaymentWalletTrDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private long quotationId;
	private boolean isBulk;
	private String batchNo;
	private BigDecimal amount;
	private String profileEmail;
	private boolean isWallet;
	private String userId;
}
