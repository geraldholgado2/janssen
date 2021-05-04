package com.sajsp.system;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sajsp.system.controller.CashCountController;
import com.sajsp.system.service.CashCountService;
import com.sajsp.system.service.impl.CashCountServiceImpl;

@Configuration
public class CashCountTestConfig {

//	@Bean
//	public CashCountController cashCountController() {
//		return new CashCountController();
//	}

	@Bean
	public CashCountService cashCountService() {
		return new CashCountServiceImpl();
	}

}
