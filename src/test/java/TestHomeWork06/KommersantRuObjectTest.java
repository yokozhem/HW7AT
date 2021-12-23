package TestHomeWork06;

import HomeWork06.LoginPage;
import HomeWork06.PageElements.SidebarMenu;
import HomeWork06.PersonalAccountPages.*;
import HomeWork06.TestData.TestData;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;



public class KommersantRuObjectTest {

    // Создаём экземпляр драйвера для возможности доступа к нему по всему классу
    private static ChromeDriver webDriver;

    // Создаём экземпляр опций для браузера
    private static ChromeOptions chromeOptions;

    // Создаём экземпляр ожиданий
    private static WebDriverWait webDriverWait;

    // Создаём экземпляр логера
    private static Logger logger = LoggerFactory.getLogger(KommersantRuObjectTest.class);

    // Создаём экземпляр тестовых данных
    private static TestData testData = new TestData();

    @BeforeAll
    static void registerDriver() {

        // Инициализируем драйвер менеджер
        WebDriverManager.chromedriver().setup();

        // Создаём экземпляр класса ChromeOptions (!!! Утонить необходимость)
        chromeOptions = new ChromeOptions();

        /* Передаём в объект chromeOptions настройки для браузера
        --no-sandbox - для работы в докер-контейнере для Chrome
        start-maximized - запуск окна "на весь экран"
        --disable-notification - отключение всплывающих окон
        user-agent=Googlebot/2.1 (+http://www.google.com/bot.html) - запуск в режиме поискового бота
        --incognito - запуск окна в режиме инкогнито
        .setPageLoadTimeout(Duration.ofSeconds(10)) - максимальное время ожидания загрузки страницы
         */
        chromeOptions
                .addArguments("--no-sandbox")
                .addArguments("start-maximized")
                .addArguments("--disable-notification")
                .addArguments("user-agent=Googlebot/2.1 (+http://www.google.com/bot.html)")
                .addArguments("--incognito")
                .setPageLoadTimeout(Duration.ofSeconds(10));
    }

