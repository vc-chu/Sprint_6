package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class MainPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Главная страница
    private final By page = By.tagName("body");

    // Кнопка «Заказать» сверху
    private final By topOrderButton = By.xpath(
            "(//button[text()='Заказать'])[1]"
    );

    // Кнопка «Заказать» снизу
    private final By bottomOrderButton = By.xpath(
            "(//button[text()='Заказать'])[2]"
    );

    // Вопросы
    private final By faqQuestions = By.className("accordion__button");

    // Ответы
    private final By faqAnswers = By.className("accordion__panel");

    // Cookie-панель
    private final By cookieBanner = By.className(
            "App_CookieConsent__1yUIN"
    );

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void open() {
        driver.get("https://qa-scooter.education-services.ru/");

        wait.until(ExpectedConditions.visibilityOfElementLocated(page));

        closeCookieBanner();
    }

    public void closeCookieBanner() {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
                "const banner = document.querySelector('.App_CookieConsent__1yUIN');" +
                        "if (banner) {" +
                        "    banner.remove();" +
                        "}"
        );

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                cookieBanner
        ));
    }

    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                topOrderButton
        )).click();
    }

    public void clickBottomOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                bottomOrderButton
        )).click();
    }

    public void clickFaqQuestion(int index) {
        wait.until(ExpectedConditions.elementToBeClickable(
                faqQuestions
        ));

        driver.findElements(faqQuestions)
                .get(index)
                .click();
    }

    public String getFaqAnswer(int index) {
        List<WebElement> answers = driver.findElements(faqAnswers);

        return wait.until(ExpectedConditions.visibilityOf(
                answers.get(index)
        )).getText();
    }
}
