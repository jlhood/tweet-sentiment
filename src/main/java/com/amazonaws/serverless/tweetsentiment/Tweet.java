package com.amazonaws.serverless.tweetsentiment;

import org.json.JSONObject;

/**
 * Encapsulates parsing of tweet.
 */
public class Tweet {
    private final JSONObject json;

    public Tweet(String tweetJsonString) {
        json = new JSONObject(tweetJsonString);
    }

    public String getText() {
        return json.getString("text");
    }
}
