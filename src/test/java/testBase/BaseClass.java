package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;   //log4j
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass 
{
	public static WebDriver driver;
	public Logger logger;
	public Properties p;
	
	@BeforeClass(groups= {"Sanity", "Regression", "Master"})
	@Parameters({"os","browser"})
	public void setUp(String os, String br) throws IOException, URISyntaxException
	{
		//loading properties file
		FileReader file= new FileReader("./src//test//resources//config.properties");
		p= new Properties();
		p.load(file);
		
		//loading log4j file
		logger= LogManager.getLogger(this.getClass()); //Log4j
		
		if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
		{
			DesiredCapabilities cap= new DesiredCapabilities();
			
			//OS
			if(os.equalsIgnoreCase("Windows"))
			{
				cap.setPlatform(Platform.WIN11);
			}
			else if(os.equalsIgnoreCase("linux"))
			{
				cap.setPlatform(Platform.LINUX);
			}
			else if (os.equalsIgnoreCase("Mac"))
			{
				cap.setPlatform(Platform.MAC);
			}
			else
			{
				System.out.println("No matching os");
				return;
			}
			
			//Browser
			switch(br.toLowerCase())
			{
			case "chrome": cap.setBrowserName("chrome");
			break;
			case "edge": cap.setBrowserName("Microsoft Edge");
			break;
			case "firefox": cap.setBrowserName("firefox");
			break;
			default: System.out.println("No matching browser");
			return;
			}
			driver = new RemoteWebDriver(new URI("http://localhost:4444/wd/hub").toURL(), cap);
		}
		if(p.getProperty("execution_env").equalsIgnoreCase("local"))
		{
		//launching browser based on condition
		switch(br.toLowerCase())
		{
		case "chrome": driver= new ChromeDriver();
		break;
		case "edge": driver= new EdgeDriver();
		break;
		case "firefox": driver= new FirefoxDriver();
		break;
		default: System.out.println("Invalid Browser name");
		return;
		}
		}

		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get(p.getProperty("appURL2")); //reading url from properties file
		driver.manage().window().maximize();
		
	}
	
	@AfterClass(groups= {"Sanity", "Regression", "Master"})
	public void tearDown()
	{
		driver.quit();
	}
	
	public String randomString()
	{
		String generatedString=RandomStringUtils.secure().nextAlphabetic(5);
		return generatedString;
	}
	
	public String randomNumber()
	{
		String number= RandomStringUtils.secure().nextNumeric(10);
		return number;
	}
	
	public String randomAlphaNumeric()
	{
		String generatedString=RandomStringUtils.secure().nextAlphabetic(5);
		String number= RandomStringUtils.secure().nextNumeric(10);
		return(generatedString+"@"+number);
	}
	
	public String captureScreen(String tname) throws IOException
	{
		String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		
		TakesScreenshot ts = (TakesScreenshot) driver;
		File sourceFile = ts.getScreenshotAs(OutputType.FILE);
		
		String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
		File targetFile=new File(targetFilePath);
		
		sourceFile.renameTo(targetFile); //copy the source file to target file
			
		return targetFilePath; //return filepath because we need attach the screenshot to the report
	}
	
}
