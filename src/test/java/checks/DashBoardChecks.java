package checks;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import models.CreateDashboardResponseDTO;

public class DashBoardChecks {

    @Step("Checking create dashboard response body is valid")
    public static CreateDashboardResponseDTO checkCreateDashboardResponseBody(Response response) {
        return CommonChecks.assertionParseSuccessful(response.body().asString(), CreateDashboardResponseDTO.class);
    }
}
