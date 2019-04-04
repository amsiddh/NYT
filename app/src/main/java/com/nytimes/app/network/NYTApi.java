package com.nytimes.app.network;

/**
 * Created by sbingi on 3/31/2019.
 */
public class NYTApi {

    static final String BASE_URL = "https://api.nytimes.com";

    static final String MOST_POPULAR_ARTICLES = "svc/mostpopular/v2/viewed/{"+Params.TIME_PERIOD+"}.json?api-key=PjUokAvcv0kEnaEbXZY3nyLKXApBIdO6";

    static class Params {
        static final String TIME_PERIOD = "timePeriod";
    }


    public static class ErrorCode {
        public static final int UNAUTHORIZED = 404;
        public static final int LIMIT_REACHED = 429;
    }
}
