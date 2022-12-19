package io.github.oleksandrpodoliako.nearest.apiclient;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.ResponseWrapper;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Type;

public interface IRestClient<T, K> {

    default ResponseWrapper<T> send(RequestWrapper<K> requestWrapper) {
        switch (requestWrapper.getApiMethod()) {
            case GET:
                return getEntity(requestWrapper);
            case GET_ARRAY:
                return getEntityArray(requestWrapper);
            case POST:
                return postEntity(requestWrapper);
            case PUT:
                return putEntity(requestWrapper);
            case PATCH:
                return patchEntity(requestWrapper);
            case DELETE:
                return deleteEntity(requestWrapper);
            default:
                throw new RuntimeException(String.format("[%s] is not implemented", requestWrapper.getApiMethod()));
        }
    }

    private ResponseWrapper<T> getEntityArray(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();

        Response response = configureRequest(requestWrapper)
                .when()
                .get(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassArrayType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private ResponseWrapper<T> getEntity(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .get(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private ResponseWrapper<T> postEntity(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .post(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private ResponseWrapper<T> putEntity(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .put(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private ResponseWrapper<T> patchEntity(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .patch(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private ResponseWrapper<T> deleteEntity(RequestWrapper<K> requestWrapper) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        Response response = configureRequest(requestWrapper)
                .when()
                .delete(requestWrapper.getUrl());

        responseWrapper.setBody(response.as(getClassType()));
        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private RequestSpecification configureRequest(RequestWrapper<K> requestWrapper) {
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

    private Type getClassType() {
        return new TypeReference<T>() {
        }.getType();
    }

    private Type getClassArrayType() {
        return new TypeReference<T[]>() {
        }.getType();
    }
}
