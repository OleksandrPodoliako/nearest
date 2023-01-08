package io.github.oleksandrpodoliako.nearest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Request {
    public String method;
    public ArrayList<Header> header;
    public Body body;
    public Url url;

    public Map<String, String> getHeaders() {
        Map<String, String> headers = new HashMap<>();

        for (Header header : getHeader()) {
            headers.put(header.getKey(), header.getValue());
        }

        return headers;
    }
}
