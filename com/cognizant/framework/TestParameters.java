package com.cognizant.framework;

/**
 * Class to encapsulate various input parameters required for each test script
 * @author Cognizant
 */
public class TestParameters {
	private final String currentScenario;
	private final String currentTestcase;
	private String currentTestInstance;
	private String currentTestDescription;
	
	/**
	 * Constructor to initialize the {@link TestParameters} object
	 * @param currentScenario The current test scenario/module
	 * @param currentTestcase The current test case
	 */
	public TestParameters(String currentScenario, String currentTestcase) {
		this.currentScenario = currentScenario;
		this.currentTestcase = currentTestcase;
		
		// Set default values for all test parameters
		this.currentTestInstance = "Instance1";
		this.currentTestDescription = "";
	}
	
	/**
	 * Function to get the current test scenario/module
	 * @return The current test scenario/module
	 */
	public String getCurrentScenario() {
		return currentScenario;
	}
	
	/**
	 * Function to get the current test case
	 * @return The current test case
	 */
	public String getCurrentTestcase() {
		return currentTestcase;
	}
	
	/**
	 * Function to get the current test instance
	 * @return The current test instance
	 */
	public String getCurrentTestInstance() {
		return currentTestInstance;
	}
	
	/**
	 * Function to set the current test instance
	 * @param currentTestInstance The current test instance
	 */
	public void setCurrentTestInstance(String currentTestInstance) {
		this.currentTestInstance = currentTestInstance;
	}
	
	/**
	 * Function to get the description of the current test case
	 * @return The description of the current test case
	 */
	public String getCurrentTestDescription() {
		return currentTestDescription;
	}
	
	/**
	 * Function to set the description of the current test case
	 * @param currentTestDescription The description of the current test case
	 */
	public void setCurrentTestDescription(String currentTestDescription) {
		this.currentTestDescription = currentTestDescription;
	}
	
}