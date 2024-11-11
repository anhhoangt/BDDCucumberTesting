package MyTestRunner;

import org.junit.runner.RunWith;
import cucumber.api.junit.Cucumber;
import cucumber.api.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions (
		features = "Feature",
		glue= {"stepDefinitions"},
		format= {"pretty", "html:test-output"}
		)

public class TestRunner {

}
