package com.alura.feedbackAnalyser.repository;

import com.alura.feedbackAnalyser.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}