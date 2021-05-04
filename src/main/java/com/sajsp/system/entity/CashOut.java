package com.sajsp.system.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cash_out")
@NoArgsConstructor
@Getter
@Setter
public class CashOut extends Base {

	@NotNull
	@Column(name = "description")
	private String description;

	@NotNull
	@Min(1)
	@Column(name = "amount")
	private BigDecimal amount;
}
