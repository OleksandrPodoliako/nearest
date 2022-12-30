package io.github.oleksandrpodoliako.nearest.apiclient;

import io.github.oleksandrpodoliako.nearest.enums.MappingStrategy;
import io.github.oleksandrpodoliako.nearest.enums.RequestLoggingLevel;
import io.github.oleksandrpodoliako.nearest.enums.ResponseLoggingLevel;

public class NearestConfig {

    private static RequestLoggingLevel requestLogging = RequestLoggingLevel.NONE;
    private static ResponseLoggingLevel responseLogging = ResponseLoggingLevel.NONE;
    private static MappingStrategy mappingStrategy = MappingStrategy.TO_MAP;

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

    public static MappingStrategy getMappingStrategy() {
        return mappingStrategy;
    }

    public static void setMappingStrategy(MappingStrategy mappingStrategy) {
        NearestConfig.mappingStrategy = mappingStrategy;
    }
}
