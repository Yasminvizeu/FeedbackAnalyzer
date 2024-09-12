package com.alura.feedbackAnalyser.controller;


import com.alura.feedbackAnalyser.model.Feedback;
import com.alura.feedbackAnalyser.services.FeedbackService;
import com.alura.feedbackAnalyser.services.LlmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private LlmService llmService;

//    @PostMapping
//    public Feedback classifyFeedback(@RequestBody String feedbackText) {
//        return feedbackService.classifyFeedback(feedbackText);
//    }

    @PostMapping
    public String analyzeFeedback(@RequestBody Map<String, String> payload) {
        String feedback = payload.get("feedback");
        return llmService.analyzeSentiment(feedback);
    }

}
