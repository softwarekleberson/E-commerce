package com.cleancode.ecommerce.recommendation.infra.persistence;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_recommendation_chat_feedback")
public class RecommendationChatFeedbackEntity {

	@Id
	@Column(name = "response_id")
	private String responseId;

	@Column(name = "user_email", nullable = false)
	private String userEmail;

	private Boolean liked;

	@Column(name = "created_at", insertable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "feedback_at")
	private LocalDateTime feedbackAt;
}
