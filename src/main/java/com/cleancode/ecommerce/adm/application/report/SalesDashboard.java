package com.cleancode.ecommerce.adm.application.report;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.cleancode.ecommerce.adm.domain.report.RecentOrderPoint;
import com.cleancode.ecommerce.adm.domain.report.SalesByCategoryPoint;

public record SalesDashboard(LocalDate startDate, LocalDate endDate, String category,
		BigDecimal totalSales, long totalOrders, BigDecimal averageTicket, String leadingCategory,
		List<String> categories, List<SalesByCategoryPoint> salesEvolution,
		List<SalesByCategoryPoint> categoryRevenue, List<RecentOrderPoint> recentOrders) {
}
