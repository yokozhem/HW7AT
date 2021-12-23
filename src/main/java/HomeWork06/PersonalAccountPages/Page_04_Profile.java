package HomeWork06.PersonalAccountPages;

import HomeWork06.BaseClassForPages;
import HomeWork06.PageElements.SidebarMenu;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;



public class Page_04_Profile extends BaseClassForPages {

    // Объект меню
    private SidebarMenu sidebarMenu;

    // Заголовок "Имя пользователя"
    @FindBy(xpath = "//h1[@class=\"simple_page__name\"]")
    private WebElement h1Username;

    // Поле "Компания:"
    @FindBy(xpath = "//p[contains(., 'Компания')]/span")
    private WebElement spanCompanyName;

    // Поле "Должность:"
    @FindBy(xpath = "//p[contains(., 'Должность')]/span")
    private WebElement spanPositionName;

    // Конструктор класса
    public Page_04_Profile(ChromeDriver webDriver) {
        super(webDriver);
        sidebarMenu = new SidebarMenu(webDriver);
    }

    // Метод возвращающий имя пользователя
    public String getUsername() {
        return h1Username.getText();
    }

    // Метод возвращающий наименование компании
    public String getCompanyName() {
        return spanCompanyName.getText();
    }

    // Метод возвращающий наименование должности
    public String getPositionName() {
        return spanPositionName.getText();
    }

    // Геттер на меню
    public SidebarMenu getSidebarMenu() {
        return sidebarMenu;
    }
}
