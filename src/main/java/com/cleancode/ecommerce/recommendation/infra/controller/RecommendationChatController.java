package com.cleancode.ecommerce.recommendation.infra.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatFeedbackRequest;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatFeedbackResponse;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatRequest;
import com.cleancode.ecommerce.recommendation.application.dto.RecommendationChatResponse;
import com.cleancode.ecommerce.recommendation.application.usecase.RecommendationChatService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer/recommendations/chat")
public class RecommendationChatController {
	private final RecommendationChatService chatService;

	public RecommendationChatController(RecommendationChatService chatService) {
		this.chatService = chatService;
	}

	@PostMapping
	public ResponseEntity<RecommendationChatResponse> recommend(Authentication authentication,
			@Valid @RequestBody RecommendationChatRequest request) {
		return ResponseEntity.ok(chatService.recommend(request.message(), authentication.getName()));
	}

	@PostMapping("/{responseId}/feedback")
	public ResponseEntity<RecommendationChatFeedbackResponse> submitFeedback(Authentication authentication,
			@PathVariable String responseId, @Valid @RequestBody RecommendationChatFeedbackRequest request) {
		return ResponseEntity.ok(
				chatService.submitFeedback(responseId, authentication.getName(), request.liked()));
	}
}
