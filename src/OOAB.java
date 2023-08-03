
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.Point;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;


/**
 * @author sambeetmohapatra
 *
 */
public class OOAB {
    protected static String url;
    protected static int loopvar_max;
    private  String pdflink;
    private  String booklink;
    private  String odiabook;
    private String counter,bookname;
    private Integer x;
    private static WebDriver d;
    public OOAB() {
        //chrome://settings/content/pdfDocuments
        url = "http://oaob.nitrkl.ac.in/view/title/";
        counter ="/Users/sambeetmohapatra/Downloads/Workspace/Web_Automation_Practice/resources/counter.properties";
        x=10;

    }
    private void launchBrowser(String url) throws InterruptedException {
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setAcceptInsecureCerts(true);
//        chromeOptions.addArguments("--ignore-certificate-errors");
//        chromeOptions.addArguments("--ignore-ssl-errors=yes");
//        chromeOptions.addArguments("--allow-insecure-localhost");
//        chromeOptions.addArguments("--allow-running-insecure-content");
//        chromeOptions.addArguments("--download.default_directory=/Users/bard/Downloads");
//        d = new ChromeDriver(chromeOptions);
        d= new SafariDriver();
        d.manage().window().maximize();
        d.get(url);
//        d.manage().window().setPosition(new Point(-2000,0));
        System.out.println(d.getTitle().trim().toUpperCase());
        Thread.sleep(3000);
    }
    public WebElement waitForElement(WebElement wb){
        WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(100));
        wait.until(ExpectedConditions.visibilityOf(wb));
        return wb;
    }
    @Test(description = "Start the Run")
    public void startApp() throws Exception {
        new OOAB().run_oaob();

    }
    @AfterSuite
    public void quitBrowser() throws InterruptedException {
       System.out.println("completed");
        Thread.sleep(60000);
        if(d!=null) {
            d.quit();
            System.out.println("Browser Quit");
        }
    }
    public void run_oaob() throws Exception {
        try {
            Integer loopVar = Integer.parseInt(getProperty("loopvar", counter));

            loopvar_max =loopVar+x;
            for(int i=loopVar+1;i<loopvar_max;i++) {
                launchBrowser(url);
                System.out.println("URL Opened");
                System.out.println(loopVar+"");
                String book =d.findElement(By.xpath("(//div[text()='Please select a value to browse from the list below.']/..//ul//a)["+i+"]")).getText();
                Thread.sleep(2000);
                System.out.println("Book to click "+book);
                JS_ScrollToView(d.findElement(By.xpath("(//div[text()='Please select a value to browse from the " +
                        "list below.']/..//ul//a)["+i+"]")));
                Thread.sleep(2000);
                waitForElement(d.findElement(By.xpath("(//div[text()='Please select a value to browse from the " +
                        "list below.']/..//ul//a)["+i+"]"))).click();
                System.out.println("Book Clicked "+book);
                Thread.sleep(3000);
                waitForElement(d.findElement(By.xpath("//div[contains(text(),'Number of items')]/..//a"))).click();
                Thread.sleep(3000);
                waitForElement(d.findElement(By.xpath("//a[contains(text(),'PDF')]")));
                d.findElement(By.xpath("(//a[contains(text(),'PDF')])[1]")).sendKeys(Keys.ALT);
                d.findElement(By.xpath("//a[contains(text(),'PDF')]")).click();
                Thread.sleep(1000);


                System.out.println("Clicked on PDF");
                Thread.sleep(4000);
                System.out.println("Downloaded book : "+book);
                System.out.println("<b>Downloaded book : "+book+" Loopvar count is"+i+"</b>");
                writeProperty("loopvar", i, counter);

//                waitForElement(d.findElement(By.xpath("//a[text()='Titles']"))).click();
                d.navigate().back();
                Thread.sleep(2000);

                if(d!=null) {
                    d.close();
                    System.out.println("Browser Closed");
                }
            }

        }
        catch(Exception e) {
            System.out.println(e);

        }
    }
     public static String getProperty(String Skey,String File) throws IOException
     {
        FileInputStream fis= new FileInputStream(File);
        Properties prop= new Properties();
        prop.load(fis);
        return prop.getProperty(Skey);
     }

    public static void  writeProperty(String Skey,int Sval,String File) throws IOException
    {
        FileInputStream fis= new FileInputStream(File);
        Properties prop= new Properties();
        prop.load(fis);
        prop.setProperty(Skey, Sval+"");
        String value = prop.getProperty(Skey).trim();
        System.out.println("Value updated in props "+Skey+" - " +value);
        prop.store(new FileOutputStream(File), null);
    }

    public static void JS_ScrollToView(WebElement wb) throws InterruptedException {
        ((JavascriptExecutor) d).executeScript("arguments[0].scrollIntoView(true);", wb);
        Thread.sleep(1000);
    }

}
