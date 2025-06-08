package api.tests.dashboard.create;

import api.checks.DashBoardChecks;
import api.checks.ErrorChecks;
import api.config.Config;
import api.models.CreateDashboardBodyDTO;
import api.request.DashboardRequest;
import api.tests.BaseTestClass;
import api.checks.CommonChecks;
import api.data.provider.dashboard.DashboardProvider;
import io.qameta.allure.Description;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import api.models.CreateDashboardResponseDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

@Slf4j
@DisplayName("Create dashboard API tests")
public class CreateDashboardTest extends BaseTestClass {
    private static CreateDashboardBodyDTO dashboard;
    private static String projectName;
    private static Optional<Integer> idToDelete = Optional.empty();

    @BeforeAll
    public static void setUp() {
        dashboard = DashboardProvider.singleDashboardToCreateProvider();
        projectName = Config.get().getDefaultProjectName();
    }

    @Test
    @DisplayName("Positive test for create dashboard API")
    @Description("Sending valid dashboard body with api key to api point. Should receive code 201 and body of CreatedDashBoardDTO class")
    public void createDashboardPositiveTestShouldReturn201AndBodyWithId() {
        log.info("Start test with dashboard {}", dashboard);
        Response response = DashboardRequest.createDashBoard(dashboard, projectName);
        CommonChecks.checkResponseCode(HttpStatus.SC_CREATED, response);
        CreateDashboardResponseDTO responseBody = DashBoardChecks.checkCreateDashboardResponseBody(response);
        idToDelete = Optional.of(responseBody.id());
    }

    @Test
    @DisplayName("Negative test for create dashboard API without request body")
    @Description("Sending request without body with dashboard. Should receive code 400 and body of ErrorResponseDTO class")
    public void createDashBoardNegativeTestWithoutBodyShouldReturn400AndErrorMessageBody() {
        log.info("Starting test with null dashboard body");
        Response response = DashboardRequest.createDashBoard(null, projectName);
        CommonChecks.checkResponseCode(400, response);
        ErrorChecks.checkErrorResponseBody(response);
    }

    @AfterEach
    public void deleteTestData() {
        if (idToDelete.isPresent()) {
            DashboardRequest.deleteDashBoard(idToDelete.get(), projectName);
            idToDelete = Optional.empty();
        }
        log.info("End of test");
    }

}
