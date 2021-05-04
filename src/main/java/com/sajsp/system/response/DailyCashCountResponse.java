package com.sajsp.system.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import org.springframework.data.domain.Page;

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
public class DailyCashCountResponse {

	private Page<CashCountResponse> cashCounts;

	private BigDecimal totalAmount;

	private BigDecimal totalAmountCashOnHand;

	private BigDecimal totalAmountNotCashOnHand;

	private BigDecimal totalAmountMassIntention;

	private BigDecimal totalAmountParticulars;

	// TODO: Page<CashoutResponse>
	// TODO: private BigDecimal totalCashOut;

	private Date date;
}
