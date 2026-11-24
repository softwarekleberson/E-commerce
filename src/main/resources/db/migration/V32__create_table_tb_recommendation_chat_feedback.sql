CREATE TABLE tb_recommendation_chat_feedback (
    response_id VARCHAR(36) PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    liked BOOLEAN NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    feedback_at TIMESTAMP NULL
);

CREATE INDEX idx_recommendation_chat_feedback_user_email
    ON tb_recommendation_chat_feedback(user_email);
