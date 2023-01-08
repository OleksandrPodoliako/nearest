package io.github.oleksandrpodoliako.nearest.postmanintegration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;
import io.github.oleksandrpodoliako.nearest.models.Item;
import io.github.oleksandrpodoliako.nearest.models.PostmanCollection;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class PostmanIntegration {

    private PostmanCollection postmanCollection;

    public PostmanIntegration(String postmanCollectionPath) {
        try {
            this.postmanCollection = new ObjectMapper()
                    .readValue(Paths.get(postmanCollectionPath).toFile(), PostmanCollection.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> RequestWrapper<T> getRequestWrapper(String requestName, Class<T> t, String... folderNames) {
        ArrayList<Item> items = postmanCollection.getItem();

        for (String folderName : folderNames) {
            items = items.stream().filter(it -> it.getName().equals(folderName)).findFirst().get().getItem();
        }

        Item item = items.stream().filter(it -> it.getName().equals(requestName)).findFirst().get();

        try {
            T body = null;
            if (item.getRequest().getBody() != null) {
                if (t == String.class) {
                    body = (T) item.getRequest().getBody().getRaw();
                } else {
                    body = new ObjectMapper().readValue(item.getRequest().getBody().getRaw(), t);
                }
            }

            return RequestWrapper.<T>builder()
                    .url(item.getRequest().getUrl().getUrl())
                    .apiMethod(APIMethod.valueOf(item.getRequest().getMethod()))
                    .queryParameters(item.getRequest().getUrl().getQueryParameters())
                    .headers(item.getRequest().getHeaders())
                    .body(body)
                    .build();

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
