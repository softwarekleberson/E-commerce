package com.cleancode.ecommerce.adm.domain.report;

import java.math.BigDecimal;

public record SalesByCategoryPoint(String month, String category, BigDecimal total) {
}
