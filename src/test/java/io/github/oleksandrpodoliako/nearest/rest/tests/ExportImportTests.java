package io.github.oleksandrpodoliako.nearest.rest.tests;

import com.github.tomakehurst.wiremock.common.Json;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import io.github.oleksandrpodoliako.nearest.apiwrappers.RequestWrapper;
import io.github.oleksandrpodoliako.nearest.enums.APIMethod;
import io.github.oleksandrpodoliako.nearest.postmanintegration.PostmanIntegration;
import io.github.oleksandrpodoliako.nearest.rest.clients.PostClient;
import io.github.oleksandrpodoliako.nearest.rest.datageneration.DataProvider;
import io.github.oleksandrpodoliako.nearest.rest.models.Post;
import io.github.oleksandrpodoliako.nearest.utils.Converter;
import org.junit.Rule;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;


public class ExportImportTests {

    private static final String POSTMAN_COLLECTION_PATH = System.getProperty("os.name")
            .toLowerCase().contains("windows") ? "src/test/resources/" : "src\\test\\resources\\";
    private final static String POSTMAN_COLLECTION_FILE_NAME = "NearestImportFromPostman.postman_collection.json";
    private final static String BASE_URL = "http://localhost";
    private final static String GET_URL = "/posts/1";
    private final static int PORT = 44444;
    private final static String HEADER_KEY = "Content-type";
    private final static String HEADER_VALUE = "application/json; charset=UTF-8";
    private static final Map<String, String> HEADERS = new HashMap<>() {{
        put(HEADER_KEY, HEADER_VALUE);
    }};
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(PORT);

    @Test
    public void importTest() {
        String curl = "curl --request GET 'http://localhost:44444/posts/1' \\\n" +
                "--header 'Content-type: application/json; charset=UTF-8'";

        givenThat(get(GET_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(DataProvider.getPost()))));

        RequestWrapper<Post> requestWrapper = new Converter().toRequestWrapper(curl, Post.class);

        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                getRequestedFor(urlEqualTo(GET_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }

    @Test
    public void exportTest() {
        String curlExpected = "curl --request GET 'http://localhost:44444/posts/1' \\" + System.lineSeparator()
                + "--header 'Content-type: application/json; charset=UTF-8'";

        RequestWrapper<Post> requestWrapper = RequestWrapper.<Post>builder()
                .url(BASE_URL + ":" + PORT + GET_URL)
                .apiMethod(APIMethod.GET)
                .headers(HEADERS)
                .build();

        String curlActual = new Converter().toCurl(requestWrapper);

        assertEquals("The converted curl should be correct", curlExpected, curlActual);
    }

    @Test
    public void importFromPostmanTests() {
        givenThat(get(GET_URL)
                .withHeader(HEADER_KEY, containing(HEADER_VALUE))
                .willReturn(ok()
                        .withHeader(HEADER_KEY, HEADER_VALUE)
                        .withBody(Json.write(DataProvider.getPost()))));

        RequestWrapper<Post> requestWrapper = new PostmanIntegration(POSTMAN_COLLECTION_PATH + POSTMAN_COLLECTION_FILE_NAME)
                .getRequestWrapper("request1", Post.class, "folder1level", "folder2level");

        new PostClient().send(requestWrapper);

        verify(
                exactly(1),
                getRequestedFor(urlEqualTo(GET_URL))
                        .withHeader(HEADER_KEY, containing(HEADER_VALUE))
        );
    }
}
