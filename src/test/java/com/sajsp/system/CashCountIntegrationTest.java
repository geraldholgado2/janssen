package com.sajsp.system;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sajsp.system.entity.CashCount;
import com.sajsp.system.repository.CashCountRepository;
import com.sajsp.system.request.CashCountRequest;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
@SpringBootTest(webEnvironment = RANDOM_PORT, classes = SystemApplication.class)
public class CashCountIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CashCountRepository cashCountRepository;

	@AfterEach
	public void resetDb() {
		cashCountRepository.deleteAll();
	}

	@Test
	@DisplayName("Tests happy path of addCashCount POST method")
	public void whenValidInput_thenAddCashCount() throws IOException, Exception {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(1L)
				.massIntention(true).inCash(true).build();
		mockMvc.perform(post("/api/system/cash-counts").contentType(MediaType.APPLICATION_JSON).content(toJson(request))).andDo(print());

		List<CashCount> found = cashCountRepository.findAll();
		assertThat(found).extracting(CashCount::getArNumber).containsOnly(1L);
	}

	@Test
	@DisplayName("Tests addCashCount with null particulars POST method")
	public void whenInValidInputParticulars_thenAddCashCountFail() throws IOException, Exception {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(1L)
				.massIntention(false).particulars(null).build();
		mockMvc.perform(post("/api/system/cash-counts").contentType(MediaType.APPLICATION_JSON).content(toJson(request))).andDo(print())
				.andExpect(status().isBadRequest());
	}

	@Test
	@DisplayName("Tests addCashCount with valid particulars POST method")
	public void whenValidInputParticulars_thenAddCashCountSuccess() throws IOException, Exception {
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(1L)
				.massIntention(false).particulars("PLEDGE").inCash(true).build();
		mockMvc.perform(post("/api/system/cash-counts").contentType(MediaType.APPLICATION_JSON).content(toJson(request))).andDo(print())
				.andExpect(status().isOk());
	}
	
	@Test
	@DisplayName("Tests happy path of getCashCountByDate GET method")
	public void testWhen_Success_GetCashCountByDateGET() throws Exception {
		createCashCount(1L);
		Date dateToday = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(dateToday);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/system/cash-counts/" + date + "?page=0&size=5")).andDo(print())
				.andExpect(status().isOk())

				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.numberOfElements", is(1)));
	}
	
	
	@Test
	@DisplayName("Tests happy path of getDailyCashCountByDate GET method")
	public void testWhen_Success_GetDailyCashCountByDateGET() throws Exception {
		createCashCount(1L);
		createCashCount(2L);
		Date dateToday = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(dateToday);
		mockMvc.perform(MockMvcRequestBuilders.get("/api/system/daily-cash-counts/" + date + "?page=0&size=5")).andDo(print())
				.andExpect(status().isOk())

				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.totalAmount", is(200.0)));
	}

	private void createCashCount(Long arNumber) {
		CashCount cashCount = new CashCount();
		cashCount.setAmount(new BigDecimal("100"));
		cashCount.setArNumber(arNumber);
		cashCount.setMassIntention(true);
		cashCount.setInCash(true);
		cashCountRepository.save(cashCount);
	}

	private static byte[] toJson(Object object) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

}
