package com.sajsp.system.service.util;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.sajsp.system.entity.CashCount;

public class DailyCashCountUtil {

	public enum AmountType {
		TOTAL, CASH_ON_HAND, NOT_CASH_ON_HAND, MASS_INTENTION, PARTICULARS
	}

	public static BigDecimal computeAmount(List<CashCount> cashCounts, AmountType type) {

		if (cashCounts.isEmpty())
			return new BigDecimal("0");

		Stream<CashCount> stream;
		switch (type) {
		case CASH_ON_HAND:
			stream = cashCounts.stream().filter(CashCount::isInCash);
			break;

		case NOT_CASH_ON_HAND:
			stream = cashCounts.stream().filter(Predicate.not(CashCount::isInCash));
			break;

		case MASS_INTENTION:
			stream = cashCounts.stream().filter(CashCount::isMassIntention);
			break;

		case PARTICULARS:
			stream = cashCounts.stream().filter(Predicate.not(CashCount::isMassIntention));
			break;

		default:
			stream = cashCounts.stream();
			break;
		}

		return stream.map(CashCount::getAmount).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

}
