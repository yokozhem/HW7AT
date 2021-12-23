package HomeWork06;

import HomeWork06.PersonalAccountPages.Page_04_Profile;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;



public class LoginPage extends BaseClassForPages {

    // Создаём конструктор
    public LoginPage(ChromeDriver webDriver) {
        super(webDriver);
    }

    // Заголовок "Вход в личный кабинет"
    @FindBy(xpath = "//h1[@class=\"lk_popup__name\"]")
    private WebElement h1Heading;

    // Поле ввода "E-mail"
    @FindBy(id = "email")
    private WebElement inputEmail;

    // Поле ввода "Пароль"
    @FindBy(id = "password")
    private WebElement inputPassword;

    // Кнопка "Войти"
    @FindBy(xpath = "//button[@type=\"submit\"]")
    private WebElement buttonEntry;

    // Уведомление "Заполните e-mail"
    @FindBy(id = "email-error")
    private WebElement labelEmailError;

    // Уведомление "Заполните пароль"
    @FindBy(id = "password-error")
    private WebElement labelPasswordError;

    // Метод заполнения поля "E-mail"
    @Step("Выполняем заполнение поля \"E-mail\"") // Добавляем в отчёт информацию с помощью Assertj
    public void fillEmail(String email) {
        inputEmail.sendKeys(email);
    }

    // Метод заполнения поля "Пароль"
    @Step("Выполняем заполнение поля \"Пароль\"") // Добавляем в отчёт информацию с помощью Assertj
    public void fillPassword(String password) {
        inputPassword.sendKeys(password);
    }

    // Метод входа в ЛК
    public Page_04_Profile loginToYourAccount(String email, String password) {
        fillEmail(email);
        fillPassword(password);
        buttonEntry.click();
        return new Page_04_Profile(getWebDriver()); // Возвращает страницу "Мой профиль"
    }

    // Геттеры на уведомления
    public WebElement getLabelEmailError() {
        return labelEmailError;
    }
    public WebElement getLabelPasswordError() {
        return labelPasswordError;
    }
}
