package com.sajsp.system.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashCountResponse {

	private String uuid;

	private Long arNumber;

	private BigDecimal amount;

	private boolean inCash;

	private boolean massIntention;

	private String particulars;

	private String remarks;

	private Date createdAt;
}
