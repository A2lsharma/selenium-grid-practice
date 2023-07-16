import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

import java.net.MalformedURLException;
import java.net.URL;

public class Google {
    WebDriver driver;

    @BeforeTest
    public WebDriver setUp() throws MalformedURLException{
        DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2).build();
        CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder()
                .expiresInSeconds(300)        // 5 minutes
                .projectArn("arn:aws:devicefarm:us-west-2:557698079324:testgrid-project:6c529b27-c979-41d9-859d-f6e117a57aa4")
                .build();
        URL testGridUrl = null;
        try {
            CreateTestGridUrlResponse response = client.createTestGridUrl(request);
            testGridUrl = new URL(response.url());
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert testGridUrl != null;
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName("chrome");
        driver = new RemoteWebDriver(testGridUrl, capabilities);
//        driver = new ChromeDriver();
        return driver;
    }

    @Parameters({"Port"})
    @Test
    public void openGoogle(String Port) {

//        if(Port.equalsIgnoreCase("5555")){

//            System.out.println("Port :: "+Port);
//            capabilities.setCapability("uri", "http://192.168.29.145:5555");
//            capabilities.setPlatform(Platform.XP);
//        else{
//            System.out.println("Port :: "+Port);
//            DesiredCapabilities capabilities1 = new DesiredCapabilities();
//            capabilities.setBrowserName("MicrosoftEdge");
//            capabilities.setCapability("uri", "http://172.27.176.1:5555");
//            capabilities1.setPlatform(Platform.XP)
        String baseUrl = "http://demo.guru99.com/test/newtours/";
        String expectedTitle = "Welcome: Mercury Tours";
        String actualTitle = "";
        driver.manage().window().maximize();

        // launch Fire fox and direct it to the Base URL
        driver.get("https://www.google.com");
        driver.get(baseUrl);

        // get the actual value of the title
        actualTitle = driver.getTitle();

        /*
         * compare the actual title of the page with the expected one and print
         * the result as "Passed" or "Failed"
         */
        if (actualTitle.contentEquals(expectedTitle)){
            System.out.println("Test Passed!");
        } else {
            System.out.println("Test Failed");
        }

        //close Fire fox
    }


    @AfterTest
    public void closeBrowser(){
        driver.close();
    }
}
