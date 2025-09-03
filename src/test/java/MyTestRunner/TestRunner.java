package MyTestRunner;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions (
		features = "src/test/resources/Features/UserProfile.feature",
		glue= {"StepDefinitions"},
		plugin= {"pretty", "html:test-output"},
		tags = "@collaborators"
		)

public class TestRunner {

}
