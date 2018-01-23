package supportlibraries;

import org.openqa.selenium.WebDriver;

import com.cognizant.framework.CraftDataTable;
import com.cognizant.framework.DetailedReport;
import com.cognizant.framework.selenium.CraftDriver;

/**
 * Wrapper class for common framework objects, to be used across the entire test
 * case and dependent libraries
 * 
 * @author Cognizant
 */
public class ScriptHelper {

	private final CraftDataTable dataTable;
	private final DetailedReport report;
	private CraftDriver craftDriver;

	/**
	 * Constructor to initialize all the objects wrapped by the
	 * {@link ScriptHelper} class
	 * 
	 * @param dataTable
	 *            The {@link CraftDataTable} object
	 * @param report
	 *            The {@link SeleniumReport} object
	 * @param driver
	 *            The {@link WebDriver} object
	 * @param driverUtil
	 *            The {@link WebDriverUtil} object
	 */

	public ScriptHelper(CraftDataTable dataTable, CraftDriver craftDriver, DetailedReport report) {
		this.dataTable = dataTable;
		this.craftDriver = craftDriver;
		this.report = report;
	}

	/**
	 * Function to get the {@link CraftDataTable} object
	 * 
	 * @return The {@link CraftDataTable} object
	 */
	public CraftDataTable getDataTable() {
		return dataTable;
	}


	/**
	 * Function to get the {@link CraftDriver} object
	 * 
	 * @return The {@link CraftDriver} object
	 */
	public CraftDriver getcraftDriver() {
		return craftDriver;
	}
	
	public DetailedReport getReport(){
		return report;
	}

}