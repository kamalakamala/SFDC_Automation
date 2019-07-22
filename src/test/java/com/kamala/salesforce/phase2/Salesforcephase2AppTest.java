package com.kamala.salesforce.phase2;

import com.kamala.salesforce.ReusableMethods;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Salesforcephase2AppTest extends ReusableMethods {

    @BeforeMethod
    public void launchURL() {
        //WebDriver driver = new ChromeDriver();
        Reports();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\kamal\\Downloads\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.get("https://Login.salesforce.com");
    }

    public void Reports(){
        String fileName = new SimpleDateFormat("'SampleDemo('yyyyMMddHHmm'.html'").format(new Date());
        String path = "C:\\Users\\kamal\\Desktop\\ExtentReport\\" + fileName;
        reports = new ExtentReports(path);
        //logger = reports.startTest("Login page Test case results");

    }

    public static void login() throws Exception{
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

    @AfterMethod
    public void ReportsandCleanUp(ITestResult result) throws Exception {
        reports.endTest(logger);
        reports.flush();
        if(ITestResult.FAILURE==result.getStatus()){
            captureScreenShots(result.getName());
            //CreateTestScriptReport(result.getName());
        }else{
            captureScreenShots(result.getName());
            //CreateTestScriptReport(result.getName());
        }
        System.out.println("cleaning resources ...");
        if (driver != null) {
            driver.close();
        }
    }

    @Test(priority = 2)
    public  void TC2_loginMethod() throws Exception {
        //driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        CreateTestScriptReport("TC2_loginMethod");
        URL resource = getClass().getClassLoader().getResource("SalesforceTestData/TC2_loginMethod.xlsx");
        String[][] data = readXlData(resource.getFile(),"Sheet1");

        //String[][] data = readXlData("C:\\Users\\kamal\\Documents\\TC2_loginMethod.xlsx","Sheet1");

        String username1 = data[1][1];
        String password1 = data[1][2];
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, username1, "userName");
        //Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, password1, "passWord");
        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        //captureScreenShots("TC4_2_Login");
        //Thread.sleep(10000);
        switchToClassic();
    }

    @Test(priority = 4)
    public static void TC4_1_ForgotPassword()throws Exception{
        //driver.manage().timeouts().implicitlyWait(10,TimeUnit.MINUTES);
        //String[][] data = readXlData("C:\\Users\\kamal\\Documents\\.xlsx","Sheet1");
        CreateTestScriptReport("TC4_1_ForgotPassword");
        WebElement forgotPassword = driver.findElement(By.xpath("//a[@id='forgot_password_link']"));
        clickButton(forgotPassword,"ForgotPassword");
        //Thread.sleep(5000);
        URLCompare("https://login.salesforce.com/secur/forgotpassword.jsp?locale=us","ForgotPassword");
        //Thread.sleep(5000);
        WebElement username = driver.findElement(By.xpath("//input[@id='un']"));
        //creatingText(username,"eletig02-dyzb@force.com","userName");
        driver.findElement(By.xpath("//input[@id='continue']")).click();
        captureScreenShots("TC4_1_ForgotPassword");
        URLCompare("https://login.salesforce.com/secur/forgotpassword.jsp","CheckEmail");


    }
    @Test(priority = 5)
    public void TC4_2_wrongCrenditials()throws Exception{
        CreateTestScriptReport("TC4_2_WrongCrenditials");

       //URL resource = getClass().getClassLoader().getResource("SalesforceTestData/TC4_2_WrongCrenditials.xlsx");
        URL resource = getClass().getClassLoader().getResource("SalesforceTestData/TC4_2_WrongCrendentials.xlsx");
        String[][] data = readXlData(resource.getFile(),"Sheet1");
        String username1 = data [1][1];
        String password1 = data [1][2];
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "username1", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "password1", "passWord");
        WebElement userLogin = driver.findElement(By.id("Login"));
        clickButton(userLogin, "Login");
        //captureScreenShots("TC2_LoginWrongCrenditials");
        Thread.sleep(10000);
        WebElement error = driver.findElement(By.xpath("//div[@id='error']"));
        stringCompare(error,"Please check your username and password. If you still can't log in, contact your Salesforce administrator.");

    }
    //@Test(priority = 6)
    public static void TC5_userMenu()throws Exception{
        CreateTestScriptReport("TC5_userMenu");
        List<String> expMenuItems = new ArrayList<>(5);
        expMenuItems.add("My Profile");
        expMenuItems.add("My Settings");
        expMenuItems.add("Developer Console");
        expMenuItems.add("Switch to Lightning Experience");
        expMenuItems.add("Logout");
        login();
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
        CreateTestScriptReport("TC1_LoginErrorMessage");
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
        CreateTestScriptReport("TC3_Rememberme");
        WebElement uname = driver.findElement(By.id("username"));
        creatingText(uname, "eletig02-dyzb@force.com", "userName");
        Thread.sleep(5000);
        WebElement pword = driver.findElement(By.id("password"));
        creatingText(pword, "Apple@123", "passWord");
        WebElement rememberMe = driver.findElement(By.xpath("//input[@id='rememberUnkaml']"));
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
    @Test(priority = 6)
    public static void TC6_MyProfile() throws Exception{
        CreateTestScriptReport("TC6_MyProfile");
        login();
        WebElement usermenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(usermenu,"userMenu");
        WebElement myprofile = driver.findElement(By.xpath("//a[contains(text(),'My Profile')]"));
        clickButton(myprofile,"My Profile");
        WebElement editButton = driver.findElement(By.xpath("//a[@class='contactInfoLaunch editLink']//img\n"));
        clickButton(editButton,"Edit Button");
        //Thread.sleep(5000);
        WebElement contact = driver.findElement(By.xpath("//a[contains(text(),'Contact')]"));
        driver.switchTo().frame("contactInfoContentId");
        WebElement abouttab = driver.findElement(By.xpath("//a[contains(text(),'About')]"));
        clickButton(abouttab,"About Tab");
        //Thread.sleep(2000);
        WebElement lastname = driver.findElement(By.xpath("//input[@id='lastName']"));
        lastname.clear();
        creatingText(lastname,"anir","LastName");
        WebElement saveall = driver.findElement(By.xpath("//input[@class='zen-btn zen-primaryBtn zen-pas']"));
        clickButton(saveall,"Save");
        WebElement profilepagename = driver.findElement(By.xpath("//span[@id='tailBreadcrumbNode']"));
        System.out.println(profilepagename.getText());
        stringCompare(profilepagename,"eletig anir");

        WebElement post = driver.findElement(By.xpath("//span[contains(@class,'publisherattachtext')][contains(text(),'Post')]"));
        clickButton(post,"PostButton");
        WebElement postiframe = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[2]/table[1]/tbody[1]/tr[1]/td[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/iframe[1]"));
        driver.switchTo().frame(postiframe);
        //System.out.println("i am in frame");
        WebElement postmessage = driver.findElement(By.xpath("/html[1]/body[1]"));
        clickButton(postmessage,"Post Message");
        postmessage.sendKeys("Hi");
        driver.switchTo().defaultContent();
        WebElement share = driver.findElement(By.xpath("//input[@id='publishersharebutton']"));
        clickButton(share,"Share");
        WebElement txtMsg = driver.findElement(By.xpath("//p[contains(text(),'Hi')]"));
        if(txtMsg.getText().trim().equals("Hi")){
            System.out.println("Pass: Message got Printed ");
            logger.log(LogStatus.PASS,"Expected Message got printed");

        } else {
            System.out.println("Fail:Message not Printed ");
            logger.log(LogStatus.FAIL,"Expected Message not printed");

        }

        WebElement file = driver.findElement(By.xpath("//span[contains(@class,'publisherattachtext')][contains(text(),'File')]"));
        clickButton(file,"FileButton");
        WebElement uploadFile = driver.findElement(By.xpath("//a[@id='chatterUploadFileAction']"));
        clickButton(uploadFile,"uploadFileButton");
        WebElement choosefile = driver.findElement(By.xpath("//input[@id='chatterFile']"));
        //choosefile.sendKeys("C:\\testcase\\testcasefile.txt");
        creatingText(choosefile,"C:\\testcase\\testcasefile.txt","Browse File");
        //Thread.sleep(1000);
        WebElement sharebutton = driver.findElement(By.xpath("//input[@id='publishersharebutton']"));
        clickButton(sharebutton,"Share");

        WebElement image = driver.findElement(By.xpath("//span[@id='displayBadge']"));
        actionMethod(image);
        WebElement addPhoto = driver.findElement(By.xpath("//a[@id='uploadLink']"));
        addPhoto.click();
        driver.switchTo().frame("uploadPhotoContentId");
        System.out.println("I am in frame");
        WebElement choosePhoto = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadInputFile']"));
        creatingText(choosePhoto,"C:\\\\Users\\\\kamal\\\\Pictures\\\\sampletestpictures\\\\annie-spratt-pDGNBK9A0sk-unsplash.jpg","Photo");
        WebElement savebutton = driver.findElement(By.xpath("//input[@id='j_id0:uploadFileForm:uploadBtn']"));
        clickButton(savebutton,"SaveButton");
        WebElement save = driver.findElement(By.xpath("//input[@id='j_id0:j_id7:save']"));
        clickButton(save,"Save");


    }
    @Test(priority = 8)
    public static void TC7_MySettings()throws Exception{
        CreateTestScriptReport("TC7_MySettings");
        login();
        WebElement usermenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(usermenu,"UserMenu");
        WebElement mySettings = driver.findElement(By.xpath("//a[contains(text(),'My Settings')]"));
        clickButton(mySettings,"My Settings");
        WebElement personal = driver.findElement(By.xpath("//div[@id='PersonalInfo']//a[@class='header setupFolder']"));
        clickButton(personal,"PersonalButton");
        WebElement loginHistory = driver.findElement(By.xpath("//span[@id='LoginHistory_font']"));
        clickButton(loginHistory,"Login History");
        WebElement downloadLoginHistory = driver.findElement(By.xpath("//a[contains(text(),'Download login history for last six months, includ')]"));
        clickButton(downloadLoginHistory,"Download Login History");

        /*WebElement usermenu1 = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(usermenu1,"UserMenu");
        WebElement mySettings1 = driver.findElement(By.xpath("//a[contains(text(),'My Settings')]"));
        clickButton(mySettings1,"My Settings");*/
        WebElement displayLayout = driver.findElement(By.xpath("//span[@id='DisplayAndLayout_font']"));
        clickButton(displayLayout,"Display");
        WebElement customizeTab = driver.findElement(By.xpath("//span[@id='CustomizeTabs_font']"));
        clickButton(customizeTab,"CustomizeTab");
        WebElement sales = driver.findElement(By.xpath("//select[@id='p4']"));
        selectdropDown(sales,"Salesforce Chatter","salesdropdown");
        WebElement availableTab = driver.findElement(By.xpath("//select[@id='duel_select_0']"));
        Select select = new Select(availableTab);
        Boolean found = false;
        List<WebElement> alloptions = select.getOptions();
        for(WebElement we : alloptions) {
            if (we.getText().equals("Reports")) {
                found = true;
            }
        }
        if(found){
            WebElement addButton = driver.findElement(By.xpath("//img[@class='rightArrowIcon']"));
            clickButton(addButton,"Add");
        }else{
            System.out.println("Reports is not present in avaialble Tab");
        }
        WebElement save = driver.findElement(By.xpath("//input[@name='save']"));
        clickButton(save,"Save");


        WebElement email = driver.findElement(By.xpath("//div[@id='EmailSetup']//a[@class='header setupFolder']"));
        clickButton(email,"Email");
        WebElement emailSettings = driver.findElement(By.xpath("//a[@id='EmailSettings_font']"));
        clickButton(emailSettings,"Email Settings");
        WebElement sfRadioButton = driver.findElement(By.xpath("//input[@id='use_external_email1']"));
        clickButton(sfRadioButton,"RadioButton");
        WebElement emailName = driver.findElement(By.xpath("//input[@id='sender_name']"));
        creatingText(emailName,"","EmailName");
        WebElement emailAdress = driver.findElement(By.xpath("//input[@id='sender_email']"));
        creatingText(emailAdress,"","EmailAdress");
        WebElement bbcRadioButton = driver.findElement(By.xpath("//input[@id='auto_bcc1']"));
        clickButton(bbcRadioButton,"BBC Radio Button");
        WebElement save1 = driver.findElement(By.xpath("//input[@name='save']"));
        clickButton(save1,"Save");

        WebElement calenderRemainders = driver.findElement(By.xpath("//div[@id='CalendarAndReminders']//a[@class='header setupFolder']"));
        clickButton(calenderRemainders,"Calender Remainders");
        WebElement remainders = driver.findElement(By.xpath("//span[@id='Reminders_font']"));
        clickButton(remainders,"Remainders");
        WebElement testRemainder = driver.findElement(By.xpath("//input[@id='testbtn']"));
        clickButton(testRemainder,"TestRemainder");


    }
    @Test(priority = 8)
    public static void TC8_DeveloperConsole() throws Exception{
        CreateTestScriptReport("TC8_DeveloperConsole");
        login();
        WebElement usermenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(usermenu,"UserMenu");
        WebElement developerConsole = driver.findElement(By.xpath("//a[@class='debugLogLink menuButtonMenuLink']"));
        clickButton(developerConsole,"Developer Console");
        String oldwindow = driver.getWindowHandle();
        Set<String> getallwindows = driver.getWindowHandles();
        String[] getwindow = getallwindows.toArray(new String[getallwindows.size()]);
        System.out.println(getallwindows.size());
        driver.switchTo().window(getwindow[1]);
        driver.close();
        driver.switchTo().window(oldwindow);
        //driver.quit();

    }

    @Test(priority = 10)
    public static void TC9_Logout() throws Exception{
        CreateTestScriptReport("TC9_Logout");
        login();
        WebElement usermenu = driver.findElement(By.xpath("//span[@id='userNavLabel']"));
        clickButton(usermenu,"UserMenu");
        WebElement logout = driver.findElement(By.xpath("//a[contains(text(),'Logout')]"));
        clickButton(logout,"Logout");

    }
    @Test(priority = 11)
    public static void TC10_CreateAccount() throws Exception{
        CreateTestScriptReport("TC10_CreateAccount");
        login();
        WebElement accounts = driver.findElement(By.xpath("//li[@id='Account_Tab']//a[contains(text(),'Accounts')]"));
        clickButton(accounts,"Accounts");
        WebElement newAccount = driver.findElement(By.xpath("//input[@name='new']"));
        clickButton(newAccount,"New Account");
        WebElement accountName = driver.findElement(By.xpath("//input[@id='acc2']"));
        creatingText(accountName,"kkkr","AccoutName");
        //accountName.sendKeys("kamala kamalakkr");
        WebElement save = driver.findElement(By.xpath("//div[contains(@class,'pbHeader')]//input[1]"));
        clickButton(save,"Save");

    }

}
