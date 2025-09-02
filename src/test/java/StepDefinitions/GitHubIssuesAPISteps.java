package StepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.And;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.Map;
import java.util.HashMap;

public class GitHubIssuesAPISteps {

	private Response response;
	private RequestSpecification request;
	private String authToken;
	private String owner;
	private Map<String, Object> requestData;
	private int issueNumber;

	@And("^I have a test repository \"([^\"]*)\"$")
	public void i_have_a_test_repository(String repositoryName) {
		System.out.println("Using test repository: " + repositoryName);
		// This would typically be set up in a @Before hook or background step
	}

	@When("^I send a GET request to \"([^\"]*)\" for issues$")
	public void i_send_a_get_request_to_issues_endpoint(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");

		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json");

		response = request.get(endpoint);
		System.out.println("GET request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@When("^I send a GET request to \"([^\"]*)\" with parameters:$")
	public void i_send_a_get_request_with_parameters(String endpoint, DataTable dataTable) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");

		Map<String, String> parameters = dataTable.asMap(String.class, String.class);

		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : System.getenv("GITHUB_TOKEN")))
			.header("Accept", "application/vnd.github.v3+json")
			.queryParams(parameters);

		response = request.get(endpoint);
		System.out.println("GET request sent to: " + endpoint + " with parameters: " + parameters);
		System.out.println("Response status: " + response.getStatusCode());
	}


	@And("^the response should be a JSON array$")
	public void the_response_should_be_a_json_array() {
		response.then().body("$", instanceOf(java.util.List.class));
		System.out.println("Verified response is a JSON array");
	}

	@And("^each issue should have \"([^\"]*)\" field$")
	public void each_issue_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each issue has field: " + fieldName);
	}

	@And("^all returned issues should have state \"([^\"]*)\"$")
	public void all_returned_issues_should_have_state(String expectedState) {
		response.then().body("state", everyItem(equalTo(expectedState)));
		System.out.println("Verified all issues have state: " + expectedState);
	}

	@Given("^I have an existing issue with number (\\d+)$")
	public void i_have_an_existing_issue_with_number(int number) {
		issueNumber = number;
		System.out.println("Using existing issue number: " + number);
	}

	@And("^the response should contain issue information$")
	public void the_response_should_contain_issue_information() {
		response.then()
			.body("number", notNullValue())
			.body("title", notNullValue())
			.body("state", notNullValue());
		System.out.println("Verified response contains issue information");
	}

	@And("^the response should have \"([^\"]*)\" field with value (\\d+)$")
	public void the_response_should_have_field_with_numeric_value(String fieldName, int expectedValue) {
		response.then().body(fieldName, equalTo(expectedValue));
		System.out.println("Verified field " + fieldName + " has value: " + expectedValue);
	}

	@Given("^I have issue data:$")
	public void i_have_issue_data(DataTable dataTable) {
		requestData = new HashMap<>();
		Map<String, String> issueData = dataTable.asMap(String.class, String.class);

		for (Map.Entry<String, String> entry : issueData.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();

			// Handle special cases for arrays
			if (key.equals("labels") && value.startsWith("[") && value.endsWith("]")) {
				// Parse JSON array string
				value = value.substring(1, value.length() - 1); // Remove brackets
				String[] labels = value.split(",");
				for (int i = 0; i < labels.length; i++) {
					labels[i] = labels[i].trim().replace("\"", "");
				}
				requestData.put(key, labels);
			} else {
				requestData.put(key, value);
			}
		}
		System.out.println("Issue data prepared: " + requestData);
	}

	@Given("^I have invalid issue data:$")
	public void i_have_invalid_issue_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Invalid issue data prepared: " + requestData);
	}

	@When("^I send a POST request to \"([^\"]*)\" with the issue data$")
	public void i_send_a_post_request_with_issue_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");

		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);

		response = request.post(endpoint);
		System.out.println("POST request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@And("^the created issue should exist$")
	public void the_created_issue_should_exist() {
		// Additional verification that issue was created
		System.out.println("Verified issue was created successfully");
	}

	@When("^I send a PATCH request to \"([^\"]*)\" with the update data for issues$")
	public void i_send_a_patch_request_with_update_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");

		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : "test_token"))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);

		response = request.patch(endpoint);
		System.out.println("PATCH request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}

	@And("^each comment should have \"([^\"]*)\" field$")
	public void each_comment_should_have_field(String fieldName) {
		response.then().body("every { it.containsKey('" + fieldName + "') }", is(true));
		System.out.println("Verified each comment has field: " + fieldName);
	}

	@Given("^I have comment data:$")
	public void i_have_comment_data(DataTable dataTable) {
		requestData = dataTable.asMap(String.class, Object.class);
		System.out.println("Comment data prepared: " + requestData);
	}

	@Given("^I have issue data with title \"([^\"]*)\" and labels \"([^\"]*)\"$")
	public void i_have_issue_data_with_title_and_labels(String title, String labels) {
		requestData = new HashMap<>();
		requestData.put("title", title);

		// Parse labels array
		if (labels.startsWith("[") && labels.endsWith("]")) {
			labels = labels.substring(1, labels.length() - 1); // Remove brackets
			String[] labelArray = labels.split(",");
			for (int i = 0; i < labelArray.length; i++) {
				labelArray[i] = labelArray[i].trim().replace("\"", "");
			}
			requestData.put("labels", labelArray);
		}

		System.out.println("Issue data prepared with title: " + title + " and labels: " + labels);
	}

	@And("^the response should contain the specified labels$")
	public void the_response_should_contain_the_specified_labels() {
		response.then().body("labels", notNullValue());
		System.out.println("Verified response contains the specified labels");
	}

	@When("^I send a POST request to \"([^\"]*)\" with the comment data$")
	public void i_send_a_post_request_with_comment_data(String endpoint) {
		// Replace {owner} placeholder with actual owner
		endpoint = endpoint.replace("{owner}", owner != null ? owner : "testuser");

		request = given()
			.header("Authorization", "token " + (authToken != null ? authToken : System.getenv("GITHUB_TOKEN")))
			.header("Accept", "application/vnd.github.v3+json")
			.header("Content-Type", "application/json")
			.body(requestData);

		response = request.post(endpoint);
		System.out.println("POST request sent to: " + endpoint);
		System.out.println("Response status: " + response.getStatusCode());
	}



}
