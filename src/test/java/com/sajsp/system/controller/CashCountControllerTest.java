package com.sajsp.system.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sajsp.system.exception.CashCountException;
import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.service.CashCountService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CashCountController.class)
public class CashCountControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CashCountService cashCountService;

	@Test
	@DisplayName("Should check addCashCount success")
	public void test_AddCashCountPOST_Success() throws Exception, CashCountException {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(123L)
				.massIntention(true).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-counts").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isOk());
	}

	@Test
	@DisplayName("Should check addCashCount returns 400 when arNumber is null")
	public void testWhen_ArNumberGivenNull_AddCashCountPOST() throws Exception, CashCountException {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(null)
				.massIntention(true).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-counts").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should check addCashCount returns 400 when arNumber is zero")
	public void testWhen_ArNumberGivenZero_AddCashCountPOST() throws Exception, CashCountException {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(0L)
				.massIntention(true).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-counts").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should check addCashCount returns 400 when amount is null")
	public void testWhen_AmountGivenNull_AddCashCountPOST() throws Exception, CashCountException {
		CashCountRequest request = CashCountRequest.builder().amount(null).arNumber(123L).massIntention(true).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-counts").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Should check addCashCount returns 400 when amount is zero")
	public void testWhen_AmountGivenZero_AddCashCountPOST() throws Exception, CashCountException {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("0")).arNumber(123L)
				.massIntention(true).build();
		mockMvc.perform(MockMvcRequestBuilders.post("/cash-counts").contentType("application/json")
				.content(objectMapper.writeValueAsString(request))).andExpect(status().isBadRequest());
	}
}
