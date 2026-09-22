package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class OrderPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    // Поле «Имя»
    private final By firstNameField = By.cssSelector(
            "input[placeholder='* Имя']"
    );

    // Поле «Фамилия»
    private final By lastNameField = By.cssSelector(
            "input[placeholder='* Фамилия']"
    );

    // Поле «Адрес»
    private final By addressField = By.cssSelector(
            "input[placeholder='* Адрес: куда привезти заказ']"
    );

    // Поле «Станция метро»
    private final By metroStationField = By.cssSelector(
            "input[placeholder='* Станция метро']"
    );

    // Поле «Телефон»
    private final By phoneField = By.cssSelector(
            "input[placeholder='* Телефон: на него позвонит курьер']"
    );

    // Кнопка «Далее»
    private final By nextButton = By.xpath(
            "//button[text()='Далее']"
    );

    // Поле даты аренды
    private final By dateField = By.cssSelector(
            "input[placeholder='* Когда привезти самокат']"
    );

    // Календарь
    private final By datePicker = By.className(
            "react-datepicker"
    );

    // Поле срока аренды
    private final By rentalPeriodField = By.className(
            "Dropdown-placeholder"
    );

    // Цвет «чёрный жемчуг»
    private final By blackColor = By.id("black");

    // Цвет «серый безымянный»
    private final By greyColor = By.id("grey");

    // Поле комментария для курьера
    private final By commentField = By.cssSelector(
            "input[placeholder='Комментарий для курьера']"
    );

    // Кнопка «Заказать» в форме
    private final By orderButton = By.xpath(
            "//div[contains(@class, 'Order_Buttons')]" +
                    "//button[text()='Заказать']"
    );

    // Кнопка подтверждения заказа
    private final By confirmButton = By.xpath(
            "//button[text()='Да']"
    );

    // Окно с подтверждением заказа
    private final By successModal = By.className(
            "Order_Modal__YZ-d3"
    );

    public OrderPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(10)
        );
    }

    // Заполнение первой части формы
    public void fillPersonalData(
            String firstName,
            String lastName,
            String address,
            String metroStation,
            String phone
    ) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                firstNameField
        )).sendKeys(firstName);

        driver.findElement(lastNameField)
                .sendKeys(lastName);

        driver.findElement(addressField)
                .sendKeys(address);

        WebElement metroField = wait.until(
                ExpectedConditions.elementToBeClickable(
                        metroStationField
                )
        );

        metroField.click();
        metroField.sendKeys(metroStation);

        By metroOption = By.xpath(
                "//div[contains(@class, 'select-search__select')]"
        );

        wait.until(ExpectedConditions.elementToBeClickable(
                metroOption
        )).click();

        driver.findElement(phoneField)
                .sendKeys(phone);

        wait.until(ExpectedConditions.elementToBeClickable(
                nextButton
        )).click();
    }

    // Заполнение второй части формы
    public void fillRentalData(
            String date,
            String rentalPeriod,
            String comment,
            boolean chooseBlackColor
    ) {
        WebElement dateInput = wait.until(
                ExpectedConditions.elementToBeClickable(
                        dateField
                )
        );

        dateInput.click();
        dateInput.clear();
        dateInput.sendKeys(date);

        // Закрываем календарь
        dateInput.sendKeys(Keys.ESCAPE);

        // Если календарь не закрылся клавишей Escape,
        // снимаем фокус с поля через JavaScript
        if (!driver.findElements(datePicker).isEmpty()) {
            ((JavascriptExecutor) driver).executeScript(
                    "document.activeElement.blur();"
            );
        }

        // Дожидаемся исчезновения календаря
        wait.until(ExpectedConditions.invisibilityOfElementLocated(
                datePicker
        ));

        // Открываем список срока аренды
        wait.until(ExpectedConditions.elementToBeClickable(
                rentalPeriodField
        )).click();

        By rentalOption = By.xpath(
                "//div[contains(@class, 'Dropdown-option') " +
                        "and normalize-space()='" + rentalPeriod + "']"
        );

        wait.until(ExpectedConditions.elementToBeClickable(
                rentalOption
        )).click();

        // Выбираем цвет самоката
        if (chooseBlackColor) {
            wait.until(ExpectedConditions.elementToBeClickable(
                    blackColor
            )).click();
        } else {
            wait.until(ExpectedConditions.elementToBeClickable(
                    greyColor
            )).click();
        }

        driver.findElement(commentField)
                .sendKeys(comment);

        wait.until(ExpectedConditions.elementToBeClickable(
                orderButton
        )).click();
    }

    // Подтверждение заказа
    public void confirmOrder() {
        wait.until(ExpectedConditions.elementToBeClickable(
                confirmButton
        )).click();
    }

    // Проверка окна успешного создания заказа
    public boolean isOrderCreated() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        successModal
                )
        ).isDisplayed();
    }
}
