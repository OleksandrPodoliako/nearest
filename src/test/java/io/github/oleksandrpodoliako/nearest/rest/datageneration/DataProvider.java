package io.github.oleksandrpodoliako.nearest.rest.datageneration;

import com.github.javafaker.Faker;
import io.github.oleksandrpodoliako.nearest.rest.models.Post;

public class DataProvider {

    private static final Faker faker = new Faker();

    public static Post getPost() {
        return Post.builder()
                .userId(Integer.parseInt(faker.number().digits(5)))
                .id(Integer.parseInt(faker.number().digits(5)))
                .title(faker.book().title())
                .body(faker.book().publisher())
                .build();
    }
}
