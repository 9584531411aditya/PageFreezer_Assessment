package test;

import org.testng.annotations.Test;

import pageFile.MyScreenRecorder;
import pageFile.PageFile;

import org.testng.annotations.BeforeMethod;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterMethod;

public class TestFile {
	PageFile pf = new PageFile();
	
	public static String propfile(String s) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(".\\src\\main\\java\\dataFile\\data.properties"));
		Properties prop = new Properties();
		prop.load(reader);
		return prop.getProperty(s);
	}
  @Test(priority= 1)
  public void publicPortal() throws Exception {
	  MyScreenRecorder.startRecording("PublicPortal");
	 pf.openPublicPortal();
	 pf.publicPortalAccess(propfile("PublicPortalTitle"));
	 MyScreenRecorder.stopRecording();
	  
  }
  @Test(priority=2)
  public void tabsVerification() throws Exception {
	  MyScreenRecorder.startRecording("TabsVerification");
	  pf.openPublicPortal();
	  pf.search(propfile("SearchInput"));
	  pf.verifySocialTabs();
	  pf.verifyWebsiteTabs();
	  MyScreenRecorder.stopRecording();
  }
  
  @Test(priority=3)
  public void advanceSea() throws Exception  {
	  MyScreenRecorder.startRecording("AdvanceSearch");
	  pf.openPublicPortal();
	  pf.advanceSearch(propfile("SearchInput"), propfile("FromDateInput"), propfile("ToDateInput"), propfile("CheckBoxValue"), propfile("FilterInput"));
	  pf.verifySocialTabs();
	  pf.verifyWebsiteTabs();
	  MyScreenRecorder.stopRecording();
  }
  @Test(priority=4)
  public void generalWeb() throws Exception {
	  MyScreenRecorder.startRecording("SocialMediaFBPage");
	  pf.openPublicPortal();
	  pf.fbGenPage();
	  MyScreenRecorder.stopRecording();
  }
  
  @Test(priority=5)
  public void twitterpage() throws Exception {
	  MyScreenRecorder.startRecording("SocialMediaTwitterPage");
	  pf.openPublicPortal();
	  pf.twitterPage();
	  MyScreenRecorder.stopRecording();
  }
  
  
  
  
  @BeforeMethod
  public void beforeMethod() throws IOException {
	  pf.openBrowser();
  }

  @AfterMethod
  public void afterMethod() {
	  pf.closePage();
  }

}
