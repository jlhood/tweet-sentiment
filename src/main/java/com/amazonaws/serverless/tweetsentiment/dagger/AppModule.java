package com.amazonaws.serverless.tweetsentiment.dagger;

import javax.inject.Singleton;

import com.amazonaws.serverless.tweetsentiment.TweetSentiment;

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
        return new TweetSentiment();
    }
}
