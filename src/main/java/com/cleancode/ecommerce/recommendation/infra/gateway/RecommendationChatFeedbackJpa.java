package com.cleancode.ecommerce.recommendation.infra.gateway;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cleancode.ecommerce.recommendation.infra.persistence.RecommendationChatFeedbackEntity;

public interface RecommendationChatFeedbackJpa
		extends JpaRepository<RecommendationChatFeedbackEntity, String> {

	Optional<RecommendationChatFeedbackEntity> findByResponseIdAndUserEmail(String responseId, String userEmail);
}
