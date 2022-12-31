package rest.tests;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;
import org.junit.Rule;
import org.junit.Test;
import rest.clients.PostClient;
import rest.models.Post;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static rest.datageneration.DataProvider.getPost;

public class RestTests {

    private final static String BASE_URL = "http://localhost";
    private final static String GET_URL = "/posts/1";
    private final static String ENDPOINT_URL = "/posts/";
    private final static int PORT = 44444;
    private final static String HEADER_KEY = "Content-type";
    private final static String HEADER_VALUE = "application/json; charset=UTF-8";
    private static final Map<String, String> HEADERS = new HashMap<>() {{
        put(HEADER_KEY, HEADER_VALUE);
    }};

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Test
    public void getTest() {
        givenThat(get(GET_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(getPost()))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + GET_URL)
                .apiMethod(APIMethod.GET)
                .headers(HEADERS)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                getRequestedFor(urlEqualTo(GET_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void getArrayTest() {
        givenThat(get(ENDPOINT_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(new Post[]{getPost()}))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + ENDPOINT_URL)
                .apiMethod(APIMethod.GET_ARRAY)
                .headers(HEADERS)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                getRequestedFor(urlEqualTo(ENDPOINT_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void postTest() {
        Post post = getPost();
        givenThat(post(ENDPOINT_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(post))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + ENDPOINT_URL)
                .apiMethod(APIMethod.POST)
                .headers(HEADERS)
                .body(post)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                postRequestedFor(urlEqualTo(ENDPOINT_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void putTest() {
        Post post = getPost();
        givenThat(put(ENDPOINT_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(post))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + ENDPOINT_URL)
                .apiMethod(APIMethod.PUT)
                .headers(HEADERS)
                .body(post)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                putRequestedFor(urlEqualTo(ENDPOINT_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void patchTest() {
        Post post = getPost();
        givenThat(patch(urlEqualTo(ENDPOINT_URL))
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(post))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + ENDPOINT_URL)
                .apiMethod(APIMethod.PATCH)
                .headers(HEADERS)
                .body(post)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                patchRequestedFor(urlEqualTo(ENDPOINT_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void deleteTest() {
        Post post = getPost();
        givenThat(delete(ENDPOINT_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(post))));

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + ENDPOINT_URL)
                .apiMethod(APIMethod.DELETE)
                .headers(HEADERS)
                .body(post)
                .build();


        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                deleteRequestedFor(urlEqualTo(ENDPOINT_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }
}
