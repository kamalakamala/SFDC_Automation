package com.kamala.salesforce;

import com.relevantcodes.extentreports.LogStatus;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.BeforeMethod;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

import java.io.File;


public class ReusableMethods {
    static WebDriver driver;
    static ExtentReports reports;
    static ExtentTest logger;



    //Comparing two Strings
    public static void stringCompare(WebElement obj, String expectedMsg) {
        String actualMsg = obj.getText();
        if (actualMsg.equals(expectedMsg)) {
            System.out.println("Pass: Expected is equal to Actual Message ");
            logger.log(LogStatus.PASS,"Expected Message is equal to Actual Message");

        } else {
            System.out.println("Fail: Expected is not equal to Actual Message ");
            logger.log(LogStatus.FAIL,"Expected Message is not equal to Actual Message");
        }

    }

    //Comparing Two URL's
    public static void URLCompare(String expetedURL,String objName){
        String actualURL = driver.getCurrentUrl();
        if(actualURL.equals(expetedURL)){
            System.out.println("Pass: Expected "+objName + "Page");
            logger.log(LogStatus.PASS,"Expected" +objName + "Page");
        } else {
            System.out.println("Fail:  Not Expected "+objName + "Page");
            logger.log(LogStatus.PASS,"Not Expected Page");
        }

    }

    //Text Box Reusable Method
    public static void creatingText(WebElement obj, String text, String objName) {
        if (obj.isEnabled()) {
            obj.sendKeys(text);
            System.out.println("Pass: " + text + " is enterd in " + objName + " field");
            logger.log(LogStatus.PASS,   text +  "is entered in  "+ objName +  "field" );


        } else {
            System.out.println("Fail: " + text + " is  not enabled..Please Check it");
            logger.log(LogStatus.FAIL,   text + " is not enterd in " + objName + " field" );

        }
    }

    public static void dropDown(WebElement obj, String text, String dropDown) {
        if (obj.isEnabled()) {
            System.out.println(" Pass : " + dropDown + " is  available");
            Select select = new Select(obj);
            select.selectByVisibleText(text);
        } else {
            System.out.println("Fail: " + dropDown + " is not available");

        }
    }
    //REusable Method for  accepting alert  Message
    public static void acceptAlert(WebElement obj , String alert){
        if(obj.isEnabled()){
            String alertMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().accept();
            System.out.println("Pass: "+alert+" has been captured and accepted");
        }else{
            System.out.println("Fail: "+alert+" is not visible ");
        }
    }


    //REusable Method for  dismissing alert  Message
    public static void dismissAlert(WebElement obj , String alert){
        if(obj.isEnabled()){
            String alertMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().dismiss();
            System.out.println("Pass: "+alert+" has been captured and accepted");
        }else{
            System.out.println("Fail: "+alert+" is not visible ");
        }
    }

    //REusable Method for  sending text  to alert
    public static void sendingTextAlert(WebElement obj,String text,String alert){
        if(obj.isEnabled()){
            //String alertMessage = driver.switchTo().alert().getText();
            driver.switchTo().alert().sendKeys(text);
            driver.switchTo().alert().dismiss();
            System.out.println("Pass: "+alert+" has been captured and accepted");
        }else{
            System.out.println("Fail: "+alert+" is not visible ");
        }
    }

    public static void drapAndDrop(WebElement sourceobj,WebElement targetobj){
        if(sourceobj.isEnabled()){
            if(targetobj.isEnabled()){
                Actions action = new Actions(driver);
                action.dragAndDrop(sourceobj,targetobj).build().perform();
                System.out.println("Pass: Performed drag and drop operation");
            }else{
                System.out.println("Fail:target element is disabled");
            }
        } else{
            System.out.println("Fail:Source element is disabled");
        }

    }

    //Reusable Method for click button

    public static void clickButton(WebElement obj, String objName) {
        if (obj.isEnabled()) {
            obj.click();
            System.out.println("Pass: " + objName + "  is working");
            logger.log(LogStatus.PASS,   objName +  "is working" );
        } else {
            System.out.println("Pass: " + objName + "  is not working");
            logger.log(LogStatus.FAIL,  objName + " is not enabled " );
        }
    }

    //Reuasable method for launching URL
    /*@BeforeMethod
    public static void launchURL(String url, String driverpath, String driverName) {

        //WebDriver driver = new ChromeDriver();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty(driverName, driverpath);
        driver = new ChromeDriver(options);
        driver.get(url);
    }*/


    //Method to Switch to classic mode
    public static void switchToClassic() throws Exception {

        WebElement noThanks = driver.findElement(By.id("lexNoThanks"));
        if (driver.findElement(By.id("lexNoThanks")).isEnabled()) {

            driver.findElement(By.id("lexNoThanks")).click();
            driver.findElement(By.id("tryLexDialogX")).click();
            Thread.sleep(5000);

        } else {
            Thread.sleep(5000);
            WebElement viewProfile = driver.findElement(By.xpath("//div[@class='profileTrigger branding-user-profile bgimg slds-avatar slds-avatar_profile-image-small circular forceEntityIcon']//span[@class='uiImage']"));
            viewProfile.click();
            Thread.sleep(4000);
            WebElement switchToClassic = driver.findElement(By.xpath("//a[@class='profile-link-label switch-to-aloha uiOutputURL']"));
            Actions action = new Actions(driver);
            action.moveToElement(switchToClassic).build().perform();
            switchToClassic.click();
        }
    }

    public static void captureScreenShots(String screenshotName) throws Exception{
        TakesScreenshot ts = (TakesScreenshot)driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(source,new File("./Screenshot/" +screenshotName+ ".png"));
        System.out.println("ScreenShots Taken");
    }
}
