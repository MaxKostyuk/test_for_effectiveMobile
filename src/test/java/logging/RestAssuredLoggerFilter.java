package logging;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class RestAssuredLoggerFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger("restassured");

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext ctx) {

        LOGGER.info(">>> Request: {} {}", requestSpec.getMethod(), requestSpec.getURI());

        if (requestSpec.getHeaders().exist()) {
            LOGGER.debug("Request Headers:\n{}", requestSpec.getHeaders());
        }

        if (requestSpec.getBody() != null) {
            LOGGER.debug("Request Body:\n{}", Optional.ofNullable(requestSpec.getBody()));
        }

        Response response = ctx.next(requestSpec, responseSpec);

        LOGGER.info("<<< Response: HTTP {}", response.getStatusCode());

        if (response.getHeaders().exist()) {
            LOGGER.debug("Response Headers:\n{}", response.getHeaders());
        }

        String responseBody;
        try {
            responseBody = response.getBody().asPrettyString();
        } catch (Exception e) {
            responseBody = "[Cannot parse response body]";
        }

        LOGGER.debug("Response Body:\n{}", responseBody);

        return response;
    }
}