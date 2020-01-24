package com.rahul.twitter.producer;

import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class TwitterProducer {
    private static Logger logger = LoggerFactory.getLogger(TwitterProducer.class);

    private static String apiKey = "";
    private static String apiSecret = "";
    private static String accessToken = "";
    private static String accessTokenSecret = "";

    List<String> terms = Lists.newArrayList("#nifty");
    List<Long> followings = Lists.newArrayList();

    public TwitterProducer() {
    }

    public static void main(String[] args) {
        apiKey = System.getProperty("apiKey");
        apiSecret = System.getProperty("apiSecret");
        accessToken = System.getProperty("accessToken");
        accessTokenSecret = System.getProperty("accessTokenSecret");
        if (apiKey == null || apiSecret == null || accessToken == null | accessTokenSecret == null) {
            logger.error("apiKey/apiSecret/accessToken/accessTokenSecret cannot be null. Exiting");
            return;
        }

        new TwitterProducer().run();
    }

    /*
        1. Create a twitter client
        2. Create a kafka producer
        3. Loop to send tweets to kafka
    */
    public void run() {
        BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(1000);
        Client client = createTwitterClient(msgQueue);

        try {
            client.connect();
            //TODO: Create a kafka producer

            while (!client.isDone()) {
                String msg = null;
                try {
                    msg = msgQueue.poll(2, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    logger.error("ERROR: ", e);
                }
                if (msg != null) {
                    logger.info(msg);
                }
            }
        } finally {
            client.stop();
        }
    }

    /**
     * Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth)
     */
    public Client createTwitterClient(BlockingQueue<String> msgQueue) {
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(terms);
//        endpoint.followings(followings);

        return new ClientBuilder()
                .name("Hosebird-Client-01")
                .hosts(Constants.STREAM_HOST)
                .endpoint(endpoint)
                .authentication(new OAuth1(apiKey, apiSecret, accessToken, accessTokenSecret))
                .processor(new StringDelimitedProcessor(msgQueue))
                .build();
    }
}
