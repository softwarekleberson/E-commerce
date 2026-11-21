package com.cleancode.ecommerce.recommendation.application.dto;

import java.util.List;

public record RecommendationChatResponse(String answer, List<RecommendationDto> recommendations) {
}
