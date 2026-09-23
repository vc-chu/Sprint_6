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

    // URL главной страницы сервиса
    public static final String SCOOTER_URL =
            "https://qa-scooter.education-services.ru/";

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Главная страница
    private final By page = By.tagName("body");

    // Кнопка «Заказать» в верхней части страницы
    private final By topOrderButton = By.xpath(
            "(//button[text()='Заказать'])[1]"
    );

    // Кнопка «Заказать» в нижней части страницы
    private final By bottomOrderButton = By.xpath(
            "(//button[text()='Заказать'])[2]"
    );

    // Вопросы раздела «Вопросы о важном»
    private final By faqQuestions = By.className(
            "accordion__button"
    );

    // Cookie-панель
    private final By cookieBanner = By.className(
            "App_CookieConsent__1yUIN"
    );

    public MainPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    // Открыть главную страницу
    public void open() {
        driver.get(SCOOTER_URL);

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                page
        ));

        closeCookieBanner();
    }

    // Удалить cookie-панель, если она перекрывает элементы страницы
    public void closeCookieBanner() {
        List<WebElement> banners = driver.findElements(cookieBanner);

        if (!banners.isEmpty() && banners.get(0).isDisplayed()) {
            ((JavascriptExecutor) driver).executeScript(
                    "arguments[0].remove();",
                    banners.get(0)
            );
        }

        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                cookieBanner
        ));
    }

    // Кликнуть по верхней кнопке «Заказать»
    public void clickTopOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                topOrderButton
        )).click();
    }

    // Кликнуть по нижней кнопке «Заказать»
    public void clickBottomOrderButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                bottomOrderButton
        )).click();
    }

    // Кликнуть по вопросу из FAQ по его порядковому номеру
    public void clickFaqQuestion(int index) {
        List<WebElement> questions = wait.until(
                ExpectedConditions.visibilityOfAllElementsLocatedBy(
                        faqQuestions
                )
        );

        wait.until(ExpectedConditions.elementToBeClickable(
                questions.get(index)
        )).click();
    }

    // Сформировать локатор ответа FAQ по номеру вопроса
    private By getFaqAnswerLocator(int index) {
        return By.id("accordion__panel-" + index);
    }

    // Получить текст открытого ответа FAQ
    public String getFaqAnswer(int index) {
        By answerLocator = getFaqAnswerLocator(index);

        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                answerLocator
        )).getText();
    }
}