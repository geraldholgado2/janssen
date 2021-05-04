package com.sajsp.system.service.impl;

import static com.sajsp.system.service.util.DailyCashCountUtil.AmountType.*;
import static com.sajsp.system.service.util.DailyCashCountUtil.computeAmount;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.sajsp.system.entity.CashCount;
import com.sajsp.system.exception.CashCountException;
import com.sajsp.system.repository.CashCountRepository;
import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.response.CashCountResponse;
import com.sajsp.system.response.DailyCashCountResponse;
import com.sajsp.system.service.CashCountService;
import com.sajsp.system.service.mapper.CashCountMapper;
import com.sajsp.system.service.specification.CashCountSpecs;

import lombok.extern.log4j.Log4j2;

@Log4j2(topic = "cash-count-service")
@Service
public class CashCountServiceImpl implements CashCountService {

	public static final String AR_NUMBER_ALREADY_EXISTS_ERR_MSG = "AR Number already exists";

	@Autowired
	CashCountRepository cashCountRepository;

	private CashCountMapper mapper = Mappers.getMapper(CashCountMapper.class);

	@Override
	public CashCountResponse addCashCount(CashCountRequest request) throws CashCountException {
		log.info("processing addCashCount... ");

		if (cashCountRepository.existsByArNumber(request.getArNumber())) {
			log.error(AR_NUMBER_ALREADY_EXISTS_ERR_MSG);
			throw new CashCountException(AR_NUMBER_ALREADY_EXISTS_ERR_MSG);
		}

		CashCount cashCount = mapper.cashCountRequestToCashCount(request);
		log.info("addCashCount -> saving cash count -> arNumber : {} ...", cashCount.getArNumber());
		CashCount savedCashCount = cashCountRepository.save(cashCount);
		log.info("Leaving addCashCount ...");
		return mapper.cashCountToCashCountResponse(savedCashCount);
	}

	@Override
	public Page<CashCountResponse> getCashCount(Date date, Integer page, Integer size) throws CashCountException {
		log.info("processing getCashCount... ");
		date = date == null ? new Date() : date;
//		page = page - 1 < 0 ? 0 : page - 1;
		size = size == null || size == 0 ? 10 : size;
		Pageable pageable = PageRequest.of(page, size);
		Page<CashCount> cashCountPg = cashCountRepository.findAll(buildSpecs(date), pageable);

		List<CashCountResponse> cashCountList = new ArrayList<>();
		Page<CashCountResponse> cashCountResponsePage = new PageImpl<>(cashCountList);
		if (cashCountPg != null) {
			Iterable<CashCount> cashCountIterable = cashCountPg;
			cashCountIterable.forEach(cashCount -> {
				CashCountResponse resp = mapper.cashCountToCashCountResponse(cashCount);
				cashCountList.add(resp);
			});
			cashCountResponsePage = new PageImpl<>(cashCountList, cashCountPg.getPageable(),
					cashCountPg.getTotalElements());
			log.info("Found [{}] Cash Counts", cashCountList.size());
		}
		return cashCountResponsePage;
	}

	private Specification<CashCount> buildSpecs(Date date) {
		Specification<CashCount> specs = CashCountSpecs.initialize();
		if (Objects.nonNull(date)) {
			specs = specs.and(CashCountSpecs.dateIsBetween(setTime(date, 0, 0, 0, 0), setTime(date, 23, 59, 59, 59)));
		}

		return specs;
	}

	private Date setTime(final Date date, final int hourOfDay, final int minute, final int second, final int ms) {
		final GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, hourOfDay);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, second);
		gc.set(Calendar.MILLISECOND, ms);
		return gc.getTime();
	}
	
	@Override
	public DailyCashCountResponse getDailyCashCount(Date date, Integer page, Integer size) throws CashCountException {
		log.info("processing getDailyCashCount... ");
		DailyCashCountResponse response = new DailyCashCountResponse();
		response.setCashCounts(getCashCount(date, page, size));
		List<CashCount> cashCountPg = cashCountRepository.findAll(buildSpecs(date));
		response.setTotalAmount(computeAmount(cashCountPg, TOTAL));
		response.setTotalAmountCashOnHand(computeAmount(cashCountPg, CASH_ON_HAND));
		response.setTotalAmountNotCashOnHand(computeAmount(cashCountPg, NOT_CASH_ON_HAND));
		response.setTotalAmountMassIntention(computeAmount(cashCountPg, MASS_INTENTION));
		response.setTotalAmountParticulars(computeAmount(cashCountPg, PARTICULARS));
		log.info("leaving getDailyCashCount... ");
		return response;
	}

}
