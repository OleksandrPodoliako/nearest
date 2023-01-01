package io.github.oleksandrpodoliako.nearest.apiwrappers;

import io.github.oleksandrpodoliako.nearest.enums.APIMethod;

import java.util.Map;

public class RequestWrapper<T> {

    private APIMethod apiMethod;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;

    private T body;

    RequestWrapper(APIMethod apiMethod, String url, Map<String, String> headers, Map<String, String> queryParameters, T body) {
        this.apiMethod = apiMethod;
        this.url = url;
        this.headers = headers;
        this.queryParameters = queryParameters;
        this.body = body;
    }

    public static <T> RequestWrapperBuilder<T> builder() {
        return new RequestWrapperBuilder<T>();
    }

    public APIMethod getApiMethod() {
        return this.apiMethod;
    }

    public String getUrl() {
        return this.url;
    }

    public Map<String, String> getHeaders() {
        return this.headers;
    }

    public Map<String, String> getQueryParameters() {
        return this.queryParameters;
    }

    public T getBody() {
        return this.body;
    }

    public void setApiMethod(APIMethod apiMethod) {
        this.apiMethod = apiMethod;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setQueryParameters(Map<String, String> queryParameters) {
        this.queryParameters = queryParameters;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public static class RequestWrapperBuilder<T> {
        private APIMethod apiMethod;
        private String url;
        private Map<String, String> headers;
        private Map<String, String> queryParameters;
        private T body;

        RequestWrapperBuilder() {
        }

        public RequestWrapperBuilder<T> apiMethod(APIMethod apiMethod) {
            this.apiMethod = apiMethod;
            return this;
        }

        public RequestWrapperBuilder<T> url(String url) {
            this.url = url;
            return this;
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
            return new RequestWrapper<T>(apiMethod, url, headers, queryParameters, body);
        }

        public String toString() {
            return "RequestWrapper.RequestWrapperBuilder(apiMethod=" + this.apiMethod + ", url=" + this.url + ", headers=" + this.headers + ", queryParameters=" + this.queryParameters + ", body=" + this.body + ")";
        }
    }
}
