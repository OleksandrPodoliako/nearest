package io.github.oleksandrpodoliako.nearest.apiwrappers;

import io.github.oleksandrpodoliako.nearest.enums.APIMethod;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

import static io.github.oleksandrpodoliako.nearest.enums.APIMethod.*;
import static io.github.oleksandrpodoliako.nearest.enums.APIMethod.DELETE;

@Getter
@Setter
@Builder
public class RequestWrapper<T> {

    private APIMethod apiMethod;
    private String url;
    private Map<String, String> headers;
    private Map<String, String> queryParameters;

    private T body;

    public void setApiMethod(String apiMethod){
        switch (APIMethod.valueOf(apiMethod)) {
            case GET:
                this.setApiMethod(GET);
                break;
            case GET_ARRAY:
                this.setApiMethod(GET_ARRAY);
                break;
            case POST:
                this.setApiMethod(POST);
                break;
            case PUT:
                this.setApiMethod(PUT);
                break;
            case PATCH:
                this.setApiMethod(PATCH);
                break;
            case DELETE:
                this.setApiMethod(DELETE);
                break;
            default:
                throw new RuntimeException(String.format("[%s] is not implemented", this.getApiMethod()));
        }
    }

    public void setApiMethod(APIMethod apiMethod){
        this.apiMethod = apiMethod;
    }

}
