package com.amazonaws.serverless.tweetsentiment;

import java.util.List;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentRequest;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentResult;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Core tweet sentiment logic.
 */
@Slf4j
@RequiredArgsConstructor
public class TweetSentiment {
    @NonNull
    private final AmazonComprehend comprehend;
    @NonNull
    private final AmazonCloudWatch cloudWatch;

    public void publishSentimentMetrics(List<String> tweetStrings) {
        BatchDetectSentimentResult result = comprehend.batchDetectSentiment(new BatchDetectSentimentRequest()
                .withLanguageCode("en")
                .withTextList("Working with services more and more these days! Saves clients money, scales nearly infinitely, and saves me time. H… https://t.co/LdL6kDxzc1"));

        log.info("Sentiment result: {}", result);
    }
}
