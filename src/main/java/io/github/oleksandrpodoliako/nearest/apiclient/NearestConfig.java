package io.github.oleksandrpodoliako.nearest.apiclient;

import io.github.oleksandrpodoliako.nearest.enums.RequestLoggingLevel;
import io.github.oleksandrpodoliako.nearest.enums.ResponseLoggingLevel;

public class NearestConfig {

    private static RequestLoggingLevel requestLogging = RequestLoggingLevel.NONE;
    private static ResponseLoggingLevel responseLogging = ResponseLoggingLevel.NONE;

    public static RequestLoggingLevel getRequestLogging() {
        return requestLogging;
    }

    public static void setRequestLogging(RequestLoggingLevel requestLogging) {
        NearestConfig.requestLogging = requestLogging;
    }

    public static ResponseLoggingLevel getResponseLogging() {
        return responseLogging;
    }

    public static void setResponseLogging(ResponseLoggingLevel responseLogging) {
        NearestConfig.responseLogging = responseLogging;
    }
}
