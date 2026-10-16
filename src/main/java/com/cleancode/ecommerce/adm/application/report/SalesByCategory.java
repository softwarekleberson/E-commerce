package com.cleancode.ecommerce.adm.application.report;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.cleancode.ecommerce.adm.domain.report.SalesByCategoryPoint;

public interface SalesByCategory {

	List<SalesByCategoryPoint> execute(LocalDate startDate, LocalDate endDate);
	SalesDashboard dashboard(LocalDate startDate, LocalDate endDate, Optional<String> category);
}
