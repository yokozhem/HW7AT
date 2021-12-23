package HomeWork06.PageElements;

import HomeWork06.BaseClassForPages;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;



public class SidebarMenu extends BaseClassForPages {

    // Конструктор класса
    public SidebarMenu(ChromeDriver webDriver) {
        super(webDriver);
    }

    // Лист элементов бокового меню
    @FindBy(xpath = "//div[@class=\"account_menu hide_mobile\"]//span[@class=\"account_menu__name\"]")
    private List<WebElement> menuList;

    // Геттер на лист меню
    public List<WebElement> getMenuList() {
        return menuList;
    }

    // Метод наведения курсора на пункт меню
//    public void hoverMouseOnSidebarMenuItem(String itemName) {
//        Actions actions = new Actions(getWebDriver());
//
//        WebElement item = menuList
//                .stream() // Поток элементов коллекции (листа)
//                .filter(element -> element.getText().equals(itemName)) // Фильтр по названию элемента
//                .findFirst() // Первый найденный элемент коллекции
//                .get(); // возвращает найденный элемент
//
//        actions
//                .moveToElement(item) // (наведение мыши на найденный элемент (WebElement item)
//                .build()
//                .perform();
//    }

    // Метод перехода в пункт меню
//    public void goToSidebarMenuItem(String itemName) {
//
//        WebElement item = menuList
//                .stream() // Поток элементов коллекции (листа)
//                .filter(element -> element.getText().equals(itemName)) // Фильтр по названию элемента
//                .findFirst() // Первый найденный элемент коллекции
//                .get(); // возвращает найденный элемент
//
//        item.click();
//    }

    // Метод перехода в пункт меню
    public void goToSidebarMenuItem(int pageNumber) {
        menuList.get(pageNumber - 1).click();
    }

    // Метод выхода из учётной записи
    public void logout() {
        menuList.get(menuList.size() - 1).click();
    }
}
