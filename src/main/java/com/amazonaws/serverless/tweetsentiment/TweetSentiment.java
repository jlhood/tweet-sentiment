package com.amazonaws.serverless.tweetsentiment;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * Core tweet sentiment logic.
 */
@Slf4j
public class TweetSentiment {
    public void publishSentimentMetrics(List<String> tweetStrings) {
        log.info("Received tweets: {}", tweetStrings);
    }
}
