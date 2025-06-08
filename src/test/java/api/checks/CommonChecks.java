package api.checks;

import api.request.BaseRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;

@Slf4j
public class CommonChecks {
    private static final String ASSERT_EQUALS_SUCCESS_MESSAGE = "Comparing of objects successful: expected {} equals to actual {}";
    private static final String ASSERT_EQUALS_FAIL_MESSAGE = "Comparing of objects failed: {}";

    @Step("Checking if received code equals to expected {expectedCode}")
    public static void checkResponseCode(int expectedCode, Response response) {
        int actualCode = response.statusCode();
        assertionEquals(expectedCode, actualCode, String.format("Received code %d does not equal to expected %d", actualCode, expectedCode));
    }

    public static void assertionEquals(int expectedInt, int actualInt, String message) {
        try {
            Assertions.assertEquals(expectedInt, actualInt, message);
            log.info(ASSERT_EQUALS_SUCCESS_MESSAGE, expectedInt, actualInt);
        } catch (AssertionError e) {
            log.warn(ASSERT_EQUALS_FAIL_MESSAGE, e.getMessage());
            throw e;
        }
    }

    public static <T> T assertionParseSuccessful(String jsonString, Class<T> classOfT) {
        try {
            var object = Assertions.assertDoesNotThrow(() -> BaseRequest.fromJson(jsonString, classOfT));
            log.info("Parsing object is successful");
            return object;
        } catch (AssertionError e) {
            log.warn("Cannot deserialize object to required type {}: {}", classOfT, e.getMessage());
            throw e;
        }
    }
}
