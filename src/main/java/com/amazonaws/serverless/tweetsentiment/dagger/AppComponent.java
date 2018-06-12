package com.amazonaws.serverless.tweetsentiment.dagger;

import javax.inject.Singleton;

import com.amazonaws.serverless.tweetsentiment.TweetSentiment;

import dagger.Component;
import dagger.Module;

/**
 * DI interface.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    TweetSentiment tweetSentiment();
}
