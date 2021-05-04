package com.sajsp.system.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.sajsp.system.validator.ParticularsConstraint;
import com.sajsp.system.validator.RemarksConstraint;

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
@ParticularsConstraint(particulars = "particulars", isMassIntention = "massIntention", message = "Particulars cannot be missing or empty")
@RemarksConstraint(remarks = "remarks", isInCash = "inCash", message = "Remarks cannot be missing or empty")
public class CashCountRequest {

	@NotNull(message = "AR number cannot be missing or empty")
	@Min(1)
	private Long arNumber;

	@NotNull(message = "Amount cannot be missing or empty")
	@Min(1)
	private BigDecimal amount;
	
	private boolean inCash;

	private boolean massIntention;

	private String particulars;

	private String remarks;
}
