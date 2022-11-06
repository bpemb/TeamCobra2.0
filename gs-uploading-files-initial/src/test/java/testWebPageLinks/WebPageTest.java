package testWebPageLinks;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
/**
 * Class: WebPageTest
 * @author Baldwin Pemberton
 * Purpose: to test the web page and make sure the links and buttons work properly.
 *
 * Requires the local SpringBoot Server. So run the Main source code first then run this test
 */
public class WebPageTest {

    //initialize the WebDriver variable
    private static WebDriver driver;

    /**
     * this method is used to set up the chrome driver
     */
    @BeforeAll
    public static void setUpChrome() {

        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Pemberton\\OneDrive - Georgia Gwinnett College\\Software Development II\\chromedriver_win32\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("http://localhost:8080/");
    }

    @AfterAll
    public static void closeChrome() {
        driver.close();
    }





    /**
     * this method tests the User Guide link
     */
    @Test
    @Order (9)
    public void testUserGuideLink() {

        driver.get("http://localhost:8080/");

        String url = "https://github.com/cventimiglia/Team_CPMC/blob/main/USER-GUIDE.txt";

        WebElement userGuide = driver.findElement(By.id("floatingLinkGuide"));
        userGuide.sendKeys(Keys.ENTER);

        driver.get(url);
        driver.close();


    }




    /**
     * this method tests the example JSON file link
     */
    @Test
    @Order (10)

    public void testJSONFileLink1() {

        driver.get("http://localhost:8080/");

        String url = "https://github.com/cventimiglia/Team_CPMC/blob/main/ExecutionQueueOnSave.json";

        WebElement jsonFile = driver.findElement(By.id("floatingLinkExample"));
        jsonFile.sendKeys(Keys.ENTER);

        driver.get(url);
        driver.close();

    }

    /**
     * this method checks if the browse example image is shown
     */
    @Test
    @Order (9)
    public void testExampleImage() {
        driver.get("http://localhost:8080/");

        boolean imgPresent = driver.findElement(By.id("ImgBrowse")).isDisplayed();

        Assert.assertFalse(imgPresent == false);



    }


    /**
     * this method tests the back to top button at the bottom of the screen
     */
    @Test
    @Order (2)
    public void testBackToTop() {
        driver.get("http://localhost:8080/");

        WebElement jsonFile = driver.findElement(By.xpath("/html/body/div[6]/main/a"));
        jsonFile.sendKeys(Keys.ENTER);



    }

    /**
     * this method tests the choose file button and the upload button
     */
    @Test
    @Order (3)
    public void testUploadFile() {
        driver.get("http://localhost:8080/");


        WebElement chooseFile = driver.findElement(By.name("file"));
        chooseFile.sendKeys("C:\\Users\\Pemberton\\OneDrive - Georgia Gwinnett College\\Software Development II\\Programming Workspace\\gs-uploading-files-initial\\ExecutionQueueOnSave.json");

        driver.findElement(By.id("submitBtn")).sendKeys(Keys.ENTER);
    }

    /**
     * this method test the file download link for the text file.
     */
    @Test
    @Order (4)
    public void testDownloadLink() {
        driver.get("http://localhost:8080/");


        WebElement chooseFile = driver.findElement(By.name("file"));
        chooseFile.sendKeys("C:\\Users\\Pemberton\\OneDrive - Georgia Gwinnett College\\Software Development II\\Programming Workspace\\gs-uploading-files-initial\\ExecutionQueueOnSave.json");

        driver.findElement(By.id("submitBtn")).sendKeys(Keys.ENTER);

        WebElement download = driver.findElement(By.linkText("http://localhost:8080/files/ExecutionQueueOnSave-combos.txt"));
        download.sendKeys(Keys.ENTER);
    }



    @Test
    @Order (5)
    public void testGGCLink() {


        WebElement ggcLink = driver.findElement(By.xpath("/html/body/div[1]/nav/ul/li[3]/a/span"));
        ggcLink.click();

        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://teamcobra.neocities.org/CobraGGCpage.html" );
    }

    @Test
    @Order (6)
    public void testInformationLink() {

        driver.get("http://localhost:8080/");
        WebElement informationLink = driver.findElement(By.xpath("/html/body/div[1]/nav/ul/li[4]/a"));
        informationLink.click();

        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://teamcobra.neocities.org/CobraInfo.html" );
    }

    @Test
    @Order (7)
    public void testMeetTheTeamLink() {

        driver.get("http://localhost:8080/");
        WebElement mttLink = driver.findElement(By.xpath("/html/body/div[1]/nav/ul/li[5]/a/span"));
        mttLink.click();

        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://teamcobra.neocities.org/CobraMTT.html" );
    }

    @Test
    @Order (8)
    public void testAboutLink() {

        driver.get("http://localhost:8080/");
        WebElement aboutLink = driver.findElement(By.xpath("/html/body/div[1]/nav/ul/li[6]/a"));
        aboutLink.click();

        String URL = driver.getCurrentUrl();
        Assert.assertEquals(URL, "https://teamcobra.neocities.org/CobraAbout.html" );
    }
}
