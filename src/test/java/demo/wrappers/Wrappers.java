package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */

    WebDriver driver;

    public static boolean click(WebDriver driver, WebElement element){
        try{
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            Actions act = new Actions(driver);
            act.moveToElement(element);
            Thread.sleep(5000);
            act.click(element).perform();
            return true;
        }catch (Exception e){
            e.printStackTrace();
            try{
                element.click();
                return true;
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
        return false;
    }


    public static boolean sendkeys(WebDriver driver, WebElement inputbox, String keystosend){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(inputbox));
        try {
            inputbox.sendKeys(keystosend);
            return true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

    public static int count(WebDriver driver, List<WebElement> Ratings){

        int count =0;
        for (WebElement rating : Ratings){
            try{
                double ratingvalue =Double.parseDouble(rating.getText().split(" ")[0]);
                if(ratingvalue<=4.0){
                    count++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return count;
    }

 //   (//div[@class='DOjaWF gdgoEp'])[1]//div[contains(@class,'cPHDOP ')][2]//div[@class='_75nlfW']

//    public static int productcount(WebDriver driver, List<WebElement> Products, WebElement title, WebElement rating){
//        int count = 0;
//
//        for(int i =1;i<=Products.size();i++){
//            try{
//
//
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
//    }


}
