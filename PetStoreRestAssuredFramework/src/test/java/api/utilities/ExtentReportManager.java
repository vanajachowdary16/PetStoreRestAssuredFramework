package api.utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener {

	private static ExtentReports extent;
	private static ExtentSparkReporter sparkReporter;
	private static ThreadLocal<ExtentTest> testNode = new ThreadLocal<>();
	private static Map<String, ExtentTest> testSuiteMap = new ConcurrentHashMap<>();
	private static String reportName;

	public synchronized static ExtentReports getExtentInstance() {
		if (extent == null) {
			String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
			reportName = "Extent-Report-" + timeStamp + ".html";
			String reportPath = System.getProperty("user.dir") + "//reports//" + reportName;

			sparkReporter = new ExtentSparkReporter(reportPath);
			sparkReporter.config().setDocumentTitle("API Automation Test Report");
			sparkReporter.config().setReportName("API Test Execution Summary");
			sparkReporter.config().setTheme(Theme.STANDARD);

			extent = new ExtentReports();
			extent.attachReporter(sparkReporter);
			extent.setSystemInfo("Host Name", "Localhost");
			extent.setSystemInfo("Environment", "QA");
			extent.setSystemInfo("User", System.getProperty("user.name"));
		}
		return extent;
	}

	@Override
	public void onStart(ITestContext context) {
		String suiteName = context.getSuite().getName();
		ExtentTest suiteNode = getExtentInstance().createTest(suiteName);
		testSuiteMap.put(context.getName(), suiteNode);

		suiteNode.info("Suite started: " + suiteName);
		suiteNode.assignCategory("Suite: " + suiteName);

	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest suiteNode = testSuiteMap.get(result.getTestContext().getName());
		ExtentTest methodNode = suiteNode.createNode(result.getMethod().getMethodName());
		methodNode.assignCategory(result.getTestContext().getName());
		testNode.set(methodNode);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.PASS, result.getMethod().getMethodName() + " passed.");
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	@Override
	public void onTestFailure(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.FAIL, result.getMethod().getMethodName() + " failed.");
		test.log(Status.FAIL, result.getThrowable());
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		ExtentTest test = testNode.get();
		test.log(Status.SKIP, result.getMethod().getMethodName() + " skipped.");
		test.info("Reason: " + result.getThrowable());
		test.info("Execution Time: " + getExecutionTime(result) + " ms");
	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentTest suiteNode = testSuiteMap.get(context.getName());
		suiteNode.info("Suite finished: " + context.getSuite().getName());
		suiteNode.info("Passed: " + context.getPassedTests().size());
		suiteNode.info("Failed: " + context.getFailedTests().size());
		suiteNode.info("Skipped: " + context.getSkippedTests().size());

		getExtentInstance().flush();
		// openReport();
	}

	private void openReport() {
		try {
			File reportFile = new File(System.getProperty("user.dir") + "//reports//" + reportName);
			Desktop.getDesktop().browse(reportFile.toURI());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private long getExecutionTime(ITestResult result) {
		return result.getEndMillis() - result.getStartMillis();
	}
}
