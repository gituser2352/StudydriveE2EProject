package studydrive;

import java.awt.AWTException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import Components.StudydriveUIComponents;
import resources.base;

@Test
public class StudydriveUITest extends base{
	public static Logger log=LogManager.getLogger(base.class.getName());
	
	@BeforeTest()
	public void initialize() throws IOException{
		
		driver=initializeDriver();
		log.info("Driver is initialized");
		driver.get(prop.getProperty("URL"));
		log.info("Url opened is "+prop.getProperty("URL"));
		//log.error("error");
		

	}

@Test()
public void VerifyFileUpload() throws IOException, InterruptedException, SQLException, AWTException{
	//driver.get(prop.getProperty("URL"));
	log.info("Navigated to Homepage");
	driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
	Thread.sleep(3000);
	StudydriveUIComponents SUI=new StudydriveUIComponents(driver);
	
	SUI.Login();
	//Thread.sleep(5000);
	SUI.UploadFile();
	Thread.sleep(5000);
	SUI.VerifyUploadSuccessful();

}


@AfterTest()
public void teardown()
{
	driver.close();
	driver=null;
}


/*
	@DataProvider
	public Object[][] getdata(){
		//Row stands for how many different data types test should run
		//column stands for how many values per each test
		Object[][] data = new Object[2][3];
		
		//data - oth row
		data[0][0] = "nonrestricteduser@qw.com";
		data[0][1] = "123456";
		data[0][2]= "Non Restricted user";
		
/*		//data - 1st row
		data[1][0] = "restricteduser@qw.com";
		data[1][1] = "123456";
		data[1][2]= "Restricted user";
		
		return data;	
	
	}*/
}
