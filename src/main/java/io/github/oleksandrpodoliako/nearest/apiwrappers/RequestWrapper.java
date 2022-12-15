package io.github.oleksandrpodoliako.nearest.apiwrappers;

import java.util.Map;

public class RequestWrapper<T> {

    private Map<String, String> headers;
    private Map<String, String> queryParameters;

    private T body;


    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getQueryParameters() {
        return queryParameters;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    RequestWrapper(Map<String, String> headers, Map<String, String> queryParameters, T body) {
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
    }

    public static <T> RequestWrapperBuilder<T> builder() {
        return new RequestWrapperBuilder<T>();
    }

    public static class RequestWrapperBuilder<T> {
        private Map<String, String> headers;
        private Map<String, String> queryParameters;
        private T body;

        RequestWrapperBuilder() {
        }

        public RequestWrapperBuilder<T> headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public RequestWrapperBuilder<T> queryParameters(Map<String, String> queryParameters) {
            this.queryParameters = queryParameters;
            return this;
        }

        public RequestWrapperBuilder<T> body(T body) {
            this.body = body;
            return this;
        }

        public RequestWrapper<T> build() {
            return new RequestWrapper<T>(headers, queryParameters, body);
        }

        public String toString() {
            return "RequestWrapper.RequestWrapperBuilder(headers=" + this.headers + ", queryParameters=" + this.queryParameters + ", body=" + this.body + ")";
        }
    }
}
