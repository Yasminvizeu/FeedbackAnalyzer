package com.alura.feedbackAnalyser.services;

import com.alura.feedbackAnalyser.model.Feedback;
import com.alura.feedbackAnalyser.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private LlmService llmService;

    public Feedback classifyFeedback(String text) {
        // Call LLMService to get sentiment
        String sentiment = llmService.analyzeSentiment(text);

        // Create Feedback object
        Feedback feedback = new Feedback();
        feedback.setText(text);
        feedback.setSentiment(sentiment);
        // Extract features and set
        feedback.setRequestedFeatures(extractRequestedFeatures(text));

        // Save to repository
        return feedbackRepository.save(feedback);
    }

    private String extractRequestedFeatures(String text) {
        // Implementation for extracting requested features
        return ""; // Example placeholder
    }
}