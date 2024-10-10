
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import com.lambdatest.tunnel.Tunnel;

public class AutoHealDemo {

	private RemoteWebDriver driver;
	private String Status = "passed";
	Tunnel t;

	@BeforeMethod
	public void setup(Method m, ITestContext ctx) throws MalformedURLException, Exception {
		String username = "{LT_UserName}";
		String authkey = "{LT_Access_Key}";
		String hub = "@hub.lambdatest.com/wd/hub";
		DesiredCapabilities caps = new DesiredCapabilities();

		ChromeOptions browserOptions = new ChromeOptions();
		browserOptions.setCapability("platformName", "Windows 11");
		browserOptions.setCapability("browserVersion", "124");

		HashMap<String, Object> ltOptions = new HashMap<String, Object>();
		ltOptions.put("username", username);
		ltOptions.put("driver_version", "127.0");
		ltOptions.put("accessKey", authkey);
		ltOptions.put("build", "AutoHeal Demo");
		ltOptions.put("platformName", "Windows 11");
		ltOptions.put("selenium_version", "4.0.0");
		ltOptions.put("w3c", true);
		ltOptions.put("autoHeal", true);
		browserOptions.setCapability("lt:options", ltOptions);

		caps.setCapability(ChromeOptions.CAPABILITY, browserOptions);

		driver = new RemoteWebDriver(new URL(
				"https://{LT_Username}:{LT_Access_Key}@hub.lambdatest.com/wd/hub"),
				caps);

	}

	@Test
	public void basicTest() throws InterruptedException {

		System.out.println("Loading Url");

		driver.get("https://www.lambdatest.com/selenium-playground/auto-healing");
		
		driver.findElement(By.xpath("//*[text()='Change DOM ID']")).click(); //uncomment this in second run
		
		Thread.sleep(2000);

		driver.findElement(By.id("username")).click();

		driver.findElement(By.id("username")).sendKeys("test@gmail.com");

		Thread.sleep(2000);

		driver.findElement(By.id("password")).click();

		driver.findElement(By.id("password")).sendKeys("test@gmail.com");
		
		Thread.sleep(2000);
		
		driver.findElement(By.xpath("//*[text()='Submit']")).click();

		Thread.sleep(3000);

		System.out.println("Test Finished");
	}

	@AfterMethod
	public void tearDown() throws Exception {
		driver.executeScript("lambda-status=" + Status);
		driver.quit();
		// t.stop();
	}
}
