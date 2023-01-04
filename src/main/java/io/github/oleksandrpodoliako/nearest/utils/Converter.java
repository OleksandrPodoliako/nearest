package io.github.oleksandrpodoliako.nearest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;

import static io.github.oleksandrpodoliako.nearest.enums.APIMethod.*;

public class Converter {

    public <T> String toCurl(RequestWrapper<T> requestWrapper) {
        return new CurlTemplateEngine().init()
                .with(requestWrapper.getApiMethod(), requestWrapper.getUrl(), requestWrapper.getQueryParameters())
                .with(requestWrapper.getHeaders())
                .with(requestWrapper.getBody())
                .build();
    }

    public <T> RequestWrapper<T> toRequestWrapper(String curl, Class<T> t) {
        RequestWrapper<T> requestWrapper = RequestWrapper.<T>builder().build();
        CurlParser curlParser = new CurlParser();

        switch (APIMethod.valueOf(curlParser.parseApiMethod(curl))) {
            case GET:
                requestWrapper.setApiMethod(GET);
                break;
            case GET_ARRAY:
                requestWrapper.setApiMethod(GET_ARRAY);
                break;
            case POST:
                requestWrapper.setApiMethod(POST);
                break;
            case PUT:
                requestWrapper.setApiMethod(PUT);
                break;
            case PATCH:
                requestWrapper.setApiMethod(PATCH);
                break;
            case DELETE:
                requestWrapper.setApiMethod(DELETE);
                break;
            default:
                throw new RuntimeException(String.format("[%s] is not implemented", requestWrapper.getApiMethod()));
        }

        requestWrapper.setUrl(curlParser.parseUrl(curl));
        requestWrapper.setQueryParameters(curlParser.parseQueryParameters(curl));
        requestWrapper.setHeaders(curlParser.parseHeaders(curl));

        try {
            if (!curlParser.parseBody(curl).equals("")) {
                requestWrapper.setBody(new ObjectMapper().readValue(curlParser.parseBody(curl), t));
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return requestWrapper;

    }
}
