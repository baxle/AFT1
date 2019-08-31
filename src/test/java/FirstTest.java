import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;


public class FirstTest {
    private static WebDriver driver;
    private static String baseUrl;
    private Wait<WebDriver> wait;
    private WebDriverWait wait1;



    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");

        driver = new ChromeDriver();
        baseUrl = "https://www.rgs.ru/";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }


    @Test
    public void testInsurance() throws Exception {
        driver.get(baseUrl);
        driver.findElement(By.xpath("//li[@class='dropdown adv-analytics-navigation-line1-link current']/a[contains(.,'Страхование')]")).click();
        driver.findElement(By.xpath("//div[@class='grid rgs-main-menu-links']//a[contains(.,'ДМС')]")).click();

        wait = new WebDriverWait(driver, 10, 1500);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//a[contains(.,'Отправить заявку')]"))));


        assertEquals("Добровольное медицинское страхование",
                driver.findElement(By.xpath("//strong[.='Добровольное медицинское страхование']")).getText());

        driver.findElement(By.xpath("//a[contains(.,'Отправить заявку')]")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));

        assertEquals("Заявка на добровольное медицинское страхование", driver.findElement(By.xpath("//b[.='Заявка на добровольное медицинское страхование']")).getText());
        wait1 = new WebDriverWait(driver,50);
        fillField(By.name("FirstName"), "Владимир");
        fillField(By.name("LastName"), "Путин");
        fillField(By.name("MiddleName"), "Владимирович");

        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Москва");

        fillField(By.xpath("//form[@id='applicationForm']//div[5]/input[@class='form-control']"), "8005553535");
        fillField(By.name("Email"), "qwertyqwerty");
        fillField(By.name("ContactDate"), "12112019" + "\n");
        fillField(By.name("Comment"), "Без комментариев.");


        driver.findElement(By.xpath("//input[@class='checkbox']")).click();
        driver.findElement(By.xpath("//button[@id='button-m']")).click();


        assertEquals("Владимир", driver.findElement(By.name("FirstName")).getAttribute("value"));
        assertEquals("Путин", driver.findElement(By.name("LastName")).getAttribute("value"));
        assertEquals("Владимирович", driver.findElement(By.name("MiddleName")).getAttribute("value"));

        assertEquals("qwertyqwerty", driver.findElement(By.name("Email")).getAttribute("value"));
        assertEquals("Без комментариев.", driver.findElement(By.name("Comment")).getAttribute("value"));

        assertEquals("Москва",
                new Select(driver.findElement(By.name("Region"))).getAllSelectedOptions().get(0).getText());


        assertEquals("Введите адрес электронной почты",
                driver.findElement(By.xpath("//span[@class='validation-error-text']")).getText());
    }

     @After
     public void tearDown() throws Exception {
        driver.quit();
      }


    private void fillField(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
        wait1.until(ExpectedConditions.elementToBeClickable(locator));
    }


}



