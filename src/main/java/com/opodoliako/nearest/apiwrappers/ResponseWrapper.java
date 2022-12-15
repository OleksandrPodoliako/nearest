package apiwrappers;

import io.restassured.response.Response;

public class ResponseWrapper<T> {
    private T body;
    private Response responseRaw;

    public int getStatusCode() {
        return responseRaw.getStatusCode();
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
    }
}
