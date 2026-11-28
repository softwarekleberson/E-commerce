package com.cleancode.ecommerce.recommendation.application.dto;

import jakarta.validation.constraints.NotNull;

public record RecommendationChatFeedbackRequest(@NotNull Boolean liked) {
}
