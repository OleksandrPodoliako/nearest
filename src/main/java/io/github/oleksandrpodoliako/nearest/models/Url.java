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
public class Url {
    public String raw;
    public String protocol;
    public ArrayList<String> host;
    public ArrayList<String> path;

    public String getUrl() {
        if (getRaw().contains("?")) {
            return getRaw().split("\\?")[0];
        }
        return getRaw();
    }

    public Map<String, String> getQueryParameters() {
        Map<String, String> queryParameters = new HashMap<>();

        if (getRaw().contains("?")) {
            for (String queryParameter : getRaw().split("\\?")[1].split("&")) {
                if (queryParameter.contains("=")) {
                    queryParameters.put(queryParameter.split("=")[0], queryParameter.split("=")[1]);
                }
            }
        }

        return queryParameters;
    }
}
