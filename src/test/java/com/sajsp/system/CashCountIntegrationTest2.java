package com.sajsp.system;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sajsp.system.request.CashCountRequest;
import com.sajsp.system.response.CashCountResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CashCountIntegrationTest2 {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	@DisplayName("Tests happy path of addCashCount POST method")
	public void testWhen_Success_AddCashCountPOST() {
		// arrange
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(1L)
				.massIntention(true).build();

		// act
		ResponseEntity<CashCountResponse> response = testRestTemplate.postForEntity("/cash-counts", request,
				CashCountResponse.class);

		// assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody().getUuid());
		assertEquals(123L, response.getBody().getArNumber());
	}

	@Test
	@DisplayName("Should check addCashCount returns 400 when ar number already exists")
	public void testWhen_ARNumberAlreadyExists_AddCashCountPOST() {
		// arrange
		CashCountRequest request = CashCountRequest.builder().amount(new BigDecimal("100")).arNumber(2L)
				.massIntention(true).build();

		// act
		testRestTemplate.postForEntity("/cash-counts", request, CashCountResponse.class);

		ResponseEntity<CashCountResponse> response2 = testRestTemplate.postForEntity("/cash-counts", request,
				CashCountResponse.class);

		// assert
		assertEquals(HttpStatus.BAD_REQUEST, response2.getStatusCode());
	}

//	@Test
//	@DisplayName("Tests happy path of getCashCountByDate GET method")
//	public void testWhen_Success_GetCashCountByDateGET() {
//		// arrange
//		Date date = new Date();
//		// act
//		ResponseEntity<?> response = testRestTemplate.getForEntity(String.format("/cash-counts/%s", date.toString()),
//				ResponseEntity.class);
//		// assert
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//	}
}
