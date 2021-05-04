package com.sajsp.system.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cash_count")
@NoArgsConstructor
@Getter
@Setter
public class CashCount extends Base {

	@Column(name = "ar_number", unique = true)
	private Long arNumber;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "is_in_cash")
	private boolean inCash;

	@Column(name = "is_mass_intention")
	private boolean massIntention;

	@Column(name = "particulars")
	private String particulars;

	@Column(name = "remarks")
	private String remarks;
}
