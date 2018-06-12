package com.amazonaws.serverless.tweetsentiment.dagger;

import javax.inject.Singleton;

import com.amazonaws.serverless.tweetsentiment.TweetSentiment;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.comprehend.AmazonComprehend;
import com.amazonaws.services.comprehend.AmazonComprehendClientBuilder;

import dagger.Module;
import dagger.Provides;

/**
 * DI module.
 */
@Module
public class AppModule {
    @Provides
    @Singleton
    public TweetSentiment provideTweetSentiment() {
        AmazonComprehend comprehend = AmazonComprehendClientBuilder.standard().build();
        AmazonCloudWatch cloudWatch = AmazonCloudWatchClientBuilder.standard().build();
        return new TweetSentiment(comprehend, cloudWatch);
    }
}
