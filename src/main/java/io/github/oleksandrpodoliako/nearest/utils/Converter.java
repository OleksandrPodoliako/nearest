package io.github.oleksandrpodoliako.nearest.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.oleksandrpodoliako.nearest.enums.APIMethod.*;

public class Converter {

    public static <T> String toCurl(RequestWrapper<T> requestWrapper) {
        return new CurlTemplateEngine().init()
                .with(requestWrapper.getApiMethod(), requestWrapper.getUrl(), requestWrapper.getQueryParameters())
                .with(requestWrapper.getHeaders())
                .with(requestWrapper.getBody())
                .build();
    }

    public static <T> RequestWrapper<T> toRequestWrapper(String curl, Class<T> t) {
        String methodRegEx = "(?<=--request )(.*)(?= ')";
        String URLRegEx = "([\\w+]+\\:\\/\\/)?([\\w\\d-]+\\.)*[\\w-]+[\\.\\:]\\w+([\\/\\?\\=\\&\\#\\.]?[\\w-]+)*\\/?";
        String queryParameterRegEx = "\\?(.*)'";
        String headerRegEx = "--header '(.*)'";
        String bodyRegEx = "(?<=--data-raw )(.*)";

        RequestWrapper<T> requestWrapper = RequestWrapper.<T>builder().build();

        Pattern pattern = Pattern.compile(methodRegEx);
        Matcher matcher = pattern.matcher(curl);


        if (matcher.find()) {
            switch (APIMethod.valueOf(matcher.group())) {
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
        }


        pattern = Pattern.compile(URLRegEx);
        matcher = pattern.matcher(curl);

        if (matcher.find()) {
            requestWrapper.setUrl(matcher.group().split("\\?")[0]);
        }


        pattern = Pattern.compile(headerRegEx);
        matcher = pattern.matcher(curl);
        Map<String, String> headers = new HashMap<>();
        while (matcher.find()) {
            String header = matcher.group(1);
            headers.put(header.split(": ")[0], header.split(": ")[1]);
        }
        requestWrapper.setHeaders(headers);

        pattern = Pattern.compile(queryParameterRegEx);
        matcher = pattern.matcher(curl);
        Map<String, String> queryParameters = new HashMap<>();
        if (matcher.find()) {
            String[] queryParametersStringArray = matcher.group(1).split("&");

            for (String queryParameter : queryParametersStringArray) {
                queryParameters.put(queryParameter.split("=")[0], queryParameter.split("=")[1]);
            }
        }
        requestWrapper.setQueryParameters(queryParameters);

        pattern = Pattern.compile(bodyRegEx);
        matcher = pattern.matcher(curl);

        if (matcher.find()) {
            try {
                requestWrapper.setBody(new ObjectMapper().readValue(matcher.group(), t));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }

        return requestWrapper;

    }
}
