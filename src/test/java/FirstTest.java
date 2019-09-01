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
        //  driver.findElement(By.xpath("//li[contains(@class,'navigation-line1')]/a")).click()
        driver.findElement(By.xpath("//*[contains(text(),'Страхование')]")).click();
        driver.findElement(By.xpath("//*[contains(text(),'ДМС')]")).click();


        wait = new WebDriverWait(driver, 10, 1500);
        wait.until(ExpectedConditions.visibilityOf(
                driver.findElement(By.xpath("//*[contains(@class,'btn')][contains(text(),'Отправить заявку')]"))));


        assertEquals("Добровольное медицинское страхование",
                driver.findElement(By.xpath("//strong[.='Добровольное медицинское страхование']")).getText());

        driver.findElement(By.xpath("//*[contains(@class,'btn')][contains(text(),'Отправить заявку')]")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='modal-title']"))));

        assertEquals("Заявка на добровольное медицинское страхование", driver.findElement(By.xpath("//*[contains(text(),'Заявка на добровольное медицинское страхование')]")).getText());
        wait1 = new WebDriverWait(driver, 10);
        fillField(By.name("FirstName"), "Владимир");
        fillField(By.name("LastName"), "Путин");
        fillField(By.name("MiddleName"), "Владимирович");

        new Select(driver.findElement(By.name("Region"))).selectByVisibleText("Москва");



        fillField(By.name("Email"), "qwertyqwerty");


        do{
            fillField(By.name("ContactDate"), "12122019"+"\n");
            System.out.println(driver.findElement(By.name("ContactDate")).getAttribute("value"));
        }
       while ((driver.findElement(By.name("ContactDate")).getAttribute("value")) != "12122019");

      // fillField(By.name("ContactDate"), "12122019"+"\n");


        fillField(By.xpath("//div[5]//input[1]"), "8005553535");
        fillField(By.name("Comment"), "Без комментариев.");

        driver.findElement(By.xpath("//input[@class='checkbox']")).click();
        driver.findElement(By.xpath("//button[@id='button-m']")).click();


        assertEquals("Владимир", driver.findElement(By.name("FirstName")).getAttribute("value"));
        assertEquals("Путин", driver.findElement(By.name("LastName")).getAttribute("value"));
        assertEquals("Владимирович", driver.findElement(By.name("MiddleName")).getAttribute("value"));
        assertEquals("+7 (800) 555-35-35", driver.findElement(By.xpath("//div[5]//input[1]")).getAttribute("value"));
        assertEquals("qwertyqwerty", driver.findElement(By.name("Email")).getAttribute("value"));
        assertEquals("Без комментариев.", driver.findElement(By.name("Comment")).getAttribute("value"));

        assertEquals("Москва",
                new Select(driver.findElement(By.name("Region"))).getAllSelectedOptions().get(0).getText());


        assertEquals("Введите адрес электронной почты",
                driver.findElement(By.xpath("//span[@class='validation-error-text']")).getText());
        Assert.assertEquals("12.12.2019", driver.findElement(By.name("ContactDate")).getAttribute("value"));
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



