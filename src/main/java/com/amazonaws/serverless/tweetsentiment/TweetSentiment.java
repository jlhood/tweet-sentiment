package com.amazonaws.serverless.tweetsentiment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentItemResult;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentRequest;
import com.amazonaws.services.comprehend.model.BatchDetectSentimentResult;
import com.amazonaws.services.comprehend.model.SentimentScore;

import com.google.common.collect.Lists;

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
        List<MetricDatum> metrics = toMetricData(tweetStrings);
        Lists.partition(metrics, 20).forEach(this::putMetricData);
    }

    private void putMetricData(List<MetricDatum> metricData) {
        cloudWatch.putMetricData(new PutMetricDataRequest()
                .withNamespace("TweetSentiment")
                .withMetricData(metricData));
    }

    private List<MetricDatum> toMetricData(List<String> tweetStrings) {
        List<String> tweetText = tweetStrings.stream()
                .map(Tweet::new)
                .map(Tweet::getText)
                .collect(Collectors.toList());

        BatchDetectSentimentResult result = comprehend.batchDetectSentiment(new BatchDetectSentimentRequest()
                .withLanguageCode("en")
                .withTextList(tweetText));

        return result.getResultList().stream()
                .map(this::toSentimentMetrics)
                .flatMap(List::stream)
                .collect(Collectors.toList());

    }

    private List<MetricDatum> toSentimentMetrics(BatchDetectSentimentItemResult sentimentItemResult) {
        List<MetricDatum> metrics = new ArrayList<>();
        SentimentScore sentimentScore = sentimentItemResult.getSentimentScore();

        metrics.add(toSentimentMetricDatum("Mixed", sentimentScore.getMixed().doubleValue()));
        metrics.add(toSentimentMetricDatum("Negative", sentimentScore.getNegative().doubleValue()));
        metrics.add(toSentimentMetricDatum("Neutral", sentimentScore.getNeutral().doubleValue()));
        metrics.add(toSentimentMetricDatum("Positive", sentimentScore.getPositive().doubleValue()));

        return metrics;
    }

    private MetricDatum toSentimentMetricDatum(String sentimentType, Double value) {
        return new MetricDatum()
                .withMetricName("SentimentScore")
                .withDimensions(new Dimension()
                        .withName("SentimentType")
                        .withValue(sentimentType))
                .withValue(value);
    }


}
