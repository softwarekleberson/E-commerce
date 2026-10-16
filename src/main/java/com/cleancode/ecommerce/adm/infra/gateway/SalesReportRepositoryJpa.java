package com.cleancode.ecommerce.adm.infra.gateway;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.cleancode.ecommerce.adm.domain.report.SalesByCategoryPoint;
import com.cleancode.ecommerce.adm.domain.report.RecentOrderPoint;
import com.cleancode.ecommerce.adm.domain.report.SalesReportRepository;

@Repository
public class SalesReportRepositoryJpa implements SalesReportRepository {

	private static final String SALES_BY_CATEGORY_QUERY = """
			SELECT DATE_FORMAT(o.created_at, '%Y-%m') AS month,
			       p.category AS category,
			       SUM(oi.subtotal) AS total
			FROM tb_orders o
			JOIN tb_order_items oi ON oi.order_id = o.order_id
			JOIN tb_product p ON p.product_id = oi.product_ref_id OR
			     (oi.product_ref_id IS NULL AND p.name = oi.product_id AND
			      (SELECT COUNT(*) FROM tb_product legacy WHERE legacy.name = oi.product_id) = 1)
			WHERE o.status = 'PAID'
			  AND o.created_at >= :startDate
			  AND o.created_at < :endDateExclusive
			GROUP BY DATE_FORMAT(o.created_at, '%Y-%m'), p.category
			ORDER BY month, category
			""";
	private static final String MONTHLY_SALES_QUERY = """
			SELECT DATE_FORMAT(o.created_at, '%Y-%m') AS month, p.category AS category,
			       SUM(oi.subtotal) AS total
			FROM tb_orders o JOIN tb_order_items oi ON oi.order_id = o.order_id
			JOIN tb_product p ON p.product_id = oi.product_ref_id OR
			     (oi.product_ref_id IS NULL AND p.name = oi.product_id AND
			      (SELECT COUNT(*) FROM tb_product legacy WHERE legacy.name = oi.product_id) = 1)
			WHERE o.status = 'PAID' AND o.created_at >= :startDate
			  AND o.created_at < :endDateExclusive
			  AND (:category IS NULL OR p.category = :category)
			GROUP BY DATE_FORMAT(o.created_at, '%Y-%m'), p.category
			ORDER BY month, category
			""";
	private static final String CATEGORY_TOTALS_QUERY = """
			SELECT NULL AS month, p.category AS category, SUM(oi.subtotal) AS total
			FROM tb_orders o JOIN tb_order_items oi ON oi.order_id = o.order_id
			JOIN tb_product p ON p.product_id = oi.product_ref_id OR
			     (oi.product_ref_id IS NULL AND p.name = oi.product_id AND
			      (SELECT COUNT(*) FROM tb_product legacy WHERE legacy.name = oi.product_id) = 1)
			WHERE o.status = 'PAID' AND o.created_at >= :startDate
			  AND o.created_at < :endDateExclusive
			  AND (:category IS NULL OR p.category = :category)
			GROUP BY p.category ORDER BY total DESC
			""";
	private static final String COUNT_ORDERS_QUERY = """
			SELECT COUNT(DISTINCT o.order_id) FROM tb_orders o
			WHERE o.status = 'PAID' AND o.created_at >= :startDate AND o.created_at < :endDateExclusive
			  AND (:category IS NULL OR EXISTS (
	      SELECT 1 FROM tb_order_items oi JOIN tb_product p ON p.product_id = oi.product_ref_id OR
	          (oi.product_ref_id IS NULL AND p.name = oi.product_id AND
	           (SELECT COUNT(*) FROM tb_product legacy WHERE legacy.name = oi.product_id) = 1)
			      WHERE oi.order_id = o.order_id AND p.category = :category))
			""";
	private static final String RECENT_ORDERS_QUERY = """
			SELECT o.order_id AS orderId, c.full_name AS customerName,
			       GROUP_CONCAT(DISTINCT p.category ORDER BY p.category SEPARATOR ', ') AS category,
			       o.created_at AS orderDate, o.value AS orderValue, o.status AS orderStatus
			FROM tb_orders o JOIN tb_customer c ON c.customer_id = o.customer_id
			JOIN tb_order_items oi ON oi.order_id = o.order_id
			JOIN tb_product p ON p.product_id = oi.product_ref_id OR
			     (oi.product_ref_id IS NULL AND p.name = oi.product_id AND
			      (SELECT COUNT(*) FROM tb_product legacy WHERE legacy.name = oi.product_id) = 1)
			WHERE o.created_at >= :startDate AND o.created_at < :endDateExclusive
			  AND (:category IS NULL OR p.category = :category)
			GROUP BY o.order_id, c.full_name, o.created_at, o.value, o.status
			ORDER BY o.created_at DESC LIMIT 10
			""";

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public SalesReportRepositoryJpa(NamedParameterJdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<SalesByCategoryPoint> findSalesByCategory(LocalDate startDate, LocalDate endDate) {
		return salesPoints(SALES_BY_CATEGORY_QUERY, parameters(startDate, endDate, null));
	}

	@Override
	public List<String> findCategories() {
		return jdbcTemplate.query("SELECT DISTINCT category FROM tb_product ORDER BY category",
				(rs, rowNum) -> rs.getString("category"));
	}

	@Override
	public List<SalesByCategoryPoint> findSalesByMonthAndCategory(LocalDate startDate, LocalDate endDate,
			String category) {
		return salesPoints(MONTHLY_SALES_QUERY, parameters(startDate, endDate, category));
	}

	@Override
	public List<SalesByCategoryPoint> findSalesTotalsByCategory(LocalDate startDate, LocalDate endDate,
			String category) {
		return salesPoints(CATEGORY_TOTALS_QUERY, parameters(startDate, endDate, category));
	}

	@Override
	public long countPaidOrders(LocalDate startDate, LocalDate endDate, String category) {
		Long count = jdbcTemplate.queryForObject(COUNT_ORDERS_QUERY, parameters(startDate, endDate, category), Long.class);
		return count == null ? 0 : count;
	}

	@Override
	public List<RecentOrderPoint> findRecentOrders(LocalDate startDate, LocalDate endDate, String category) {
		return jdbcTemplate.query(RECENT_ORDERS_QUERY, parameters(startDate, endDate, category),
				(rs, rowNum) -> new RecentOrderPoint(rs.getString("orderId"), rs.getString("customerName"),
						rs.getString("category"), rs.getTimestamp("orderDate").toLocalDateTime(),
						rs.getBigDecimal("orderValue"), rs.getString("orderStatus")));
	}

	private List<SalesByCategoryPoint> salesPoints(String query, MapSqlParameterSource parameters) {
		return jdbcTemplate.query(query, parameters, (rs, rowNum) -> new SalesByCategoryPoint(
				rs.getString("month"), rs.getString("category"), rs.getBigDecimal("total")));
	}

	private MapSqlParameterSource parameters(LocalDate startDate, LocalDate endDate, String category) {
		return new MapSqlParameterSource().addValue("startDate", Date.valueOf(startDate))
				.addValue("endDateExclusive", Date.valueOf(endDate.plusDays(1))).addValue("category", category);
	}
}
