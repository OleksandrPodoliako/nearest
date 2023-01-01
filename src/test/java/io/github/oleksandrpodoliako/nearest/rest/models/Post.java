package io.github.oleksandrpodoliako.nearest.rest.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Post implements Serializable {
    public int userId;
    public int id;
    public String title;
    public String body;

    public Post() {
    }

    public Post(int userId, int id, String title, String body) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public static PostBuilder builder() {
        return new PostBuilder();
    }

    public int getUserId() {
        return this.userId;
    }

    public int getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getBody() {
        return this.body;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public static class PostBuilder {
        private int userId;
        private int id;
        private String title;
        private String body;

        PostBuilder() {
        }

        public PostBuilder userId(int userId) {
            this.userId = userId;
            return this;
        }

        public PostBuilder id(int id) {
            this.id = id;
            return this;
        }

        public PostBuilder title(String title) {
            this.title = title;
            return this;
        }

        public PostBuilder body(String body) {
            this.body = body;
            return this;
        }

        public Post build() {
            return new Post(userId, id, title, body);
        }

        public String toString() {
            return "Post.PostBuilder(userId=" + this.userId + ", id=" + this.id + ", title=" + this.title + ", body=" + this.body + ")";
        }
    }
}
