package com.cleancode.ecommerce.event.replacement;

import java.math.BigDecimal;

public record ReplacementEvent(String customerId, BigDecimal value) {

}
