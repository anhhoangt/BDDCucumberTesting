package MyTestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions (
		features = "src/test/resources/Features/UserRegistration.feature",
		glue= {"StepDefinitions"},
		plugin= {"pretty", "html:test-output", "json:target/cucumber-reports/Cucumber.json"}
		// tags = "@issues"  // Removed to run all 9 scenarios with elevated permissions
		)

public class TestRunner {

}
