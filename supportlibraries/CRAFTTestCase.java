package supportlibraries;

import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.selenium.*;

import org.openqa.selenium.Platform;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;

/**
 * Abstract base class for all the test cases to be automated
 * @author Cognizant
 */
public abstract class CRAFTTestCase {
	/**
	 * The current scenario
	 */
	protected String currentScenario;
	/**
	 * The current test case
	 */
	protected String currentTestcase;
	
	/**
	 * Function to do the required framework setup activities before executing the overall test suite
	 * @param testContext The TestNG {@link ITestContext} of the current test suite 
	 */
	@SuppressWarnings("unused")
	@BeforeSuite
	public void setUpTestSuite(ITestContext testContext) {
		
		int nThreads;
		if ("false".equalsIgnoreCase(testContext.getSuite().getParallel())) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}
		
		// Note: Separate threads may be spawned through usage of DataProvider
		// testContext.getSuite().getXmlSuite().getDataProviderThreadCount();
		// This will be at test case level (multiple instances on same test case in parallel)
		// This level of threading will not be reflected in the summary report
		
	}
	
	/**
	 * Function to do the required framework setup activities before executing each test case
	 */
	@BeforeMethod
	public void setUpTestRunner() {
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		if(frameworkParameters.getStopExecution()) {
			// Throwing TestNG SkipException within a configuration method causes all subsequent test methods to be skipped/aborted
			throw new SkipException("Test execution terminated by user! All subsequent tests aborted...");
		} else {
			currentScenario = this.getClass().getPackage().getName().substring(12);
			currentTestcase = this.getClass().getSimpleName();
		}
	}
	
	@DataProvider(name = "GlobalTestConfigurations", parallel = true)
	public Object[][] dataGlobal() {
		return new Object[][] {
			{ "Instance4", ExecutionMode.LOCAL, Browser.FIREFOX, Platform.WINDOWS }
		};
	}
}