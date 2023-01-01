package io.github.oleksandrpodoliako.nearest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;

public class Converter {

    public static <T> String toCurl(RequestWrapper<T> requestWrapper) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("curl");
        stringBuilder.append(" ");
        stringBuilder.append("--request");
        stringBuilder.append(" ");
        stringBuilder.append(requestWrapper.getApiMethod().getName());
        stringBuilder.append(" ");
        stringBuilder.append("'");
        stringBuilder.append(requestWrapper.getUrl());

        if (requestWrapper.getQueryParameters() != null) {
            stringBuilder.append("?");
            String prefix = "";
            for (String key : requestWrapper.getQueryParameters().keySet()) {
                stringBuilder.append(prefix);
                stringBuilder.append(key);
                stringBuilder.append("=");
                stringBuilder.append(requestWrapper.getQueryParameters().get(key));
                prefix = "&";
            }
        }

        stringBuilder.append("'");

        if (requestWrapper.getHeaders() != null) {
            for (String key : requestWrapper.getHeaders().keySet()) {
                stringBuilder.append(" ");
                stringBuilder.append("\\");
                stringBuilder.append(System.lineSeparator());
                stringBuilder.append("--header");
                stringBuilder.append(" ");
                stringBuilder.append("'");
                stringBuilder.append(key);
                stringBuilder.append(":");
                stringBuilder.append(" ");
                stringBuilder.append(requestWrapper.getHeaders().get(key));
                stringBuilder.append("'");
            }
        }

        if (requestWrapper.getBody() != null) {
            stringBuilder.append(" ");
            stringBuilder.append("\\");
            stringBuilder.append(System.lineSeparator());
            stringBuilder.append("--data-raw");
            stringBuilder.append(" ");

            try {
                stringBuilder.append(new ObjectMapper().writeValueAsString(requestWrapper.getBody()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }

        return stringBuilder.toString();
    }
}
