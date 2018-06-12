package com.amazonaws.serverless.tweetsentiment;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

public class TweetSentimentTest {
    private TweetSentiment tweetSentiment;

    @Before
    public void setUp() throws Exception {
        tweetSentiment = new TweetSentiment();
    }

    @Test
    public void publishSentimentMetrics() throws Exception {
        tweetSentiment.publishSentimentMetrics(Collections.emptyList());
    }

}