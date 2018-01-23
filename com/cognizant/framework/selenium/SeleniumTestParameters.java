package com.cognizant.framework.selenium;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.TestParameters;

import org.openqa.selenium.Platform;

/**
 * Class to encapsulate various input parameters required for each test script
 * 
 * @author Cognizant
 */
public class SeleniumTestParameters extends TestParameters {
	private ExecutionMode executionMode;
	private Browser browser;
	private String browserVersion;
	private Platform platform;
	private MobileExecutionPlatform mobileExecutionPlatform;
	private String deviceName;
	private String perfectoDeviceId;
	
	public SeleniumTestParameters(String currentScenario, String currentTestcase) {
		super(currentScenario, currentTestcase);
	}

	/**
	 * Function to get the {@link ExecutionMode} for the test being executed
	 * 
	 * @return The {@link ExecutionMode} for the test being executed
	 */
	public ExecutionMode getExecutionMode() {
		return executionMode;
	}

	/**
	 * Function to set the {@link ExecutionMode} for the test being executed
	 * 
	 * @param executionMode
	 *            The {@link ExecutionMode} for the test being executed
	 */
	public void setExecutionMode(ExecutionMode executionMode) {
		this.executionMode = executionMode;
	}

	/**
	 * Function to get the {@link Browser} on which the test is to be executed
	 * 
	 * @return The {@link Browser} on which the test is to be executed
	 */
	public Browser getBrowser() {
		return browser;
	}

	/**
	 * Function to set the {@link Browser} on which the test is to be executed
	 * 
	 * @param browser
	 *            The {@link Browser} on which the test is to be executed
	 */
	public void setBrowser(Browser browser) {
		this.browser = browser;
	}

	/**
	 * Function to get the Browser Version on which the test is to be executed
	 * 
	 * @return The Browser Version on which the test is to be executed
	 */
	public String getBrowserVersion() {
		return browserVersion;
	}

	/**
	 * Function to set the Browser Version on which the test is to be executed
	 * 
	 * @param version
	 *            The Browser Version on which the test is to be executed
	 */
	public void setBrowserVersion(String version) {
		this.browserVersion = version;
	}

	/**
	 * Function to get the {@link Platform} on which the test is to be executed
	 * 
	 * @return The {@link Platform} on which the test is to be executed
	 */
	public Platform getPlatform() {
		return platform;
	}

	/**
	 * Function to set the {@link Platform} on which the test is to be executed
	 * 
	 * @param platform
	 *            The {@link Platform} on which the test is to be executed
	 */
	public void setPlatform(Platform platform) {
		this.platform = platform;
	}

	/**
	 * Function to get the browser and platform on which the test is to be
	 * executed
	 * 
	 * @return The browser and platform on which the test is to be executed
	 */
	public String getBrowserAndPlatform() {
		if (this.browser == null) {
			throw new FrameworkException(
					"The browser has not been initialized!");
		}

		String browserAndPlatform = this.browser.toString();
		if (this.browserVersion != null) {
			browserAndPlatform += " " + browserVersion;
		}
		if (this.platform != null) {
			browserAndPlatform += " on " + this.platform;
		}

		return browserAndPlatform;
	}
	
	public MobileExecutionPlatform getMobileExecutionPlatform() {
		return mobileExecutionPlatform;
	}

	public void setMobileExecutionPlatform(
			MobileExecutionPlatform mobileExecutionPlatform) {
		this.mobileExecutionPlatform = mobileExecutionPlatform;
	}
	
	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;

		if (ExecutionMode.PERFECTO.equals(this.executionMode)) {
			// Properties properties = Settings.getInstance();
			this.perfectoDeviceId = this.deviceName;
		}
	}

}