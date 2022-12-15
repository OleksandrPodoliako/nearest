package io.github.oleksandrpodoliako.nearest.apiclient;


import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.ResponseWrapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Type;

public interface IRestClient {

    <T> Type getClassType();

    <T> Type getClassArrayType();

    default <T, K> ResponseWrapper<T[]> getEntityArray(String url, RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T[]> responseWrapper = new ResponseWrapper<>();

        Response response = configureRequest(requestWrapper)
                .when()
                .get(url);

        responseWrapper.setBody(response.as(getClassArrayType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    default <T, K> ResponseWrapper<T> getEntity(String url, RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .get(url);

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    default <T, K> ResponseWrapper<T> postEntity(Class<T> t, RequestWrapper<K> requestWrapper, String url) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .post(url);

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    default <T, K> ResponseWrapper<T> putEntity(String url, RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .put(url);

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    default <T, K> ResponseWrapper<T> patchEntity(String url, RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .patch(url);

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    default <T, K> ResponseWrapper<T> deleteEntity(String url, RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .delete(url);

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private <T> RequestSpecification configureRequest(RequestWrapper<T> requestWrapper) {
        RequestSpecification requestSpecification = RestAssured.given();


        if (requestWrapper.getHeaders() != null) {
            for (String key : requestWrapper.getHeaders().keySet()) {
                requestSpecification.header(key, requestWrapper.getHeaders().get(key));
            }
        }

        if (requestWrapper.getQueryParameters() != null) {
            for (String key : requestWrapper.getQueryParameters().keySet()) {
                requestSpecification.queryParam(key, requestWrapper.getQueryParameters().get(key));
            }
        }

        if (requestWrapper.getBody() != null) {
            requestSpecification.body(requestWrapper.getBody());
        }

        return requestSpecification;
    }
}
