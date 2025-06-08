package ui.tests;

import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import ui.pom.DashboardPage;
import ui.pom.LoginPage;
import ui.webdriver.WebDriverFactory;

@DisplayName("UI add widget test")
public class AddWidgetTest {
    private static WebDriver driver;
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static String login;
    private static String password;

    @BeforeAll
    public static void setup() {
        driver = WebDriverFactory.createDriver();
        loginPage = new LoginPage(driver);
        dashboardPage = new DashboardPage(driver);
        login = "default";
        password = "1q2w3e";
    }

    @Test
    @DisplayName("Positive test to add widget")
    @Description("Logging in, then go to dashboard page, choosing dashboard, clicking \"add widget\" button, choosing widget type," +
            " clicking twice button \"next step\", then button \"add\". Should appear popup window with message of successful widget creation ")
    public void shouldAddTaskProgressWidgetSuccessfully() {
        loginPage.loggingIn(login, password);
        dashboardPage.openPage();
        dashboardPage.clickLinkToDashboard(0);
        int numberOfWidgetsBefore = dashboardPage.getNumberOfAddedWidgets();
        dashboardPage.clickAddWidgetButton();
        dashboardPage.chooseProjectActivityPanelType();
        dashboardPage.clickNextStepButton();
        dashboardPage.clickNextStepButton();
        dashboardPage.clickAddButton();
        Assertions.assertTrue(dashboardPage.isPopUpWindowWidgetAddedVisible());
        int numberOfWidgetsAfter = dashboardPage.getNumberOfAddedWidgets();
        Assertions.assertEquals(numberOfWidgetsBefore + 1, numberOfWidgetsAfter, "New widget is not found");
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
