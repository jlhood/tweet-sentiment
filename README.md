## Tweet Sentiment

This serverless app processes events from the [aws-serverless-twitter-event-source](https://github.com/awslabs/aws-serverless-twitter-event-source) serverless app, calls [Amazon Comprehend](https://aws.amazon.com/comprehend/) to do sentiment analysis on the tweet text, and publishes the sentiment scores as custom metrics to [Amazon CloudWatch](https://aws.amazon.com/cloudwatch/) Metrics.

This app was built during an [AWS live coding session on Twitch](https://www.twitch.tv/videos/272408977).

## Architecture

![App Architecture](https://github.com/jlhood/tweet-sentiment/raw/master/images/app-architecture.png)

1. The aws-serverless-twitter-event-source app periodically invokes the TweetSentiment Lambda function to process tweet search results.
1. The TweetSentiment Lambda calls Amazon Comprehend to get sentiment scores on the tweet text and then sends the scores as metrics to CloudWatch Metrics.

## Installation Steps

### Install the tweet-sentiment app

1. [Create an AWS account](https://portal.aws.amazon.com/gp/aws/developer/registration/index.html) if you do not already have one and login
1. Go to this app's page on the [Serverless Application Repository](https://serverlessrepo.aws.amazon.com/applications/arn:aws:serverlessrepo:us-east-1:277187709615:applications~tweet-sentiment) and click "Deploy"
1. Go to the [AWS Lambda Console](http://console.aws.amazon.com/lambda/home) and note down the name of the TweetSentiment function that was created by the deployment.

### Install the aws-serverless-twitter-event-source app

The tweet-sentiment app uses the [aws-serverless-twitter-event-source](https://github.com/awslabs/aws-serverless-twitter-event-source) app as a source of Tweet data. So after deploying the tweet-sentiment app to your account, you need to install the aws-serverless-twitter-event-source app to send twitter events to the tweet-sentiment app.

Refer to the aws-serverless-twitter-event-source [README](https://github.com/awslabs/aws-serverless-twitter-event-source/blob/master/README.md) for general installation steps. There are some parameter settings specific to this app:

1. `TweetProcessorFunctionName` - This should be set to the name of the TweetSentiment function that you noted down when installing the tweet-sentiment app.
1. `BatchSize` - It's fine to use the aws-serverless-twitter-event-source default value of 15.
1. `SearchText` - This controls what tweets are pulled in for sentiment analysis. The search used during the live stream was `#AWSLambda -filter:nativeretweets`. However, you can use whatever Twitter search you'd like to perform sentiment analysis on. Note, if you want to change the SearchText after deploying the app, you can always do this via the AWS Lambda Console by finding the TwitterSearchPoller lambda created by the aws-serverless-twitter-event-source app and changing its `SEARCH_TEXT` environment variable value.

## License Summary

This code is made available under the MIT license. See the LICENSE file.
