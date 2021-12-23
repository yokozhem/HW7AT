package HomeWork06;

import HomeWork06.PageElements.SidebarMenu;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;



public abstract class BaseClassForPages {

    // Создаём экземпляр драйвера для возможности доступа к нему по всему классу (!!! приватность нужна или нет)
    private ChromeDriver webDriver;

    // Создаём конструктор
    public BaseClassForPages(ChromeDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this); // !!! разобраться с кодом
    }

    public ChromeDriver getWebDriver() {
        return webDriver;
    }
}
