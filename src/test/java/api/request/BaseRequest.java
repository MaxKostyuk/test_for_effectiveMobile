package api.request;

import api.logging.RestAssuredLoggerFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import api.config.Config;
import io.restassured.RestAssured;

public class BaseRequest {
    private static final ObjectMapper mapper;
    protected static final String apiKey;

    static {
        apiKey = Config.get().getApiKey();
        RestAssured.baseURI = Config.get().getBaseUrl();
        RestAssured.filters(new RestAssuredLoggerFilter());
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
    }

    public static <T> T fromJson(String jsonString, Class<T> classOfT) throws Exception {
        return mapper.readValue(jsonString, classOfT);
    }

}
