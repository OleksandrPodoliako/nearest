package io.github.oleksandrpodoliako.nearest.apiclient;


import com.fasterxml.jackson.core.type.TypeReference;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.ResponseWrapper;
import io.github.oleksandrpodoliako.nearest.enums.MappingStrategy;
import io.github.oleksandrpodoliako.nearest.utils.Converter;
import io.github.oleksandrpodoliako.nearest.utils.FilesUtil;
import io.github.oleksandrpodoliako.nearest.utils.NearestConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import static io.github.oleksandrpodoliako.nearest.utils.NearestConfig.*;

public interface IRestClient<T, K> {

    default ResponseWrapper<T> send(RequestWrapper<K> requestWrapper) {

        switch (NearestConfig.getExportStrategy()) {
            case TO_FILE:
                FilesUtil fileWriter = new FilesUtil();
                fileWriter.writeToFile(NearestConfig.getExportFileName(), new Converter().toCurl(requestWrapper));
                fileWriter.writeToFile(NearestConfig.getExportFileName(), "");
                break;
            case TO_CONSOLE:
                System.out.println("curl:");
                System.out.println(new Converter().toCurl(requestWrapper));
                break;
            default: // do nothing;
                break;
        }

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
        Response response = configureRequest(requestWrapper)
                .when()
                .get(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassArrayType());
    }

    private ResponseWrapper<T> getEntity(RequestWrapper<K> requestWrapper) {
        Response response = configureRequest(requestWrapper)
                .when()
                .get(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassType());
    }

    private ResponseWrapper<T> postEntity(RequestWrapper<K> requestWrapper) {
        Response response = configureRequest(requestWrapper)
                .when()
                .post(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassType());
    }

    private ResponseWrapper<T> putEntity(RequestWrapper<K> requestWrapper) {
        Response response = configureRequest(requestWrapper)
                .when()
                .put(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassType());
    }

    private ResponseWrapper<T> patchEntity(RequestWrapper<K> requestWrapper) {
        Response response = configureRequest(requestWrapper)
                .when()
                .patch(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassType());
    }

    private ResponseWrapper<T> deleteEntity(RequestWrapper<K> requestWrapper) {
        Response response = configureRequest(requestWrapper)
                .when()
                .delete(requestWrapper.getUrl());

        return fillResponseWrapper(response, getClassType());
    }

    private RequestSpecification configureRequest(RequestWrapper<K> requestWrapper) {
        RequestSpecification requestSpecification = RestAssured.given();

        configureRequestLogging(requestSpecification);

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

    private ResponseWrapper<T> fillResponseWrapper(Response response, Type type) {
        ResponseWrapper<T> responseWrapper = new ResponseWrapper<>();
        configureResponseLogging(response);

        responseWrapper.setStatusLine(response.getStatusLine());

        Map<String, String> headers = new HashMap<>();
        response.headers().forEach(header -> headers.put(header.getName(), header.getValue()));
        responseWrapper.setHeaders(headers);

        if (getMappingStrategy().equals(MappingStrategy.TO_MAP)) {
            responseWrapper.setBody(response.as(type));
        }

        responseWrapper.setResponseRaw(response);

        return responseWrapper;
    }

    private void configureRequestLogging(RequestSpecification requestSpecification) {
        switch (getRequestLogging()) {
            case ALL:
                requestSpecification.log().all();
                break;
            case PARAMETERS:
                requestSpecification.log().parameters();
                break;
            case METHODS:
                requestSpecification.log().method();
                break;
            default: // do nothing;
                break;
        }
    }

    private void configureResponseLogging(Response response) {
        switch (getResponseLogging()) {
            case ALL:
                response.then().log().all();
                break;
            case BODY:
                response.then().log().body();
                break;
            case STATUS:
                response.then().log().body();
                break;
            default: // do nothing;
                break;
        }
    }
}
