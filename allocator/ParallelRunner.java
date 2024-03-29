package allocator;

import supportlibraries.DriverScript;

import com.cognizant.framework.selenium.*;
import com.cognizant.framework.FrameworkParameters;


/**
 * Class to facilitate parallel execution of test scripts
 * @author Cognizant
 */
class ParallelRunner implements Runnable {
	private final SeleniumTestParameters testParameters;
	private int testBatchStatus = 0;
	
	
	/**
	 * Constructor to initialize the details of the test case to be executed
	 * @param testParameters The {@link SeleniumTestParameters} object (passed from the {@link Allocator})
	 */
	ParallelRunner(SeleniumTestParameters testParameters) {
		super();
		
		this.testParameters = testParameters;
	}
	
	/**
	 * Function to get the overall test batch status
	 * @return The test batch status (0 = Success, 1 = Failure)
	 */
	public int getTestBatchStatus() {
		return testBatchStatus;
	}
	
	@Override
	public void run() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		
		if(!frameworkParameters.getStopExecution()) {
			DriverScript driverScript = new DriverScript(this.testParameters);
			driverScript.driveTestExecution();
		}
		
	}
}