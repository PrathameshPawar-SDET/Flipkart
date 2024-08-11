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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.security.PrivateKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Collectors;


import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    private WebDriver driver;
    private WebDriverWait wait;


    @FindBy(xpath = "//input[contains(@placeholder,'Search for Products')]")
    private WebElement searchElement;

    @FindBy(xpath = "//button[contains(@title,'Search for Products')]")
    private WebElement SearchButton;

    @FindBy(xpath = "//div[text()='Popularity']")
    private WebElement popularity;

    @FindBy(xpath = "//span[contains(@id,'productRating')]")
    private List<WebElement> rating;

    @FindBy(xpath = "(//div[@class='QCKZip hpLdC3'])[1]")
    private WebElement fourandabove;

    @FindBy(xpath = "//div[@class='_34xRTS NXhch9']//span[contains(text(),'Login')]")
    private WebElement Loginmodal;

    @FindBy(xpath = "//div[@class='JFPqaw']//span[@role='button']")
    private WebElement closebutton;

//     @FindBy(xpath = "(//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')]//div[@class='_75nlfW']")
//     private  List<WebElement> Productsxpath;

    /*
     * TODO: Write your tests here with testng @Test annotation.
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */

        @Test
    public void testCase01() {
        System.out.println("Start with testcase 01");
        driver.get("https://www.flipkart.com/");

        Wrappers.sendkeys(driver, searchElement, "Washing Machine");
        Wrappers.click(driver, SearchButton);
        Wrappers.click(driver, popularity);
        int count = Wrappers.count(driver, rating);

        System.out.println("Count of items with rating less than or equal to 4 stars: " + count);

    }

        @Test
    public void testCase02() throws InterruptedException {
        System.out.println("Start with testcase 02");
        driver.get("https://www.flipkart.com/");
        Thread.sleep(2000);
        try {
            if (Loginmodal.isDisplayed()) {
                closebutton.click();
            } else {
                System.out.println("Modal not found");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        Wrappers.sendkeys(driver, searchElement, "iPhone");
        Wrappers.click(driver, SearchButton);
        Thread.sleep(2000);
        //    (//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')][1]//div[@class='_75nlfW']

        List<WebElement> Products = driver.findElements(By.xpath("(//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')]//div[@class='_75nlfW']"));
        boolean condition = false;

        for (int i = 1; i <= Products.size(); i++) {
            try {

                WebElement Title = driver.findElement(By.xpath("(//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')][" + i + "]//div[@class='_75nlfW']//div[contains(@class,'KzDlHZ')]"));
                WebElement Discount = driver.findElement(By.xpath("(//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')][" + i + "]//div[@class='_75nlfW']//div[contains(@class,'UkUFwK')]"));

                String titleText = Title.getText();
                String discountText = Discount.getText();

                int discountpercentage = Integer.parseInt(discountText.replaceAll("[^0-9]", ""));
                if (discountpercentage > 17) {
                    System.out.println("Title: " + titleText + " have Discount of: " + discountpercentage + " %");
                    condition = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (!condition) {
            System.out.println("No product found with discount more than 17%");
        }


    }

    @Test
    public void testCase03() throws InterruptedException {
        System.out.println("Start with testcase 03");
        driver.get("https://www.flipkart.com/");

        Wrappers.sendkeys(driver, searchElement, "Coffee Mug");
        Wrappers.click(driver, SearchButton);
        Wrappers.click(driver, fourandabove);
        Thread.sleep(2000);

        List<WebElement> ProductElements = driver.findElements(By.xpath("(//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')]//div[contains(@data-id,'MUG')]"));


        List<Map<String, Object>> products = new ArrayList<>();

        for (WebElement ProductElement : ProductElements) {
            try {

                WebElement TitleElement = ProductElement.findElement(By.xpath(".//a[@class='wjcEIp']"));
                String titlevalue = TitleElement.getAttribute("title");
                WebElement ImageElement = ProductElement.findElement(By.xpath(".//img[@class='DByuf4']"));
                String imageURL = ImageElement.getAttribute("src");

                WebElement ReviewElement = null;
                try {
                    ReviewElement = ProductElement.findElement(By.xpath(".//span[@class='Wphh3N']"));
                } catch (Exception e) {
                    System.out.println("No review element found for title: " + titlevalue);
                }

                int reviewcount = 0;
                if (ReviewElement != null) {
                    String review = ReviewElement.getText();
                    if (!review.trim().isEmpty()) {
                        try {
                            reviewcount = Integer.parseInt(review.replaceAll("[^0-9]", ""));
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid Review count :" + review);
                        }
                    }
                } else {
                    System.out.println("Review count is not available for product with title: " + titlevalue);
                }

                Map<String, Object> ProductDetails = new HashMap<>();
                ProductDetails.put("title", titlevalue);
                ProductDetails.put("imageurl", imageURL);
                ProductDetails.put("review", reviewcount);

                products.add(ProductDetails);

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
//        System.out.println("Products List: " + products);
        List<Map<String, Object>> topProducts = products.stream()
                .filter(product -> product.get("review") != null)
                .sorted((p1, p2) -> {
                    Integer reviewCount1 = (Integer) p1.get("review");
                    Integer reviewCount2 = (Integer) p2.get("review");
                    if (reviewCount1 == null) reviewCount1 = 0;
                    if (reviewCount2 == null) reviewCount2 = 0;
                    return Integer.compare(reviewCount2, reviewCount1);
                })
                .limit(5)
                .collect(Collectors.toList());

        if (!topProducts.isEmpty()) {
            for (Map<String, Object> product : topProducts) {
                System.out.println("Title: " + product.get("title") + " - Image URL: " + product.get("imageurl") + " - Review Count: " + product.get("review"));
            }
        } else {
            System.out.println("No products found with reviews.");
        }
    }


    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser() {
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
        PageFactory.initElements(new AjaxElementLocatorFactory(driver, 5), this);
    }

    @AfterTest
    public void endTest() {
        driver.close();
        driver.quit();

    }
}