package com.sajsp.system.service.specification;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

public class CashCountSpecs<T> {

	private static final String DATE = "createdAt";

	public static <T> Specification<T> initialize() {
		return (root, query, cb) -> {
			return cb.and();
		};
	}

	public static <T> Specification<T> dateIsBetween(Date fromDate, Date toDate) {
		return (root, query, cb) -> {

			return cb.and(cb.greaterThanOrEqualTo(root.get(DATE), fromDate), cb.lessThan(root.get(DATE), toDate)
					);
//			if (fromDate == null && toDate != null) {
//				return cb.lessThanOrEqualTo(root.get(DATE), toDate);
//			} else if (fromDate != null && toDate == null) {
//				return cb.greaterThanOrEqualTo(root.get(DATE), fromDate);
//			} else {
//				return cb.and(cb.lessThanOrEqualTo(root.get(DATE), toDate),
//						cb.greaterThanOrEqualTo(root.get(DATE), fromDate));
//			}
		};
	}
}
