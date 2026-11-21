package com.cleancode.ecommerce.recommendation.application.usecase;

import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatResponse;

public interface RecommendationChatService {
	RecommendationChatResponse recommend(String message);
}
