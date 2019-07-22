package com.kamala.salesforce.phase1;

import com.kamala.salesforce.ReusableMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalesforceAppTest extends ReusableMethods {

    @BeforeMethod
    public void launchURL() {
        //WebDriver driver = new ChromeDriver();
        Reports();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kamal\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.get("https://Login.salesforce.com");
    }

    public void Reports(){
        String fileName = new SimpleDateFormat("'SampleDemo('yyyyMMddHHmm'.html'").format(new Date());
        String path = "C:\\Users\\kamal\\Desktop\\ExtentReport\\" + fileName;
        reports = new ExtentReports(path);
        logger = reports.startTest("Login page Test case results");

    }

    @AfterMethod
    public void ReportsandCleanUp(ITestResult result) throws Exception {
        reports.endTest(logger);
        reports.flush();
        if(ITestResult.FAILURE==result.getStatus()){
            captureScreenShots(result.getName());
        }else{
            captureScreenShots(result.getName());
        }
        System.out.println("cleaning resources ...");
        if (driver != null) {
            driver.close();
        }
    }

    @Test(priority = 2)
    public static void TC2_loginMethod() throws Exception {
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "eletig02-dyzb@force.com", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "Apple@123", "passWord");
        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        //captureScreenShots("TC4_2_Login");
        Thread.sleep(10000);
        switchToClassic();
    }
    @Test(priority = 4)
    public static void TC4_1_ForgotPassword()throws Exception{
        WebElement forgotPassword = driver.findElement(By.xpath("//a[@id='forgot_password_link']"));
        clickButton(forgotPassword,"ForgotPassword");
        Thread.sleep(5000);
        URLCompare("https://login.salesforce.com/secur/forgotpassword.jsp?locale=us","ForgotPassword");
        Thread.sleep(5000);
        WebElement username = driver.findElement(By.xpath("//input[@id='un']"));
        //creatingText(username,"eletig02-dyzb@force.com","userName");
        driver.findElement(By.xpath("//input[@id='continue']")).click();
        captureScreenShots("TC4_1_ForgotPassword");
        URLCompare("https://login.salesforce.com/secur/forgotpassword.jsp","CheckEmail");


    }
    @Test(priority = 5)
    public static void TC4_2_wrongCrenditials()throws Exception{
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "eletig0234-dyzb@force.com", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "Apple@123", "passWord");
        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        //captureScreenShots("TC2_LoginWrongCrenditials");
        Thread.sleep(10000);
        WebElement error = driver.findElement(By.xpath("//div[@id='error']"));
        stringCompare(error,"Please check your username and password. If you still can't log in, contact your Salesforce administrator.");

    }
    @Test(priority = 6)
    public static void TC5_userMenu()throws Exception{
        List<String> expMenuItems = new ArrayList<>(5);
        expMenuItems.add("My Profile");
        expMenuItems.add("My Settings");
        expMenuItems.add("Developer Console");
        expMenuItems.add("Switch to Lightning Experience");
        expMenuItems.add("Logout");
        TC2_loginMethod();
        Thread.sleep(5000);
        WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(userMenu,"userMenu");
        Thread.sleep(4000);
        //captureScreenShots("TC5_Usermenu");
        List<WebElement> menuItems = driver.findElements(By.xpath("(//div[@id='userNav-menuItems'])//a"));
        boolean allExists = true;
        for (String str : expMenuItems) {
            boolean isExists = false;
            for (WebElement we : menuItems) {
                if (str.equals(we.getText())) {
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                allExists = false;
            }

        }
        if(!allExists) {
            System.out.println("all menu items not present");
            logger.log(LogStatus.FAIL,"All Menu items  are not Present");

        } else {
            System.out.println("items are present");
            logger.log(LogStatus.PASS,"All Menu items Present");
        }
    }




    @Test(priority = 1)
    public static void TC1_LoginErrorMessage() throws Exception {
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "eletig02-dyzb@force.com", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "Apple@1234", "passWord");

        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        //captureScreenShots("TC1_LoginError");
        WebElement errorMsg = driver.findElement(By.xpath("//div[@id='error']"));
        stringCompare(errorMsg, "Please check your username and password. If you still can't log in, contact your Salesforce administrator.");
    }
    @Test(priority = 3)
    public static void TC3_Rememberme() throws Exception {
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "eletig02-dyzb@force.com", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "Apple@123", "passWord");
        WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUn']"));
        clickButton(rememberMe, "RememberMe");
        //captureScreenShots("TC3_RememberMe");
        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        switchToClassic();
        Thread.sleep(4000);
        WebElement userMenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(userMenu, "Menu");
        Thread.sleep(4000);
        WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        clickButton(logout, "Logout");
        captureScreenShots("TC3_Logout");
        Thread.sleep(4000);
        WebElement username = driver.findElement(By.xpath("//span[@id='idcard-identity']"));
        System.out.println(username.getText());
        stringCompare(username,"eletig02-dyzb@force.com");




    }
}