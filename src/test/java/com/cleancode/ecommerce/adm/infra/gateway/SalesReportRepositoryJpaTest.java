package com.cleancode.ecommerce.adm.infra.gateway;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

@ExtendWith(MockitoExtension.class)
class SalesReportRepositoryJpaTest {

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Test
	void allSalesReportQueriesOnlyIncludeDeliveredOrders() {
		var repository = new SalesReportRepositoryJpa(jdbcTemplate);
		LocalDate startDate = LocalDate.of(2026, 1, 1);
		LocalDate endDate = LocalDate.of(2026, 1, 31);

		repository.findSalesByCategory(startDate, endDate);
		repository.findSalesByMonthAndCategory(startDate, endDate, null);
		repository.findSalesTotalsByCategory(startDate, endDate, null);
		repository.countDeliveredOrders(startDate, endDate, null);
		repository.findRecentOrders(startDate, endDate, null);

		ArgumentCaptor<String> queryCaptor = ArgumentCaptor.forClass(String.class);
		verify(jdbcTemplate, times(4)).query(queryCaptor.capture(), any(SqlParameterSource.class),
				any(RowMapper.class));
		verify(jdbcTemplate).queryForObject(queryCaptor.capture(), any(SqlParameterSource.class), eq(Long.class));

		List<String> queries = queryCaptor.getAllValues();
		assertThat(queries).hasSize(5).allSatisfy(query -> assertThat(query).contains("o.delivered = TRUE")
				.doesNotContain("o.status = 'PAID'"));
	}
}
