package io.github.oleksandrpodoliako.nearest.apiwrappers;

import io.restassured.response.Response;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class ResponseWrapper<T> {

    private String statusLine;
    private Map<String, String> headers;
    private T body;
    private Response responseRaw;

}
