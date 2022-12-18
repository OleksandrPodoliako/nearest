# NeaRest is a Rest Assured wrapper for test automation activities
[![Maven Central](https://img.shields.io/maven-central/v/io.github.oleksandrpodoliako/nearest.svg)](https://search.maven.org/artifact/io.github.oleksandrpodoliako/nearest)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/OleksandrPodoliako/nearest/blob/main/LICENSE)
![Free](https://img.shields.io/badge/free-open--source-green.svg) \
![](https://img.shields.io/badge/Java-11-blue)
![](https://img.shields.io/badge/Rest--Assured-5.2.0-blue)
![](https://img.shields.io/badge/Jackson-2.14.0-blue)

## What is NeaRest?
NeaRest is a Rest Assure wrapper for writing Rest clients. Nearest reduces amount of code, which needed to be written for REST clients,  by using generics. NeaRest inherent HTTP two level structure. On first level Nearest has RequestWrapper and ResponseWrapper. On second level RequestWrapper has headers and body. ResponseWrapper has body and raw response. Also, NeaRest has RestAssuredWrapper, which allow to do more specific actions with raw Rest Assured. Also, NeaRest has iRestClient, which contains already implemented CRUD operations.

## How to use?
* Add NeaRest to the project
```
<dependency>
    <groupId>io.github.oleksandrpodoliako</groupId>
    <artifactId>nearest</artifactId>
    <version>1.2.0</version>
</dependency>
```
* Create POJO model

```
@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    public int userId;
    public int id;
    public String title;
    public String body;

    public Post() {
    }
}
```

* Create Client class and add interface IRestClient. Specify request's and response's body types in interface

```
public class PostClient implements IRestClient<Post, Post> {
}
```

* Now you can instantiate Client class, which has all CRUD operations implemented

```
public class Main {
    private static final String URL = "https://jsonplaceholder.typicode.com/posts/1";

    public static void main(String[] args) {

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder().build();

        ResponseWrapper<Post> responseWrapper = new PostClient().getEntity(URL, requestWrapper);
        
    }
}
```

The full project-illustration can be found by [link](https://github.com/OleksandrPodoliako/nearest-example)