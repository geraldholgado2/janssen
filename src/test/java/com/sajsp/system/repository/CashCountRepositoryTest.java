package com.sajsp.system.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.sajsp.system.entity.CashCount;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE, connection = EmbeddedDatabaseConnection.H2)
public class CashCountRepositoryTest {

	@Autowired
	private CashCountRepository repository;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void save_returnsCashCount() {
		CashCount cashCount = new CashCount();
		cashCount.setAmount(new BigDecimal("100"));
		cashCount.setArNumber(123L);
		cashCount.setMassIntention(true);

		CashCount savedCashCount = repository.save(cashCount);
		assertThat(savedCashCount).hasFieldOrPropertyWithValue("arNumber", 123L);
		assertThat(savedCashCount).hasFieldOrPropertyWithValue("uuid", cashCount.getUuid());
	}
}
