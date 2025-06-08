package checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.ErrorResponseDto;

public class ErrorChecks {

    @Step("Checking if reponse body has valid structure")
    public static ErrorResponseDto checkErrorResponseBody(Response response) {
        return CommonChecks.assertionParseSuccessful(response.body().asString(), ErrorResponseDto.class);
    }
}
