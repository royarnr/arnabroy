package com.cognizant.framework.selenium;

import java.sql.Driver;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import com.cognizant.framework.Settings;
import com.sun.tracing.dtrace.ProviderAttributes;

public class PerfectoDashboard {
	
	private WebDriver perfectodashboard = null;
	private Properties properties;
	private static final PerfectoDashboard dashboard = new PerfectoDashboard();
	
	private PerfectoDashboard()
	{
	}
	
	public void launchDashboard()
	{
		properties = Settings.getInstance();
		System.setProperty("webdriver.ie.driver", properties.getProperty("InternetExplorerDriverPath"));
		
		perfectodashboard = new InternetExplorerDriver();
		perfectodashboard.get("https://manulife.perfectomobile.com/nexperience/dashboard.jsp?autoWatch=true&");
		
		try {
			
			perfectodashboard.findElement(By.name("username")).sendKeys(properties.getProperty("PerfectoUser"));
			perfectodashboard.findElement(By.name("password")).sendKeys(properties.getProperty("PerfectoPassword"));
			perfectodashboard.findElement(By.name("submitBtn")).click();
			
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			//do nothing
		}
	}
	
	public static PerfectoDashboard getInstance()
	{
		return dashboard;
	}
	
	public WebDriver getDashboard()
	{
		return perfectodashboard;
	}
	
	public void closeDashboard()
	{
		perfectodashboard.close();
	}

}
