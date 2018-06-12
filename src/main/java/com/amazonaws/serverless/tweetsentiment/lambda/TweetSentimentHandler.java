package com.amazonaws.serverless.tweetsentiment.lambda;

import java.util.List;

import com.amazonaws.serverless.tweetsentiment.TweetSentiment;
import com.amazonaws.serverless.tweetsentiment.dagger.AppComponent;
import com.amazonaws.serverless.tweetsentiment.dagger.DaggerAppComponent;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

/**
 * Lambda entrypoint.
 */
public class TweetSentimentHandler implements RequestHandler<List<String>, Void> {
    private final TweetSentiment tweetSentiment;

    public TweetSentimentHandler() {
        AppComponent component = DaggerAppComponent.create();
        tweetSentiment = component.tweetSentiment();
    }

    @Override
    public Void handleRequest(List<String> tweets, Context context) {
        tweetSentiment.publishSentimentMetrics(tweets);
        return null;
    }
}
