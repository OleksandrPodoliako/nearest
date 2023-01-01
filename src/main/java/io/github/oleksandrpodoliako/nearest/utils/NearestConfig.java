package io.github.oleksandrpodoliako.nearest.utils;

import io.github.oleksandrpodoliako.nearest.enums.ExportStrategy;
import io.github.oleksandrpodoliako.nearest.enums.MappingStrategy;
import io.github.oleksandrpodoliako.nearest.enums.RequestLoggingLevel;
import io.github.oleksandrpodoliako.nearest.enums.ResponseLoggingLevel;

public class NearestConfig {

    private static RequestLoggingLevel requestLogging = RequestLoggingLevel.NONE;
    private static ResponseLoggingLevel responseLogging = ResponseLoggingLevel.NONE;
    private static MappingStrategy mappingStrategy = MappingStrategy.TO_MAP;
    private static ExportStrategy exportStrategy = ExportStrategy.NOT_TO_EXPORT;

    private static String exportFileName = System.getProperty("os.name")
            .toLowerCase().contains("windows") ? "target/exports.txt" : "target\\exports.txt";

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

    public static ExportStrategy getExportStrategy() {
        return exportStrategy;
    }

    public static void setExportStrategy(ExportStrategy exportStrategy) {
        NearestConfig.exportStrategy = exportStrategy;
    }

    public static String getExportFileName() {
        return exportFileName;
    }

    public static void setExportFileName(String exportFileName) {
        NearestConfig.exportFileName = exportFileName;
    }
}
