# NeaRest is a Rest Assure wrapper for test automation activities
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg)](https://github.com/OleksandrPodoliako/nearest/blob/main/LICENSE)
![Free](https://img.shields.io/badge/free-open--source-green.svg)

## What is NeaRest?
NeaRest is a Rest Assure wrapper to write Rest clients. Nearest reduces amount of code by using generics. NeaRest has natural REST 
structure.

## How to use?
* Add NeaRest to the project
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

* Create Client class and interface IRestClient
* implement two methods from interface IRestClient to return POJO model and POJO models array


```
public class TestClient implements IRestClient {
    @Override
    public <T> Type getClassType() {
        return Post.class;
    }

    @Override
    public <T> Type getClassArrayType() {
        return Post[].class;
    }
}
```
* Now you can instantiate Client class, which has all CRUD operations implemented

```
public class Main {
    private static final String URL = "https://jsonplaceholder.typicode.com/posts/1";

    public static void main(String[] args) {

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .build();

        ResponseWrapper<Post> responseWrapper = new MyClient().getEntity(URL, requestWrapper);
        
    }
}
```
