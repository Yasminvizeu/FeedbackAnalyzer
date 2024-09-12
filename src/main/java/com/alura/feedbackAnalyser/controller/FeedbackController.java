package com.alura.feedbackAnalyser.controller;


import com.alura.feedbackAnalyser.model.Feedback;
import com.alura.feedbackAnalyser.services.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/feedbacks")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping
    public Feedback classifyFeedback(@RequestBody String feedbackText) {
        return feedbackService.classifyFeedback(feedbackText);
    }
}
