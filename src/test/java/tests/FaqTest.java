package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.MainPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FaqTest {

    private WebDriver driver;
    private MainPage mainPage;

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();

        options.addArguments(
                "--no-sandbox",
                "--headless=new",
                "--disable-dev-shm-usage",
                "--window-size=1920,1080"
        );

        driver = new ChromeDriver(options);

        mainPage = new MainPage(driver);
        mainPage.open();
    }

    @ParameterizedTest
    @MethodSource("faqData")
    void checkFaqAnswer(int questionIndex, String expectedAnswer) {
        mainPage.clickFaqQuestion(questionIndex);

        assertEquals(
                expectedAnswer,
                mainPage.getFaqAnswer(questionIndex)
        );
    }

    static Stream<Arguments> faqData() {
        return Stream.of(
                Arguments.of(
                        0,
                        "Сутки — 400 рублей. Оплата курьеру — наличными или картой."
                ),
                Arguments.of(
                        1,
                        "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто оформить несколько заказов — один за другим."
                )
        );
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}

