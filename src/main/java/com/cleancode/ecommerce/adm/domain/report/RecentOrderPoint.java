package com.cleancode.ecommerce.adm.domain.report;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record RecentOrderPoint(String orderId, String customerName, String category,
		LocalDateTime date, BigDecimal value, String status) {
}
