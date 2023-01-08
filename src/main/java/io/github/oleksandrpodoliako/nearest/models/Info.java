package io.github.oleksandrpodoliako.nearest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Info {

    @JsonProperty("_postman_id")
    public String postmanId;
    public String name;
    public String schema;
    @JsonProperty("_exporter_id")
    public String exporterId;

}
