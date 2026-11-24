package com.cleancode.ecommerce.recommendation.application.usecase;

import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatResponse;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatFeedbackResponse;

public interface RecommendationChatService {
	RecommendationChatResponse recommend(String message, String userEmail);

	RecommendationChatFeedbackResponse submitFeedback(String responseId, String userEmail, boolean liked);
}
