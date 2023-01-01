package io.github.oleksandrpodoliako.nearest.enums;

public enum APIMethod {
    GET("GET"),
    GET_ARRAY("GET"),
    POST("POST"),
    PUT("PUT"),
    PATCH("PATCH"),
    DELETE("DELETE");

    private final String name;

    APIMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
