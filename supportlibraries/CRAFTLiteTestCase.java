package supportlibraries;

import java.util.Properties;

import org.openqa.selenium.Platform;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;

import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;
import com.cognizant.framework.selenium.*;

/**
 * Abstract base class for all the test cases to be automated
 * @author Cognizant
 */
public abstract class CRAFTLiteTestCase {
	/**
	 * The current scenario
	 */
	protected String currentScenario;
	/**
	 * The current test case
	 */
	protected String currentTestcase;
	
	/**
	 * The {@link CraftliteDataTable} object (passed from the Driver script)
	 */
	protected CraftDataTable dataTable;
	/**
	 * The {@link CraftDriver} object (passed from the Driver script)
	 */
	protected CraftDriver driver;
	
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library from another)
	 */
	protected ScriptHelper scriptHelper;
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	protected Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters =
										FrameworkParameters.getInstance();
	
	
	/**
	 * Function to initialize the various objects that may beed to be used with a test script
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public void initialize(ScriptHelper scriptHelper) {
		this.scriptHelper = scriptHelper;
		
		this.dataTable = scriptHelper.getDataTable();
		this.driver = scriptHelper.getcraftDriver();
		
		properties = Settings.getInstance();
	}
	
	/**
	 * Function to do the required framework setup activities before executing each test case
	 */
	@BeforeMethod
	public void setUpTestRunner() {
		if(frameworkParameters.getStopExecution()) {
			// Throwing TestNG SkipException within a configuration method causes all subsequent test methods to be skipped/aborted
			throw new SkipException("Aborting all subsequent tests!");
		} else {
			currentScenario = this.getClass().getPackage().getName().substring(12);
			currentTestcase = this.getClass().getSimpleName();
		}
	}
	
	/**
	 * Function to handle any pre-requisite steps required before beginning the test case execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void setUp();
	
	/**
	 * Function to handle the core test steps required as part of the test case
	 */
	public abstract void executeTest();
	
	/**
	 * Function to handle any clean-up steps required after completing the test case execution <br>
	 * <u>Note</u>: This function can be left blank if not applicable
	 */
	public abstract void tearDown();
	
	@DataProvider(name = "GlobalTestConfigurations", parallel = true)
	public Object[][] dataGlobal() {
		return new Object[][] {
			//{ "Instance4", ExecutionMode.PERFECTO_REMOTEWEBDRIVER, "Apple_iPad_Air", Browser.PERFECTO_MOBILE_SAFARI, Platform.ANY },
			//{ "Instance5", ExecutionMode.PERFECTO_REMOTEWEBDRIVER, "Samsung_Galaxy_S6", Browser.PERFECTO_MOBILE_CHROME, Platform.ANDROID },
			//{ "Instance6", ExecutionMode.PERFECTO_REMOTEWEBDRIVER, "Samsung_Galaxy_S5", Browser.PERFECTO_MOBILE, Platform.ANDROID },
			//{ "Instance1", ExecutionMode.LOCAL, "N/A", Browser.CHROME, Platform.WINDOWS },
		//	{ "Instance2", ExecutionMode.LOCAL, "N/A", Browser.FIREFOX, Platform.WINDOWS },
			{ "Instance3", ExecutionMode.LOCAL, "N/A", Browser.FIREFOX, Platform.WINDOWS }
		};
	}
}