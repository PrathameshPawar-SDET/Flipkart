package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.security.PrivateKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
     private WebDriver driver;
     private WebDriverWait wait;


     @FindBy(xpath = "//input[contains(@placeholder,'Search for Products')]")
     private WebElement searchElement;

     @FindBy(xpath = "//button[contains(@title,'Search for Products')]")
     private  WebElement SearchButton;

     @FindBy(xpath = "//div[text()='Popularity']")
     private  WebElement popularity;

     @FindBy(xpath = "//span[contains(@id,'productRating')]")
     private  List<WebElement> rating;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

    @Test
    public void testCase01(){
        System.out.println("Start with testcase 01");
        driver.get("https://www.flipkart.com/");

        Wrappers.sendkeys(driver,searchElement,"Washing Machine");
        Wrappers.click(driver,SearchButton);
        Wrappers.click(driver, popularity);
        int count = Wrappers.count(driver, rating);

        System.out.println("Count of items with rating less than or equal to 4 stars: "+count);







    }


    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,10), this);
    }

    @AfterTest
    public void endTest()
    {
//        driver.close();
//        driver.quit();

    }
}