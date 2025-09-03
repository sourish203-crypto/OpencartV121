package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.DataProviders;

public class TC003_LoginDDT extends BaseClass
{
	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups="Datadriven")
	public void verify_LoginDDT(String email, String pwd, String exp) throws InterruptedException
	{
		logger.info("**** Starting TC_003_LoginDDT *****");
		try
		{
			//HomePage
			HomePage hp= new HomePage(driver);
			hp.clickMyAccount();
			hp.clickLogin();
			
			//LoginPage
			LoginPage lp= new LoginPage(driver);
			lp.setEmail(email);
			lp.setPassword(pwd);
			//Thread.sleep(2000);
			lp.clickLogin();
			
			//My Account Page
			MyAccountPage myAcc= new MyAccountPage(driver);
			boolean targetPage= myAcc.isMyAccountPageExist();
			
			/*Data is valid  - login success - test pass  - logout
			Data is valid -- login failed - test fail
	
			Data is invalid - login success - test fail  - logout
			Data is invalid -- login failed - test pass
			*/
			
			if(exp.equalsIgnoreCase("Valid"))
			{
				if(targetPage==true)
				{
					Assert.assertTrue(true);
					myAcc.clickLogout();
				}
				else
				{
					Assert.assertTrue(false);
				}
				
			}
			if(exp.equalsIgnoreCase("Invalid"))
			{
				if(targetPage==true)
				{
					myAcc.clickLogout();
					Assert.assertTrue(false);
				}
				else
				{
					Assert.assertTrue(true);
				}
			}
		}
		catch(Exception e)
		{
			Assert.fail("An exception occurred: " + e.getMessage());
		}
		Thread.sleep(3000);
		logger.info("**** Finished TC_003_LoginDDT *****");
	}

}
