package api.request;

import api.config.Config;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import api.models.CreateDashboardBodyDTO;

public class DashboardRequest extends BaseRequest {
    private static final String CREATE_DASHBOARD_URL;

    static {
        CREATE_DASHBOARD_URL = Config.get().getCreateDashboardUrl();
    }

    public static Response createDashBoard(CreateDashboardBodyDTO dashBoard, String projectName) {
        return createDashBoard(dashBoard, projectName, apiKey);
    }

    @Step("Sending request to create dashboard {dashBoard} in project {projectName} with api key {apiKey}")
    public static Response createDashBoard(CreateDashboardBodyDTO dashBoard, String projectName, String apiKey) {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON)
                .auth().oauth2(apiKey);
        if (dashBoard != null)
            request.body(dashBoard);
        return request.post(String.format(CREATE_DASHBOARD_URL, projectName));
    }

    @Step("Deleting dashboard with id {dashboardId} in project {projectName} with api key {apiKey}")
    public static Response deleteDashBoard(int dashboardId, String projectName, String apiKey) {
        return RestAssured.given()
                .accept(ContentType.JSON)
                .auth().oauth2(apiKey)
                .delete(String.format(CREATE_DASHBOARD_URL + "/" + dashboardId, projectName));
    }

    public static Response deleteDashBoard(int dashboardId, String projectName) {
        return deleteDashBoard(dashboardId, projectName, apiKey);
    }
}
