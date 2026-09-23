package tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.junit.jupiter.api.Assertions.assertTrue;

import pages.MainPage;
import pages.OrderPage;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class OrderTest {

    private WebDriver driver;
    private MainPage mainPage;
    private OrderPage orderPage;

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

        // Объекты Page Object создаются только после инициализации driver
        mainPage = new MainPage(driver);
        orderPage = new OrderPage(driver);

        mainPage.open();
    }

    @ParameterizedTest
    @MethodSource("orderData")
    void createOrder(
            boolean useTopButton,
            String firstName,
            String lastName,
            String address,
            String metroStation,
            String phone,
            String date,
            String rentalPeriod,
            String comment,
            boolean chooseBlackColor
    ) {
        if (useTopButton) {
            mainPage.clickTopOrderButton();
        } else {
            mainPage.clickBottomOrderButton();
        }

        orderPage.fillPersonalData(
                firstName,
                lastName,
                address,
                metroStation,
                phone
        );

        orderPage.fillRentalData(
                date,
                rentalPeriod,
                comment,
                chooseBlackColor
        );

        orderPage.confirmOrder();

        assertTrue(
                orderPage.getOrderStatusText().contains("Заказ оформлен"),
                "Сообщение об успешном создании заказа не появилось"
        );
    }

    static Stream<Arguments> orderData() {
        return Stream.of(
                Arguments.of(
                        true,
                        "Иван",
                        "Иванов",
                        "ул. Тверская, 1",
                        "Тверская",
                        "89991234567",
                        "01.10.2026",
                        "сутки",
                        "Позвонить за час",
                        true
                ),
                Arguments.of(
                        false,
                        "Анна",
                        "Петрова",
                        "ул. Арбат, 10",
                        "Арбатская",
                        "89997654321",
                        "02.10.2026",
                        "двое суток",
                        "Оставить у двери",
                        false
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
