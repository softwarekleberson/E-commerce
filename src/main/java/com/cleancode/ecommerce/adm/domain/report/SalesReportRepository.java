package com.cleancode.ecommerce.adm.domain.report;

import java.time.LocalDate;
import java.util.List;

public interface SalesReportRepository {

	List<SalesByCategoryPoint> findSalesByCategory(LocalDate startDate, LocalDate endDate);
	List<String> findCategories();
	List<SalesByCategoryPoint> findSalesByMonthAndCategory(LocalDate startDate, LocalDate endDate, String category);
	List<SalesByCategoryPoint> findSalesTotalsByCategory(LocalDate startDate, LocalDate endDate, String category);
	long countPaidOrders(LocalDate startDate, LocalDate endDate, String category);
	List<RecentOrderPoint> findRecentOrders(LocalDate startDate, LocalDate endDate, String category);
}
