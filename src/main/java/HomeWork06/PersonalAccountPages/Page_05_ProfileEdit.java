package HomeWork06.PersonalAccountPages;

import HomeWork06.BaseClassForPages;
import HomeWork06.PageElements.SidebarMenu;
import io.qameta.allure.Step;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;



public class Page_05_ProfileEdit extends BaseClassForPages {

    // Объект меню
    private SidebarMenu sidebarMenu;

    // Наименование страницы "Редактирование профиля"
    @FindBy(xpath = "//h1[@class=\"simple_page__name\"]")
    private WebElement h1PageName;

    // Поле "Компания"
    @FindBy(name = "Company")
    private WebElement fieldCompany;

    // Поле "Должность"
    @FindBy(name = "Position")
    private WebElement fieldPosition;

    // Конструктор класса
    public Page_05_ProfileEdit(ChromeDriver webDriver) {
        super(webDriver);
        sidebarMenu = new SidebarMenu(webDriver);
    }

    // Метод заполнения поля "Компания"
    @Step("Заполняем поле \"Компания\"")
    public Page_05_ProfileEdit fillCompany(String companyName) {
        fieldCompany.clear();
        fieldCompany.sendKeys(companyName);
        fieldCompany.submit();
        return this;
    }

    // Метод заполнения поля "Должность"
    @Step("Заполняем поле \"Должность\"")
    public Page_05_ProfileEdit fillPosition(String positionName) {
        fieldPosition.clear();
        fieldPosition.sendKeys(positionName);
        fieldPosition.submit();
        return this;
    }

    // Геттер наименования страницы
    public String getPageName() {
        return h1PageName.getText();
    }

    public SidebarMenu getSidebarMenu() {
        return sidebarMenu;
    }
}
