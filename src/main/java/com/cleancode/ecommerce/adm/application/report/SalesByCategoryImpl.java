package com.cleancode.ecommerce.adm.application.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import com.cleancode.ecommerce.adm.domain.report.SalesByCategoryPoint;
import com.cleancode.ecommerce.adm.domain.report.SalesReportRepository;

public class SalesByCategoryImpl implements SalesByCategory {

	private final SalesReportRepository repository;

	public SalesByCategoryImpl(SalesReportRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<SalesByCategoryPoint> execute(LocalDate startDate, LocalDate endDate) {
		if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("O período informado é inválido.");
		}
		return repository.findSalesByCategory(startDate, endDate);
	}

	@Override
	public SalesDashboard dashboard(LocalDate startDate, LocalDate endDate, Optional<String> category) {
		if (startDate == null || endDate == null || startDate.isAfter(endDate)) {
			throw new IllegalArgumentException("O período informado é inválido.");
		}
		String selectedCategory = category.filter(value -> !value.isBlank()).orElse(null);
		var revenueByCategory = repository.findSalesTotalsByCategory(startDate, endDate, selectedCategory);
		BigDecimal totalSales = revenueByCategory.stream().map(SalesByCategoryPoint::total)
				.reduce(BigDecimal.ZERO, BigDecimal::add);
		long totalOrders = repository.countPaidOrders(startDate, endDate, selectedCategory);
		BigDecimal averageTicket = totalOrders == 0 ? BigDecimal.ZERO
				: totalSales.divide(BigDecimal.valueOf(totalOrders), 2, java.math.RoundingMode.HALF_UP);
		String leadingCategory = revenueByCategory.stream()
				.max((left, right) -> left.total().compareTo(right.total()))
				.map(SalesByCategoryPoint::category).orElse(null);

		return new SalesDashboard(startDate, endDate, selectedCategory, totalSales, totalOrders,
				averageTicket, leadingCategory, repository.findCategories(),
				repository.findSalesByMonthAndCategory(startDate, endDate, selectedCategory),
				revenueByCategory, repository.findRecentOrders(startDate, endDate, selectedCategory));
	}
}
