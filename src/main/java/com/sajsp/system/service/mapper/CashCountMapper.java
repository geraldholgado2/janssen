package com.sajsp.system.service.mapper;

import org.mapstruct.Mapper;

import com.sajsp.system.entity.CashCount;
import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.response.CashCountResponse;

@Mapper
public interface CashCountMapper {
	CashCount cashCountRequestToCashCount(CashCountRequest cashCountRequest);

	CashCountResponse cashCountToCashCountResponse(CashCount cashCount);
}
