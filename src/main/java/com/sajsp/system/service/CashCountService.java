package com.sajsp.system.service;

import java.util.Date;

import org.springframework.data.domain.Page;

import com.sajsp.system.exception.CashCountException;
import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.response.CashCountResponse;
import com.sajsp.system.response.DailyCashCountResponse;

public interface CashCountService {
	CashCountResponse addCashCount(CashCountRequest request) throws CashCountException;;

	Page<CashCountResponse> getCashCount(Date date, Integer page, Integer size) throws CashCountException;
	
	DailyCashCountResponse getDailyCashCount(Date date, Integer page, Integer size) throws CashCountException;
}
