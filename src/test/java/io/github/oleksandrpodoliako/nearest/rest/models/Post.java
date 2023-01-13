package io.github.oleksandrpodoliako.nearest.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {
    public int userId;
    public int id;
    public String title;
    public String body;

}
