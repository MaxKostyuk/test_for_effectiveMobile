package ui.pom;

import io.qameta.allure.Step;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class LoginPage {
    private static final String LOGIN_PAGE_URL = "https://demo.reportportal.io/ui/#login";
    private static final By LOGIN_FIELD = By.xpath("//input[@name='login']");
    private static final By PASSWORD_FIELD = By.xpath("//input[@name='password']");
    private static final By LOGIN_BUTTON = By.xpath("//button[text()='Login']");
    private static final By POPUP_SUCCESSFUL_LOGIN = By.xpath("//h2[text()='Signed in successfully']");
    private final WebDriver driver;

    @Step("Opening login page")
    public void openPage() {
        log.info("Opening login page {}", LOGIN_PAGE_URL);
        driver.get(LOGIN_PAGE_URL);
    }

    @Step("Filling login field with value {login}")
    public void sendLoginValue(String login) {
        log.debug("Sending login {} to the field", login);
        sendKeysWhenVisible(login, LOGIN_FIELD);
    }

    @Step("Filling password field with value {password}")
    public void sendPasswordValue(String password) {
        log.debug("Sending password {} to the field", password);
        sendKeysWhenVisible(password, PASSWORD_FIELD);
    }

    @Step("Pushing login button to submit login")
    public void pushLoginButton() {
        log.info("Clicking login button");
        clickWhenVisible(LOGIN_BUTTON);
    }

    @Step("Checking popup window of successful login is visible")
    public void isPopupWindowSuccessfulLoginVisible() {
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(POPUP_SUCCESSFUL_LOGIN));
            log.info("Logging in successful");
        } catch (TimeoutException e) {
            log.error("Successful login popup not found", e);
            throw e;
        }
    }

    @Step("Logging in")
    public void loggingIn(String login, String password) {
        openPage();
        sendLoginValue(login);
        sendPasswordValue(password);
        pushLoginButton();
        isPopupWindowSuccessfulLoginVisible();
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

    private void sendKeysWhenVisible(String key, By locator) {
        try {
            getWait().until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
            driver.findElement(locator).clear();
            driver.findElement(locator).sendKeys(key);
        } catch (TimeoutException e) {
            log.error("Not found element {}", locator);
            throw e;
        }
    }
}