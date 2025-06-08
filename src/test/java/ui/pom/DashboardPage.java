package ui.pom;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DashboardPage {
    private static final String DASHBOARD_PAGE_URL = "https://demo.reportportal.io/ui/dashboard";
    private static final By DASHBOARD_LINK_IN_LIST = By.xpath("//div[contains(@class, 'dashboardTable__dashboard-table--')]//a");
    private static final By ADD_NEW_WIDGET_BUTTON = By.xpath("//span[text()='Add new widget']/ancestor::button");
    private static final By PROJECT_ACTIVITY_TYPE = By.xpath("//div[text()='Project activity panel']");
    private static final By NEXT_STEP_BUTTON = By.xpath("//span[text()='Next step']/ancestor::button");
    private static final By ADD_BUTTON = By.xpath("//button[text()='Add']");
    private static final By POPUP_WINDOW_WIDGET_ADDED = By.xpath("//h2[text()='Widget has been added successfully']");
    private static final By ADDED_WIDGET = By.xpath("//div[contains(@class, 'react-grid-item')]");
    private final WebDriver driver;

    @Step("Opening dashboards page")
    public void openPage() {
        log.info("Opening dashboard page {}", DASHBOARD_PAGE_URL);
        driver.get(DASHBOARD_PAGE_URL);
    }

    @Step("Clicking link to dashboard #{dashboardNumber} from list")
    public void clickLinkToDashboard(int dashboardNumber) {
        log.info("Clicking of dashboard {} in the list of dashboards", dashboardNumber);
        getWait().until(ExpectedConditions.visibilityOfElementLocated(DASHBOARD_LINK_IN_LIST));
        List<WebElement> listOfDashboards = driver.findElements(DASHBOARD_LINK_IN_LIST);
        if (dashboardNumber >= listOfDashboards.size()) {
            log.error("Dashboard index {} out of bounds (max = {})", dashboardNumber, listOfDashboards.size() - 1);
            throw new IllegalArgumentException("Dashboard index out of bounds: " + dashboardNumber);
        }
        listOfDashboards.get(dashboardNumber).click();
    }

    @Step("Clicking on add widget button")
    public void clickAddWidgetButton() {
        log.info("Clicking add widget button");
        clickWhenVisible(ADD_NEW_WIDGET_BUTTON);
    }

    @Step("Choosing from list project activity panel type for widget")
    public void chooseProjectActivityPanelType() {
        log.info("Choosing project activity panel type for widget");
        clickWhenVisible(PROJECT_ACTIVITY_TYPE);
    }

    @Step("Clicking next step button")
    public void clickNextStepButton() {
        clickWhenVisible(NEXT_STEP_BUTTON);
    }

    @Step("Clicking add button")
    public void clickAddButton() {
        clickWhenVisible(ADD_BUTTON);
    }

    @Step("Checking if popup window of successful widget creation appeared")
    public boolean isPopUpWindowWidgetAddedVisible() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            wait.until(ExpectedConditions.visibilityOfElementLocated(POPUP_WINDOW_WIDGET_ADDED));
            log.info("Popup message of successful adding widget found");
            return true;
        } catch (TimeoutException e) {
            log.error("Popup window with message of successful adding widget not found");
            return false;
        }
    }

    @Step("Getting number of added widgets")
    public int getNumberOfAddedWidgets() {
        return driver.findElements(ADDED_WIDGET).size();
    }

    private WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    private void clickWhenVisible(By locator) {
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
            driver.findElement(locator).click();
        } catch (TimeoutException e) {
            log.error("Not found element {}", locator);
            throw e;
        }
    }
}
