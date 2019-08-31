import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class SecondTest {

    private static WebDriver driver;
    private static String baseUrl;
    private Wait<WebDriver> wait;
    private WebDriverWait wait1;

    @Before
    public void setUp() throws Exception {

        System.setProperty("webdriver.chrome.driver", "drv/chromedriver.exe");

        driver = new ChromeDriver();
        baseUrl = "https://www.sberbank.ru/ru/person";
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, 10, 1500);
        wait1 = new WebDriverWait(driver,50);
    }


    @Test
    public void testInsurance() throws Exception {


        driver.get(baseUrl);
        driver.findElement(By.xpath("//div[@class='paste-region__region header__region header__region_77']//span[.='Москва']")).click();

        wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h4[@class='kit-grid-modal__header']"))));
       fillField(By.xpath("//div[@class='kit-input kit-input_focused']/input[@class='kit-input__control']"), "Нижегородская область");

       driver.findElement(By.xpath("//a[.='Нижегородская область']")).click();

        assertEquals("Нижегородская область", driver.findElement(By.xpath("//div[@class='paste-region__region header__region header__region_52']//span[.='Нижегородская область']")).getText());

        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight);");

        driver.findElement(By.xpath("//UL[@class='footer__social']/self::UL")).isEnabled();
    }




    private void fillField(By locator, String value) {
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(value);
        wait1.until(ExpectedConditions.elementToBeClickable(locator));
    }
}
