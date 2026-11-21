package com.cleancode.ecommerce.recommendation.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RecommendationChatRequest(@NotBlank @Size(max = 2000) String message) {
}