    @BeforeEach
    void setUpBrowser() {

        // Создаём экземпляр класса webdriver
        webDriver = new ChromeDriver(chromeOptions);

        // Implicit Wait (Неявное ожидание) - выставляем 5 секунд (!!! обязательно определяем в коде)
        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test
    @DisplayName("01 Проверка доступности главной страницы")
    void homePageLoaded() {

        logger.info("\n[INFO] Проверка доступности главной страницы");

        Assertions
                .assertDoesNotThrow(()-> webDriver
                        .navigate()
                        .to("https://www.kommersant.ru/"), "Страница недоступна");
    }

    @Test
    @DisplayName("02 Позитивная проверка авторизации на сайте")
    void PositiveAuthorizationCheck() {

        logger.info("\n[INFO] Проверка авторизации в личном кабинете");
        logger.debug("\n[DEBUG] Проверяем доступность страницы авторизации");
        Assertions
                .assertDoesNotThrow(()-> webDriver
                                .navigate()
                                .to("https://www.kommersant.ru/lk/login"),
                        "Страница авторизации недоступна");



        logger.debug("\n[DEBUG] Создаём экземпляр страницы авторизации");
        LoginPage loginPage = new LoginPage(webDriver);

        logger.info("\n[INFO] Переходим в личный кабинет");
        logger.debug("\n[DEBUG] Заполняем поля \"Ваш e-mail\" и \"Введите пароль\"" +
                "\n[DEBUG] Тестовые учётные данные: логин tinetoon@mail.ru, пароль te$st");
        Page_04_Profile page04Profile = loginPage.loginToYourAccount(testData.getLOGIN(), testData.getPASSWORD());

        logger.debug("\n[DEBUG] Проверяем имя пользователя \"qr\" на странице ЛК");
        Assertions.assertEquals(page04Profile.getUsername(), "qr",
                "Имя пользователя не соответствует значению по умолчанию");
    }

    @Test
    @DisplayName("03 Негативная проверка авторизации на сайте (пустые поля)")
    void NegativeAuthorizationCheck() {

        logger.info("\n[INFO] Проверка возможности авторизации в личном кабинете с пустыми полями e-mail & password");
        Assertions
                .assertDoesNotThrow(()-> webDriver
                                .navigate()
                                .to("https://www.kommersant.ru/lk/login"),
                        "Страница авторизации недоступна");

        logger.debug("\n[DEBUG] Создаём экземпляр страницы авторизации");
        LoginPage loginPage = new LoginPage(webDriver);

        logger.info("\n[INFO] Переходим в личный кабинет" +
                "\n[INFO] Поля e-mail & password оставляем не заполненными");
        loginPage.loginToYourAccount("","");
        Assertions.assertEquals(true, loginPage.getLabelEmailError().isDisplayed());
        Assertions.assertEquals(true, loginPage.getLabelPasswordError().isDisplayed());
    }

    @Nested
    class PersonalAccountEditingTest {

        SidebarMenu sidebarMenu;
        Page_01_Subscriptions page_01_subscriptions;
        Page_02_Bookmarks page_02_bookmarks;
        Page_03_ViewHistory page_03_viewHistory;
        Page_04_Profile page_04_profile;
        Page_05_ProfileEdit page_05_profileEdit;
        Page_06_SubscriptionSettings page_06_subscriptionSettings;
        Page_07_Help page_07_help;
        Page_08_Services page_08_services;
        Page_09_Feedback page_09_feedback;

        // Предусловие - авторизация на сайте
        @BeforeEach
        void authorization() {

            logger.info("\n[INFO] Выполнение предусловия - авторизация в личном кабинете");
            logger.debug("\n[DEBUG] Проверяем доступность страницы авторизации");
            Assertions
                    .assertDoesNotThrow(()-> webDriver
                                    .navigate()
                                    .to("https://www.kommersant.ru/lk/login"),
                            "Страница авторизации недоступна");

            logger.debug("\n[DEBUG] Создаём экземпляр страницы авторизации");
            LoginPage loginPage = new LoginPage(webDriver);

            logger.info("\n[INFO] Переходим в личный кабинет");
            logger.debug("\n[DEBUG] Заполняем поля \"Ваш e-mail\" и \"Введите пароль\"" +
                    "\n[DEBUG] Тестовые учётные данные: логин tinetoon@mail.ru, пароль te$st");
            page_04_profile = loginPage.loginToYourAccount(testData.getLOGIN(), testData.getPASSWORD());

            logger.debug("\n[DEBUG] Имя пользователя по умолчанию - \"qr\"");
            Assertions
                    .assertEquals(page_04_profile
                            .getUsername(), "qr",
                            "Имя пользователя не соответствует значению по умолчанию");
        }

        @Test
        @DisplayName("01 Проверка пунктов меню в ЛК")
        void TestingMenuItems() {

            logger.info("\n[INFO] Проверка пунктов меню в ЛК");
            logger.debug("\n[DEBUG] Порядковые номера элементов в листе пунктов меню должны быть следующие: " +
                    "\n[DEBUG] - 0 Уведомления" +
                    "\n[DEBUG] - 1 Избранное" +
                    "\n[DEBUG] - 2 История чтения" +
                    "\n[DEBUG] - 3 Мой профиль" +
                    "\n[DEBUG] - 4 Настройки профиля" +
                    "\n[DEBUG] - 5 Настройки уведомлений" +
                    "\n[DEBUG] - 6 Помощь" +
                    "\n[DEBUG] - 7 Сервисы «Ъ»" +
                    "\n[DEBUG] - 8 Обратная связь" +
                    "\n[DEBUG] - 9 Выход");

            // Инициализируем лист элементов меню
            sidebarMenu = new SidebarMenu(webDriver);

            Assertions
                    .assertEquals("Уведомления", sidebarMenu.getMenuList().get(0)
                            .getText(), "Пункт меню \"Уведомления\" не найден");
            Assertions
                    .assertEquals("Избранное", sidebarMenu.getMenuList().get(1)
                            .getText(), "Пункт меню \"Избранное\" не найден");
            Assertions
                    .assertEquals("История чтения", sidebarMenu.getMenuList().get(2)
                            .getText(), "Пункт меню \"История чтения\" не найден");
            Assertions
                    .assertEquals("Мой профиль", sidebarMenu.getMenuList().get(3)
                            .getText(), "Пункт меню \"Мой профиль\" не найден");
            Assertions
                    .assertEquals("Настройки профиля", sidebarMenu.getMenuList().get(4)
                            .getText(), "Пункт меню \"Настройки профиля\" не найден");
            Assertions
                    .assertEquals("Настройки уведомлений", sidebarMenu.getMenuList().get(5)
                            .getText(), "Пункт меню \"Настройки уведомлений\" не найден");
            Assertions
                    .assertEquals("Помощь", sidebarMenu.getMenuList().get(6)
                            .getText(), "Пункт меню \"Помощь\" не найден");
            Assertions
                    .assertEquals("Сервисы «Ъ»", sidebarMenu.getMenuList().get(7)
                            .getText(), "Пункт меню \"Сервисы «Ъ»\" не найден");
            Assertions
                    .assertEquals("Обратная связь", sidebarMenu.getMenuList().get(8)
                            .getText(), "Пункт меню \"Обратная связь\" не найден");
            Assertions
                    .assertEquals("Выход", sidebarMenu.getMenuList().get(9)
                            .getText(), "Пункт меню \"Выход\" не найден");
        }

        @Test
        @DisplayName("02 Проверка перехода по страницам меню")
        void goToSubscriptionsTest() {

            page_04_profile.getSidebarMenu().goToSidebarMenuItem(1);
            page_01_subscriptions = new Page_01_Subscriptions(webDriver);
            Assertions
                    .assertEquals("https://www.kommersant.ru/LK/Subscriptions",
                            page_01_subscriptions.getWebDriver().getCurrentUrl());

            page_01_subscriptions.getSidebarMenu().goToSidebarMenuItem(2);
            page_02_bookmarks = new Page_02_Bookmarks(webDriver);
            Assertions
                    .assertEquals("https://www.kommersant.ru/LK/Bookmarks",
                            page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_02_bookmarks.getSidebarMenu().goToSidebarMenuItem(3);
            page_03_viewHistory = new Page_03_ViewHistory(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/ViewHistory",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_03_viewHistory.getSidebarMenu().goToSidebarMenuItem(4);
            Assertions.assertEquals("https://www.kommersant.ru/lk/profile",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_04_profile.getSidebarMenu().goToSidebarMenuItem(5);
            page_05_profileEdit = new Page_05_ProfileEdit(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/ProfileEdit",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_05_profileEdit.getSidebarMenu().goToSidebarMenuItem(6);
            page_06_subscriptionSettings = new Page_06_SubscriptionSettings(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/SubscriptionSettings",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_06_subscriptionSettings.getSidebarMenu().goToSidebarMenuItem(7);
            page_07_help = new Page_07_Help(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/Help",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_07_help.getSidebarMenu().goToSidebarMenuItem(8);
            page_08_services = new Page_08_Services(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/Services",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());

            page_08_services.getSidebarMenu().goToSidebarMenuItem(9);
            page_09_feedback = new Page_09_Feedback(webDriver);
            Assertions.assertEquals("https://www.kommersant.ru/LK/Feedback",
                    page_02_bookmarks.getWebDriver().getCurrentUrl());
        }

        @Test
        @DisplayName("03 Проверка редактирования поля \"Компания\" в ЛК")
        void editingTheCompanyField() {
            page_04_profile.getSidebarMenu().goToSidebarMenuItem(5);
            page_05_profileEdit = new Page_05_ProfileEdit(webDriver);
            page_05_profileEdit
                    .fillCompany(testData.getCOMPANY())
                    .getSidebarMenu()
                    .goToSidebarMenuItem(4);
            Assertions.assertEquals(testData.getCOMPANY(),
                    page_04_profile.getCompanyName());
        }

        @Test
        @DisplayName("04 Проверка редактирования поля \"Должность\" в ЛК")
        void editingThePositionField() {
            page_04_profile.getSidebarMenu().goToSidebarMenuItem(5);
            page_05_profileEdit = new Page_05_ProfileEdit(webDriver);
            page_05_profileEdit
                    .fillPosition(testData.getPOSITION())
                    .getSidebarMenu()
                    .goToSidebarMenuItem(4);
            Assertions.assertEquals(testData.getPOSITION(),
                    page_04_profile.getPositionName());
        }

        // Постусловие - выход из учётной записи
        @AfterEach
        void SignOutOfAccount() {

            // Выходим из ЛК
            logger.debug("\n[DEBUG] Выходим из ЛК");
            Assertions
                    .assertDoesNotThrow(()->
                            page_04_profile
                                    .getSidebarMenu()
                                    .logout(),
                                    "Выход из ЛК невозможен");

            // Устанавливаем паузу 2 секунды (!!! необходимо разобраться с ожиданиями)
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Метод запускающийся после каждого теста
    @AfterEach
    void exitTheBrowser() {

        // Закрываем браузер
        webDriver.quit();
    }
}
