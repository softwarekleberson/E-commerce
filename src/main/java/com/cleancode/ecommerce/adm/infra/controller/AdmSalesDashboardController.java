package com.cleancode.ecommerce.adm.infra.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.adm.application.report.SalesByCategory;
import com.cleancode.ecommerce.adm.application.report.SalesDashboard;
import com.cleancode.ecommerce.adm.domain.report.SalesByCategoryPoint;

@RestController
@RequestMapping("/adm/dashboard/sales")
@CrossOrigin(origins = "*")
public class AdmSalesDashboardController {

	private final SalesByCategory salesByCategory;

	public AdmSalesDashboardController(SalesByCategory salesByCategory) {
		this.salesByCategory = salesByCategory;
	}

	@GetMapping("/categories")
	public ResponseEntity<List<SalesByCategoryPoint>> getSalesByCategory(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
		if (startDate.isAfter(endDate)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(salesByCategory.execute(startDate, endDate));
	}

	@GetMapping
	public ResponseEntity<SalesDashboard> getDashboard(
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
			@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
			@RequestParam(required = false) Optional<String> category) {
		if (startDate.isAfter(endDate)) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok(salesByCategory.dashboard(startDate, endDate, category));
	}
}
