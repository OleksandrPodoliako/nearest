package io.github.oleksandrpodoliako.nearest.apiwrappers;

import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

public class ResponseWrapper<T> {

    private String statusLine;
    private Map<String, String> headers;
    private T body;
    private Response responseRaw;

    public int getStatusCode() {
        return responseRaw.getStatusCode();
    }

    public String getStatusLine() {
        return statusLine;
    }

    public void setStatusLine(String statusLine) {
        this.statusLine = statusLine;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Response getResponseRaw() {
        return responseRaw;
    }

    public void setResponseRaw(Response responseRaw) {
        this.responseRaw = responseRaw;
        initResponseFields(responseRaw);
    }

    private void initResponseFields(Response responseRaw) {
        setStatusLine(getResponseRaw().getStatusLine());
        Map<String, String> headers = new HashMap<>();
        responseRaw.headers().forEach(header -> headers.put(header.getName(), header.getValue()));
        setHeaders(headers);
    }
}
