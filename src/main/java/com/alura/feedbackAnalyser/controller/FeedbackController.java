package com.alura.feedbackAnalyser.controller;


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

    public FeedbackController(LlmService llmService) {
        this.llmService = llmService;
    }

    @PostMapping("/analyze")
    public Map<String, Object> analyzeFeedback(@RequestBody String feedbackText) {
        return llmService.processFeedback(feedbackText);
    }


}
