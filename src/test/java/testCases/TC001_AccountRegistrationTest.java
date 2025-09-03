package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass
{	
	@Test(groups= {"Regression", "Master"})
	public void verify_account_registration() throws InterruptedException
	{
		logger.info("***** Starting TC001_AccountRegistrationTest  ****");
		logger.debug("This is a debug log message");
		try
		{
			HomePage hp= new HomePage(driver);
			hp.clickMyAccount();
			logger.info("Clicked on My Account Link");
			
			hp.clickRegister();
			logger.info("Clicked on Register Link");
			
			AccountRegistrationPage regPage= new AccountRegistrationPage(driver);
			logger.info("Providing customer details...");
			
			regPage.setFirstName(randomString().toUpperCase());
			regPage.setLastName(randomString().toUpperCase());
			regPage.setEmail(randomString()+"@gmail.com");
			regPage.setTelephone(randomNumber());
			
			String password= randomAlphaNumeric();
			regPage.setPassword(password);
			regPage.setConfirmPassword(password);
			Thread.sleep(2000);
			regPage.checkPolicy();
			regPage.clickContinue();
			
			Thread.sleep(3000);
			
			logger.info("Validating expected message...");
			String message= regPage.getConfirmationMsg();
			if(message.equals("Your Account Has Been Created!"))
			{
				logger.info("Test Passed..");
				Assert.assertTrue(true);
			}
			else
			{
				logger.error("Test failed..");
				logger.debug("Debug Logs...");
				Assert.assertTrue(false);
			}
//			Assert.assertEquals(message, "Your Account Has Been Created!!!", "Confirmation message mismatch");

		}
		catch(Exception e)
		{
			Assert.fail();
		}
		finally 
		{
			logger.info("***** Finished TC001_AccountRegistrationTest *****");
		}
	}
	
}
