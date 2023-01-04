package io.github.oleksandrpodoliako.nearest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;

import java.util.Map;

public class CurlTemplateEngine {

    private StringBuilder result;

    public CurlTemplateEngine init() {
        result = new StringBuilder();
        return this;
    }


    public CurlTemplateEngine with(APIMethod method, String url, Map<String, String> queryParameters) {

        StringBuilder urlWithQueryParameters = new StringBuilder(url);

        if (queryParameters != null) {
            boolean isFirst = true;
            for (String key : queryParameters.keySet()) {
                urlWithQueryParameters.append(isFirst ? "?" : "&").append(key).append("=").append(queryParameters.get(key));
                isFirst = false;
            }
        }

        String curlWithUrl = "curl --request ${method} '${url}'"
                .replaceFirst("\\$\\{method}", method.getName())
                .replaceFirst("\\$\\{url}", urlWithQueryParameters.toString());

        getResult().append(curlWithUrl);

        return this;
    }

    public CurlTemplateEngine with(Map<String, String> values) {

        if (values == null) {
            return this;
        }

        String curlWithHeadersTemplate = "--header '${key}: ${value}'";

        for (String key : values.keySet()) {
            String curlWithHeaders = curlWithHeadersTemplate.replaceFirst("\\$\\{key}", key);
            curlWithHeaders = curlWithHeaders.replaceFirst("\\$\\{value}", values.get(key));
            getResult().append(" \\").append(System.lineSeparator());
            getResult().append(curlWithHeaders);
        }

        return this;
    }

    public CurlTemplateEngine with(Object object) {

        if (object == null) {
            return this;
        }

        String curlWithBody = "--data-raw ${object}";

        try {
            curlWithBody = curlWithBody.replaceFirst("\\$\\{object}", new ObjectMapper().writeValueAsString(object));
            getResult().append(" \\").append(System.lineSeparator());
            getResult().append(curlWithBody);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return this;
    }


    public String build() {
        return this.getResult().toString();
    }

    private StringBuilder getResult() {
        return result;
    }
}
